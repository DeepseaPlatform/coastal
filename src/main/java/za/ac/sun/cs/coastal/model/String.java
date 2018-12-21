package za.ac.sun.cs.coastal.model;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * A model of some operations of {@link java.lang.String}. This implementation
 * exploits the fact that string instances enjoy a special status in Java and
 * are also treated specially by COASTAL. Wherever possible, COASTAL mirrors
 * Java string instances with much simpler array-like symbolic values.
 */
public class String {

	private static final Expression MONE = new IntegerConstant(-1, 32);

	private final int minChar;

	private final int maxChar;

	public String(COASTAL coastal, ImmutableConfiguration options) {
		minChar = (Character) coastal.getDefaultMinValue(char.class);
		maxChar = (Character) coastal.getDefaultMaxValue(char.class);
	}

	public boolean length____I(SymbolicState state) {
		int thisAddress = (int) intConstantValue(state.pop());
		state.push(state.getStringLength(thisAddress));
		return true;
	}

	public boolean charAt__I__C(SymbolicState state) {
		Expression index = state.pop();
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		Expression guard = Operation.and(Operation.ge(index, IntegerConstant.ZERO32),
				Operation.lt(index, new IntegerConstant(thisLength, 32)));
		if (index instanceof IntegerConstant) {
			state.push(state.getStringChar(thisAddress, (int) ((IntegerConstant) index).getValue()));
		} else {
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, minChar, maxChar);
			Expression subguard = null;
			for (int i = 0; i < thisLength; i++) {
				Expression eq = Operation.and(Operation.eq(index, new IntegerConstant(i, 32)),
						Operation.eq(var, state.getStringChar(thisAddress, i)));
				if (subguard == null) {
					subguard = eq;
				} else {
					subguard = Operation.or(subguard, eq);
				}
			}
			guard = Operation.and(guard, subguard);
			state.push(var);
		}
		state.pushExtraConjunct(guard);
		return true;
	}

	public boolean indexOf__C__I(SymbolicState state) {
		Expression chr = state.pop();
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength == 0) {
			state.push(MONE);
		} else {
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, -1, thisLength - 1);
			state.pushExtraConjunct(firstOccurrenceGuard(state, thisAddress, chr, var, 0, thisLength));
			state.push(var);
		}
		return true;
	}

	public boolean indexOf__CI__I(SymbolicState state) {
		Expression expr = state.pop();
		Expression chr = state.pop();
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength == 0) {
			state.push(MONE);
		} else if (expr instanceof IntegerConstant) {
			int ofs = (int) intConstantValue(expr);
			if (ofs >= thisLength) {
				state.push(MONE);
			} else {
				Expression var = new IntegerVariable(state.getNewVariableName(), 32, -1, thisLength - 1);
				state.pushExtraConjunct(firstOccurrenceGuard(state, thisAddress, chr, var, ofs, thisLength));
				state.push(var);
			}
		} else {
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, -1, thisLength - 1);
			Expression pc = null;
			for (int i = 0; i < thisLength; i++) {
				Expression guard = Operation.eq(expr, new IntegerConstant(i, 32));
				guard = Operation.and(guard, firstOccurrenceGuard(state, thisAddress, chr, var, i, thisLength));
				if (pc == null) {
					pc = guard;
				} else {
					pc = Operation.or(pc, guard);
				}
			}
			state.pushExtraConjunct(pc);
			state.push(var);
		}
		return true;
	}

	// Encode the constraint that the first occurrence of character "chr" in string "strAddresss"
	// occurs at position "rval".  The string has fixed concrete length "len" and the first occurrence
	// is at least "ofs".
	private Expression firstOccurrenceGuard(SymbolicState state, int strAddress, Expression chr, Expression rval,
			int ofs, int len) {
		assert ofs < len;
		Expression thisChar = state.getStringChar(strAddress, ofs);
		Expression foundAtOfs = Operation.eq(thisChar, chr);
		Expression returnValue = Operation.eq(rval, new IntegerConstant(ofs, 32));
		Expression guard = Operation.and(foundAtOfs, returnValue);
		Expression mismatch = null;
		for (int o = ofs + 1; o < len; o++) {
			if (mismatch == null) {
				mismatch = Operation.not(foundAtOfs);
			} else {
				mismatch = Operation.and(mismatch, Operation.not(foundAtOfs));
			}
			thisChar = state.getStringChar(strAddress, o);
			foundAtOfs = Operation.and(mismatch, Operation.eq(thisChar, chr));
			returnValue = Operation.eq(rval, new IntegerConstant(o, 32));
			guard = Operation.or(guard, Operation.and(foundAtOfs, returnValue));
		}
		if (mismatch == null) {
			mismatch = Operation.not(foundAtOfs);
		} else {
			mismatch = Operation.and(mismatch, Operation.not(foundAtOfs));
		}
		mismatch = Operation.and(mismatch, Operation.eq(rval, MONE));
		return Operation.or(mismatch, guard);
	}

	public boolean startsWith__Ljava_1lang_1String_2__Z(SymbolicState state) {
		int prefixAddress = (int) intConstantValue(state.pop());
		int thisAddress = (int) intConstantValue(state.pop());
		int prefixLength = (int) intConstantValue(state.getStringLength(prefixAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = state.getStringChar(prefixAddress, i);
				Expression thisChar = state.getStringChar(thisAddress, i);
				Expression eq = Operation.eq(prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.and(guard, eq);
				}
			}
			if (guard == null) {
				state.push(IntegerConstant.ONE32); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
				Expression pc = Operation.or(Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32)),
						Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32)));
				state.pushExtraConjunct(pc);
				state.push(var);
			}
		} else {
			state.push(IntegerConstant.ZERO32); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean startsWith__Ljava_1lang_1String_2I__Z(SymbolicState state) {
		Expression offset = state.pop();
		int prefixAddress = (int) intConstantValue(state.pop());
		int thisAddress = (int) intConstantValue(state.pop());
		int prefixLength = (int) intConstantValue(state.getStringLength(prefixAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		int first = 0;
		if (offset instanceof IntegerConstant) {
			first = (int) intConstantValue(offset);
		} else {
			return false; // CANNOT (YET) HANDLE SYMBOLIC OFFSETS
		}
		if (thisLength >= first + prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = state.getStringChar(prefixAddress, i);
				Expression thisChar = state.getStringChar(thisAddress, i + first);
				Expression eq = Operation.eq(prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.and(guard, eq);
				}
			}
			if (guard == null) {
				state.push(IntegerConstant.ONE32); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
				Expression posGuard = Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32));
				Expression negGuard = Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32));
				Expression pc = Operation.or(posGuard, negGuard);
				state.pushExtraConjunct(pc);
				state.push(var);
			}
		} else {
			state.push(IntegerConstant.ZERO32); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean endsWith__Ljava_1lang_1String_2__Z(SymbolicState state) {
		int prefixAddress = (int) intConstantValue(state.pop());
		int thisAddress = (int) intConstantValue(state.pop());
		int prefixLength = (int) intConstantValue(state.getStringLength(prefixAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = state.getStringChar(prefixAddress, i);
				Expression thisChar = state.getStringChar(thisAddress, i + thisLength - prefixLength);
				Expression eq = Operation.eq(prefixChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.and(guard, eq);
				}
			}
			if (guard == null) {
				state.push(IntegerConstant.ONE32); // |prefix| == 0, so result is always TRUE (=1)
			} else {
				Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
				Expression pc = Operation.or(Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32)),
						Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32)));
				state.pushExtraConjunct(pc);
				state.push(var);
			}
		} else {
			state.push(IntegerConstant.ZERO32); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	private long intConstantValue(Expression expr) {
		assert expr instanceof IntegerConstant;
		return ((IntegerConstant) expr).getValue();
	}

}
