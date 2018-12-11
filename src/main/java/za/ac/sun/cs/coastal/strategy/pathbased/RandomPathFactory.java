package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.Random;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;

public class RandomPathFactory implements StrategyFactory {

	public RandomPathFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new RandomStrategyManager(coastal);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((RandomStrategyManager) manager).incrementTaskCount();
		return new RandomPathStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// RANDOM SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	private static class RandomStrategyManager extends PathBasedManager {

		protected int taskCount = 0;

		RandomStrategyManager(COASTAL coastal) {
			super(coastal);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		public String getName() {
			return "RandomStrategy";
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT FINDS NEW PATHS WITH RANDOM SEARCH
	//
	// ======================================================================

	private static class RandomPathStrategy extends PathBasedStrategy {

		private final Random rng = new Random();

		RandomPathStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			Long seed = coastal.getConfig().getLong("coastal.strategy[@seed]");
			if (seed != null) {
				rng.setSeed(seed);
			}
		}

		@Override
		public SegmentedPC findNewPath(PathTree pathTree) {
			SegmentedPC pc = null;
			PathTreeNode cur = pathTree.getRoot();
			outer: while (true) {
				int n = cur.getChildCount();
				int i = rng.nextInt(n);
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode ch = cur.getChild(i);
					if ((ch != null) && !ch.isComplete()) {
						pc = (SegmentedPC) cur.getExecutionForChild(i, pc);
						cur = ch;
						continue outer;
					}
				}
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode ch = cur.getChild(i);
					if (ch == null) {
						return (SegmentedPC) cur.getExecutionForChild(i, pc);
					}
				}
				return null;
			}
		}

	}

}
