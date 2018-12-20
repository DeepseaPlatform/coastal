package za.ac.sun.cs.coastal.model;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;

/**
 * A model of some operations of {@link java.lang.Math}.
 */
public class Math {

	private static int min, max;

	public Math(COASTAL coastal, ImmutableConfiguration options) {
		min = (Integer) coastal.getDefaultMinValue(int.class);
		max = (Integer) coastal.getDefaultMaxValue(int.class);
	}

	public boolean max__II__I(SymbolicState state) {
		Expression arg0 = state.pop();
		Expression arg1 = state.pop();
		Expression var = new IntegerVariable(state.getNewVariableName(), 32, min, max);
		Expression pc = Operation.or(Operation.and(Operation.ge(arg0, arg1), Operation.eq(arg0, var)),
				Operation.and(Operation.lt(arg0, arg1), Operation.eq(arg1, var)));
		state.pushExtraConjunct(pc);
		state.push(var);
		return true;
	}

}
