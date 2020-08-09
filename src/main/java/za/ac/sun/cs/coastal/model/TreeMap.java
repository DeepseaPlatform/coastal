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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.diver.SymbolicValueFactory.SymbolicValue;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;

/**
 * A model of some operations of {@link java.lang.String}. This implementation
 * exploits the fact that string instances enjoy a special status in Java and
 * are also treated specially by COASTAL. Wherever possible, COASTAL mirrors
 * Java string instances with much simpler array-like symbolic values.
 */
public class TreeMap {

	private static final Map<Integer, List<Tuple>> PUTS = new HashMap<>();

	public TreeMap(COASTAL coastal, Configuration config) {
	}

	public boolean put__Ljava_1lang_1Object_2Ljava_1lang_1Object_2__Ljava_1lang_1Object_2(SymbolicState state) {
		SymbolicValue key = state.pop();
		SymbolicValue value = state.pop();
		SymbolicValue thisRef = state.pop();
		if ((thisRef == null) || !thisRef.isConstant()) {
			state.push(new IntegerVariable(state.getNewVariableName(), 32, Integer.MIN_VALUE, Integer.MAX_VALUE));
		} else {
			int thisAddress = (int) intConstantValue(thisRef.toExpression());
			List<Tuple> thisPuts = PUTS.get(thisAddress);
			if (thisPuts == null) {
				thisPuts = new ArrayList<>();
				PUTS.put(thisAddress, thisPuts);
			}
			thisPuts.add(new Tuple(key, value));
		}
		return true;
	}

	private long intConstantValue(Expression expr) {
		assert expr instanceof IntegerConstant;
		return ((IntegerConstant) expr).getValue();
	}

//	private long intConstantValue(SymbolicValue expr) {
//		return intConstantValue(expr.toExpression());
//	}
	
}
