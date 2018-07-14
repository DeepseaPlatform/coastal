package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * A model of some operations of {@link java.lang.Math}.
 */
public class Math {

	public Math() {
	}

	public boolean max__II__I() {
		Expression arg0 = SymbolicState.pop();
		Expression arg1 = SymbolicState.pop();
		int min = Configuration.getDefaultMinIntValue();
		int max = Configuration.getDefaultMaxIntValue();
		Expression var = new IntVariable(SymbolicState.getNewVariableName(), min, max);
		Expression pc = new Operation(Operator.OR,
				new Operation(Operator.AND, new Operation(Operator.GE, arg0, arg1),
						new Operation(Operator.EQ, arg0, var)),
				new Operation(Operator.AND, new Operation(Operator.LT, arg0, arg1),
						new Operation(Operator.EQ, arg1, var)));
		SymbolicState.pushExtraConjunct(pc);
		SymbolicState.push(var);
		return true;
	}

}
