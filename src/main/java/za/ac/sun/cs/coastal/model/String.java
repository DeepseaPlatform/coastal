package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * A model of some operations of {@link java.lang.String}. This implementation
 * exploits the fact that string instances enjoy a special status in Java and
 * are also treated specially by COASTAL. Wherever possible, COASTAL mirrors
 * Java string instances with much simpler array-like symbolic values.
 */
public class String {

	public String() {
	}

	public boolean length____I() {
		int thisAddress = intConstantValue(SymbolicState.pop());
		SymbolicState.push(SymbolicState.getStringLength(thisAddress));
		return true;
	}

	public boolean charAt__I__C() {
		Expression index = SymbolicState.pop();
		int thisAddress = intConstantValue(SymbolicState.pop());
		int thisLength = intConstantValue(SymbolicState.getStringLength(thisAddress));
		Expression guard = Operation.apply(Operator.AND, Operation.apply(Operator.GE, index, Operation.ZERO),
				Operation.apply(Operator.LT, index, new IntConstant(thisLength)));
		if (index instanceof IntConstant) {
			SymbolicState.push(SymbolicState.getStringChar(thisAddress, ((IntConstant) index).getValue()));
		} else {
			Expression var = new IntVariable(SymbolicState.getNewVariableName(), 0, 1);
			for (int i = 0; i < thisLength; i++) {
				Expression eq = Operation.apply(Operator.AND, Operation.apply(Operator.EQ, index, new IntConstant(i)),
						Operation.apply(Operator.EQ, var, SymbolicState.getStringChar(thisAddress, i)));
				guard = Operation.apply(Operator.AND, guard, eq);
			}
			SymbolicState.push(var);
		}
		SymbolicState.pushExtraConjunct(guard);
		return true;
	}

	public boolean startsWith__Ljava_1lang_1String_2__Z() {
		int prefixAddress = intConstantValue(SymbolicState.pop());
		int thisAddress = intConstantValue(SymbolicState.pop());
		int prefixLength = intConstantValue(SymbolicState.getStringLength(prefixAddress));
		int thisLength = intConstantValue(SymbolicState.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = SymbolicState.getStringChar(prefixAddress, i);
				Expression thisChar = SymbolicState.getStringChar(thisAddress, i);
				Expression eq = Operation.apply(Operator.EQ, prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.apply(Operator.AND, guard, eq);
				}
			}
			if (guard == null) {
				SymbolicState.push(Operation.ONE); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntVariable(SymbolicState.getNewVariableName(), 0, 1);
				Expression pc = Operation.apply(Operator.OR,
						Operation.apply(Operator.AND, guard, Operation.apply(Operator.EQ, var, Operation.ONE)),
						Operation.apply(Operator.AND, Operation.apply(Operator.NOT, guard),
								Operation.apply(Operator.EQ, var, Operation.ZERO)));
				SymbolicState.pushExtraConjunct(pc);
				SymbolicState.push(var);
			}
		} else {
			SymbolicState.push(Operation.ZERO); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean endsWith__Ljava_1lang_1String_2__Z() {
		int prefixAddress = intConstantValue(SymbolicState.pop());
		int thisAddress = intConstantValue(SymbolicState.pop());
		int prefixLength = intConstantValue(SymbolicState.getStringLength(prefixAddress));
		int thisLength = intConstantValue(SymbolicState.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = SymbolicState.getStringChar(prefixAddress, i);
				Expression thisChar = SymbolicState.getStringChar(thisAddress, i + thisLength - prefixLength);
				Expression eq = Operation.apply(Operator.EQ, prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.apply(Operator.AND, guard, eq);
				}
			}
			if (guard == null) {
				SymbolicState.push(Operation.ONE); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntVariable(SymbolicState.getNewVariableName(), 0, 1);
				Expression pc = Operation.apply(Operator.OR,
						Operation.apply(Operator.AND, guard, Operation.apply(Operator.EQ, var, Operation.ONE)),
						Operation.apply(Operator.AND, Operation.apply(Operator.NOT, guard),
								Operation.apply(Operator.EQ, var, Operation.ZERO)));
				SymbolicState.pushExtraConjunct(pc);
				SymbolicState.push(var);
			}
		} else {
			SymbolicState.push(Operation.ZERO); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	private int intConstantValue(Expression expr) {
		assert expr instanceof IntConstant;
		return ((IntConstant) expr).getValue();
	}

}
