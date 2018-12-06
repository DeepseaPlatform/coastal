package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.green.expr.Expression;

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

	public static Expression getStringLength(int stringId) {
		return state.getStringLength(stringId);
	}

	public static Expression getStringChar(int stringId, int index) {
		return state.getStringChar(stringId, index);
	}

	public static void push(Expression expr) {
		state.push(expr);
	}

	public static Expression pop() {
		return state.pop();
	}

	public static void pushExtraConjunct(Expression extraConjunct) {
		state.pushExtraConjunct(extraConjunct);
	}

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	public static boolean getRecordMode() {
		return state.getRecordMode();
	}

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return state.getConcreteInt(triggerIndex, index, address, currentValue);
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return state.getConcreteChar(triggerIndex, index, address, currentValue);
	}

	public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return state.getConcreteByte(triggerIndex, index, address, currentValue);
	}
	
	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		return state.getConcreteString(triggerIndex, index, address, currentValue);
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return state.getConcreteIntArray(triggerIndex, index, address, currentValue);
	}

	public static char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return state.getConcreteCharArray(triggerIndex, index, address, currentValue);
	}
	
	public static byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return state.getConcreteByteArray(triggerIndex, index, address, currentValue);
	}
	
	public static void triggerMethod(int methodNumber) {
		state.triggerMethod(methodNumber);
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

	public static void linenumber(int instr, int line) {
		state.linenumber(instr, line);
	}

	public static void insn(int instr, int opcode) throws LimitConjunctException {
		state.insn(instr, opcode);
	}

	public static void intInsn(int instr, int opcode, int operand) throws LimitConjunctException {
		state.intInsn(instr, opcode, operand);
	}

	public static void varInsn(int instr, int opcode, int var) throws LimitConjunctException {
		state.varInsn(instr, opcode, var);
	}

	public static void typeInsn(int instr, int opcode) throws LimitConjunctException {
		state.typeInsn(instr, opcode);
	}

	public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		state.fieldInsn(instr, opcode, owner, name, descriptor);
	}

	public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		state.methodInsn(instr, opcode, owner, name, descriptor);
	}

	public static void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException {
		state.invokeDynamicInsn(instr, opcode);
	}

	public static void jumpInsn(int instr, int opcode) throws LimitConjunctException {
		state.jumpInsn(instr, opcode);
	}

	public static void postJumpInsn(int instr, int opcode) throws LimitConjunctException {
		state.postJumpInsn(instr, opcode);
	}

	public static void ldcInsn(int instr, int opcode, Object value) throws LimitConjunctException {
		state.ldcInsn(instr, opcode, value);
	}

	public static void iincInsn(int instr, int var, int increment) throws LimitConjunctException {
		state.iincInsn(instr, var, increment);
	}

	public static void tableSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		state.tableSwitchInsn(instr, opcode);
	}

	public static void tableCaseInsn(int min, int max, int value) throws LimitConjunctException {
		state.tableCaseInsn(min, max, value);
	}

	public static void lookupSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		state.lookupSwitchInsn(instr, opcode);
	}

	public static void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException {
		state.multiANewArrayInsn(instr, opcode);
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	public static void noException() throws LimitConjunctException {
		state.noException();
	}
	
	public static void startCatch(int instr) throws LimitConjunctException {
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

}
