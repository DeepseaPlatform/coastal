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

public class RealVariable extends Variable {

	/**
	 * The number of bits in this variable: either 32 or 64.
	 */
	protected final int size;
	
	protected final double lowerBound;

	protected final double upperBound;
	
	public RealVariable(final String name, final int size, final double lowerBound, final double upperBound) {
		super(name);
		assert (size == 32) || (size == 64);
		this.size = size;
		assert (lowerBound <= upperBound);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getSize() {
		return size;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}
	
	// ======================================================================
	//
	// VISITOR
	//
	// ======================================================================
	
	@Override
	public void accept(Visitor visitor) throws VisitorException {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

}
