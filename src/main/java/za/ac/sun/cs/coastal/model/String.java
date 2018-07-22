package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.symbolic.SymbolicVM;
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
		int thisAddress = intConstantValue(SymbolicVM.pop());
		SymbolicVM.push(SymbolicVM.getStringLength(thisAddress));
		return true;
	}

	public boolean charAt__I__C() {
		Expression index = SymbolicVM.pop();
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		Expression guard = Operation.apply(Operator.AND, Operation.apply(Operator.GE, index, Operation.ZERO),
				Operation.apply(Operator.LT, index, new IntConstant(thisLength)));
		if (index instanceof IntConstant) {
			SymbolicVM.push(SymbolicVM.getStringChar(thisAddress, ((IntConstant) index).getValue()));
		} else {
			Expression var = new IntVariable(SymbolicVM.getNewVariableName(), 0, 1);
			for (int i = 0; i < thisLength; i++) {
				Expression eq = Operation.apply(Operator.AND, Operation.apply(Operator.EQ, index, new IntConstant(i)),
						Operation.apply(Operator.EQ, var, SymbolicVM.getStringChar(thisAddress, i)));
				guard = Operation.apply(Operator.AND, guard, eq);
			}
			SymbolicVM.push(var);
		}
		SymbolicVM.pushExtraConjunct(guard);
		return true;
	}

	public boolean startsWith__Ljava_1lang_1String_2__Z() {
		int prefixAddress = intConstantValue(SymbolicVM.pop());
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int prefixLength = intConstantValue(SymbolicVM.getStringLength(prefixAddress));
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = SymbolicVM.getStringChar(prefixAddress, i);
				Expression thisChar = SymbolicVM.getStringChar(thisAddress, i);
				Expression eq = Operation.apply(Operator.EQ, prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.apply(Operator.AND, guard, eq);
				}
			}
			if (guard == null) {
				SymbolicVM.push(Operation.ONE); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntVariable(SymbolicVM.getNewVariableName(), 0, 1);
				Expression pc = Operation.apply(Operator.OR,
						Operation.apply(Operator.AND, guard, Operation.apply(Operator.EQ, var, Operation.ONE)),
						Operation.apply(Operator.AND, Operation.apply(Operator.NOT, guard),
								Operation.apply(Operator.EQ, var, Operation.ZERO)));
				SymbolicVM.pushExtraConjunct(pc);
				SymbolicVM.push(var);
			}
		} else {
			SymbolicVM.push(Operation.ZERO); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean endsWith__Ljava_1lang_1String_2__Z() {
		int prefixAddress = intConstantValue(SymbolicVM.pop());
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int prefixLength = intConstantValue(SymbolicVM.getStringLength(prefixAddress));
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = SymbolicVM.getStringChar(prefixAddress, i);
				Expression thisChar = SymbolicVM.getStringChar(thisAddress, i + thisLength - prefixLength);
				Expression eq = Operation.apply(Operator.EQ, prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.apply(Operator.AND, guard, eq);
				}
			}
			if (guard == null) {
				SymbolicVM.push(Operation.ONE); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntVariable(SymbolicVM.getNewVariableName(), 0, 1);
				Expression pc = Operation.apply(Operator.OR,
						Operation.apply(Operator.AND, guard, Operation.apply(Operator.EQ, var, Operation.ONE)),
						Operation.apply(Operator.AND, Operation.apply(Operator.NOT, guard),
								Operation.apply(Operator.EQ, var, Operation.ZERO)));
				SymbolicVM.pushExtraConjunct(pc);
				SymbolicVM.push(var);
			}
		} else {
			SymbolicVM.push(Operation.ZERO); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	private int intConstantValue(Expression expr) {
		assert expr instanceof IntConstant;
		return ((IntConstant) expr).getValue();
	}

}
