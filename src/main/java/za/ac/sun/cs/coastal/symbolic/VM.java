/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.symbolic;

import java.util.concurrent.ConcurrentHashMap;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.ValueFactory.Value;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;

public class VM {

	public static final ConcurrentHashMap<Long, State> STATE_MAP = new ConcurrentHashMap<>();

	public static void setState(State state) {
		STATE_MAP.put(Thread.currentThread().getId(), state);
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	public static String getNewVariableName() {
		return STATE_MAP.get(Thread.currentThread().getId()).getNewVariableName();
	}

	public static Value getStringLength(int stringId) {
		return STATE_MAP.get(Thread.currentThread().getId()).getStringLength(stringId);
	}

	public static Value getStringChar(int stringId, int index) {
		return STATE_MAP.get(Thread.currentThread().getId()).getStringChar(stringId, index);
	}

	public static void push(Value expr) {
		STATE_MAP.get(Thread.currentThread().getId()).push(expr);
	}

	public static Value pop() {
		return STATE_MAP.get(Thread.currentThread().getId()).pop();
	}

	public static void pushExtraConjunct(Expression extraConjunct) {
		STATE_MAP.get(Thread.currentThread().getId()).pushExtraCondition(extraConjunct);
	}

	public static int createSymbolicInt(int oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicInt(oldValue, uniqueId);
	}

	public static short createSymbolicShort(short oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicShort(oldValue, uniqueId);
	}

	public static boolean createSymbolicBoolean(boolean oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicBoolean(oldValue, uniqueId);
	}

	public static byte createSymbolicByte(byte oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicByte(oldValue, uniqueId);
	}

	public static char createSymbolicChar(char oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicChar(oldValue, uniqueId);
	}

	public static long createSymbolicLong(long oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicLong(oldValue, uniqueId);
	}

	public static float createSymbolicFloat(float oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicFloat(oldValue, uniqueId);
	}

	public static double createSymbolicDouble(double oldValue, int uniqueId) {
		return STATE_MAP.get(Thread.currentThread().getId()).createSymbolicDouble(oldValue, uniqueId);
	}

	public static int makeSymbolicInt(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicInt(newName);
	}

	public static short makeSymbolicShort(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicShort(newName);
	}

	public static boolean makeSymbolicBoolean(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicBoolean(newName);
	}

	public static byte makeSymbolicByte(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicByte(newName);
	}

	public static char makeSymbolicChar(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicChar(newName);
	}

	public static long makeSymbolicLong(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicLong(newName);
	}

	public static float makeSymbolicFloat(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicFloat(newName);
	}

	public static double makeSymbolicDouble(String newName) {
		return STATE_MAP.get(Thread.currentThread().getId()).makeSymbolicDouble(newName);
	}

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	public static boolean getRecordingMode() {
		return STATE_MAP.get(Thread.currentThread().getId()).getRecordingMode();
	}

	public static boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteBoolean(triggerIndex, index, address,
				currentValue);
	}

	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteByte(triggerIndex, index, address,
				currentValue);
	}

	public static short getConcreteShort(int triggerIndex, int index, int address, short currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteShort(triggerIndex, index, address,
				currentValue);
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteChar(triggerIndex, index, address,
				currentValue);
	}

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteInt(triggerIndex, index, address, currentValue);
	}

	public static long getConcreteLong(int triggerIndex, int index, int address, long currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteLong(triggerIndex, index, address,
				currentValue);
	}

	public static float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteFloat(triggerIndex, index, address,
				currentValue);
	}

	public static double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteDouble(triggerIndex, index, address,
				currentValue);
	}

	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteString(triggerIndex, index, address,
				currentValue);
	}

	public static boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteBooleanArray(triggerIndex, index, address,
				currentValue);
	}

	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteByteArray(triggerIndex, index, address,
				currentValue);
	}

	public static short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteShortArray(triggerIndex, index, address,
				currentValue);
	}

	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteCharArray(triggerIndex, index, address,
				currentValue);
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteIntArray(triggerIndex, index, address,
				currentValue);
	}

	public static long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteLongArray(triggerIndex, index, address,
				currentValue);
	}

	public static float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteFloatArray(triggerIndex, index, address,
				currentValue);
	}

	public static double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteDoubleArray(triggerIndex, index, address,
				currentValue);
	}

	public static String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) {
		return STATE_MAP.get(Thread.currentThread().getId()).getConcreteStringArray(triggerIndex, index, address,
				currentValue);
	}

	public static void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) {
		STATE_MAP.get(Thread.currentThread().getId()).triggerMethod(methodNumber, triggerIndex, isStatic);
	}

	public static void startMethod(int methodNumber, int argCount) {
		STATE_MAP.get(Thread.currentThread().getId()).startMethod(methodNumber, argCount);
	}

	public static void returnValue(boolean returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(char returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(double returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(float returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(int returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(long returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	public static void returnValue(short returnValue) {
		STATE_MAP.get(Thread.currentThread().getId()).returnValue(returnValue);
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	public static void linenumber(int instr, int line, String filename) {
		STATE_MAP.get(Thread.currentThread().getId()).linenumber(instr, line, filename);
	}

	public static void label(int instr, String label) {
		STATE_MAP.get(Thread.currentThread().getId()).label(instr, label);
	}

	public static void insn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).insn(instr, opcode);
	}

	public static void intInsn(int instr, int opcode, int operand) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).intInsn(instr, opcode, operand);
	}

	public static void varInsn(int instr, int opcode, int var) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).varInsn(instr, opcode, var);
	}

	public static void typeInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).typeInsn(instr, opcode);
	}

	public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).fieldInsn(instr, opcode, owner, name, descriptor);
	}

	public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).methodInsn(instr, opcode, owner, name, descriptor);
	}

	public static void invokeDynamicInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).invokeDynamicInsn(instr, opcode);
	}

	public static void jumpInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).jumpInsn(instr, opcode);
	}

	public static void jumpInsn(int value, int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).jumpInsn(value, instr, opcode);
	}

	public static void jumpInsn(Object value, int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).jumpInsn(value, instr, opcode);
	}

	public static void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).jumpInsn(value1, value2, instr, opcode);
	}

	public static void postJumpInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).postJumpInsn(instr, opcode);
	}

	public static void ldcInsn(int instr, int opcode, int value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, long value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, float value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, double value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).ldcInsn(instr, opcode, value);
	}

	public static void ldcInsn(int instr, int opcode, Object value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).ldcInsn(instr, opcode, value);
	}

	public static void iincInsn(int instr, int var, int increment) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).iincInsn(instr, var, increment);
	}

	public static void tableSwitchInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).tableSwitchInsn(instr, opcode);
	}

	public static void tableCaseInsn(int min, int max, int value) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).tableCaseInsn(min, max, value);
	}

	public static void lookupSwitchInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).lookupSwitchInsn(instr, opcode);
	}

	public static void lookupCaseInsn(int id, int choice) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).lookupCaseInsn(id, choice);
	}

	public static void multiANewArrayInsn(int instr, int opcode) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).multiANewArrayInsn(instr, opcode);
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	public static void noException() throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).noException();
	}

	public static void startCatch(int instr) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).startCatch(instr);
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	public static void stop() {
		STATE_MAP.get(Thread.currentThread().getId()).stop();
	}

	public static void stop(String message) {
		STATE_MAP.get(Thread.currentThread().getId()).stop(message);
	}

	public static void mark(int marker) {
		STATE_MAP.get(Thread.currentThread().getId()).mark(marker);
	}

	public static void mark(String marker) {
		STATE_MAP.get(Thread.currentThread().getId()).mark(marker);
	}

	public static void printPC(String label) {
		STATE_MAP.get(Thread.currentThread().getId()).printPC(label);
	}

	public static void printPC() {
		STATE_MAP.get(Thread.currentThread().getId()).printPC();
	}

	// ======================================================================
	//
	// CLASS LOADING
	//
	// ======================================================================

	public static void loadClasses(String descriptor) {
		STATE_MAP.get(Thread.currentThread().getId()).loadClasses(descriptor);
	}

	// ======================================================================
	//
	// SPECIAL ROUTINES
	//
	// ======================================================================

	public static void systemExit(int status) throws SymbolicException {
		STATE_MAP.get(Thread.currentThread().getId()).systemExit(status);
	}

}
