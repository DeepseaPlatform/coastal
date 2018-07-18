package za.ac.sun.cs.coastal.strategy;

import java.util.Map;

import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.green.expr.Constant;

public interface Strategy extends Reporter {

	Map<String, Constant> refine();

}
