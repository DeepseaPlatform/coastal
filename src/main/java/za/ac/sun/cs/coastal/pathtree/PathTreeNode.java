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

	private static final int SPACING = 2;

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
			int numChildren = getChildCount();
			int totalHeight = 0;
			for (int i = 0; i < numChildren; i++) {
				PathTreeNode child = getChild(i);
				totalHeight = Math.max(totalHeight, (child == null) ? 1 : child.height());
			}
			return 1 + totalHeight;
		}
	}

	public int width() {
		// Calculate the label width
		int labelWidth = 2 + Integer.toString(id).length();
		int conditionWidth = 0;
		int childrenWidth = 0;
		if (isLeaf()) {
			conditionWidth = 4;
		} else if (isInfeasible()) {
			conditionWidth = 6;
		} else {
			Execution execution = getExecution();
			if (execution instanceof SegmentedPC) {
				conditionWidth = ((SegmentedPC) execution).getExpression().toString().length();
			} else {
				conditionWidth = 1;
			}
			int numChildren = getChildCount();
			childrenWidth = -SPACING;
			for (int i = 0; i < numChildren; i++) {
				PathTreeNode child = getChild(i);
				child = getChild(i);
				childrenWidth += SPACING + ((child == null) ? 1 : child.width());
			}
		}
		return 1 + Math.max(labelWidth, Math.max(conditionWidth, childrenWidth));
	}

	public int stringFill(char[][] lines, int x, int y) {
		// Construct the label
		StringBuilder b = new StringBuilder();
		if (hasBeenGenerated()) {
			b.append('*');
		}
		if (isFullyExplored()) {
			b.append('!');
		}
		b.append('#').append(id);
		String label = b.toString();
		int labelWidth = label.length();
		// Construct the "condition"
		b.setLength(0);
		if (isLeaf()) {
			b.append("LEAF");
		} else if (isInfeasible()) {
			b.append("INFEAS");
		} else {
			Execution execution = getExecution();
			if (execution instanceof SegmentedPC) {
				b.append(((SegmentedPC) execution).getExpression().toString());
			} else {
				b.append('?');
			}
		}
		String condition = b.toString();
		int conditionWidth = condition.length();
		// Recalculate the children width
		int numChildren = getChildCount();
		int childrenWidth = -SPACING;
		for (int i = 0; i < numChildren; i++) {
			PathTreeNode child = getChild(i);
			child = getChild(i);
			childrenWidth += SPACING + ((child == null) ? 1 : child.width());
		}
		// Decide where to start the label/condition/children
		int width = Math.max(labelWidth, Math.max(conditionWidth, childrenWidth));
		int middle = x + width / 2;
		int labelX = middle - labelWidth / 2;
		int conditionX = middle - conditionWidth / 2;
		int childX = middle - childrenWidth / 2;
		// Place the label and condition
		stringWrite(lines, labelX, y, label);
		stringWrite(lines, conditionX, y + 1, condition);
		// Now construct each of the leaves
		if (!isLeaf() && !isInfeasible()) {
			int firstHook = -1;
			int lastHook = -1;
			int curx = childX;
			for (int i = 0; i < numChildren; i++) {
				PathTreeNode child = getChild(i);
				child = getChild(i);
				if (child == null) {
					// We shall draw this leaf ourselves
					stringWrite(lines, curx, y + 4, "?");
					lastHook = curx;
				} else {
					lastHook = child.stringFill(lines, curx, y + 4);
				}
				stringWrite(lines, lastHook, y + 2, getExecution().getOutcome(i));
				stringWrite(lines, lastHook, y + 3, "|");
				if (firstHook == -1) {
					firstHook = lastHook;
				}
				curx += SPACING + ((child == null) ? 1 : child.width());
			}
			// Write the horizontal line
			for (int i = firstHook + 1; i < lastHook; i++) {
				if (lines[y + 2][i] == ' ') {
					lines[y + 2][i] = '-';
				}
			}
		}
		return middle;
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
