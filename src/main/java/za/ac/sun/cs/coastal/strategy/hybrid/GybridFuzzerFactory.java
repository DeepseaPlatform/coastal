package za.ac.sun.cs.coastal.strategy.hybrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.MTRandom;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.surfer.TraceState;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.Payload;

public class GybridFuzzerFactory implements StrategyFactory {

	protected final ImmutableConfiguration options;

	public GybridFuzzerFactory(COASTAL coastal, ImmutableConfiguration options) {
		this.options = options;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new GybridFuzzerManager(coastal, options);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((GybridFuzzerManager) manager).incrementTaskCount();
		return new GybridFuzzerStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// HYBRID FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class GybridFuzzerManager implements StrategyManager {

		private static final int DEFAULT_QUEUE_LIMIT = 100000;

		private static final int NEW_EDGE_SCORE = 100;

		private static final int NEW_BUCKET_SCORE = 50;

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

		protected final int queueLimit;

		protected final long randomSeed;

		protected final double attenuation;

		protected final int firstRepeat;

		protected final int pairRepeat;

		protected int taskCount = 0;

		/**
		 * Counter of number of refinements.
		 */
		protected final AtomicLong refineCount = new AtomicLong(0);

		/**
		 * Accumulator of all the refinement times.
		 */
		protected final AtomicLong strategyTime = new AtomicLong(0);

		/**
		 * Accumulator of all the strategy waiting times.
		 */
		private final AtomicLong strategyWaitTime = new AtomicLong(0);

		/**
		 * Counter for the strategy waiting times.
		 */
		private final AtomicLong strategyWaitCount = new AtomicLong(0);

		protected final Map<String, Integer> edgesSeen = new HashMap<>();

		public GybridFuzzerManager(COASTAL coastal, ImmutableConfiguration options) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
			queueLimit = ConfigHelper.zero(options.getInt("queue-limit", 0), DEFAULT_QUEUE_LIMIT);
			randomSeed = ConfigHelper.zero(options.getInt("seed", 0), System.currentTimeMillis());
			attenuation = ConfigHelper.minmax(options.getDouble("attenuation", 0.5), 0.0, 1.0);
			firstRepeat = ConfigHelper.zero(options.getInt("first-repeat", 0), 100);
			pairRepeat = ConfigHelper.zero(options.getInt("pair-repeat", 0), 50);
		}

		public PathTree getPathTree() {
			return pathTree;
		}

		public PathTreeNode insertPath0(Trace trace, boolean infeasible) {
			return pathTree.insertPath(trace, infeasible);
		}

		public boolean insertPath(Trace trace, boolean infeasible) {
			return (pathTree.insertPath(trace, infeasible) == null);
		}

		public int getQueueLimit() {
			return queueLimit;
		}

		protected long getRandomSeed() {
			return randomSeed + taskCount;
		}

		protected double getAttenuation() {
			return attenuation;
		}

		protected int getFirstRepeat() {
			return firstRepeat;
		}

		protected int getPairRepeat() {
			return pairRepeat;
		}

		/**
		 * Increment the number of refinements.
		 */
		public void incrementRefinements() {
			refineCount.incrementAndGet();
		}

		/**
		 * Add a reported dive time to the accumulator that tracks how long the
		 * dives took.
		 * 
		 * @param time
		 *            the time for this dive
		 */
		public void recordTime(long time) {
			strategyTime.addAndGet(time);
		}

		/**
		 * Add a reported strategy wait time. This is used to determine if it
		 * makes sense to create additional threads (or destroy them).
		 * 
		 * @param time
		 *            the wait time for this strategy
		 */
		public void recordWaitTime(long time) {
			strategyWaitTime.addAndGet(time);
			strategyWaitCount.incrementAndGet();
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		protected int getTaskCount() {
			return taskCount;
		}

		protected synchronized int scoreEdges(Map<String, Integer> edges) {
			int score = 0;
			for (Map.Entry<String, Integer> entry : edges.entrySet()) {
				String edge = entry.getKey();
				int newCount = entry.getValue();
				Integer count = edgesSeen.get(entry.getKey());
				if (count == null) {
					edgesSeen.put(edge, newCount);
					score += NEW_EDGE_SCORE;
				} else if (newCount > count) {
					edgesSeen.put(edge, newCount);
					score += NEW_BUCKET_SCORE;
				}
			}
			return score;
		}

		public void report(Object object) {
			String name = getName();
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			broker.publish("report", new Tuple(name + ".tasks", getTaskCount()));
			broker.publish("report", new Tuple(name + ".refinements", refineCount.get()));
			broker.publish("report", new Tuple(name + ".wait-time", swt));
			broker.publish("report", new Tuple(name + ".total-time", strategyTime.get()));
		}

		private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#refinements", "waiting time",
				"total time" };

