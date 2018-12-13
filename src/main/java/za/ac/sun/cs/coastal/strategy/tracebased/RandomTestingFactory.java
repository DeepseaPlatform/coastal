package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.IntConstant;

public class RandomTestingFactory extends TraceBasedFactory {

	protected final ImmutableConfiguration options;

	public RandomTestingFactory(COASTAL coastal, ImmutableConfiguration options) {
		this.options = options;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new RandomTestingManager(coastal, options);
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

		protected final int maxNumberOfModels;

		public RandomTestingManager(COASTAL coastal, ImmutableConfiguration options) {
			super(coastal);
			maxNumberOfModels = ConfigHelper.zero(options.getInt("max-models", 1000), Integer.MAX_VALUE - 1);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		protected int getMaxNumberOfModels() {
			return maxNumberOfModels;
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

		private final int maxNumberOfModels;

		private int numberOfModels = 0;

		public RandomTestingStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			pathTree = coastal.getPathTree();
			maxNumberOfModels = ((RandomTestingManager) manager).getMaxNumberOfModels();
		}

		@Override
		protected List<Model> refine0(Trace trace) {
			if (numberOfModels++ >= maxNumberOfModels) {
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
