package za.ac.sun.cs.coastal.symbolic;

import java.util.Map;

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
	private final Map<String, Object> concreteValues;

	/**
	 * Additional information about this model.
	 */
	private Payload payload;

	/**
	 * Construct a new model.
	 * 
	 * @param priority
	 *            the priority for this model
	 * @param concreteValues
	 *            the variable-value mapping for this model
	 */
	public Model(int priority, Map<String, Object> concreteValues) {
		this.priority = priority;
		this.concreteValues = concreteValues;
		this.payload = null;
	}

	/**
	 * Construct a new model.
	 * 
	 * @param priority
	 *            the priority for this model
	 * @param concreteValues
	 *            the variable-value mapping for this model
	 * @param payload
	 *            additional payload information
	 */
	public Model(int priority, Map<String, Object> concreteValues, Payload payload) {
		this.priority = priority;
		this.concreteValues = concreteValues;
		this.payload = payload;
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
	public Map<String, Object> getConcreteValues() {
		return concreteValues;
	}

	/**
	 * Set the payload for this model.
	 * 
	 * @param payload the paylaod information
	 */
	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	/**
	 * Return the payload for this model.
	 * 
	 * @return the payload information
	 */
	public Payload getPayload() {
		return payload;
	}
	
	/*
	 * (non-Javadoc)
	 * 
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
