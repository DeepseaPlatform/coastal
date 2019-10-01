package za.ac.sun.cs.coastal.pathtree;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.pathtree.PathTree.Lines;
import za.ac.sun.cs.coastal.symbolic.Branch;
import za.ac.sun.cs.coastal.symbolic.Choice;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Path;

/**
 * Abstract class for nodes of the path tree. Inner classes define final,
 * non-abstract subclasses to represent specific kinds of nodes.
 */
public abstract class PathTreeNode {

	/**
	 * Global counter for path tree nodes.
	 */
	private static int pathTreeNodeCounter = -1;

	/**
	 * The id for this path tree node.
	 */
	private final int id = ++pathTreeNodeCounter;

	/**
	 * The parent of this node.
	 */
	private final PathTreeNodeInner parent;

	/**
	 * Which child of the parent is this node?
	 */
	// private final long myIndex;

	/**
	 * Create a new node for the path tree.
	 * 
	 * @param parent the parent node
	 */
	private PathTreeNode(PathTreeNodeInner parent, long index) {
		this.parent = parent;
		// this.myIndex = index;
	}

	/**
	 * Create a child node for this node.
	 * 
	 * @param path  the path associated with the child node
	 * @param index the index of the child node of its parent
	 * @return the node child node
	 */
	public final PathTreeNode createChild(Path path, long index) {
		return new PathTreeNodeInner((PathTreeNodeInner) this, path, index);
	}

	/**
	 * Create a leaf child node for this node.
	 * 
	 * @param index the index of the child node of its parent
	 * @param execution the execution that corresponds to this node
	 * @return the node child node
	 */
	public final PathTreeNode createLeaf(long index, Execution execution) {
		return new PathTreeNodeLeaf((PathTreeNodeInner) this, index, execution);
	}

	/**
	 * Create an infeasible child node for this node.
	 * 
	 * @param index the index of the child node of its parent
	 * @param execution the execution that corresponds to this node
	 * @return the node child node
	 */
	public final PathTreeNode createInfeasible(long index, Execution execution) {
		return new PathTreeNodeInfeasible((PathTreeNodeInner) this, index, execution);
	}

	/**
	 * Create a root node for a path tree. This node will have a {@code null}
	 * parent, and the index associated with the node is 0, even though it has no
	 * meaning.
	 * 
	 * @param path the path associated with this node
	 * @return the new root node
	 */
	public static final PathTreeNode createRoot(Path path) {
		return new PathTreeNodeInner(path);
	}

	/**
	 * Return the identifier of this node.
	 * 
	 * @return the node identifier
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Return the branch associated with this node. If the node is a leaf or is
	 * infeasible, return {@code null}.
	 * 
	 * @return the branch associated with this node or {@code null}
	 */
	public abstract Branch getBranch();

	/**
	 * Return the path associated with this node. If the node is a leaf or is
	 * infeasible, return {@code null}.
	 * 
	 * @return the path associated with this node or {@code null}
	 */
	public abstract Path getPath();

	/**
	 * Return the number of children for this path tree node. If the node is a leaf
	 * or is infeasible, return 0.
	 * 
	 * @return the number of children of this node
	 */
	public abstract int getChildCount();

	/**
	 * Return a given child node of this path tree node. If the node is a leaf or is
	 * infeasible, always return {@code null}.
	 * 
	 * @param childIndex the number of the child
	 * @return the given child (or {@code null} if there is no such child
	 */
	public abstract PathTreeNode getChild(long childIndex);

	public final Path getPathForChild(long childIndex) {
		Path path = getPath();
		Choice lastChoice = path.getChoice();
		return new Path(path.getParent(), new Choice(lastChoice.getBranch(), childIndex));
	}

	/**
	 * Return the parent node of this path tree node.
	 * 
	 * @return this node's parent
	 */
	public final PathTreeNode getParent() {
		return parent;
	}

	/**
	 * Return whether or not this path tree node is a leaf.
	 * 
	 * @return the "leaf" status of this node
	 */
	public boolean isLeaf() {
		return false;
	}

	/**
	 * Return whether or not this path tree node is infeasible.
	 * 
	 * @return the "infeasible" status of this node
	 */
	public boolean isInfeasible() {
		return false;
	}

	/**
	 * Return the "fully explored" status of this node.
	 * 
	 * @return whether or not this node has been fully explored
	 */
	public abstract boolean isFullyExplored();

	/**
	 * Mark this node as fully explored.
	 */
	public abstract void setFullyExplored();

	/**
	 * Return the "completed" status of this node. A node is complete if it has been
	 * fully explored or if it is a leaf or infeasible.
	 * 
	 * @return the completed status of this node.
	 */
	public final boolean isComplete() {
		return isFullyExplored() || isLeaf() || isInfeasible();
	}

	/**
	 * Return the "generated" status of this node.
	 * 
	 * @return whether or not a negated path has been generated for this node
	 */
	public abstract boolean hasBeenGenerated();

	/**
	 * Mark this node as generated.
	 */
	public abstract void setGenerated();

