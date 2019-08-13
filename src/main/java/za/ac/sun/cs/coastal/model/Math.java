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
