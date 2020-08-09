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
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * A model of some operations of {@link java.lang.Math}.
 */
public class Math {

	private static int min, max;

	public Math(COASTAL coastal, Configuration config) {
		min = (Integer) coastal.getDefaultMinValue(int.class);
		max = (Integer) coastal.getDefaultMaxValue(int.class);
	}

	public boolean max__II__I(SymbolicState state) {
		Expression arg0 = state.pop().toExpression();
		Expression arg1 = state.pop().toExpression();
		Expression var = new IntegerVariable(state.getNewVariableName(), 32, min, max);
		Expression pc = Operation.or(Operation.and(Operation.ge(arg0, arg1), Operation.eq(arg0, var)),
				Operation.and(Operation.lt(arg0, arg1), Operation.eq(arg1, var)));
		state.pushExtraCondition(pc);
		state.push(state.getSymbolicValueFactory().createSymbolicValue(var));
		return true;
	}

}
