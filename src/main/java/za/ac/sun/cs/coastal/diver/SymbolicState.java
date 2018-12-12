package za.ac.sun.cs.coastal.diver;

import java.lang.reflect.Array;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.diver.SegmentedPC.SegmentedPCIf;
import za.ac.sun.cs.coastal.diver.SegmentedPC.SegmentedPCSwitch;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Constant;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.solver.RealConstant;
import za.ac.sun.cs.coastal.solver.RealVariable;
import za.ac.sun.cs.coastal.symbolic.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.State;
import za.ac.sun.cs.coastal.symbolic.SymbolicException;

public class SymbolicState implements State {

	/**
	 * Separator for fields. For example, the characters of a symbolic object
	 * with address 0 are called {@code 0/a}, {@code 0/b}, {@code 0/c}, ...
	 */
	private static final String FIELD_SEPARATOR = "/";

	/**
	 * Separator for array elements. For example, the elements of an array named
	 * {@code A} are called {@code A_D_0}, {@code A_D_1}, {@code A_D_2}, ...
	 */
	private static final String INDEX_SEPARATOR = "$"; // "_D_"

	/**
	 * Separator for string characters. For example, the characters of a string
	 * named {@code X} are called {@code X_H_0}, {@code X_H_1}, {@code X_H_2},
	 * ...
	 */
	public static final String CHAR_SEPARATOR = "!"; // "_H_"

	/**
	 * Prefix for new symbolic variables.
	 */
	public static final String NEW_VAR_PREFIX = "$"; // "U_D_"

	public static final String CREATE_VAR_PREFIX = "N_D_"; // "@"
	
	private final COASTAL coastal;

	private final Logger log;

	private final Broker broker;

	private final long limitConjuncts;

	private final boolean traceAll;

	// if true, check for limit on conjuncts
	private static boolean dangerFlag = false;

	private boolean symbolicMode = false;

	private boolean recordMode = false;

	private boolean mayRecord = true;

	private final Stack<SymbolicFrame> frames = new Stack<>();

	private int objectIdCount = 0;

	private int newVariableCount = 0;

	private final Map<String, Expression> instanceData = new HashMap<>();

	private final Stack<Expression> args = new Stack<>();

	private SegmentedPC spc = null;

	private Expression pendingExtraConjunct = null;

	private final List<Expression> noExceptionExpression = new ArrayList<>();

	private int exceptionDepth = 0;

	private Expression throwable = null;

	private final Map<String, Object> concreteValues;

	private boolean mayContinue = true;

	private boolean justExecutedDelegate = false;

	private int lastInvokingInstruction = 0;

	private final Stack<Expression> pendingSwitch = new Stack<>();

	public SymbolicState(COASTAL coastal, Map<String, Object> concreteValues) throws InterruptedException {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		limitConjuncts = ConfigHelper.limitLong(coastal.getConfig(), "coastal.settings.conjunct-limit");
		traceAll = coastal.getConfig().getBoolean("coastal.settings.trace-all", false);
		this.concreteValues = concreteValues;
		symbolicMode = traceAll;
	}

