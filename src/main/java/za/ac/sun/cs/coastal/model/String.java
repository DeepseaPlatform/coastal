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

	private static final Expression MONE = new IntConstant(-1);

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

	public boolean indexOf__C__I() {
		Expression chr = SymbolicVM.pop();
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		if (thisLength == 0) {
			SymbolicVM.push(MONE);
		} else {
			Expression var = new IntVariable(SymbolicVM.getNewVariableName(), -1, thisLength - 1);
			SymbolicVM.pushExtraConjunct(firstOccurrenceGuard(thisAddress, chr, var, 0, thisLength));
			SymbolicVM.push(var);
		}
		return true;
	}
	
	public boolean indexOf__CI__I() {
		Expression expr = SymbolicVM.pop();
		Expression chr = SymbolicVM.pop();
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		if (thisLength == 0) {
			SymbolicVM.push(MONE);
		} else if (expr instanceof IntConstant) {
			int ofs = intConstantValue(expr);
			if (ofs >= thisLength) {
				SymbolicVM.push(MONE);
			} else {
				Expression var = new IntVariable(SymbolicVM.getNewVariableName(), -1, thisLength - 1);
				SymbolicVM.pushExtraConjunct(firstOccurrenceGuard(thisAddress, chr, var, ofs, thisLength));
				SymbolicVM.push(var);
			}
		} else {
			Expression var = new IntVariable(SymbolicVM.getNewVariableName(), -1, thisLength - 1);
			Expression pc = null;
			for (int i = 0; i < thisLength; i++) {
				Expression guard = Operation.apply(Operator.EQ, expr, new IntConstant(i));
				guard = Operation.apply(Operator.AND, guard, firstOccurrenceGuard(thisAddress, chr, var, i, thisLength));
				if (pc == null) {
					pc = guard;
				} else {
					pc = Operation.apply(Operator.OR, pc, guard);
				}
			}
			SymbolicVM.pushExtraConjunct(pc);
			SymbolicVM.push(var);
		}
		return true;
	}

	// Encode the constraint that the first occurrence of character "chr" in string "strAddresss"
	// occurs at position "rval".  The string has fixed concrete length "len" and the first occurrence
	// is at least "ofs".
	private Expression firstOccurrenceGuard(int strAddress, Expression chr, Expression rval, int ofs, int len) {
		assert ofs < len;
		Expression thisChar = SymbolicVM.getStringChar(strAddress, ofs);
		Expression foundAtOfs = Operation.apply(Operator.EQ, thisChar, chr);
		Expression returnValue = Operation.apply(Operator.EQ, rval, new IntConstant(ofs));
		Expression guard = Operation.apply(Operator.AND, foundAtOfs, returnValue);
		Expression mismatch = null;
		for (int o = ofs + 1; o < len; o++) {
			if (mismatch == null) {
				mismatch = Operation.apply(Operator.NOT, foundAtOfs);
			} else {
				mismatch = Operation.apply(Operator.AND, mismatch, Operation.apply(Operator.NOT, foundAtOfs));
			}
			thisChar = SymbolicVM.getStringChar(strAddress, o);
			foundAtOfs = Operation.apply(Operator.AND, mismatch, Operation.apply(Operator.EQ, thisChar, chr));
			returnValue = Operation.apply(Operator.EQ, rval, new IntConstant(o));
			guard = Operation.apply(Operator.OR, guard, Operation.apply(Operator.AND, foundAtOfs, returnValue));
		}
		if (mismatch == null) {
			mismatch = Operation.apply(Operator.NOT, foundAtOfs);
		} else {
			mismatch = Operation.apply(Operator.AND, mismatch, Operation.apply(Operator.NOT, foundAtOfs));
		}
		mismatch = Operation.apply(Operator.AND, mismatch, Operation.apply(Operator.EQ, rval, MONE));
		return Operation.apply(Operator.OR, mismatch, guard);
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

	public boolean startsWith__Ljava_1lang_1String_2I__Z() {
		Expression offset = SymbolicVM.pop();
		int prefixAddress = intConstantValue(SymbolicVM.pop());
		int thisAddress = intConstantValue(SymbolicVM.pop());
		int prefixLength = intConstantValue(SymbolicVM.getStringLength(prefixAddress));
		int thisLength = intConstantValue(SymbolicVM.getStringLength(thisAddress));
		int first = 0;
		if (offset instanceof IntConstant) {
			first = intConstantValue(offset);
		} else {
			return false; // CANNOT (YET) HANDLE SYMBOLIC OFFSETS
		}
		if (thisLength >= first + prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = SymbolicVM.getStringChar(prefixAddress, i);
				Expression thisChar = SymbolicVM.getStringChar(thisAddress, i + first);
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
				Expression posGuard = Operation.apply(Operator.AND,
						guard,
						Operation.apply(Operator.EQ, var, Operation.ONE)); 
				Expression negGuard = Operation.apply(Operator.AND,
						Operation.apply(Operator.NOT, guard),
						Operation.apply(Operator.EQ, var, Operation.ZERO)); 
				Expression pc = Operation.apply(Operator.OR, posGuard, negGuard);
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
