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
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * A model of some operations of {@link java.lang.Character}.
 */
public class Character {

	private static char min, max;
	private static IntegerConstant lowerA = new IntegerConstant('a', 16);
	private static IntegerConstant lowerZ = new IntegerConstant('z', 16);
	private static IntegerConstant upperA = new IntegerConstant('A', 16);
	private static IntegerConstant upperZ = new IntegerConstant('Z', 16);
	private static IntegerConstant thirtyTwo = new IntegerConstant(32, 16);

	public Character(COASTAL coastal, Configuration config) {
		min = (char) coastal.getDefaultMinValue(char.class);
		max = (char) coastal.getDefaultMaxValue(char.class);
	}

	public boolean toUpperCase__C__C(SymbolicState state) {
		Expression arg = state.pop().toExpression();
		Expression var = new IntegerVariable(state.getNewVariableName(), 16, min, max);
		Expression pc0 = Operation.and(Operation.lt(arg, lowerA), Operation.eq(arg, var));
		Expression pc1 = Operation.and(Operation.gt(arg, lowerZ), Operation.eq(arg, var));
		Expression pc2 = Operation.and(Operation.ge(arg, lowerA), Operation.le(arg, lowerZ));
		Expression pc3 = Operation.and(pc2, Operation.eq(arg, Operation.add(var, thirtyTwo)));
		Expression pc = Operation.or(pc3, Operation.or(pc0, pc1));
		state.pushExtraCondition(pc);
		state.push(state.getSymbolicValueFactory().createSymbolicValue(var));
		return true;
	}

	public boolean toLowerCase__C__C(SymbolicState state) {
		Expression arg = state.pop().toExpression();
		Expression var = new IntegerVariable(state.getNewVariableName(), 16, min, max);
		Expression pc0 = Operation.and(Operation.lt(arg, upperA), Operation.eq(arg, var));
		Expression pc1 = Operation.and(Operation.gt(arg, upperZ), Operation.eq(arg, var));
		Expression pc2 = Operation.and(Operation.ge(arg, upperA), Operation.le(arg, upperZ));
		Expression pc3 = Operation.and(pc2, Operation.eq(var, Operation.add(arg, thirtyTwo)));
		Expression pc = Operation.or(pc3, Operation.or(pc0, pc1));
		state.pushExtraCondition(pc);
		state.push(state.getSymbolicValueFactory().createSymbolicValue(var));
		return true;
	}
	
	public boolean isLetter__C__Z(SymbolicState state) {
		Expression arg = state.pop().toExpression();
		Expression var = new IntegerVariable(state.getNewVariableName(), 32, 0, 1);
		Expression pcu = Operation.and(Operation.ge(arg, upperA), Operation.eq(arg, upperZ));
		Expression pcl = Operation.and(Operation.ge(arg, lowerA), Operation.eq(arg, lowerZ));
		Expression pcy = Operation.or(pcu, pcl);
		Expression pcn = Operation.or(pcu, pcl);
		Expression pc0 = Operation.and(pcn, Operation.eq(var, IntegerConstant.ZERO32));
		Expression pc1 = Operation.and(pcy, Operation.eq(var, IntegerConstant.ONE32));
		Expression pc = Operation.or(pc0, pc1);
		state.pushExtraCondition(pc);
		state.push(state.getSymbolicValueFactory().createSymbolicValue(var));
		return true;
	}
	
}
