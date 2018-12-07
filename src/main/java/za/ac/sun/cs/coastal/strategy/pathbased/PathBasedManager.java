package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.concurrent.atomic.AtomicLong;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.strategy.StrategyManager;

public class PathBasedManager implements StrategyManager {

	protected final COASTAL coastal;
	
	protected final Broker broker;

	protected final PathTree pathTree;
	
	/**
	 * A count of the number of infeasible paths.
	 */
	protected final AtomicLong infeasibleCount = new AtomicLong(0);

	/**
	 * Accumulator of all the path tree insertion times.
	 */
	protected final AtomicLong pathTreeTime = new AtomicLong(0);
	
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
	protected final AtomicLong refineTime = new AtomicLong(0);
	
	public PathBasedManager(COASTAL coastal) {
		this.coastal = coastal;
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		pathTree = coastal.getPathTree();
	}

	public PathTree getPathTree() {
		return pathTree;
	}

	public void incrementInfeasibleCount() {
		infeasibleCount.incrementAndGet();		
	}
	
	public long getInfeasibleCount() {
		return infeasibleCount.get();		
	}
	
	public PathTreeNode insertPath0(SegmentedPC spc, boolean infeasible) {
		long t = System.currentTimeMillis();
		PathTreeNode lastNode = pathTree.insertPath(spc, infeasible);
		pathTreeTime.addAndGet(System.currentTimeMillis() - t);
		return lastNode;
	}
	
	public boolean insertPath(SegmentedPC spc, boolean infeasible) {
		long t = System.currentTimeMillis();
		PathTreeNode lastNode = pathTree.insertPath(spc, infeasible);
		pathTreeTime.addAndGet(System.currentTimeMillis() - t);
		return (lastNode == null);
	}

	public void recordSolverTime(long time) {
		solverTime.addAndGet(time);
	}
	
	public void recordExtractionTime(long time) {
		extractionTime.addAndGet(time);
	}
	
	public void recordRefineTime(long time) {
		refineTime.addAndGet(time);
	}

	public void report(Object object) {
		broker.publish("report", new Tuple("Strategy.inserted-paths", pathTree.getPathCount()));
		broker.publish("report", new Tuple("Strategy.revisited-paths", pathTree.getRevisitCount()));
		broker.publish("report", new Tuple("Strategy.infeasible-count", infeasibleCount.get()));
		broker.publish("report", new Tuple("Strategy.pathtree-time", pathTreeTime.get()));
		broker.publish("report", new Tuple("Strategy.solver-time", solverTime.get()));
		broker.publish("report", new Tuple("Strategy.extraction-time", extractionTime.get()));
		broker.publish("report", new Tuple("Strategy.total-time", refineTime.get()));
	}

}
