package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.Path;

public class AFLStyleFuzzerFactory implements StrategyFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "@A@";

	protected final Configuration configuration;

	public AFLStyleFuzzerFactory(COASTAL coastal, Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new AFLStyleFuzzerManager(coastal, configuration);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((AFLStyleFuzzerManager) manager).incrementTaskCount();
		return new Strategy[] { new AFLStyleFuzzerStrategy(coastal, (StrategyManager) manager) };
	}

	// ======================================================================
	//
	// AFL STYLE FUZZER STRATEGY MANAGER
	//
	// ======================================================================

	public static class AFLStyleFuzzerManager implements StrategyManager {

		private static final int DEFAULT_QUEUE_LIMIT = 100000;

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

		public AFLStyleFuzzerManager(COASTAL coastal, Configuration configuration) {
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
			pathTree = coastal.getPathTree();
			queueLimit = configuration.getInt("queue-limit", 0, DEFAULT_QUEUE_LIMIT);
			randomSeed = configuration.getLong("seed", 0, System.currentTimeMillis());
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
					score += 5; // MAKE CONSTANT
				} else if (newCount > count) {
					edgesSeen.put(edge, newCount);
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
			return "AFLStyleFuzzer";
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
	// AFL STYLE FUZZER STRATEGY
	//
	// ======================================================================

	public static class AFLStyleFuzzerStrategy extends Strategy {

		private static final int[] INTERESTING_8 = { -128, -1, 0, 1, 16, 32, 64, 100, 127 };

		private static final int[] INTERESTING_16 = { -32768, -128, 128, 255, 256, 512, 1000, 1024, 4096, 32767 };

		private static final int[] INTERESTING_32 = { -2147483648, -100663046, -32769, 32768, 65535, 65536, 100663045,
				2147483647 };

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

		protected final AFLStyleFuzzerManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		protected int modelsAdded;

		protected Map<String, Class<?>> parameters = null;

		protected List<String> parameterNames = null;

		protected List<String> keys = null;

		protected final int queueLimit;

		private final MTRandom rng;

		public AFLStyleFuzzerStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (AFLStyleFuzzerManager) manager;
			broker = coastal.getBroker();
			queueLimit = this.manager.getQueueLimit();
			rng = new MTRandom(this.manager.getRandomSeed());
		}

		@Override
		public void run() {
			log.trace("{} strategy task starting", LOG_PREFIX);
			try {
				while (true) {
					long t0 = System.currentTimeMillis();
					Execution execution = coastal.getNextTrace();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					manager.incrementRefinements();
					log.trace("{} starting refinement", LOG_PREFIX);
					refine(execution);
				}
			} catch (InterruptedException e) {
				log.trace("{} strategy task canceled", LOG_PREFIX);
				// throw e;
			}
		}

		protected void refine(Execution execution) {
			long t0 = System.currentTimeMillis();
			modelsAdded = -1;
			refine0(execution);
			log.trace("{} added {} surfer models", LOG_PREFIX, modelsAdded);
			coastal.updateWork(modelsAdded);
			manager.recordTime(System.currentTimeMillis() - t0);
		}

		protected void refine0(Execution execution) {
			int score = calculateScore(execution);
			if ((score > 0) || (coastal.getSurferModelQueueLength() < queueLimit)) {
				byte[] base = extractBase(execution);
				flipBits(base, score);
				flipBytes(base, score);
				arithInc(base, score);
				arithDec(base, score);
				replaceInteresting(base, score);
				havoc(base, score);
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
		 * counts.
		 * 
		 * @param execution the trace to calculate a score for
		 * @return the score
		 */
		private int calculateScore(Execution execution) {
			int oldScore = 0; // trace.getScore();
			Path path = execution.getPath();
			Map<String, Integer> edges = new HashMap<>();
			String prevBlock = (String) path.getChoice().getPayload("block");
			path = path.getParent();
			while (path != null) {
				String block = (String) path.getChoice().getPayload("block");
				String edge = block + ":" + prevBlock;
				Integer count = edges.get(edge);
				if (count == null) {
					edges.put(edge, 1);
				} else {
					edges.put(edge, count + 1);
				}
				prevBlock = block;
				path = path.getParent();
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
			return manager.scoreEdges(edges) + (oldScore >> 1);
		}

		/**
		 * Convert the variable/value assignments that generated trace to an array of
		 * bytes.
		 * 
		 * @param execution the trace
		 * @return an array of bytes
		 */
		private byte[] extractBase(Execution execution) {
			parameters = coastal.getParameters();
			keys = new ArrayList<>(parameters.keySet());
			Collections.sort(keys);
			Input input = execution.getInput();
			BitOutputStream bos = new BitOutputStream();
			for (String name : keys) {
				Class<?> type = parameters.get(name);
				Object value = input.get(name);
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
			return bos.toByteArray();
		}

		/**
		 * Convert an array of bytes to a set of variable/value assignments.
		 * 
		 * @param base  the array of bytes
		 * @param score the variable/value assignment
		 */
		private void generateNewModel(byte[] base, int score) {
			BitInputStream bis = new BitInputStream(base);
			Input input = new Input();
			for (String name : keys) {
				Class<?> type = parameters.get(name);
				if (type == null) {
					continue;
				} else if (type == boolean.class) {
					input.put(name, bis.read(1));
				} else if (type == byte.class) {
					input.put(name, bis.read(8));
				} else if (type == char.class) {
					input.put(name, bis.read(16));
				} else if (type == short.class) {
					input.put(name, bis.read(16));
				} else if (type == int.class) {
					input.put(name, bis.read(32));
				} else if (type == long.class) {
					input.put(name, bis.read(64));
				} else if (type == String.class) {
					// ????
					System.exit(1);
				} else if (type == boolean[].class) {
					// ????
					System.exit(1);
				}
			}
			input.setPayload("score", score);
			if (coastal.addSurferModel(input)) {
				modelsAdded++;
			}
		}

		// ----------------------------------------------------------------------
		//
		// HIGH-LEVEL MUTATIONS
		//
		// ----------------------------------------------------------------------

		private static final int ARITH_MAX = 35;

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

		private void flipBytes(byte[] base, int score) {
			for (int i = 0; i < base.length; i++) {
				generateNewModel(oneWalkingByte(base, i), score);
			}
			for (int i = 0; i < base.length - 1; i++) {
				generateNewModel(twoWalkingBytes(base, i), score);
			}
			for (int i = 0; i < base.length - 3; i++) {
				generateNewModel(fourWalkingBytes(base, i), score);
			}
		}

		private void arithInc(byte[] base, int score) {
			for (int d = 1; d <= ARITH_MAX; d++) {
				for (int i = 0; i < base.length; i++) {
					generateNewModel(incrementByte(base, i, d), score);
					if (i < base.length - 1) {
						generateNewModel(incrementTwoBytes(base, i, d), score);
					}
					if (i < base.length - 3) {
						generateNewModel(incrementFourBytes(base, i, d), score);
					}
				}
			}
		}

		private void arithDec(byte[] base, int score) {
			for (int d = 1; d <= ARITH_MAX; d++) {
				for (int i = 0; i < base.length; i++) {
					generateNewModel(incrementByte(base, i, -d), score);
					if (i < base.length - 1) {
						generateNewModel(incrementTwoBytes(base, i, -d), score);
					}
					if (i < base.length - 3) {
						generateNewModel(incrementFourBytes(base, i, -d), score);
					}
				}
			}
		}

		private void replaceInteresting(byte[] base, int score) {
			for (int i = 0; i < INTERESTING_8.length; i++) {
				byte newVal = (byte) (INTERESTING_8[i] & 0xff);
				for (int j = 0; j < base.length; j++) {
					byte curVal = base[j];
					base[j] = newVal;
					generateNewModel(base, score);
					base[j] = curVal;
				}
			}
			for (int i = 0; i < INTERESTING_16.length; i++) {
				byte newVal0 = (byte) (INTERESTING_16[i] & 0xff);
				byte newVal1 = (byte) (INTERESTING_16[i] >>> 8);
				for (int j = 0; j < base.length - 1; j++) {
					byte curVal1 = base[j];
					byte curVal2 = base[j + 1];
					base[j] = newVal0;
					base[j + 1] = newVal1;
					generateNewModel(base, score);
					base[j] = newVal1;
					base[j + 1] = newVal0;
					generateNewModel(base, score);
					base[j] = curVal1;
					base[j + 1] = curVal2;
				}
			}
			for (int i = 0; i < INTERESTING_32.length; i++) {
				byte newVal0 = (byte) (INTERESTING_32[i] & 0xff);
				byte newVal1 = (byte) ((INTERESTING_32[i] >>> 8) & 0xff);
				byte newVal2 = (byte) ((INTERESTING_32[i] >>> 16) & 0xff);
				byte newVal3 = (byte) ((INTERESTING_32[i] >>> 24) & 0xff);
				for (int j = 0; j < base.length - 3; j++) {
					byte curVal1 = base[j];
					byte curVal2 = base[j + 1];
					byte curVal3 = base[j + 1];
					byte curVal4 = base[j + 1];
					base[j] = newVal0;
					base[j + 1] = newVal1;
					base[j + 2] = newVal2;
					base[j + 3] = newVal3;
					generateNewModel(base, score);
					base[j] = newVal3;
					base[j + 1] = newVal2;
					base[j + 2] = newVal1;
					base[j + 3] = newVal0;
					generateNewModel(base, score);
					base[j] = curVal1;
					base[j + 1] = curVal2;
					base[j + 2] = curVal3;
					base[j + 3] = curVal4;

				}
			}
		}

		public void havoc(byte[] base, int score) {
			byte[] oldBase = Arrays.copyOf(base, base.length);
			int p, q;
			int runs = rng.nextInt(984) + 16;
			int tweaks = rng.nextInt(32) + 1;
			for (int i = 0; i < runs; i++) {
				for (int j = 0; j < tweaks; j++) {
					int option = rng.nextInt(11);
					switch (option) {
					case 0: // Flip a random bit
						p = rng.nextInt(base.length);
						q = rng.nextInt(8);
						base[p] = (byte) (base[p] ^ (1 << q));
						break;
					case 1: // Set random byte to an interesting value
						p = rng.nextInt(base.length);
						q = rng.nextInt(INTERESTING_8.length);
						base[p] = (byte) INTERESTING_8[q];
						break;
					case 2: // Set two bytes to interesting value
						if (base.length < 2) {
							continue;
						}
						p = rng.nextInt(base.length - 1);
						q = rng.nextInt(INTERESTING_16.length);
						if (rng.nextBoolean()) {
							base[p] = (byte) (INTERESTING_16[q] & 0xff);
							base[p + 1] = (byte) (INTERESTING_16[q] >>> 8);
						} else {
							base[p] = (byte) (INTERESTING_16[q] >>> 8);
							base[p + 1] = (byte) (INTERESTING_16[q] & 0xff);
						}
						break;
					case 3: // Set four bytes to interesting value
						if (base.length < 4) {
							continue;
						}
						p = rng.nextInt(base.length - 3);
						q = rng.nextInt(INTERESTING_32.length);
						if (rng.nextBoolean()) {
							base[p] = (byte) (INTERESTING_32[q] & 0xff);
							base[p + 1] = (byte) ((INTERESTING_32[q] >>> 8) & 0xff);
							base[p + 2] = (byte) ((INTERESTING_32[q] >>> 16) & 0xff);
							base[p + 3] = (byte) ((INTERESTING_32[q] >>> 24) & 0xff);
						} else {
							base[p] = (byte) ((INTERESTING_32[q] >>> 24) & 0xff);
							base[p + 1] = (byte) ((INTERESTING_32[q] >>> 16) & 0xff);
							base[p + 2] = (byte) ((INTERESTING_32[q] >>> 8) & 0xff);
							base[p + 3] = (byte) (INTERESTING_32[q] & 0xff);
						}
						break;
					case 4: // Randomly subtract from byte
						p = rng.nextInt(base.length);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] -= (byte) q;
						break;
					case 5: // Randomly subtract from two bytes
						if (base.length < 2) {
							continue;
						}
						p = rng.nextInt(base.length - 1);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] -= (byte) q;
						base[p + 1] -= (byte) q;
						break;
					case 6: // Randomly subtract from four bytes
						if (base.length < 4) {
							continue;
						}
						p = rng.nextInt(base.length - 3);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] -= (byte) q;
						base[p + 1] -= (byte) q;
						base[p + 2] -= (byte) q;
						base[p + 3] -= (byte) q;
						break;
					case 7: // Randomly add to byte
						p = rng.nextInt(base.length);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] += (byte) q;
						break;
					case 8: // Randomly add to two bytes
						if (base.length < 2) {
							continue;
						}
						p = rng.nextInt(base.length - 1);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] += (byte) q;
						base[p + 1] += (byte) q;
						break;
					case 9: // Randomly add to four bytes
						if (base.length < 4) {
							continue;
						}
						p = rng.nextInt(base.length - 3);
						q = rng.nextInt(ARITH_MAX) + 1;
						base[p] += (byte) q;
						base[p + 1] += (byte) q;
						base[p + 2] += (byte) q;
						base[p + 3] += (byte) q;
						break;
					case 10: // Set a random byte to a random value
						p = rng.nextInt(base.length);
						q = rng.nextInt(256);
						base[p] = (byte) q;
						break;
					// case 11:
					// case 12:
					// if (fixedInput) {
					// j--;
					// continue;
					// }
					// // Delete bytes.
					// if (base.length < 2) {
					// continue;
					// }
					// byteNum = rand.nextInt(base.length);
					// base = Mutations.removeByte(base, byteNum);
					// break;
					//
					// case 13:
					// // Insert or clone random bytes
					// if (fixedInput) {
					// j--;
					// continue;
					// }
					// boolean clone = (rand.nextInt(4) > 0);
					// int blockSize = rand.nextInt(base.length); // how much you want to clone or
					// insert
					// int blockStart = rand.nextInt(base.length - blockSize + 1); // where do you
					// start from
					// int newPos = rand.nextInt(base.length); // where are we putting the new stuff
					// base = Mutations.CloningOrInserting(base, clone, blockStart, newPos,
					// blockSize);
					// break;
					//
					// case 14:
					// // Overwrite bytes
					// // Random chunk or fixed bytes.
					// if (rand.nextInt(4) == 0) {
					// // Fixed bytes.
					// byteNum = rand.nextInt(base.length);
					// base = Mutations.replaceByte(base, (byte) (rand.nextInt(255) + 1), byteNum);
					// } else {
					// // Random chunk.
					// byteNum = rand.nextInt(base.length);
					// tmp = rand.nextInt(base.length);
					// if (byteNum == tmp) {
					// continue;
					// }
					// base = Mutations.replaceByte(base, base[tmp], byteNum);
					//
					// }
					// break;
					default:
						break;
					}
				}
				generateNewModel(base, score);
				System.arraycopy(oldBase, 0, base, 0, oldBase.length);
			}
		}

		// ----------------------------------------------------------------------
		//
		// LOW-LEVEL MUTATIONS
		//
		// ----------------------------------------------------------------------

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

		private static byte[] oneWalkingByte(byte[] base, int byteNo) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (base[byteNo] ^ 0xff);
			return newBase;
		}

		private static byte[] twoWalkingBytes(byte[] base, int byteNo) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (newBase[byteNo] ^ 0xff);
			newBase[byteNo + 1] = (byte) (newBase[byteNo + 1] ^ 0xff);
			return newBase;
		}

		private static byte[] fourWalkingBytes(byte[] base, int byteNo) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (newBase[byteNo] ^ 0xff);
			newBase[byteNo + 1] = (byte) (newBase[byteNo + 1] ^ 0xff);
			newBase[byteNo + 2] = (byte) (newBase[byteNo + 2] ^ 0xff);
			newBase[byteNo + 3] = (byte) (newBase[byteNo + 3] ^ 0xff);
			return newBase;
		}

		private static byte[] incrementByte(byte[] base, int byteNo, int amount) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (newBase[byteNo] + amount);
			return newBase;
		}

		private static byte[] incrementTwoBytes(byte[] base, int byteNo, int amount) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (newBase[byteNo] + amount);
			newBase[byteNo + 1] = (byte) (newBase[byteNo + 1] + amount);
			return newBase;
		}

		private static byte[] incrementFourBytes(byte[] base, int byteNo, int amount) {
			byte[] newBase = Arrays.copyOf(base, base.length);
			newBase[byteNo] = (byte) (newBase[byteNo] + amount);
			newBase[byteNo + 1] = (byte) (newBase[byteNo + 1] + amount);
			newBase[byteNo + 2] = (byte) (newBase[byteNo + 2] + amount);
			newBase[byteNo + 3] = (byte) (newBase[byteNo + 3] + amount);
			return newBase;
		}

	}

	// ======================================================================
	//
	// BIT INPUT STREAM
	//
	// ======================================================================

	public static final class BitInputStream {

		private final byte[] bytes;

		private int availInByte = 8;

		private int nextByte = 0;

		public BitInputStream(byte[] bytes) {
			this.bytes = bytes;
		}

		public long read(int bits) {
			if (bits <= availInByte) {
				long result = bytes[nextByte] & ((1 << bits) - 1);
				if (bits < availInByte) {
					bytes[nextByte] >>>= bits;
					availInByte -= bits;
				} else {
					availInByte = 8;
					nextByte++;
				}
				return result;
			} else {
				long result = bytes[nextByte];
				int offset = availInByte;
				nextByte++;
				bits -= availInByte;
				availInByte = 8;
				while (bits >= 8) {
					result |= bytes[nextByte] << offset;
					offset += 8;
					nextByte++;
					bits -= 8;
				}
				if (bits > 0) {
					result |= bytes[nextByte] & ((1 << bits) - 1);
					bytes[nextByte] >>>= bits;
					availInByte -= bits;
				}
				return result;
			}
		}

	}

	// ======================================================================
	//
	// BIT OUTPUT STREAM
	//
	// ======================================================================

	public static final class BitOutputStream {

		private static final int INITIAL_SIZE = 4;

		private byte[] bytes = new byte[INITIAL_SIZE];

		private int size = INITIAL_SIZE;

		private int free = size * 8;

		private int freeInByte = 8;

		private int firstFreeByte = 0;

		public void write(long value, int bits) {
			while (bits > free) {
				resize();
			}
			if (freeInByte >= bits) {
				long toCopy = (value << (8 - freeInByte)) & 0xffL;
				bytes[firstFreeByte] |= (byte) toCopy;
				free -= bits;
				freeInByte -= bits;
				if (freeInByte == 0) {
					freeInByte = 8;
					firstFreeByte++;
				}
			} else {
				long toCopy = (value << (8 - freeInByte)) & 0xffL;
				bytes[firstFreeByte] |= (byte) toCopy;
				value >>>= freeInByte;
				free -= freeInByte;
				bits -= freeInByte;
				freeInByte = 8;
				firstFreeByte++;
				while (bits >= 8) {
					bytes[firstFreeByte] |= (byte) (value & 0xffL);
					value >>>= 8;
					free -= 8;
					bits -= 8;
					firstFreeByte++;
				}
				if (bits > 0) {
					bytes[firstFreeByte] |= (byte) (value & 0xffL);
					free -= bits;
					freeInByte -= bits;
				}
			}
		}

		private void resize() {
			int newSize = size + (size >> 2);
			byte[] newBytes = Arrays.copyOf(bytes, newSize);
			free += (newSize - size) * 8;
			bytes = newBytes;
			size = newSize;
		}

		public byte[] toByteArray() {
			return bytes;
		}

	}

}
