package za.ac.sun.cs.coastal.solver;

import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Operation.Operator;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.util.Reporter;

public class SolverGreen extends Solver {

	protected static int problemCounter = 0;

	protected final Logger log;

	protected final Green green;

	public SolverGreen(COASTAL coastal, Configuration configuration) {
		super(coastal, configuration);
		log = coastal.getLog();
		green = new Green("COASTAL", LogManager.getLogger("GREEN"));
		Properties greenProperties = configuration.extract("green");
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
//		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3JavaService");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
	}

	@SuppressWarnings("unchecked")
	public Input solve(Expression expression) {
		try {
			Translator t = new Translator();
			expression.accept(t);
			za.ac.sun.cs.green.expr.Expression expr = t.getTranslation();
			Instance instance = new Instance(green, null, expr);
			Map<IntVariable, IntConstant> model = (Map<IntVariable, IntConstant>) instance.request("model");
//			Map<IntVariable, Integer> model = (Map<IntVariable, Integer>) instance.request("model");
			return (model == null) ? null : retrieveModel(model);
		} catch (AssertionError x) {
			log.trace("VISITOR ASSERTION EXCEPTION", x);
		} catch (VisitorException x) {
			log.trace("VISITOR EXCEPTION", x);
		}
		return null;
	}

	@Override
	public void issueReport() {
		green.report(new Reporter() {
			@Override
			public void reportMessage(String context, String message) {
				coastal.getBroker().publish("report", new Tuple("green." + context, message));
			}

			@Override
			public void report(String context, String key, String value) {
				reportMessage(context, key + ":" + value);
			}
		});
	}

	// ======================================================================
	//
	// TRANSLATOR VISITOR
	//
	// ======================================================================

	private static class Translator extends Visitor {

		private final Stack<za.ac.sun.cs.green.expr.Expression> stack = new Stack<>();

		public za.ac.sun.cs.green.expr.Expression getTranslation() {
			return stack.pop();
		}

		// ----------------------------------------------------------------------
		// VISITOR ROUTINES
		// ----------------------------------------------------------------------

		@Override
		public void postVisit(IntegerConstant c) {
			long val = c.getValue();
			stack.push(new IntConstant((int) val));
		}

		@Override
		public void postVisit(RealConstant c) {
			double val = c.getValue();
			stack.push(new za.ac.sun.cs.green.expr.RealConstant(val));
		}

		@Override
		public void postVisit(IntegerVariable v) {
			stack.push(new IntVariable(v.getName(), (int) v.getLowerBound(), (int) v.getUpperBound()));
		}

		@Override
		public void postVisit(RealVariable v) {
			stack.push(new za.ac.sun.cs.green.expr.RealVariable(v.getName(), v.getLowerBound(), v.getUpperBound(), 64));
		}

		@Override
		public void postVisit(Operation operation) throws TranslatorUnsupportedOperation {
			za.ac.sun.cs.green.expr.Expression l = null;
			za.ac.sun.cs.green.expr.Expression r = null;
			Operator op = operation.getOperator();
			int arity = op.getArity();
			if (arity == 2) {
				if (!stack.isEmpty()) {
					r = stack.pop();
				}
				if (!stack.isEmpty()) {
					l = stack.pop();
				}
				switch (op) {
				case OR:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.OR, l, r));
					break;
				case AND:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.AND, l,
							r));
					break;
				case EQ:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.EQ, l, r));
					break;
				case NE:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.NE, l, r));
					break;
				case LT:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.LT, l, r));
					break;
				case LE:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.LE, l, r));
					break;
				case GT:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.GT, l, r));
					break;
				case GE:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.GE, l, r));
					break;
				case ADD:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.ADD, l,
							r));
					break;
				case SUB:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.SUB, l,
							r));
					break;
				case MUL:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.MUL, l,
							r));
					break;
				case DIV:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.DIV, l,
							r));
					break;
				case REM:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.MOD, l,
							r));
					break;
				case BITOR:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.BIT_OR,
							l, r));
					break;
				case BITAND:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.BIT_AND,
							l, r));
					break;
				case BITXOR:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.BIT_XOR,
							l, r));
					break;
				case SHL:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.SHIFTL,
							l, r));
					break;
				case ASHR:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.SHIFTR,
							l, r));
					break;
				case LSHR:
					stack.push(new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.SHIFTUR,
							l, r));
					break;
				case LCMP:
				case FCMPL:
				case DCMPL:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.LT, l, r));
					break;
				case FCMPG:
				case DCMPG:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.GT, l, r));
					break;
				default:
					assert false;
					break;
				}
			} else if (arity == 1) {
				if (!stack.isEmpty()) {
					l = stack.pop();
				}
				switch (op) {
				case NOT:
					stack.push(
							new za.ac.sun.cs.green.expr.Operation(za.ac.sun.cs.green.expr.Operation.Operator.NOT, l));
					break;
				case B2I:
				case S2I:
				case I2L:
				case I2C:
				case I2S:
				case I2B:
				case F2D:
					stack.push(l);
					break;
				default:
					assert false;
					break;
				}
			}
		}

	}

	@SuppressWarnings("serial")
	private static class TranslatorUnsupportedOperation extends VisitorException {

		TranslatorUnsupportedOperation(String message) {
			super(message);
		}

	}

	// ======================================================================
	//
	// MODEL PARSING
	//
	// ======================================================================

	private Input retrieveModel(Map<IntVariable, IntConstant> greenModel) {
		Input model = new Input();
		for (IntVariable variable : greenModel.keySet()) {
			String name = variable.getName();
			Long value = Long.valueOf(greenModel.get(variable).getValue());
			model.put(name, value);
		}
		return model;
	}

//	private Input retrieveModel(Map<IntVariable, Integer> greenModel) {
//		Input model = new Input();
//		for (IntVariable variable : greenModel.keySet()) {
//			String name = variable.getName();
//			Long value = Long.valueOf(greenModel.get(variable).intValue());
//			model.put(name, value);
//		}
//		return model;
//	}

}
