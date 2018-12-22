package za.ac.sun.cs.coastal.strategy.hybrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Model;

public class HybridFuzzerFactory implements StrategyFactory {

	protected final ImmutableConfiguration options;

	public HybridFuzzerFactory(COASTAL coastal, ImmutableConfiguration options) {
		this.options = options;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new HybridFuzzerManager(coastal, options);
	}

	@Override
	public Strategy createTask(COASTAL coastal, TaskManager manager) {
		((HybridFuzzerManager) manager).incrementTaskCount();
		return new HybridFuzzerStrategy(coastal, (StrategyManager) manager);
	}

	// ======================================================================
	//
	// HYBRID FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class HybridFuzzerManager implements StrategyManager {

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

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

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

		protected final Map<Tuple, Integer> tuples = new HashMap<>();

		public HybridFuzzerManager(COASTAL coastal, ImmutableConfiguration options) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
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

		protected synchronized int scoreTuples(Map<Tuple, Integer> tuples) {
			int score = 0;
			for (Map.Entry<Tuple, Integer> entry : tuples.entrySet()) {
				Tuple tuple = entry.getKey();
				int newCount = entry.getValue();
				Integer count = tuples.get(entry.getKey()); 
				if (count == null) {
					tuples.put(tuple, newCount);
					score += 5; // MAKE CONSTANT
				} else if (newCount > count) {
					tuples.put(tuple, newCount);
					score += 3; // MAKE CONSTANT
				}
			}
			return score;
		}

		public void report(Object object) {
			String name = getName();
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			broker.publish("report", new Tuple(name + ".tasks", getTaskCount()));
			broker.publish("report", new Tuple(name + ".wait-time", swt));
			broker.publish("report", new Tuple(name + ".total-time", strategyTime.get()));
		}

		private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#refinements", "waiting time",
				"total time" };

		@Override
		public String getName() {
			return "HybridFuzzer";
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

	public static class HybridFuzzerStrategy extends Strategy {

		protected final HybridFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int modelsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected List<String> parameterNames = null;
		
		protected List<String> keys = null;

		public HybridFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (HybridFuzzerManager) manager;
			broker = coastal.getBroker();
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
			int score = calculateScore(trace);
			byte[] base = extractBase(trace);
			flipBits(base, score);
		}

		private int calculateScore(Trace trace) {
			int prevBlock = -1;
			Map<Tuple, Integer> tuples = new HashMap<>();
			while (trace != null) {
				int block = trace.getBlock();
				Tuple tuple = new Tuple(block, prevBlock);
				Integer count = tuples.get(tuple);
				if (count == null) {
					tuples.put(tuple, 1);
				} else {
					tuples.put(tuple, count + 1);
				}
				prevBlock = block;
				trace = trace.getParent();
			}
			for (Tuple tuple : tuples.keySet()) {
				int count = tuples.get(tuple);
				if (count > 128) {
					tuples.put(tuple, 8);
				} else {
					tuples.put(tuple, HybridFuzzerManager.COUNT_TO_BUCKET[count]);
				}
			}
			return manager.scoreTuples(tuples);
		}

		private byte[] extractBase(Trace trace) {
			parameters = coastal.getParameters();
			keys = new ArrayList<>(parameters.keySet());
			Collections.sort(keys);
			Map<String, Object> model = trace.getModel();
			BitOutputStream bos = new BitOutputStream();
			for (String name : keys) {
				Class<?> type = parameters.get(name);
				Object value =  model.get(name);
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
				} else if (type == boolean[].class) {
					// ????
				}
			}
			return bos.toByteArray();
		}

		private void flipBits(byte[] base, int score) {
			for (int i = 0; i < 8; i++) {
				generateNewModel(oneWalkingBit(base, i), score);
			}
			for (int i = 0; i < 7; i++) {
				generateNewModel(twoWalkingBits(base, i), score);
			}
			for (int i = 0; i < 5; i++) {
				generateNewModel(fourWalkingBits(base, i), score);
			}
		}

		private static byte[] oneWalkingBit(byte[] base, int bit) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			for (int j = 0; j < newBase.length; j++) {
				newBase[j] = (byte) (newBase[j] ^ (1 << bit));
			}
			return newBase;
		}

		private static byte[] twoWalkingBits(byte[] base, int bit) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			for (int j = 0; j < newBase.length; j++) {
				newBase[j] = (byte) (newBase[j] ^ (3 << bit));
			}
			return newBase;
		}

		private static byte[] fourWalkingBits(byte[] base, int bit) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			for (int j = 0; j < newBase.length; j++) {
				newBase[j] = (byte) (newBase[j] ^ (15 << bit));
			}
			return newBase;
		}

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
				} else if (type == boolean[].class) {
					// ????
				}
			}
			if (coastal.addSurferModel(new Model(score, model))) {
				modelsAdded++;
			}
		}

	}

}