		@Override
		public String getName() {
			return "GybridFuzzer";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[4];
			int index = 0;
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			long c = refineCount.get();
			long t = strategyTime.get();
			propertyValues[index++] = getTaskCount();
			propertyValues[index++] = String.format("%d (%.1f/sec)", c, c / (0.001 * t));
			propertyValues[index++] = swt;
			propertyValues[index++] = t;
			return propertyValues;
		}

	}

	// ======================================================================
	//
	// HYBRID FUZZER STRATEGY
	//
	// ======================================================================

	public static class GybridFuzzerStrategy extends Strategy {

		public static final int[] COUNT_TO_BUCKET = new int[128];

		static {
			COUNT_TO_BUCKET[0] = 0;
			COUNT_TO_BUCKET[1] = 1;
			COUNT_TO_BUCKET[2] = 2;
			COUNT_TO_BUCKET[3] = 3;
			for (int k = 4; k < 8; k++) {
				COUNT_TO_BUCKET[k] = 4;
			}
			for (int k = 8; k < 16; k++) {
				COUNT_TO_BUCKET[k] = 5;
			}
			for (int k = 16; k < 32; k++) {
				COUNT_TO_BUCKET[k] = 6;
			}
			for (int k = 32; k < 128; k++) {
				COUNT_TO_BUCKET[k] = 7;
			}
		}

		protected final GybridFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int modelsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected final int queueLimit;

		private final MTRandom rng;

		private final double attenuation;

		protected final int firstRepeat;

		protected final int pairRepeat;

		private int highScore = 0;

		public GybridFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (GybridFuzzerManager) manager;
			broker = coastal.getBroker();
			queueLimit = this.manager.getQueueLimit();
			rng = new MTRandom(this.manager.getRandomSeed());
			attenuation = this.manager.getAttenuation();
			firstRepeat = this.manager.getFirstRepeat();
			pairRepeat = this.manager.getPairRepeat();
		}

		private Set<Integer> setValues = new HashSet<>();
		private Set<Integer> incValues = new HashSet<>();

		@Override
		public Void call() throws Exception {
			log.trace("^^^ strategy task starting");
			try {
				long t0 = System.currentTimeMillis();
				Trace trace = coastal.getNextTrace();
				int score = calculateScore(trace);
				long t1 = System.currentTimeMillis();
				manager.recordWaitTime(t1 - t0);
				manager.incrementRefinements();
				log.trace("+++ starting refinement");
				setValues = trace.getSetValues();
				incValues = trace.getIncValues();
//				Set<Integer> sv = trace.getSetValues();
//				if (sv != null) {
//					setValues.addAll(sv);
//				}
//				Set<Integer> iv = trace.getIncValues();
//				if (iv != null) {
//					incValues.addAll(iv);
//				}
				refineFirst(trace, score);
				while (true) {
					t0 = System.currentTimeMillis();
					Trace trace1 = null, trace2 = null;
					int score1 = 0, score2 = 0;
					do {
						trace1 = coastal.getNextTrace();
						score1 = calculateScore(trace1);
					} while (score1 <= 0);
					setValues = trace1.getSetValues();
					incValues = trace1.getIncValues();
					int tries = 5;
					do {
						trace2 = coastal.getNextTrace();
						score2 = calculateScore(trace2);
					} while ((tries-- > 0) && (score2 <= 0));
					//} while ((tries-- > 0) && ((score2 <= 0) || trace1.toString().equals(trace2.toString())));
					setValues.addAll(trace2.getSetValues());
					incValues.addAll(trace2.getIncValues());
					t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					manager.incrementRefinements();
					log.trace("+++ starting refinement");
					refinePair(trace1, score1, trace2, score2);
				}
			} catch (InterruptedException e) {
				log.trace("^^^ strategy task canceled");
				throw e;
			}
		}

		protected void refineFirst(Trace trace, int score) {
			long t0 = System.currentTimeMillis();
			modelsAdded = -1;
			manager.insertPath(trace, false);
			parameters = coastal.getParameters();
			Map<String, Object> model = trace.getModel();
			if (score > highScore) {
				highScore = score;
				log.info("NEW HIGH SCORE: {} {}", score, model);
			}
			for (int i = 0; i < firstRepeat; i++) {
				mutatem(score, model);
			}
			log.trace("+++ added {} surfer models", modelsAdded);
			coastal.updateWork(modelsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void refinePair(Trace trace1, int score1, Trace trace2, int score2) {
			long t0 = System.currentTimeMillis();
			modelsAdded = -1;
			manager.insertPath(trace1, false);
			manager.insertPath(trace2, false);
			Map<String, Object> model1 = trace1.getModel();
			Map<String, Object> model2 = trace2.getModel();
			if (score1 > highScore) {
				highScore = score1;
				log.info("NEW HIGH SCORE: {} {}", score1, model1);
			}
			if (score2 > highScore) {
				highScore = score2;
				log.info("NEW HIGH SCORE: {} {}", score2, model2);
			}
			int score = Math.max(score1, score2);
			for (int i = 0; i < pairRepeat; i++) {
				Map<String, Object> newModel1 = new HashMap<>(model1);
				Map<String, Object> newModel2 = new HashMap<>(model2);
				if (!trace1.toString().equals(trace2.toString())) {
					for (Map.Entry<String, Object> entry : model1.entrySet()) {
						String name = entry.getKey();
						Object value1 = model1.get(name);
						Object value2 = model2.get(name);
						assert (value2 != null);
						if (rng.nextInt(100) < 5) {
							Object value = null;
							if (value1 instanceof Long) {
								value = (((Long) value1) + ((Long) value2)) / 2;
							} else {
								value = (((Double) value1) + ((Double) value2)) / 2;
							}
							newModel1.put(name, value);
							newModel2.put(name, value);
						} else if (rng.nextBoolean()) {
							newModel1.put(name, value2);
							newModel2.put(name, value1);
						}
					}
				}
				mutatem(score, newModel1);
				mutatem(score, newModel2);
			}
			log.trace("+++ added {} surfer models", modelsAdded);
			coastal.updateWork(modelsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void mutatem(int score, Map<String, Object> model) {
			for (Map.Entry<String, Class<?>> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Class<?> type = entry.getValue();
				if (type == boolean.class) {
					update(score, model, name, (Integer) coastal.getMinBound(name, type),
							(Integer) coastal.getMaxBound(name, type));
				} else if (type == byte.class) {
					update(score, model, name, (Byte) coastal.getMinBound(name, type),
							(Byte) coastal.getMaxBound(name, type));
				} else if (type == short.class) {
					update(score, model, name, (Short) coastal.getMinBound(name, type),
							(Short) coastal.getMaxBound(name, type));
				} else if (type == char.class) {
					update(score, model, name, (Character) coastal.getMinBound(name, type),
							(Character) coastal.getMaxBound(name, type));
				} else if (type == int.class) {
					update(score, model, name, (Integer) coastal.getMinBound(name, type),
							(Integer) coastal.getMaxBound(name, type));
				} else if (type == long.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					newModel.put(name, randomLong(min, max));
					Model mdl = new Model(score, newModel);
					mdl.setPayload(new GybridPayload(score));
					if (coastal.addSurferModel(mdl)) {
						modelsAdded++;
					}
				} else if (type == float.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					double min = (Float) coastal.getMinBound(name, type);
					double max = (Float) coastal.getMaxBound(name, type);
					newModel.put(name, Double.valueOf(rng.nextDouble(min, max)));
					Model mdl = new Model(score, newModel);
					mdl.setPayload(new GybridPayload(score));
					if (coastal.addSurferModel(mdl)) {
						modelsAdded++;
					}
				} else if (type == double.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					double min = (Double) coastal.getMinBound(name, type);
					double max = (Double) coastal.getMaxBound(name, type);
					newModel.put(name, Double.valueOf(rng.nextDouble(min, max)));
					Model mdl = new Model(score, newModel);
					mdl.setPayload(new GybridPayload(score));
					if (coastal.addSurferModel(mdl)) {
						modelsAdded++;
					}
				} else if (type == String.class) {
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					int length = coastal.getParameterSize(name);
					for (int i = 0; i < length; i++) {
						Map<String, Object> newModel = new HashMap<>(model);
						newModel.put(name + TraceState.CHAR_SEPARATOR + i, randomLong(min, max));
						Model mdl = new Model(score, newModel);
						mdl.setPayload(new GybridPayload(score));
						if (coastal.addSurferModel(mdl)) {
							modelsAdded++;
						}
					}
				} else if (type == char[].class) {
					int min = (Character) coastal.getMinBound(name, type);
					int max = (Character) coastal.getMaxBound(name, type);
					int length = coastal.getParameterSize(name);
					for (int i = 0; i < length; i++) {
						update(score, model, name + TraceState.INDEX_SEPARATOR + i, min, max);
					}
				} else {
					System.exit(1);
				}
			}
		}

		private void update(int score, Map<String, Object> model, String name, int min, int max) {
			Map<String, Object> newModel = new HashMap<>(model);
			newModel.put(name, Long.valueOf(randomInt(min, max)));
			Model mdl = new Model(score, newModel);
			mdl.setPayload(new GybridPayload(score));
			if (coastal.addSurferModel(mdl)) {
				modelsAdded++;
			}
			if (setValues != null) {
				for (int set : setValues) {
					int cur = ((Long) model.get(name)).intValue();
					if ((set >= min) && (set <= max) && (set != cur)) {
						newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						mdl = new Model(score, newModel);
						mdl.setPayload(new GybridPayload(score));
						if (coastal.addSurferModel(mdl)) {
							modelsAdded++;
						}
					}
				}
			}
			if (incValues != null) {
				for (int inc : incValues) {
					int set = ((Long) model.get(name)).intValue() + inc;
					if ((set >= min) && (set <= max)) {
						newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						mdl = new Model(score, newModel);
						mdl.setPayload(new GybridPayload(score));
						if (coastal.addSurferModel(mdl)) {
							modelsAdded++;
						}
					}
					set = ((Long) model.get(name)).intValue() - inc;
					if ((set >= min) && (set <= max)) {
						newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						mdl = new Model(score, newModel);
						mdl.setPayload(new GybridPayload(score));
						if (coastal.addSurferModel(mdl)) {
							modelsAdded++;
						}
					}
				}
			}
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

		/**
		 * Calculate the score for a trace. First, the trace is scanned for its
		 * "blocks", which are line numbers that represent basic blocks.
		 * Consecutive blocks form tuples, the tuples are counted, and the
		 * counts are converted according to the "bucket scale":
		 * 
		 * <ul>
		 * <li>1 occurrence = bucket 1</li>
		 * <li>2 occurrences = bucket 2</li>
		 * <li>3 occurrences = bucket 3</li>
		 * <li>4&ndash;7 occurrences = bucket 4</li>
		 * <li>8&ndash;15 occurrences = bucket 5</li>
		 * <li>16&ndash;31 occurrences = bucket 6</li>
		 * <li>32&ndash;127 occurrences = bucket 7</li>
		 * <li>&ge;128 occurrences = bucket 8</li>
		 * </ul>
		 *
		 * This information is passed to the manager, which returns a score
		 * based on the counts. Finally, the score of the trace's parent trace
		 * is added but scaled by a factor.
		 * 
		 * @param trace
		 *            the trace to calculate a score for
		 * @return the score
		 */
		private int calculateScore(Trace trace) {
			GybridPayload pl = (GybridPayload) trace.getPayload();
			int oldScore = (pl == null) ? 0 : pl.score;
			Map<String, Integer> edges = new HashMap<>();
			while (trace != null) {
				String edge = trace.getBlock();
				Integer count = edges.get(edge);
				if (count == null) {
					edges.put(edge, 1);
				} else {
					edges.put(edge, count + 1);
				}
				trace = trace.getParent();
			}
			for (String edge : edges.keySet()) {
				int count = edges.get(edge);
				if (count > 128) {
					edges.put(edge, 8);
				} else {
					edges.put(edge, COUNT_TO_BUCKET[count]);
				}
			}
			return manager.scoreEdges(edges) + edges.size() + (int) (attenuation * oldScore);
		}

	}

	public static class GybridPayload implements Payload {
		public int score;

		public GybridPayload(int score) {
			this.score = score;
		}
	}

}
