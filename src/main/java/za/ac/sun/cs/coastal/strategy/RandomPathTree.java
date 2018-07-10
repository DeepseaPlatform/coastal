package za.ac.sun.cs.coastal.strategy;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.green.expr.Expression;

public class RandomPathTree {

	// LOGGING AND DEBUGGING
	// ---------------------

	private static final Logger lgr = Configuration.getLogger();

	private static final boolean dumpPaths = Configuration.getDumpPaths();

	private static int pathTreeCounter = 0;

	private int id = ++pathTreeCounter;

	// WHOLE TREE PROPERTIES
	// ---------------------

	private static RandomPathTree root = null;

	private static int pathCount = 0;

	private static int revisitCount = 0;

	// INDIVIDUAL NODES
	// ----------------

	private final Expression activeConjunct;

	private final Expression passiveConjunct;

	private final RandomPathTree parent;

	private RandomPathTree left = null;

	private RandomPathTree right = null;

	private boolean isFullyExplored = false;

	private boolean isInfeasible = false;

	private boolean isLeaf = false;

	private static final Random rng = new Random();
	
	public RandomPathTree(boolean isInfeasible, RandomPathTree parent) {
		this.activeConjunct = null;
		this.passiveConjunct = null;
		this.parent = parent;
		if (isInfeasible) {
			this.isInfeasible = true;
		} else {
			this.isLeaf = true;
		}
	}

	public RandomPathTree(SegmentedPC spc, RandomPathTree parent) {
		this.activeConjunct = spc.getActiveConjunct();
		this.passiveConjunct = spc.getPassiveConjunct();
		this.parent = parent;
	}

	public static void setSeed(long seed) {
		rng.setSeed(seed);
	}

	public static String getId(RandomPathTree pathTree) {
		return (pathTree == null) ? "NUL" : ("#" + pathTree.getId());
	}

	public int getId() {
		return id;
	}

	public static int getPathCount() {
		return pathCount;
	}

	public static int getRevisitCount() {
		return revisitCount;
	}

	private boolean isComplete() {
		return isFullyExplored || isInfeasible || isLeaf;
	}

	public static SegmentedPC insertPath(SegmentedPC spc, boolean isInfeasible) {
		SegmentedPC pc = insertPath0(spc, isInfeasible);
		if (dumpPaths) {
			for (String ll : RandomPathTree.stringRepr()) {
				lgr.debug(ll);
			}
		}
		return pc;
	}

