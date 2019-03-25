package za.ac.sun.cs.coastal.symbolic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InputSet {

	/**
	 * Mapping from variable names to variable values.
	 */
	private final Map<String, Object> inputs;

	/**
	 * Construct a new, empty set of inputs.
	 */
	public InputSet() {
		inputs = new HashMap<>();
	}

	/**
	 * Construct a new set of inputs based on an existing set.
	 * 
	 * @param inputSet the older set of inputs
	 */
	public InputSet(InputSet inputSet) {
		inputs = new HashMap<>(inputSet.inputs);
	}

	/**
	 * Return the variable value associated with a name.
	 * 
	 * @param name the name of the variable
	 * @return the value of the variable (or {@code null})
	 */
	public Object get(String name) {
		return inputs.get(name);
	}

	/**
	 * Associates a new value with a variable name.
	 * 
	 * @param name  the new of the variable
	 * @param value the new value of the variable
	 * @return the old value of the variable (or {@code null})
	 */
	public Object put(String name, Object value) {
		return inputs.put(name, value);
	}

	/**
	 * Returns the set of all names that are mapped in this set of inputs.
	 * 
	 * @return all names with values
	 */
	public Set<String> getNames() {
		return inputs.keySet();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return inputs.toString();
	}

}
