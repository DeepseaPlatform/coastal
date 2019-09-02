/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.symbolic;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Symbolic;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.ValueFactory.Value;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;

/**
 * The contract that defines the behaviour of an abstract state.
 */
public abstract class State {

	// ======================================================================
	//
	// SHARED CONSTANTS
	//
	// ======================================================================

	/**
	 * Separator for fields. For example, the characters of a symbolic object with
	 * address 0 are called {@code 0/a}, {@code 0/b}, {@code 0/c}, ...
	 */
	public static final String FIELD_SEPARATOR = "/";

	/**
	 * Separator for array elements. For example, the elements of an array named
	 * {@code A} are called {@code A_D_0}, {@code A_D_1}, {@code A_D_2}, ...
	 */
	public static final String INDEX_SEPARATOR = "$"; // "_D_"

	/**
	 * Separator for string characters. For example, the characters of a string
	 * named {@code X} are called {@code X_H_0}, {@code X_H_1}, {@code X_H_2}, ...
	 */
	public static final String CHAR_SEPARATOR = "!"; // "_H_"

	/**
	 * Prefix for new symbolic variables.
	 */
	public static final String NEW_VAR_PREFIX = "$"; // "U_D_"

	public static final String CREATE_VAR_PREFIX = "N_D_"; // "@"

	// ======================================================================
	//
	// SHARED FIELDS
	//
	// ======================================================================

	/**
	 * The COASTAL that this state belongs to.
	 */
	protected final COASTAL coastal;

	/**
	 * Shortcut to the log instance.
	 */
	protected final Logger log;

	/**
	 * Shortcut to the broker instance.
	 */
	protected final Broker broker;

	/**
	 * The input that triggered this execution.
	 * 
	 * This field is not final, because
	 * {@link za.ac.sun.cs.coastal.surfer.TraceState} resets it without creating a
	 * new instance.
	 */
	protected Input input;

	/**
	 * The path constructed for this execution thus far.
	 */
	protected Path path = null;

	/**
	 * Whether or not symbolic tracking is switched on/off. When on, the state
	 * mirrors the execution of the system-under-test.
	 */
	private boolean trackingMode = false;

	/**
	 * Whether or not symbolic recording is switched on/off. When on, symbolic
	 * information (such as path conditions) are recorded.
	 */
	private boolean recordingMode = false;

	// ======================================================================
	//
	// CONSTRUCTOR & SHARED METHODS
	//
	// ======================================================================

	/**
	 * Construct a new state to record an execution.
	 * 
	 * @param coastal the COASTAL instance that initiated the execution
	 * @param input   input values for the run
	 */
	public State(COASTAL coastal, Input input) { // throws InterruptedException
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		this.input = input;
	}

	/**
	 * Return the value of the tracking mode flag.
	 * 
	 * @return tracking mode flag
	 */
	public final boolean getTrackingMode() {
		return trackingMode;
	}

	/**
	 * Set a new value for the tracking mode flag.
	 * 
	 * @param trackingMode new value for the tracking mode flag
	 */
	public final void setTrackingMode(boolean trackingMode) {
		this.trackingMode = trackingMode;
	}

	/**
	 * Return the value of the recording mode flag.
	 * 
	 * @return recording mode flag
	 */
	public final boolean getRecordingMode() {
		return recordingMode;
	}

