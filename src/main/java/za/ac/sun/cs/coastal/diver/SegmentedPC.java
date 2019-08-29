package za.ac.sun.cs.coastal.diver;

import za.ac.sun.cs.coastal.solver.Constant;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.symbolic.Branch;

/**
 * A class that implements segmented path conditions. It is called "segmented"
 * <b>not</b> because it forms part of a chain of conjuncts (or segments), but
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
 * Passive conjuncts are generated mainly by delegates.
 */
public abstract class SegmentedPC extends Branch {

	/**
	 * Additional conjuncts that additional constraints on the branch that will not
	 * be negated.
	 */
	private final Expression passiveConjunct;

	private SegmentedPC(Expression passiveConjunct) {
		this.passiveConjunct = passiveConjunct;
	}

	/**
	 * Return the passive conjunct.
	 * 
	 * @return passive conjunct
	 */
	protected final Expression getPassiveConjunct() {
		return passiveConjunct;
	}

	/**
	 * Check whether or not the given expression is constant (= consists only of
	 * constant expressions and operators).
	 * 
	 * @param expression
	 *                   the input expression to check
	 * @return {@code true} if and only if the expression is a constant
	 */
	public static final boolean isConstant(Expression expression) {
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

	/**
	 * Return an expression for the active conjunct (in its positive form). This
	 * excludes the passive conjunct; callers are only interested in how decisions
	 * are made at this branching point.
	 * 
	 * @return expression for the active conjunct
	 */
	public abstract Expression getExpression();

	// ======================================================================
	//
	// BINARY CHOICES
	//
	// ======================================================================

	/**
	 * Representation of a binary branch.
	 */
	public static final class Binary extends SegmentedPC {

		/**
		 * The active conjunct that represents the binary branch.
		 */
		private final Expression positiveConjunct;

		/**
		 * The negative form of the active conjunct.
		 */
		private final Expression negativeConjunct;

		/**
		 * The positive forms of the active and passive conjuncts.
		 */
		private final Expression positiveCondition;

		/**
		 * The negative forms of the active and passive conjuncts.
		 */
		private final Expression negativeCondition;

		/**
		 * Construct a new binary branch.
		 * 
		 * @param activeConjunct
		 *                        the active conjunct
		 * @param passiveConjunct
		 *                        the passive conjunct
		 */
		public Binary(Expression activeConjunct, Expression passiveConjunct) {
			super(passiveConjunct);
			this.positiveConjunct = activeConjunct;
			this.negativeConjunct = negate(this.positiveConjunct);
			if (passiveConjunct == null) {
				this.positiveCondition = this.positiveConjunct;
				this.negativeCondition = this.negativeConjunct;
			} else {
				this.positiveCondition = Operation.and(this.positiveConjunct, getPassiveConjunct());
				this.negativeCondition = Operation.and(this.negativeConjunct, getPassiveConjunct());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getNumberOfAlternatives()
		 */
		@Override
		public long getNumberOfAlternatives() {
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getAlternative(int)
		 */
		@Override
		public String getAlternativeRepr(long alternative) {
			assert (alternative == 0) || (alternative == 1);
			return (alternative == 0) ? "F" : "T";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getAlternative(long alternative) {
			assert (alternative == 0) || (alternative == 1);
			return (alternative == 0) ? negativeConjunct : positiveConjunct;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getPCContribution(long alternative) {
			assert (alternative == 0) || (alternative == 1);
			return (alternative == 0) ? negativeCondition : positiveCondition;
		}

		/**
		 * Return the negation of the expression (which is assumed to be a boolean
		 * expression).
		 * 
		 * @param expression
		 *                   the expression to negate
		 * @return the negated expression
		 */
		private static Expression negate(Expression expression) {
			if (expression instanceof Operation) {
				Operation operation = (Operation) expression;
				switch (operation.getOperator()) {
				case NOT:
					return operation.getOperand(0);
				case EQ:
					return Operation.ne(operation.getOperand(0), operation.getOperand(1));
				case NE:
					return Operation.eq(operation.getOperand(0), operation.getOperand(1));
				case LT:
					return Operation.ge(operation.getOperand(0), operation.getOperand(1));
				case LE:
					return Operation.gt(operation.getOperand(0), operation.getOperand(1));
				case GT:
					return Operation.le(operation.getOperand(0), operation.getOperand(1));
				case GE:
					return Operation.lt(operation.getOperand(0), operation.getOperand(1));
				case AND:
					return Operation.or(negate(operation.getOperand(0)), negate(operation.getOperand(1)));
				case OR:
					return Operation.and(negate(operation.getOperand(0)), negate(operation.getOperand(1)));
				default:
					break;
				}
			}
			return Operation.not(expression);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.diver.SegmentedPC#getExpression()
		 */
		@Override
		public Expression getExpression() {
			return positiveConjunct;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#toString0()
		 */
		@Override
		protected String toString0() {
			StringBuilder rep = new StringBuilder();
			rep.append(getExpression().toString());
			if (getPassiveConjunct() != null) {
				rep.append('{').append(getPassiveConjunct()).append('}');
			}
			return rep.toString();
		}

	}

	// ======================================================================
	//
	// N-ARY CHOICES
	//
	// ======================================================================

	/**
	 * Representation of an n-ary branch with potential choices from min to max and
	 * one additional choice to represent values less than min or greater than max.
	 */
	public static final class Nary extends SegmentedPC {

		private final Expression expression;

		private final long min;

		private final long max;

		public Nary(Expression expression, long min, long max, Expression passiveConjunct) {
			super(passiveConjunct);
			this.expression = expression;
			this.min = min;
			this.max = max;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getNumberOfAlternatives()
		 */
		@Override
		public long getNumberOfAlternatives() {
			return (max - min + 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getAlternative(int)
		 */
		@Override
		public String getAlternativeRepr(long alternative) {
			return ((alternative < 0) || (alternative >= max - min + 1)) ? "DFLT" : Long.toString(alternative + min);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getAlternative(long alternative) {
			if ((alternative < 0) || (alternative >= max - min + 1)) {
				Expression lo = Operation.lt(expression, new IntegerConstant(min, 32));
				Expression hi = Operation.gt(expression, new IntegerConstant(max, 32));
				return Operation.or(lo, hi);
			} else {
				return Operation.eq(expression, new IntegerConstant(alternative + min, 32));
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getPCContribution(long alternative) {
			Expression condition = getAlternative(alternative);
			if (getPassiveConjunct() == null) {
				return condition;
			} else {
				return Operation.and(condition, this.getPassiveConjunct());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.diver.SegmentedPC#getExpression()
		 */
		@Override
		public Expression getExpression() {
			return expression;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#toString0()
		 */
		@Override
		protected String toString0() {
			return getExpression().toString();
		}

	}

	// ======================================================================
	//
	// K-ARY CHOICES
	//
	// ======================================================================

	/**
	 * Representation of a k-ary branch with potential choices that are listed
	 * explicitly.
	 */
	public static final class Kary extends SegmentedPC {

		private final Expression expression;

		private final int[] keys;

		public Kary(Expression expression, int[] keys, Expression passiveConjunct) {
			super(passiveConjunct);
			this.expression = expression;
			this.keys = keys;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getNumberOfAlternatives()
		 */
		@Override
		public long getNumberOfAlternatives() {
			return (keys.length + 1);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getAlternative(int)
		 */
		@Override
		public String getAlternativeRepr(long alternative) {
			return ((alternative < 0) || (alternative >= keys.length)) ? "DFLT" : Integer.toString(keys[(int) alternative]);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getAlternative(long alternative) {
			if ((alternative < 0) || (alternative >= keys.length)) {
				Expression alt = null;
				for (int i = 0; i < keys.length; i++) {
					Expression choice = Operation.ne(expression, new IntegerConstant(keys[i], 32));
					if (i == 0) {
						alt = choice;
					} else {
						alt = Operation.or(choice, alt);
					}
				}
				return alt;
			} else {
				return Operation.eq(expression, new IntegerConstant(keys[(int) alternative], 32));
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getPCContribution(int)
		 */
		@Override
		public Expression getPCContribution(long alternative) {
			Expression condition = getAlternative(alternative);
			if (getPassiveConjunct() == null) {
				return condition;
			} else {
				return Operation.and(condition, this.getPassiveConjunct());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.diver.SegmentedPC#getExpression()
		 */
		@Override
		public Expression getExpression() {
			return expression;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#toString0()
		 */
		@Override
		protected String toString0() {
			return getExpression().toString();
		}

	}

}
