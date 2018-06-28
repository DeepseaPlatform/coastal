package za.ac.sun.cs.coastal.symbolic;

import java.util.Stack;

import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * Class to store a path condition as a linked list of triples.
 * 
 * Each triple contains (1) an active conjunct that represents a branch
 * condition, (2) a passive conjunct that represents additional constraints on
 * the variables, and (3) a signal that indicates whether the branch was taken
 * ("<code>0</code>") or not ("<code>1</code>").
 *
 * For efficiency, this data structure also stores (1) the accumulated path
 * condition which is the conjunction of all path condition conjuncts of this
 * instance and its parent instances, and (2) the signature which is the
 * concatenation of this instance's signal and those of all its parents.
 */
public class SegmentedPC {

	public static final SegmentedPC ZERO = new SegmentedPC(null, Operation.TRUE, Operation.TRUE, 'x');

	protected final SegmentedPC parent;

	protected SegmentedPC child;

	protected final Expression activeConjunct;

	protected final Expression passiveConjunct;

	protected final char signal;

	protected Expression pathCondition = null;

	protected String signature = null;

	protected String stringRep = null, stringRep0 = null;

	public SegmentedPC(SegmentedPC parent, Expression activeConjunct, Expression passiveConjunct, char signal) {
		this.parent = parent;
		if (parent != null) {
			parent.child = this;
		}
		this.activeConjunct = activeConjunct;
		this.passiveConjunct = passiveConjunct;
		this.signal = signal;
	}

	public SegmentedPC getParent() {
		return parent;
	}

	public SegmentedPC getChild() {
		return child;
	}

	public Expression getActiveConjunct() {
		return activeConjunct;
	}

	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	public char getSignal() {
		return signal;
	}

	public Expression getPathCondition() {
		if (pathCondition == null) {
			Stack<Expression> conjuncts = new Stack<>();
			for (SegmentedPC spc = this; spc != null; spc = spc.parent) {
				if ((spc.activeConjunct != Operation.TRUE) && (spc.activeConjunct != null)) {
					if (spc.signal == '1') {
						conjuncts.push(spc.activeConjunct);
					} else {
						conjuncts.push(negate(spc.activeConjunct));
					}
				}
				if ((spc.passiveConjunct != Operation.TRUE) && (spc.passiveConjunct != null)) {
					conjuncts.push(spc.passiveConjunct);
				}
			}
			if (conjuncts.isEmpty()) {
				pathCondition = Operation.TRUE;
			} else {
				pathCondition = conjuncts.pop();
				while (!conjuncts.isEmpty()) {
					pathCondition = new Operation(Operator.AND, conjuncts.pop(), pathCondition);
				}
			}
		}
		return pathCondition;
	}

	public String getSignature() {
		if (signature == null) {
			StringBuilder sb = new StringBuilder();
			for (SegmentedPC spc = this; spc != null; spc = spc.parent) {
				if (spc.signal != 'x') {
					sb.insert(0, spc.signal);
				}
			}
			signature = sb.toString();
		}
		return signature;
	}

	public SegmentedPC negate() {
		return new SegmentedPC(parent, activeConjunct, passiveConjunct, (signal == '0') ? '1' : '0');
	}

	public static Expression negate(Expression expression) {
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
			default:
				break;
			}
		}
		return new Operation(Operator.NOT, expression);
	}

	@Override
	public String toString() {
		if (stringRep == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(toString0());
			stringRep = sb.toString();
		}
		return stringRep;
	}

	public String toString0() {
		if (stringRep0 == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(signal);
			if (activeConjunct == null) {
				sb.append("  ").append(String.format("%-30s", "null"));
			} else {
				sb.append("  ").append(String.format("%-30s", activeConjunct.toString()));
			}
			if (passiveConjunct == null) {
				sb.append("  null");
			} else {
				sb.append("  ").append(passiveConjunct.toString());
			}
			if (parent != null) {
				sb.append('\n').append(parent.toString0());
			}
			stringRep0 = sb.toString();
		}
		return stringRep0;
	}

}
