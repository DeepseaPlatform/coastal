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

import za.ac.sun.cs.coastal.solver.Expression;

/**
 * An encapsulation of a potential branching point encountered during an
 * execution of the system-under-test.
 */
public abstract class Branch {

	/**
	 * Return the number of alternatives at this branch.
	 * 
	 * @return the number of alternatives
	 */
	public abstract long getNumberOfAlternatives();

	/**
	 * Return a string representation of a specific alternative at this branch.
	 * Typically, this would be "F" or "T" for a binary branch (if), or "1", "2",
	 * ... for a n-ary branch (switch).
	 * 
	 * @param alternative the number of the alternative
	 * @return a string representation of the alternative
	 */
	public abstract String getAlternativeRepr(long alternative);

	/**
	 * Return an expression that describes a specific alternative at this branch.
	 * 
	 * @param alternative number of the alternative
	 * @return expression for the alternative
	 */
	public abstract Expression getAlternative(long alternative);

	/**
	 * Return an expression that describes the contribution that this branch makes
	 * to the path condition when a given alternative is taken at this branching
	 * point. This may include, for example, the correct active conjunct and the
	 * passive conjunct.
	 * 
	 * @param alternative which alternative to describe
	 * @return branch's contribution to the path condition
	 */
	protected abstract Expression getPCContribution(long alternative);

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	/**
	 * String representation of this choice.
	 */
	protected String stringRep = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		if (stringRep == null) {
			stringRep = toString0();
		}
		return stringRep;
	}

	/**
	 * Return a string representation of this branch.
	 * 
	 * @return string representation of this branch
	 */
	protected abstract String toString0();

}
