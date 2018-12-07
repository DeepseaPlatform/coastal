package za.ac.sun.cs.coastal.surfer;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.symbolic.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.State;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;

public class TraceState implements State {

	private static final String INDEX_SEPARATOR = "_D_"; // "$"

	public static final String CHAR_SEPARATOR = "_H_"; // "#"

	private final COASTAL coastal;

	private final Logger log;

	private final Broker broker;

	private boolean traceMode = false;

	private boolean recordMode = false;

	private boolean mayRecord = true;

	private int frameCount = 0;

	private Trace trace = null;

	private final Map<String, Constant> concreteValues;

	private boolean mayContinue = true;

	public TraceState(COASTAL coastal, Map<String, Constant> concreteValues) throws InterruptedException {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		this.concreteValues = concreteValues;
	}

	public boolean getSymbolicMode() {
		return traceMode;
	}

	@Override
	public boolean getRecordMode() {
		return recordMode;
	}

	public boolean mayContinue() {
		return mayContinue;
	}

	public void discontinue() {
		mayContinue = false;
	}

	public Trace getTrace() {
		return trace;
	}

	@Override
	public void pushExtraConjunct(Expression extraConjunct) {
		assert false; // should never be invoked
	}

	private boolean methodReturn() {
		assert traceMode;
		assert frameCount > 0;
		if ((frameCount == 0) && recordMode) {
			log.trace(">>> symbolic record mode switched off");
			recordMode = false;
			mayRecord = false;
			traceMode = false;
		}
		return traceMode;
	}

	@Override
	public String getNewVariableName() {
		assert false; // should never be invoked
		return null;
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	@Override
	public void stop() {
		if (!traceMode) {
			return;
		}
		broker.publish("stop", new Tuple(this, null));
	}

	@Override
	public void stop(String message) {
		if (!traceMode) {
			return;
		}
		broker.publish("stop", new Tuple(this, message));
	}

	@Override
	public void mark(int marker) {
		if (!traceMode) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	@Override
	public void mark(String marker) {
		if (!traceMode) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	// ======================================================================
	//
	// SEMI-INSTRUCTIONS
	//
	// ======================================================================

	@Override
	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		return ((IntConstant) concreteValues.get(name)).getValue();
	}

	@Override
	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		return (char) ((IntConstant) concreteValues.get(name)).getValue();
	}

	@Override
	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		return (byte) ((IntConstant) concreteValues.get(name)).getValue();
	}

