package za.ac.sun.cs.coastal.strategy.hybrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.Payload;

public class MybridFuzzerFactory implements StrategyFactory {

	protected final ImmutableConfiguration options;

	public MybridFuzzerFactory(COASTAL coastal, ImmutableConfiguration options) {
		this.options = options;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new MybridFuzzerManager(coastal, options);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((MybridFuzzerManager) manager).incrementTaskCount();
		return new MybridFuzzerStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// HYBRID FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class MybridFuzzerManager implements StrategyManager {

		private static final int DEFAULT_QUEUE_LIMIT = 100000;

		private static final int NEW_EDGE_SCORE = 200;

		private static final int NEW_BUCKET_SCORE = 50;

		public static final double SCORE_ATTENUATION = 0.35;

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

		protected final int queueLimit;

		protected final long randomSeed;

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

		public MybridFuzzerManager(COASTAL coastal, ImmutableConfiguration options) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
			queueLimit = ConfigHelper.zero(options.getInt("queue-limit", 0), DEFAULT_QUEUE_LIMIT);
			randomSeed = ConfigHelper.zero(options.getInt("seed", 0), System.currentTimeMillis());
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
			return "MybridFuzzer";
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

	public static class MybridFuzzerStrategy extends Strategy {

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

		protected final MybridFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int modelsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected List<String> parameterNames = null;

		protected List<String> keys = null;

		protected final int queueLimit;

		public MybridFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (MybridFuzzerManager) manager;
			broker = coastal.getBroker();
			queueLimit = this.manager.getQueueLimit();
		}

		@Override
		public Void call() throws Exception {
			log.trace("^^^ strategy task starting");
			try {
				while (true) {
					long t0 = System.currentTimeMillis();
					Trace trace = coastal.getNextTrace();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					manager.incrementRefinements();
					log.trace("+++ starting refinement");
					refine(trace);
				}
			} catch (InterruptedException e) {
				log.trace("^^^ strategy task canceled");
				throw e;
			}
		}

		protected void refine(Trace trace) {
			long t0 = System.currentTimeMillis();
			modelsAdded = -1;
			refine0(trace);
			log.trace("+++ added {} surfer models", modelsAdded);
			coastal.updateWork(modelsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void refine0(Trace trace) {
			manager.insertPath(trace, false);
			int score = calculateScore(trace);
			if ((score > 0) || (coastal.getSurferModelQueueLength() < queueLimit)) {
				byte[] base = extractBase(trace);
				for (int k = 0; k < base.length; k++) {
//					byte old = base[k];
//					base[k] += (byte) 15;
//					generateNewModel(base, score);
//					base[k] = (byte) rng.nextInt(256);
//					generateNewModel(base, score);
//					base[k] = old;
					for (int i = 0; i < 8; i++) {
						base[k] ^= (byte) (1 << i);
						generateNewModel(base, score);
						base[k] ^= i;
					}
				}
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
			MybridPayload pl = (MybridPayload) trace.getPayload(); 
			int oldScore = (pl == null) ? 0 : pl.score;
			Map<String, Integer> edges = new HashMap<>();
			String prevBlock = trace.getBlock();
			trace = trace.getParent();
			while (trace != null) {
				String block = trace.getBlock();
				String edge = block + ":" + prevBlock;
				Integer count = edges.get(edge);
				if (count == null) {
					edges.put(edge, 1);
				} else {
					edges.put(edge, count + 1);
				}
				prevBlock = block;
				trace = trace.getParent();
			}
			String edg = "X:" + prevBlock;
			Integer cnt = edges.get(edg);
			if (cnt == null) {
				edges.put(edg, 1);
			} else {
				edges.put(edg, cnt + 1);
			}
			for (String edge : edges.keySet()) {
				int count = edges.get(edge);
				if (count > 128) {
					edges.put(edge, 8);
				} else {
					edges.put(edge, COUNT_TO_BUCKET[count]);
				}
			}
			return manager.scoreEdges(edges) + (int) (MybridFuzzerManager.SCORE_ATTENUATION * oldScore);
		}

		/**
		 * Convert the variable/value assignments that generated trace to an
		 * array of bytes.
		 * 
		 * @param trace
		 *            the trace
		 * @return an array of bytes
		 */
		private byte[] extractBase(Trace trace) {
			parameters = coastal.getParameters();
			keys = new ArrayList<>(parameters.keySet());
			Collections.sort(keys);
			Map<String, Object> model = trace.getModel();
			BitOutputStream bos = new BitOutputStream();
			for (String name : keys) {
				Class<?> type = parameters.get(name);
				Object value = model.get(name);
				if (type == null) {
					continue;
				} else if (type == boolean.class) {
					bos.write((Long) value, 1);
				} else if (type == byte.class) {
					bos.write((Long) value, 8);
				} else if (type == char.class) {
					bos.write((Long) value, 16);
				} else if (type == short.class) {
					bos.write((Long) value, 16);
				} else if (type == int.class) {
					bos.write((Long) value, 32);
				} else if (type == long.class) {
					bos.write((Long) value, 64);
				} else if (type == String.class) {
					// ????
					System.exit(1);
				} else if (type == boolean[].class) {
					// ????
					System.exit(1);
				}
			}
			//log.info("model:{}",  model.toString());
			return bos.toByteArray();
		}

		/**
		 * Convert an array of bytes to a set of variable/value assignments.
		 * 
		 * @param base
		 *            the array of bytes
		 * @param score
		 *            the variable/value assignment
		 */
		private void generateNewModel(byte[] base, int score) {
			BitInputStream bis = new BitInputStream(base);
			Map<String, Object> model = new HashMap<>();
			for (String name : keys) {
				Class<?> type = parameters.get(name);
				if (type == null) {
					continue;
				} else if (type == boolean.class) {
					model.put(name, bis.read(1));
				} else if (type == byte.class) {
					model.put(name, bis.read(8));
				} else if (type == char.class) {
					model.put(name, bis.read(16));
				} else if (type == short.class) {
					model.put(name, bis.read(16));
				} else if (type == int.class) {
					model.put(name, bis.read(32));
				} else if (type == long.class) {
					model.put(name, bis.read(64));
				} else if (type == String.class) {
					// ????
					System.exit(1);
				} else if (type == boolean[].class) {
					// ????
					System.exit(1);
				}
			}
			Model mdl = new Model(score, model);
			mdl.setPayload(new MybridPayload(score));
			if (coastal.addSurferModel(mdl)) {
				modelsAdded++;
			}
		}

	}

	public static class MybridPayload implements Payload {
		public int score;
		public MybridPayload(int score) {
			this.score = score;
		}
	}

}
