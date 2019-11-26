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
 * Exception to signal that an unexpected event ({@link Throwable}) has occurred
 * during execution. An instance of this exception wraps the true throwable.
 */
public class ErrorException extends COASTALException {

	/**
	 * Required for serialization.
	 */
	private static final long serialVersionUID = 1282785987285619356L;

	/**
	 * Constructor to wrap another throwable.
	 * 
	 * @param throwable Another {@link Throwable} to wrap
	 */
	public ErrorException(Throwable throwable) {
		super(throwable);
	}

}
