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
 * Exception to signal that a run has been prematurely aborted because the
 * surfer has detected that the corrent execution path has been explored before.
 */
public class AbortedRunException extends ControlException {

	/**
	 * Required for serialization.
	 */
	private static final long serialVersionUID = 1282665998285619356L;

}
