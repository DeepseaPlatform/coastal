package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.diver.SegmentedPC.SegmentedPCIf;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.Path;

public class GenerationalFactory extends PathBasedFactory {

	public GenerationalFactory(COASTAL coastal, ImmutableConfiguration options) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new GenerationalManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((GenerationalManager) manager).incrementTaskCount();
		Strategy strategy = null;
		if (((GenerationalManager) manager).full) {
			strategy = new GenerationalFullStrategy(coastal, (StrategyManager) manager);
		} else {
			strategy = new GenerationalStrategy(coastal, (StrategyManager) manager);
		}
		return new Strategy[] { strategy };
	}

	// ======================================================================
	//
	// MANAGER FOR GENERATIONAL SEARCH
	//
	// ======================================================================

	private static class GenerationalManager extends PathBasedManager {

		protected int taskCount = 0;

		private final int priorityStart;

		private final int priorityDelta;

		private final boolean full;

		GenerationalManager(COASTAL coastal) {
			super(coastal);
			if (coastal.getConfig().getBoolean("coastal.strategy[@topdown]", false)) {
				priorityStart = 0;
				priorityDelta = 1;
			} else {
				priorityStart = 10000;
				priorityDelta = -1;
			}
			full = coastal.getConfig().getBoolean("coastal.strategy[@full]", true);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		public String getName() {
			return "GenerationalStrategy";
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT NEGATES EACH CONJUNCT ALONG PATH
	//
	// ======================================================================

	private static class GenerationalFullStrategy extends PathBasedStrategy {

		private final int priorityStart;

		private final int priorityDelta;

		GenerationalFullStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			priorityStart = ((GenerationalManager) manager).priorityStart;
			priorityDelta = ((GenerationalManager) manager).priorityDelta;
		}

		@Override
		protected List<Model> refine0(Execution execution) {
			if (execution == null) {
				return null;
			}
			List<Model> models = new ArrayList<>();
			Path path = execution.getPath();
			log.trace("explored <{}> {}", path.getSignature(), path.getPathCondition().toString());
			PathTreeNode bottom = manager.insertPath0(execution, false);
			if (bottom != null) {
				List<Path> altPaths = new ArrayList<>();
				bottom = bottom.getParent();
				for (Path pointer = path; (pointer != null) && !bottom.hasBeenGenerated(); pointer = pointer.getParent()) {
					altPaths.add(generateAltSpc(execution, path));
					bottom.setGenerated();
					bottom = bottom.getParent();
				}
				int priority = priorityStart;
				for (Path altPath : altPaths) {
					Expression pc = altPath.getPathCondition();
					String sig = altPath.getSignature();
					log.trace("trying   <{}> {}", sig, pc.toString());
					Input model = solver.solve(pc);
					if (model == null) {
						log.trace("no model");
						log.trace("(The spc is {})", altPath.getPathCondition().toString());
						manager.insertPath(altPath, true);
					} else {
						String modelString = model.toString();
						log.trace("new model: {}", modelString);
						if (visitedModels.add(modelString)) {
							models.add(new Model(priority, model));
							priority += priorityDelta;
						} else {
							log.trace("model {} has been visited before", modelString);
						}
					}
				}
			} else {
				log.trace("revisited path -- no new models generated");
			}
			return models;
		}

		private Path generateAltSpc(Execution execution, Path pointer) {
			SegmentedPC parent = null;
			boolean value = ((SegmentedPCIf) execution).getValue();
			if (execution == pointer) {
				parent = execution.getParent();
				value = !value;
			} else {
				parent = generateAltSpc(execution.getParent(), pointer);
			}
			return new SegmentedPCIf(parent, execution.getExpression(), execution.getPassiveConjunct(), value);
		}

		@Override
		protected Path findNewPath(PathTree pathTree) {
			return null;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT NEGATES EACH CONJUNCT ALONG TRUNCATED PATH
	//
	// ======================================================================

	/**
	 * A generational strategy: a path condition such as
	 * {@code A && B && C && D} is negated and truncated conjunct by conjunct to
	 * produce {@code A && B && C && !D}, {@code A && B && !C}, {@code A && !B},
	 * {@code !A}.
	 */
	private static class GenerationalStrategy extends PathBasedStrategy {

		/**
		 * The priority of the longest generated new path condition.
		 */
		private final int priorityStart;

		/**
		 * The change in priority for shorter and shorter generated path
		 * conditions.
		 */
		private final int priorityDelta;

		/**
		 * Construct a new task that implements the generational strategy.
		 * 
		 * @param coastal
		 *            instance of COASTAL
		 * @param manager
		 *            a generational task manager
		 */
		GenerationalStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			priorityStart = ((GenerationalManager) manager).priorityStart;
			priorityDelta = ((GenerationalManager) manager).priorityDelta;
		}

		@Override
		protected List<Model> refine0(SegmentedPC spc) {
			if ((spc == null) || (spc == SegmentedPC.NULL)) {
				return null;
			}
			List<Model> models = new ArrayList<>();
			log.trace("explored <{}> {}", spc.getSignature(), spc.getPathCondition().toString());
			PathTreeNode bottom = manager.insertPath0(spc, false);
			if (bottom != null) {
				List<SegmentedPC> altSpcs = new ArrayList<>();
				bottom = bottom.getParent();
				for (SegmentedPC pointer = spc; (pointer != null)
						&& !bottom.hasBeenGenerated(); pointer = pointer.getParent()) {
					altSpcs.add(generateAltSpc(pointer));
					bottom.setGenerated();
					bottom = bottom.getParent();
				}
				int priority = priorityStart;
				for (SegmentedPC altSpc : altSpcs) {
					Expression pc = altSpc.getPathCondition();
					String sig = altSpc.getSignature();
					log.trace("trying   <{}> {}", sig, pc.toString());
					Input model = solver.solve(pc);
					if (model == null) {
						log.trace("no model");
						log.trace("(The spc is {})", altSpc.getPathCondition().toString());
						manager.insertPath(altSpc, true);
					} else {
						String modelString = model.toString();
						log.trace("new model: {}", modelString);
						if (visitedModels.add(modelString)) {
							models.add(new Model(priority, model));
							priority += priorityDelta;
						} else {
							log.trace("model {} has been visited before", modelString);
						}
					}
				}
			} else {
				log.trace("revisited path -- no new models generated");
			}
			return models;
		}

		private SegmentedPC generateAltSpc(SegmentedPC pointer) {
			SegmentedPC parent = pointer.getParent();
			Expression pc = pointer.getExpression();
			Expression passive = pointer.getPassiveConjunct();
			boolean value = ((SegmentedPCIf) pointer).getValue();
			return new SegmentedPCIf(parent, pc, passive, !value);
		}

		@Override
		protected Path findNewPath(PathTree pathTree) {
			return null;
		}

	}

}
