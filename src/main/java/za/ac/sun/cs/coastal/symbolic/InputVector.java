/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal.symbolic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The inputs to a run of the system-under-test in the form of a mapping from
 * variable indices (as {@link Integer}s) to variable values (as
 * {@link Object}s). This differs from {@link InputMap} which maps variables
 * names (as {@link String}) to values.
 */
public class InputVector {

	/**
	 * Mapping from variable indices to variable values.
	 */
	private final Map<Integer, Object> inputVector;

	/**
	 * Construct a new, empty set of inputs.
	 */
	public InputVector() {
		inputVector = new HashMap<>();
	}

	/**
	 * Construct a new set of inputs based on an existing set.
	 * 
	 * @param inputVector the older set of inputs
	 */
	public InputVector(InputVector inputVector) {
		this.inputVector = new HashMap<>(inputVector.inputVector);
	}

	/**
	 * Return the variable value associated with an index.
	 * 
	 * @param index the name of the variable
	 * @return the value of the variable (or {@code null})
	 */
	public Object get(int index) {
		return inputVector.get(index);
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
	 * Returns the set of all indices that are mapped.
	 * 
	 * @return all indices with values
	 */
	public Set<Integer> getIndices() {
		return inputVector.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return inputVector.toString();
	}

}
