/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.symbolic.exceptions;

/**
 * Exception to signal that a significant event has occurred during execution to
 * which the system must react.
 */
public class ControlException extends COASTALException {

	/**
	 * Required for serialization.
	 */
	private static final long serialVersionUID = 1282785987285619356L;

	/**
	 * Standard constructor.
	 */
	public ControlException() {
		super();
	}

	/**
	 * Standard constructor.
	 *
	 * @param message
	 *                exception message
	 */
	public ControlException(String message) {
		super(message);
	}

}
