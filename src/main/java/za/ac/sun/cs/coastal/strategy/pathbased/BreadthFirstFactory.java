package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.LinkedList;
import java.util.Queue;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.strategy.StrategyManager;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public class BreadthFirstFactory implements StrategyFactory {

	public BreadthFirstFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new PathBasedManager(coastal);
	}

	@Override
	public Strategy createStrategy(COASTAL coastal, StrategyManager manager) {
		return new BreadthFirstStrategy(coastal, manager);
	}

	// ======================================================================
	//
	// SPECIFIC FACTORY
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
