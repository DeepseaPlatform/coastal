/**
 * 
 */
package za.ac.sun.cs.coastal.solver;

import java.util.Stack;

import za.ac.sun.cs.coastal.solver.Operation.Operator;
import za.ac.sun.cs.coastal.symbolic.Input;

/**
 *
 */
public final class Evaluator extends Visitor {

	private static final Evaluator EVALUATOR = new Evaluator();

	private final Stack<Constant> stack = new Stack<>();

	private Input input;

	private Evaluator() {
	}

	private void setInput(Input input) {
		this.input = input;
	}
	
	public static Constant evaluate(Input input, Expression expression) {
		EVALUATOR.setInput(input);
		try {
			expression.accept(EVALUATOR);
		} catch (VisitorException x) {
			return null;
		}
		return (Constant) EVALUATOR.stack.pop();
	}
	
	@Override
	public void postVisit(RealConstant realConstant) throws VisitorException {
		stack.push(realConstant);
	}
	
	@Override
	public void postVisit(IntegerConstant integerConstant) throws VisitorException {
		stack.push(integerConstant);
	}

	@Override
	public void postVisit(RealVariable realVariable) throws VisitorException {
		Double value = (Double) (input.get(realVariable.getName()));
		stack.push(new RealConstant((double) value, 64));
	}
	
	@Override
	public void postVisit(IntegerVariable integerVariable) throws VisitorException {
		Long value = (Long) (input.get(integerVariable.getName()));
		stack.push(new IntegerConstant((long) value, 64));
	}

	@Override
	public void postVisit(Operation operation) throws VisitorException {
		Constant l = null, r = null;
		Operator op = operation.getOperator();
		int arity = op.getArity();
		if (arity == 2) {
			assert !stack.isEmpty();
			r = stack.pop();
			assert !stack.isEmpty();
			l = stack.pop();
		} else if (arity == 1) {
			assert !stack.isEmpty();
			l = stack.pop();
		}
		switch (op) {
		case OR:
			long ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			long rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push((ll == 0) ? r : IntegerConstant.ONE64);
			break;
		case AND:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push((ll == 1) ? r : IntegerConstant.ZERO64);
			break;
		case NOT:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			stack.push((ll == 1) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case EQ:
			double lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			double rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx == rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case NE:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx != rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case LT:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx < rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case LE:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx <= rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case GT:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx > rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case GE:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx >= rx) ? IntegerConstant.ZERO64 : IntegerConstant.ONE64);
			break;
		case LCMP:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push((ll == rr) ? IntegerConstant.ZERO64 : (ll < rr) ? IntegerConstant.MONE64 : IntegerConstant.ONE64);
			break;
		case FCMPL:
		case FCMPG:
		case DCMPL:
		case DCMPG:
			lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
			rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
			stack.push((lx == rx) ? IntegerConstant.ZERO64 : (lx < rx) ? IntegerConstant.MONE64 : IntegerConstant.ONE64);
			break;
		case ADD:
			if ((l instanceof IntegerConstant) && (r instanceof IntegerConstant)) {
				ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
				rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
				stack.push(new IntegerConstant(ll + rr, 64));
			} else {
				lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
				rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
				stack.push(new RealConstant(lx + rx, 64));
			}
			break;
		case SUB:
			if ((l instanceof IntegerConstant) && (r instanceof IntegerConstant)) {
				ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
				rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
				stack.push(new IntegerConstant(ll - rr, 64));
			} else {
				lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
				rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
				stack.push(new RealConstant(lx - rx, 64));
			}
			break;
		case MUL:
			if ((l instanceof IntegerConstant) && (r instanceof IntegerConstant)) {
				ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
				rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
				stack.push(new IntegerConstant(ll * rr, 64));
			} else {
				lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
				rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
				stack.push(new RealConstant(lx * rx, 64));
			}
			break;
		case DIV:
			if ((l instanceof IntegerConstant) && (r instanceof IntegerConstant)) {
				ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
				rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
				stack.push(new IntegerConstant(ll / rr, 64));
			} else {
				lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
				rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
				stack.push(new RealConstant(lx / rx, 64));
			}
			break;
		case REM:
			if ((l instanceof IntegerConstant) && (r instanceof IntegerConstant)) {
				ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
				rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
				stack.push(new IntegerConstant(ll % rr, 64));
			} else {
				lx = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : ((RealConstant) l).getValue();
				rx = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : ((RealConstant) r).getValue();
				stack.push(new RealConstant(lx % rx, 64));
			}
			break;
		case B2I:
		case S2I:
		case I2L:
		case I2S:
		case I2C:
		case I2B:
		case F2D:
			stack.push(l);
			break;
		case BITOR:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll | rr, 64));
			break;
		case BITAND:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll & rr, 64));
			break;
		case BITXOR:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll ^ rr, 64));
			break;
		case SHL:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll << rr, 64));
			break;
		case ASHR:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll >> rr, 64));
			break;
		case LSHR:
			ll = (l instanceof IntegerConstant) ? ((IntegerConstant) l).getValue() : 0;
			rr = (r instanceof IntegerConstant) ? ((IntegerConstant) r).getValue() : 0;
			stack.push(new IntegerConstant(ll >>> rr, 64));
			break;
		default:
			stack.push(IntegerConstant.ZERO32);
		}
	}
	
}
