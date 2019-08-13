package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.Random;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.symbolic.Path;

public class RandomPathFactory extends PathBasedFactory {

	private final Configuration config;

	public RandomPathFactory(COASTAL coastal, Configuration config) {
		this.config = config;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new RandomStrategyManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((RandomStrategyManager) manager).incrementTaskCount();
		return new Strategy[] { new RandomPathStrategy(coastal, config, (StrategyManager) manager) };
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

		RandomPathStrategy(COASTAL coastal, Configuration config, StrategyManager manager) {
			super(coastal, manager);
			rng.setSeed(config.getLong("seed", 0, System.currentTimeMillis()));
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
