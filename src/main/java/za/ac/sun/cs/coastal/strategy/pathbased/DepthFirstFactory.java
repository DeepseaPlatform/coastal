package za.ac.sun.cs.coastal.strategy.pathbased;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.strategy.StrategyManager;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public class DepthFirstFactory implements StrategyFactory {

	public DepthFirstFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new PathBasedManager(coastal);
	}

	@Override
	public Strategy createStrategy(COASTAL coastal, StrategyManager manager) {
		return new DepthFirstStrategy(coastal, manager);
	}

	// ======================================================================
	//
	// SPECIFIC FACTORY
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
