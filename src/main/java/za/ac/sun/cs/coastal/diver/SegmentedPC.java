package za.ac.sun.cs.coastal.diver;

import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * A class that implements segmented path conditions. It is called "segmented"
 * <b>not</b> because it consists of a chain of conjuncts (or segments), but
 * because each link in the chain comprises more than one expression (these are
 * the segments).
 * 
 * There are two segments in each link:
 * 
 * <ul>
 * <li>a main segment (or "active conjunct") that represents a branch
 * encountered during the execution, and</li>
 * <li>an auxiliary segment (or "passive conjunct") that represent additional
 * constraints on the variables.</li>
 * </ul>
 * 
 * The passive conjuncts are generated mainly by delegates.
 * 
 * Each instance of this class represents one link in the chain (technically, a
 * pair of main and auxiliary segments), with a link to the parent
 * {@code SegmentedPC} in the chain.
 */
public abstract class SegmentedPC implements Execution {

	/**
	 * A sentinel instance used when a {@code null} value is not appropriate.
	 */
	public static final SegmentedPC NULL = new SegmentedPCIf(null, null, null, true);

	/**
	 * The parent {@code SegmentedPC} in the path condition.
	 */
	protected final SegmentedPC parent;

	/**
	 * The depth (or length) of the path condition.
	 */
	protected final int depth;

	/**
	 * The passive conjunct. This is taken into account when compiling the full
	 * path condition (this link conjoined with the path condition of the
	 * parent). But it is never negated, since it represents external
	 * constraints that must remain in place.
	 */
	protected final Expression passiveConjunct;

	/**
	 * The computed path condition that consists of this link's active and
	 * passive conjuncts conjoined with the path condition of the parent. This
	 * is only computed when needed.
	 */
	protected Expression pathCondition = null;

	/**
	 * Construct a new segmented path condition.
	 * 
	 * @param parent
	 *            the parent path condition
	 * @param passiveConjunct
	 *            the passive conjunct
	 */
	protected SegmentedPC(SegmentedPC parent, Expression passiveConjunct) {
		this.parent = parent;
		this.passiveConjunct = passiveConjunct;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.Execution#getParent()
	 */
	@Override
	public SegmentedPC getParent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.Execution#getDepth()
	 */
	@Override
	public int getDepth() {
		return depth;
	}

	/**
	 * Return the passive conjunct. This may comprise a whole expression and not
	 * just a single comparison.
	 * 
	 * @return the passive conjunct expression
	 */
	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	/**
	 * Compute (if necessary) and return the path condition that consists of
	 * this {@code SegmentedPC} and the path condition of its parent.
	 * 
	 * @return the path condition for this instance
	 */
	public Expression getPathCondition() {
		if (pathCondition == null) {
			SegmentedPC p = getParent();
			if (p != null) {
				pathCondition = p.getPathCondition();
			}
			Expression apc = getActiveConjunct();
			if ((apc != null)) { // && !isConstant(apc) && !p.contains(apc)) {
				// Do we even need isConstant and p.contains()?
				// Perhaps duplicate and constant conjuncts are no longer relevant?
				pathCondition = (pathCondition == null) ? apc : new Operation(Operator.AND, apc, pathCondition);
			}
			Expression ppc = getPassiveConjunct();
			if (ppc != null) {
				pathCondition = (pathCondition == null) ? ppc : new Operation(Operator.AND, ppc, pathCondition);
			}
		}
		return pathCondition;
	}

	/**
	 * Return the expression that corresponds to the branch that generated this
	 * link in the path condition chain.
	 * 
	 * @return the branch expression associated with this instance
	 */
	public abstract Expression getExpression();

	/**
	 * Return the active conjunct that corresponds to this link in the path
	 * condition chain. <b>Note:</b> This is different from the expression
	 * returned by {@link #getExpression()}, which is the condition in its
	 * "positive" form as it appears in the code. The value of
	 * {@link #getActiveConjunct()} may be the negation of this expression.
	 * 
	 * @return the active conjunct for this instance
	 */
	public abstract Expression getActiveConjunct();

	/**
	 * A string representation of this segmented path condition. This is only
	 * computed when needed.
	 */
	protected String stringRep = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (stringRep == null) {
			stringRep = toString0();
		}
		return stringRep;
	}

	/**
	 * Compute the string representation of this segmented path condition.
	 * 
	 * @return a string representation of this segmented path condition
	 */
	public abstract String toString0();

	/**
	 * Check whether or not the given expression is constant (= consists only of
	 * constant expressions and operators).
	 * 
	 * @param expression
	 *            the input expression to check
	 * @return {@code true} if and only if the expression is a constant
	 */
	public static boolean isConstant(Expression expression) {
		if (expression instanceof Operation) {
			Operation operation = (Operation) expression;
			int n = operation.getOperatandCount();
			for (int i = 0; i < n; i++) {
				if (!isConstant(operation.getOperand(i))) {
					return false;
				}
			}
			return true;
		} else {
			return (expression instanceof Constant);
		}
	}

	// ======================================================================
	//
	// BINARY CHOICES
	//
	// ======================================================================

	public static class SegmentedPCIf extends SegmentedPC {

		private final Expression expression;

		private final boolean value;

		private final Expression activeConjunct;

		private final String signature;

		private SegmentedPC negated = null;

		public SegmentedPCIf(SegmentedPC parent, Expression expression, Expression passiveConjunct, boolean value) {
			super(parent, passiveConjunct);
			this.expression = expression;
			this.value = value;
			this.activeConjunct = this.value ? this.expression : negate(this.expression);
			if (this.value) {
				SegmentedPC p = getParent();
				this.signature = (p == null) ? "1" : (p.getSignature() + "1");
			} else {
				SegmentedPC p = getParent();
				this.signature = (p == null) ? "0" : (p.getSignature() + "0");
			}
		}

