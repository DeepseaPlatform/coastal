package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.Random;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.strategy.StrategyManager;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public class RandomPathFactory implements StrategyFactory {

	public RandomPathFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new PathBasedManager(coastal);
	}

	@Override
	public Strategy createStrategy(COASTAL coastal, StrategyManager manager) {
		return new RandomPathStrategy(coastal, manager);
	}

	// ======================================================================
	//
	// SPECIFIC FACTORY
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
						pc = cur.getPcForChild(i, pc);
						cur = ch;
						continue outer;
					}
				}
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
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
