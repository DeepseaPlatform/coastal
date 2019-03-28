package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.Choice;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
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
	// ABSTRACT PARENT GENERATIOANL STRATEGY
	//
	// ======================================================================

	private abstract static class GenerationalAbstractStrategy extends PathBasedStrategy {

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
		GenerationalAbstractStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			priorityStart = ((GenerationalManager) manager).priorityStart;
			priorityDelta = ((GenerationalManager) manager).priorityDelta;
		}

		@Override
		protected final List<Input> refine0(Execution execution) {
			if (execution == null) {
				return null;
			}
			List<Input> inputs = new ArrayList<>();
			Path path = execution.getPath();
			log.trace("explored <{}> {}", path.getSignature(), path.getPathCondition().toString());
			PathTreeNode bottom = manager.insertPath0(execution, false);
			if (bottom != null) {
				List<Path> altPaths = new ArrayList<>();
				bottom = bottom.getParent();
				int depth = bottom.getPath().getDepth();
				for (Path pointer = path; (pointer != null) && !bottom.hasBeenGenerated(); pointer = pointer.getParent(), depth--) {
					altPaths.addAll(generateAltPaths(path, pointer, depth));
					bottom.setGenerated();
					bottom = bottom.getParent();
				}
				int priority = priorityStart;
				for (Path altPath : altPaths) {
					Expression pc = altPath.getPathCondition();
					String sig = altPath.getSignature();
					log.trace("trying   <{}> {}", sig, pc.toString());
					Input input = solver.solve(pc);
					if (input == null) {
						log.trace("no model");
						log.trace("(The path is {})", altPath.getPathCondition().toString());
						manager.insertPath(altPath, true);
					} else {
						String modelString = input.toString();
						log.trace("new model: {}", modelString);
						if (visitedInputs.add(modelString)) {
							input.setPayload("priority", priority);
							inputs.add(input);
							priority += priorityDelta;
						} else {
							log.trace("model {} has been visited before", modelString);
						}
					}
				}
			} else {
				log.trace("revisited path -- no new models generated");
			}
			return inputs;
		}

		protected abstract List<Path> generateAltPaths(Path path, Path pointer, int depth);

		@Override
		public final Path findNewPath(PathTree pathTree) {
			return null;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT NEGATES EACH CONJUNCT ALONG PATH
	//
	// ======================================================================

	private static class GenerationalFullStrategy extends GenerationalAbstractStrategy {

		GenerationalFullStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
		}

		protected final List<Path> generateAltPaths(Path path, Path pointer, int targetDepth) {
			List<Path> altPaths = new ArrayList<>();
			Choice lastChoice = pointer.getChoice();
			for (long i = 0, n = lastChoice.getBranch().getNumberOfAlternatives(); i < n; i++) {
				if (i != lastChoice.getAlternative()) {
					altPaths.add(generateAltPath(path, targetDepth, i));
				}
			}
			return altPaths;
		}

		private Path generateAltPath(Path path, int targetDepth, long alternative) {
			if (targetDepth > 0) {
				return new Path(generateAltPath(path.getParent(), targetDepth - 1, alternative), path.getChoice());
			} else {
				return new Path(path.getParent(), path.getChoice().getAlternative(alternative));
			}
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
	private static class GenerationalStrategy extends GenerationalAbstractStrategy {

		GenerationalStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
		}

		protected final List<Path> generateAltPaths(Path path, Path pointer, int targetDepth) {
			List<Path> altPaths = new ArrayList<>();
			Choice lastChoice = pointer.getChoice();
			for (long i = 0, n = lastChoice.getBranch().getNumberOfAlternatives(); i < n; i++) {
				if (i != lastChoice.getAlternative()) {
					altPaths.add(new Path(pointer.getParent(), lastChoice.getAlternative(i)));
				}
			}
			return altPaths;
		}

	}

}
