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
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * A model of some operations of {@link java.lang.StringBuilder}.
 */
public class StringBuilder {

	public StringBuilder(COASTAL coastal, Configuration config) {
	}

	public boolean _init___Ljava_1lang_1String_2__V(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue str = state.peek();
		SymbolicValue thiss = state.peek(1);
		// Check that the required parameters are constants.
		if (!str.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.multiPop(2);
		// Symbolic modelling
		int strAddress = (int) intConstantValue(str.toExpression());
		int strLength = (int) state.getStringLength(strAddress).toValue();
		int thisAddress = (int) intConstantValue(thiss.toExpression());
		state.setStringLength(thisAddress, new IntegerConstant(strLength, 32));
		for (int i = 0; i < strLength; i++) {
			state.setStringChar(thisAddress, i, state.getStringChar(strAddress, i));
		}
		return true;
	}

	public boolean charAt__I__C(SymbolicState state) {
		// Pick up the parameters
		SymbolicValue index = state.peek();
		SymbolicValue thiss = state.peek(1);
		// Check that the required parameters are constants.
		if (!index.isConstant()) {
			return false;
		}
		if (!thiss.isConstant()) {
			return false;
		}
		// Remove the parameters for real!
		state.multiPop(2);
		// Symbolic modelling
		int indexValue = (int) intConstantValue(index.toExpression());
		int thisAddress = (int) intConstantValue(thiss.toExpression());
		int thisLength = (int) state.getStringLength(thisAddress).toValue();
		if ((indexValue >= 0) && (indexValue < thisLength)) {
			state.push(state.getStringChar(thisAddress, indexValue));
		} else {
			state.produceException(Operation.TRUE, IntegerConstant.ZERO32);
		}
		return true;
	}
	
	private long intConstantValue(Expression expr) {
		assert expr instanceof IntegerConstant;
		return ((IntegerConstant) expr).getValue();
	}

}
