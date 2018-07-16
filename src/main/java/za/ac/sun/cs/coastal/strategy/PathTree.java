package za.ac.sun.cs.coastal.strategy;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public abstract class PathTree {

	protected final Logger lgr = Configuration.getLogger();

	protected final boolean dumpPaths = Configuration.getDumpPaths();

	private PathTreeNode root = null;

	private int pathCount = 0;

	private int revisitCount = 0;

	public PathTreeNode getRoot() {
		return root;
	}

	public int getPathCount() {
		return pathCount;
	}

	public int getRevisitCount() {
		return revisitCount;
	}

	public SegmentedPC insertPath(SegmentedPC spc, boolean isInfeasible) {
		pathCount++;
		/*
		 * Step 1: Deconstruct the path condition.
		 */
		final int depth = spc.getDepth();
		SegmentedPC[] path = new SegmentedPC[depth];
		int idx = depth - 1;
		for (SegmentedPC s = spc; s != null; s = s.getParent()) {
			path[idx--] = s;
		}
		assert idx == -1;
		if (dumpPaths) {
			lgr.debug("depth:{}", depth);
		}
		/*
		 * Step 2: Add the new path (spc) to the path tree
		 */
		root = insert(root, path, 0, depth, isInfeasible);
		//		/// 
		//		if ((cur != null) && cur.isLeaf() && (idx == depth)) {
		//			if (dumpPaths) {
		//				lgr.debug("Revisit!");
		//			}
		//			revisitCount++;
		//		}
		/*
		 * Step 3: Dump the tree if required
		 */
		if (dumpPaths && (root != null)) {
			for (String ll : stringRepr()) {
				lgr.debug(ll);
			}
		}
		/*
		 * Step 4: Return a new path through the tree
		 */
		return findNewPath();
	}

	private PathTreeNode insert(PathTreeNode node, SegmentedPC[] path, int cur, int depth, boolean isInfeasible) {
		/*
		 * Step 1: create node if necessary
		 */
		if (node == null) {
			node = PathTreeNode.createNode(path[cur], path[cur].getNrOfOutcomes());
		}
		/*
		 * Step 2: find the child and check that it checks out
		 */
		int i = path[cur].getOutcomeIndex();
		/*
		 * Step 3: insert the rest of the path
		 */
		if (cur + 1 < depth) {
			node.setChild(i, insert(node.getChild(i), path, cur + 1, depth, isInfeasible));
		} else if (isInfeasible) {
			node.setChild(i, PathTreeNode.createInfeasible());
		} else {
			node.setChild(i, PathTreeNode.createLeaf());
		}
		/*
		 * Step 4: check if this node is now fully explored
		 */
		if (!node.isFullyExplored()) {
			int n = node.getChildCount();
			boolean full = true;
			for (i = 0; i < n; i++) {
				PathTreeNode x = node.getChild(i);
				if ((x == null) || !x.isComplete()) {
					full = false;
					break;
				}
			}
			if (full) {
				node.setFullyExplored();
			}
		}
		/*
		 * Step 5: return this node
		 */
		return node;
	}

	public abstract SegmentedPC findNewPath();

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
