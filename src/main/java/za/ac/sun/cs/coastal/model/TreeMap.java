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
