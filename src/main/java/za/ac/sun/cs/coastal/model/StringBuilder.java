/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;

/**
 * A model of some operations of {@link java.lang.StringBuilder}.
 */
public class StringBuilder {

	public StringBuilder(COASTAL coastal, Configuration config) {
	}

	public boolean _init___Ljava_1lang_1String_2__V(SymbolicState state) {
		SymbolicValue sbValue = state.pop();
		if ((sbValue == null) || !sbValue.isConstant()) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, Integer.MIN_VALUE, Integer.MAX_VALUE));
		} else {
			int thisAddress = (int) intConstantValue(sbValue.toExpression());
			int length = (int) state.getStringLength(thisAddress).toValue();
			int stringId = state.createString();
			state.setStringLength(stringId, new IntegerConstant(length, 32));
			for (int i = 0; i < length; i++) {
				state.setStringChar(stringId, i, state.getStringChar(thisAddress, i));
			}
			state.push(new IntegerConstant(stringId, 32));
		}
		return true;
	}

	public boolean charAt__I__C(SymbolicState state) {
		int indexValue = 0;
		Expression index = state.peek().toExpression();
		if (index instanceof IntegerConstant) {
			indexValue = (int) intConstantValue(index);
			index = state.pop().toExpression();
		} else {
			return false; // CANNOT (YET) HANDLE SYMBOLIC OFFSETS
		}
		int thisAddress = (int) intConstantValue(state.pop());
		int thisLength = (int) intConstantValue(state.getStringLength(thisAddress));
		if ((indexValue >= 0) && (indexValue < thisLength)) {
			state.push(state.getStringChar(thisAddress, indexValue));
		} else {
			// SHOULD BE THROWING AN EXCEPTION
			state.push(IntegerConstant.ZERO32);
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
