/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.symbolic.exceptions.COASTALException;
import za.ac.sun.cs.coastal.symbolic.exceptions.UnsupportedOperationException;

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

	private int falseStringId = -1;

	private int trueStringId = -1;

	public String(COASTAL coastal, Configuration config) {
		minChar = (java.lang.Character) coastal.getDefaultMinValue(char.class);
		maxChar = (java.lang.Character) coastal.getDefaultMaxValue(char.class);
	}

	/**
	 * Model the routine {@code String.<init>()} symbolically.
	 *
	 * @param state
	 *              reference to the current symbolic state
	 * @return {@code true} if and only if the modelling succeeded
	 */
	public boolean _init_____V(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue thiss = state.peek();
		// Check that the required parameters are constants.
		if (!thiss.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.pop();
		// Symbolic modelling
		int thisAddress = (int) intConstantValue(thiss.toExpression());
		state.setStringLength(thisAddress, IntegerConstant.ZERO32);
		return true;
	}

	/**
	 * Model the routine {@code String.<init>(String str)} symbolically.
	 *
	 * @param state
	 *              reference to the current symbolic state
	 * @return {@code true} if and only if the modelling succeeded
	 */
	public boolean _init___Ljava_1lang_1String_2__V(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue str = state.peek();
		SymbolicValue thiss = state.peek(1);
		// Check that the required parameters are constants.
		if (!str.isConstant()) {
			return false;
		}
		if (!thiss.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.multiPop(2);
		// Symbolic modelling
		int strAddress = (int) intConstantValue(str.toExpression());
		if (strAddress == 0) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, Integer.MIN_VALUE, Integer.MAX_VALUE));
		} else {
			int thisAddress = (int) intConstantValue(thiss.toExpression());
			int strLength = (int) state.getStringLength(strAddress).toValue();
			state.setStringLength(thisAddress, new IntegerConstant(strLength, 32));
			for (int i = 0; i < strLength; i++) {
				state.setStringChar(thisAddress, i, state.getStringChar(strAddress, i));
			}
		}
		return true;
	}

	/**
	 * Model the routine {@code String.<init>(char[] value)} symbolically.
	 *
	 * @param state
	 *              reference to the current symbolic state
	 * @return {@code true} if and only if the modelling succeeded
	 */
	public boolean _init____3C__V(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue value = state.peek();
		SymbolicValue thiss = state.peek(1);
		// Check that the required parameters are constants.
		if (!value.isConstant()) {
			return false;
		}
		if (!thiss.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.multiPop(2);
		// Symbolic modelling
		int valueAddress = (int) intConstantValue(value.toExpression());
		if (valueAddress == 0) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, Integer.MIN_VALUE, Integer.MAX_VALUE));
		} else {
			int thisAddress = (int) intConstantValue(thiss.toExpression());
			int valueLength = (int) state.getStringLength(valueAddress).toValue();
			state.setStringLength(thisAddress, new IntegerConstant(valueLength, 32));
			for (int i = 0; i < valueLength; i++) {
				state.setStringChar(thisAddress, i, state.getStringChar(valueAddress, i));
			}
		}
		return true;
	}

	/**
	 * Model the routine {@code String.<init>(char[] value, int offset, int count)}
	 * symbolically.
	 *
	 * @param state
	 *              reference to the current symbolic state
	 * @return {@code true} if and only if the modelling succeeded
	 */
	public boolean _init____3CII__V(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue count = state.peek();
		SymbolicValue offset = state.peek(1);
		SymbolicValue value = state.peek(2);
		SymbolicValue thiss = state.peek(3);
		// Check that the required parameters are constants.
		if (!count.isConstant()) {
			return false;
		}
		if (!offset.isConstant()) {
			return false;
		}
		if (!value.isConstant()) {
			return false;
		}
		if (!thiss.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.multiPop(4);
		// Symbolic modelling
		int countValue = (int) intConstantValue(count.toExpression());
		int offsetValue = (int) intConstantValue(offset.toExpression());
		int valueAddress = (int) intConstantValue(value.toExpression());
		if (valueAddress != 0) {
			int valueLength = (int) state.getArrayLength(valueAddress).toValue();
			if ((offsetValue < 0) || (countValue < 0) || (offsetValue + countValue > valueLength)) {
				// exception will occur
				state.produceException(Operation.FALSE, IntegerConstant.ZERO32);
			} else {
				int thisAddress = (int) intConstantValue(thiss.toExpression());
				state.setStringLength(thisAddress, count.toExpression());
				for (int i = 0; i < countValue; i++) {
					state.setStringChar(thisAddress, i, state.getArrayValue(valueAddress, i + offsetValue));
				}
			}
		}
		return true;
	}

	public boolean valueOf__Z__Ljava_1lang_1String_2(SymbolicState state) {
		SymbolicValue bool = state.pop();
		if (falseStringId == -1) {
			falseStringId = state.createString();
			state.setStringLength(falseStringId, new IntegerConstant(5, 32));
			state.setStringChar(falseStringId, 0, new IntegerConstant('f', 16));
			state.setStringChar(falseStringId, 1, new IntegerConstant('a', 16));
			state.setStringChar(falseStringId, 2, new IntegerConstant('l', 16));
			state.setStringChar(falseStringId, 3, new IntegerConstant('s', 16));
			state.setStringChar(falseStringId, 4, new IntegerConstant('e', 16));
			trueStringId = state.createString();
			state.setStringLength(trueStringId, new IntegerConstant(4, 32));
			state.setStringChar(trueStringId, 0, new IntegerConstant('t', 16));
			state.setStringChar(trueStringId, 1, new IntegerConstant('r', 16));
			state.setStringChar(trueStringId, 2, new IntegerConstant('u', 16));
			state.setStringChar(trueStringId, 3, new IntegerConstant('e', 16));
		}
		Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
		Expression posGuard = Operation.and(Operation.eq(bool.toExpression(), IntegerConstant.ONE32),
				Operation.eq(var, new IntegerConstant(trueStringId, 32)));
		Expression negGuard = Operation.and(Operation.eq(bool.toExpression(), IntegerConstant.ZERO32),
				Operation.eq(var, new IntegerConstant(falseStringId, 32)));
		Expression pc = Operation.or(posGuard, negGuard);
		state.pushExtraCondition(pc);
		state.push(var);
		return true;
	}

	public boolean length____I(SymbolicState state) {
		SymbolicValue stringValue = state.pop();
		if ((stringValue == null) || !stringValue.isConstant()) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, Integer.MIN_VALUE, Integer.MAX_VALUE));
		} else {
			int thisAddress = (int) intConstantValue(stringValue.toExpression());
			state.push(state.getStringLength(thisAddress));
		}
		return true;
	}

	public boolean equals__Ljava_1lang_1Object_2__Z(SymbolicState state) {
		SymbolicValue stringValue1 = state.pop();
		SymbolicValue stringValue2 = state.pop();
		if ((stringValue1 == null) || !stringValue1.isConstant()) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, 0, 1));
		} else if ((stringValue2 == null) || !stringValue2.isConstant()) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, 0, 1));
		} else {
			int thisAddress1 = (int) intConstantValue(stringValue1.toExpression());
			int thisAddress2 = (int) intConstantValue(stringValue2.toExpression());
			int length1 = (int) state.getStringLength(thisAddress1).toValue();
			int length2 = (int) state.getStringLength(thisAddress2).toValue();
			if (length1 != length2) {
				state.push(IntegerConstant.ZERO32);
			} else if (length1 == 0) {
				state.push(IntegerConstant.ONE32);
			} else {
				Expression guard = null;
				for (int i = 0; i < length1; i++) {
					Expression ch1 = state.getStringChar(thisAddress1, i).toExpression();
					Expression ch2 = state.getStringChar(thisAddress2, i).toExpression();
					Expression eq = Operation.eq(ch1, ch2);
					if (i == 0) {
						guard = eq;
					} else {
						guard = Operation.and(guard, eq);
					}
				}
				Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
				Expression pc = Operation.or(Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32)),
						Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32)));
				state.pushExtraCondition(pc);
				state.push(var);
			}
		}
		return true;
	}

	public boolean contains__Ljava_1lang_1CharSequence_2__Z(SymbolicState state) {
		Expression other = state.peek().toExpression();
		Expression thiss = state.peek(1).toExpression();
		if (!(other instanceof IntegerConstant) || !(thiss instanceof IntegerConstant)) {
			return false; // CANNOT HANDLE SYMBOLIC OFFSETS
		}
		other = state.pop().toExpression();
		thiss = state.pop().toExpression();
		int otherAddress = (int) intConstantValue(other);
		int thisAddress = (int) intConstantValue(thiss);
		int otherLength = (int) intConstantValue(state.getStringLength(otherAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (otherLength > thisLength) {
			state.push(IntegerConstant.ZERO32);
		} else if (otherLength == 0) {
			state.push(IntegerConstant.ONE32);
		} else {
			Expression guard = null;
			int maxShift = thisLength - otherLength;
			for (int shift = 0; shift <= maxShift; shift++) {
				Expression subguard = null;
				for (int i = 0; i < otherLength; i++) {
					Expression otherChar = state.getStringChar(otherAddress, i).toExpression();
					Expression thisChar = state.getStringChar(thisAddress, i + shift).toExpression();
					Expression eq = Operation.eq(otherChar, thisChar);
					if (subguard == null) {
						subguard = eq;
					} else {
						subguard = Operation.and(subguard, eq);
					}
				}
				if (guard == null) {
					guard = subguard;
				} else {
					guard = Operation.or(guard, subguard);
				}
			}
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
			Expression posGuard = Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32));
			Expression negGuard = Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32));
			Expression pc = Operation.or(posGuard, negGuard);
			state.pushExtraCondition(pc);
			state.push(var);
		}
		return true;
	}

	public boolean regionMatches__ILjava_1lang_1String_2II__Z(SymbolicState state) {
		Expression len = state.peek().toExpression();
		Expression ooffset = state.peek(1).toExpression();
		Expression other = state.peek(2).toExpression();
		Expression toffset = state.peek(3).toExpression();
		Expression thiss = state.peek(4).toExpression();
		if (!(len instanceof IntegerConstant) || !(ooffset instanceof IntegerConstant)
				|| !(other instanceof IntegerConstant) || !(toffset instanceof IntegerConstant)
				|| !(thiss instanceof IntegerConstant)) {
			return false; // CANNOT HANDLE SYMBOLIC OFFSETS
		}
		len = state.pop().toExpression();
		ooffset = state.pop().toExpression();
		other = state.pop().toExpression();
		toffset = state.pop().toExpression();
		thiss = state.pop().toExpression();
		int length = (int) intConstantValue(len);
		int ooffsetValue = (int) intConstantValue(ooffset);
		int toffsetValue = (int) intConstantValue(toffset);
		int otherAddress = (int) intConstantValue(other);
		int thisAddress = (int) intConstantValue(thiss);
		int otherLength = (int) intConstantValue(state.getStringLength(otherAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if ((toffsetValue < 0) || (ooffsetValue < 0) || (toffsetValue + length > thisLength)
				|| (ooffsetValue > otherLength)) {
			state.push(IntegerConstant.ZERO32);
		} else {
			Expression guard = null;
			for (int i = 0; i < length; i++) {
				Expression otherChar = state.getStringChar(otherAddress, i + ooffsetValue).toExpression();
				Expression thisChar = state.getStringChar(thisAddress, i + toffsetValue).toExpression();
				Expression eq = Operation.eq(otherChar, thisChar);
				if (i == 0) {
					guard = eq;
				} else {
					guard = Operation.and(guard, eq);
				}
			}
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
			Expression posGuard = Operation.and(guard, Operation.eq(var, IntegerConstant.ONE32));
			Expression negGuard = Operation.and(Operation.not(guard), Operation.eq(var, IntegerConstant.ZERO32));
			Expression pc = Operation.or(posGuard, negGuard);
			state.pushExtraCondition(pc);
			state.push(var);
		}
		return true;
	}

	public boolean charAt__I__C(SymbolicState state) {
		Expression index = state.pop().toExpression();
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		Expression guard = Operation.and(Operation.ge(index, IntegerConstant.ZERO32),
				Operation.lt(index, new IntegerConstant(thisLength, 32)));
		if (index instanceof IntegerConstant) {
			state.push(state.getStringChar(thisAddress, (int) ((IntegerConstant) index).getValue()));
		} else {
			Expression var = new IntegerVariable(state.getNewVariableName(), 16, minChar, maxChar);
			Expression subguard = null;
			for (int i = 0; i < thisLength; i++) {
				Expression eq = Operation.and(Operation.eq(index, new IntegerConstant(i, 32)),
						Operation.eq(var, state.getStringChar(thisAddress, i).toExpression()));
				if (subguard == null) {
					subguard = eq;
				} else {
					subguard = Operation.or(subguard, eq);
				}
			}
			guard = Operation.and(guard, subguard);
			state.push(var);
		}
		state.pushExtraCondition(guard);
		return true;
	}

	public boolean indexOf__C__I(SymbolicState state) {
		Expression chr = state.pop().toExpression();
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength == 0) {
			state.push(MONE);
		} else {
			Expression var = new IntegerVariable(state.getNewVariableName(), 32, -1, thisLength - 1);
			state.pushExtraCondition(firstOccurrenceGuard(state, thisAddress, chr, var, 0, thisLength));
			state.push(var);
		}
		return true;
	}

	public boolean indexOf__CI__I(SymbolicState state) {
		Expression expr = state.pop().toExpression();
		Expression chr = state.pop().toExpression();
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
				state.pushExtraCondition(firstOccurrenceGuard(state, thisAddress, chr, var, ofs, thisLength));
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
			state.pushExtraCondition(pc);
			state.push(var);
		}
		return true;
	}

	// Encode the constraint that the first occurrence of character "chr" in string
	// "strAddresss"
	// occurs at position "rval". The string has fixed concrete length "len" and the
	// first occurrence
	// is at least "ofs".
	private Expression firstOccurrenceGuard(SymbolicState state, int strAddress, Expression chr, Expression rval,
			int ofs, int len) {
		assert ofs < len;
		Expression thisChar = state.getStringChar(strAddress, ofs).toExpression();
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
			thisChar = state.getStringChar(strAddress, o).toExpression();
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
				Expression prefixChar = state.getStringChar(prefixAddress, i).toExpression();
				Expression thisChar = state.getStringChar(thisAddress, i).toExpression();
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
				state.pushExtraCondition(pc);
				state.push(var);
			}
		} else {
			state.push(IntegerConstant.ZERO32); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean startsWith__Ljava_1lang_1String_2I__Z(SymbolicState state) {
		int first = 0;
		Expression offset = state.peek().toExpression();
		if (offset instanceof IntegerConstant) {
			first = (int) intConstantValue(offset);
			offset = state.pop().toExpression();
		} else {
			return false; // CANNOT (YET) HANDLE SYMBOLIC OFFSETS
		}
		int prefixAddress = (int) intConstantValue(state.pop());
		int thisAddress = (int) intConstantValue(state.pop());
		int prefixLength = (int) intConstantValue(state.getStringLength(prefixAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength >= first + prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = state.getStringChar(prefixAddress, i).toExpression();
				Expression thisChar = state.getStringChar(thisAddress, i + first).toExpression();
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
				state.pushExtraCondition(pc);
				state.push(var);
			}
		} else {
			state.push(IntegerConstant.ZERO32); // |this| < |prefix|, so result is always FALSE (=0)
		}
		return true;
	}

	public boolean split__Ljava_1lang_1String_2___3Ljava_1lang_1String_2(SymbolicState state) throws COASTALException {
		throw new UnsupportedOperationException("String.split() is unimplemented");
	}
	
	public boolean endsWith__Ljava_1lang_1String_2__Z(SymbolicState state) {
		int prefixAddress = (int) intConstantValue(state.pop());
		int thisAddress = (int) intConstantValue(state.pop());
		int prefixLength = (int) intConstantValue(state.getStringLength(prefixAddress));
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if (thisLength >= prefixLength) {
			Expression guard = null;
			for (int i = 0; i < prefixLength; i++) {
				Expression prefixChar = state.getStringChar(prefixAddress, i).toExpression();
				Expression thisChar = state.getStringChar(thisAddress, i + thisLength - prefixLength).toExpression();
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
				state.pushExtraCondition(pc);
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

	private long intConstantValue(SymbolicValue expr) {
		return intConstantValue(expr.toExpression());
	}

}
