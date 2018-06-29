package za.ac.sun.cs.coastal.strategy;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public class DepthFirstStrategy implements Strategy {

	private static final Logger lgr = Configuration.getLogger();

	private static final Logger greenLgr = LogManager.getLogger("GREEN");

	private static final Green green = new Green("COASTAL", greenLgr);
	
	private static int pathCount = 0;

	private static int infeasibleCount = 0;
	
	private static int revisitCount = 0;
	
	private long totalTime = 0, solverTime = 0;
	
	public DepthFirstStrategy() {
		Reporters.register(this);
		Properties greenProperties = new Properties();
		greenProperties.setProperty("green.log.level", "OFF");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.canonizer", "za.ac.sun.cs.green.service.canonizer.ModelCanonizerService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3JavaService");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
	}

	@Override
	public Map<String, Constant> refine() {
		long t0 = System.currentTimeMillis();
		SegmentedPC spc = SymbolicState.getSegmentedPathCondition();
		lgr.info("explored <" + spc.getSignature() + "> " + spc.getPathCondition());
		boolean infeasible = false;
		while (true) {
			spc = PathTree.insertPath(spc, infeasible);
			if (spc == null) {
				totalTime += System.currentTimeMillis() - t0;
				return null;
			}
			infeasible = false;
			Expression pc = spc.getPathCondition();
			String sig = spc.getSignature();
			lgr.info("trying <" + sig + "> " + pc);
			Instance instance = new Instance(green, null, pc);
			long t1 = System.currentTimeMillis();
			@SuppressWarnings("unchecked")
			Map<IntVariable, Object> model = (Map<IntVariable, Object>) instance.request("model");
			solverTime += System.currentTimeMillis() - t1;
			if (model == null) {
				lgr.info("no model");
				infeasible = true;
				infeasibleCount++;
			} else {
				Map<String, Constant> newModel = new HashMap<>();
				for (IntVariable variable : model.keySet()) {
					String name = variable.getName();
					Constant value = new IntConstant((Integer) model.get(variable));
					newModel.put(name, value);
				}
				String modelString = newModel.entrySet().stream().filter(p -> !p.getKey().startsWith("$")).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())).toString();
				lgr.info("new model: {}", modelString);
//				if (visitedModels.add(modelString)) {
					totalTime += System.currentTimeMillis() - t0;
					return newModel;
//				} else {
//					lgr.debug("model {} has been visited before, recurring", modelString);
//					spc = PathTree.insertPath(spc, false);
//				}
			}
		}

	}

	// ======================================================================
	//
	// PATH TREE
	//
	// ======================================================================

	private static class PathTree {

		private static int pathTreeCounter = 0;

		private int id = ++pathTreeCounter;

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

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public String getName() {
		return "DepthFirstStrategy";
	}

	@Override
	public void report(PrintWriter out) {
		out.println("  Inserted paths: " + pathCount);
		out.println("  Revisited paths: " + revisitCount);
		out.println("  Infeasible paths: " + infeasibleCount);
		out.println("  Solver time: " + solverTime);
		out.println("  Overall strategy time: " + totalTime);
	}

}
