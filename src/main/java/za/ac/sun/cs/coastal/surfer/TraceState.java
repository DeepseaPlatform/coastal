package za.ac.sun.cs.coastal.surfer;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.surfer.Trace.TraceIf;
import za.ac.sun.cs.coastal.symbolic.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.State;

public class TraceState implements State {

	public static final String INDEX_SEPARATOR = "_D_"; // "$"

	public static final String CHAR_SEPARATOR = "_H_"; // "#"

	private final COASTAL coastal;

	private final Logger log;

	private final Broker broker;

	private boolean traceMode = false;

	private boolean recordMode = false;

	private boolean mayRecord = true;

	private int frameCount = 0;

	private Trace trace = null;

	private final Map<String, Object> concreteValues;

	private final boolean useCurrentValues;

	private boolean mayContinue = true;

	private String lastLabel = "NONE";

	public TraceState(COASTAL coastal, Map<String, Object> concreteValues) throws InterruptedException {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		useCurrentValues = (concreteValues == null);
		this.concreteValues = (concreteValues == null) ? new HashMap<>() : concreteValues;
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

	public Map<String, Object> getConcreteValues() {
		return concreteValues;
	}

	@Override
	public void pushExtraConjunct(Expression extraConjunct) {
		assert false; // should never be invoked
	}

	private boolean methodReturn() {
		assert traceMode;
		assert frameCount > 0;
		frameCount--;
		if ((frameCount == 0) && recordMode) {
			log.trace(">>> symbolic record mode switched off");
			recordMode = false;
			mayRecord = false;
			traceMode = false;
			trace.setModel(concreteValues);
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

	private long getConcreteIntegral(int triggerIndex, int index, int address, long currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				concreteValues.put(name, currentValue);
			}
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		Object concrete = concreteValues.get(name);
		if ((concrete == null) || !(concrete instanceof Long)) {
			return currentValue;
		} else {
			return (Long) concrete;
		}
	}

	private double getConcreteReal(int triggerIndex, int index, int address, double currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				concreteValues.put(name, currentValue);
			}
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		Object concrete = concreteValues.get(name);
		if ((concrete == null) || !(concrete instanceof Double)) {
			return currentValue;
		} else {
			return (Double) concrete;
		}
	}

	@Override
	public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		return getConcreteIntegral(triggerIndex, index, address, currentValue ? 1 : 0) != 0;
	}

	@Override
	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return (byte) getConcreteIntegral(triggerIndex, index, address, currentValue);
	}

	@Override
	public short getConcreteShort(int triggerIndex, int index, int address, short currentValue) {
		return (short) getConcreteIntegral(triggerIndex, index, address, currentValue);
	}

	@Override
	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return (char) getConcreteIntegral(triggerIndex, index, address, currentValue);
	}

	@Override
	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return (int) getConcreteIntegral(triggerIndex, index, address, currentValue);
	}

	@Override
	public long getConcreteLong(int triggerIndex, int index, int address, long currentValue) {
		return (long) getConcreteIntegral(triggerIndex, index, address, currentValue);
	}

	@Override
	public float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) {
		return (float) getConcreteReal(triggerIndex, index, address, currentValue);
	}

	@Override
	public double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) {
		return (double) getConcreteReal(triggerIndex, index, address, currentValue);
	}

	@Override
	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				int length = currentValue.length();
				coastal.setParameterSize(name, length);
				for (int i = 0; i < length; i++) {
					concreteValues.put(name + CHAR_SEPARATOR + i, (long) currentValue.charAt(i));
				}
			}
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
			Object concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof Long)) {
				chars[i] = (char) ((Long) concrete).intValue();
			} else {
				chars[i] = currentValue.charAt(i);
			}
		}
		return new String(chars);
	}

	public Object getConcreteIntegralArray(int triggerIndex, int index, int address, Object currentArray,
			Function<Long, Object> convert) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				int length = Array.getLength(currentArray);
				coastal.setParameterSize(name, length);
				if ((length > 0)) {
					Object zero = Array.get(currentArray, 0);
					if (zero instanceof Number) {
						for (int i = 0; i < length; i++) {
							concreteValues.put(name + INDEX_SEPARATOR + i,
									((Number) Array.get(currentArray, i)).longValue());
						}
					} else {
						for (int i = 0; i < length; i++) {
							concreteValues.put(name + INDEX_SEPARATOR + i,
									(long) ((Character) Array.get(currentArray, i)).charValue());
						}
					}
				}
			}
			return currentArray;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentArray;
		}
		int length = Array.getLength(currentArray);
		Class<?> type = trigger.getParamType(index);
		assert type.isArray();
		Class<?> elementType = type.getComponentType();
		Object newArray = Array.newInstance(elementType, length);
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Object concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof Long)) {
				Array.set(newArray, i, convert.apply((Long) concrete));
			} else {
				Array.set(newArray, i, Array.get(currentArray, i));
			}
		}
		return newArray;
	}

	public Object getConcreteRealArray(int triggerIndex, int index, int address, Object currentArray, Class<?> type,
			Function<Double, Object> convert) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				int length = Array.getLength(currentArray);
				coastal.setParameterSize(name, length);
				for (int i = 0; i < length; i++) {
					concreteValues.put(name + INDEX_SEPARATOR + i, (double) Array.get(currentArray, i));
				}
			}
			return currentArray;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentArray;
		}
		int length = Array.getLength(currentArray);
		Object newArray = Array.newInstance(type, length);
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Object concrete = concreteValues.get(entryName);
			if ((concrete != null) && (concrete instanceof Double)) {
				Array.set(newArray, i, convert.apply((Double) concrete));
			} else {
				Array.set(newArray, i, Array.get(currentArray, i));
			}
		}
		return newArray;
	}

	@Override
	public boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return (boolean[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (x != 0));
	}

	@Override
	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return (byte[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (byte) x.intValue());
	}

	@Override
	public short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return (short[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (short) x.intValue());
	}

	@Override
	public char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return (char[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (char) x.intValue());
	}

	@Override
	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return (int[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (int) x.intValue());
	}

	@Override
	public long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return (long[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (long) x.intValue());
	}

	@Override
	public float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		return (float[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (float) x.intValue());
	}

	@Override
	public double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		return (double[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (double) x.intValue());
	}

	@Override
	public String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) {
		if (concreteValues == null) {
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			return currentValue;
		}
		int length = currentValue.length;
		String[] strings = new String[length];
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			int slength = currentValue[i].length();
			char[] chars = new char[slength];
			for (int j = 0; j < slength; j++) {
				String sentryName = entryName + CHAR_SEPARATOR + j;
				Object concrete = concreteValues.get(sentryName);
				if ((concrete != null) && (concrete instanceof Long)) {
					chars[j] = (char) ((Long) concrete).intValue();
				} else {
					chars[j] = currentValue[i].charAt(j);
				}
			}
			strings[i] = new String(chars);
		}
		return strings;
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
	public void label(int instr, String label) {
		if (!traceMode) {
			return;
		}
		log.trace("### LABEL {}", label);
		lastLabel = label;
		broker.publishThread("label", new Tuple(instr, label));
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
			trace = new TraceIf(trace, lastLabel, true);
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
			trace = ((TraceIf) trace).negate(lastLabel);
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
