/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.surfer;

import za.ac.sun.cs.coastal.symbolic.ValueFactory;

/**
 * Factory for producing and manipulating symbolic values.
 */
public interface TraceValueFactory extends ValueFactory {

	// ======================================================================
	//
	// VALUE OBJECT
	//
	// ======================================================================

	/**
	 * Interface requirements for symbolic values.
	 */
	public interface TraceValue extends Value {

	}

}
