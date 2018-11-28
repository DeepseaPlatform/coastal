package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

public abstract class SegmentedPC {

	public static final SegmentedPC Null = new SegmentedPCIf(null, null, null, true);
	
	private final SegmentedPC parent;

	private final Expression passiveConjunct;

	private final int depth;

	private Expression pathCondition = null;

	public SegmentedPC(SegmentedPC parent, Expression passiveConjunct) {
		this.parent = parent;
		this.passiveConjunct = passiveConjunct;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	public SegmentedPC getParent() {
		return parent;
	}

	public Expression getPassiveConjunct() {
		return passiveConjunct;
	}

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

	public abstract String getSignature();

	public abstract int getNrOfOutcomes();

	public abstract int getOutcomeIndex();

	public abstract String getOutcome(int index);

	public abstract SegmentedPC getChild(int index, SegmentedPC parent);

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

}
