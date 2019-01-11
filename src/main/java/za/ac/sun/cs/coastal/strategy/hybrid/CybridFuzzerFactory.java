package za.ac.sun.cs.coastal.strategy.hybrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

public class CybridFuzzerFactory implements StrategyFactory {

	protected final ImmutableConfiguration options;

	public CybridFuzzerFactory(COASTAL coastal, ImmutableConfiguration options) {
		this.options = options;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new CybridFuzzerManager(coastal, options);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((CybridFuzzerManager) manager).incrementTaskCount();
		return new CybridFuzzerStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// HYBRID FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class CybridFuzzerManager implements StrategyManager {

		private static final int DEFAULT_QUEUE_LIMIT = 10000000;

		private static final int NEW_EDGE_SCORE = 100;

		private static final int NEW_BUCKET_SCORE = 50;

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

		protected final int queueLimit;

		protected final long randomSeed;

		protected final double attenuation;

		protected final int mutationCount;

		protected final int eliminationCount;

		protected final double eliminationRatio;

		protected final int keepTop;

		protected final boolean drawTree;

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

		public CybridFuzzerManager(COASTAL coastal, ImmutableConfiguration options) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
			queueLimit = ConfigHelper.zero(options.getInt("queue-limit", 0), DEFAULT_QUEUE_LIMIT);
			randomSeed = ConfigHelper.zero(options.getInt("random-seed", 0), System.currentTimeMillis());
			attenuation = ConfigHelper.minmax(options.getDouble("attenuation", 0.5), 0.0, 1.0);
			mutationCount = ConfigHelper.zero(options.getInt("mutation-count", 0), 100);
			eliminationCount = options.getInt("elimination-count", 0);
			eliminationRatio = ConfigHelper.minmax(options.getDouble("elimination-ratio", 0), 0.0, 1.0);
			keepTop = ConfigHelper.minmax(options.getInt("keep-top", 1), 1, Integer.MAX_VALUE);
			drawTree = options.getBoolean("draw-final-tree", false);
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

		protected int getQueueLimit() {
			return queueLimit;
		}

		protected long getRandomSeed() {
			return randomSeed + taskCount;
		}

		protected double getAttenuation() {
			return attenuation;
		}

		protected int getMutationCount() {
			return mutationCount;
		}

		protected int getEliminationCount() {
			return eliminationCount;
		}

		protected double getEliminationRatio() {
			return eliminationRatio;
		}

		protected int getKeepTop() {
			return keepTop;
		}

		/**
		 * Increment the number of refinements.
		 */
		public void incrementRefinements() {
			refineCount.incrementAndGet();
		}

		/**
		 * Add a reported dive time to the accumulator that tracks how long the dives
		 * took.
		 * 
		 * @param time the time for this dive
		 */
		public void recordTime(long time) {
			strategyTime.addAndGet(time);
		}

		/**
		 * Add a reported strategy wait time. This is used to determine if it makes
		 * sense to create additional threads (or destroy them).
		 * 
		 * @param time the wait time for this strategy
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
			if (drawTree) {
				int i = 0;
				for (String ll : pathTree.stringRepr()) {
					broker.publish("report", new Tuple(String.format("%s.tree%03d", name, i++), ll));
				}
			}
		}

		private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#refinements", "waiting time",
				"total time" };

		@Override
		public String getName() {
			return "CybridFuzzer";
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

	public static class CybridFuzzerStrategy extends Strategy {

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

		protected final CybridFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int modelsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected final int queueLimit;

		private final MTRandom rng;

		private final double attenuation;

		protected final int mutationCount;

		protected final int eliminationCount;

		protected final double eliminationRatio;

		protected final int keepTop;

		public CybridFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (CybridFuzzerManager) manager;
			broker = coastal.getBroker();
			queueLimit = this.manager.getQueueLimit();
			rng = new MTRandom(this.manager.getRandomSeed());
			attenuation = this.manager.getAttenuation();
			mutationCount = this.manager.getMutationCount();
			eliminationCount = this.manager.getEliminationCount();
			eliminationRatio = this.manager.getEliminationRatio();
			keepTop = this.manager.getKeepTop();
		}

		private Set<Integer> setValues = new HashSet<>();
		private Set<Integer> incValues = new HashSet<>();

		@Override
		public Void call() throws Exception {
			log.trace("^^^ strategy task starting");
			try {
				TraceCollection keepers = new TraceCollection(keepTop);
				TraceCollection allTime = new TraceCollection(keepTop);
				long t0 = System.currentTimeMillis();
				Trace trace0 = coastal.getNextTrace();
				long t1 = System.currentTimeMillis();
				manager.recordWaitTime(t1 - t0);
				manager.incrementRefinements();
				log.trace("+++ starting refinement");
				int score0 = calculateScore(trace0);
				keepers.add(score0, trace0);
				allTime.add(score0, trace0);
				setValues.addAll(trace0.getSetValues());
				incValues.addAll(trace0.getIncValues());
				refine(trace0, new GybridPayload(score0));
				while (true) {
					while (coastal.getSurferModelQueueLength() > queueLimit) {
						Thread.sleep(200);
					}
					keepers.clear();
					int eliminate = Math.max(eliminationCount,
							(int) (eliminationRatio * coastal.getTraceQueueLength()));
					for (int i = 0; i < eliminate; i++) {
						t0 = System.currentTimeMillis();
						Trace tracex = coastal.getNextTrace(200);
						t1 = System.currentTimeMillis();
						manager.recordWaitTime(t1 - t0);
						if (tracex == null) {
							log.trace("+++ out of traces");
							break;
						}
						int scorex = calculateScore(tracex);
						keepers.add(scorex, tracex);
						allTime.add(scorex, tracex);
					}
					manager.incrementRefinements();
					log.trace("+++ starting refinement");
					TraceCollection candidates = (keepers.size() > 0) ? keepers : allTime;
					setValues.clear();
					incValues.clear();
					for (Trace trace : candidates.traces()) {
						setValues.addAll(trace.getSetValues());
						incValues.addAll(trace.getIncValues());
					}
					for (int j = 0, n = candidates.size(); j < n; j++) {
						refine(candidates.getTrace(j), new GybridPayload(candidates.getScore(j)));
					}
					PathTreeNode root = manager.getPathTree().getRoot();
					if ((root != null) && root.isFullyExplored()) {
						coastal.stopWork("PATH TREE FULLY EXPLORED");
						break;
					}
				}
			} catch (InterruptedException e) {
				log.trace("^^^ strategy task canceled");
				throw e;
			}
			log.trace("^^^ strategy task finished");
			return null;
		}

		protected void refine(Trace trace, GybridPayload payload) {
			long t0 = System.currentTimeMillis();
			modelsAdded = -1;
			manager.insertPath(trace, false);
			parameters = coastal.getParameters();
			Map<String, Object> model = trace.getModel();
			mutate(payload, model);
			log.trace("+++ added {} surfer models", modelsAdded);
			coastal.updateWork(modelsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void mutate(GybridPayload payload, Map<String, Object> model) {
			for (Map.Entry<String, Class<?>> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Class<?> type = entry.getValue();
				if (type == boolean.class) {
					update(payload, model, name, (Integer) coastal.getMinBound(name, type),
							(Integer) coastal.getMaxBound(name, type));
				} else if (type == byte.class) {
					update(payload, model, name, (Byte) coastal.getMinBound(name, type),
							(Byte) coastal.getMaxBound(name, type));
				} else if (type == short.class) {
					update(payload, model, name, (Short) coastal.getMinBound(name, type),
							(Short) coastal.getMaxBound(name, type));
				} else if (type == char.class) {
					update(payload, model, name, (Character) coastal.getMinBound(name, type),
							(Character) coastal.getMaxBound(name, type));
				} else if (type == int.class) {
					update(payload, model, name, (Integer) coastal.getMinBound(name, type),
							(Integer) coastal.getMaxBound(name, type));
				} else if (type == long.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					newModel.put(name, randomLong(min, max));
					submitModel(payload, newModel);
				} else if (type == float.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					double min = (Float) coastal.getMinBound(name, type);
					double max = (Float) coastal.getMaxBound(name, type);
					newModel.put(name, Double.valueOf(rng.nextDouble(min, max)));
					submitModel(payload, newModel);
				} else if (type == double.class) {
					Map<String, Object> newModel = new HashMap<>(model);
					double min = (Double) coastal.getMinBound(name, type);
					double max = (Double) coastal.getMaxBound(name, type);
					newModel.put(name, Double.valueOf(rng.nextDouble(min, max)));
					submitModel(payload, newModel);
				} else if (type == String.class) {
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					int length = coastal.getParameterSize(name);
					for (int i = 0; i < length; i++) {
						Map<String, Object> newModel = new HashMap<>(model);
						newModel.put(name + TraceState.CHAR_SEPARATOR + i, randomLong(min, max));
						submitModel(payload, newModel);
					}
				} else if (type == char[].class) {
					int min = (Character) coastal.getMinBound(name, type);
					int max = (Character) coastal.getMaxBound(name, type);
					int length = coastal.getParameterSize(name);
					for (int i = 0; i < length; i++) {
						update(payload, model, name + TraceState.INDEX_SEPARATOR + i, min, max);
					}
				} else {
					System.exit(1);
				}
			}
		}

		private void update(GybridPayload payload, Map<String, Object> model, String name, int min, int max) {
			for (int i = 0, n = mutationCount / 2; i < n; i++) {
				Map<String, Object> newModel = new HashMap<>(model);
				newModel.put(name, Long.valueOf(randomInt(min, max)));
				submitModel(payload, newModel);
			}
			if (setValues != null) {
				int cur = ((Long) model.get(name)).intValue();
				for (int set : setValues) {
					if ((set >= min) && (set <= max) && (set != cur)) {
						Map<String, Object> newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						submitModel(payload, newModel);
					}
				}
			}
			if (incValues != null) {
				for (int inc : incValues) {
					int set = ((Long) model.get(name)).intValue() + inc;
					if ((set >= min) && (set <= max)) {
						Map<String, Object> newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						submitModel(payload, newModel);
					}
					set = ((Long) model.get(name)).intValue() - inc;
					if ((set >= min) && (set <= max)) {
						Map<String, Object> newModel = new HashMap<>(model);
						newModel.put(name, Long.valueOf(set));
						submitModel(payload, newModel);
					}
				}
			}
			for (int i = 0, n = (mutationCount + 1) / 2; i < n; i++) {
				Map<String, Object> newModel = new HashMap<>(model);
				newModel.put(name, Long.valueOf(randomInt(min, max)));
				submitModel(payload, newModel);
			}
		}

		private void submitModel(GybridPayload payload, Map<String, Object> model) {
			Model mdl = new Model(payload.score, model);
			mdl.setPayload(payload);
			if (coastal.addSurferModel(mdl)) {
				modelsAdded++;
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
		 * "blocks", which are line numbers that represent basic blocks. Consecutive
		 * blocks form tuples, the tuples are counted, and the counts are converted
		 * according to the "bucket scale":
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
		 * This information is passed to the manager, which returns a score based on the
		 * counts. Finally, the score of the trace's parent trace is added but scaled by
		 * a factor.
		 * 
		 * @param trace the trace to calculate a score for
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

	public static class TraceCollection {

		protected final int capacity;

		protected final int[] scores;

		protected final Trace[] traces;

		protected int size;

		public TraceCollection(int capacity) {
			assert capacity > 0;
			this.capacity = capacity;
			scores = new int[capacity];
			traces = new Trace[capacity];
			size = 0;
		}

		public void clear() {
			size = 0;
		}

		public void add(int score, Trace trace) {
			if (size == 0) {
				scores[0] = score;
				traces[0] = trace;
				size = 1;
			} else {
				int position = 0;
				while ((position < size) && (score <= scores[position])) {
					position++;
				}
//				if ((position < capacity) && !trace.toString().equals(traces[position].toString())) {
				if (position < capacity) {
					if (size < capacity) {
						size++;
					}
					for (int i = size - 1; i > position; i--) {
						scores[i] = scores[i - 1];
						traces[i] = traces[i - 1];
					}
					scores[position] = score;
					traces[position] = trace;
				}
			}
		}

		public int size() {
			return size;
		}

		public Iterable<Trace> traces() {
			return new Iterable<Trace>() {
				@Override
				public Iterator<Trace> iterator() {
					return new Iterator<Trace>() {
						private int index = 0;

						@Override
						public Trace next() {
							return traces[index++];
						}

						@Override
						public boolean hasNext() {
							return index < size;
						}
					};
				}
			};
		}

		public int getScore(int index) {
			return scores[index];
		}

		public Trace getTrace(int index) {
			return traces[index];
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(size).append('/').append(capacity);
			for (int i = 0; i < size; i++) {
				b.append(' ').append(scores[i]).append(':').append(traces[i].toString());
			}
			return b.toString();
		}

	}

}
