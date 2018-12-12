package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Model;

public abstract class TraceBasedFactory implements StrategyFactory {

	// ======================================================================
	//
	// TRACE-BASED SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	public abstract static class TraceBasedManager implements StrategyManager {

		protected final COASTAL coastal;

		protected final Broker broker;

		protected final PathTree pathTree;

		/**
		 * Accumulator of all the solver times.
		 */
		protected final AtomicLong solverTime = new AtomicLong(0);

		/**
		 * Accumulator of all the model extraction times.
		 */
		protected final AtomicLong extractionTime = new AtomicLong(0);

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

		public TraceBasedManager(COASTAL coastal) {
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

		public void recordSolverTime(long time) {
			solverTime.addAndGet(time);
		}

		public void recordExtractionTime(long time) {
			extractionTime.addAndGet(time);
		}

		public void recordRefineTime(long time) {
			strategyTime.addAndGet(time);
		}

		public void report(Object object) {
			String name = getName();
			double swt = strategyWaitTime.get() / strategyWaitCount.doubleValue();
			broker.publish("report", new Tuple(name + ".tasks", getTaskCount()));
			broker.publish("report", new Tuple(name + ".solver-time", solverTime.get()));
			broker.publish("report", new Tuple(name + ".extraction-time", extractionTime.get()));
			broker.publish("report", new Tuple(name + ".wait-time", swt));
			broker.publish("report", new Tuple(name + ".total-time", strategyTime.get()));
		}

		protected abstract int getTaskCount();

	}

	// ======================================================================
	//
	// TRACE-BASED SEARCH STRATEGY
	//
	// ======================================================================

	public abstract static class TraceBasedStrategy extends Strategy {

		protected final TraceBasedManager manager;

		protected final Broker broker;

		protected final Set<String> visitedModels = new HashSet<>();

		public TraceBasedStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
			this.manager = (TraceBasedManager) manager;
			broker = coastal.getBroker();
		}

		@Override
		public List<Model> refine(Execution execution) {
			long t0 = System.currentTimeMillis();
			List<Model> newModels = refine0((Trace) execution);
			manager.recordRefineTime(System.currentTimeMillis() - t0);
			return newModels;
		}

		protected List<Model> refine0(Trace trace) {
			if ((trace == null) || (trace == Trace.NULL)) {
				return null;
			}
			return null;
			//			log.trace("... explored <{}>", trace.getSignature());
			//			manager.insertPath(trace, false); // ignore revisited return value
			//			while (true) {
			//				trace = findNewPath(manager.getPathTree());
			//				if (trace == null) {
			//					log.trace("... no further paths");
			//					return null;
			//					// log.trace("...Tree shape: {}", pathTree.getShape());
			//				}
			//				Expression pc = trace.getPathCondition();
			//				String sig = trace.getSignature();
			//				log.trace("... trying   <{}> {}", sig, pc.toString());
			//				Map<String, Constant> model = findModel(pc);
			//				if (model == null) {
			//					log.trace("... no model");
			//					log.trace("(The spc is {})", trace.getPathCondition().toString());
			//					manager.insertPath(trace, true);
			//				} else {
			//					String modelString = model.toString();
			//					log.trace("... new model: {}", modelString);
			//					if (visitedModels.add(modelString)) {
			//						return Collections.singletonList(new Model(0, model));
			//					} else {
			//						manager.insertPath(trace, false);
			//						log.trace("... model {} has been visited before, retrying", modelString);
			//					}
			//				}
			//			}
		}

	}

}
