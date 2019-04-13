package za.ac.sun.cs.coastal.solver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Operation extends Expression {

	public static final Expression FALSE = Operation.eq(IntegerConstant.ZERO32, IntegerConstant.ONE32);

	public static final Expression TRUE = Operation.eq(IntegerConstant.ZERO32, IntegerConstant.ZERO32);

	protected final Operator operator;

	protected final Expression[] operands;

	public Operation(final Operator operator, Expression... operands) {
		this.operator = operator;
		this.operands = operands;
	}

	public Operator getOperator() {
		return operator;
	}

	public int getOperatandCount() {
		return operands.length;
	}

	public Iterable<Expression> getOperands() {
		return new Iterable<Expression>() {
			@Override
			public Iterator<Expression> iterator() {
				return new Iterator<Expression>() {
					private int index = 0;

					@Override
					public boolean hasNext() {
						return index < operands.length;
					}

					@Override
					public Expression next() {
						if (index < operands.length) {
							return operands[index++];
						} else {
							throw new NoSuchElementException();
						}
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	public Expression getOperand(int index) {
		if ((index < 0) || (index >= operands.length)) {
			return null;
		} else {
			return operands[index];
		}
	}

	// ======================================================================
	//
	// VISITOR
	//
	// ======================================================================

	@Override
	public void accept(Visitor visitor) throws VisitorException {
		visitor.preVisit(this);
		for (Expression operand : operands) {
			operand.accept(visitor);
		}
		visitor.postVisit(this);
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	@Override
	public String toString0() {
		StringBuilder sb = new StringBuilder();
		int arity = operator.getArity();
		Fix fix = operator.getFix();
		if (arity == 2 && fix == Fix.INFIX) {
			if ((operands[0] instanceof Constant) || (operands[0] instanceof Variable)) {
				sb.append(operands[0].toString());
			} else {
				sb.append('(');
				sb.append(operands[0].toString());
				sb.append(')');
			}
			sb.append(operator.toString());
			if ((operands[1] instanceof Constant) || (operands[1] instanceof Variable)) {
				sb.append(operands[1].toString());
			} else {
				sb.append('(');
				sb.append(operands[1].toString());
				sb.append(')');
			}
		} else if (arity == 1 && fix == Fix.INFIX) {
			sb.append(operator.toString());
			if ((operands[0] instanceof Constant) || (operands[0] instanceof Variable)) {
				sb.append(operands[0].toString());
			} else {
				sb.append('(');
				sb.append(operands[0].toString());
				sb.append(')');
			}
		} else if (fix == Fix.POSTFIX) {
			sb.append(operands[0].toString());
			sb.append('.');
			sb.append(operator.toString());
			sb.append('(');
			if (operands.length > 1) {
				sb.append(operands[1].toString());
				for (int i = 2; i < operands.length; i++) {
					sb.append(',');
					sb.append(operands[i].toString());
				}
			}
			sb.append(')');
		} else if (operands.length > 0) {
			sb.append(operator.toString());
			sb.append('(');
			sb.append(operands[0].toString());
			for (int i = 1; i < operands.length; i++) {
				sb.append(',');
				sb.append(operands[i].toString());
			}
			sb.append(')');
		} else {
			sb.append(operator.toString());
		}
		return sb.toString();
	}

	// ======================================================================
	//
	// UTILITY CONSTRUCTORS
	//
	// ======================================================================

	public static Expression or(Expression a, Expression b) {
		return new Operation(Operator.OR, a, b);
	}

	public static Expression and(Expression a, Expression b) {
		return new Operation(Operator.AND, a, b);
	}

	public static Expression not(Expression a) {
		return new Operation(Operator.NOT, a);
	}

	public static Expression eq(Expression a, Expression b) {
		return new Operation(Operator.EQ, a, b);
	}

	public static Expression ne(Expression a, Expression b) {
		return new Operation(Operator.NE, a, b);
	}

	public static Expression lt(Expression a, Expression b) {
		return new Operation(Operator.LT, a, b);
	}

	public static Expression le(Expression a, Expression b) {
		return new Operation(Operator.LE, a, b);
	}

	public static Expression gt(Expression a, Expression b) {
		return new Operation(Operator.GT, a, b);
	}

	public static Expression ge(Expression a, Expression b) {
		return new Operation(Operator.GE, a, b);
	}

	public static Expression lcmp(Expression a, Expression b) {
		return new Operation(Operator.LCMP, a, b);
	}

	public static Expression fcmpl(Expression a, Expression b) {
		return new Operation(Operator.FCMPL, a, b);
	}

	public static Expression fcmpg(Expression a, Expression b) {
		return new Operation(Operator.FCMPG, a, b);
	}

	public static Expression dcmpl(Expression a, Expression b) {
		return new Operation(Operator.DCMPL, a, b);
	}

	public static Expression dcmpg(Expression a, Expression b) {
		return new Operation(Operator.DCMPG, a, b);
	}
	
	public static Expression b2i(Expression a) {
		return new Operation(Operator.B2I, a);
	}
	
	public static Expression s2i(Expression a) {
		return new Operation(Operator.S2I, a);
	}
	
	public static Expression i2l(Expression a) {
		return new Operation(Operator.I2L, a);
	}
	
	public static Expression i2s(Expression a) {
		return new Operation(Operator.I2S, a);
	}
	
	public static Expression i2c(Expression a) {
		return new Operation(Operator.I2C, a);
	}
	
	public static Expression i2b(Expression a) {
		return new Operation(Operator.I2B, a);
	}
	
	public static Expression f2d(Expression a) {
		return new Operation(Operator.F2D, a);
	}

	public static Expression add(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() + ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((IntegerConstant) a).getValue() + ((RealConstant) b).getValue());
			}
		} else if (a instanceof RealConstant) {
			if (b instanceof IntegerConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((RealConstant) a).getValue() + ((IntegerConstant) b).getValue());
			} else if (b instanceof RealConstant) {
				int z = Math.max(((RealConstant) a).getSize(), ((RealConstant) b).getSize());
				return new RealConstant(((RealConstant) a).getValue() + ((RealConstant) b).getValue(), z);
			}
		}
		return new Operation(Operator.ADD, a, b);
	}

	public static Expression sub(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() - ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((IntegerConstant) a).getValue() - ((RealConstant) b).getValue());
			}
		} else if (a instanceof RealConstant) {
			if (b instanceof IntegerConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((RealConstant) a).getValue() - ((IntegerConstant) b).getValue());
			} else if (b instanceof RealConstant) {
				int z = Math.max(((RealConstant) a).getSize(), ((RealConstant) b).getSize());
				return new RealConstant(((RealConstant) a).getValue() - ((RealConstant) b).getValue(), z);
			}
		}
		return new Operation(Operator.SUB, a, b);
	}

	public static Expression mul(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() * ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((IntegerConstant) a).getValue() * ((RealConstant) b).getValue());
			}
		} else if (a instanceof RealConstant) {
			if (b instanceof IntegerConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((RealConstant) a).getValue() * ((IntegerConstant) b).getValue());
			} else if (b instanceof RealConstant) {
				int z = Math.max(((RealConstant) a).getSize(), ((RealConstant) b).getSize());
				return new RealConstant(((RealConstant) a).getValue() * ((RealConstant) b).getValue(), z);
			}
		}
		return new Operation(Operator.MUL, a, b);
	}

	public static Expression div(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				if (((IntegerConstant) b).getValue() == 0) {
					return null;
				}
				return new IntegerConstant(((IntegerConstant) a).getValue() / ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((IntegerConstant) a).getValue() / ((RealConstant) b).getValue());
			}
		} else if (a instanceof RealConstant) {
			if (b instanceof IntegerConstant) {
				// Should not happen
				assert false;
				// return new RealConstant(((RealConstant) a).getValue() / ((IntegerConstant) b).getValue());
			} else if (b instanceof RealConstant) {
				int z = Math.max(((RealConstant) a).getSize(), ((RealConstant) b).getSize());
				if (((RealConstant) b).getValue() == 0.0) {
					return null;
				} 
				return new RealConstant(((RealConstant) a).getValue() / ((RealConstant) b).getValue(), z);
			}
		}
		return new Operation(Operator.DIV, a, b);
	}
	
	public static Expression rem(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				if (((IntegerConstant) b).getValue() == 0) {
					return null;
				}
				return new IntegerConstant(((IntegerConstant) a).getValue() % ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant) {
			if (b instanceof IntegerConstant) {
				//should not happen
				assert false;
			} else if (b instanceof RealConstant) {
				int z = Math.max(((RealConstant) a).getSize(), ((RealConstant) b).getSize());
				if (((RealConstant) b).getValue() == 0.0) {
					return null;
				} 
				return new RealConstant(((RealConstant) a).getValue() % ((RealConstant) b).getValue(), z);
			}
		}
		return new Operation(Operator.REM, a, b);
	}
	
	public static Expression bitor(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() | ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.BITOR, a, b);
	}
	
	public static Expression bitand(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() & ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.BITAND, a, b);
	}
	
	public static Expression bitxor(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = Math.max(((IntegerConstant) a).getSize(), ((IntegerConstant) b).getSize());
				return new IntegerConstant(((IntegerConstant) a).getValue() ^ ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.BITXOR, a, b);
	}
	
	public static Expression shl(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = ((IntegerConstant) a).getSize();
				return new IntegerConstant(((IntegerConstant) a).getValue() << ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.SHL, a, b);
	}
	
	public static Expression ashr(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = ((IntegerConstant) a).getSize();
				return new IntegerConstant(((IntegerConstant) a).getValue() >> ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.ASHR, a, b);
	}
	
	public static Expression lshr(Expression a, Expression b) {
		if (a instanceof IntegerConstant) {
			if (b instanceof IntegerConstant) {
				int z = ((IntegerConstant) a).getSize();
				return new IntegerConstant(((IntegerConstant) a).getValue() >>> ((IntegerConstant) b).getValue(), z);
			} else if (b instanceof RealConstant || b instanceof RealVariable) {
				//should not happen
				assert false;
			}
		} else if (a instanceof RealConstant || b instanceof RealConstant) {
			//bitwise operators do not work on real values
			assert false;
		} else if (a instanceof RealVariable || b instanceof RealVariable) {
			assert false;
		}
		return new Operation(Operator.LSHR, a, b);
	}

	// ======================================================================
	//
	// OPERATORS
	//
	// ======================================================================

	public enum Fix {
		PREFIX, INFIX, POSTFIX;
	}

	public enum Operator {

		// @formatter:off
		// ----Logical operators ----
		OR("||", 2, Fix.INFIX, "or", "or"),
		AND("&&", 2, Fix.INFIX, "and", "and"),
		NOT("!", 1, Fix.PREFIX, "not", "not"),
		// ---- Relational operators ----
		EQ("==", 2, Fix.INFIX, "=", "fp.eq"),
		NE("!=", 2, Fix.INFIX, null, null),
		LT("<", 2, Fix.INFIX, "bvslt", "fp.lt"),
		LE("<=", 2, Fix.INFIX, "bvsle", "fp.leq"),
		GT(">", 2, Fix.INFIX, "bvsgt", "fp.gt"),
		GE(">=", 2, Fix.INFIX, "bvsge", "fp.geq"),
		// ---- Special relational operators ----
		LCMP("LCMP", 2, Fix.PREFIX, null, null),
		FCMPL("FCMPL", 2, Fix.PREFIX, null, null),
		FCMPG("FCMPG", 2, Fix.PREFIX, null, null),
		DCMPL("DCMPL", 2, Fix.PREFIX, null, null),
		DCMPG("DCMPG", 2, Fix.PREFIX, null, null),
		// ---- Conversion operators ----
		B2I("B2I", 1, Fix.PREFIX, null, null),
		S2I("S2I", 1, Fix.PREFIX, null, null),
		I2L("I2L", 1, Fix.PREFIX, null, null),
		I2S("I2C", 1, Fix.PREFIX, null, null),
		I2C("I2S", 1, Fix.PREFIX, null, null),
		I2B("I2B", 1, Fix.PREFIX, null, null),
		F2D("F2D", 1, Fix.PREFIX, null, null),
		// ---- Arithmetic operators ----
		ADD("+", 2, Fix.INFIX, "bvadd", "fp.add RNE"),
		SUB("-", 2, Fix.INFIX, "bvsub", "fp.sub RNE"),
		MUL("*", 2, Fix.INFIX, "bvmul", "fp.mul RNE"),
		DIV("/", 2, Fix.INFIX, "bvsdiv", "fp.div RNE"),
		REM("%", 2, Fix.INFIX, "bvsrem", "fp.rem RNE"),
		// ---- Bitwise operators ----
		BITOR("|", 2, Fix.INFIX, "bvor", null),
		BITAND("&", 2, Fix.INFIX, "bvand", null),
		BITXOR("^", 2, Fix.INFIX, "bvxor", null),
		SHL("<<", 2, Fix.INFIX, "bvshl", null),
		ASHR(">>", 2, Fix.INFIX, "bvashr", null),
		LSHR(">>>", 2, Fix.INFIX, "bvlshr", null);
		// @formatter:on

		private final String string;

		private final int maxArity;

		private final Fix fix;

		private final String bvop;

		private final String fpop;

		Operator(final String string, final int maxArity, final Fix fix, final String bvop, final String fpop) {
			this.string = string;
			this.maxArity = maxArity;
			this.fix = fix;
			this.bvop = bvop;
			this.fpop = fpop;
		}

		public int getArity() {
			return maxArity;
		}

		public Fix getFix() {
			return fix;
		}

		public String getBVOp() {
			return bvop;
		}

		public String getFPOp() {
			return fpop;
		}

		@Override
		public final String toString() {
			return string;
		}

	}

}
