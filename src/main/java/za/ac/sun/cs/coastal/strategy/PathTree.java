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
		final int depth = spc.getDepth();
		SegmentedPC[] path = new SegmentedPC[depth];
		int idx = depth - 1;
		for (SegmentedPC s = spc; s != null; s = s.getParent()) {
			path[idx--] = s;
		}
		assert idx == -1;
		PathTreeNode pre = null;
		PathTreeNode cur = root;
		if (dumpPaths) {
			lgr.debug("depth:{}", depth);
			lgr.debug("Inserting into existing paths");
		}
		// First following existing tree as far as we can go
		boolean lastBranch = false;
		idx = 0;
		while ((cur != null) && (idx < depth)) {
			SegmentedPC s = path[idx];
			lastBranch = s.isLastConjunctTrue();
			if (dumpPaths) {
				lgr.debug("  cur:{} pre:{} conj:{} truth:{} idx:{}", getId(cur), getId(pre), s.getActiveConjunct(),
						lastBranch, idx);
			}
			assert !cur.isLeaf();
			pre = cur;
			cur = lastBranch ? cur.getRight() : cur.getLeft();
			idx++;
		}
		if ((cur != null) && cur.isLeaf() && (idx == depth)) {
			if (dumpPaths) {
				lgr.debug("Revisit!");
			}
			revisitCount++;
		}
		// If there is more to insert but no pre-tree, init root
		if ((idx < depth) && (pre == null)) {
			SegmentedPC s = path[idx];
			if (dumpPaths) {
				lgr.debug("Creating root");
			}
			pre = root = new PathTreeNode(s, pre);
			lastBranch = s.isLastConjunctTrue();
			idx++;
		}
		// While there is more to insert, create subtree node-by-node
		if ((idx < depth) && dumpPaths) {
			lgr.debug("More to insert");
		}
		while (idx < depth) {
			SegmentedPC s = path[idx];
			if (lastBranch) {
				if (dumpPaths) {
					lgr.debug("  new right child for pre:{} conj:{} idx:{}", getId(pre), s.getActiveConjunct(), idx);
				}
				assert pre.getRight() == null;
				pre.setRight(new PathTreeNode(s, pre));
				pre = pre.getRight();
			} else {
				if (dumpPaths) {
					lgr.debug("  new left child for pre:{} conj:{} idx:{}", getId(pre), s.getActiveConjunct(), idx);
				}
				assert pre.getLeft() == null;
				pre.setLeft(new PathTreeNode(s, pre));
				pre = pre.getLeft();
			}
			lastBranch = s.isLastConjunctTrue();
			idx++;
		}
		// Lastly, create the leaf
		if (pre == null) {
			root = new PathTreeNode(isInfeasible, pre);
			if (dumpPaths) {
				lgr.debug("Create new root {} (isInfeasible={})", getId(root), isInfeasible);
			}
		} else if (lastBranch) {
			pre.setRight(new PathTreeNode(isInfeasible, pre));
			if (dumpPaths) {
				lgr.debug("Create right leaf {} (isInfeasible={})", getId(pre.getRight()), isInfeasible);
			}
		} else {
			pre.setLeft(new PathTreeNode(isInfeasible, pre));
			if (dumpPaths) {
				lgr.debug("Create left leaf {} (isInfeasible={})", getId(pre.getLeft()), isInfeasible);
			}
		}
		// Travel back up branch and fill in completed flags
		if (dumpPaths) {
			lgr.debug("Travelling back up to root, propagating isFullyExplored");
		}
		while (pre != null) {
			boolean leftComplete = (pre.getLeft() != null) && pre.getLeft().isComplete();
			boolean rightComplete = (pre.getRight() != null) && pre.getRight().isComplete();
			if (leftComplete && rightComplete) {
				if (dumpPaths) {
					lgr.debug("  Setting fully explored for {}", getId(pre));
				}
				pre.setFullyExplored(true);
				pre = pre.getParent();
			} else {
				if (dumpPaths) {
					lgr.debug("  Stopping at {}", getId(pre));
				}
				pre.setFullyExplored(false);
				break;
			}
		}
		if (dumpPaths && (root != null)) {
			for (String ll : stringRepr()) {
				lgr.debug(ll);
			}
		}
		// Travel back down and find left-most unexplored path
		if (dumpPaths) {
			lgr.debug("Now travelling back down to find a viable path");
		}
		return findNewPath();
	}

	public abstract SegmentedPC findNewPath();

	public static String getId(PathTreeNode node) {
		return (node == null) ? "NUL" : ("#" + node.getId());
	}

	public String[] stringRepr() {
		int h = root.height() * 4 - 2;
		int w = root.width();
		char[][] lines = new char[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				lines[i][j] = ' ';
			}
		}
		root.stringFill(lines, 0, 0, 0);
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
