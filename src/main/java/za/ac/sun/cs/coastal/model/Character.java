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
	
}
