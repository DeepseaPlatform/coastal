package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.strategy.MTRandom;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.surfer.TraceState;
import za.ac.sun.cs.coastal.symbolic.Model;

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

		protected final long randomSeed;

		public RandomTestingManager(COASTAL coastal, ImmutableConfiguration options) {
			super(coastal);
			maxNumberOfModels = ConfigHelper.zero(options.getInt("max-models", 1000), Integer.MAX_VALUE - 1);
			randomSeed = ConfigHelper.zero(options.getInt("seed", 0), System.currentTimeMillis());
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		protected int getMaxNumberOfModels() {
			return maxNumberOfModels;
		}

		protected long getRandomSeed() {
			return randomSeed + taskCount;
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
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

		private final PathTree pathTree;

		private final Map<String, Object> concreteValues = new HashMap<>();

		private final int maxNumberOfModels;

		private int numberOfModels = 0;

		private final MTRandom rng;

		public RandomTestingStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			pathTree = coastal.getPathTree();
			maxNumberOfModels = ((RandomTestingManager) manager).getMaxNumberOfModels();
			rng = new MTRandom(((RandomTestingManager) manager).getRandomSeed());
		}

		@Override
		protected List<Model> refine0(Trace trace) {
			if (numberOfModels >= maxNumberOfModels) {
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
			return refine1();
		}

		@Override
		protected List<Model> refine1() {
			for (Map.Entry<String, Class<?>> parameter : coastal.getParameters().entrySet()) {
				String name = parameter.getKey();
				Class<?> type = parameter.getValue();
				if (type == boolean.class) {
					int min = (Integer) coastal.getMinBound(name, type);
					int max = (Integer) coastal.getMaxBound(name, type);
					int value = rng.nextInt(min, max + 1);
					concreteValues.put(name, Long.valueOf(value));
				} else if (type == byte.class) {
					int min = (Byte) coastal.getMinBound(name, type);
					int max = (Byte) coastal.getMaxBound(name, type);
					int value = rng.nextInt(min, max + 1);
					concreteValues.put(name, Long.valueOf(value));
				} else if (type == short.class) {
					int min = (Short) coastal.getMinBound(name, type);
					int max = (Short) coastal.getMaxBound(name, type);
					int value = rng.nextInt(min, max + 1);
					concreteValues.put(name, Long.valueOf(value));
				} else if (type == char.class) {
					int min = (Character) coastal.getMinBound(name, type);
					int max = (Character) coastal.getMaxBound(name, type);
					int value = rng.nextInt(min, max + 1);
					concreteValues.put(name, Long.valueOf(value));
				} else if (type == int.class) {
					int min = (Integer) coastal.getMinBound(name, type);
					int max = (Integer) coastal.getMaxBound(name, type);
					int value = randomInt(min, max);
					concreteValues.put(name, Long.valueOf(value));
				} else if (type == long.class) {
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					concreteValues.put(name, randomLong(min, max));
				} else if (type == float.class) {
					double min = (Float) coastal.getMinBound(name, type);
					double max = (Float) coastal.getMaxBound(name, type);
					double value = rng.nextDouble(min, max);
					concreteValues.put(name, Double.valueOf(value));
				} else if (type == double.class) {
					double min = (Double) coastal.getMinBound(name, type);
					double max = (Double) coastal.getMaxBound(name, type);
					double value = rng.nextDouble(min, max);
					concreteValues.put(name, value);
				} else if (type == String.class) {
					int size = coastal.getParameterSize(name);
					int min = (Character) coastal.getMinBound(name, char.class);
					int max = (Character) coastal.getMaxBound(name, char.class);
					for (int i = 0; i < size; i++) {
						int value = rng.nextInt(min, max + 1);
						concreteValues.put(name + TraceState.CHAR_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == boolean[].class) {
					int size = coastal.getParameterSize(name);
					int min = (Integer) coastal.getMinBound(name, type);
					int max = (Integer) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						int value = rng.nextInt(min, max + 1);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == byte[].class) {
					int size = coastal.getParameterSize(name);
					int min = (Byte) coastal.getMinBound(name, type);
					int max = (Byte) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						int value = rng.nextInt(min, max + 1);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == short[].class) {
					int size = coastal.getParameterSize(name);
					int min = (Short) coastal.getMinBound(name, type);
					int max = (Short) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						int value = rng.nextInt(min, max + 1);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == short[].class) {
					int size = coastal.getParameterSize(name);
					int min = (Short) coastal.getMinBound(name, type);
					int max = (Short) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						int value = rng.nextInt(min, max + 1);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == char[].class) {
					int size = coastal.getParameterSize(name);
					int min = (Character) coastal.getMinBound(name, type);
					int max = (Character) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						int value = randomInt(min, max);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Long.valueOf(value));
					}
				} else if (type == long[].class) {
					int size = coastal.getParameterSize(name);
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, randomLong(min, max));
					}
				} else if (type == float[].class) {
					int size = coastal.getParameterSize(name);
					double min = (Float) coastal.getMinBound(name, type);
					double max = (Float) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						double value = rng.nextDouble(min, max);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, Double.valueOf(value));
					}
				} else if (type == double[].class) {
					int size = coastal.getParameterSize(name);
					double min = (Double) coastal.getMinBound(name, type);
					double max = (Double) coastal.getMaxBound(name, type);
					for (int i = 0; i < size; i++) {
						double value = rng.nextDouble(min, max);
						concreteValues.put(name + TraceState.INDEX_SEPARATOR + i, value);
					}
				}
			}
			String modelString = concreteValues.toString();
			log.trace("... new model: {}", modelString);
			numberOfModels++;
			return Collections.singletonList(new Model(0, concreteValues));
		}

		private int randomInt(int min, int max) {
			if ((min == Integer.MIN_VALUE) && (max == Integer.MAX_VALUE)) {
				return rng.nextInt();
			} else if (max == Integer.MAX_VALUE) {
				return 1 + rng.nextInt(min - 1, max);
			} else {
				return rng.nextInt(min, max + 1);
			}
		}

		private long randomLong(long min, long max) {
			if ((min == Long.MIN_VALUE) && (max == Long.MAX_VALUE)) {
				return 1 + rng.nextLong();
			} else if (max == Long.MAX_VALUE) {
				return 1 + rng.nextLong(min - 1, max);
			} else {
				return rng.nextLong(min, max + 1);
			}
		}

	}

}
