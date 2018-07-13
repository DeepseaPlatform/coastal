package za.ac.sun.cs.coastal.strategy;

import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.green.expr.Expression;

public class PathTreeNode {

	private static final int PADDING = 3;
	private static final int MIN_WIDTH = 9;
	private static final int BRANCH = 3;

	private static int pathTreeCounter = 0;

	private final int id = ++pathTreeCounter;

	private final Expression activeConjunct;

	private final Expression passiveConjunct;

	private final PathTreeNode parent;

	private PathTreeNode left = null;

	private PathTreeNode right = null;

	private boolean isFullyExplored = false;

	private boolean isInfeasible = false;

	private boolean isLeaf = false;

	public PathTreeNode(boolean isInfeasible, PathTreeNode parent) {
		this.activeConjunct = null;
		this.passiveConjunct = null;
		this.parent = parent;
		if (isInfeasible) {
			this.isInfeasible = true;
		} else {
			this.isLeaf = true;
		}
	}

	public PathTreeNode(SegmentedPC spc, PathTreeNode parent) {
		this.activeConjunct = spc.getActiveConjunct();
		this.passiveConjunct = spc.getPassiveConjunct();
		this.parent = parent;
	}

	public int getId() {
		return id;
	}
	
	public Expression getActiveConjunct() {
		return activeConjunct;
	}

	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	public PathTreeNode getParent() {
		return parent;
	}
	
	public PathTreeNode getLeft() {
		return left;
	}

	public void setLeft(PathTreeNode left) {
		this.left = left;
	}
	
	public PathTreeNode getRight() {
		return right;
	}

	public void setRight(PathTreeNode right) {
		this.right = right;
	}
	
	public boolean isFullyExplored() {
		return isFullyExplored;
	}

	public void setFullyExplored() {
		setFullyExplored(true);
	}

	public void setFullyExplored(boolean isFullyExplored) {
		this.isFullyExplored = isFullyExplored;
	}

	public boolean isInfeasible() {
		return isInfeasible;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public boolean isComplete() {
		return isFullyExplored || isInfeasible || isLeaf;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(id);
		b.append("[isFullyExplored=").append(isFullyExplored);
		b.append(" isInfeasible=").append(isInfeasible);
		b.append(" isLeaf=").append(isLeaf);
		b.append(" L=").append((left == null) ? "NUL" : ("#" + left.id));
		b.append(" R=").append((right == null) ? "NUL" : ("#" + right.id));
		if (activeConjunct != null) {
			b.append(" aConj=").append(activeConjunct.toString());
		}
		if (passiveConjunct != null) {
			b.append(" pConj=").append(passiveConjunct.toString());
		}
		b.append(']');
		return b.toString();
	}

	public int height() {
		int l = (left == null) ? 0 : left.height();
		int r = (right == null) ? 0 : right.height();
		return 1 + Math.max(l, r);
	}

	public int width() {
		if (isLeaf) {
			return 4 + PADDING;
		} else if (isInfeasible) {
			return 6 + PADDING;
		} else {
			// String c = SegmentedPC.constraintBeautify(pactiveConjunct.toString());
			// int w = Math.max(MIN_WIDTH, c.length());
			int w = Math.max(MIN_WIDTH, activeConjunct.toString().length());
			int l = (left == null) ? (1 + PADDING) : left.width();
			int r = (right == null) ? (1 + PADDING) : right.width();
			return BRANCH + l + Math.max(w, BRANCH + r);
		}
	}

	public String getShape() {
		if (isLeaf) {
			return "L";
		} else if (isInfeasible) {
			return "I";
		} else {
			String l = (left == null) ? "0" : left.getShape();
			String r = (right == null) ? "0" : right.getShape();
			return "(" + l + r + ")";
		}
	}

	public int stringFill(char[][] lines, int minx, int y, int depth) {
		if (isLeaf) {
			stringWrite(lines, minx, y, id);
			stringWrite(lines, minx, y + 1, "LEAF");
			return minx;
		} else if (isInfeasible) {
			stringWrite(lines, minx, y, id);
			stringWrite(lines, minx, y + 1, "INFEAS");
			return minx;
		} else {
			int lw = (right == null) ? 1 : right.width();
			int lx = minx;
			if (right == null) {
				stringWrite(lines, minx, y, "-");
			} else {
				lx = right.stringFill(lines, minx, y + 4, depth + 1);
			}
			int rx = minx;
			if (left == null) {
				stringWrite(lines, minx, y, "-");
			} else {
				rx = left.stringFill(lines, minx + lw, y + 4, depth + 1);
			}
			int mx = (lx + rx) / 2;
			stringWrite(lines, mx, y, id + (isFullyExplored ? " FULL" : ""));
			// String c = SegmentedPC.constraintBeautify(pathTree.activeConjunct.toString());
			// stringWrite(lines, mx, y + 1, c);
			stringWrite(lines, mx, y + 1, activeConjunct.toString());
			for (int x = lx; x <= rx; x++) {
				lines[y + 2][x] = '-';
			}
			lines[y + 2][mx] = '+';
			stringWrite(lines, lx, y + 2, "+F");
			stringWrite(lines, rx - 1, y + 2, "T+");
			lines[y + 3][lx] = '|';
			lines[y + 3][rx] = '|';
			return mx;
		}
	}
	
	private static void stringWrite(char[][] lines, int x, int y, int number) {
		stringWrite(lines, x, y, Integer.toString(number));
	}

	private static void stringWrite(char[][] lines, int x, int y, String string) {
		for (int i = 0; i < string.length(); i++) {
			lines[y][x++] = string.charAt(i);
		}
	}

}
