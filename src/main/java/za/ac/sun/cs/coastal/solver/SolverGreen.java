package za.ac.sun.cs.coastal.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.solver.Operation.Operator;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public class SolverGreen extends Solver {

	protected static int problemCounter = 0;

	protected final Logger log;

	protected final Green green;

	public SolverGreen(COASTAL coastal, ImmutableConfiguration config) {
		super(coastal, config);
		log = coastal.getLog();
		for (Iterator<String> it = config.getKeys(); it.hasNext();) {
			String key = it.next();
			log.info("key: {}", key);
		}
		green = new Green("COASTAL", LogManager.getLogger("GREEN"));
		Properties greenProperties = new Properties();
		config.getKeys("green.").forEachRemaining(k -> greenProperties.setProperty(k, config.getString(k)));
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
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
			return retrieveModel(model, t.getVariables());
		} catch (AssertionError x) {
			log.trace("VISITOR ASSERTION EXCEPTION", x);
		} catch (VisitorException x) {
			log.trace("VISITOR EXCEPTION", x);
		}
		return null;
	}

	// ======================================================================
	//
	// TRANSLATOR VISITOR
	//
	// ======================================================================

	private static class Translator extends Visitor {

		private final Stack<za.ac.sun.cs.green.expr.Expression> stack = new Stack<>();

		private final List<String> variableDefs = new LinkedList<>();

		private final Map<String, Variable> variables = new HashMap<>();

		private boolean addedLcmp = false;

		private boolean addedFcmpl = false, addedFcmpg = false;

		private boolean addedDcmpl = false, addedDcmpg = false;

		public Map<String, Variable> getVariables() {
			return variables;
		}

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
			stack.push(new za.ac.sun.cs.green.expr.RealVariable(v.getName(), v.getLowerBound(), v.getUpperBound()));
		}

		@Override
		public void postVisit(Operation operation) throws TranslatorUnsupportedOperation {
			za.ac.sun.cs.green.expr.Expression l = null;
			za.ac.sun.cs.green.expr.Expression r = null;
			Operator op = operation.getOperator();
			int arity = op.getArity();
			if (arity == 2) {
				Class<? extends Expression> lType = null; // rt = null;
				int lSize = 0, rSize = 0, maxSize = 0;
				if (!stack.isEmpty()) {
					r = stack.pop();
					// rt = r.getType();
					rSize = r.getSize();
				}
				if (!stack.isEmpty()) {
					l = stack.pop();
					lType = l.getType();
					lSize = l.getSize();
				}
				// checkCompatible(lt, ls, rt, rs);
				StringBuilder b = new StringBuilder();
				switch (op) {
				case EQ:
					maxSize = (lSize > rSize) ? lSize : rSize;
					b.append('(').append(setOperator(Operator.EQ, lType)).append(' ');
					if (lSize < maxSize) {
						b.append("((_ sign_extend ").append(maxSize - lSize).append(") ").append(l.getEntry())
								.append(") ");
					} else {
						b.append(l.getEntry()).append(' ');
					}
					if (rSize < maxSize) {
						b.append("((_ sign_extend ").append(maxSize - rSize).append(") ").append(r.getEntry())
								.append("))");
					} else {
						b.append(r.getEntry()).append(')');
					}
					stack.push(new StackEntry(b.toString(), lType, maxSize));
					break;
				case NE:
					maxSize = (lSize > rSize) ? lSize : rSize;
					b.append("(not (").append(setOperator(Operator.EQ, lType)).append(' ');
					if (lSize < maxSize) {
						b.append("((_ sign_extend ").append(maxSize - lSize).append(") ").append(l.getEntry())
								.append(") ");
					} else {
						b.append(l.getEntry()).append(' ');
					}
					if (rSize < maxSize) {
						b.append("((_ sign_extend ").append(maxSize - rSize).append(") ").append(r.getEntry())
								.append(")))");
					} else {
						b.append(r.getEntry()).append("))");
					}
					stack.push(new StackEntry(b.toString(), lType, maxSize));
					break;
//					b.append("(not (").append(setOperator(Operator.EQ, lType)).append(' ');
//					b.append(l.getEntry()).append(' ');
//					b.append(r.getEntry()).append("))");
//					stack.push(new StackEntry(b.toString(), lType, lSize));
//					break;
				case LCMP:
					addLcmp();
					b.append("(lcmp! ");
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 32));
					break;
				case FCMPL:
					addFcmpl();
					b.append("(fcmpl! ");
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 32));
					break;
				case FCMPG:
					addFcmpg();
					b.append("(fcmpg! ");
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 32));
					break;
				case DCMPL:
					addDcmpl();
					b.append("(dcmpl! ");
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 32));
					break;
				case DCMPG:
					addDcmpg();
					b.append("(dcmpg! ");
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 32));
					break;
				case AND:
					String lStr = l.getEntry();
					if (lStr.equals("(= #x00000000 #x00000000)")) {
						b.append(r.getEntry());
						stack.push(new StackEntry(b.toString(), lType, lSize));
					} else {
						b.append('(').append(setOperator(op, lType)).append(' ');
						b.append(lStr).append('\n');
						b.append(r.getEntry()).append(')');
						stack.push(new StackEntry(b.toString(), lType, lSize));
					}
					break;
				default:
					b.append('(').append(setOperator(op, lType)).append(' ');
					b.append(l.getEntry()).append(' ');
					b.append(r.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), lType, lSize));
					break;
				}
			} else if (arity == 1) {
				Class<? extends Expression> lt = null;
				int ls = 0;
				if (!stack.isEmpty()) {
					l = stack.pop();
					lt = l.getType();
					ls = l.getSize();
				}
				StringBuilder b = new StringBuilder();
				switch (op) {
				case B2I:
					b.append("((_ sign_extend 24) ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 64));
					break;
				case S2I:
					b.append("((_ sign_extend 16) ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 64));
					break;
				case I2L:
					b.append("((_ sign_extend 32) ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 64));
					break;
				case I2C:
				case I2S:
					b.append("((_ extract 15 0) ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 16));
					break;
				case I2B:
					b.append("((_ extract 7 0) ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), IntegerConstant.class, 8));
					break;
				case F2D:
					b.append("((_ to_fp 11 53) RNE ");
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), RealConstant.class, 64));
					break;
				default:
					b.append('(').append(setOperator(op, lt)).append(' ');
					b.append(l.getEntry()).append(')');
					stack.push(new StackEntry(b.toString(), lt, ls));
					break;
				}
			}
		}

//		private void checkCompatible(Class<? extends Expression> lt, int ls, Class<? extends Expression> rt, int rs) {
//			assert ls == rs;
//			assert ((lt == IntegerConstant.class) || (lt == IntegerVariable.class)) == ((rt == IntegerConstant.class)
//					|| (rt == IntegerVariable.class));
//		}

		private void addLcmp() {
			if (!addedLcmp) {
				StringBuilder b = new StringBuilder();
				b.append("(define-fun lcmp! ((l!l (_ BitVec 64)) (r!r (_ BitVec 64))) (_ BitVec 32) ");
				b.append("(ite (bvsgt l!l r!r) #x00000001 ");
				b.append("(ite (bvslt l!l r!r) #xffffffff #x00000000)))");
				variableDefs.add(b.toString());
				addedLcmp = true;
			}
		}

		private void addFcmpl() {
			if (!addedFcmpl) {
				StringBuilder b = new StringBuilder();
				b.append("(define-fun fcmpl! ((l!l Float32) (r!r Float32)) (_ BitVec 32) ");
				b.append("(ite (fp.gt l!l r!r) #x00000001 ");
				b.append("(ite (fp.lt l!l r!r) #xffffffff ");
				b.append("(ite (fp.eq l!l r!r) #x00000000 #xffffffff))))");
				variableDefs.add(b.toString());
				addedFcmpl = true;
			}
		}

		private void addFcmpg() {
			if (!addedFcmpg) {
				StringBuilder b = new StringBuilder();
				b.append("(define-fun fcmpg! ((l!l Float32) (r!r Float32)) (_ BitVec 32) ");
				b.append("(ite (fp.gt l!l r!r) #x00000001 ");
				b.append("(ite (fp.lt l!l r!r) #xffffffff ");
				b.append("(ite (fp.eq l!l r!r) #x00000000 #x00000001))))");
				variableDefs.add(b.toString());
				addedFcmpg = true;
			}
		}

		private void addDcmpl() {
			if (!addedDcmpl) {
				StringBuilder b = new StringBuilder();
				b.append("(define-fun dcmpl! ((l!l Float64) (r!r Float64)) (_ BitVec 32) ");
				b.append("(ite (fp.gt l!l r!r) #x00000001 ");
				b.append("(ite (fp.lt l!l r!r) #xffffffff ");
				b.append("(ite (fp.eq l!l r!r) #x00000000 #xffffffff))))");
				variableDefs.add(b.toString());
				addedDcmpl = true;
			}
		}

		private void addDcmpg() {
			if (!addedDcmpg) {
				StringBuilder b = new StringBuilder();
				b.append("(define-fun dcmpg! ((l!l Float64) (r!r Float64)) (_ BitVec 32) ");
				b.append("(ite (fp.gt l!l r!r) #x00000001 ");
				b.append("(ite (fp.lt l!l r!r) #xffffffff ");
				b.append("(ite (fp.eq l!l r!r) #x00000000 #x00000001))))");
				variableDefs.add(b.toString());
				addedDcmpg = true;
			}
		}

		private String setOperator(Operator operator, Class<? extends Expression> type)
				throws TranslatorUnsupportedOperation {
			String op = ((type == RealConstant.class) || (type == RealVariable.class)) ? operator.getFPOp()
					: operator.getBVOp();
			if (op == null) {
				throw new TranslatorUnsupportedOperation("unsupported operation " + op);
			}
			return op;
		}

		// ----------------------------------------------------------------------
		// VISITOR HELPERS
		// ----------------------------------------------------------------------

		private String literal(long v, int size) {
			if (size == 32) {
				return String.format("#x%08x", (int) v);
			} else if (size == 16) {
				return String.format("#x%04x", v);
			} else if (size == 64) {
				return String.format("#x%016x", v);
			} else {
				return "UNKNOWN SIZE";
			}
		}

		private String literal(double v, int size) {
			if (size == 32) {
				if (Double.isNaN(v)) {
					return "(_ NaN 8 24)";
				} else if (Double.isInfinite(v)) {
					if (v > 0) {
						return "(_ +oo 8 24)";
					} else {
						return "(_ -oo 8 24)";
					}
				} else {
					int bits = Float.floatToIntBits((float) v);
					int s = (bits & 0x80000000) >>> 31;
					int e = (bits & 0x7f800000) >>> 23;
					int m = (bits & 0x007fffff) >>> 0;
					if ((e != 0) || (m != 0)) {
						String ee = String.format("#b%8s", Integer.toBinaryString(e)).replace(' ', '0');
						String mm = String.format("#b%23s", Integer.toBinaryString(m)).replace(' ', '0');
						return String.format("(fp #b%s %s %s)", Integer.toBinaryString(s), ee, mm);
					} else if (s == 0) {
						return "(_ +zero 8 24)";
					} else {
						return "(_ -zero 8 24)";
					}
				}
			} else {
				if (Double.isNaN(v)) {
					return "(_ NaN 11 53)";
				} else if (Double.isInfinite(v)) {
					if (v > 0) {
						return "(_ +oo 11 53)";
					} else {
						return "(_ -oo 11 53)";
					}
				} else {
					long bits = Double.doubleToLongBits(v);
					long s = (bits & 0x8000000000000000L) >>> 63;
					long e = (bits & 0x7ff0000000000000L) >>> 52;
					long m = (bits & 0x000fffffffffffffL) >>> 0;
					if ((e != 0) || (m != 0)) {
						String ee = String.format("#b%11s", Long.toBinaryString(e)).replace(' ', '0');
						String mm = String.format("#x%013x", m);
						return String.format("(fp #b%s %s %s)", Long.toBinaryString(s), ee, mm);
					} else if (s == 0) {
						return "(_ +zero 11 53)";
					} else {
						return "(_ -zero 11 53)";
					}
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

	private Input retrieveModel(Map<IntVariable, IntConstant> greenModel, Map<String, Variable> variables) {
		Input model = new Input();
		for (Variable variable : variables.values()) {
			String name = variable.getName();
			if (assignment.containsKey(name)) {
				Number value = null;
				if (variable instanceof IntegerVariable) {
					String z = assignment.get(name);
					if (((IntegerVariable) variable).getSize() == 32) {
						int val = Integer.parseUnsignedInt(z.substring(2), z.startsWith("#b") ? 2 : 16);
						value = Long.valueOf(val);
					} else {
						long val = Long.parseUnsignedLong(z.substring(2), z.startsWith("#b") ? 2 : 16);
						value = Long.valueOf(val);
					}
				} else if (variable instanceof RealVariable) {
					// UNHANDLED: NaN, -zero, +oo, -oo
					String z = assignment.get(name);
					if (z.equals("(_ +zero 11 53)")) {
						value = Double.valueOf(+0.0);
					} else if (z.equals("(_ -zero 11 53)")) {
						value = Double.valueOf(-0.0);
					} else {
						Pattern pattern = Pattern
								.compile("^\\(fp (#[bx][0-9a-f]+) (#[bx][0-9a-f]+) (#[bx][0-9a-f]+)\\)$");
						Matcher matcher = pattern.matcher(z);
						if (matcher.matches()) {
							long s = Long.parseLong(matcher.group(1).substring(2),
									matcher.group(1).startsWith("#b") ? 2 : 16);
							long e = Long.parseLong(matcher.group(2).substring(2),
									matcher.group(2).startsWith("#b") ? 2 : 16);
							long m = Long.parseLong(matcher.group(3).substring(2),
									matcher.group(3).startsWith("#b") ? 2 : 16);
							double val = 0.0;
							if (((RealVariable) variable).getSize() == 32) {
								val = Float.intBitsToFloat((int) ((s << 31) | (e << 23) | m));
							} else {
								val = Double.longBitsToDouble((s << 63) | (e << 52) | m);
							}
							value = Double.valueOf(val);
						} else {
							value = null;
						}
					}
				}
				if (value != null) {
					model.put(name, value);
				}
			}
		}
		return model;
	}

}
