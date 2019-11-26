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
 * Exception to signal that symbolic record mode has been switched off (in
 * either a diver or surfer) and that the run is therefore complete.
 */
public class UnsupportedOperationException extends ControlException {

	/**
	 * Required for serialization.
	 */
	private static final long serialVersionUID = 1282665987285619356L;

	/**
	 * Standard constructor.
	 */
	public UnsupportedOperationException() {
		super();
	}

	/**
	 * Standard constructor.
	 *
	 * @param message
	 *                exception message
	 */
	public UnsupportedOperationException(String message) {
		super(message);
	}
	
}
