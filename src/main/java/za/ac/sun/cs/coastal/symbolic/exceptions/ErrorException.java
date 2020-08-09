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
