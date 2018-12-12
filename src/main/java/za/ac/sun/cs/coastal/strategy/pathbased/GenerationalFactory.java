package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.diver.SegmentedPCIf;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;

public class GenerationalFactory extends PathBasedFactory {

	public GenerationalFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new GenerationalManager(coastal);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((GenerationalManager) manager).incrementTaskCount();
		if (((GenerationalManager) manager).full) {
			return new GenerationalFullStrategy(coastal, (StrategyManager) manager);
		} else {
			return new GenerationalStrategy(coastal, (StrategyManager) manager);
		}
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
					altSpcs.add(generateAltSpc(spc, pointer));
					bottom.setGenerated();
					bottom = bottom.getParent();
				}
				int priority = priorityStart;
				for (SegmentedPC altSpc : altSpcs) {
					Expression pc = altSpc.getPathCondition();
					String sig = altSpc.getSignature();
					log.trace("trying   <{}> {}", sig, pc.toString());
					Map<String, Constant> model = findModel(pc);
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

		private SegmentedPC generateAltSpc(SegmentedPC spc, SegmentedPC pointer) {
			SegmentedPC parent = null;
			boolean value = ((SegmentedPCIf) spc).getValue();
			if (spc == pointer) {
				parent = spc.getParent();
				value = !value;
			} else {
				parent = generateAltSpc(spc.getParent(), pointer);
			}
			return new SegmentedPCIf(parent, spc.getExpression(), spc.getPassiveConjunct(), value);
		}

		@Override
		protected SegmentedPC findNewPath(PathTree pathTree) {
			return null;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT NEGATES EACH CONJUNCT ALONG TRUNCATED PATH
	//
	// ======================================================================

	private static class GenerationalStrategy extends PathBasedStrategy {

		private final int priorityStart;

		private final int priorityDelta;

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
					Map<String, Constant> model = findModel(pc);
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
		protected SegmentedPC findNewPath(PathTree pathTree) {
			return null;
		}

	}

}
