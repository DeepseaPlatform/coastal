package za.ac.sun.cs.coastal.strategy.pathbased;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;

public class DepthFirstFactory implements StrategyFactory {

	public DepthFirstFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new DepthFirstStrategyManager(coastal);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((DepthFirstStrategyManager) manager).incrementTaskCount();
		return new DepthFirstStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// DEPTH-FIRST SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	private static class DepthFirstStrategyManager extends PathBasedManager {

		protected int taskCount = 0;

		DepthFirstStrategyManager(COASTAL coastal) {
			super(coastal);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		public String getName() {
			return "DepthFirstStrategy";
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT FINDS NEW PATHS WITH DEPTH-FIRST SEARCH
	//
	// ======================================================================

	private static class DepthFirstStrategy extends PathBasedStrategy {

		DepthFirstStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
		}

		@Override
		public SegmentedPC findNewPath(PathTree pathTree) {
			SegmentedPC pc = null;
			PathTreeNode cur = pathTree.getRoot();
			outer: while (true) {
				int n = cur.getChildCount();
				for (int i = 0; i < n; i++) {
					PathTreeNode ch = cur.getChild(i);
					if ((ch != null) && !ch.isComplete()) {
						pc = cur.getPcForChild(i, pc);
						cur = ch;
						continue outer;
					}
				}
				for (int i = 0; i < n; i++) {
					PathTreeNode ch = cur.getChild(i);
					if (ch == null) {
						return cur.getPcForChild(i, pc);
					}
				}
				return null;
			}
		}

	}

}
