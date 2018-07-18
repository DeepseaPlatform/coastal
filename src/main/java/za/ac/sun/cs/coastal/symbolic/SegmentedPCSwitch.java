package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

public class SegmentedPCSwitch extends SegmentedPC {

	private final Expression expression;

	private final int min;

	private final int max;

	private final int cur;

	private final Expression activeConjunct;

	private final String signature;

	public SegmentedPCSwitch(SegmentedPC parent, Expression expression, Expression passiveConjunct, int min, int max,
			int cur) {
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
	public SegmentedPC getChild(int index, SegmentedPC parent) {
		if (min + index == cur) {
			return this;
		} else if (index > max - min) {
			return new SegmentedPCSwitch(parent, expression, getPassiveConjunct(), min, max, min - 1);
		} else {
			return new SegmentedPCSwitch(parent, expression, getPassiveConjunct(), min, max, min + index);
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