	public boolean getSymbolicMode() {
		return symbolicMode;
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

	public SegmentedPC getSegmentedPathCondition() {
		return spc;
	}

	@Override
	public void push(Expression expr) {
		frames.peek().push(expr);
	}

	@Override
	public Expression pop() {
		return frames.peek().pop();
	}

	private Expression peek() {
		return frames.peek().peek();
	}

	private Expression getLocal(int index) {
		return frames.peek().getLocal(index);
	}

	private void setLocal(int index, Expression value) {
		frames.peek().setLocal(index, value);
	}

	private void putField(int objectId, String fieldName, Expression value) {
		putField(Integer.toString(objectId), fieldName, value);
	}

	private void putField(String objectName, String fieldName, Expression value) {
		String fullFieldName = objectName + FIELD_SEPARATOR + fieldName;
		instanceData.put(fullFieldName, value);
	}

	private Expression getField(int objectId, String fieldName) {
		return getField(Integer.toString(objectId), fieldName);
	}

	private Expression getField(String objectName, String fieldName) {
		String fullFieldName = objectName + FIELD_SEPARATOR + fieldName;
		Expression value = instanceData.get(fullFieldName);
		if (value == null) {
			// THIS int.class ASSUMPTION IS SOMETIMES WRONG
			// WE MUST INSTEAD ALSO STORE THE TYPE OF THE ARRAY ELEMS
			// THIS PROBABLY REQUIRES A SECOND MAP LIKE instanceData
			int min = (Integer) coastal.getDefaultMinValue(int.class);
			int max = (Integer) coastal.getDefaultMaxValue(int.class);
			value = new IntegerVariable(getNewVariableName(), 32, min, max);
			instanceData.put(fullFieldName, value);
		}
		return value;
	}

	// Arrays are just objects
	private int createArray() {
		return incrAndGetNewObjectId();
	}

	//	private int getArrayLength(int arrayId) {
	//		return ((IntConstant) getField(arrayId, "length")).getValue();
	//	}

	private void setArrayType(int arrayId, int type) {
		putField(arrayId, "type", new IntegerConstant(type, 32));
	}

	private void setArrayLength(int arrayId, int length) {
		putField(arrayId, "length", new IntegerConstant(length, 32));
	}

	private Expression getArrayValue(int arrayId, int index) {
		return getField(arrayId, "" + index);
	}

	private void setArrayValue(int arrayId, int index, Expression value) {
		putField(arrayId, "" + index, value);
	}

	// Strings are just objects
	private int createString() {
		return incrAndGetNewObjectId();
	}

	@Override
	public Expression getStringLength(int stringId) {
		return getField(stringId, "length");
	}

	private void setStringLength(int stringId, Expression length) {
		putField(stringId, "length", length);
	}

	@Override
	public Expression getStringChar(int stringId, int index) {
		return getField(stringId, "" + index);
	}

	private void setStringChar(int stringId, int index, Expression value) {
		putField(stringId, "" + index, value);
	}

	private void pushConjunct(Expression conjunct, boolean truthValue) {
		String c = conjunct.toString();
		spc = new SegmentedPCIf(spc, conjunct, pendingExtraConjunct, truthValue);
		pendingExtraConjunct = null;
		log.trace(">>> adding conjunct: {}", c);
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
	}

	private void pushConjunct(Expression conjunct) {
		pushConjunct(conjunct, true);
	}

	private void pushConjunct(Expression expression, long min, long max, long cur) {
		Expression conjunct;
		if (cur < min) {
			Expression lo = Operation.lt(expression, new IntegerConstant(min, 32));
			Expression hi = Operation.gt(expression, new IntegerConstant(max, 32));
			conjunct = Operation.or(lo, hi);
		} else {
			conjunct = Operation.eq(expression, new IntegerConstant(cur, 32));
		}
		String c = conjunct.toString();
		spc = new SegmentedPCSwitch(spc, expression, pendingExtraConjunct, min, max, cur);
		pendingExtraConjunct = null;
		log.trace(">>> adding (switch) conjunct: {}", c);
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
	}

	@Override
	public void pushExtraConjunct(Expression extraConjunct) {
		if (!SegmentedPC.isConstant(extraConjunct)) {
			if (pendingExtraConjunct == null) {
				pendingExtraConjunct = extraConjunct;
			} else {
				pendingExtraConjunct = Operation.and(extraConjunct, pendingExtraConjunct);
			}
		}
	}

	private boolean methodReturn() {
		assert symbolicMode;
		assert !frames.isEmpty();
		int methodNumber = frames.pop().getMethodNumber();
		if (frames.isEmpty() && recordMode) {
			log.trace(">>> symbolic record mode switched off");
			recordMode = false;
			mayRecord = false;
			symbolicMode = traceAll;
		}
		broker.publishThread("exit-method", methodNumber);
		return symbolicMode;
	}

	private int incrAndGetNewObjectId() {
		return ++objectIdCount;
	}

	@Override
	public String getNewVariableName() {
		return NEW_VAR_PREFIX + newVariableCount++;
	}
	
	public int createSymbolicInt(int currentValue, int uniqueId) {
		if (!symbolicMode) {
			return 0;
		}
		
		String name = CREATE_VAR_PREFIX + uniqueId;
		pop();
		push(new IntegerVariable(name, 32, 0, 255));
		IntegerConstant concrete = (IntegerConstant) (concreteValues == null ? null : concreteValues.get(name));
		
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			return currentValue;
		} else {
			int newValue = (int) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	private void dumpFrames() {
		int n = frames.size();
		for (int i = n - 1; i >= 0; i--) {
			log.trace("--> st{} locals:{} <{}>", frames.get(i).stack, frames.get(i).locals,
					frames.get(i).getInvokingInstruction());
		}
		log.trace("--> data:{}", instanceData);
	}

	private static final Class<?>[] DELEGATE_PARAMETERS = new Class<?>[] { SymbolicState.class };

	private final Object[] delegateArguments = new Object[] { this };

	private boolean executeDelegate(String owner, String name, String descriptor) {
		Object delegate = coastal.findDelegate(owner);
		if (delegate == null) {
			return false;
		}
		String methodName = name + getAsciiSignature(descriptor);
		Method delegateMethod = null;
		try {
			delegateMethod = delegate.getClass().getDeclaredMethod(methodName, DELEGATE_PARAMETERS);
		} catch (NoSuchMethodException | SecurityException e) {
			log.trace("@@@ no delegate: {}", methodName);
			return false;
		}
		assert delegateMethod != null;
		log.trace("@@@ found delegate: {}", methodName);
		try {
			if ((boolean) delegateMethod.invoke(delegate, delegateArguments)) {
				justExecutedDelegate = true;
				return true;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException x) {
			// This should never happen!
			x.printStackTrace();
		}
		return false;
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	@Override
	public void stop() {
		if (!symbolicMode) {
			return;
		}
		broker.publish("stop", new Tuple(this, null));
	}

	@Override
	public void stop(String message) {
		if (!symbolicMode) {
			return;
		}
		pop();
		broker.publish("stop", new Tuple(this, message));
	}

	@Override
	public void mark(int marker) {
		if (!symbolicMode) {
			return;
		}
		pop();
		broker.publishThread("mark", marker);
	}

	@Override
	public void mark(String marker) {
		if (!symbolicMode) {
			return;
		}
		pop();
		broker.publishThread("mark", marker);
	}

	// ======================================================================
	//
	// SEMI-INSTRUCTIONS
	//
	// ======================================================================

	private long getConcreteIntegral(int triggerIndex, int index, int address, int size, long currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntegerConstant(currentValue, size));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		long min = 0, max = 0;
		Object minObject = coastal.getMinBound(name, type);
		Object maxObject = coastal.getMaxBound(name, type);
		if (minObject instanceof Number) {
			min = ((Number) minObject).longValue();
			max = ((Number) maxObject).longValue();
		} else if (minObject instanceof Character) {
			min = ((Character) minObject).charValue();
			max = ((Character) maxObject).charValue();
		} else {
			Banner bn = new Banner('@');
			bn.println("UNKNOWN MIN/MAX BOUNDS CLASS:");
			bn.println(minObject.getClass().getName());
			bn.display(log);
			System.exit(-1);
		}
		setLocal(address, new IntegerVariable(name, size, min, max));
		Long concrete = (Long) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : concrete;
	}

	private double getConcreteReal(int triggerIndex, int index, int address, int size, double currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new RealConstant(currentValue, size));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		double min = ((Number) coastal.getMinBound(name, type)).doubleValue();
		double max = ((Number) coastal.getMaxBound(name, type)).doubleValue();
		setLocal(address, new RealVariable(name, size, min, max));
		Double concrete = (Double) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : concrete;
	}

	//	@Override
	//	public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
	//		return getConcreteIntegral(triggerIndex, index, address, currentValue ? 1 : 0) != 0;
	//	}

	@Override
	public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntegerConstant(currentValue ? 1 : 0, 32));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		int min = (Integer) coastal.getMinBound(name, type);
		int max = (Integer) coastal.getMaxBound(name, type);
		setLocal(address, new IntegerVariable(name, 32, min, max));
		Long concrete = (Long) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : (concrete != 0);
	}

