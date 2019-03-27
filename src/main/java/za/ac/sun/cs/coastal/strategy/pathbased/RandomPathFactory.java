package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.Random;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.symbolic.Path;

public class RandomPathFactory extends PathBasedFactory {

	public RandomPathFactory(COASTAL coastal, ImmutableConfiguration options) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new RandomStrategyManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((RandomStrategyManager) manager).incrementTaskCount();
		return new Strategy[] { new RandomPathStrategy(coastal, (StrategyManager) manager) };
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
		public Path findNewPath(PathTree pathTree) {
			PathTreeNode curNode = pathTree.getRoot();
			outer: while (true) {
				int n = curNode.getChildCount();
				int i = rng.nextInt(n);
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode childNode = curNode.getChild(i);
					if ((childNode != null) && !childNode.isComplete()) {
						curNode = childNode;
						continue outer;
					}
				}
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode childNode = curNode.getChild(i);
					if (childNode == null) {
						return curNode.getPathForChild(i);
					}
				}
				return null;
			}
		}

	}

}
