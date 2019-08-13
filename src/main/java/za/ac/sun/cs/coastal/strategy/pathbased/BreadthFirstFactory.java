package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.LinkedList;
import java.util.Queue;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.symbolic.Path;

public class BreadthFirstFactory extends PathBasedFactory {

	public BreadthFirstFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new BreadthFirstStrategyManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((BreadthFirstStrategyManager) manager).incrementTaskCount();
		return new Strategy[] { new BreadthFirstStrategy(coastal, (StrategyManager) manager) };
	}

	// ======================================================================
	//
	// BREADTH-FIRST SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	private static class BreadthFirstStrategyManager extends PathBasedManager {

		protected int taskCount = 0;

		BreadthFirstStrategyManager(COASTAL coastal) {
			super(coastal);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		public String getName() {
			return "BreadthFirstStrategy";
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT FINDS NEW PATHS WITH BREADTH-FIRST SEARCH
	//
	// ======================================================================

	private static class BreadthFirstStrategy extends PathBasedStrategy {

		BreadthFirstStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
		}

		@Override
		public Path findNewPath(PathTree pathTree) {
			Queue<PathTreeNode> workingSet = new LinkedList<>();
			PathTreeNode root = pathTree.getRoot();
			if (root == null) {
				return null;
			}
			workingSet.add(root);
			while (!workingSet.isEmpty()) {
				PathTreeNode node = workingSet.remove();
				int n = node.getChildCount();
				for (int i = 0; i < n; i++) {
					PathTreeNode childNode = node.getChild(i);
					if (childNode == null) {
						return node.getPathForChild(i);
					} else if (!childNode.isComplete()) {
						workingSet.add(childNode);
					}
				}
			}
			return null;
		}

	}

}
