package za.ac.sun.cs.coastal.pathtree;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Execution;

/**
 * Representation of all execution paths in a single tree.
 */
public class PathTree {

	/**
	 * The one-and-only logger.
	 */
	protected final Logger log;

	/**
	 * The message broker.
	 */
	protected final Broker broker;
	
	/**
	 * Flag to indicate whether paths should be drawn after updates.
	 */
	private final boolean drawPaths;

	/**
	 * The root of the tree.
	 */
	private PathTreeNode root = null;

	/**
	 * The number of paths inserted.
	 */
	private final AtomicLong insertedCount = new AtomicLong(0);

	/**
	 * The number of paths whose insertion was unnecessary because they
	 * constitute a revisit of an already-inserted path.
	 */
	private final AtomicLong revisitCount = new AtomicLong(0);

	/**
	 * Accumulator of all the path tree insertion times.
	 */
	protected final AtomicLong insertTime = new AtomicLong(0);
	
	/**
	 * A count of the number of infeasible paths.
	 */
	protected final AtomicLong infeasibleCount = new AtomicLong(0);

	/**
	 * A lock for updating the root of the tree.
	 */
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);

	/**
	 * Constructor.
	 * 
	 * @param coastal
	 *            reference to the analysis run controller
	 */
	public PathTree(COASTAL coastal) {
		log = coastal.getLog();
		this.broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		drawPaths = coastal.getConfig().getBoolean("coastal.settings.draw-paths", false);
	}

	/**
	 * Return the root of the path tree.
	 * 
	 * @return the path tree root node
	 */
	public PathTreeNode getRoot() {
		return root;
	}

	public long getInsertedCount() {
		return insertedCount.get();
	}

	public long getRevisitCount() {
		return revisitCount.get();
	}
	
	public long getInfeasibleCount() {
		return infeasibleCount.get();
	}
	
	public void report(Object object) {
		broker.publish("report", new Tuple("PathTree.inserted-count", insertedCount.get()));
		broker.publish("report", new Tuple("PathTree.revisit-count", revisitCount.get()));
		broker.publish("report", new Tuple("PathTree.infeasible-count", infeasibleCount.get()));
		broker.publish("report", new Tuple("PathTree.insert-time", insertTime.get()));
	}

	// ======================================================================
	//
	// PATH INSERTION
	//
	// ======================================================================
	
	public PathTreeNode insertPath(Execution execution, boolean isInfeasible) {
		long t = System.currentTimeMillis();
		insertedCount.incrementAndGet();
		if (isInfeasible) {
			infeasibleCount.incrementAndGet();
		}
		/*
		 * Step 1: Deconstruct the path condition.
		 */
		final int depth = execution.getDepth();
		final Execution[] path = new Execution[depth];
		int idx = depth;
		for (Execution e = execution; e != null; e = e.getParent()) {
			path[--idx] = e;
		}
		assert idx == 0;
		log.trace("::: depth:{}", depth);
		/*
		 * Step 2: Add the new path to the path tree
		 */
		if (root == null) {
			lock.writeLock().lock();
			try {
				if (root == null) {
					log.trace("::: creating root");
					root = PathTreeNode.createNode(path[0]);
				}
			} finally {
				lock.writeLock().unlock();
			}
		}
		PathTreeNode lastNode = insert(path, isInfeasible);
		/*
		 * Step 3: Dump the tree if required
		 */
		if (drawPaths && (root != null)) {
			log.trace(":::");
			for (String ll : stringRepr()) {
				log.trace("::: {}", ll);
			}
		}
		insertTime.addAndGet(System.currentTimeMillis() - t);
		return lastNode;
	}
	
	private PathTreeNode insert(Execution[] path, boolean isInfeasible) {
		int depth = path.length;
		PathTreeNode[] visitedNodes = new PathTreeNode[depth];
		PathTreeNode parent = root;
		visitedNodes[0] = root;
		int i = path[0].getOutcomeIndex();
		for (int j = 1; j < depth; j++) {
			if (path[j] instanceof Trace) {
				log.trace("::: insert(parent:{}, cur/depth:{}/{})", getId(parent), j, depth);
			} else {
				Expression conjunct = ((SegmentedPC) path[j]).getActiveConjunct();
				log.trace("::: insert(parent:{}, conjunct:{}, cur/depth:{}/{})", getId(parent), conjunct, j, depth);
			}
			PathTreeNode n = parent.getChild(i);
			if (n == null) {
				parent.lock();
				if (parent.getChild(i) == null) {
					n = PathTreeNode.createNode(path[j]);
					parent.setChild(i, n);
				} else {
					n = parent.getChild(i);
				}
				parent.unlock();
			}
			parent = n;
			visitedNodes[j] = n;
			i = path[j].getOutcomeIndex();
		}
		// At this point:
		// parent == node{i_d-1}, j == depth, i == i_d-1 == path[j-1].getOutcomeIndex()
		PathTreeNode n = parent.getChild(i);
		if (n == null) {
			parent.lock();
			if (parent.getChild(i) == null) {
				if (isInfeasible) {
					n = PathTreeNode.createInfeasible();
				} else {
					n = PathTreeNode.createLeaf();
				}
				parent.setChild(i, n);
			}
			parent.unlock();
		} else {
			n = null;
			revisitCount.incrementAndGet();
			if (isInfeasible) {
				assert true;
				// TO DO ----> assert ( NODE REALLY IS INFEASIBLE ) 
			} else {
				assert true;
				// TO DO ----> assert ( NODE REALLY IS LEAF ) 
			}
		}
		for (int j = depth - 1; j >= 0; j--) {
			PathTreeNode node = visitedNodes[j];
			if (!node.isFullyExplored()) {
				int k = node.getChildCount();
				boolean full = true;
				for (i = 0; i < k; i++) {
					PathTreeNode x = node.getChild(i);
					if ((x == null) || !x.isComplete()) {
						full = false;
						break;
					}
				}
				if (full) {
					node.lock();
					if (!node.isFullyExplored()) {
						log.trace("::: setting {} as fully explored", getId(node));
						node.setFullyExplored();
					}
					node.unlock();
				}
			}
		}
		return n;
	}
	
	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	private String getId(PathTreeNode node) {
		if (node == null) {
			return "NUL";
		} else {
			return "#" + node.getId();
		}
	}

	public String[] stringRepr() {
		int h = root.height() * 4 - 1;
		int w = root.width();
		/*
		 * Step 1: create and clear the character array
		 */
		char[][] lines = new char[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				lines[i][j] = ' ';
			}
		}
		/*
		 * Step 2: draw the path tree
		 */
		root.stringFill(lines, 0, 0);
		/*
		 * Step 3: convert the character array to strings
		 */
		String[] finalLines = new String[h];
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < h; i++) {
			b.setLength(0);
			int k = w - 1;
			while ((k >= 0) && (lines[i][k] == ' ')) {
				k--;
			}
			for (int j = 0; j <= k; j++) {
				b.append(lines[i][j]);
			}
			finalLines[i] = b.toString();
		}
		return finalLines;
	}

	public String getShape() {
		return (root == null) ? "0" : root.getShape();
	}

}
