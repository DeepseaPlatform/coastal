package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.IntConstant;

public class RandomTestingFactory extends TraceBasedFactory {

	public RandomTestingFactory(COASTAL coastal) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new RandomTestingManager(coastal);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((RandomTestingManager) manager).incrementTaskCount();
		return new RandomTestingStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// RANDOM TESTING MANAGER
	//
	// ======================================================================

	public static class RandomTestingManager extends TraceBasedManager {

		protected int taskCount = 0;

		public RandomTestingManager(COASTAL coastal) {
			super(coastal);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		protected int getTaskCount() {
			return 0;
		}

		@Override
		public String getName() {
			return "RandomTesting";
		}

	}

	// ======================================================================
	//
	// RANDOM TESTER STRATEGY
	//
	// ======================================================================

	public static class RandomTestingStrategy extends TraceBasedStrategy {

		private final Random rng = new Random();

		private final PathTree pathTree;

		private final Map<String, Constant> concreteValues = new HashMap<>();

		private int limit = 1000;

		public RandomTestingStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			pathTree = coastal.getPathTree();
		}

		@Override
		protected List<Model> refine0(Trace trace) {
			if (limit-- < 0) {
				return null;
			}
			if ((trace == null) || (trace == Trace.NULL)) {
				return null;
			}
			log.trace("... explored <{}>", trace.getSignature());
			manager.insertPath(trace, false);
			if (pathTree.getRoot().isFullyExplored()) {
				return null;
			}
			for (Map.Entry<String, Class<?>> parameter : coastal.getParameters().entrySet()) {
				String name = parameter.getKey();
				Class<?> type = parameter.getValue();
				if (type == boolean.class) {
					int min = (Integer) coastal.getMinBound(name, type);
					int max = (Integer) coastal.getMaxBound(name, type);
					int value = min + rng.nextInt(max - min + 1);
					concreteValues.put(name, new IntConstant(value));
				} else if (type == byte.class) {
					int min = (Byte) coastal.getMinBound(name, type);
					int max = (Byte) coastal.getMaxBound(name, type);
					int value = min + rng.nextInt(max - min + 1);
					concreteValues.put(name, new IntConstant(value));
				} else if (type == short.class) {
					int min = (Short) coastal.getMinBound(name, type);
					int max = (Short) coastal.getMaxBound(name, type);
					int value = min + rng.nextInt(max - min + 1);
					concreteValues.put(name, new IntConstant(value));
				} else if (type == char.class) {
					int min = (Character) coastal.getMinBound(name, type);
					int max = (Character) coastal.getMaxBound(name, type);
					int value = min + rng.nextInt(max - min + 1);
					concreteValues.put(name, new IntConstant(value));
				} else if (type == int.class) {
					int min = (Integer) coastal.getMinBound(name, type);
					int max = (Integer) coastal.getMaxBound(name, type);
					int value = min + rng.nextInt(max - min + 1);
					concreteValues.put(name, new IntConstant(value));
				}
			}
			String modelString = concreteValues.toString();
			log.trace("... new model: {}", modelString);
			return Collections.singletonList(new Model(0, concreteValues));
		}

	}

}
