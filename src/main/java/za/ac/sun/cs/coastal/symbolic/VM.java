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
package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.ValueFactory.Value;
import za.ac.sun.cs.coastal.symbolic.exceptions.COASTALException;
import za.ac.sun.cs.coastal.symbolic.exceptions.ControlException;
import za.ac.sun.cs.coastal.symbolic.exceptions.ErrorException;

/**
 * Class with static methods that are called by the instrumentation that are
 * added to the classes of the system-under-test.
 */
public class VM {

	/**
	 * Reference to the actual state. Different copies of this class are loaded by
	 * different classloaders, resulting in different copies of this reference.
	 */
	public static State state = null;

	/**
	 * Set the state for a particular copy of this class.
	 *
	 * @param state
	 *              state for this copy of this class
	 */
	public static void setState(State state) {
		VM.state = state;
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	/**
	 * Create and return a new variable name.
	 *
	 * @return new variable name
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static String getNewVariableName() throws COASTALException {
		try {
			return state.getNewVariableName();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Return the value of the length of the given string in the current state.
	 *
	 * @param stringId
	 *                 address of the string
	 * @return value of the string length
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static Value getStringLength(int stringId) throws COASTALException {
		try {
			return state.getStringLength(stringId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Return the value of the character at a given position of the given string in
	 * the current state.
	 *
	 * @param stringId
	 *                 address of the string
	 * @param index
	 *                 index of character
	 * @return value of the character
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static Value getStringChar(int stringId, int index) throws COASTALException {
		try {
			return state.getStringChar(stringId, index);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Push a value onto the expression stack of the current state.
	 *
	 * @param expr
	 *             value to push
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void push(Value expr) throws COASTALException {
		try {
			state.push(expr);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Pop and return the value on top of the expression stack of the current state.
	 *
	 * @return value popped off the expression stacks
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static Value pop() throws COASTALException {
		try {
			return state.pop();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Add an additional conjunct to the current path condition. The new conjunct
	 * represents a constraint on the relationship of the variables and will not be
	 * negated.
	 *
	 * @param extraConjunct
	 *                      additional conjunct
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void pushExtraConjunct(Expression extraConjunct) throws COASTALException {
		try {
			state.pushExtraCondition(extraConjunct);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 32-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The method returns the concrete value
	 * to use for the identifier: if the current model provides an overriding value,
	 * it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static int createSymbolicInt(int oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicInt(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 16-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The method returns the concrete value
	 * to use for the identifier: if the current model provides an overriding value,
	 * it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static short createSymbolicShort(short oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicShort(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 16-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The variable is constrained to take
	 * on only the values 0 and 1. The method returns the concrete value to use for
	 * the identifier: if the current model provides an overriding value, it is
	 * used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static boolean createSymbolicBoolean(boolean oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicBoolean(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 8-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The method returns the concrete value
	 * to use for the identifier: if the current model provides an overriding value,
	 * it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static byte createSymbolicByte(byte oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicByte(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 16-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The method returns the concrete value
	 * to use for the identifier: if the current model provides an overriding value,
	 * it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static char createSymbolicChar(char oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicChar(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 64-bit integer variable with a name derived from the
	 * given identifier. The identifier is assumed to be unique in the sense that
	 * during this execution, no other such request will use the same identifier,
	 * even if source code lines are the same. The method returns the concrete value
	 * to use for the identifier: if the current model provides an overriding value,
	 * it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static long createSymbolicLong(long oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicLong(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 32-bit floating-point variable with a name derived from
	 * the given identifier. The identifier is assumed to be unique in the sense
	 * that during this execution, no other such request will use the same
	 * identifier, even if source code lines are the same. The method returns the
	 * concrete value to use for the identifier: if the current model provides an
	 * overriding value, it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static float createSymbolicFloat(float oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicFloat(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 64-bit floating-point variable with a name derived from
	 * the given identifier. The identifier is assumed to be unique in the sense
	 * that during this execution, no other such request will use the same
	 * identifier, even if source code lines are the same. The method returns the
	 * concrete value to use for the identifier: if the current model provides an
	 * overriding value, it is used. Otherwise, the provided value is returned.
	 *
	 * @param oldValue
	 *                 default concrete value for the identifier if not overridden
	 *                 by model
	 * @param uniqueId
	 *                 unique identifier for this variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static double createSymbolicDouble(double oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicDouble(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param uniqueId
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static String createSymbolicString(int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicString(uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * 
	 *
	 *
	 * @param condition
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void assume(boolean condition) throws COASTALException {
		try {
			state.assume(condition);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 32-bit integer variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static int makeSymbolicInt(String newName) throws COASTALException {
		try {
			return state.makeSymbolicInt(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 16-bit integer variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static short makeSymbolicShort(String newName) throws COASTALException {
		try {
			return state.makeSymbolicShort(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 32-bit integer variable with the given name. The
	 * variable is constrained to take on only the values 0 and 1.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static boolean makeSymbolicBoolean(String newName) throws COASTALException {
		try {
			return state.makeSymbolicBoolean(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 8-bit integer variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static byte makeSymbolicByte(String newName) throws COASTALException {
		try {
			return state.makeSymbolicByte(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 16-bit integer variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static char makeSymbolicChar(String newName) throws COASTALException {
		try {
			return state.makeSymbolicChar(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 64-bit integer variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static long makeSymbolicLong(String newName) throws COASTALException {
		try {
			return state.makeSymbolicLong(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 32-bit floating-point variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static float makeSymbolicFloat(String newName) throws COASTALException {
		try {
			return state.makeSymbolicFloat(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Create a new symbolic 64-bit floating-point variable with the given name.
	 *
	 * @param newName
	 *                name for the variable
	 * @return the concrete value to use for the variable
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static double makeSymbolicDouble(String newName) throws COASTALException {
		try {
			return state.makeSymbolicDouble(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param newName
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static String makeSymbolicString(String newName) throws COASTALException {
		try {
			return state.makeSymbolicString(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	/**
	 * TODO
	 *
	 *
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static boolean getRecordingMode() throws COASTALException {
		try {
			return state.getRecordingMode();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue)
			throws COASTALException {
		try {
			return state.getConcreteBoolean(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue)
			throws COASTALException {
		try {
			return state.getConcreteByte(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static short getConcreteShort(int triggerIndex, int index, int address, short currentValue)
			throws COASTALException {
		try {
			return state.getConcreteShort(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue)
			throws COASTALException {
		try {
			return state.getConcreteChar(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue)
			throws COASTALException {
		try {
			return state.getConcreteInt(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static long getConcreteLong(int triggerIndex, int index, int address, long currentValue)
			throws COASTALException {
		try {
			return state.getConcreteLong(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static float getConcreteFloat(int triggerIndex, int index, int address, float currentValue)
			throws COASTALException {
		try {
			return state.getConcreteFloat(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static double getConcreteDouble(int triggerIndex, int index, int address, double currentValue)
			throws COASTALException {
		try {
			return state.getConcreteDouble(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue)
			throws COASTALException {
		try {
			return state.getConcreteString(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteBooleanArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteByteArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteShortArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteCharArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteIntArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteLongArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteFloatArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteDoubleArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param triggerIndex
	 * @param index
	 * @param address
	 * @param currentValue
	 * @return
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue)
			throws COASTALException {
		try {
			return state.getConcreteStringArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param methodNumber
	 * @param triggerIndex
	 * @param isStatic
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) throws COASTALException {
		try {
			state.triggerMethod(methodNumber, triggerIndex, isStatic);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param methodNumber
	 * @param methodName
	 * @param argCount
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void startMethod(int methodNumber, String methodName, int argCount) throws COASTALException {
		try {
			state.startMethod(methodNumber, methodName, argCount);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(boolean returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(char returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(double returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(float returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(int returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(long returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param returnValue
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void returnValue(short returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param line
	 * @param filename
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void linenumber(int instr, int line, String filename) throws COASTALException {
		try {
			state.linenumber(instr, line, filename);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param label
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void label(int instr, String label) throws COASTALException {
		try {
			state.label(instr, label);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void insn(int instr, int opcode) throws COASTALException {
		try {
			state.insn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param operand
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void intInsn(int instr, int opcode, int operand) throws COASTALException {
		try {
			state.intInsn(instr, opcode, operand);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param var
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void varInsn(int instr, int opcode, int var) throws COASTALException {
		try {
			state.varInsn(instr, opcode, var);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param type
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void typeInsn(int instr, int opcode, String type) throws COASTALException {
		try {
			state.typeInsn(instr, opcode, type);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param owner
	 * @param name
	 * @param descriptor
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws COASTALException {
		try {
			state.fieldInsn(instr, opcode, owner, name, descriptor);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param owner
	 * @param name
	 * @param descriptor
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws COASTALException {
		try {
			state.methodInsn(instr, opcode, owner, name, descriptor);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void invokeDynamicInsn(int instr, int opcode) throws COASTALException {
		try {
			state.invokeDynamicInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void jumpInsn(int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param value
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void jumpInsn(int value, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param value
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void jumpInsn(Object value, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param value1
	 * @param value2
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void jumpInsn(int value1, int value2, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value1, value2, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void postJumpInsn(int instr, int opcode) throws COASTALException {
		try {
			state.postJumpInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void ldcInsn(int instr, int opcode, int value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void ldcInsn(int instr, int opcode, long value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void ldcInsn(int instr, int opcode, float value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void ldcInsn(int instr, int opcode, double value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void ldcInsn(int instr, int opcode, Object value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param var
	 * @param increment
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void iincInsn(int instr, int var, int increment) throws COASTALException {
		try {
			state.iincInsn(instr, var, increment);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void tableSwitchInsn(int instr, int opcode) throws COASTALException {
		try {
			state.tableSwitchInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param min
	 * @param max
	 * @param value
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void tableCaseInsn(int min, int max, int value) throws COASTALException {
		try {
			state.tableCaseInsn(min, max, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void lookupSwitchInsn(int instr, int opcode) throws COASTALException {
		try {
			state.lookupSwitchInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param id
	 * @param choice
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void lookupCaseInsn(int id, int choice) throws COASTALException {
		try {
			state.lookupCaseInsn(id, choice);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @param opcode
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void multiANewArrayInsn(int instr, int opcode) throws COASTALException {
		try {
			state.multiANewArrayInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	/**
	 * TODO
	 *
	 *
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void noException() throws COASTALException {
		try {
			state.noException();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param instr
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void startCatch(int instr) throws COASTALException {
		try {
			state.startCatch(instr);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	/**
	 * TODO
	 *
	 *
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void stop() throws COASTALException {
		try {
			state.stop();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param message
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void stop(String message) throws COASTALException {
		try {
			state.stop(message);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param marker
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void mark(int marker) throws COASTALException {
		try {
			state.mark(marker);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param marker
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void mark(String marker) throws COASTALException {
		try {
			state.mark(marker);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * TODO
	 *
	 *
	 * @param label
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void printPC(String label) throws COASTALException {
		try {
			state.printPC(label);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	/**
	 * Print the path condition.
	 *
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void printPC() throws COASTALException {
		try {
			state.printPC();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// CLASS LOADING
	//
	// ======================================================================

	/**
	 * Load all the classes referenced in the given Java type descriptor.
	 *
	 * @param descriptor
	 *                   Java type descriptor
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void loadClasses(String descriptor) throws COASTALException {
		try {
			state.loadClasses(descriptor);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	// ======================================================================
	//
	// SPECIAL ROUTINES
	//
	// ======================================================================

	/**
	 * Handle a call to <code>System.exit()</code> symbolically.
	 *
	 * @param status
	 *               exit status
	 * @throws COASTALException
	 *                          if any exception occurs during the execution of the
	 *                          call
	 */
	public static void systemExit(int status) throws COASTALException {
		try {
			state.systemExit(status);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

}
