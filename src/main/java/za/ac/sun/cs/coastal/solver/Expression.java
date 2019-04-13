package za.ac.sun.cs.coastal.solver;

import java.util.Stack;

public abstract class Expression implements Comparable<Expression> {

	protected String stringRepr = null;

	public abstract void accept(Visitor visitor) throws VisitorException;

	@Override
	public final int compareTo(Expression expression) {
		return toString().compareTo(expression.toString());
	}

	@Override
	public final boolean equals(Object object) {
		return (object != null) && (object.getClass() == this.getClass()) && toString().equals(object.toString());
	}

	@Override
	public final int hashCode() {
		return toString().hashCode();
	}

	@Override
	public final String toString() {
		if (stringRepr == null) {
			stringRepr = toString0();
		}
		return stringRepr;
	}

	protected abstract String toString0();

	// ======================================================================
	//
	// SIZING
	//
	// ======================================================================

	public final int getBitSize() {
		SizeVisitor sv = new SizeVisitor();
		try {
			accept(sv);
		} catch (VisitorException e) {
			return 0;
		}
		return sv.getSize();
	}

	private static class SizeVisitor extends Visitor {

		private final Stack<Integer> stack = new Stack<>();

		public int getSize() {
			return stack.isEmpty() ? 0 : stack.peek();
		}

		@Override
		public void postVisit(Operation operation) throws VisitorException {
			int n = operation.getOperatandCount();
			int result = 0;
			while (n-- > 0) {
				result = Math.max(result, stack.pop());
			}
			stack.push(result);
		}

		@Override
		public void postVisit(RealVariable realVariable) throws VisitorException {
			stack.push(realVariable.getSize());
		}

		@Override
		public void postVisit(IntegerVariable integerVariable) throws VisitorException {
			stack.push(integerVariable.getSize());
		}

		@Override
		public void postVisit(RealConstant realConstant) throws VisitorException {
			stack.push(realConstant.getSize());
		}

		@Override
		public void postVisit(IntegerConstant integerConstant) throws VisitorException {
			stack.push(integerConstant.getSize());
		}

	}

	// ======================================================================
	//
	// ISREAL
	//
	// ======================================================================

	public final boolean isReal() {
		RealVisitor rv = new RealVisitor();
		try {
			accept(rv);
		} catch (VisitorException e) {
			return false;
		}
		return rv.isReal();
	}

	private static class RealVisitor extends Visitor {

		private final Stack<Boolean> stack = new Stack<>();

		public boolean isReal() {
			return stack.isEmpty() ? false : stack.peek();
		}

		@Override
		public void postVisit(Operation operation) throws VisitorException {
			int n = operation.getOperatandCount();
			boolean result = true;
			while (n-- > 0) {
				result = stack.pop() ? result : false;
			}
			stack.push(result);
		}

		@Override
		public void postVisit(RealVariable realVariable) throws VisitorException {
			stack.push(true);
		}

		@Override
		public void postVisit(IntegerVariable integerVariable) throws VisitorException {
			stack.push(false);
		}

		@Override
		public void postVisit(RealConstant realConstant) throws VisitorException {
			stack.push(true);
		}

		@Override
		public void postVisit(IntegerConstant integerConstant) throws VisitorException {
			stack.push(false);
		}

	}

}
