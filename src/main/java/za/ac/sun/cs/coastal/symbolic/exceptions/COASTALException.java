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
	 *
	 * @param message
	 *                exception message
	 */
	public COASTALException(String message) {
		super(message);
	}

	/**
	 * Constructor to wrap another throwable.
	 * 
	 * @param throwable
	 *                  Another {@link Throwable} to wrap
	 */
	public COASTALException(Throwable throwable) {
		super(throwable);
	}

}
