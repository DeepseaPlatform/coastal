package za.ac.sun.cs.coastal.pathtree;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Path;

/**
 * Representation of all execution paths in a single tree.
 */
public class PathTree implements Comparator<PathTreeNode> {

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
	 * Flag to indicate whether deepest paths should be recorded.
	 */
	private boolean recordDeepest = false;

	/**
	 * The root of the tree.
	 */
	private PathTreeNode root = null;

	/**
	 * The number of paths inserted.
	 */
	private final AtomicLong insertedCount = new AtomicLong(0);

	/**
	 * The number of paths whose insertion was unnecessary because they constitute a
	 * revisit of an already-inserted path.
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
	 * A queue to keep track of the deepest nodes that are not fully explored.
	 */
	private final PriorityBlockingQueue<PathTreeNode> deepest = new PriorityBlockingQueue<>(10, this);

	/**
	 * Constructor.
	 * 
	 * @param coastal reference to the analysis run controller
	 */
	public PathTree(COASTAL coastal) {
		log = coastal.getLog();
		this.broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		drawPaths = coastal.getConfig().getBoolean("coastal.settings.draw-paths", false);
	}

	/**
	 * Set the status of the {@code recordDeepest} flag.
	 * 
	 * @param recordDeepest the new value for the flag
	 */
	public void setRecordDeepest(boolean recordDeepest) {
		this.recordDeepest = recordDeepest;
	}

	/**
	 * Return the root of the path tree.
	 * 
	 * @return the path tree root node
	 */
	public PathTreeNode getRoot() {
		return root;
	}

	/**
	 * Return the number of paths inserted.
	 * 
	 * @return the number of paths inserted
	 */
	public long getInsertedCount() {
		return insertedCount.get();
	}

	/**
	 * Return the number of paths revisited.
	 * 
	 * @return the number of paths revisited
	 */
	public long getRevisitCount() {
		return revisitCount.get();
	}

	/**
	 * Return the number of infeasible paths.
	 * 
	 * @return the number of infeasible paths
	 */
	public long getInfeasibleCount() {
		return infeasibleCount.get();
	}

	/**
	 * Publish a report about the use of the path tree.
	 * 
	 * @param object dummy
	 */
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

	/**
	 * Insert a single path in the path tree. The execution that this path
	 * represents is given as a parameter. This may not be an actual execution, in
	 * the sense that it may be that the path associated with the execution is
	 * infeasible (in other words, there is no model for the path). In this case,
	 * the execution will contain a path, but no inputs. The {@code isInfeasible}
	 * flag indicates when this is the case.
	 * 
	 * @param execution    the execution associated with the path
	 * @param isInfeasible whether or not the execution was infeasible
	 * @return the terminal node of the newly-inserted path in the path tree
	 */
	public PathTreeNode insertPath(Execution execution, boolean isInfeasible) {
		long t = System.currentTimeMillis();
		final Path path = execution.getPath();
		if (path == null) {
			insertTime.addAndGet(System.currentTimeMillis() - t);
			return null;
		}
		insertedCount.incrementAndGet();
		if (isInfeasible) {
			infeasibleCount.incrementAndGet();
		}
		/*
		 * Step 1: Deconstruct the path condition.
		 */
		final int depth = path.getDepth();
		final Path[] paths = new Path[depth];
		int idx = depth;
		for (Path p = path; p != null; p = p.getParent()) {
			paths[--idx] = p;
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
					root = PathTreeNode.createRoot(paths[0]);
				}
			} finally {
				lock.writeLock().unlock();
			}
		}
		PathTreeNode lastNode = insert(execution, paths, isInfeasible);
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

	/**
	 * Helper routine to insert a path into the path tree. The major difference is
	 * that this routine assumes that the root exists, and that the path that
	 * corresponds to the execution has been separated into its prefixes.
	 * 
	 * @param execution    the execution associated with the path
	 * @param paths        the prefixes of the path that corresponds to the
	 *                     execution
	 * @param isInfeasible whether or not the execution was infeasible
	 * @return deepest node on the newly inserted path 
	 */
	private PathTreeNode insert(Execution execution, Path[] paths, boolean isInfeasible) {
		int depth = paths.length;
		PathTreeNode[] visitedNodes = new PathTreeNode[depth];
		PathTreeNode parent = root;
		visitedNodes[0] = root;
		long alt = paths[0].getChoice().getAlternative();
		for (int j = 1; j < depth; j++) {
			if (paths[j].getChoice().getBranch() instanceof Trace) {
				log.trace("::: insert(parent:{}, cur/depth:{}/{})", getId(parent), j, depth);
			} else {
				Expression conjunct = paths[j].getChoice().getActiveConjunct();
				log.trace("::: insert(parent:{}, conjunct:{}, cur/depth:{}/{})", getId(parent), conjunct, j, depth);
			}
			PathTreeNode n = parent.getChild(alt);
			if (n == null) {
				parent.lock();
				if (parent.getChild(alt) == null) {
					n = parent.createChild(paths[j], alt);
				} else {
					n = parent.getChild(alt);
				}
				parent.unlock();
			}
			parent = n;
			visitedNodes[j] = n;
			alt = paths[j].getChoice().getAlternative();
		}
		// At this point:
		// parent == node{i_d-1}, j == depth, i == i_d-1 == path[j-1].getOutcomeIndex()
		PathTreeNode n = parent.getChild(alt);
		if (n == null) {
			parent.lock();
			if (parent.getChild(alt) == null) {
				if (isInfeasible) {
					n = parent.createInfeasible(alt, execution);
				} else {
					n = parent.createLeaf(alt, execution);
				}
			}
			parent.unlock();
		} else {
			if (isInfeasible) {
				assert n.isInfeasible();
			} else {
				assert n.isLeaf();
			}
			n = null;
			revisitCount.incrementAndGet();
		}
		for (int j = depth - 1; j >= 0; j--) {
			PathTreeNode node = visitedNodes[j];
			if (!node.isFullyExplored()) {
				int k = node.getChildCount();
				boolean full = true;
				for (alt = 0; alt < k; alt++) {
					PathTreeNode x = node.getChild(alt);
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
		if (recordDeepest && (n != null) && !n.isFullyExplored()) {
			log.trace("%%%%%%% DEEPEST NODE ADDED: {}", n);
			deepest.add(n);
		}
		return n;
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	/**
	 * Return a formatted string to represent the {@code id} of a
	 * {@link PathTreeNode}.
	 * 
	 * @param node the node to compute the representation for
	 * @return a string representation of the node's {@code id}
	 */
	private String getId(PathTreeNode node) {
		if (node == null) {
			return "NUL";
		} else {
			return "#" + node.getId();
		}
	}

	/**
	 * Compute a string representation of the path tree. The tree is neatly
	 * formatted using ASCII graphics, and returned as an array of strings.
	 * 
	 * @return an array of strings that stores an ASCII image of the path tree
	 */
	public String[] stringRepr() {
		if (root == null) {
			return new String[] { "EMPTY PATH TREE" };
		}
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

	// ======================================================================
	//
	// SUPPORT FOR DEEPEST NODES
	//
	// ======================================================================

	public PathTreeNode getDeepestNode() {
		PathTreeNode node = null;
		do {
			node = deepest.poll();
		} while ((node != null) && node.isFullyExplored());
		return node;
	}

	@Override
	public int compare(PathTreeNode node1, PathTreeNode node2) {
		Path path1 = node1.getPath();
		if (path1 == null) {
			return 1;
		}
		Path path2 = node2.getPath();
		if (path2 == null) {
			return -1;
		}
		return Integer.compare(path2.getDepth(), path1.getDepth());
	}

}
