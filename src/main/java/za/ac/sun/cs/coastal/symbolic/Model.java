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

/**
 * An encapsulation of a model (a mapping from symbolic variables to concrete
 * values) and their priorities.
 */
public class Model extends PayloadCarrierImpl {

	/**
	 * The priority of this model.
	 */
	private final int priority;

	/**
	 * The input values for a run of the system under test.
	 */
	private final Input input;

	/**
	 * Construct a new model.
	 * 
	 * @param priority the priority for this model
	 * @param input    the variable-value mapping for this model
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
