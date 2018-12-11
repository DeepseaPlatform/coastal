package za.ac.sun.cs.coastal.pathtree;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.Execution;

public final class PathTreeNode {

	/**
	 * Global counter for path tree nodes.
	 */
	private static int pathTreeNodeCounter = -1;

	/**
	 * The id for this path tree node.
	 */
	private final int id = ++pathTreeNodeCounter;

	/**
	 * The execution that corresponds to this node. Note that this may not be an
	 * actual execution of the program; instead, it could represent an
	 * infeasible execution.
	 */
	private final Execution execution;

	/**
	 * The child nodes of this node.
	 */
	private final PathTreeNode[] children;

	/**
	 * The parent of this node.
	 */
	private PathTreeNode parent;

	/**
	 * Is this a leaf node?
	 */
	private final boolean leaf;

	/**
	 * Is this execution infeasible?
	 */
	private final boolean infeasible;

	/**
	 * Has all executions that pass through this node been fully explored?
	 */
	private boolean fullyExplored = false;

	/**
	 * Flag used for generational search: has the negation of this execution
	 * been generated?
	 */
	private boolean isGenerated = false;

	/**
	 * A lock used when adding children to this node. Because all changes to the
	 * path tree are monotone (in other words, we only add children, or change
	 * fields in one direction), we do not need much locking.
	 */
	private final WriteLock lock = new ReentrantReadWriteLock(false).writeLock();

	/**
	 * Create a new path tree node. This constructor is private; node creation
	 * routines follow.
	 * 
	 * @param execution
	 *            the execution represented by this node
	 * @param nrOfChildren
	 *            the number of children
	 * @param isLeaf
	 *            whether or not this node is a leaf
	 * @param isInfeasible
	 *            whether or not the associated execution is infeasible
	 */
	private PathTreeNode(Execution execution, int nrOfChildren, boolean isLeaf, boolean isInfeasible) {
		this.execution = execution;
		this.children = new PathTreeNode[nrOfChildren];
		parent = null;
		this.leaf = isLeaf;
		this.infeasible = isInfeasible;
	}

	/**
	 * Create a new path tree node for the given execution.
	 * 
	 * @param trace
	 *            the execution to create the node for
	 * @return a new path tree node
	 */
	public static PathTreeNode createNode(Execution trace) {
		return new PathTreeNode(trace, trace.getNrOfOutcomes(), false, false);
	}

	/**
	 * Create an infeasible node.
	 * 
	 * @return a new (infeasible) path tree node
	 */
	public static PathTreeNode createInfeasible() {
		return new PathTreeNode(null, 0, false, true);
	}

	/**
	 * Create a new leaf node.
	 * 
	 * @return a new leaf node
	 */
	public static PathTreeNode createLeaf() {
		return new PathTreeNode(null, 0, true, false);
	}

	/**
	 * Return the identifier of this node.
	 * 
	 * @return the node identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Return the execution associated with the path tree node.
	 * 
	 * @return the execution associated with this node
	 */
	public Execution getExecution() {
		return execution;
	}

	/**
	 * Return the number of children for this path tree node.
	 * 
	 * @return the number of children of this node
	 */
	public int getChildCount() {
		return children.length;
	}

	/**
	 * Return a given child node of this path tree node.
	 * 
	 * @param index the number of the child
	 * @return the given child (or {@code null} if there is no such child
	 */
	public PathTreeNode getChild(int index) {
		if ((index < 0) || (index >= children.length)) {
			return null;
		} else {
			return children[index];
		}
	}

	/**
	 * Set the given child node of this path tree node.  This operation should happen between calls of {@link #lock()} and {@link #unlock()}.
	 * 
	 * @param index the number of the child to set
	 * @param node the new child node
	 */
	public void setChild(int index, PathTreeNode node) {
		node.parent = this;
		children[index] = node;
	}

	/**
	 * Return the parent node of this path tree node.
	 * 
	 * @return this node's parent
	 */
	public PathTreeNode getParent() {
		return parent;
	}

	/**
	 * Return whether or not this path tree node is a leaf.
	 * 
	 * @return the "leaf" status of this node
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * Return whether or not this path tree node is infeasible.
	 * 
	 * @return the "infeasible" status of this node
	 */
	public boolean isInfeasible() {
		return infeasible;
	}

	/**
	 * Return the "fully explored" status of this node.
	 * 
	 * @return whether or not this node has been fully explored
	 */
	public boolean isFullyExplored() {
		return fullyExplored;
	}

	/**
	 * Mark this node as fully explored.
	 */
	public void setFullyExplored() {
		fullyExplored = true;
	}

	/**
	 * Return the "completed" status of this node. A node is complete if it has
	 * been fully explored or if it is a leaf or infeasible.
	 * 
	 * @return the completed status of this node.
	 */
	public boolean isComplete() {
		return isFullyExplored() || isLeaf() || isInfeasible();
	}

	/**
	 * Return the "generated" status of this node.
	 * 
	 * @return whether or not a negated path has been generated for this node
	 */
	public boolean hasBeenGenerated() {
		return isGenerated;
	}

	/**
	 * Mark this node as generated.
	 */
	public void setGenerated() {
		isGenerated = true;
	}

	/**
	 * Request the lock for this node.
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * Release the lock for this node.
	 */
	public void unlock() {
		lock.unlock();
	}

	public Execution getExecutionForChild(int i, Execution parent) {
		return getExecution().getChild(i, parent);
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	private static final int SPACING = 3;

	/**
	 * Return the height of the subtree starting at this node.
	 * 
	 * @return the height of the subtree
	 */
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
			Execution ex = getExecution();
			String e = "0";
			if (ex instanceof SegmentedPC) {
				e = ((SegmentedPC) ex).getExpression().toString();
			}
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
			stringWrite(lines, lastx, y + 2, getExecution().getOutcome(0));
			lines[y + 3][lastx] = '|';
			int k = getChildCount();
			for (int i = 1; i < k; i++) {
				x += SPACING;
				ch = getChild(i);
				int d = 0;
				String z = getExecution().getOutcome(i);
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
			Execution ex = getExecution();
			String e = "0";
			if (ex instanceof SegmentedPC) {
				e = ((SegmentedPC) ex).getExpression().toString();
			}
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

	private static void stringWrite(char[][] lines, int x, int y, String string) {
		for (int i = 0; i < string.length(); i++) {
			lines[y][x++] = string.charAt(i);
		}
	}

	/**
	 * Return the shape of the subtree starting at this node. This is a string
	 * with nested parenthesized strings and the characters '<code>L</code>'
	 * (for leaf), '<code>I</code>' (for infeasible node), and '<code>0</code>'
	 * (for an explored stub).
	 * 
	 * @return the shape string for the subtree
	 */
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