	@Override
	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentValue;
		}
		int length = currentValue.length();
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			String entryName = name + CHAR_SEPARATOR + i;
			Constant concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof IntConstant)) {
				chars[i] = (char) ((IntConstant) concrete).getValue();
			} else {
				chars[i] = currentValue.charAt(i);
			}
		}
		return new String(chars);
	}

	@Override
	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentValue;
		}
		int length = currentValue.length;
		int[] value = new int[length];
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Constant concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof IntConstant)) {
				value[i] = ((IntConstant) concrete).getValue();
			} else {
				value[i] = currentValue[i];
			}
		}
		return value;
	}

	@Override
	public char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentValue;
		}
		int length = currentValue.length;
		char[] value = new char[length];
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Constant concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof IntConstant)) {
				value[i] = (char) ((IntConstant) concrete).getValue();
			} else {
				value[i] = currentValue[i];
			}
		}
		return value;
	}

	@Override
	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentValue;
		}
		int length = currentValue.length;
		byte[] value = new byte[length];
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Constant concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof IntConstant)) {
				value[i] = (byte) ((IntConstant) concrete).getValue();
			} else {
				value[i] = currentValue[i];
			}
		}
		return value;
	}

	@Override
	public void triggerMethod(int methodNumber) {
		if (!recordMode) {
			recordMode = mayRecord;
			if (recordMode) {
				log.trace(">>> symbolic record mode switched on");
				mayRecord = false;
				traceMode = true;
				frameCount++;
			}
		}
		broker.publishThread("enter-method", methodNumber);
	}

	@Override
	public void startMethod(int methodNumber, int argCount) {
		if (!traceMode) {
			return;
		}
		log.trace(">>> transferring arguments");
		frameCount++;
		broker.publishThread("enter-method", methodNumber);
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	/*
	 * Instructions that can throw exceptions:
	 * 
	 * AALOAD AASTORE ANEWARRAY ARETURN ARRAYLENGTH ATHROW BALOAD BASTORE CALOAD
	 * CASTORE CHECKCAST DALOAD DASTORE DRETURN FALOAD FASTORE FRETURN GETFIELD
	 * GETSTATIC IALOAD IASTORE IDIV INVOKEDYNAMIC INVOKEINTERFACE INVOKESPECIAL
	 * INVOKESTATIC INVOKEVIRTUAL IREM IRETURN LALOAD LASTORE LDIV LREM LRETURN
	 * MONITORENTER MONITOREXIT MULTIANEWARRAY NEW NEWARRAY PUTFIELD PUTSTATIC
	 * RETURN SALOAD SASTORE
	 */

	@Override
	public void linenumber(int instr, int line) {
		if (!traceMode) {
			return;
		}
		log.trace("### LINENUMBER {}", line);
		broker.publishThread("linenumber", new Tuple(instr, line));
	}

	@Override
	public void insn(int instr, int opcode) throws LimitConjunctException {
		if (!traceMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("insn", new Tuple(instr, opcode));
		switch (opcode) {
//		case Opcodes.IDIV:
//			assert noExceptionExpression == null;
//			noExceptionExpression = new Operation(Operator.NE, e, Operation.ZERO);
//			exceptionDepth = Thread.currentThread().getStackTrace().length;
//			throwable = Operation.ZERO;
//			break;
		case Opcodes.IRETURN:
		case Opcodes.ARETURN:
		case Opcodes.RETURN:
			methodReturn();
			break;
//		case Opcodes.ATHROW:
//			assert noExceptionExpression == null;
//			noExceptionExpression = new Operation(Operator.NE, Operation.ZERO, Operation.ZERO);
//			exceptionDepth = Thread.currentThread().getStackTrace().length;
//			throwable = pop();
//			break;
		default:
			break;
		}
	}

	@Override
	public void intInsn(int instr, int opcode, int operand) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void varInsn(int instr, int opcode, int var) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void typeInsn(int instr, int opcode) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(boolean returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(char returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(double returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(float returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(int returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(long returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void returnValue(short returnValue) {
		assert false; // should never be invoked 
	}

	@Override
	public void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	/* Missing offset because destination not yet known. */
	@Override
	public void jumpInsn(int instr, int opcode) throws LimitConjunctException {
		if (!traceMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		if (recordMode && (opcode != Opcodes.GOTO)) {
			trace = new TraceIf(trace, true);
		}
	}

	@Override
	public void postJumpInsn(int instr, int opcode) throws LimitConjunctException {
		if (!traceMode) {
			return;
		}
		if (recordMode) {
			log.trace("(POST) {}", Bytecodes.toString(opcode));
			log.trace(">>> previous conjunct is false");
			assert trace instanceof TraceIf;
			trace = ((TraceIf) trace).negate();
		}
	}

	@Override
	public void ldcInsn(int instr, int opcode, Object value) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void iincInsn(int instr, int var, int increment) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void tableSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void tableCaseInsn(int min, int max, int value) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void lookupSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	// ======================================================================
	//
	// UTILITIES
	//
	// ======================================================================

//	public static String getReturnType(String descriptor) {
//		int i = 0;
//		if (descriptor.charAt(i++) != '(') {
//			return "?"; // missing '('
//		}
//		i = descriptor.indexOf(')', i);
//		if (i == -1) {
//			return "?"; // missing ')'
//		}
//		return descriptor.substring(i + 1);
//	}
//
//	public static String getAsciiSignature(String descriptor) {
//		return descriptor.replace('/', '_').replace("_", "_1").replace(";", "_2").replace("[", "_3").replace("(", "__")
//				.replace(")", "__");
//	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	@Override
	public void noException() throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public void startCatch(int instr) throws LimitConjunctException {
		assert false; // should never be invoked 
	}

	@Override
	public Expression getStringLength(int stringId) {
		assert false; // should never be invoked 
		return null;
	}

	@Override
	public Expression getStringChar(int stringId, int index) {
		assert false; // should never be invoked 
		return null;
	}

	@Override
	public void push(Expression expr) {
		assert false; // should never be invoked 
	}

	@Override
	public Expression pop() {
		assert false; // should never be invoked 
		return null;
	}

}
