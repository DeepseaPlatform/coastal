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
 * Parent class for all exceptions produced by the COASTAL tool. This includes
 * exceptions that are intentionally thrown to control the flow of the tool as
 * well as unintentional exceptions that happen as a result of bugs or assertion
 * violations.
 */
public class COASTALException extends Exception {

	/**
	 * Required for serialization.
	 */
	private static final long serialVersionUID = 1282785987285619356L;

	/**
	 * Standard constructor.
	 */
	public COASTALException() {
		super();
	}

	/**
	 * Standard constructor.
	 */
	public COASTALException(String message) {
		super(message);
	}
	
	/**
	 * Constructor to wrap another throwable.
	 * 
	 * @param throwable Another {@link Throwable} to wrap
	 */
	public COASTALException(Throwable throwable) {
		super(throwable);
	}

}
