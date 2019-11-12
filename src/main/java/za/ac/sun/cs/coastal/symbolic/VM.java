/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.ValueFactory.Value;
import za.ac.sun.cs.coastal.symbolic.exceptions.COASTALException;
import za.ac.sun.cs.coastal.symbolic.exceptions.ControlException;
import za.ac.sun.cs.coastal.symbolic.exceptions.ErrorException;

public class VM {

	public static State state = null;

	public static void setState(State state) {
		VM.state = state;
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	public static String getNewVariableName() throws COASTALException {
		try {
			return state.getNewVariableName();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static Value getStringLength(int stringId) throws COASTALException {
		try {
			return state.getStringLength(stringId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static Value getStringChar(int stringId, int index) throws COASTALException {
		try {
			return state.getStringChar(stringId, index);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void push(Value expr) throws COASTALException {
		try {
			state.push(expr);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static Value pop() throws COASTALException {
		try {
			return state.pop();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void pushExtraConjunct(Expression extraConjunct) throws COASTALException {
		try {
			state.pushExtraCondition(extraConjunct);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static int createSymbolicInt(int oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicInt(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static short createSymbolicShort(short oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicShort(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static boolean createSymbolicBoolean(boolean oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicBoolean(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static byte createSymbolicByte(byte oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicByte(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static char createSymbolicChar(char oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicChar(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static long createSymbolicLong(long oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicLong(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static float createSymbolicFloat(float oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicFloat(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static double createSymbolicDouble(double oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicDouble(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static String createSymbolicString(String oldValue, int uniqueId) throws COASTALException {
		try {
			return state.createSymbolicString(oldValue, uniqueId);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static int makeSymbolicInt(String newName) throws COASTALException {
		try {
			return state.makeSymbolicInt(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static short makeSymbolicShort(String newName) throws COASTALException {
		try {
			return state.makeSymbolicShort(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static boolean makeSymbolicBoolean(String newName) throws COASTALException {
		try {
			return state.makeSymbolicBoolean(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static byte makeSymbolicByte(String newName) throws COASTALException {
		try {
			return state.makeSymbolicByte(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static char makeSymbolicChar(String newName) throws COASTALException {
		try {
			return state.makeSymbolicChar(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static long makeSymbolicLong(String newName) throws COASTALException {
		try {
			return state.makeSymbolicLong(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static float makeSymbolicFloat(String newName) throws COASTALException {
		try {
			return state.makeSymbolicFloat(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
	public static double makeSymbolicDouble(String newName) throws COASTALException {
		try {
			return state.makeSymbolicDouble(newName);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
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

	public static boolean getRecordingMode() throws COASTALException {
		try {
			return state.getRecordingMode();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) throws COASTALException {
		try {
			return state.getConcreteBoolean(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) throws COASTALException {
		try {
			return state.getConcreteByte(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static short getConcreteShort(int triggerIndex, int index, int address, short currentValue) throws COASTALException {
		try {
			return state.getConcreteShort(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) throws COASTALException {
		try {
			return state.getConcreteChar(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) throws COASTALException {
		try {
			return state.getConcreteInt(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static long getConcreteLong(int triggerIndex, int index, int address, long currentValue) throws COASTALException {
		try {
			return state.getConcreteLong(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) throws COASTALException {
		try {
			return state.getConcreteFloat(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) throws COASTALException {
		try {
			return state.getConcreteDouble(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) throws COASTALException {
		try {
			return state.getConcreteString(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) throws COASTALException {
		try {
			return state.getConcreteBooleanArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) throws COASTALException {
		try {
			return state.getConcreteByteArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) throws COASTALException {
		try {
			return state.getConcreteShortArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) throws COASTALException {
		try {
			return state.getConcreteCharArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) throws COASTALException {
		try {
			return state.getConcreteIntArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) throws COASTALException {
		try {
			return state.getConcreteLongArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) throws COASTALException {
		try {
			return state.getConcreteFloatArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) throws COASTALException {
		try {
			return state.getConcreteDoubleArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) throws COASTALException {
		try {
			return state.getConcreteStringArray(triggerIndex, index, address, currentValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) throws COASTALException {
		try {
			state.triggerMethod(methodNumber, triggerIndex, isStatic);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void startMethod(int methodNumber, int argCount) throws COASTALException {
		try {
			state.startMethod(methodNumber, argCount);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(boolean returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(char returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(double returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(float returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(int returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void returnValue(long returnValue) throws COASTALException {
		try {
			state.returnValue(returnValue);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

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

	public static void linenumber(int instr, int line, String filename) throws COASTALException {
		try {
			state.linenumber(instr, line, filename);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void label(int instr, String label) throws COASTALException {
		try {
			state.label(instr, label);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void insn(int instr, int opcode) throws COASTALException {
		try {
			state.insn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void intInsn(int instr, int opcode, int operand) throws COASTALException {
		try {
			state.intInsn(instr, opcode, operand);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void varInsn(int instr, int opcode, int var) throws COASTALException {
		try {
			state.varInsn(instr, opcode, var);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void typeInsn(int instr, int opcode) throws COASTALException {
		try {
			state.typeInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

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

	public static void invokeDynamicInsn(int instr, int opcode) throws COASTALException {
		try {
			state.invokeDynamicInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void jumpInsn(int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void jumpInsn(int value, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void jumpInsn(Object value, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void jumpInsn(int value1, int value2, int instr, int opcode) throws COASTALException {
		try {
			state.jumpInsn(value1, value2, instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void postJumpInsn(int instr, int opcode) throws COASTALException {
		try {
			state.postJumpInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void ldcInsn(int instr, int opcode, int value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void ldcInsn(int instr, int opcode, long value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void ldcInsn(int instr, int opcode, float value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void ldcInsn(int instr, int opcode, double value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void ldcInsn(int instr, int opcode, Object value) throws COASTALException {
		try {
			state.ldcInsn(instr, opcode, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void iincInsn(int instr, int var, int increment) throws COASTALException {
		try {
			state.iincInsn(instr, var, increment);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void tableSwitchInsn(int instr, int opcode) throws COASTALException {
		try {
			state.tableSwitchInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void tableCaseInsn(int min, int max, int value) throws COASTALException {
		try {
			state.tableCaseInsn(min, max, value);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void lookupSwitchInsn(int instr, int opcode) throws COASTALException {
		try {
			state.lookupSwitchInsn(instr, opcode);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void lookupCaseInsn(int id, int choice) throws COASTALException {
		try {
			state.lookupCaseInsn(id, choice);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}
	
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

	public static void noException() throws COASTALException {
		try {
			state.noException();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

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

	public static void stop() throws COASTALException {
		try {
			state.stop();
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void stop(String message) throws COASTALException {
		try {
			state.stop(message);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void mark(int marker) throws COASTALException {
		try {
			state.mark(marker);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void mark(String marker) throws COASTALException {
		try {
			state.mark(marker);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

	public static void printPC(String label) throws COASTALException {
		try {
			state.printPC(label);
		} catch (ControlException x) {
			throw x;
		} catch (Throwable x) {
			throw new ErrorException(x);
		}
	}

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
