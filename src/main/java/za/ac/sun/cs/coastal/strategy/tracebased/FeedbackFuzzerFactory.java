package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.MTRandom;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.surfer.TraceState;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.Path;

public class FeedbackFuzzerFactory implements StrategyFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "@F@";

	protected final Configuration configuration;

	public FeedbackFuzzerFactory(COASTAL coastal, Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new FeedbackFuzzerManager(coastal, configuration);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((FeedbackFuzzerManager) manager).incrementTaskCount();
		return new Strategy[] { new FeedbackFuzzerStrategy(coastal, (StrategyManager) manager) };
	}

	// ======================================================================
	//
	// FEEDBACK FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class FeedbackFuzzerManager implements StrategyManager {

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

		public FeedbackFuzzerManager(COASTAL coastal, Configuration configuration) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
			queueLimit = configuration.getInt("queue-limit", 0, DEFAULT_QUEUE_LIMIT);
			randomSeed = configuration.getLong("random-seed", 0, System.currentTimeMillis());
			attenuation = configuration.getDouble("attenuation", 0.5, 0.0, 1.0);
			mutationCount = configuration.getInt("mutation-count", 0, 100);
			eliminationCount = configuration.getInt("elimination-count", 0);
			eliminationRatio = configuration.getDouble("elimination-ratio", 0, 0.0, 1.0);
			keepTop = configuration.getInt("keep-top", 1, 1, Integer.MAX_VALUE);
			drawTree = configuration.getBoolean("draw-final-tree", false);
		}

		public PathTree getPathTree() {
			return pathTree;
		}

		public PathTreeNode insertPath0(Execution execution, boolean infeasible) {
			return pathTree.insertPath(execution, infeasible);
		}

		public boolean insertPath(Execution execution, boolean infeasible) {
			return (pathTree.insertPath(execution, infeasible) == null);
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
			return "FeedbackFuzzer";
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
	// FEEDBACK FUZZER STRATEGY
	//
	// ======================================================================

	public static class FeedbackFuzzerStrategy extends Strategy {

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

		protected final FeedbackFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int inputsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected final int queueLimit;

		private final MTRandom rng;

		private final double attenuation;

		protected final int mutationCount;

		protected final int eliminationCount;

		protected final double eliminationRatio;

		protected final int keepTop;

		public FeedbackFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (FeedbackFuzzerManager) manager;
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

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			log.trace("{} strategy task starting", LOG_PREFIX);
			try {
				ExecutionCollection keepers = new ExecutionCollection(keepTop);
				ExecutionCollection allTime = new ExecutionCollection(keepTop);
				long t0 = System.currentTimeMillis();
				Execution execution0 = coastal.getNextTrace();
				long t1 = System.currentTimeMillis();
				manager.recordWaitTime(t1 - t0);
				manager.incrementRefinements();
				log.trace("{} starting refinement", LOG_PREFIX);
				int score0 = calculateScore(execution0);
				keepers.add(score0, execution0);
				allTime.add(score0, execution0);
				setValues.addAll((Set<Integer>) execution0.getPayload("setValues"));
				incValues.addAll((Set<Integer>) execution0.getPayload("incValues"));
				refine(execution0, score0);
				while (true) {
					while (coastal.getSurferModelQueueLength() > queueLimit) {
						Thread.sleep(200);
					}
					keepers.clear();
					int eliminate = Math.max(eliminationCount,
							(int) (eliminationRatio * coastal.getTraceQueueLength()));
					for (int i = 0; i < eliminate; i++) {
						t0 = System.currentTimeMillis();
						Execution executionx = coastal.getNextTrace(200);
						t1 = System.currentTimeMillis();
						manager.recordWaitTime(t1 - t0);
						if (executionx == null) {
							log.trace("{} out of traces", LOG_PREFIX);
							break;
						}
						int scorex = calculateScore(executionx);
						keepers.add(scorex, executionx);
						allTime.add(scorex, executionx);
					}
					manager.incrementRefinements();
					log.trace("{} starting refinement", LOG_PREFIX);
					ExecutionCollection candidates = (keepers.size() > 0) ? keepers : allTime;
					setValues.clear();
					incValues.clear();
					for (Execution execution : candidates.executions()) {
						setValues.addAll((Set<Integer>) execution.getPayload("setValues"));
						incValues.addAll((Set<Integer>) execution.getPayload("incValues"));
					}
					for (int j = 0, n = candidates.size(); j < n; j++) {
						refine(candidates.getExecution(j), candidates.getScore(j));
					}
					PathTreeNode root = manager.getPathTree().getRoot();
					if ((root != null) && root.isFullyExplored()) {
						coastal.stopWork("PATH TREE FULLY EXPLORED");
						break;
					}
				}
			} catch (InterruptedException e) {
				log.trace("{} strategy task canceled", LOG_PREFIX);
				// throw e;
			}
			log.trace("{} strategy task finished", LOG_PREFIX);
			// return null;
		}

		protected void refine(Execution execution, int score) {
			long t0 = System.currentTimeMillis();
			inputsAdded = -1;
			manager.insertPath(execution, false);
			parameters = coastal.getParameters();
			Input input = execution.getInput();
			mutate(score, input);
			log.trace("{} added {} surfer models", LOG_PREFIX, inputsAdded);
			coastal.updateWork(inputsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void mutate(int score, Input model) {
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
					Input newInput = new Input(model);
					long min = (Long) coastal.getMinBound(name, type);
					long max = (Long) coastal.getMaxBound(name, type);
					newInput.put(name, randomLong(min, max));
					submitInput(score, newInput);
				} else if (type == float.class) {
					Input newInput = new Input(model);
					double min = (Float) coastal.getMinBound(name, type);
					double max = (Float) coastal.getMaxBound(name, type);
					newInput.put(name, Double.valueOf(rng.nextDouble(min, max)));
					submitInput(score, newInput);
				} else if (type == double.class) {
					Input newInput = new Input(model);
					double min = (Double) coastal.getMinBound(name, type);
					double max = (Double) coastal.getMaxBound(name, type);
					newInput.put(name, Double.valueOf(rng.nextDouble(min, max)));
					submitInput(score, newInput);
				} else if (type == String.class) {
					int min = (Integer) coastal.getMinBound(name, char.class);
					int max = (Integer) coastal.getMaxBound(name, char.class);
					int length = coastal.getParameterSize(name);
					for (int i = 0; i < length; i++) {
						Input newInput = new Input(model);
						newInput.put(name + TraceState.CHAR_SEPARATOR + i, randomLong(min, max));
						submitInput(score, newInput);
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

		private void update(int score, Input model, String name, int min, int max) {
			for (int i = 0, n = mutationCount / 2; i < n; i++) {
				Input newInput = new Input(model);
				newInput.put(name, Long.valueOf(randomInt(min, max)));
				submitInput(score, newInput);
			}
			if (setValues != null) {
				int cur = ((Long) model.get(name)).intValue();
				for (int set : setValues) {
					if ((set >= min) && (set <= max) && (set != cur)) {
						Input newInput = new Input(model);
						newInput.put(name, Long.valueOf(set));
						submitInput(score, newInput);
					}
				}
			}
			if (incValues != null) {
				for (int inc : incValues) {
					int set = ((Long) model.get(name)).intValue() + inc;
					if ((set >= min) && (set <= max)) {
						Input newInput = new Input(model);
						newInput.put(name, Long.valueOf(set));
						submitInput(score, newInput);
					}
					set = ((Long) model.get(name)).intValue() - inc;
					if ((set >= min) && (set <= max)) {
						Input newInput = new Input(model);
						newInput.put(name, Long.valueOf(set));
						submitInput(score, newInput);
					}
				}
			}
			for (int i = 0, n = (mutationCount + 1) / 2; i < n; i++) {
				Input newInput = new Input(model);
				newInput.put(name, Long.valueOf(randomInt(min, max)));
				submitInput(score, newInput);
			}
		}

		private void submitInput(int score, Input input) {
			input.setPayload("score", score);
			if (coastal.addSurferModel(input)) {
				inputsAdded++;
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
		 * @param execution execution to calculate a score for
		 * @return score for the execution
		 */
		private int calculateScore(Execution execution) {
			int oldScore = execution.getInput().getPayload("score", 0);
			Path path = execution.getPath();
			Map<String, Integer> edges = new HashMap<>();
			while (path != null) {
				String edge = (String) path.getChoice().getPayload("block");
				Integer count = edges.get(edge);
				if (count == null) {
					edges.put(edge, 1);
				} else {
					edges.put(edge, count + 1);
				}
				path = path.getParent();
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

	// ======================================================================
	//
	// SIZE-LIMITED COLLECTION OF EXECUTIONS
	//
	// ======================================================================

	/**
	 * Sorted collection of a fixed number of top scoring executions.
	 */
	public static class ExecutionCollection {

		/**
		 * Capacity of collection, i.e., maximum number of executions to keep.
		 */
		protected final int capacity;

		/**
		 * Array of top scores.
		 */
		protected final int[] scores;

		/**
		 * Array of top executions.
		 */
		protected final Execution[] executions;

		/**
		 * Number of items in collection.
		 */
		protected int size;

		/**
		 * Construct a new collection.
		 * 
		 * @param capacity capacity of the new collection
		 */
		public ExecutionCollection(int capacity) {
			assert capacity > 0;
			this.capacity = capacity;
			scores = new int[capacity];
			executions = new Execution[capacity];
			size = 0;
		}

		/**
		 * Clear the collection.
		 */
		public void clear() {
			size = 0;
		}

		/**
		 * Return the number of executions in the collection
		 * 
		 * @return size of the collection
		 */
		public int size() {
			return size;
		}

		/**
		 * Add a new execution to the collection. If the execution's score is among the
		 * top scores, it is added. If necessary, a low-scoring execution is thrown
		 * away.
		 * 
		 * @param score     score for the new execution
		 * @param execution new execution
		 */
		public void add(int score, Execution execution) {
			if (size == 0) {
				scores[0] = score;
				executions[0] = execution;
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
						executions[i] = executions[i - 1];
					}
					scores[position] = score;
					executions[position] = execution;
				}
			}
		}

		public Iterable<Execution> executions() {
			return new Iterable<Execution>() {
				@Override
				public Iterator<Execution> iterator() {
					return new Iterator<Execution>() {
						private int index = 0;

						@Override
						public Execution next() {
							return executions[index++];
						}

						@Override
						public boolean hasNext() {
							return index < size;
						}
					};
				}
			};
		}

		/**
		 * Return the score of the index-th spot in the collection.
		 * 
		 * @param index position of the desired score
		 * @return score in the desired position
		 */
		public int getScore(int index) {
			return scores[index];
		}

		/**
		 * Return the execution of the index-th spot in the collection.
		 * 
		 * @param index position of the desired execution
		 * @return execution in the desired position
		 */
		public Execution getExecution(int index) {
			return executions[index];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(size).append('/').append(capacity);
			for (int i = 0; i < size; i++) {
				b.append(' ').append(scores[i]).append(':').append(executions[i].toString());
			}
			return b.toString();
		}

	}

}
