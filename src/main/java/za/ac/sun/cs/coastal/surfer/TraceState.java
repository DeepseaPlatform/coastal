package za.ac.sun.cs.coastal.surfer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.surfer.TraceValueFactory.TraceValue;
import za.ac.sun.cs.coastal.symbolic.Branch;
import za.ac.sun.cs.coastal.symbolic.Choice;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.Path;
import za.ac.sun.cs.coastal.symbolic.State;
import za.ac.sun.cs.coastal.symbolic.ValueFactory.Value;
import za.ac.sun.cs.coastal.symbolic.exceptions.AbortedRunException;
import za.ac.sun.cs.coastal.symbolic.exceptions.CompletedRunException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SystemExitException;
import za.ac.sun.cs.coastal.utility.Translator;

public final class TraceState extends State {

	private boolean useCurrentValues;

	private int frameCount = 0;

	private boolean mayRecord = true;

	/**
	 * 
	 */
	private int triggeringIndex = -1;

	private boolean mayContinue = true;

	private PathTreeNode pathTreeNode;

	private final Set<Integer> setValues = new HashSet<>();

	private final Set<Integer> incValues = new HashSet<>();

	private final List<String> lines = new ArrayList<>();

	/**
	 * Create a new instance of the tracing state.
	 * 
	 * @param coastal instance of COASTAL that started this run
	 * @param input   input values for the run
	 */
	public TraceState(COASTAL coastal, Input input) { // throws InterruptedException
		super(coastal, input);
		useCurrentValues = (input == null) || (input.getSize() == 0);
		pathTreeNode = coastal.getPathTree().getRoot();
	}

	public void reset(Input input) {
		setTrackingMode(false);
		setRecordingMode(false);
		mayRecord = true;
		frameCount = 0;
		path = null;
		useCurrentValues = (input == null) || (input.getSize() == 0);
		this.input = (input == null) ? new Input() : input;
		pathTreeNode = coastal.getPathTree().getRoot();
		mayContinue = true;
		setValues.clear();
		incValues.clear();
	}

	public boolean mayContinue() {
		return mayContinue;
	}

	public void discontinue() {
		mayContinue = false;
	}

	/**
	 * Return the result of the execution.
	 * 
	 * @return result of the execution
	 */
	public Execution getExecution() {
		Execution result = new Execution(path, input);
		result.setPayload("setValues", new HashSet<Integer>(setValues));
		result.setPayload("incValues", new HashSet<Integer>(incValues));
		result.setPayload("lines", lines);
		return result;
	}

	/**
	 * Handle the termination of a method. The return value is handled elsewhere.
	 * The task of this method is to potentially switch off the symbolic tracking
	 * flag.
	 * 
	 * @return {@code true} if and only if symbolic tracking mode is still switched
	 *         on
	 */
	private boolean methodReturn() throws CompletedRunException {
		assert getTrackingMode();
		assert frameCount > 0;
		frameCount--;
		if ((frameCount == 0) && getRecordingMode()) {
			log.trace("******** symbolic record mode switched off (trace) ********");
			setRecordingMode(false);
			mayRecord = false;
			setTrackingMode(false);
			throw new CompletedRunException();
		}
		return getTrackingMode();
	}

