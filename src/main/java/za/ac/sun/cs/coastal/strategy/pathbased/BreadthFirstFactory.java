package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.LinkedList;
import java.util.Queue;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;

public class BreadthFirstFactory implements StrategyFactory {

	public BreadthFirstFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new BreadthFirstStrategyManager(coastal);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((BreadthFirstStrategyManager) manager).incrementTaskCount();
		return new BreadthFirstStrategy(coastal, (StrategyManager) manager);
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
		public SegmentedPC findNewPath(PathTree pathTree) {
			Queue<SegmentedPC> workingPCs = new LinkedList<>();
			Queue<PathTreeNode> workingSet = new LinkedList<>();
			workingSet.add(pathTree.getRoot());
			while (!workingSet.isEmpty()) {
				SegmentedPC parent = workingPCs.isEmpty() ? null : workingPCs.remove();
				PathTreeNode node = workingSet.remove();
				int n = node.getChildCount();
				for (int i = 0; i < n; i++) {
					PathTreeNode ch = node.getChild(i);
					if (ch == null) {
						return node.getPcForChild(i, parent);
					} else if (!ch.isComplete()) {
						workingPCs.add(node.getPcForChild(i, parent));
						workingSet.add(ch);
					}
				}
			}
			return null;
		}

	}

}
