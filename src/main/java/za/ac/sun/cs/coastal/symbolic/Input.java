package za.ac.sun.cs.coastal.symbolic;

import java.util.Set;

/**
 * The inputs to a run of the system-under-test. The most important component of
 * this is a mapping from variables names to values. For now, there are no other
 * components. In the future, the input may be extended to include details about
 * the environment.
 */
public class Input {

	/**
	 * Mapping from variable names to variable values.
	 */
	private final InputMap inputMap;

	/**
	 * Construct a new, empty input.
	 */
	public Input() {
		inputMap = new InputMap();
	}

	/**
	 * Construct a new input based on an existing input.
	 * 
	 * @param input the older input
	 */
	public Input(Input input) {
		inputMap = new InputMap(input.inputMap);
	}

	/**
	 * Return the variable value associated with a name.
	 * 
	 * @param name the name of the variable
	 * @return the value of the variable (or {@code null})
	 */
	public Object get(String name) {
		return inputMap.get(name);
	}

	/**
	 * Associates a new value with a variable name.
	 * 
	 * @param name  the new of the variable
	 * @param value the new value of the variable
	 * @return the old value of the variable (or {@code null})
	 */
	public Object put(String name, Object value) {
		return inputMap.put(name, value);
	}

	/**
	 * Returns the set of all names that are mapped in this set of inputs.
	 * 
	 * @return all names with values
	 */
	public Set<String> getNames() {
		return inputMap.getNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return inputMap.toString();
	}

}
