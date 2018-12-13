package za.ac.sun.cs.coastal.diver;

import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

public abstract class SegmentedPC implements Execution {

	public static final SegmentedPC NULL = new SegmentedPCIf(null, null, null, true);

	private final SegmentedPC parent;

	private final Expression passiveConjunct;

	private final int depth;

	private Expression pathCondition = null;

	public SegmentedPC(SegmentedPC parent, Expression passiveConjunct) {
		this.parent = parent;
		this.passiveConjunct = passiveConjunct;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	@Override
	public SegmentedPC getParent() {
		return parent;
	}

	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	public Expression getPathCondition() {
		if (pathCondition == null) {
			SegmentedPC p = getParent();
			if (p != null) {
				Expression ppc = p.getPathCondition();
				if (ppc != null) {
					pathCondition = ppc;
				}
			}
			Expression psc = getPassiveConjunct();
			if (psc != null) {
				if (pathCondition != null) {
					pathCondition = new Operation(Operator.AND, psc, pathCondition);
				} else {
					pathCondition = psc;
				}
			}
			if (pathCondition == null) {
				pathCondition = getActiveConjunct();
			} else {
				pathCondition = new Operation(Operator.AND, getActiveConjunct(), pathCondition);
			}
		}
		return pathCondition;
	}

	public abstract Expression getExpression();

	public abstract Expression getActiveConjunct();

	protected static Expression negate(Expression expression) {
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

	private String stringRep = null;

	@Override
	public String toString() {
		if (stringRep == null) {
			stringRep = toString0();
		}
		return stringRep;
	}

	public abstract String toString0();

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
