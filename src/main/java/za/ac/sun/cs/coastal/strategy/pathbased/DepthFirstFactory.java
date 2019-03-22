package za.ac.sun.cs.coastal.strategy.pathbased;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;

public class DepthFirstFactory extends PathBasedFactory {

	public DepthFirstFactory(COASTAL coastal, ImmutableConfiguration options) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new DepthFirstStrategyManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((DepthFirstStrategyManager) manager).incrementTaskCount();
		return new Strategy[] { new DepthFirstStrategy(coastal, (StrategyManager) manager) };
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
						pc = (SegmentedPC) cur.getExecutionForChild(i, pc);
						cur = ch;
						continue outer;
					}
				}
				for (int i = 0; i < n; i++) {
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
