package za.ac.sun.cs.coastal.strategy;

import java.util.Map;

import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.expr.Constant;

public interface Strategy {

	Map<String, Constant> refine(SymbolicState symbolicState);

}
