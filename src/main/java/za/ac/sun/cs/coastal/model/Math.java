package za.ac.sun.cs.coastal.model;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.symbolic.SymbolicVM;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

/**
 * A model of some operations of {@link java.lang.Math}.
 */
public class Math {

	private static int min, max;

	public Math(COASTAL coastal) {
		min = coastal.getDefaultMinValue(int.class);
		max = coastal.getDefaultMaxValue(int.class);
	}

	public boolean max__II__I() {
		Expression arg0 = SymbolicVM.pop();
		Expression arg1 = SymbolicVM.pop();
		Expression var = new IntVariable(SymbolicVM.getNewVariableName(), min, max);
		Expression pc = new Operation(Operator.OR,
				new Operation(Operator.AND, new Operation(Operator.GE, arg0, arg1),
						new Operation(Operator.EQ, arg0, var)),
				new Operation(Operator.AND, new Operation(Operator.LT, arg0, arg1),
						new Operation(Operator.EQ, arg1, var)));
		SymbolicVM.pushExtraConjunct(pc);
		SymbolicVM.push(var);
		return true;
	}

}
