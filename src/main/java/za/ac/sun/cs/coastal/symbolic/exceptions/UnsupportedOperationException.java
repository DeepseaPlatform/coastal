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
