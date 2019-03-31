package za.ac.sun.cs.coastal.symbolic;

import java.util.Set;

/**
 * The inputs to a run of the system-under-test. The most important component of
 * this is a mapping from variables to values. In fact, there are two such
 * mappings: one from names to values (which is produced when a path constraint
 * is solved), and one from indices to values (which is produced when the
 * system-under-test executes).
 * 
 * For now, there are no other components. In the future, the input may be
 * extended to include details about the environment.
 */
public final class Input extends PayloadCarrierImpl {

	/**
	 * Mapping from variable names to variable values.
	 */
	private final InputMap inputMap;

	/**
	 * Mapping from variable indices to variable values.
	 */
	private final InputVector inputVector;

	/**
	 * Construct a new, empty input.
	 */
	public Input() {
		inputMap = new InputMap();
		inputVector = new InputVector();
	}

	/**
	 * Construct a new input based on an existing input.
	 * 
	 * @param input the older input
	 */
	public Input(Input input) {
		inputMap = new InputMap(input.inputMap);
		inputVector = new InputVector(input.inputVector);
	}

	public int getSize() {
		return inputMap.getSize();
	}

	/**
	 * Return the priority of this input. First the method checks is there is a
	 * "priority" payload. If not, it checks for a "score" payload. If either is
	 * present, it is returned. Otherwise, the method returns 0.
	 * 
	 * @return priority of this input
	 */
	public int getPriority() {
		Integer priority = (Integer) getPayload("priority");
		if (priority == null) {
			priority = (Integer) getPayload("score");
		}
		return priority;
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
	 * Return the variable value associated with an index.
	 * 
	 * @param index the index of the variable
	 * @return the value of the variable (or {@code null})
	 */
	public Object get(int index) {
		return inputVector.get(index);
	}

	/**
	 * Associates a new value with a variable name.
	 * 
	 * @param name  the name of the variable
	 * @param value the new value of the variable
	 * @return the old value of the variable (or {@code null})
	 */
	public Object put(String name, Object value) {
		return inputMap.put(name, value);
	}

	/**
	 * Associates a new value with a variable index.
	 * 
	 * @param index the index of the variable
	 * @param value the new value of the variable
	 * @return the old value of the variable (or {@code null})
	 */
	public Object put(int index, Object value) {
		return inputVector.put(index, value);
	}

	/**
	 * Returns the set of all names that are mapped in this set of inputs.
	 * 
	 * @return all names with values
	 */
	public Set<String> getNames() {
		return inputMap.getNames();
	}

	/**
	 * Returns the set of all indices that are mapped in this set of inputs.
	 * 
	 * @return all indices with values
	 */
	public Set<Integer> getIndices() {
		return inputVector.getIndices();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder rep = new StringBuilder();
		rep.append('<').append(toMapString());
		rep.append('-').append(inputVector.toString());
		rep.append('-').append(getPayload().toString());
		rep.append('>');
		return rep.toString();
	}

	public String toMapString() {
		return inputMap.toString();
	}

}