	// ======================================================================
	//
	// STATE ROUTINES
	//
	// ======================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getNewVariableName()
	 */
	@Override
	public String getNewVariableName() {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getStringLength(int)
	 */
	@Override
	public TraceValue getStringLength(int stringId) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getStringChar(int, int)
	 */
	@Override
	public TraceValue getStringChar(int stringId, int index) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#push(za.ac.sun.cs.coastal.solver.
	 * Expression)
	 */
	@Override
	public void push(Value expr) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#pop()
	 */
	@Override
	public TraceValue pop() {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.ac.sun.cs.coastal.symbolic.State#pushExtraCondition(za.ac.sun.cs.coastal.
	 * solver.Expression)
	 */
	@Override
	public void pushExtraCondition(Expression extraCondition) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicBoolean(boolean, int)
	 */
	@Override
	public boolean createSymbolicBoolean(boolean currentValue, int uniqueId) {
		return false; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicByte(byte, int)
	 */
	@Override
	public byte createSymbolicByte(byte currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicShort(short, int)
	 */
	@Override
	public short createSymbolicShort(short currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicChar(char, int)
	 */
	@Override
	public char createSymbolicChar(char currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicInt(int, int)
	 */
	@Override
	public int createSymbolicInt(int currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicLong(long, int)
	 */
	@Override
	public long createSymbolicLong(long currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicFloat(float, int)
	 */
	@Override
	public float createSymbolicFloat(float currentValue, int uniqueId) {
		return 0; // TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicDouble(double, int)
	 */
	@Override
	public double createSymbolicDouble(double currentValue, int uniqueId) {
		return 0; // TODO
	}

	public int makeSymbolicInt(String newName) {
		return 0;
	}
	
	public short makeSymbolicShort(String newName) {
		return (short) 0;
	}
	
	public boolean makeSymbolicBoolean(String newName) {
		return false;
	}
	
	public byte makeSymbolicByte(String newName) {
		return (byte) 0;
	}
	
	public char makeSymbolicChar(String newName) {
		return '\0';
	}
	
	public long makeSymbolicLong(String newName) {
		return 0L;
	}
	
	public float makeSymbolicFloat(String newName) {
		return 0F;
	}
	
	public double makeSymbolicDouble(String newName) {
		return 0.0;
	}
	
	// ======================================================================
	//
	// METHOD ROUTINES
	//
	// ======================================================================

	/**
	 * Compute a value to use during the execution. This method caters for
	 * {@code byte}, {@code short}, {@code char}, {@code int}, and {@code long}
	 * values.
	 * 
	 * If the trigger description that the formal parameter must be symbolic, a new
	 * symbolic variable is created with the appropriate bounds. If a new input
	 * value is provided to use in the execution, the symbolic variable is set to
	 * that values. If not, the default value (that the system-under-test would have
	 * used naturally), is used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return value that will be used during the execution
	 */
	private long getConcreteIntegral(int triggerIndex, int index, int address, long currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				input.put(name, currentValue);
			}
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		Object concrete = input.get(name);
		if ((concrete == null) || !(concrete instanceof Long)) {
			return currentValue;
		} else {
			return (Long) concrete;
		}
	}

	/**
	 * Compute a value to use during the execution. This method caters for
	 * {@code float} and {@code double} values.
	 * 
	 * If the trigger description that the formal parameter must be symbolic, a new
	 * symbolic variable is created with the appropriate bounds. If a new input
	 * value is provided to use in the execution, the symbolic variable is set to
	 * that values. If not, the default value (that the system-under-test would have
	 * used naturally), is used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return value that will be used during the execution
	 */
	private double getConcreteReal(int triggerIndex, int index, int address, double currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				input.put(name, currentValue);
			}
			return currentValue;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) {
			return currentValue;
		}
		Object concrete = input.get(name);
		if ((concrete == null) || !(concrete instanceof Double)) {
			return currentValue;
		} else {
			return (Double) concrete;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteBoolean(int, int, int,
	 * boolean)
	 */
	@Override
	public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		return getConcreteIntegral(triggerIndex, index, address, currentValue ? 1 : 0) != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteByte(int, int, int, byte)
	 */
	@Override
	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		int value = (int) getConcreteIntegral(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (byte) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteShort(int, int, int,
	 * short)
	 */
	@Override
	public short getConcreteShort(int triggerIndex, int index, int address, short currentValue) {
		int value = (int) getConcreteIntegral(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (short) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteChar(int, int, int, char)
	 */
	@Override
	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		int value = (int) getConcreteIntegral(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (char) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteInt(int, int, int, int)
	 */
	@Override
	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		int value = (int) getConcreteIntegral(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (int) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteLong(int, int, int, long)
	 */
	@Override
	public long getConcreteLong(int triggerIndex, int index, int address, long currentValue) {
		long value = (long) getConcreteIntegral(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (long) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteFloat(int, int, int,
	 * float)
	 */
	@Override
	public float getConcreteFloat(int triggerIndex, int index, int address, float currentValue) {
		float value = (float) getConcreteReal(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (float) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteDouble(int, int, int,
	 * double)
	 */
	@Override
	public double getConcreteDouble(int triggerIndex, int index, int address, double currentValue) {
		double value = (double) getConcreteReal(triggerIndex, index, address, currentValue);
		input.put(index, value);
		return (double) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteString(int, int, int,
	 * java.lang.String)
	 */
	@Override
	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				int length = currentValue.length();
				coastal.setParameterSize(name, length);
				for (int i = 0; i < length; i++) {
					input.put(name + CHAR_SEPARATOR + i, (long) currentValue.charAt(i));
				}
			}
			input.put(index, currentValue);
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
			Object concrete = input.get(entryName);
			if ((concrete != null) && (concrete instanceof Long)) {
				chars[i] = (char) ((Long) concrete).intValue();
			} else {
				chars[i] = currentValue.charAt(i);
			}
		}
		input.put(index, new String(chars));
		return new String(chars);
	}

	/**
	 * Create an array of concrete values to use during the execution. This method
	 * caters for {@code boolean}, {@code byte}, {@code short}, {@code char},
	 * {@code int}, and {@code long} values.
	 * 
	 * If the trigger description that the formal parameter must be symbolic, new
	 * symbolic variables are created with the appropriate bounds. If new input
	 * values are provided to use in the execution, the symbolic variables are set
	 * to these values. If not, the default values (that the system-under-test would
	 * have used naturally), are used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentArray default values to use (if concolic execution does not
	 *                     override the values)
	 * @param convert      {@link Function} to convert a {@link Long} value to the
	 *                     desired type
	 * @return array of concrete values that will be used during the execution
	 */
	private Object getConcreteIntegralArray(int triggerIndex, int index, int address, Object currentArray,
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
							input.put(name + INDEX_SEPARATOR + i, ((Number) Array.get(currentArray, i)).longValue());
						}
					} else {
						for (int i = 0; i < length; i++) {
							input.put(name + INDEX_SEPARATOR + i,
									(long) ((Character) Array.get(currentArray, i)).charValue());
						}
					}
				}
			}
			input.put(index, currentArray);
			return currentArray;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			input.put(index, currentArray);
			return currentArray;
		}
		int length = Array.getLength(currentArray);
		Class<?> type = trigger.getParamType(index);
		assert type.isArray();
		Class<?> elementType = type.getComponentType();
		Object newArray = Array.newInstance(elementType, length);
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Object concrete = input.get(entryName);
			if ((concrete != null) && (concrete instanceof Long)) {
				Array.set(newArray, i, convert.apply((Long) concrete));
			} else {
				Array.set(newArray, i, Array.get(currentArray, i));
			}
		}
		input.put(index, newArray);
		return newArray;
	}

	/**
	 * Create an array of concrete values to use during the execution. This method
	 * caters for {@code float} and {@code double} values.
	 * 
	 * If the trigger description that the formal parameter must be symbolic, new
	 * symbolic variables are created with the appropriate bounds. (The
	 * {@code sizeInBits} is needed to create symbolic variables because the solver
	 * is bit-vector based.) If new input values are provided to use in the
	 * execution, the symbolic variables are set to these values. If not, the
	 * default values (that the system-under-test would have used naturally), are
	 * used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param currentArray default values to use (if concolic execution does not
	 *                     override the values)
	 * @param type         the type of the array
	 * @param convert      {@link Function} to convert a {@link Double} value to the
	 *                     desired type
	 * @return array of concrete values that will be used during the execution
	 */
	@SuppressWarnings("unused")
	private Object getConcreteRealArray(int triggerIndex, int index, int address, Object currentArray, Class<?> type,
			Function<Double, Object> convert) {
		if (useCurrentValues) {
			Trigger trigger = coastal.getTrigger(triggerIndex);
			String name = trigger.getParamName(index);
			if (name != null) {
				int length = Array.getLength(currentArray);
				coastal.setParameterSize(name, length);
				for (int i = 0; i < length; i++) {
					input.put(name + INDEX_SEPARATOR + i, (double) Array.get(currentArray, i));
				}
			}
			input.put(index, currentArray);
			return currentArray;
		}
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			input.put(index, currentArray);
			return currentArray;
		}
		int length = Array.getLength(currentArray);
		Object newArray = Array.newInstance(type, length);
		for (int i = 0; i < length; i++) {
			String entryName = name + INDEX_SEPARATOR + i;
			Object concrete = input.get(entryName);
			if ((concrete != null) && (concrete instanceof Double)) {
				Array.set(newArray, i, convert.apply((Double) concrete));
			} else {
				Array.set(newArray, i, Array.get(currentArray, i));
			}
		}
		input.put(index, newArray);
		return newArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteBooleanArray(int, int,
	 * int, boolean[])
	 */
	@Override
	public boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return (boolean[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (x != 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteByteArray(int, int, int,
	 * byte[])
	 */
	@Override
	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return (byte[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (byte) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteShortArray(int, int, int,
	 * short[])
	 */
	@Override
	public short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return (short[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (short) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteCharArray(int, int, int,
	 * char[])
	 */
	@Override
	public char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		return (char[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (char) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteIntArray(int, int, int,
	 * int[])
	 */
	@Override
	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return (int[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (int) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteLongArray(int, int, int,
	 * long[])
	 */
	@Override
	public long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return (long[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue, x -> (long) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteFloatArray(int, int, int,
	 * float[])
	 */
	@Override
	public float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		// TO DO: is this correct? should this be getConcreteRealArray???
		return (float[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (float) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteDoubleArray(int, int,
	 * int, double[])
	 */
	@Override
	public double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		// TO DO: is this correct? should this be getConcreteRealArray???
		return (double[]) getConcreteIntegralArray(triggerIndex, index, address, currentValue,
				x -> (double) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteStringArray(int, int,
	 * int, java.lang.String[])
	 */
	@Override
	public String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentValue) {
		if (input == null) {
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
				Object concrete = input.get(sentryName);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#triggerMethod(int, int)
	 */
	@Override
	public void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) {
		if (!getRecordingMode()) {
			setRecordingMode(mayRecord);
			if (getRecordingMode()) {
				log.trace("******** symbolic record mode switched on (trace) ********");
				mayRecord = false;
				setTrackingMode(true);
				frameCount++;
				triggeringIndex = triggerIndex;
			}
		}
		broker.publishThread("enter-method", methodNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#startMethod(int, int)
	 */
	@Override
	public void startMethod(int methodNumber, int argCount) {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("transferring {} arguments, methodNumber={}", argCount, methodNumber);
		frameCount++;
		broker.publishThread("enter-method", methodNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(boolean)
	 */
	@Override
	public void returnValue(boolean returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(char)
	 */
	@Override
	public void returnValue(char returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(double)
	 */
	@Override
	public void returnValue(double returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(float)
	 */
	@Override
	public void returnValue(float returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(int)
	 */
	@Override
	public void returnValue(int returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(long)
	 */
	@Override
	public void returnValue(long returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(short)
	 */
	@Override
	public void returnValue(short returnValue) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#linenumber(int, int)
	 */
	@Override
	public void linenumber(int instr, int line, String filename) {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> LINENUMBER {}", line);
		if (getRecordingMode()) {
			lines.add(filename + ":" + line);
		}
		broker.publishThread("linenumber", new Tuple(instr, line));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#label(int, java.lang.String)
	 */
	@Override
	public void label(int instr, String label) {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#insn(int, int)
	 */
	@Override
	public void insn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> {} (offset={})", Bytecodes.toString(opcode), instr);
		broker.publishThread("insn", new Tuple(instr, opcode));
		switch (opcode) {
		// case Opcodes.IDIV:
		// assert noExceptionExpression == null;
		// noExceptionExpression = new Operation(Operator.NE, e, Operation.ZERO);
		// exceptionDepth = Thread.currentThread().getStackTrace().length;
		// throwable = Operation.ZERO;
		// break;
		case Opcodes.IRETURN:
		case Opcodes.ARETURN:
		case Opcodes.RETURN:
			methodReturn();
			break;
		// case Opcodes.ATHROW:
		// assert noExceptionExpression == null;
		// noExceptionExpression = new Operation(Operator.NE, Operation.ZERO,
		// Operation.ZERO);
		// exceptionDepth = Thread.currentThread().getStackTrace().length;
		// throwable = pop();
		// break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#intInsn(int, int, int)
	 */
	@Override
	public void intInsn(int instr, int opcode, int operand) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#varInsn(int, int, int)
	 */
	@Override
	public void varInsn(int instr, int opcode, int var) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#typeInsn(int, int)
	 */
	@Override
	public void typeInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#fieldInsn(int, int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#methodInsn(int, int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#invokeDynamicInsn(int, int)
	 */
	@Override
	public void invokeDynamicInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(int, int)
	 */
	@Override
	public void jumpInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> {} (offset={})", Bytecodes.toString(opcode), instr);
		broker.publishThread("jump-insn", new Tuple(instr, opcode));
		switch (opcode) {
		case Opcodes.GOTO:
			break;
		case Opcodes.JSR:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		default:
			log.fatal("UNEXPECTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(int, int, int)
	 */
	@Override
	public void jumpInsn(int value, int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> {} (offset={})", Bytecodes.toString(opcode), instr);
		broker.publishThread("jump-insn", new Tuple(instr, opcode));
		switch (opcode) {
		case Opcodes.IFEQ:
			jumpInsn(instr, opcode, value == 0);
			break;
		case Opcodes.IFNE:
			jumpInsn(instr, opcode, value != 0);
			break;
		case Opcodes.IFLT:
			jumpInsn(instr, opcode, value < 0);
			break;
		case Opcodes.IFLE:
			jumpInsn(instr, opcode, value <= 0);
			break;
		case Opcodes.IFGT:
			jumpInsn(instr, opcode, value > 0);
			break;
		case Opcodes.IFGE:
			jumpInsn(instr, opcode, value >= 0);
			break;
		default:
			log.fatal("UNEXPECTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(java.lang.Object, int, int)
	 */
	@Override
	public void jumpInsn(Object value, int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> {} (offset={})", Bytecodes.toString(opcode), instr);
		switch (opcode) {
		case Opcodes.IFNULL:
			jumpInsn(instr, opcode, value == null);
			break;
		case Opcodes.IFNONNULL:
			jumpInsn(instr, opcode, value != null);
			break;
		default:
			log.fatal("UNEXPECTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(int, int, int, int)
	 */
	@Override
	public void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("--> {} (offset={})", Bytecodes.toString(opcode), instr);
		broker.publishThread("jump-insn", new Tuple(instr, opcode));
		switch (opcode) {
		case Opcodes.IF_ACMPEQ:
		case Opcodes.IF_ICMPEQ:
			setValues.add(value1);
			setValues.add(value2);
			jumpInsn(instr, opcode, value1 == value2);
			break;
		case Opcodes.IF_ACMPNE:
		case Opcodes.IF_ICMPNE:
			setValues.add(value1);
			setValues.add(value2);
			jumpInsn(instr, opcode, value1 != value2);
			break;
		case Opcodes.IF_ICMPLT:
			incValues.add(Math.abs(value1 - value2) + 1);
			jumpInsn(instr, opcode, value1 < value2);
			break;
		case Opcodes.IF_ICMPGE:
			incValues.add(Math.abs(value1 - value2) + 1);
			jumpInsn(instr, opcode, value1 >= value2);
			break;
		case Opcodes.IF_ICMPGT:
			incValues.add(Math.abs(value1 - value2) + 1);
			jumpInsn(instr, opcode, value1 > value2);
			break;
		case Opcodes.IF_ICMPLE:
			incValues.add(Math.abs(value1 - value2) + 1);
			jumpInsn(instr, opcode, value1 <= value2);
			break;
		default:
			log.fatal("UNEXPECTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}

	/**
	 * Generic jump instruction that keeps track of the position of the execution in
	 * the path tree. If a fully explored node is reached, execution is terminated.
	 * 
	 * @param instr  number of the instruction
	 * @param opcode instruction opcode
	 * @param result outcome of the binary branch
	 * @throws SymbolicException if the execution is aborted because if has been
	 *                           explored before
	 */
	private void jumpInsn(int instr, int opcode, boolean result) throws SymbolicException {
		if (getRecordingMode()) {
			broker.publishThread("jump-insn", new Tuple(instr, opcode));
			if (pathTreeNode != null) {
				int ptn = pathTreeNode.getId();
				if (result) {
					pathTreeNode = pathTreeNode.getChild(1);
				} else {
					pathTreeNode = pathTreeNode.getChild(0);
				}
				if (pathTreeNode != null) {
					log.trace("path tree node: #{} -> #{}", ptn, pathTreeNode.getId());
				}
				if ((pathTreeNode != null) && pathTreeNode.isFullyExplored()) {
					throw new AbortedRunException();
				}
			}
			Branch branch = new Trace.Binary();
			Choice choice = new Choice(branch, result ? 1 : 0);
			choice.setPayload("block", Integer.toString(instr));
			path = new Path(path, choice);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#postJumpInsn(int, int)
	 */
	@Override
	public void postJumpInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#ldcInsn(int, int, java.lang.Object)
	 */
	@Override
	public void ldcInsn(int instr, int opcode, Object value) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#iincInsn(int, int, int)
	 */
	@Override
	public void iincInsn(int instr, int var, int increment) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#tableSwitchInsn(int, int)
	 */
	@Override
	public void tableSwitchInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#tableCaseInsn(int, int, int)
	 */
	@Override
	public void tableCaseInsn(int min, int max, int value) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see za.ac.sun.cs.coastal.symbolic.State#lookupSwitchInsn(int, int)
	 */
	@Override
	public void lookupSwitchInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see za.ac.sun.cs.coastal.symbolic.State#lookupCaseInsn(int, int)
	 */
	@Override
	public void lookupCaseInsn(int id, int choice) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#multiANewArrayInsn(int, int)
	 */
	@Override
	public void multiANewArrayInsn(int instr, int opcode) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	// ======================================================================
	//
	// EXCEPTION HANDLING
	//
	// ======================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#noException()
	 */
	@Override
	public void noException() throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#startCatch(int)
	 */
	@Override
	public void startCatch(int instr) throws SymbolicException {
		throw new RuntimeException("INTERNAL ERROR -- SHOULD NOT BE INVOKED");
	}

	// ======================================================================
	//
	// SYMBOLIC INTERACTION
	//
	// ======================================================================

	/**
	 * Return a string representation of the trigger and its inputs.
	 * 
	 * @return a string representation of the trigger and its inputs
	 */
	private String gatherInputs() {
		if (triggeringIndex == -1) {
			return null;
		}
		StringBuilder inputs = new StringBuilder();
		Trigger trigger = coastal.getTrigger(triggeringIndex);
		inputs.append(trigger.getMethodName()).append('(');
		int argc = trigger.getParamCount();
		for (int argi = 0; argi < argc; argi++) {
			if (argi > 0) {
				inputs.append(", ");
			}
			String argName = trigger.getParamName(argi);
			if (argName == null) {
				inputs.append('*');
				continue;
			}
			inputs.append(argName).append("==");
			inputs.append(gatherConcreteValue(trigger, argi));
		}
		inputs.append(')');
		return inputs.toString();
	}

	/**
	 * Return a string representation for the value of a particular parameter
	 * (identified by {@code index}) of a given {@code trigger}.
	 * 
	 * @param trigger the specific trigger
	 * @param index   the index of the parameter
	 * @return a string representation of the value of the parameter
	 */
	private String gatherConcreteValue(Trigger trigger, int index) {
		return gatherConcreteValue0(trigger.getParamType(index), input.get(index));
	}

	/**
	 * Return a string representation of a {@code value} given its type.
	 * 
	 * @param argType the type of the value
	 * @param value   the value itself
	 * @return a string representation of the value
	 */
	private String gatherConcreteValue0(Class<?> argType, Object value) {
		StringBuilder repr = new StringBuilder();
		if (value == null) {
			repr.append("NULL");
		} else if (argType == boolean.class) {
			repr.append((Boolean) value);
		} else if (argType == byte.class) {
			repr.append((Integer) value);
		} else if (argType == short.class) {
			repr.append((Integer) value);
		} else if (argType == char.class) {
			repr.append('\'').append(Translator.translate("" + (Character) value)).append('\'');
		} else if (argType == int.class) {
			repr.append((Integer) value);
		} else if (argType == long.class) {
			repr.append((Long) value);
		} else if (argType == float.class) {
			repr.append((Float) value);
		} else if (argType == double.class) {
			repr.append((Double) value);
		} else if (argType == String.class) {
			repr.append('"').append(Translator.translate((String) value)).append('"');
		} else if (argType.isArray()) {
			Class<?> elmentType = argType.getComponentType();
			repr.append('[');
			for (int i = 0, n = Array.getLength(value); i < n; i++) {
				if (i > 0) {
					repr.append(", ");
				}
				repr.append(gatherConcreteValue0(elmentType, Array.get(value, i)));
			}
			repr.append(']');
		} else {
			repr.append("???");
		}
		return repr.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#stop()
	 */
	@Override
	public void stop() {
		if (!getTrackingMode()) {
			return;
		}
//		broker.publish("stop", new Tuple(this, null));
		String triggerValues = gatherInputs();
		broker.publish("stop", new Tuple(this, null, triggerValues));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#stop(java.lang.String)
	 */
	@Override
	public void stop(String message) {
		if (!getTrackingMode()) {
			return;
		}
//		broker.publish("stop", new Tuple(this, message));
		String triggerValues = gatherInputs();
		broker.publish("stop", new Tuple(this, message, triggerValues));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#mark(int)
	 */
	@Override
	public void mark(int marker) {
		if (!getTrackingMode()) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#mark(java.lang.String)
	 */
	@Override
	public void mark(String marker) {
		if (!getTrackingMode()) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#printPC()
	 */
	@Override
	public void printPC() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#printPC(java.lang.String)
	 */
	@Override
	public void printPC(String label) {
		// do nothing
	}

	// ======================================================================
	//
	// CLASS LOADING
	//
	// ======================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#loadClasses(java.lang.String)
	 */
	@Override
	public void loadClasses(String descriptor) {
		// do nothing
	}

	// ======================================================================
	//
	// SPECIAL ROUTINES
	//
	// ======================================================================

	public void systemExit(int status) throws SymbolicException {
		throw new SystemExitException();
	}

}
