package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.green.expr.Expression;

public interface State {

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	String getNewVariableName();

	Expression getStringLength(int stringId);

	Expression getStringChar(int stringId, int index);

	void push(Expression expr);

	Expression pop();

	void pushExtraConjunct(Expression extraConjunct);
	
	int createSymbolicInt(int oldValue, int uniqueId);

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	boolean getRecordMode();

	int getConcreteInt(int triggerIndex, int index, int address, int currentValue);

	char getConcreteChar(int triggerIndex, int index, int address, char currentValue);

	byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue);

	String getConcreteString(int triggerIndex, int index, int address, String currentValue);

	int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue);

	char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue);

	byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue);

	void triggerMethod(int methodNumber);

	void startMethod(int methodNumber, int argCount);

	void returnValue(boolean returnValue);

	void returnValue(char returnValue);

	void returnValue(double returnValue);

	void returnValue(float returnValue);
	
	void returnValue(int returnValue);
	
	void returnValue(long returnValue);
	
	void returnValue(short returnValue);
	
	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	void linenumber(int instr, int line);

	void insn(int instr, int opcode) throws LimitConjunctException;

	void intInsn(int instr, int opcode, int operand) throws LimitConjunctException;

	void varInsn(int instr, int opcode, int var) throws LimitConjunctException;

	void typeInsn(int instr, int opcode) throws LimitConjunctException;

	void fieldInsn(int instr, int opcode, String owner, String name, String descriptor) throws LimitConjunctException;

	void methodInsn(int instr, int opcode, String owner, String name, String descriptor) throws LimitConjunctException;

	void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException;

	void jumpInsn(int instr, int opcode) throws LimitConjunctException;

	void postJumpInsn(int instr, int opcode) throws LimitConjunctException;

	void ldcInsn(int instr, int opcode, Object value) throws LimitConjunctException;

	void iincInsn(int instr, int var, int increment) throws LimitConjunctException;

	void tableSwitchInsn(int instr, int opcode) throws LimitConjunctException;

	void tableCaseInsn(int min, int max, int value) throws LimitConjunctException;

	void lookupSwitchInsn(int instr, int opcode) throws LimitConjunctException;

	void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException;

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	void noException() throws LimitConjunctException;
	
	void startCatch(int instr) throws LimitConjunctException;

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	void stop();
	
	void stop(String message);
	
	void mark(int marker);

	void mark(String marker);

}
