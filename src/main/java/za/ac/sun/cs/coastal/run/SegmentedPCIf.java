package za.ac.sun.cs.coastal.run;

import za.ac.sun.cs.green.expr.Expression;

public class SegmentedPCIf extends SegmentedPC {

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
	public SegmentedPC getChild(int index, SegmentedPC parent) {
		if (getValue() == (index == 1)) {
			return this;
		} else {
			return new SegmentedPCIf(parent, getExpression(), getPassiveConjunct(), index == 1);
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
