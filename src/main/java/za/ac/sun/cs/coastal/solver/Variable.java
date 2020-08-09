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
package za.ac.sun.cs.coastal.solver;

public abstract class Variable extends Expression {

	private final String name;

	private final Object original;

	public Variable(final String name) {
		this.name = name;
		this.original = null;
	}
	
	public Variable(final String name, final Object original) {
		this.name = name;
		this.original = original;
	}

	public final String getName() {
		return name;
	}

	public final Object getOriginal() {
		return original;
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	@Override
	public final String toString0() {
		return getName();
	}
	
}
