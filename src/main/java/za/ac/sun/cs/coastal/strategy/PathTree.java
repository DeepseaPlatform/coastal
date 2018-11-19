package za.ac.sun.cs.coastal.strategy;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.run.SegmentedPC;

/**
 * Representation of all execution paths in a single tree.
 */
public abstract class PathTree {

	/**
	 * The one-and-only logger.
	 */
	protected final Logger log;

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
	private final AtomicLong pathCount = new AtomicLong(0);

	/**
	 * The number of paths whose insertion was unnecessary because they constitute a revisit of an already-inserted path.
	 */
	private final AtomicLong revisitCount = new AtomicLong(0);

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);

	/**
	 * Constructor.
	 * 
	 * @param configuration the runtime configuration
	 */
	public PathTree(Configuration configuration) {
		log = configuration.getLog();
		drawPaths = configuration.getDrawPaths();
	}

	public PathTreeNode getRoot() {
		return root;
	}

	public long getPathCount() {
		return pathCount.get();
	}

	public long getRevisitCount() {
		return revisitCount.get();
	}

	public SegmentedPC insertPath(SegmentedPC spc, boolean isInfeasible) {
		pathCount.incrementAndGet();
		/*
		 * Step 1: Deconstruct the path condition.
		 */
		final int depth = spc.getDepth();
		final SegmentedPC[] path = new SegmentedPC[depth];
		int idx = depth;
		for (SegmentedPC s = spc; s != null; s = s.getParent()) {
			path[--idx] = s;
		}
		assert idx == 0;
		log.trace("::: depth:{}", depth);
		/*
		 * Step 2: Add the new path (spc) to the path tree
		 */
		if (root == null) {
			lock.writeLock().lock();
			try {
				if (root == null) {
					root = PathTreeNode.createNode(path[0], path[0].getNrOfOutcomes());
				}
			} finally {
				lock.writeLock().unlock();
			}
		}
		insert(path, isInfeasible);
		/*
		 * Step 3: Dump the tree if required
		 */
		if (drawPaths && (root != null)) {
			log.trace(":::");
			for (String ll : stringRepr()) {
				log.trace("::: {}", ll);
			}
		}
		/*
		 * Step 4: Return a new path through the tree
		 */
		return findNewPath();
	}

	private String getId(PathTreeNode node) {
		if (node == null) {
			return "NUL";
		} else {
			return "#" + node.getId();
		}
	}

	/*
	 * d == depth - 1
	 * 
	 * ROOT         path[0].getActiveConjunct  i_0=path[0].getOutcomeIndex()
	 *    |
	 * node{i_0}    path[1].getActiveConjunct  i_1=path[1].getOutcomeIndex()
	 *    |
	 * node{i_1}    path[2].getActiveConjunct  i_2=path[2].getOutcomeIndex()
	 *    |
	 * ...
	 *    |
	 * node{i_j-1}  path[j].getActiveConjunct  i_j=path[j].getOutcomeIndex()
	 *    |
	 * ...
	 *    |
	 * node{i_d-1}  path[d].getActiveConjunct  i_d=path[d].getOutcomeIndex()
	 *    |
	 * infeasble/leaf
	 */
	private void insert(SegmentedPC[] path, boolean isInfeasible) {
		int depth = path.length;
		PathTreeNode[] visitedNodes = new PathTreeNode[depth];
		PathTreeNode parent = root;
		visitedNodes[0] = root;
		// RECORD THE ROOT AS WE MOVE DOWN *** TODO
		int i = path[0].getOutcomeIndex();
		for (int j = 1; j < depth; j++) {
			log.trace("::: insert(parent:{}, conjunct:{}, cur/depth:{}/{})", getId(parent), path[j].getActiveConjunct(), j, depth);
			PathTreeNode n = parent.getChild(i);
			if (n == null) {
				parent.lock();
				if (parent.getChild(i) == null) {
					n = PathTreeNode.createNode(path[j], path[j].getNrOfOutcomes());
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
					parent.setChild(i, PathTreeNode.createInfeasible());
				} else {
					parent.setChild(i, PathTreeNode.createLeaf());
				}
			}
			parent.unlock();
		} else {
			revisitCount.incrementAndGet();
			if (isInfeasible) {
				// CHECK THAT IT IS INFEASIBLE *** TODO
			} else {
				// CHECK THAT IT IS LEAF *** TODO
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
	}

	// ======================================================================
	//
	// DFPathTree
	//
	// ======================================================================

	public abstract SegmentedPC findNewPath();

	// ======================================================================
	//
	// DFPathTree
	//
	// ======================================================================

	public String[] stringRepr() {
		int h = root.height() * 4 - 2;
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
