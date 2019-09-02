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
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;

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

	public static String getNewVariableName() {
		return state.getNewVariableName();
	}

	public static Value getStringLength(int stringId) {
		return state.getStringLength(stringId);
	}

	public static Value getStringChar(int stringId, int index) {
		return state.getStringChar(stringId, index);
	}

	public static void push(Value expr) {
		state.push(expr);
	}

	public static Value pop() {
		return state.pop();
	}

	public static void pushExtraConjunct(Expression extraConjunct) {
		state.pushExtraCondition(extraConjunct);
	}

	public static int createSymbolicInt(int oldValue, int uniqueId) {
		return state.createSymbolicInt(oldValue, uniqueId);
	}

	public static short createSymbolicShort(short oldValue, int uniqueId) {
		return state.createSymbolicShort(oldValue, uniqueId);
	}

	public static boolean createSymbolicBoolean(boolean oldValue, int uniqueId) {
		return state.createSymbolicBoolean(oldValue, uniqueId);
	}

	public static byte createSymbolicByte(byte oldValue, int uniqueId) {
		return state.createSymbolicByte(oldValue, uniqueId);
	}

	public static char createSymbolicChar(char oldValue, int uniqueId) {
		return state.createSymbolicChar(oldValue, uniqueId);
	}

	public static long createSymbolicLong(long oldValue, int uniqueId) {
		return state.createSymbolicLong(oldValue, uniqueId);
	}

	public static float createSymbolicFloat(float oldValue, int uniqueId) {
		return state.createSymbolicFloat(oldValue, uniqueId);
	}

	public static double createSymbolicDouble(double oldValue, int uniqueId) {
		return state.createSymbolicDouble(oldValue, uniqueId);
	}

	public static int makeSymbolicInt(String newName) {
		return state.makeSymbolicInt(newName);
	}
	
	public static short makeSymbolicShort(String newName) {
		return state.makeSymbolicShort(newName);
	}
	
	public static boolean makeSymbolicBoolean(String newName) {
		return state.makeSymbolicBoolean(newName);
	}
	
	public static byte makeSymbolicByte(String newName) {
		return state.makeSymbolicByte(newName);
	}
	
	public static char makeSymbolicChar(String newName) {
		return state.makeSymbolicChar(newName);
	}
	
	public static long makeSymbolicLong(String newName) {
		return state.makeSymbolicLong(newName);
	}
	
	public static float makeSymbolicFloat(String newName) {
		return state.makeSymbolicFloat(newName);
	}
	
	public static double makeSymbolicDouble(String newName) {
		return state.makeSymbolicDouble(newName);
	}
	
	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	public static boolean getRecordingMode() {
		return state.getRecordingMode();
	}

	public static boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		return state.getConcreteBoolean(triggerIndex, index, address, currentValue);
	}

	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return state.getConcreteByte(triggerIndex, index, address, currentValue);
	}

	public static short getConcreteShort(int triggerIndex, int index, int address, short currentValue) {
		return state.getConcreteShort(triggerIndex, index, address, currentValue);
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return state.getConcreteChar(triggerIndex, index, address, currentValue);
	}

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return state.getConcreteInt(triggerIndex, index, address, currentValue);
	}

	public static long getConcreteLong(int triggerIndex, int index, int address, long currentValue) {
		return state.getConcreteLong(triggerIndex, index, address, currentValue);
	}

	public static float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) {
		return state.getConcreteFloat(triggerIndex, index, address, currentValue);
	}

	public static double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) {
		return state.getConcreteDouble(triggerIndex, index, address, currentValue);
	}

	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		return state.getConcreteString(triggerIndex, index, address, currentValue);
	}

	public static boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return state.getConcreteBooleanArray(triggerIndex, index, address, currentValue);
	}

	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return state.getConcreteByteArray(triggerIndex, index, address, currentValue);
	}

	public static short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return state.getConcreteShortArray(triggerIndex, index, address, currentValue);
	}

	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return state.getConcreteCharArray(triggerIndex, index, address, currentValue);
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return state.getConcreteIntArray(triggerIndex, index, address, currentValue);
	}

	public static long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return state.getConcreteLongArray(triggerIndex, index, address, currentValue);
	}

	public static float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		return state.getConcreteFloatArray(triggerIndex, index, address, currentValue);
	}

	public static double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		return state.getConcreteDoubleArray(triggerIndex, index, address, currentValue);
	}

	public static String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) {
		return state.getConcreteStringArray(triggerIndex, index, address, currentValue);
	}

	public static void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) {
		state.triggerMethod(methodNumber, triggerIndex, isStatic);
	}

	public static void startMethod(int methodNumber, int argCount) {
		state.startMethod(methodNumber, argCount);
	}

	public static void returnValue(boolean returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(char returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(double returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(float returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(int returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(long returnValue) {
		state.returnValue(returnValue);
	}

	public static void returnValue(short returnValue) {
		state.returnValue(returnValue);
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	public static void linenumber(int instr, int line, String filename) {
		state.linenumber(instr, line, filename);
	}

	public static void label(int instr, String label) {
		state.label(instr, label);
	}

	public static void insn(int instr, int opcode) throws SymbolicException {
		state.insn(instr, opcode);
	}

	public static void intInsn(int instr, int opcode, int operand) throws SymbolicException {
		state.intInsn(instr, opcode, operand);
	}

	public static void varInsn(int instr, int opcode, int var) throws SymbolicException {
		state.varInsn(instr, opcode, var);
	}

	public static void typeInsn(int instr, int opcode) throws SymbolicException {
		state.typeInsn(instr, opcode);
	}

	public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		state.fieldInsn(instr, opcode, owner, name, descriptor);
	}

	public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		state.methodInsn(instr, opcode, owner, name, descriptor);
	}

	public static void invokeDynamicInsn(int instr, int opcode) throws SymbolicException {
		state.invokeDynamicInsn(instr, opcode);
	}

	public static void jumpInsn(int instr, int opcode) throws SymbolicException {
		state.jumpInsn(instr, opcode);
	}

	public static void jumpInsn(int value, int instr, int opcode) throws SymbolicException {
		state.jumpInsn(value, instr, opcode);
	}

	public static void jumpInsn(Object value, int instr, int opcode) throws SymbolicException {
		state.jumpInsn(value, instr, opcode);
	}

	public static void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException {
		state.jumpInsn(value1, value2, instr, opcode);
	}

	public static void postJumpInsn(int instr, int opcode) throws SymbolicException {
		state.postJumpInsn(instr, opcode);
	}

	public static void ldcInsn(int instr, int opcode, int value) throws SymbolicException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, long value) throws SymbolicException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, float value) throws SymbolicException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, double value) throws SymbolicException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, Object value) throws SymbolicException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void iincInsn(int instr, int var, int increment) throws SymbolicException {
		state.iincInsn(instr, var, increment);
	}

	public static void tableSwitchInsn(int instr, int opcode) throws SymbolicException {
		state.tableSwitchInsn(instr, opcode);
	}

	public static void tableCaseInsn(int min, int max, int value) throws SymbolicException {
		state.tableCaseInsn(min, max, value);
	}

	public static void lookupSwitchInsn(int instr, int opcode) throws SymbolicException {
		state.lookupSwitchInsn(instr, opcode);
	}

	public static void lookupCaseInsn(int id, int choice) throws SymbolicException {
		state.lookupCaseInsn(id, choice);
	}
	
	public static void multiANewArrayInsn(int instr, int opcode) throws SymbolicException {
		state.multiANewArrayInsn(instr, opcode);
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	public static void noException() throws SymbolicException {
		state.noException();
	}

	public static void startCatch(int instr) throws SymbolicException {
		state.startCatch(instr);
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	public static void stop() {
		state.stop();
	}

	public static void stop(String message) {
		state.stop(message);
	}

	public static void mark(int marker) {
		state.mark(marker);
	}

	public static void mark(String marker) {
		state.mark(marker);
	}

	public static void printPC(String label) {
		state.printPC(label);
	}

	public static void printPC() {
		state.printPC();
	}

	// ======================================================================
	//
	// CLASS LOADING
	//
	// ======================================================================

	public static void loadClasses(String descriptor) {
		state.loadClasses(descriptor);
	}

	// ======================================================================
	//
	// SPECIAL ROUTINES
	//
	// ======================================================================

	public static void systemExit(int status) throws SymbolicException {
		state.systemExit(status);
	}

}
