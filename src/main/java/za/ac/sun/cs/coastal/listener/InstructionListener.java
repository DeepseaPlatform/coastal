package za.ac.sun.cs.coastal.listener;

import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;

public interface InstructionListener extends Listener {

	void changeInstrumentationManager(InstrumentationClassManager classManager);

	void enterMethod(int methodNumber);

	void exitMethod(int methodNumber);

	void linenumber(int instr, int opcode);

	void insn(int instr, int opcode);

	void intInsn(int instr, int opcode, int operand);

	void varInsn(int instr, int opcode, int var);

	void typeInsn(int instr, int opcode);

	void fieldInsn(int instr, int opcode, String owner, String name, String descriptor);

	void methodInsn(int instr, int opcode, String owner, String name, String descriptor);

	void invokeDynamicInsn(int instr, int opcode);

	void jumpInsn(int instr, int opcode);

	void postJumpInsn(int instr, int opcode);

	void ldcInsn(int instr, int opcode, Object value);

	void iincInsn(int instr, int var, int increment);

	void tableSwitchInsn(int instr, int opcode);

	void lookupSwitchInsn(int instr, int opcode);

	void multiANewArrayInsn(int instr, int opcode);

}