	/**
	 * Request the lock for this node.
	 */
	public abstract void lock();

	/**
	 * Release the lock for this node.
	 */
	public abstract void unlock();

	/**
	 * Return the execution associated with this node. This only makes sense if this
	 * node is either a leaf or an infeasible terminal node.
	 *
	 * @return the execution associated with this node
	 */
	public abstract Execution getExecution();

	// ----------------------------------------------------------------------
	//
	// STRING REPRESENTATION
	//
	// ----------------------------------------------------------------------

	private static final int SPACING = 2;

//	/**
//	 * Return the height of the subtree starting at this node.
//	 * 
//	 * @return the height of the subtree
//	 */
//	private int height() {
//		if (isLeaf()) {
//			return 1;
//		} else if (isInfeasible()) {
//			return 1;
//		} else {
//			int numChildren = getChildCount();
//			int totalHeight = 0;
//			for (int i = 0; i < numChildren; i++) {
//				PathTreeNode child = getChild(i);
//				totalHeight = Math.max(totalHeight, (child == null) ? 1 : child.height());
//			}
//			return 1 + totalHeight;
//		}
//	}

	private int width() {
		// Calculate the label width
		int labelWidth = 2 + Integer.toString(id).length();
		int conditionWidth = 0;
		int childrenWidth = 0;
		if (isLeaf()) {
			conditionWidth = 4;
		} else if (isInfeasible()) {
			conditionWidth = 6;
		} else {
			Branch branch = getBranch();
			if (branch instanceof SegmentedPC) {
				conditionWidth = ((SegmentedPC) branch).getExpression().toString().length();
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

	public int stringFill(Lines lines, int x, int y) {
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
			Branch branch = getBranch();
			if (branch instanceof SegmentedPC) {
				b.append(((SegmentedPC) branch).getExpression().toString());
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
				if (child == null) {
					// We shall draw this leaf ourselves
					stringWrite(lines, curx, y + 4, "?");
					lastHook = curx;
				} else {
					lastHook = child.stringFill(lines, curx, y + 4);
				}
				stringWrite(lines, lastHook, y + 2, getBranch().getAlternativeRepr(i));
				stringWrite(lines, lastHook, y + 3, "|");
				if (firstHook == -1) {
					firstHook = lastHook;
				}
				curx += SPACING + ((child == null) ? 1 : child.width());
			}
			// Write the horizontal line
			for (int i = firstHook + 1; i < lastHook; i++) {
				if (lines.get(i, y + 2) == ' ') {
					lines.put(i, y + 2, '-');
				}
			}
		}
		return middle;
	}

	private static void stringWrite(Lines lines, int x, int y, String string) {
		for (int i = 0; i < string.length(); i++) {
			lines.put(x++, y, string.charAt(i));
		}
	}

	/**
	 * Return the shape of the subtree starting at this node. This is a string with
	 * nested parenthesized strings and the characters '<code>L</code>' (for leaf),
	 * '<code>I</code>' (for infeasible node), and '<code>0</code>' (for an explored
	 * stub).
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

	// ======================================================================
	//
	// REGULAR NODE
	//
	// ======================================================================

	private static final class PathTreeNodeInner extends PathTreeNode {

		/**
		 * The branch that led to this node being reach from its parent. Note that this
		 * may not be part of an actual execution of the program; instead, it could
		 * represent an infeasible execution. This field is not final, because a node
		 * may be updated when new information about it becomes available.
		 */
		private Branch branch;

		/**
		 * The path that leads to this execution. This field is not final, because a
		 * node may be updated when new information about it becomes available.
		 */
		private Path path;

		/**
		 * The child nodes of this node.
		 */
		private final PathTreeNode[] children;

		/**
		 * Has all executions that pass through this node been fully explored?
		 */
		private boolean fullyExplored = false;

		/**
		 * Flag used for generational search: has the negation of this execution been
		 * generated?
		 */
		private boolean isGenerated = false;

		/**
		 * A lock used when adding children to this node. Because all changes to the
		 * path tree are monotone (in other words, we only add children, or change
		 * fields in one direction), we do not need much locking.
		 */
		private final WriteLock lock = new ReentrantReadWriteLock(false).writeLock();

		/**
		 * Create a new path tree node. This constructor is private; public node
		 * creation routines are found in the {@link PathTreeNode} base class.
		 * 
		 * @param parent the parent of this node
		 * @param path   the path that led to this node
		 * @param index  the index of this child at its parent
		 */
		private PathTreeNodeInner(PathTreeNodeInner parent, Path path, long index) {
			super(parent, index);
			this.branch = path.getChoice().getBranch();
			this.path = path;
			this.children = new PathTreeNode[(int) branch.getNumberOfAlternatives()];
			parent.children[(int) index] = this;
		}

		/**
		 * Create a new path tree node that will act as a root for a path tree. This
		 * constructor is private; public node creation routines are found in the
		 * {@link PathTreeNode} base class.
		 * 
		 * @param path the path that led to this node
		 */
		private PathTreeNodeInner(Path path) {
			super(null, 0);
			this.branch = path.getChoice().getBranch();
			this.path = path;
			this.children = new PathTreeNode[(int) branch.getNumberOfAlternatives()];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getBranch()
		 */
		@Override
		public Branch getBranch() {
			return branch;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getPath()
		 */
		@Override
		public Path getPath() {
			return path;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getChildCount()
		 */
		@Override
		public int getChildCount() {
			return children.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getChild(long)
		 */
		public PathTreeNode getChild(long childIndex) {
			if ((childIndex < 0) || (childIndex >= children.length)) {
				return null;
			} else {
				return children[(int) childIndex];
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#isFullyExplored()
		 */
		@Override
		public boolean isFullyExplored() {
			return fullyExplored;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#setFullyExplored()
		 */
		@Override
		public void setFullyExplored() {
			fullyExplored = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#hasBeenGenerated()
		 */
		@Override
		public boolean hasBeenGenerated() {
			return isGenerated;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#setGenerated()
		 */
		@Override
		public void setGenerated() {
			isGenerated = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#lock()
		 */
		@Override
		public void lock() {
			lock.lock();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#unlock()
		 */
		@Override
		public void unlock() {
			lock.unlock();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getExecution()
		 */
		public Execution getExecution() {
			throw new RuntimeException("INNER NODE DO NOT HAVE ASSOCIATED EXECUTIONS");
		}

	}

	// ======================================================================
	//
	// TERMINAL NODE (LEAF OR INFEASIBLE)
	//
	// ======================================================================

	private abstract static class PathTreeNodeTerminal extends PathTreeNode {

		/**
		 * The execution associated with this terminal node.
		 */
		private final Execution execution;

		/**
		 * Create a new terminal node for the path tree.
		 * 
		 * @param parent the parent node
		 * @param index  the index of the leaf relative to its parent
		 */
		private PathTreeNodeTerminal(PathTreeNodeInner parent, long index, Execution execution) {
			super(parent, index);
			this.execution = execution;
			parent.children[(int) index] = this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getBranch()
		 */
		@Override
		public final Branch getBranch() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getPath()
		 */
		@Override
		public final Path getPath() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getChildCount()
		 */
		@Override
		public final int getChildCount() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getChild(long)
		 */
		public final PathTreeNode getChild(long childIndex) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#isFullyExplored()
		 */
		@Override
		public final boolean isFullyExplored() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#setFullyExplored()
		 */
		@Override
		public final void setFullyExplored() {
			throw new RuntimeException("CANNOT SET THE FULLY-EXPLORED STATUS OF A TERMINAL NODE");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#hasBeenGenerated()
		 */
		@Override
		public final boolean hasBeenGenerated() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#setGenerated()
		 */
		@Override
		public final void setGenerated() {
			throw new RuntimeException("CANNOT SET THE GENERATED STATUS OF A TERMINAL NODE");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#lock()
		 */
		@Override
		public final void lock() {
			throw new RuntimeException("CANNOT LOCK A TERMINAL NODE");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#unlock()
		 */
		@Override
		public final void unlock() {
			throw new RuntimeException("CANNOT UNLOCK A TERMINAL NODE");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#getExecution()
		 */
		@Override
		public final Execution getExecution() {
			return execution;
		}

	}

	// ======================================================================
	//
	// LEAF NODE
	//
	// ======================================================================

	private static final class PathTreeNodeLeaf extends PathTreeNodeTerminal {

		/**
		 * Create a new leaf node for the path tree.
		 * 
		 * @param parent the parent node
		 * @param index  the index of the leaf relative to its parent
		 */
		private PathTreeNodeLeaf(PathTreeNodeInner parent, long index, Execution execution) {
			super(parent, index, execution);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#isLeaf()
		 */
		@Override
		public boolean isLeaf() {
			return true;
		}

	}

	// ======================================================================
	//
	// INFEASIBLE NODE
	//
	// ======================================================================

	private static final class PathTreeNodeInfeasible extends PathTreeNodeTerminal {

		/**
		 * Create a new terminal node for the path tree.
		 * 
		 * @param parent the parent node
		 * @param index  the index of the leaf relative to its parent
		 */
		private PathTreeNodeInfeasible(PathTreeNodeInner parent, long index, Execution execution) {
			super(parent, index, execution);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.pathtree.PathTreeNode#isInfeasible()
		 */
		@Override
		public boolean isInfeasible() {
			return true;
		}

	}

//	/**
//	 * Change the execution associated with this node.
//	 * 
//	 * @param execution new execution to add to the node
//	 */
//	public void updateExecution(Execution execution) {
//		this.execution = execution;
//	}
//	
//	/**
//	 * Set the given child node of this path tree node.  This operation should happen between calls of {@link #lock()} and {@link #unlock()}.
//	 * 
//	 * @param index the number of the child to set
//	 * @param node the new child node
//	 */
//	public void setChild(int index, PathTreeNode node) {
//		node.parent = this;
//		children[index] = node;
//	}

//	public Execution getExecutionForChild(int i, Execution parent) {
//		return getExecution().getChild(i, parent);
//	}

}
