package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;

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
	
	int createSymbolicInt(int currentValue, int uniqueId);

	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	boolean getRecordMode();

	boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue);
	
	byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue);
	
	short getConcreteShort(int triggerIndex, int index, int address, short currentValue);
	
	char getConcreteChar(int triggerIndex, int index, int address, char currentValue);

	int getConcreteInt(int triggerIndex, int index, int address, int currentValue);
	
	long getConcreteLong(int triggerIndex, int index, int address, long currentValue);
	
	float getConcreteFloat(int triggerIndex, int index, int address, float currentValue);
	
	double getConcreteDouble(int triggerIndex, int index, int address, double currentValue);
	
	String getConcreteString(int triggerIndex, int index, int address, String currentValue);

	boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue);
	
	byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue);
	
	short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue);
	
	char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue);
	
	int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue);
	
	long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue);
	
	float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue);
	
	double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue);
	
	String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue);
	
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

	void label(int instr, String label);
	
	void insn(int instr, int opcode) throws SymbolicException;

	void intInsn(int instr, int opcode, int operand) throws SymbolicException;

	void varInsn(int instr, int opcode, int var) throws SymbolicException;

	void typeInsn(int instr, int opcode) throws SymbolicException;

	void fieldInsn(int instr, int opcode, String owner, String name, String descriptor) throws SymbolicException;

	void methodInsn(int instr, int opcode, String owner, String name, String descriptor) throws SymbolicException;

	void invokeDynamicInsn(int instr, int opcode) throws SymbolicException;

	void jumpInsn(int instr, int opcode) throws SymbolicException;

	void jumpInsn(int value, int instr, int opcode) throws SymbolicException;
	
	void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException;
	
	void postJumpInsn(int instr, int opcode) throws SymbolicException;

	void ldcInsn(int instr, int opcode, Object value) throws SymbolicException;

	void iincInsn(int instr, int var, int increment) throws SymbolicException;

	void tableSwitchInsn(int instr, int opcode) throws SymbolicException;

	void tableCaseInsn(int min, int max, int value) throws SymbolicException;

	void lookupSwitchInsn(int instr, int opcode) throws SymbolicException;

	void multiANewArrayInsn(int instr, int opcode) throws SymbolicException;

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	void noException() throws SymbolicException;
	
	void startCatch(int instr) throws SymbolicException;

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
