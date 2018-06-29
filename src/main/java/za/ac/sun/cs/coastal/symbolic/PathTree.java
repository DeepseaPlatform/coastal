package za.ac.sun.cs.coastal.symbolic;

public class PathTree {

	private static int pathTreeCounter = 0;

	private int id = ++pathTreeCounter;

	private static int pathCount = 0;

	private static int revisitCount = 0;
	
	private static PathTree root = null;

	private boolean isFullyExplored = false;
	
	private boolean isInfeasible = false;
	
	private boolean isLeaf = false;
	
	private PathTree parent = null;
	
	private PathTree left = null;

	private PathTree right = null;

	private SegmentedPC spc = null;

	public PathTree(boolean isInfeasible, PathTree parent) {
		if (isInfeasible) {
			this.isInfeasible = true;
		} else {
			this.isLeaf = true;
		}
		this.parent = parent;
	}

	public PathTree(SegmentedPC spc, PathTree parent) {
		this.spc = spc;
		this.parent = parent;
	}
	
	public static int getPathCount() {
		return pathCount;
	}

	public static int getRevisitCount() {
		return revisitCount;
	}
	
	public boolean isComplete() {
		return isFullyExplored || isInfeasible || isLeaf;
	}
	
	public static SegmentedPC insertPath(SegmentedPC spc, boolean isInfeasible) {
		pathCount++;
		SegmentedPC spc0 = spc;
		if (spc0 != null) {
			while (spc0.getParent() != null) {
				spc0 = spc0.getParent();
			}
			spc = spc0;
		}
		PathTree pre = null;
		PathTree cur = root;
		char lastBranch = 'x';
		// First following existing tree as far as we can go
		while ((cur != null) && (spc != null)) {
			assert !cur.isLeaf;
			pre = cur;
			lastBranch = spc.getSignal();
			cur = (lastBranch == '0') ? cur.left : cur.right;
			spc = spc.getChild();
		}
		if ((cur != null) && cur.isLeaf && (spc == null)) {
			revisitCount++;
		}
		// If there is more to insert but no pre-tree, init root
		if ((spc != null) && (pre == null)) {
			pre = root = new PathTree(spc, pre);
			lastBranch = spc.getSignal();
			spc = spc.getChild();
		}
		// While there is more to insert, create subtree node-by-node
		while (spc != null) {
			if (lastBranch == '0') {
				assert pre.left == null;
				pre.left = new PathTree(spc, pre);
				pre = pre.left;
			} else {
				assert pre.right == null;
				pre.right = new PathTree(spc, pre);
				pre = pre.right;
			}
			lastBranch = spc.getSignal();
			spc = spc.getChild();
		}
		// Lastly, create the leaf
		if (pre == null) {
			root = new PathTree(isInfeasible, pre);
		} else if (lastBranch == '0') {
			pre.left = new PathTree(isInfeasible, pre);
		} else {
			pre.right = new PathTree(isInfeasible, pre);
		}
		// Travel back up branch and fill in completed flags
		// ??? pre = pre.parent;
		while (pre != null) {
			boolean leftComplete = (pre.left != null) && pre.left.isComplete();
			boolean rightComplete = (pre.right != null) && pre.right.isComplete();
			if (leftComplete && rightComplete) {
				pre.isFullyExplored = true;
				pre = pre.parent;
			} else {
				pre.isFullyExplored = false;
				break;
			}
		}
		// Travel back down and find left-most unexplored path
		cur = root;
		while (true) {
			if ((cur.left != null) && !cur.left.isComplete()) {
				cur = cur.left;
			} else if ((cur.right != null) && !cur.right.isComplete()) {
				cur = cur.right;
			} else if (cur == root) {
				return null;
			} else if ((cur.left != null) || (cur.right != null)) {
				return cur.spc.negate();
			} else {
				return null;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('@').append(id);
		b.append("[isFullyExplored=").append(isFullyExplored);
		b.append(" isInfeasible=").append(isInfeasible);
		b.append(" isLeaf=").append(isLeaf);
		b.append(" L=@").append(left == null ? 0 : left.id);
		b.append(" R=@").append(right == null ? 0 : right.id);
		b.append(" spc=").append(spc == null ? "X" : ("\n" + spc.toString())).append(']');
		return b.toString();
	}

}