		private static Expression negate(Expression expression) {
			if (expression instanceof Operation) {
				Operation operation = (Operation) expression;
				switch (operation.getOperator()) {
				case NOT:
					return operation.getOperand(0);
				case EQ:
					return new Operation(Operator.NE, operation.getOperand(0), operation.getOperand(1));
				case NE:
					return new Operation(Operator.EQ, operation.getOperand(0), operation.getOperand(1));
				case LT:
					return new Operation(Operator.GE, operation.getOperand(0), operation.getOperand(1));
				case LE:
					return new Operation(Operator.GT, operation.getOperand(0), operation.getOperand(1));
				case GT:
					return new Operation(Operator.LE, operation.getOperand(0), operation.getOperand(1));
				case GE:
					return new Operation(Operator.LT, operation.getOperand(0), operation.getOperand(1));
				case AND:
					Expression e0 = negate(operation.getOperand(0));
					Expression e1 = negate(operation.getOperand(1));
					return new Operation(Operator.OR, e0, e1);
				case OR:
					e0 = negate(operation.getOperand(0));
					e1 = negate(operation.getOperand(1));
					return new Operation(Operator.AND, e0, e1);
				default:
					break;
				}
			}
			return new Operation(Operator.NOT, expression);
		}

		@Override
		public Expression getExpression() {
			return expression;
		}

		public boolean getValue() {
			return value;
		}

		@Override
		public Expression getActiveConjunct() {
			return activeConjunct;
		}

		@Override
		public String getSignature() {
			return signature;
		}

		@Override
		public int getNrOfOutcomes() {
			return 2;
		}

		@Override
		public int getOutcomeIndex() {
			return value ? 1 : 0;
		}

		@Override
		public String getOutcome(int index) {
			if (index == 0) {
				return "F";
			} else {
				return "T";
			}
		}

		@Override
		public SegmentedPC getChild(int index, Execution parent) {
			if (getValue() == (index == 1)) {
				return this;
			} else {
				return new SegmentedPCIf((SegmentedPC) parent, getExpression(), getPassiveConjunct(), index == 1);
			}
		}

		public SegmentedPC negate() {
			if (negated == null) {
				negated = new SegmentedPCIf(getParent(), getExpression(), getPassiveConjunct(), !getValue());
			}
			return negated;
		}

		@Override
		public String toString0() {
			if (this == SegmentedPC.NULL) {
				return "Null-SPC";
			}
			Expression e = getPassiveConjunct();
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%-7s ", getValue() ? "TRUE" : "FALSE"));
			sb.append(String.format("%-30s ", getExpression().toString()));
			sb.append(String.format("%s", (e == null) ? "NULL" : e.toString()));
			SegmentedPC p = getParent();
			if (p != null) {
				sb.append('\n').append(p.toString());
			}
			return sb.toString();
		}

	}

	// ======================================================================
	//
	// N-ARY CHOICES
	//
	// ======================================================================

	public static class SegmentedPCSwitch extends SegmentedPC {

		private final Expression expression;

		private final int min;

		private final int max;

		private final int cur;

		private final Expression activeConjunct;

		private final String signature;

		public SegmentedPCSwitch(SegmentedPC parent, Expression expression, Expression passiveConjunct, int min,
				int max, int cur) {
			super(parent, passiveConjunct);
			this.expression = expression;
			this.min = min;
			this.max = max;
			this.cur = cur;
			if (this.cur < this.min) {
				Operation lo = new Operation(Operator.LT, expression, new IntConstant(this.min));
				Operation hi = new Operation(Operator.GT, expression, new IntConstant(this.max));
				this.activeConjunct = new Operation(Operator.OR, lo, hi);
				SegmentedPC p = getParent();
				this.signature = (p == null) ? "X" : (p.getSignature() + "X");
			} else {
				this.activeConjunct = new Operation(Operator.EQ, expression, new IntConstant(this.cur));
				String q = Integer.toString(this.cur) + ".";
				SegmentedPC p = getParent();
				this.signature = (p == null) ? q : (p.getSignature() + "." + q);
			}
		}

		@Override
		public Expression getExpression() {
			return expression;
		}

		@Override
		public Expression getActiveConjunct() {
			return activeConjunct;
		}

		@Override
		public String getSignature() {
			return signature;
		}

		@Override
		public int getNrOfOutcomes() {
			return max - min + 2;
		}

		@Override
		public int getOutcomeIndex() {
			return (cur < min) ? (max - min + 1) : (cur - min);
		}

		@Override
		public String getOutcome(int index) {
			if ((index >= 0) && (index < max - min + 1)) {
				return Integer.toString(index + min);
			} else {
				return "DF";
			}
		}

		@Override
		public SegmentedPC getChild(int index, Execution parent) {
			SegmentedPC p = (SegmentedPC) parent;
			if (min + index == cur) {
				return this;
			} else if (index > max - min) {
				return new SegmentedPCSwitch(p, expression, getPassiveConjunct(), min, max, min - 1);
			} else {
				return new SegmentedPCSwitch(p, expression, getPassiveConjunct(), min, max, min + index);
			}
		}

		@Override
		public String toString0() {
			Expression e = getPassiveConjunct();
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%-7s ", (cur < min) ? "DEFAULT" : Integer.toString(cur)));
			sb.append(String.format("%-30s ", getExpression().toString()));
			sb.append(String.format("%s", (e == null) ? "NULL" : e.toString()));
			SegmentedPC p = getParent();
			if (p != null) {
				sb.append('\n').append(p.toString());
			}
			return sb.toString();
		}

	}

}