	public static SegmentedPC insertPath0(SegmentedPC spc, boolean isInfeasible) {
		pathCount++;
		final int depth = spc.getDepth();
		SegmentedPC[] path = new SegmentedPC[depth];
		int idx = depth - 1;
		for (SegmentedPC s = spc; s != null; s = s.getParent()) {
			path[idx--] = s;
		}
		assert idx == -1;
		RandomPathTree pre = null;
		RandomPathTree cur = root;
		idx = 0;
		if (dumpPaths) {
			lgr.debug("Inserting into existing paths");
		}
		// First following existing tree as far as we can go
		boolean lastBranch = false;
		idx = 0;
		while ((cur != null) && (idx < depth)) {
			SegmentedPC s = path[idx];
			lastBranch = s.isLastConjunctFalse();
			if (dumpPaths) {
				lgr.debug("  cur:{} pre:{} conj:{} truth:{}", getId(cur), getId(pre), s.getActiveConjunct(),
						lastBranch);
			}
			assert !cur.isLeaf;
			pre = cur;
			cur = lastBranch ? cur.right : cur.left;
			idx++;
		}
		if ((cur != null) && cur.isLeaf && (idx == depth)) {
			// EXPERIMENTAL DOES NOTWORK FULLY
			/*
			 * while ((pre != null) && pre.isFullyExplored) { pre = pre.parent;
			 * } if (pre != null) { pre.isFullyExplored = true; }
			 */
			/* WE NEED TO DO SOMETHING HERE */
			revisitCount++;
		}
		// If there is more to insert but no pre-tree, init root
		if ((idx < depth) && (pre == null)) {
			SegmentedPC s = path[idx];
			if (dumpPaths) {
				lgr.debug("Creating root");
			}
			pre = root = new RandomPathTree(s, pre);
			lastBranch = s.isLastConjunctFalse();
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
					lgr.debug("  new right child for pre:{} conj:{}", getId(pre), s.getActiveConjunct());
				}
				assert pre.right == null;
				pre.right = new RandomPathTree(s, pre);
				pre = pre.right;
			} else {
				if (dumpPaths) {
					lgr.debug("  new left child for pre:{} conj:{}", getId(pre), s.getActiveConjunct());
				}
				assert pre.left == null;
				pre.left = new RandomPathTree(s, pre);
				pre = pre.left;
			}
			lastBranch = s.isLastConjunctFalse();
			idx++;
		}
		// Lastly, create the leaf
		if (pre == null) {
			root = new RandomPathTree(isInfeasible, pre);
			if (dumpPaths) {
				lgr.debug("Create new root {} (isInfeasible={})", getId(root), isInfeasible);
			}
		} else if (lastBranch) {
			pre.right = new RandomPathTree(isInfeasible, pre);
			if (dumpPaths) {
				lgr.debug("Create right leaf {} (isInfeasible={})", getId(pre.right), isInfeasible);
			}
		} else {
			pre.left = new RandomPathTree(isInfeasible, pre);
			if (dumpPaths) {
				lgr.debug("Create left leaf {} (isInfeasible={})", getId(pre.left), isInfeasible);
			}
		}
		// Travel back up branch and fill in completed flags
		if (dumpPaths) {
			lgr.debug("Travelling back up to root, propagating isFullyExplored");
		}
		while (pre != null) {
			boolean leftComplete = (pre.left != null) && pre.left.isComplete();
			boolean rightComplete = (pre.right != null) && pre.right.isComplete();
			if (leftComplete && rightComplete) {
				if (dumpPaths) {
					lgr.debug("  Setting fully explored for {}", getId(pre));
				}
				pre.isFullyExplored = true;
				pre = pre.parent;
			} else {
				if (dumpPaths) {
					lgr.debug("  Stopping at {}", getId(pre));
				}
				pre.isFullyExplored = false;
				break;
			}
		}
		// Travel back down and find left-most unexplored path
		if (dumpPaths) {
			lgr.debug("Now travelling back down to find a viable path");
		}
		SegmentedPC newSpc = null;
		cur = root;
		while (true) {
			if (rng.nextBoolean()) {
				if ((cur.left != null) && !cur.left.isComplete()) {
					if (dumpPaths) {
						lgr.debug("  At {}, left is available", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, false);
					cur = cur.left;
				} else if (cur.left == null) {
					if (dumpPaths) {
						lgr.debug("  At {} (dead end), generating negate path (L)", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, false);
					return newSpc;
				} else if ((cur.right != null) && !cur.right.isComplete()) {
					if (dumpPaths) {
						lgr.debug("  At {}, right is available", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, true);
					cur = cur.right;
				} else if (cur.right == null) {
					if (dumpPaths) {
						lgr.debug("  At {} (dead end), generating negate path (R)", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, true);
					return newSpc;
				} else {
					if (dumpPaths) {
						lgr.debug("  At #{} (dead end), no more paths", cur.id);
					}
					return null;
				}
			} else {
				if ((cur.right != null) && !cur.right.isComplete()) {
					if (dumpPaths) {
						lgr.debug("  At {}, right is available", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, true);
					cur = cur.right;
				} else if (cur.right == null) {
					if (dumpPaths) {
						lgr.debug("  At {} (dead end), generating negate path (R)", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, true);
					return newSpc;
				} else if ((cur.left != null) && !cur.left.isComplete()) {
					if (dumpPaths) {
						lgr.debug("  At {}, left is available", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, false);
					cur = cur.left;
				} else if (cur.left == null) {
					if (dumpPaths) {
						lgr.debug("  At {} (dead end), generating negate path (L)", getId(cur));
					}
					newSpc = new SegmentedPC(newSpc, cur.activeConjunct, cur.passiveConjunct, false);
					return newSpc;
				} else {
					if (dumpPaths) {
						lgr.debug("  At #{} (dead end), no more paths", cur.id);
					}
					return null;
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getId(this));
		b.append("[isFullyExplored=").append(isFullyExplored);
		b.append(" isInfeasible=").append(isInfeasible);
		b.append(" isLeaf=").append(isLeaf);
		b.append(" L=").append(getId(left));
		b.append(" R=").append(getId(right));
		if (activeConjunct != null) {
			b.append(" aConj=").append(activeConjunct.toString());
		}
		if (passiveConjunct != null) {
			b.append(" pConj=").append(passiveConjunct.toString());
		}
		b.append(']');
		return b.toString();
	}

	public static String[] stringRepr() {
		int h = height(root) * 4 - 2;
		int w = width(root);
		char[][] lines = new char[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				lines[i][j] = ' ';
			}
		}
		stringFill(lines, 0, 0, root, 0);
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

	private static int height(RandomPathTree pathTree) {
		if (pathTree == null) {
			return 1;
		}
		int l = (pathTree.left == null) ? 0 : height(pathTree.left);
		int r = (pathTree.right == null) ? 0 : height(pathTree.right);
		return 1 + Math.max(l, r);
	}

	private static final int PADDING = 3;
	private static final int MIN_WIDTH = 9;
	private static final int BRANCH = 3;

	private static int width(RandomPathTree pathTree) {
		if (pathTree == null) {
			return 1 + PADDING;
		} else if (pathTree.isLeaf) {
			return 4 + PADDING;
		} else if (pathTree.isInfeasible) {
			return 6 + PADDING;
		} else {
			// String c = SegmentedPC.constraintBeautify(pathTree.activeConjunct.toString());
			// int w = Math.max(MIN_WIDTH, c.length());
			int w = Math.max(MIN_WIDTH, pathTree.activeConjunct.toString().length());
			return BRANCH + width(pathTree.left) + Math.max(w, BRANCH + width(pathTree.right));
		}
	}

	private static int stringFill(char[][] lines, int minx, int y, RandomPathTree pathTree, int depth) {
		if (pathTree == null) {
			stringWrite(lines, minx, y, "-");
			return minx;
		} else if (pathTree.isLeaf) {
			stringWrite(lines, minx, y, getId(pathTree));
			stringWrite(lines, minx, y + 1, "LEAF");
			return minx;
		} else if (pathTree.isInfeasible) {
			stringWrite(lines, minx, y, getId(pathTree));
			stringWrite(lines, minx, y + 1, "INFEAS");
			return minx;
		} else {
			int lw = width(pathTree.right);
			int lx = stringFill(lines, minx, y + 4, pathTree.right, depth + 1);
			int rx = stringFill(lines, minx + lw, y + 4, pathTree.left, depth + 1);
			int mx = (lx + rx) / 2;
			stringWrite(lines, mx, y, getId(pathTree) + (pathTree.isFullyExplored ? " FULL" : ""));
			// String c = SegmentedPC.constraintBeautify(pathTree.activeConjunct.toString());
			// stringWrite(lines, mx, y + 1, c);
			stringWrite(lines, mx, y + 1, pathTree.activeConjunct.toString());
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

	private static void stringWrite(char[][] lines, int x, int y, String string) {
		for (int i = 0; i < string.length(); i++) {
			lines[y][x++] = string.charAt(i);
		}
	}

	public static String getShape() {
		return getShape(root);
	}

	private static String getShape(RandomPathTree pathTree) {
		if (pathTree == null) {
			return "0";
		} else if (pathTree.isLeaf) {
			return "L";
		} else if (pathTree.isInfeasible) {
			return "I";
		} else {
			return "(" + getShape(pathTree.left) + getShape(pathTree.right) + ")";
		}
	}

}