	/**
	 * Set a new value for the recording mode flag.
	 * 
	 * @param recordingMode new value for the recording mode flag
	 */
	public final void setRecordingMode(boolean recordingMode) {
		this.recordingMode = recordingMode;
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	/**
	 * Create a new unique variable name for a symbolic variable.
	 * 
	 * @return new unique variable name
	 */
	public abstract String getNewVariableName();

	/**
	 * Return the symbolic value of the length of a given string.
	 * 
	 * @param stringId identifier for the string
	 * @return symbolic expression for the length of the string
	 */
	public abstract Value getStringLength(int stringId);

	/**
	 * Return the symbolic value of the character at a given index in a given
	 * string.
	 * 
	 * @param stringId identifier for the string
	 * @param index    index of the character
	 * @return symbolic value for the character
	 */
	public abstract Value getStringChar(int stringId, int index);

	/**
	 * Push an expression onto the top of the expression stack of the topmost
	 * invocation frame.
	 * 
	 * @param expr the expression to push onto the current expression stack
	 */
	public abstract void push(Value expr);

	/**
	 * Pop and return the expression on the top of the expression stack of the
	 * topmost invocation frame.
	 * 
	 * @return the expression removed from the current expression stack
	 */
	public abstract Value pop();

	/**
	 * Register an additional constraint that will be added to the passive conjunct
	 * at the next branching point.
	 * 
	 * @param extraCondition constraint to add
	 */
	public abstract void pushExtraCondition(Expression extraCondition);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract boolean createSymbolicBoolean(boolean currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract byte createSymbolicByte(byte currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract short createSymbolicShort(short currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract char createSymbolicChar(char currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract int createSymbolicInt(int currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract long createSymbolicLong(long currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract float createSymbolicFloat(float currentValue, int uniqueId);

	/**
	 * Create a new symbolic variable. It is the responsibility of the caller to
	 * ensure that the identifier is unique.
	 * 
	 * @param currentValue default value to use in case no overriding value exists
	 * @param uniqueId     identifier to assign to the new variable
	 * @return value for the new variable
	 */
	public abstract double createSymbolicDouble(double currentValue, int uniqueId);

	public abstract int makeSymbolicInt(String newName);
	
	public abstract short makeSymbolicShort(String newName);
	
	public abstract boolean makeSymbolicBoolean(String newName);
	
	public abstract byte makeSymbolicByte(String newName);
	
	public abstract char makeSymbolicChar(String newName);
	
	public abstract long makeSymbolicLong(String newName);
	
	public abstract float makeSymbolicFloat(String newName);
	
	public abstract double makeSymbolicDouble(String newName);
	
	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	/*
	 * These methods deal with what happens when a method is invoked and when
	 * control returns from a method. This includes:
	 * 
	 * - picking up constrained inputs for concolic execution
	 * 
	 * - invocation of triggering and non-triggering methods
	 * 
	 * - handling of return values
	 */

	/**
	 * Return the value to use for a {@code boolean} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue);

	/**
	 * Return the value to use for a {@code byte} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue);

	/**
	 * Return the value to use for a {@code short} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract short getConcreteShort(int triggerIndex, int index, int address, short currentValue);

	/**
	 * Return the value to use for a {@code char} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract char getConcreteChar(int triggerIndex, int index, int address, char currentValue);

	/**
	 * Return the value to use for a {@code int} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract int getConcreteInt(int triggerIndex, int index, int address, int currentValue);

	/**
	 * Return the value to use for a {@code long} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract long getConcreteLong(int triggerIndex, int index, int address, long currentValue);

	/**
	 * Return the value to use for a {@code float} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract float getConcreteFloat(int triggerIndex, int index, int address, float currentValue);

	/**
	 * Return the value to use for a {@code double} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract double getConcreteDouble(int triggerIndex, int index, int address, double currentValue);

	/**
	 * Return the value to use for a {@code String} variable.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the value to use during the execution
	 */
	public abstract String getConcreteString(int triggerIndex, int index, int address, String currentValue);

	/**
	 * Return the value to use for an array of {@code boolean} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue);

	/**
	 * Return the value to use for an array of {@code byte} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue);

	/**
	 * Return the value to use for an array of {@code short} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue);

	/**
	 * Return the value to use for an array of {@code char} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue);

	/**
	 * Return the value to use for an array of {@code int} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue);

	/**
	 * Return the value to use for an array of {@code long} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue);

	/**
	 * Return the value to use for an array of {@code float} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue);

	/**
	 * Return the value to use for an array of {@code double} values.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue);

	/**
	 * Return the value to use for an array of strings.
	 * 
	 * @param triggerIndex index of the trigger
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return the values to use during the execution
	 */
	public abstract String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue);

	/**
	 * Switch on symbolic tracking because a trigger method has been invoked (and
	 * tracking is not currently switched on).
	 * 
	 * @param methodNumber unique number of the method
	 * @param triggerIndex index of the matching trigger
	 * @param isStatic whether or not the triggered method is static
	 */
	public abstract void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic);

	/**
	 * Handle the invocation of a non-triggering method. Tracking may or may not be
	 * switched on.
	 * 
	 * @param methodNumber unique number of the method
	 * @param argCount     number of formal parameters for the method
	 */
	public abstract void startMethod(int methodNumber, int argCount);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(boolean returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(char returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(double returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(float returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(int returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(long returnValue);

	/**
	 * Ensure that the return value of a method is placed onto the top of the
	 * expression stack of the topmost invocation frame.
	 * 
	 * @param returnValue return value expression
	 */
	public abstract void returnValue(short returnValue);

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	/*
	 * Not all instructions are instrumented always. In some circumstances,
	 * instrumentation is not required. However, when an instruction *is*
	 * instrumented, it results in a call to one of the methods below.
	 */

	/**
	 * Handle a line number declaration. Line numbers refer to the source file from
	 * which the class was compiler.
	 * 
	 * @param instr the number of the instruction
	 * @param line  the line number
	 */
	public abstract void linenumber(int instr, int line, String filename);

	/**
	 * Handle a label.
	 * 
	 * @param instr the number of the instruction following the label
	 * @param label a unique label identifier
	 */
	public abstract void label(int instr, String label);

	/**
	 * Handle a zero operand instruction.
	 * 
	 * These instructions are {@code NOP}, {@code ACONST_NULL}, {@code ICONST_M1},
	 * {@code ICONST_0}, {@code ICONST_1}, {@code ICONST_2}, {@code ICONST_3},
	 * {@code ICONST_4}, {@code ICONST_5}, {@code LCONST_0}, {@code LCONST_1},
	 * {@code FCONST_0}, {@code FCONST_1}, {@code FCONST_2}, {@code DCONST_0},
	 * {@code DCONST_1}, {@code IALOAD}, {@code LALOAD}, {@code FALOAD},
	 * {@code DALOAD}, {@code AALOAD}, {@code BALOAD}, {@code CALOAD},
	 * {@code SALOAD}, {@code IASTORE}, {@code LASTORE}, {@code FASTORE},
	 * {@code DASTORE}, {@code AASTORE}, {@code BASTORE}, {@code CASTORE},
	 * {@code SASTORE}, {@code POP}, {@code POP2}, {@code DUP}, {@code DUP_X1},
	 * {@code DUP_X2}, {@code DUP2}, {@code DUP2_X1}, {@code DUP2_X2}, {@code SWAP},
	 * {@code IADD}, {@code LADD}, {@code FADD}, {@code DADD}, {@code ISUB},
	 * {@code LSUB}, {@code FSUB}, {@code DSUB}, {@code IMUL}, {@code LMUL},
	 * {@code FMUL}, {@code DMUL}, {@code IDIV}, {@code LDIV}, {@code FDIV},
	 * {@code DDIV}, {@code IREM}, {@code LREM}, {@code FREM}, {@code DREM},
	 * {@code INEG}, {@code LNEG}, {@code FNEG}, {@code DNEG}, {@code ISHL},
	 * {@code LSHL}, {@code ISHR}, {@code LSHR}, {@code IUSHR}, {@code LUSHR},
	 * {@code IAND}, {@code LAND}, {@code IOR}, {@code LOR}, {@code IXOR},
	 * {@code LXOR}, {@code I2L}, {@code I2F}, {@code I2D}, {@code L2I},
	 * {@code L2F}, {@code L2D}, {@code F2I}, {@code F2L}, {@code F2D}, {@code D2I},
	 * {@code D2L}, {@code D2F}, {@code I2B}, {@code I2C}, {@code I2S},
	 * {@code LCMP}, {@code FCMPL}, {@code FCMPG}, {@code DCMPL}, {@code DCMPG},
	 * {@code IRETURN}, {@code LRETURN}, {@code FRETURN}, {@code DRETURN},
	 * {@code ARETURN}, {@code RETURN}, {@code ARRAYLENGTH}, {@code ATHROW},
	 * {@code MONITORENTER}, {@code MONITOREXIT}.
	 * 
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void insn(int instr, int opcode) throws SymbolicException;

	/**
	 * Handle an instruction with a single {@code int} operand.
	 *
	 * These instructions are {@code BIPUSH}, {@code SIPUSH}, {@code NEWARRAY}.
	 * 
	 * @param instr   the number of the instruction
	 * @param opcode  the instruction opcode
	 * @param operand the operand of the instruction
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void intInsn(int instr, int opcode, int operand) throws SymbolicException;

	/**
	 * Handle a local variable instruction. A local variable instruction is an
	 * instruction that loads or stores the value of a local variable.
	 * 
	 * These instructions are {@code ILOAD}, {@code LLOAD}, {@code FLOAD},
	 * {@code DLOAD}, {@code ALOAD}, {@code ISTORE}, {@code LSTORE}, {@code FSTORE},
	 * {@code DSTORE}, {@code ASTORE}, {@code RET}.
	 * 
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @param var    the identifier of the local variable
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void varInsn(int instr, int opcode, int var) throws SymbolicException;

	/**
	 * Handle a type instruction. A type instruction is an instruction that takes
	 * the internal name of a class as parameter.
	 * 
	 * These instructions are {@code NEW}, {@code ANEWARRAY}, {@code CHECKCAST},
	 * {@code INSTANCEOF}.
	 * 
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void typeInsn(int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a field instruction. A field instruction is an instruction that loads
	 * or stores the value of a field of an object.
	 * 
	 * These instructions are {@code GETSTATIC}, {@code PUTSTATIC},
	 * {@code GETFIELD}, {@code PUTFIELD}.
	 *
	 * @param instr      the number of the instruction
	 * @param opcode     the instruction opcode
	 * @param owner      the internal name of the field's owner class
	 * @param name       the name of the field
	 * @param descriptor the type descriptor of the field
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException;

	/**
	 * Handle a method instruction. A method instruction is an instruction that
	 * invokes a method.
	 * 
	 * These instructions are {@code INVOKEVIRTUAL}, {@code INVOKESPECIAL},
	 * {@code INVOKESTATIC}, {@code INVOKEINTERFACE}.
	 * 
	 * @param instr      the number of the instruction
	 * @param opcode     the instruction opcode
	 * @param owner      the internal name of the method's owner class
	 * @param name       the name of the method
	 * @param descriptor the type descriptor of the method
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException;

	/**
	 * Handle an {@code INVOKEDYNAMIC} instruction.
	 * 
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void invokeDynamicInsn(int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a jump instruction. A jump instruction is an instruction that may jump
	 * to another instruction. When the instruction is emitted by instrumentation,
	 * the exact offset is not available, which is why the destination (either as an
	 * offset of as a label) is not included as a parameter.
	 * 
	 * These instructions are {@code IFEQ}, {@code IFNE}, {@code IFLT},
	 * {@code IFGE}, {@code IFGT}, {@code IFLE}, {@code IF_ICMPEQ},
	 * {@code IF_ICMPNE}, {@code IF_ICMPLT}, {@code IF_ICMPGE}, {@code IF_ICMPGT},
	 * {@code IF_ICMPLE}, {@code IF_ACMPEQ}, {@code IF_ACMPNE}, {@code GOTO},
	 * {@code JSR}, {@code IFNULL}, {@code IFNONNULL}.
	 * 
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void jumpInsn(int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a jump instruction where the operand used in the comparison is a
	 * concrete integer. In the case of a comparison, the other operand is zero.
	 * This is used to record information about the choices made during an
	 * execution.
	 * 
	 * @see #jumpInsn(int, int)
	 * 
	 * @param value  concrete value of one operand
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void jumpInsn(int value, int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a jump instruction where the operand used in the comparison is a
	 * concrete object. This is used to record information about the choices made
	 * during an execution.
	 * 
	 * @see #jumpInsn(int, int)
	 * 
	 * @param value  concrete value of one operand
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void jumpInsn(Object value, int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a jump instruction where both the operands used in the comparison are
	 * concrete. This is used to record information about the choices made during an
	 * execution.
	 * 
	 * @see #jumpInsn(int, int)
	 * 
	 * @param value1 concrete value of first operand
	 * @param value2 concrete value of second operand
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException;

	/**
	 * Handle the situation where a jump has *NOT* taken place.
	 * 
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void postJumpInsn(int instr, int opcode) throws SymbolicException;

	/**
	 * Handle a {@code LDC} instruction.
	 * 
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @param value  constant to be loaded on the stack
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void ldcInsn(int instr, int opcode, Object value) throws SymbolicException;

	/**
	 * Handle an {@code IINC} instruction.
	 * 
	 * @param instr     number of the instruction
	 * @param var       index of the local variable to be incremented
	 * @param increment amount to increment the local variable by
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void iincInsn(int instr, int var, int increment) throws SymbolicException;

	/**
	 * Handle a {@code TABLESWITCH} instruction.
	 * 
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void tableSwitchInsn(int instr, int opcode) throws SymbolicException;

	/*
	 * ????????
	 */
	public abstract void tableCaseInsn(int min, int max, int value) throws SymbolicException;

	/**
	 * Handle a {@code LOOKUPSWITCH} instruction.
	 * 
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void lookupSwitchInsn(int instr, int opcode) throws SymbolicException;

	/*
	 * ????????
	 */
	public abstract void lookupCaseInsn(int id, int choice) throws SymbolicException;
	
	/**
	 * Handle a {@code MULTIANEWARRAY} instruction.
	 *
	 * @param instr  the number of the instruction
	 * @param opcode the instruction opcode
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void multiANewArrayInsn(int instr, int opcode) throws SymbolicException;

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	/*
	 * Every instruction of the JVM that can cause an exception, must be handled
	 * carefully. If the instruction does not cause an exception, the
	 * instrumentation inserts a call to noException() after the instruction. If the
	 * instruction causes an exception, the instrumentation insert a call to
	 * startCatch() after the instruction.
	 */

	/**
	 * Handle the case that no exception occurs after an instruction that could
	 * potentially raise an exception
	 * 
	 * @throws SymbolicException if a symbolic exception has occurred during the
	 *                           method
	 */
	public abstract void noException() throws SymbolicException;

	/**
	 * Handle the case where the execution of an instruction has raised an
	 * exception.
	 * 
	 * @param instr the instruction that caused the exception
	 * @throws SymbolicException if a symbolic exception occurs during the method
	 */
	public abstract void startCatch(int instr) throws SymbolicException;

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	/*
	 * The instrumentation intercepts static calls to the Symbolic class and
	 * replaces them with calls to the routines below.
	 */

	/**
	 * Handle the situation where the execution has reached a call to
	 * {@link Symbolic#stop()}.
	 */
	public abstract void stop();

	/**
	 * Handle the situation where the execution has reached a call to
	 * {@link Symbolic#stop(String)}.
	 * 
	 * @param message a message passed from the call
	 */
	public abstract void stop(String message);

	/**
	 * Handle the situation where the execution has reached a symbolic marker. This
	 * is usually the result of a call to {@link Symbolic#mark(int)}.
	 * 
	 * @param marker the marker identity as an integer
	 */
	public abstract void mark(int marker);

	/**
	 * Handle the situation where the execution has reached a symbolic marker. This
	 * is usually the result of a call to {@link Symbolic#mark(String)}.
	 * 
	 * @param marker the marker identity as a string
	 */
	public abstract void mark(String marker);

	/**
	 * Handle the situation where the execution has reached a call to
	 * {@link Symbolic#printPC()}.
	 */
	public abstract void printPC();

	/**
	 * Handle the situation where the execution has reached a call to
	 * {@link Symbolic#printPC(String)}.
	 * 
	 * @param label a message passed from the call
	 */
	public abstract void printPC(String label);

	// ======================================================================
	//
	// CLASS LOADING
	//
	// ======================================================================

	public abstract void loadClasses(String descriptor);

	// ======================================================================
	//
	// SPECIAL ROUTINES
	//
	// ======================================================================

	public abstract void systemExit(int status) throws SymbolicException;

}
