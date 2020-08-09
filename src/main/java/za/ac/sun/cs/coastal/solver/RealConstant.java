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

public class RealConstant extends Constant {

	public static final Constant ZERO32 = new RealConstant(0, 32);

	public static final Constant ZERO64 = new RealConstant(0, 64);
	
	private final double value;

	protected final int size;
	
	public RealConstant(final double value, final int size) {
		this.value = value;
		this.size = size;
	}

	public double getValue() {
		return value;
	}

	public int getSize() {
		return size;
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

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	@Override
	public String toString0() {
		return Double.toString(value);
	}

}