	@Override
	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		return (byte) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
	}

	@Override
	public short getConcreteShort(int triggerIndex, int index, int address, short currentValue) {
		return (short) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
	}

	@Override
	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		return (char) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
	}

	@Override
	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		return (int) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
	}

	@Override
	public long getConcreteLong(int triggerIndex, int index, int address, long currentValue) {
		return (long) getConcreteIntegral(triggerIndex, index, address, 64, currentValue);
	}

	@Override
	public float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) {
		return (float) getConcreteReal(triggerIndex, index, address, 32, currentValue);
	}

	@Override
	public double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) {
		return (double) getConcreteReal(triggerIndex, index, address, 64, currentValue);
	}

	@Override
	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length();
		int stringId = createString();
		setStringLength(stringId, new IntegerConstant(length, 32));
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				IntegerConstant chValue = new IntegerConstant(currentValue.charAt(i), 32);
				setStringChar(stringId, i, chValue);
			}
			setLocal(address, new IntegerConstant(stringId, 32));
			return currentValue;
		} else {
			char minChar = (Character) coastal.getDefaultMinValue(char.class);
			char maxChar = (Character) coastal.getDefaultMaxValue(char.class);
			char[] chars = new char[length];
			currentValue.getChars(0, length, chars, 0); // copy string into chars[]
			for (int i = 0; i < length; i++) {
				String entryName = name + CHAR_SEPARATOR + i;
				Object concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				Expression entryExpr = new IntegerVariable(entryName, 32, minChar, maxChar);
				if ((concrete != null) && (concrete instanceof Long)) {
					chars[i] = (char) ((Long) concrete).intValue();
				}
				setArrayValue(stringId, i, entryExpr);
			}
			setLocal(address, new IntegerConstant(stringId, 32));
			return new String(chars);
		}
	}

	public Object getConcreteIntegralArray(int triggerIndex, int index, int address, int size, Object currentArray,
			Function<Object, Long> unconvert, Function<Long, Object> convert) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = Array.getLength(currentArray);
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new IntegerConstant(unconvert.apply(Array.get(currentArray, i)), size));
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			return currentArray;
		} else {
			Class<?> type = trigger.getParamType(index);
			assert type.isArray();
			Class<?> elementType = type.getComponentType();
			Object newArray = Array.newInstance(elementType, length);
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Object concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof Long)) {
					Array.set(newArray, i, convert.apply((Long) concrete));
				} else {
					Array.set(newArray, i, Array.get(currentArray, i));
				}
				Long min = null, max = null;
				Object minBound = coastal.getMinBound(entryName, name, elementType);
				if (minBound instanceof Number) {
					min = ((Number) minBound).longValue();
				} else if (minBound instanceof Character) {
					min = Long.valueOf((Character) minBound);
				}
				Object maxBound = coastal.getMaxBound(entryName, name, elementType);
				if (maxBound instanceof Number) {
					max = ((Number) maxBound).longValue();
				} else if (maxBound instanceof Character) {
					max = Long.valueOf((Character) maxBound);
				}
				Expression entryExpr = new IntegerVariable(entryName, size, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			return newArray;
		}
	}

	public Object getConcreteRealArray(int triggerIndex, int index, int address, int size, Object currentArray,
			Function<Object, Double> unconvert, Function<Double, Object> convert) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = Array.getLength(currentArray);
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new RealConstant(unconvert.apply(Array.get(currentArray, i)), size));
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			return currentArray;
		} else {
			Class<?> type = trigger.getParamType(index);
			Object newArray = Array.newInstance(type, length);
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Object concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof Double)) {
					Array.set(newArray, i, convert.apply((Double) concrete));
				} else {
					Array.set(newArray, i, Array.get(currentArray, i));
				}
				double min = (Double) coastal.getMinBound(entryName, name, type);
				double max = (Double) coastal.getMaxBound(entryName, name, type);
				Expression entryExpr = new RealVariable(entryName, size, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			return newArray;
		}
	}

	@Override
	public boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return (boolean[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue,
				o -> ((Boolean) o) ? 1L : 0, x -> (x != 0));
	}

	@Override
	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return (byte[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Byte) o,
				x -> (byte) x.intValue());
	}

	@Override
	public short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return (short[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Short) o,
				x -> (short) x.intValue());
	}

	@Override
	public char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return (char[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue,
				o -> (long) (Character) o, x -> (char) x.intValue());
	}

	@Override
	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return (int[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Integer) o,
				x -> (int) x.intValue());
	}

	@Override
	public long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return (long[]) getConcreteIntegralArray(triggerIndex, index, address, 64, currentValue, o -> (long) (Long) o,
				x -> (long) x.intValue());
	}

	@Override
	public float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		return (float[]) getConcreteRealArray(triggerIndex, index, address, 32, currentValue, o -> (double) (Float) o,
				x -> (float) x.doubleValue());
	}

	@Override
	public double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		return (double[]) getConcreteRealArray(triggerIndex, index, address, 64, currentValue, o -> (double) (Double) o,
				x -> (double) x.doubleValue());
	}

	/*
	 * ======= // int length = currentValue.length(); // int stringId =
	 * createString(); // setStringLength(stringId, new IntConstant(length)); //
	 * if (name == null) { // not symbolic // for (int i = 0; i < length; i++) {
	 * // IntConstant chValue = new IntConstant(currentValue.charAt(i)); //
	 * setStringChar(stringId, i, chValue); // } // setLocal(address, new
	 * IntConstant(stringId)); // return currentValue; // } else { // char[]
	 * chars = new char[length]; // currentValue.getChars(0, length, chars, 0);
	 * // copy string into chars[] // for (int i = 0; i < length; i++) { //
	 * String entryName = name + CHAR_SEPARATOR + i; // Constant concrete =
	 * ((name == null) || (concreteValues == null)) ? null :
	 * concreteValues.get(entryName); // Expression entryExpr = new
	 * IntVariable(entryName, 0, 255); // if ((concrete != null) && (concrete
	 * instanceof IntConstant)) { // chars[i] = (char) ((IntConstant)
	 * concrete).getValue(); // } // setArrayValue(stringId, i, entryExpr); // }
	 * // setLocal(address, new IntConstant(stringId)); // return new
	 * String(chars); // }
	 * 
	 * @Override public String[] getConcreteStringArray(int triggerIndex, int
	 * index, int address, String[] currentValue) { Trigger trigger =
	 * coastal.getTrigger(triggerIndex); String name =
	 * trigger.getParamName(index); int length = currentValue.length; int
	 * arrayId = createArray(); setArrayLength(arrayId, length); String[] value;
	 * if (name == null) { // not symbolic value = currentValue; for (int i = 0;
	 * i < length; i++) { int slength = currentValue[i].length(); int stringId =
	 * createString(); for (int j = 0; j < slength; j++) { IntegerConstant
	 * chValue = new IntegerConstant(currentValue[i].charAt(j));
	 * setStringChar(stringId, j, chValue); } setArrayValue(arrayId, i, new
	 * IntegerConstant(stringId)); } } else { int minChar = (Character)
	 * coastal.getDefaultMinValue(char.class); int maxChar = (Character)
	 * coastal.getDefaultMaxValue(char.class); value = new String[length]; for
	 * (int i = 0; i < length; i++) { int slength = currentValue[i].length();
	 * int stringId = createString(); for (int j = 0; j < slength; j++) { String
	 * entryName = name + INDEX_SEPARATOR + i; Constant concrete = ((name ==
	 * null) || (concreteValues == null)) ? null :
	 * concreteValues.get(entryName);
	 * 
	 * IntegerConstant chValue = new IntegerConstant(currentValue[i].charAt(j));
	 * setStringChar(stringId, j, chValue); } setArrayValue(arrayId, i, new
	 * IntegerConstant(stringId)); } } setLocal(index, new
	 * IntegerConstant(arrayId)); return value; } // int length =
	 * currentValue.length; // String[] strings = new String[length]; // for
	 * (int i = 0; i < length; i++) { // String entryName = name +
	 * INDEX_SEPARATOR + i; // int slength = currentValue[i].length(); // char[]
	 * chars = new char[slength]; // for (int j = 0; j < slength; j++) { //
	 * String sentryName = entryName + CHAR_SEPARATOR + j; // Constant concrete
	 * = concreteValues.get(sentryName); // if ((concrete != null) && (concrete
	 * instanceof IntConstant)) { // chars[j] = (char) ((IntConstant)
	 * concrete).getValue(); // } else { // chars[j] =
	 * currentValue[i].charAt(j); // } // } // strings[i] = new String(chars);
	 * // } // return strings; =======
	 */
	@Override
	public String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void triggerMethod(int methodNumber) {
		if (!recordMode) {
			recordMode = mayRecord;
			if (recordMode) {
				log.trace(">>> symbolic record mode switched on");
				mayRecord = false;
				symbolicMode = true;
				frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
				dumpFrames();
			}
		}
		broker.publishThread("enter-method", methodNumber);
	}

	@Override
	public void startMethod(int methodNumber, int argCount) {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> transferring arguments");
		if (frames.isEmpty()) {
			frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
			for (int i = 0; i < argCount; i++) {
				setLocal(1, IntegerConstant.ZERO32);
			}
		} else {
			assert args.isEmpty();
			for (int i = 0; i < argCount; i++) {
				args.push(pop());
			}
			frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
			for (int i = 0; i < argCount; i++) {
				setLocal(i, args.pop());
			}
		}
		dumpFrames();
		broker.publishThread("enter-method", methodNumber);
	}

	private void checkLimitConjuncts() throws SymbolicException {
		if (dangerFlag) {
			if ((spc != null) && (spc.getDepth() >= limitConjuncts)) {
				throw new LimitConjunctException();
			}
			dangerFlag = false;
		}
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
		if (!symbolicMode) {
			return;
		}
		log.trace("### LINENUMBER {}", line);
		broker.publishThread("linenumber", new Tuple(instr, line));
	}

	@Override
	public void label(int instr, String label) {
		if (!symbolicMode) {
			return;
		}
		log.trace("### LABEL {}", label);
		broker.publishThread("label", new Tuple(instr, label));
	}
	
	@Override
	public void insn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ACONST_NULL:
			push(IntegerConstant.ZERO32);
			break;
		case Opcodes.ICONST_M1:
			push(new IntegerConstant(-1, 32));
			break;
		case Opcodes.ICONST_0:
			push(IntegerConstant.ZERO32);
			break;
		case Opcodes.ICONST_1:
			push(IntegerConstant.ONE32);
			break;
		case Opcodes.ICONST_2:
			push(new IntegerConstant(2, 32));
			break;
		case Opcodes.ICONST_3:
			push(new IntegerConstant(3, 32));
			break;
		case Opcodes.ICONST_4:
			push(new IntegerConstant(4, 32));
			break;
		case Opcodes.ICONST_5:
			push(new IntegerConstant(5, 32));
			break;
		case Opcodes.LCONST_0:
			push(IntegerConstant.ZERO64);
			break;
		case Opcodes.LCONST_1:
			push(IntegerConstant.ONE64);
			break;
		case Opcodes.FCONST_0:
			push(RealConstant.ZERO32);
			break;
		case Opcodes.FCONST_1:
			push(new RealConstant(1, 32));
			break;
		case Opcodes.FCONST_2:
			push(new RealConstant(2, 32));
			break;
		case Opcodes.DCONST_0:
			push(RealConstant.ZERO64);
			break;
		case Opcodes.DCONST_1:
			push(new RealConstant(1, 64));
			break;
		case Opcodes.IALOAD:
			int i = (int) ((IntegerConstant) pop()).getValue();
			int a = (int) ((IntegerConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.BALOAD:
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.CALOAD:
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.IASTORE:
			Expression e = pop();
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.BASTORE:
			e = pop();
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.CASTORE:
			e = pop();
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.POP:
			pop();
			break;
		case Opcodes.DUP:
			push(peek());
			break;
		case Opcodes.IADD:
		case Opcodes.LADD:
		case Opcodes.FADD:
		case Opcodes.DADD:
			e = pop();
			push(Operation.add(pop(), e));
			break;
		case Opcodes.IMUL:
		case Opcodes.LMUL:
		case Opcodes.FMUL:
		case Opcodes.DMUL:
			e = pop();
			push(Operation.mul(pop(), e));
			break;
		case Opcodes.IDIV:
			e = pop();
			push(Operation.div(pop(), e));
			noExceptionExpression.add(Operation.ne(e, IntegerConstant.ZERO32));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.LDIV:
			e = pop();
			push(Operation.div(pop(), e));
			noExceptionExpression.add(Operation.ne(e, IntegerConstant.ZERO64));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.FDIV:
			e = pop();
			push(Operation.div(pop(), e));
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO32));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.DDIV:
			e = pop();
			push(Operation.div(pop(), e));
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO64));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.IREM:
			e = pop();
			//push(Operation.rem(a, b))
			Operation.rem(pop(), e);
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO64));
			// Add ExceptionExpression to noExceptionExpressionList
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.ISUB:
			e = pop();
			push(Operation.sub(pop(), e));
			break;
		case Opcodes.F2D:
			push(Operation.f2d(pop()));
			break;
		case Opcodes.I2L:
			push(Operation.i2l(pop()));
			break;
		//		case Opcodes.L2I:
		//		case Opcodes.D2F:
		//		case Opcodes.I2B:
		//		case Opcodes.I2C:
		//		case Opcodes.I2S:
		//			break;
		case Opcodes.LCMP:
			e = pop();
			push(Operation.lcmp(pop(), e));
			break;
		case Opcodes.FCMPL:
			e = pop();
			push(Operation.fcmpl(pop(), e));
			break;
		case Opcodes.FCMPG:
			e = pop();
			push(Operation.fcmpg(pop(), e));
			break;
		case Opcodes.DCMPL:
			e = pop();
			push(Operation.dcmpl(pop(), e));
			break;
		case Opcodes.DCMPG:
			e = pop();
			push(Operation.dcmpg(pop(), e));
			break;
		case Opcodes.IRETURN:
			e = pop();
			if (methodReturn()) {
				push(e);
			}
			break;
		case Opcodes.ARETURN:
			e = pop();
			if (methodReturn()) {
				push(e);
			}
			break;
		case Opcodes.RETURN:
			methodReturn();
			break;
		case Opcodes.ARRAYLENGTH:
			int id = (int) ((IntegerConstant) pop()).getValue();
			push(getField(id, "length"));
			break;
		case Opcodes.ATHROW:
			noExceptionExpression.add(Operation.FALSE);
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = pop();
			broker.publish("assert-failed", new Tuple(this, null));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void intInsn(int instr, int opcode, int operand) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), operand);
		broker.publishThread("int-insn", new Tuple(instr, opcode, operand));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.BIPUSH:
			push(new IntegerConstant(operand, 32));
			break;
		case Opcodes.SIPUSH:
			push(new IntegerConstant(operand, 32));
			break;
		case Opcodes.NEWARRAY:
			Constant init = null;
			switch (operand) {
			case Opcodes.T_LONG:
				init = IntegerConstant.ZERO64;
				break;
			case Opcodes.T_DOUBLE:
				init = RealConstant.ZERO64;
				break;
			case Opcodes.T_FLOAT:
				init = RealConstant.ZERO32;
				break;
			case Opcodes.T_BOOLEAN:
			case Opcodes.T_BYTE:
			case Opcodes.T_SHORT:
			case Opcodes.T_CHAR:
			case Opcodes.T_INT:
				init = IntegerConstant.ZERO32;
				break;
			default:
				assert false;
				break;
			}
			Expression e = pop();
			int n = (int) ((IntegerConstant) e).getValue();
			int id = createArray();
			setArrayType(id, operand);
			setArrayLength(id, n);
			for (int i = 0; i < n; i++) {
				setArrayValue(id, i, init);
			}
			push(new IntegerConstant(id, 32));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} (opcode: {})", instr, Bytecodes.toString(opcode), operand,
					opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void varInsn(int instr, int opcode, int var) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), var);
		broker.publishThread("var-insn", new Tuple(instr, opcode, var));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ALOAD:
		case Opcodes.ILOAD:
		case Opcodes.LLOAD:
		case Opcodes.FLOAD:
		case Opcodes.DLOAD:
			push(getLocal(var));
			break;
		case Opcodes.ASTORE:
		case Opcodes.ISTORE:
		case Opcodes.LSTORE:
		case Opcodes.FSTORE:
		case Opcodes.DSTORE:
			setLocal(var, pop());
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void typeInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("type-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.NEW:
			int id = incrAndGetNewObjectId();
			push(new IntegerConstant(id, 32));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {} {} {}", instr, Bytecodes.toString(opcode), owner, name, descriptor);
		broker.publishThread("field-insn", new Tuple(instr, opcode, owner, name, descriptor));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.GETSTATIC:
			push(getField(owner, name));
			break;
		case Opcodes.PUTSTATIC:
			Expression e = pop();
			putField(owner, name, e);
			break;
		case Opcodes.GETFIELD:
			int id = (int) ((IntegerConstant) pop()).getValue();
			push(getField(id, name));
			break;
		case Opcodes.PUTFIELD:
			e = pop();
			id = (int) ((IntegerConstant) pop()).getValue();
			putField(id, name, e);
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} {} {} (opcode: {})", instr, Bytecodes.toString(opcode),
					owner, name, descriptor, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {} {} {}", instr, Bytecodes.toString(opcode), owner, name, descriptor);
		broker.publishThread("method-insn", new Tuple(instr, opcode, owner, name, descriptor));
		checkLimitConjuncts();
		lastInvokingInstruction = instr;
		switch (opcode) {
		case Opcodes.INVOKESPECIAL:
		case Opcodes.INVOKEVIRTUAL:
			String className = owner.replace('/', '.');
			if (!coastal.isTarget(className)) {
				if (!executeDelegate(className, name, descriptor)) {
					// get rid of arguments
					int n = 1 + getArgumentCount(descriptor);
					while (n-- > 0) {
						pop();
					}
					// insert return type on stack
					pushReturnValue(getReturnType(descriptor).charAt(0));
				}
			}
			break;
		case Opcodes.INVOKESTATIC:
			className = owner.replace('/', '.');
			if (!coastal.isTarget(className)) {
				if (!executeDelegate(className, name, descriptor)) {
					// get rid of arguments
					int n = getArgumentCount(descriptor);
					while (n-- > 0) {
						pop();
					}
					// insert return type on stack
					pushReturnValue(getReturnType(descriptor).charAt(0));
				}
			}
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} {} {} (opcode: {})", instr, Bytecodes.toString(opcode),
					owner, name, descriptor, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	private void pushReturnValue(char type) {
		if (type == 'Z') {
			push(new IntegerVariable(getNewVariableName(), 8, 0, 1));
		} else if (type == 'B') {
			byte min = (byte) coastal.getDefaultMinValue(byte.class);
			byte max = (byte) coastal.getDefaultMaxValue(byte.class);
			push(new IntegerVariable(getNewVariableName(), 8, min, max));
		} else if (type == 'C') {
			char min = (char) coastal.getDefaultMinValue(char.class);
			char max = (char) coastal.getDefaultMaxValue(char.class);
			push(new IntegerVariable(getNewVariableName(), 16, min, max));
		} else if (type == 'S') {
			short min = (short) coastal.getDefaultMinValue(short.class);
			short max = (short) coastal.getDefaultMaxValue(short.class);
			push(new IntegerVariable(getNewVariableName(), 16, min, max));
		} else if (type == 'I') {
			int min = (int) coastal.getDefaultMinValue(int.class);
			int max = (int) coastal.getDefaultMaxValue(int.class);
			push(new IntegerVariable(getNewVariableName(), 32, min, max));
		} else if (type == 'L') {
			long min = (long) coastal.getDefaultMinValue(long.class);
			long max = (long) coastal.getDefaultMaxValue(long.class);
			push(new IntegerVariable(getNewVariableName(), 64, min, max));
		} else if (type == 'F') {
			float min = (float) coastal.getDefaultMinValue(float.class);
			float max = (float) coastal.getDefaultMaxValue(float.class);
			push(new RealVariable(getNewVariableName(), 32, min, max));
		} else if (type == 'D') {
			double min = (double) coastal.getDefaultMinValue(double.class);
			double max = (double) coastal.getDefaultMaxValue(double.class);
			push(new RealVariable(getNewVariableName(), 64, min, max));
		} else if ((type != 'V') && (type != '?')) {
			push(IntegerConstant.ZERO32);
		}
	}

	@Override
	public void returnValue(boolean returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = returnValue ? IntegerConstant.ONE32 : IntegerConstant.ZERO32;
			pushExtraConjunct(Operation.eq(peek(), value));
		}
	}

	@Override
	public void returnValue(char returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 32);
			pushExtraConjunct(Operation.eq(peek(), value));
		}
	}

	@Override
	public void returnValue(double returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE double");
		System.exit(1);
	}

	@Override
	public void returnValue(float returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE float");
		System.exit(1);
	}

	@Override
	public void returnValue(int returnValue) {
		if (!symbolicMode) {
			return;
		}
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 32);
			pushExtraConjunct(Operation.eq(peek(), value));
		}
	}

	@Override
	public void returnValue(long returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE long");
		System.exit(1);
	}

	@Override
	public void returnValue(short returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 32);
			pushExtraConjunct(Operation.eq(peek(), value));
		}
	}

	@Override
	public void invokeDynamicInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("invoke-dynamic-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		lastInvokingInstruction = instr;
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void jumpInsn(int value, int instr, int opcode) throws SymbolicException {
		jumpInsn(instr, opcode);
	}
	
	@Override
	public void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException {
		jumpInsn(instr, opcode);
	}
	
	/* Missing offset because destination not yet known. */
	@Override
	public void jumpInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("jump-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		if (recordMode) {
			dangerFlag = true;
			switch (opcode) {
			case Opcodes.GOTO:
				// do nothing
				break;
			case Opcodes.IFEQ:
				Expression e = pop();
				pushConjunct(Operation.eq(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFNE:
				e = pop();
				pushConjunct(Operation.ne(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFLT:
				e = pop();
				pushConjunct(Operation.lt(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFGE:
				e = pop();
				pushConjunct(Operation.ge(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFGT:
				e = pop();
				pushConjunct(Operation.gt(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFLE:
				e = pop();
				pushConjunct(Operation.le(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IF_ICMPEQ:
				e = pop();
				pushConjunct(Operation.eq(pop(), e));
				break;
			case Opcodes.IF_ICMPNE:
				e = pop();
				pushConjunct(Operation.ne(pop(), e));
				break;
			case Opcodes.IF_ICMPLT:
				e = pop();
				pushConjunct(Operation.lt(pop(), e));
				break;
			case Opcodes.IF_ICMPGE:
				e = pop();
				pushConjunct(Operation.ge(pop(), e));
				break;
			case Opcodes.IF_ICMPGT:
				e = pop();
				pushConjunct(Operation.gt(pop(), e));
				break;
			case Opcodes.IF_ICMPLE:
				e = pop();
				pushConjunct(Operation.le(pop(), e));
				break;
			case Opcodes.IF_ACMPEQ:
				e = pop();
				pushConjunct(Operation.eq(pop(), e));
				break;
			case Opcodes.IF_ACMPNE:
				e = pop();
				pushConjunct(Operation.ne(pop(), e));
				break;
			case Opcodes.IFNULL:
				e = pop();
				pushConjunct(Operation.eq(e, IntegerConstant.ZERO32));
				break;
			case Opcodes.IFNONNULL:
				e = pop();
				pushConjunct(Operation.ne(e, IntegerConstant.ZERO32));
				break;
			default:
				log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
				System.exit(1);
			}
		} else {
			switch (opcode) {
			case Opcodes.GOTO:
				break;
			case Opcodes.IFEQ:
			case Opcodes.IFNE:
			case Opcodes.IFLT:
			case Opcodes.IFGE:
			case Opcodes.IFGT:
			case Opcodes.IFLE:
			case Opcodes.IFNULL:
			case Opcodes.IFNONNULL:
				pop();
				break;
			case Opcodes.IF_ICMPEQ:
			case Opcodes.IF_ICMPNE:
			case Opcodes.IF_ICMPLT:
			case Opcodes.IF_ICMPGE:
			case Opcodes.IF_ICMPGT:
			case Opcodes.IF_ICMPLE:
			case Opcodes.IF_ACMPEQ:
			case Opcodes.IF_ACMPNE:
				pop();
				pop();
				break;
			default:
				log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
				System.exit(1);
			}
		}
		dumpFrames();
	}

	@Override
	public void postJumpInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		if (recordMode) {
			log.trace("(POST) {}", Bytecodes.toString(opcode));
			log.trace(">>> previous conjunct is false");
			broker.publishThread("post-jump-insn", new Tuple(instr, opcode));
			assert spc instanceof SegmentedPCIf;
			spc = ((SegmentedPCIf) spc).negate();
			checkLimitConjuncts();
			log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		}
	}

	@Override
	public void ldcInsn(int instr, int opcode, Object value) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {} {}", instr, Bytecodes.toString(opcode), value, value.getClass().getSimpleName());
		broker.publishThread("ldc-insn", new Tuple(instr, opcode, value));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.LDC:
			if (value instanceof Integer) {
				push(new IntegerConstant((int) value, 32));
			} else if (value instanceof Long) {
				push(new IntegerConstant((long) value, 64));
			} else if (value instanceof Float) {
				push(new RealConstant((float) value, 32));
			} else if (value instanceof Double) {
				push(new RealConstant((double) value, 64));
			} else if (value instanceof String) {
				String s = (String) value;
				int id = createArray();
				putField(id, "length", new IntegerConstant(s.length(), 32));
				for (int i = 0; i < s.length(); i++) {
					setArrayValue(id, i, new IntegerConstant(s.charAt(i), 32));
				}
				push(new IntegerConstant(id, 32));
			} else {
				push(IntegerConstant.ZERO32);
			}
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void iincInsn(int instr, int var, int increment) throws SymbolicException {
		final int opcode = 132;
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), increment);
		broker.publishThread("iinc-insn", new Tuple(instr, var, increment));
		checkLimitConjuncts();
		Expression e0 = getLocal(var);
		Expression e1 = new IntegerConstant(increment, 32);
		setLocal(var, Operation.add(e0, e1));
		dumpFrames();
	}

	@Override
	public void tableSwitchInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("table-switch-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		pendingSwitch.push(pop());
		dumpFrames();
	}

	@Override
	public void tableCaseInsn(int min, int max, int value) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		if (recordMode) {
			log.trace("CASE FOR {}", Bytecodes.toString(Opcodes.TABLESWITCH));
			checkLimitConjuncts();
			if (!pendingSwitch.isEmpty()) {
				pushConjunct(pendingSwitch.pop(), min, max, value);
			}
			dumpFrames();
		}
	}

	@Override
	public void lookupSwitchInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("lookup-switch-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	@Override
	public void multiANewArrayInsn(int instr, int opcode) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("multi-anew-array-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	// ======================================================================
	//
	// UTILITIES
	//
	// ======================================================================

	private static int getArgumentCount(String descriptor) {
		int count = 0;
		int i = 0;
		if (descriptor.charAt(i++) != '(') {
			return 0;
		}
		while (true) {
			char ch = descriptor.charAt(i++);
			if (ch == ')') {
				return count;
			} else if ((ch == 'B') || (ch == 'C') || (ch == 'D') || (ch == 'F') || (ch == 'I') || (ch == 'J')
					|| (ch == 'S') || (ch == 'Z')) {
				count++;
			} else if (ch == 'L') {
				i = descriptor.indexOf(';', i);
				if (i == -1) {
					return 0; // missing ';'
				}
				i++;
				count++;
			} else if (ch != '[') {
				return 0; // unknown character in signature 
			}
		}
	}

	public static String getReturnType(String descriptor) {
		int i = 0;
		if (descriptor.charAt(i++) != '(') {
			return "?"; // missing '('
		}
		i = descriptor.indexOf(')', i);
		if (i == -1) {
			return "?"; // missing ')'
		}
		return descriptor.substring(i + 1);
	}

	public static String getAsciiSignature(String descriptor) {
		return descriptor.replace('/', '_').replace("_", "_1").replace(";", "_2").replace("[", "_3").replace("(", "__")
				.replace(")", "__");
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	@Override
	public void noException() throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> no exception");
		for (Expression e : noExceptionExpression) {
			pushConjunct(e);
		}
		noExceptionExpression.clear();
		checkLimitConjuncts();
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		dumpFrames();
	}

	@Override
	public void startCatch(int instr) throws SymbolicException {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> exception occurred");
		int catchDepth = Thread.currentThread().getStackTrace().length;
		int deltaDepth = exceptionDepth - catchDepth;
		if (deltaDepth >= frames.size()) {
			log.trace(">>> symbolic record mode switched off");
			recordMode = false;
			mayRecord = false;
			symbolicMode = traceAll;
		} else {
			while (deltaDepth-- > 0) {
				frames.pop();
			}
			frames.peek().clear();
			push(throwable);
		}
		for (Expression e : noExceptionExpression) {
			pushConjunct(e, false);
		}
		noExceptionExpression.clear();
		checkLimitConjuncts();
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		dumpFrames();
	}

}
