package za.ac.sun.cs.coastal.run;

import za.ac.sun.cs.green.expr.Expression;

public class SymbolicVM {

	private static SymbolicState symbolicState = null;

	public static void setState(SymbolicState symbolicState) {
		SymbolicVM.symbolicState = symbolicState;
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	public static String getNewVariableName() {
		return symbolicState.getNewVariableName();
	}

	public static Expression getStringLength(int stringId) {
		return symbolicState.getStringLength(stringId);
	}

	public static Expression getStringChar(int stringId, int index) {
		return symbolicState.getStringChar(stringId, index);
	}

	public static void push(Expression expr) {
		symbolicState.push(expr);
	}

	public static Expression pop() {
		return symbolicState.pop();
	}

	public static void pushExtraConjunct(Expression extraConjunct) {
		symbolicState.pushExtraConjunct(extraConjunct);
	}

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	public static boolean getRecordMode() {
		return symbolicState.getRecordMode();
	}

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return symbolicState.getConcreteInt(triggerIndex, index, address, currentValue);
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return symbolicState.getConcreteChar(triggerIndex, index, address, currentValue);
	}

	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return symbolicState.getConcreteByte(triggerIndex, index, address, currentValue);
	}
	
	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		return symbolicState.getConcreteString(triggerIndex, index, address, currentValue);
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return symbolicState.getConcreteIntArray(triggerIndex, index, address, currentValue);
	}

	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return symbolicState.getConcreteCharArray(triggerIndex, index, address, currentValue);
	}
	
	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return symbolicState.getConcreteByteArray(triggerIndex, index, address, currentValue);
	}
	
	public static void triggerMethod(int methodNumber) {
		symbolicState.triggerMethod(methodNumber);
	}

	public static void startMethod(int methodNumber, int argCount) {
		symbolicState.startMethod(methodNumber, argCount);
	}

	public static void returnValue(boolean returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(char returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(double returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(float returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(int returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(long returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	public static void returnValue(short returnValue) {
		symbolicState.returnValue(returnValue);
	}
	
	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	public static void linenumber(int instr, int line) {
		symbolicState.linenumber(instr, line);
	}

	public static void insn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.insn(instr, opcode);
	}

	public static void intInsn(int instr, int opcode, int operand) throws LimitConjunctException {
		symbolicState.intInsn(instr, opcode, operand);
	}

	public static void varInsn(int instr, int opcode, int var) throws LimitConjunctException {
		symbolicState.varInsn(instr, opcode, var);
	}

	public static void typeInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.typeInsn(instr, opcode);
	}

	public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		symbolicState.fieldInsn(instr, opcode, owner, name, descriptor);
	}

	public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		symbolicState.methodInsn(instr, opcode, owner, name, descriptor);
	}

	public static void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.invokeDynamicInsn(instr, opcode);
	}

	public static void jumpInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.jumpInsn(instr, opcode);
	}

	public static void postJumpInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.postJumpInsn(instr, opcode);
	}

	public static void ldcInsn(int instr, int opcode, Object value) throws LimitConjunctException {
		symbolicState.ldcInsn(instr, opcode, value);
	}

	public static void iincInsn(int instr, int var, int increment) throws LimitConjunctException {
		symbolicState.iincInsn(instr, var, increment);
	}

	public static void tableSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.tableSwitchInsn(instr, opcode);
	}

	public static void tableCaseInsn(int min, int max, int value) throws LimitConjunctException {
		symbolicState.tableCaseInsn(min, max, value);
	}

	public static void lookupSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.lookupSwitchInsn(instr, opcode);
	}

	public static void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException {
		symbolicState.multiANewArrayInsn(instr, opcode);
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	public static void noException() throws LimitConjunctException {
		symbolicState.noException();
	}
	
	public static void startCatch(int instr) throws LimitConjunctException {
		symbolicState.startCatch(instr);
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	public static void stop() {
		symbolicState.stop();
	}

	public static void stop(String message) {
		symbolicState.stop(message);
	}

	public static void mark(int marker) {
		symbolicState.mark(marker);
	}

	public static void mark(String marker) {
		symbolicState.mark(marker);
	}

}
