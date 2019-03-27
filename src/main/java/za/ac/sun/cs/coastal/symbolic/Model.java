package za.ac.sun.cs.coastal.symbolic;

import java.util.HashMap;
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
	 * The input values for a run of the system under test.
	 */
	private final Input input;

	/**
	 * Additional payload information.
	 */
	private final Map<String, Object> payload = new HashMap<>();

	/**
	 * Construct a new model.
	 * 
	 * @param priority
	 *            the priority for this model
	 * @param input
	 *            the variable-value mapping for this model
	 */
	public Model(int priority, Input input) {
		this.priority = priority;
		this.input = input;
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
	public Input getInput() {
		return input;
	}

	/**
	 * Return the value of a payload field.
	 * 
	 * @param key key for the payload field
	 * @return value of the payload field 
	 */
	public Object getPayload(String key) {
		return payload.get(key);
	}
	
	/**
	 * Set the value of a payload field.
	 * 
	 * @param key key for the payload field
	 * @param value new value for the payload field
	 */
	public void setPayload(String key, Object value) {
		payload.put(key, value);
	}

	/**
	 * Set several payload field by copying them from another model.
	 * 
	 * @param model source model for payload fields
	 */
	public void copyPayload(Model model) {
		payload.putAll(model.getPayload());
	}
	
	/**
	 * Return the payload in its "raw" form.  Warning: this exposes the payload to other components.
	 * 
	 * @return payload
	 */
	public Map<String, Object> getPayload() {
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
		b.append(input);
		return b.toString();
	}

}
