package za.ac.sun.cs.coastal.symbolic;

import java.util.Map;

import za.ac.sun.cs.green.expr.Constant;

public class Model {

	private final int priority;
	
	private final Map<String, Constant> concreteValues;

	public Model(int priority, Map<String, Constant> concreteValues) {
		this.priority = priority;
		this.concreteValues = concreteValues;
	}

	public int getPriority() {
		return priority;
	}

	public Map<String, Constant> getConcreteValues() {
		return concreteValues;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(priority).append(':');
		b.append(concreteValues);
		return b.toString();
	}

}
