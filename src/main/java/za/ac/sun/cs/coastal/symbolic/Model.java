package za.ac.sun.cs.coastal.symbolic;

import java.util.Map;

import za.ac.sun.cs.green.expr.Constant;

/**
 * An encapsulation of a model (a mapping from symbolic variables to concrete
 * values) and their priorities.
 */
public class Model {

	/**
	 * The priority of this model.
	 */
	private final int priority;

	/**
	 * A mapping from symbolic variables to concrete values. The concrete values
	 * are still represented by symbolic GREEN expressions, but as the type
	 * indicates, the expressions are constant values.
	 */
	private final Map<String, Constant> concreteValues;

	/**
	 * Construct a new model.
	 * 
	 * @param priority the priority for this model
	 * @param concreteValues the variable-value mapping for this model
	 */
	public Model(int priority, Map<String, Constant> concreteValues) {
		this.priority = priority;
		this.concreteValues = concreteValues;
	}

	/**
	 * Return the priority for this model.
	 * 
	 * @return the model priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Return the variable-value mapping associated with this model
	 * 
	 * @return the model's variable-value mapping
	 */
	public Map<String, Constant> getConcreteValues() {
		return concreteValues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(priority).append(':');
		b.append(concreteValues);
		return b.toString();
	}

}
