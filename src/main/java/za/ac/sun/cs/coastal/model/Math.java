package za.ac.sun.cs.coastal.model;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

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
		Expression var = new IntVariable(state.getNewVariableName(), min, max);
		Expression pc = new Operation(Operator.OR,
				new Operation(Operator.AND, new Operation(Operator.GE, arg0, arg1),
						new Operation(Operator.EQ, arg0, var)),
				new Operation(Operator.AND, new Operation(Operator.LT, arg0, arg1),
						new Operation(Operator.EQ, arg1, var)));
		state.pushExtraConjunct(pc);
		state.push(var);
		return true;
	}

}
