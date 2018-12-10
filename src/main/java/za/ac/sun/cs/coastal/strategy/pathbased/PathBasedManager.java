package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.concurrent.atomic.AtomicLong;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyFactory.StrategyManager;

public abstract class PathBasedManager implements StrategyManager {

	protected final COASTAL coastal;

	protected final Broker broker;

	protected final PathTree pathTree;
	
//	/**
//	 * A count of the number of infeasible paths.
//	 */
//	protected final AtomicLong infeasibleCount = new AtomicLong(0);

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
	 * Add a reported strategy wait time. This is used to determine if it makes
	 * sense to create additional threads (or destroy them).
	 * 
	 * @param time
	 *            the wait time for this strategy
	 */
	public void recordWaitTime(long time) {
		strategyWaitTime.addAndGet(time);
		strategyWaitCount.incrementAndGet();
	}

	public PathBasedManager(COASTAL coastal) {
		this.coastal = coastal;
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		pathTree = coastal.getPathTree();
	}

	public PathTree getPathTree() {
		return pathTree;
	}

//	public void incrementInfeasibleCount() {
//		infeasibleCount.incrementAndGet();		
//	}
//	
//	public long getInfeasibleCount() {
//		return infeasibleCount.get();		
//	}
	
	public PathTreeNode insertPath0(SegmentedPC spc, boolean infeasible) {
		return pathTree.insertPath(spc, infeasible);
	}
	
	public boolean insertPath(SegmentedPC spc, boolean infeasible) {
		return (pathTree.insertPath(spc, infeasible) == null);
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
//		broker.publish("report", new Tuple(name + ".infeasible-count", infeasibleCount.get()));
		broker.publish("report", new Tuple(name + ".solver-time", solverTime.get()));
		broker.publish("report", new Tuple(name + ".extraction-time", extractionTime.get()));
		broker.publish("report", new Tuple(name + ".wait-time", swt));
		broker.publish("report", new Tuple(name + ".total-time", strategyTime.get()));
	}

	protected abstract int getTaskCount();

}
