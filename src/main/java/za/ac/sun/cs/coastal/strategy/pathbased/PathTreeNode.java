package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public final class PathTreeNode {

	private static final int SPACING = 3;

	private static int pathTreeNodeCounter = -1;

	private final int id = ++pathTreeNodeCounter;

	private final SegmentedPC pc;

	private final PathTreeNode[] children;

	private PathTreeNode parent;

	private final boolean leaf;

	private final boolean infeasible;

	private boolean fullyExplored = false;

	private boolean isGenerated = false;

	private final WriteLock lock = new ReentrantReadWriteLock(false).writeLock();

	private PathTreeNode(SegmentedPC pc, int nrOfChildren, boolean isLeaf, boolean isInfeasible) {
		this.pc = pc;
		this.children = new PathTreeNode[nrOfChildren];
		parent = null;
		this.leaf = isLeaf;
		this.infeasible = isInfeasible;
	}

	public static PathTreeNode createNode(SegmentedPC pc, int nrOfChildren) {
		return new PathTreeNode(pc, nrOfChildren, false, false);
	}

	public static PathTreeNode createInfeasible() {
		return new PathTreeNode(null, 0, false, true);
	}

	public static PathTreeNode createLeaf() {
		return new PathTreeNode(null, 0, true, false);
	}

	public int getId() {
		return id;
	}

	public SegmentedPC getPc() {
		return pc;
	}

	public int getChildCount() {
		return children.length;
	}

	public PathTreeNode getChild(int index) {
		return children[index];
	}

	public void setChild(int index, PathTreeNode node) {
		node.parent = this;
		children[index] = node;
	}

	public PathTreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public boolean isInfeasible() {
		return infeasible;
	}

	public boolean isFullyExplored() {
		return fullyExplored;
	}

	public void setFullyExplored() {
		fullyExplored = true;
	}

	public boolean isComplete() {
		return isFullyExplored() || isLeaf() || isInfeasible();
	}

	public boolean hasBeenGenerated() {
		return isGenerated;
	}
	
	public void setGenerated() {
		isGenerated = true;
	}
	
	public void lock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}
	
	public SegmentedPC getPcForChild(int i, SegmentedPC parent) {
		return getPc().getChild(i, parent);
	}

	public int height() {
		if (isLeaf()) {
			return 1;
		} else if (isInfeasible()) {
			return 1;
		} else {
			PathTreeNode ch = getChild(0);
			int m = (ch == null) ? 0 : ch.height();
			for (int i = 1; i < getChildCount(); i++) {
				ch = getChild(i);
				m = Math.max(m, (ch == null) ? 0 : ch.height());
			}
			return 1 + m;
		}
	}

	public int width() {
		if (isLeaf()) {
			return 4;
		} else if (isInfeasible()) {
			return 6;
		} else {
			PathTreeNode ch = getChild(0);
			int m = (ch == null) ? 1 : ch.width();
			for (int i = 1; i < getChildCount(); i++) {
				ch = getChild(i);
				m += SPACING + ((ch == null) ? 1 : ch.width());
			}
			String e = getPc().getExpression().toString();
			return 1 + Math.max(m, 2 * e.length());
		}
	}

	public int stringFill(char[][] lines, int x, int y) {
		if (isLeaf()) {
			if (hasBeenGenerated()) {
				stringWrite(lines, x, y, "#!" + id);
			} else {
				stringWrite(lines, x, y, "#" + id);
			}
			stringWrite(lines, x, y + 1, "LEAF");
			return x;
		} else if (isInfeasible()) {
			if (hasBeenGenerated()) {
				stringWrite(lines, x, y, "#!" + id);
			} else {
				stringWrite(lines, x, y, "#" + id);
			}
			stringWrite(lines, x, y + 1, "INFEAS");
			return x;
		} else {
			int firstx, lastx;
			PathTreeNode ch = getChild(0);
			if (ch == null) {
				stringWrite(lines, x, y + 4, "-");
				firstx = x;
				x += 1;
			} else {
				firstx = ch.stringFill(lines, x, y + 4);
				x += ch.width();
			}
			lastx = firstx;
			stringWrite(lines, lastx, y + 2, getPc().getOutcome(0));
			lines[y + 3][lastx] = '|';
			int k = getChildCount();
			for (int i = 1; i < k; i++) {
				x += SPACING;
				ch = getChild(i);
				int d = 0;
				String z = getPc().getOutcome(i);
				if (i < k - 1) {
					d = z.length() - 1;
				}
				if (ch == null) {
					stringWrite(lines, x, y + 4, "-");
					lastx = x;
					x += 1;
				} else {
					lastx = ch.stringFill(lines, x, y + 4);
					x += ch.width();
				}
				stringWrite(lines, lastx - d, y + 2, z);
				lines[y + 3][lastx] = '|';
			}
			int cx = (firstx + lastx) / 2, mx = cx;
			String n = "#";
			if (hasBeenGenerated()) {
				n += "!" + Integer.toString(id);
			} else {
				n += Integer.toString(id);
			}
			String e = getPc().getExpression().toString();
			mx -= Math.min(mx, Math.max(e.length(), n.length()) / 2);
			stringWrite(lines, mx, y, n);
			stringWrite(lines, mx, y + 1, e);
			for (x = firstx; x <= lastx; x++) {
				if (lines[y + 2][x] == ' ') {
					lines[y + 2][x] = '-';
				}
			}
			if (lines[y + 2][cx] == '-') {
				lines[y + 2][cx] = '+';
			}
			return mx;
		}
	}

	//	private static void stringWrite(char[][] lines, int x, int y, int number) {
	//		stringWrite(lines, x, y, Integer.toString(number));
	//	}

	private static void stringWrite(char[][] lines, int x, int y, String string) {
		for (int i = 0; i < string.length(); i++) {
			lines[y][x++] = string.charAt(i);
		}
	}

	public String getShape() {
		StringBuilder b = new StringBuilder();
		if (isLeaf()) {
			b.append('L');
		} else if (isInfeasible()) {
			b.append('I');
		} else {
			b.append('(');
			int n = getChildCount();
			for (int i = 0; i < n; i++) {
				PathTreeNode node = getChild(i);
				b.append((node == null) ? '0' : node.getShape());
			}
			b.append(')');
		}
		return b.toString();
	}

}
