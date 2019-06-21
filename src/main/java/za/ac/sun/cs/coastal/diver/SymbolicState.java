package za.ac.sun.cs.coastal.diver;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

import org.apache.commons.text.StringEscapeUtils;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Constant;
import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.IntegerConstant;
import za.ac.sun.cs.coastal.solver.IntegerVariable;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.solver.RealConstant;
import za.ac.sun.cs.coastal.solver.RealVariable;
import za.ac.sun.cs.coastal.symbolic.Branch;
import za.ac.sun.cs.coastal.symbolic.Choice;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.Path;
import za.ac.sun.cs.coastal.symbolic.State;
import za.ac.sun.cs.coastal.symbolic.exceptions.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;

/**
 * Implementation of abstract states for an execution. An instance of a symbolic
 * state mirrors a true execution, but captures only the information that is
 * required to perform concolic execution.
 */
public final class SymbolicState extends State {

	/**
	 * The limit on path lengths. In other words, this is the maximum number of
	 * branches per path.
	 */
	private final long limitConjuncts;

	/**
	 * Whether or not the program state should be tracked from the very beginning of
	 * the execution, or whether it should only be switched on when a trigger method
	 * is invoked.
	 */
	private final boolean trackAll;

	/**
	 * Whether or not the current path is in danger of exceeding the path length
	 * limit.
	 */
	private boolean dangerFlag = false;

	/**
	 * Whether or not record mode may be switched on. Initially this is true, but
	 * once a trigger has been fully explored, it is set to false, so that
	 * subsequent triggers cannot reactivate symbolic recording. This is important,
	 * because the symbolic information of such a second activation could interfere
	 * with the information of a first activation, rendering both sets of
	 * information unusable.
	 */
	private boolean mayRecord = true;

	/**
	 * A symbolic representation of the method invocation stack of the true
	 * execution.
	 */
	private final Stack<SymbolicFrame> frames = new Stack<>();

	/**
	 * The number of objects created during this execution.
	 */
	private int objectIdCount = 0;

	/**
	 * The number of symbolic variables created during this execution.
	 */
	private int newVariableCount = 0;

	/**
	 * A store for data about the heap and static variables.
	 * 
	 * TO DO: Add a description of of the mapping. Give examples.
	 */
	private final Map<String, Expression> instanceData = new HashMap<>();

	/**
	 * Extra constraints that are added to the next branching point as the "passive
	 * conjunct".
	 * 
	 * TO DO: Add a description of circumstances that create such constraints.
	 */
	private Expression pendingExtraCondition = null;

	/**
	 * List of conditions that describe that no exception has taken place since the
	 * last branching point.
	 * 
	 * TO DO: Add more detail about how this conditions are used.
	 */
	private final List<Expression> noExceptionExpression = new ArrayList<>();

	/**
	 * 
	 */
	private int exceptionDepth = 0;

	/**
	 * 
	 */
	private Expression throwable = null;

	/**
	 * 
	 */
	private int triggeringIndex = -1;

	/**
	 * 
	 */
	private boolean mayContinue = true;

	/**
	 * 
	 */
	private boolean justExecutedDelegate = false;

	/**
	 * 
	 */
	private int lastInvokingInstruction = 0;

	/**
	 * 
	 */
	private final Stack<Expression> pendingSwitch = new Stack<>();

	/**
	 * The class loader that is used for the system under test.
	 */
	private ClassLoader classLoader = null;

	/**
	 * Create a new instance of the symbolic state.
	 * 
	 * @param coastal instance of COASTAL that started this run
	 * @param input   input values for the run
	 */
	public SymbolicState(COASTAL coastal, Input input) { // throws InterruptedException
		super(coastal, input);
		limitConjuncts = ConfigHelper.limitLong(coastal.getConfig(), "coastal.settings.conjunct-limit");
		trackAll = coastal.getConfig().getBoolean("coastal.settings.trace-all", false);
		setTrackingMode(trackAll);
	}

	/**
	 * Set the class loader to the given value.
	 * 
	 * @param classLoader the actual class loader used for the system-under-test
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * Return the value of the {@link #mayContinue} flag that indicates whether
	 * clients (such as diver, surfers, and strategies) should continue work on the
	 * problem.
	 * 
	 * @return value of the {@link #mayContinue} flag
	 */
	public boolean mayContinue() {
		return mayContinue;
	}

	/**
	 * Set the {@link #mayContinue} flag to {@code false}.
	 */
	public void discontinue() {
		mayContinue = false;
	}

	/**
	 * Return the result of the execution.
	 * 
	 * @return result of the execution
	 */
	public Execution getExecution() {
		return new Execution(path, input);
	}

	/**
	 * Return the symbolic value of a local variable in the topmost invocation
	 * frame.
	 * 
	 * @param index the index of the variable
	 * @return the symbolic value of the local variable
	 */
	private Expression getLocal(int index) {
		return frames.peek().getLocal(index);
	}

	/**
	 * Return the symbolic value of a local variable in the topmost invocation
	 * frame.
	 * 
	 * @param index the index of the variable
	 * @param value the new symbolic value of the local variable
	 */
	private void setLocal(int index, Expression value) {
		frames.peek().setLocal(index, value);
	}

	/**
	 * Update the symbolic value of a field in a particular object instance.
	 * 
	 * @param objectId  the identifier of the object
	 * @param fieldName the name of the field
	 * @param value     the new symbolic expression for the field
	 */
	private void putField(int objectId, String fieldName, Expression value) {
		putField(Integer.toString(objectId), fieldName, value);
	}

	/**
	 * Update the symbolic value of a field in a particular object instance.
	 * 
	 * @param objectName the name of the object
	 * @param fieldName  the name of the field
	 * @param value      the new symbolic expression for the field
	 */
	private void putField(String objectName, String fieldName, Expression value) {
		String fullFieldName = objectName + FIELD_SEPARATOR + fieldName;
		instanceData.put(fullFieldName, value);
	}

	/**
	 * Return the symbolic value of a field in a particular object instance.
	 * 
	 * @param objectId  the identifier of the object
	 * @param fieldName the name of the field
	 * @return the symbolic expression for the value of the field
	 */
	private Expression getField(int objectId, String fieldName) {
		return getField(Integer.toString(objectId), fieldName);
	}

	/**
	 * Return the symbolic value of a field in a particular object instance. The
	 * object is identifier by its "name", which could simply be a string version of
	 * the object number, or a more complex name.
	 * 
	 * @param objectName the name of the object
	 * @param fieldName  the name of the field
	 * @return the symbolic expression for the value of the field
	 */
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

	/**
	 * Handle the termination of a method. The return value is handled elsewhere.
	 * The task of this method is to potentially switch off the symbolic tracking
	 * flag.
	 * 
	 * @return {@code true} if and only if symbolic tracking mode is still switched
	 *         on
	 */
	private boolean methodReturn() {
		assert getTrackingMode();
		assert !frames.isEmpty();
		int methodNumber = frames.pop().getMethodNumber();
		if (frames.isEmpty() && getRecordingMode()) {
			log.trace(">>> symbolic record mode switched off");
			setRecordingMode(false);
			mayRecord = false;
			setTrackingMode(trackAll);
		}
		broker.publishThread("exit-method", methodNumber);
		return getTrackingMode();
	}

	/**
	 * Increment and return the object counter.
	 * 
	 * @return the new value of the object counter
	 */
	private int incrAndGetNewObjectId() {
		return ++objectIdCount;
	}

	/**
	 * Dump the stack of invocation frames to the log.
	 */
	private void dumpFrames() {
		int n = frames.size();
		for (int i = n - 1; i >= 0; i--) {
			log.trace("--> st{} locals:{} <{}>", frames.get(i).stack, frames.get(i).locals,
					frames.get(i).getInvokingInstruction());
		}
		log.trace("--> data:{}", instanceData);
	}

	/**
	 * A convenience field. It is an array of the types of the parameters of a
	 * delegate method.
	 */
	private static final Class<?>[] DELEGATE_PARAMETERS = new Class<?>[] { SymbolicState.class };

	/**
	 * A convenience field. It is an array of the actual parameters passed to a
	 * delegate method.
	 */
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

	/**
	 * If the danger flag is switched on, check if the current path reaches or
	 * exceeds the path length limit. If so, an exception is raised. If the danger
	 * flag is set, it is reset to {@code false}.
	 * 
	 * @throws SymbolicException if the path length limit has been reached or
	 *                           exceeded
	 */
	private void checkLimitConjuncts() throws SymbolicException {
		if (dangerFlag) {
			if ((path != null) && (path.getDepth() >= limitConjuncts)) {
				throw new LimitConjunctException();
			}
			dangerFlag = false;
		}
	}

	/**
	 * Parse a Java type descriptor of a method and count and return the number of
	 * formal parameters.
	 * 
	 * @param descriptor the type descriptor of a method
	 * @return the number of formal arguments
	 */
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

	/**
	 * Extract and return the return type from a Java type descriptor of a method.
	 * The return type is returned in the Java type descriptor format and is not
	 * converted to an integer code or even an instance of {@link Class}.
	 * 
	 * @param descriptor the type descriptor of a method
	 * @return a type descriptor for the return type of the method as specified in
	 *         the given type descriptor
	 */
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

	/**
	 * Return the sanitized type descriptor for a method. This is used to convert a
	 * Java type descriptor string to a name that can be given to a method.
	 * 
	 * The method simply replaces "illegal" characters with legal characters:
	 * 
	 * <ul>
	 * <li>"<code>/</code>" is replaced with "<code>_</code>"</li>
	 * <li>"<code>_</code>" is replaced with "<code>_1</code>"</li>
	 * <li>"<code>;</code>" is replaced with "<code>_2</code>"</li>
	 * <li>"<code>[</code>" is replaced with "<code>_3</code>"</li>
	 * <li>"<code>(</code>" and "<code>)</code>" are replaced with "<code>__</code>"
	 * </li>
	 * </ul>
	 * 
	 * @param descriptor the original Java type descriptor
	 * @return the sanitized type descriptor
	 */
	public static String getAsciiSignature(String descriptor) {
		return descriptor.replace('/', '_').replace("_", "_1").replace(";", "_2").replace("[", "_3").replace("(", "__")
				.replace(")", "__");
	}

	/**
	 * Push a new symbolic variable onto the top of the expression stack of the
	 * topmost invocation frame to represent the return value of an untracked
	 * method. The nature of the symbolic variable is based on the Java type
	 * descriptor of the method's return type.
	 * 
	 * @param type the method's return type as a Java type descriptor
	 */
	private void pushReturnValue(char type) {
		if (type == 'Z') { // boolean
			push(new IntegerVariable(getNewVariableName(), 32, 0, 1), 32);
		} else if (type == 'B') { // byte
			byte min = (byte) coastal.getDefaultMinValue(byte.class);
			byte max = (byte) coastal.getDefaultMaxValue(byte.class);
			push(new IntegerVariable(getNewVariableName(), 8, min, max), 8);
		} else if (type == 'C') { // char
			char min = (char) coastal.getDefaultMinValue(char.class);
			char max = (char) coastal.getDefaultMaxValue(char.class);
			push(new IntegerVariable(getNewVariableName(), 16, min, max), 16);
		} else if (type == 'S') { // short
			short min = (short) coastal.getDefaultMinValue(short.class);
			short max = (short) coastal.getDefaultMaxValue(short.class);
			push(new IntegerVariable(getNewVariableName(), 16, min, max), 16);
		} else if (type == 'I') { // integer
			int min = (int) coastal.getDefaultMinValue(int.class);
			int max = (int) coastal.getDefaultMaxValue(int.class);
			push(new IntegerVariable(getNewVariableName(), 32, min, max), 32);
		} else if (type == 'J') { // long
			long min = (long) coastal.getDefaultMinValue(long.class);
			long max = (long) coastal.getDefaultMaxValue(long.class);
			push(new IntegerVariable(getNewVariableName(), 64, min, max), 64);
		} else if (type == 'F') { // float
			float min = (float) coastal.getDefaultMinValue(float.class);
			float max = (float) coastal.getDefaultMaxValue(float.class);
			push(new RealVariable(getNewVariableName(), 32, min, max), 32);
		} else if (type == 'D') { // double
			double min = (double) coastal.getDefaultMinValue(double.class);
			double max = (double) coastal.getDefaultMaxValue(double.class);
			push(new RealVariable(getNewVariableName(), 64, min, max), 64);
		} else if (type == 'L') { // object
			int min = 0;
			int max = Integer.MAX_VALUE;
			push(new IntegerVariable(getNewVariableName(), 32, min, max), 32);
		} else if ((type != 'V') && (type != '?')) {
			push(IntegerConstant.ZERO32, 32);
		}
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
		return NEW_VAR_PREFIX + newVariableCount++;
	}

	// ----------------------------------------------------------------------
	// ROUTINES TO SUPPORT SYMBOLIC ARRAYS
	// ----------------------------------------------------------------------

	/**
	 * Create a new array. Arrays are just objects, and this method simply
	 * increments and returns the object counter.
	 * 
	 * @return the new array identifier
	 */
	private int createArray() {
		return incrAndGetNewObjectId();
	}

	// private int getArrayLength(int arrayId) {
	// return ((IntConstant) getField(arrayId, "length")).getValue();
	// }

	/**
	 * Set the type of the elements of an array.
	 * 
	 * @param arrayId the array identifier
	 * @param type    the type of the array elements
	 */
	private void setArrayType(int arrayId, int type) {
		putField(arrayId, "type", new IntegerConstant(type, 32));
	}

	/**
	 * Set the length of an array.
	 * 
	 * @param arrayId the array identifier
	 * @param length  the length of the array
	 */
	private void setArrayLength(int arrayId, int length) {
		putField(arrayId, "length", new IntegerConstant(length, 32));
	}

	/**
	 * Return the symbolic value stored in an array at a particular index.
	 * 
	 * @param arrayId the array identifier
	 * @param index   the index of the element
	 * @return the symbolic element value in the array
	 */
	private Expression getArrayValue(int arrayId, int index) {
		return getField(arrayId, "" + index);
	}

	/**
	 * Set the symbolic value stored in an array at a particular index.
	 * 
	 * @param arrayId the array identifier
	 * @param index   the index of the element
	 * @param value   the new symbolic value of the array element
	 */
	private void setArrayValue(int arrayId, int index, Expression value) {
		if (index < 0) {
			return;
		}
		Expression length = getField(arrayId, "length");
		if (length instanceof IntegerVariable) {
			String name = ((IntegerVariable) length).getName();
			int i = (int) new IntegerConstant((Long) input.get(name), 32).getValue();
			if (i < index) {
				return;
			}
		} else {
			if (((IntegerConstant) length).getValue() < index) {
				return;
			}
		}
		putField(arrayId, "" + index, value);
	}

	// ----------------------------------------------------------------------
	// ROUTINES TO SUPPORT SYMBOLIC STRING VALUES
	// ----------------------------------------------------------------------

	/**
	 * Create a new string. Strings are just objects, and this method simply
	 * increments and returns the object counter.
	 * 
	 * @return new string identifier
	 */
	private int createString() {
		return incrAndGetNewObjectId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getStringLength(int)
	 */
	@Override
	public Expression getStringLength(int stringId) {
		return getField(stringId, "length");
	}

	/**
	 * Set the length for a string instance.
	 * 
	 * @param stringId unique string identifier
	 * @param length   new length
	 */
	private void setStringLength(int stringId, Expression length) {
		putField(stringId, "length", length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getStringChar(int, int)
	 */
	@Override
	public Expression getStringChar(int stringId, int index) {
		return getField(stringId, "" + index);
	}

	/**
	 * Set a character inside a string.
	 * 
	 * @param stringId unique string identifier
	 * @param index    character index to set
	 * @param value    value for the character
	 */
	private void setStringChar(int stringId, int index, Expression value) {
		putField(stringId, "" + index, value);
	}

	// ----------------------------------------------------------------------
	// ROUTINES TO MANIPULATE THE EXPRESSION STACK
	// ----------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#push(za.ac.sun.cs.coastal.solver.
	 * Expression)
	 */
	@Override
	public void push(Expression expr) {
		frames.peek().push(expr);
//		if (expr == null) {
//			push(IntegerConstant.ZERO32);
//		} else {
//			push(expr, expr.getBitSize());
//		}
	}

	public void push(Expression expr, int bitSize) {
		frames.peek().push(expr);
//		if (expr.isReal()) {
//			frames.peek().push(expr);
//		} else if (bitSize == 8) {
//			frames.peek().push(Operation.b2i(expr));
//		} else if (bitSize == 16) {
//			frames.peek().push(Operation.s2i(expr));
//		} else {
//			frames.peek().push(expr);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#pop()
	 */
	@Override
	public Expression pop() {
		return frames.peek().pop();
	}

	/**
	 * Return (but do not remove) the expression on the top of the expression stack
	 * of the topmost invocation frame.
	 * 
	 * @return the expression on top of the expression stack
	 */
	private Expression peek() {
		return frames.peek().peek();
	}

	// ----------------------------------------------------------------------
	// ROUTINES TO MANIPULATE THE PATH
	// ----------------------------------------------------------------------

	/**
	 * Add a binary conjunct to the current path. This also adds the accumulated
	 * passive conjuncts to the current path.
	 * 
	 * @param conjunct   the conjunct to add
	 * @param truthValue the value of the conjunct
	 */
	private void pushConjunct(Expression conjunct, boolean truthValue) {
		Branch branch = new SegmentedPC.Binary(conjunct, pendingExtraCondition);
		path = new Path(path, new Choice(branch, truthValue ? 1 : 0));
		pendingExtraCondition = null;
		log.trace(">>> adding conjunct: {}", conjunct.toString());
		log.trace(">>> path is now: {}", path.getPathCondition().toString());
	}

	/**
	 * Add a binary conjunct to the current path. The conjunct is assumed to be
	 * true.
	 * 
	 * @param conjunct the conjunct to add
	 */
	private void pushConjunct(Expression conjunct) {
		pushConjunct(conjunct, true);
	}

	/**
	 * Add an n-ary conjunct to the current path. This also adds the accumulated
	 * passive conjuncts to the current path.
	 * 
	 * @param expression the expression that determines the choice at this branching
	 *                   point
	 * @param min        the smallest value handled by the n-ary branching point
	 * @param max        the largest value handled by the n-ary branching point
	 * @param cur        the current value taken at the n-ary branching point
	 */
	private void pushConjunct(Expression expression, long min, long max, long cur) {
		Expression conjunct;
		if ((cur < min) || (cur > max)) {
			Expression lo = Operation.lt(expression, new IntegerConstant(min, 32));
			Expression hi = Operation.gt(expression, new IntegerConstant(max, 32));
			conjunct = Operation.or(lo, hi);
			cur = max + 1;
		} else {
			conjunct = Operation.eq(expression, new IntegerConstant(cur, 32));
		}
		Branch branch = new SegmentedPC.Nary(expression, min, max, pendingExtraCondition);
		path = new Path(path, new Choice(branch, cur - min));
		pendingExtraCondition = null;
		log.trace(">>> adding (switch) conjunct: {}", conjunct.toString());
		log.trace(">>> path is now: {}", path.getPathCondition().toString());
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
		if (!SegmentedPC.isConstant(extraCondition)) {
			if (pendingExtraCondition == null) {
				pendingExtraCondition = extraCondition;
			} else {
				pendingExtraCondition = Operation.and(extraCondition, pendingExtraCondition);
			}
		}
	}

	// ----------------------------------------------------------------------
	// ROUTINES TO CREATE NEW SYMBOLIC VARIABLES DURING EXECUTION
	// ----------------------------------------------------------------------

	public boolean createSymbolicBoolean(boolean currentValue, String name) {
		if (!getTrackingMode()) {
			return false;
		}
		pop();
		push(new IntegerVariable(name, 32, 0, 1), 32);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 32);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue ? 1 : 0));
			return currentValue;
		} else {
			boolean newValue = concrete.getValue() != 0;
			log.trace(">>> get symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public byte createSymbolicByte(byte currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new IntegerVariable(name, 8, Byte.MIN_VALUE, Byte.MAX_VALUE), 8);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 8);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue));
			return currentValue;
		} else {
			byte newValue = (byte) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public short createSymbolicShort(short currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new IntegerVariable(name, 16, Short.MIN_VALUE, Short.MAX_VALUE), 16);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 16);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue));
			return currentValue;
		} else {
			short newValue = (short) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public char createSymbolicChar(char currentValue, String name) {
		if (!getTrackingMode()) {
			return 0x00;
		}
		pop();
		push(new IntegerVariable(name, 16, Character.MIN_VALUE, Character.MAX_VALUE), 16);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 16);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue));
			return currentValue;
		} else {
			char newValue = (char) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public int createSymbolicInt(int currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new IntegerVariable(name, 32, Integer.MIN_VALUE, Integer.MAX_VALUE), 32);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 32);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue));
			return currentValue;
		} else {
			int newValue = (int) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public long createSymbolicLong(long currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new IntegerVariable(name, 64, Long.MIN_VALUE, Long.MAX_VALUE), 64);
		Long concreteVal = (input == null) ? null : (Long) input.get(name);
		IntegerConstant concrete = concreteVal == null ? null : new IntegerConstant(concreteVal, 64);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Long(currentValue));
			return currentValue;
		} else {
			long newValue = (long) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public float createSymbolicFloat(float currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new RealVariable(name, 32, Float.MIN_VALUE, Float.MAX_VALUE), 32);
		Double concreteVal = (input == null) ? null : (Double) input.get(name);
		RealConstant concrete = concreteVal == null ? null : new RealConstant(concreteVal, 32);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Double(currentValue));
			return currentValue;
		} else {
			float newValue = (float) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	public double createSymbolicDouble(double currentValue, String name) {
		if (!getTrackingMode()) {
			return 0;
		}
		pop();
		push(new RealVariable(name, 64, Double.MIN_VALUE, Double.MAX_VALUE), 64);
		Double concreteVal = (input == null) ? null : (Double) input.get(name);
		RealConstant concrete = concreteVal == null ? null : new RealConstant(concreteVal, 64);
		if (concrete == null) {
			log.trace(">>> create symbolic var {}, default value of {}", name, currentValue);
			input.put(name, new Double(currentValue));
			return currentValue;
		} else {
			double newValue = (double) concrete.getValue();
			log.trace(">>> create symbolic var {}, default value of {}", name, newValue);
			return newValue;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicBoolean(boolean, int)
	 */
	@Override
	public boolean createSymbolicBoolean(boolean currentValue, int uniqueId) {
		return createSymbolicBoolean(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicByte(byte, int)
	 */
	@Override
	public byte createSymbolicByte(byte currentValue, int uniqueId) {
		return createSymbolicByte(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicShort(short, int)
	 */
	@Override
	public short createSymbolicShort(short currentValue, int uniqueId) {
		return createSymbolicShort(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicChar(char, int)
	 */
	@Override
	public char createSymbolicChar(char currentValue, int uniqueId) {
		return createSymbolicChar(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicInt(int, int)
	 */
	@Override
	public int createSymbolicInt(int currentValue, int uniqueId) {
		return createSymbolicInt(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicLong(long, int)
	 */
	@Override
	public long createSymbolicLong(long currentValue, int uniqueId) {
		return createSymbolicLong(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicFloat(float, int)
	 */
	@Override
	public float createSymbolicFloat(float currentValue, int uniqueId) {
		return createSymbolicFloat(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#createSymbolicDouble(double, int)
	 */
	@Override
	public double createSymbolicDouble(double currentValue, int uniqueId) {
		return createSymbolicDouble(currentValue, CREATE_VAR_PREFIX + uniqueId);
	}

	@Override
	public boolean makeSymbolicBoolean(String newName) {
		return createSymbolicBoolean(false, newName);
	}

	@Override
	public int makeSymbolicInt(String newName) {
		return createSymbolicInt(0, newName);
	}

	@Override
	public short makeSymbolicShort(String newName) {
		return createSymbolicShort((short) 0, newName);
	}

	@Override
	public byte makeSymbolicByte(String newName) {
		return createSymbolicByte((byte) 0, newName);
	}

	@Override
	public char makeSymbolicChar(String newName) {
		return createSymbolicChar('\0', newName);
	}

	@Override
	public long makeSymbolicLong(String newName) {
		return createSymbolicLong(0L, newName);
	}

	@Override
	public float makeSymbolicFloat(String newName) {
		return createSymbolicFloat(0F, newName);
	}

	@Override
	public double makeSymbolicDouble(String newName) {
		return createSymbolicDouble(0.0, newName);
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
	 * symbolic variable is created with the appropriate bounds. (The
	 * {@code sizeInBits} is needed to create the symbolic variable because the
	 * solver is bit-vector based.) If a new input value is provided to use in the
	 * execution, the symbolic variable is set to that values. If not, the default
	 * value (that the system-under-test would have used naturally), is used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param sizeInBits   size of the desired type
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return value that will be used during the execution
	 */
	private long getConcreteIntegral(int triggerIndex, int index, int address, int sizeInBits, long currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntegerConstant(currentValue, sizeInBits));
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
		setLocal(address, new IntegerVariable(name, sizeInBits, min, max));
		Long concrete = (Long) ((input == null) ? null : input.get(name));
		return (concrete == null) ? currentValue : concrete;
	}

	/**
	 * Compute a value to use during the execution. This method caters for
	 * {@code float} and {@code double} values.
	 * 
	 * If the trigger description that the formal parameter must be symbolic, a new
	 * symbolic variable is created with the appropriate bounds. (The
	 * {@code sizeInBits} is needed to create the symbolic variable because the
	 * solver is bit-vector based.) If a new input value is provided to use in the
	 * execution, the symbolic variable is set to that values. If not, the default
	 * value (that the system-under-test would have used naturally), is used.
	 * 
	 * @param triggerIndex index of the triggering method
	 * @param index        index of the formal parameter
	 * @param address      local variable address where parameter is stored
	 * @param sizeInBits   size of the desired type
	 * @param currentValue default values to use (if concolic execution does not
	 *                     override the values)
	 * @return value that will be used during the execution
	 */
	private double getConcreteReal(int triggerIndex, int index, int address, int sizeInBits, double currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new RealConstant(currentValue, sizeInBits));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		double min = ((Number) coastal.getMinBound(name, type)).doubleValue();
		double max = ((Number) coastal.getMaxBound(name, type)).doubleValue();
		setLocal(address, new RealVariable(name, sizeInBits, min, max));
		Double concrete = (Double) ((input == null) ? null : input.get(name));
		return (concrete == null) ? currentValue : concrete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteBoolean(int, int, int,
	 * boolean)
	 */
	@Override
	public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntegerConstant(currentValue ? 1 : 0, 32));
			return currentValue;
		}
		// Class<?> type = trigger.getParamType(index);
		int min = 0;
		int max = 1;
		setLocal(address, new IntegerVariable(name, 32, min, max));
		Long concrete = (Long) ((input == null) ? null : input.get(name));
		boolean value = (concrete == null) ? currentValue : (concrete != 0);
		input.put(index, value);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteByte(int, int, int, byte)
	 */
	@Override
	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		int value = (int) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
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
		int value = (int) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
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
		int value = (int) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
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
		int value = (int) getConcreteIntegral(triggerIndex, index, address, 32, currentValue);
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
		long value = (long) getConcreteIntegral(triggerIndex, index, address, 64, currentValue);
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
		float value = (float) getConcreteReal(triggerIndex, index, address, 32, currentValue);
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
		double value = (double) getConcreteReal(triggerIndex, index, address, 64, currentValue);
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
			input.put(index, currentValue);
			return currentValue;
		} else {
			char minChar = (Character) coastal.getDefaultMinValue(char.class);
			char maxChar = (Character) coastal.getDefaultMaxValue(char.class);
			char[] chars = new char[length];
			currentValue.getChars(0, length, chars, 0); // copy string into chars[]
			for (int i = 0; i < length; i++) {
				String entryName = name + CHAR_SEPARATOR + i;
				Object concrete = ((name == null) || (input == null)) ? null : input.get(entryName);
				Expression entryExpr = new IntegerVariable(entryName, 32, minChar, maxChar);
				if ((concrete != null) && (concrete instanceof Long)) {
					chars[i] = (char) ((Long) concrete).intValue();
				}
				setArrayValue(stringId, i, entryExpr);
			}
			setLocal(address, new IntegerConstant(stringId, 32));
			input.put(index, new String(chars));
			return new String(chars);
		}
	}

	/**
	 * Create an array of concrete values to use during the execution. This method
	 * caters for {@code boolean}, {@code byte}, {@code short}, {@code char},
	 * {@code int}, and {@code long} values.
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
	 * @param sizeInBits   size of the desired type
	 * @param currentArray default values to use (if concolic execution does not
	 *                     override the values)
	 * @param unconvert    {@link Function} to convert an object to a {@link Long}
	 *                     value
	 * @param convert      {@link Function} to convert a {@link Long} value to the
	 *                     desired type
	 * @return array of concrete values that will be used during the execution
	 */
	private Object getConcreteIntegralArray(int triggerIndex, int index, int address, int sizeInBits,
			Object currentArray, Function<Object, Long> unconvert, Function<Long, Object> convert) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = Array.getLength(currentArray);
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new IntegerConstant(unconvert.apply(Array.get(currentArray, i)), sizeInBits));
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			input.put(index, currentArray);
			return currentArray;
		} else {
			Class<?> type = trigger.getParamType(index);
			assert type.isArray();
			Class<?> elementType = type.getComponentType();
			Object newArray = Array.newInstance(elementType, length);
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Object concrete = ((name == null) || (input == null)) ? null : input.get(entryName);
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
				Expression entryExpr = new IntegerVariable(entryName, sizeInBits, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			input.put(index, newArray);
			return newArray;
		}
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
	 * @param sizeInBits   size of the desired type
	 * @param currentArray default values to use (if concolic execution does not
	 *                     override the values)
	 * @param unconvert    {@link Function} to convert an object to a {@link Double}
	 * @param convert      {@link Function} to convert a {@link Double} value to the
	 *                     desired type
	 * @return array of concrete values that will be used during the execution
	 */
	private Object getConcreteRealArray(int triggerIndex, int index, int address, int sizeInBits, Object currentArray,
			Function<Object, Double> unconvert, Function<Double, Object> convert) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = Array.getLength(currentArray);
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new RealConstant(unconvert.apply(Array.get(currentArray, i)), sizeInBits));
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			input.put(index, currentArray);
			return currentArray;
		} else {
			Class<?> type = trigger.getParamType(index);
			Object newArray = Array.newInstance(type, length);
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Object concrete = ((name == null) || (input == null)) ? null : input.get(entryName);
				if ((concrete != null) && (concrete instanceof Double)) {
					Array.set(newArray, i, convert.apply((Double) concrete));
				} else {
					Array.set(newArray, i, Array.get(currentArray, i));
				}
				double min = (Double) coastal.getMinBound(entryName, name, type);
				double max = (Double) coastal.getMaxBound(entryName, name, type);
				Expression entryExpr = new RealVariable(entryName, sizeInBits, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			input.put(index, newArray);
			return newArray;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteBooleanArray(int, int,
	 * int, boolean[])
	 */
	@Override
	public boolean[] getConcreteBooleanArray(int triggerIndex, int index, int address, boolean[] currentValue) {
		return (boolean[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue,
				o -> ((Boolean) o) ? 1L : 0, x -> (x != 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteByteArray(int, int, int,
	 * byte[])
	 */
	@Override
	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		return (byte[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Byte) o,
				x -> (byte) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteShortArray(int, int, int,
	 * short[])
	 */
	@Override
	public short[] getConcreteShortArray(int triggerIndex, int index, int address, short[] currentValue) {
		return (short[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Short) o,
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
		return (char[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue,
				o -> (long) (Character) o, x -> (char) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteIntArray(int, int, int,
	 * int[])
	 */
	@Override
	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		return (int[]) getConcreteIntegralArray(triggerIndex, index, address, 32, currentValue, o -> (long) (Integer) o,
				x -> (int) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteLongArray(int, int, int,
	 * long[])
	 */
	@Override
	public long[] getConcreteLongArray(int triggerIndex, int index, int address, long[] currentValue) {
		return (long[]) getConcreteIntegralArray(triggerIndex, index, address, 64, currentValue, o -> (long) (Long) o,
				x -> (long) x.intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteFloatArray(int, int, int,
	 * float[])
	 */
	@Override
	public float[] getConcreteFloatArray(int triggerIndex, int index, int address, float[] currentValue) {
		return (float[]) getConcreteRealArray(triggerIndex, index, address, 32, currentValue, o -> (double) (Float) o,
				x -> (float) x.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteDoubleArray(int, int,
	 * int, double[])
	 */
	@Override
	public double[] getConcreteDoubleArray(int triggerIndex, int index, int address, double[] currentValue) {
		return (double[]) getConcreteRealArray(triggerIndex, index, address, 64, currentValue, o -> (double) (Double) o,
				x -> (double) x.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#getConcreteStringArray(int, int,
	 * int, java.lang.String[])
	 */
	@Override
	public String[] getConcreteStringArray(int triggerIndex, int index, int address, String[] currentArray) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = (currentArray == null) ? 0 : currentArray.length;
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				int stringLength = currentArray[i].length();
				int stringId = createString();
				for (int j = 0; j < stringLength; j++) {
					IntegerConstant chValue = new IntegerConstant(currentArray[i].charAt(j), 16);
					setStringChar(stringId, i, chValue);
				}
				setArrayValue(arrayId, i, new IntegerConstant(stringId, 32));
			}
			setLocal(index, new IntegerConstant(arrayId, 32));
			input.put(index, currentArray);
			return currentArray;
		} else {
			throw new RuntimeException("UNIMPLEMENTED METHOD");
//			char minChar = (Character) coastal.getDefaultMinValue(char.class);
//			char maxChar = (Character) coastal.getDefaultMaxValue(char.class);
//			String[] newArray = new String[length];
//			for (int i = 0; i < length; i++) {
//				String entryName = name + INDEX_SEPARATOR + i;
//				/*???*/ Object concrete = ((name == null) || (input == null)) ? null : input.get(entryName);
//				char[] chars = new char[length];
//			}
//			setLocal(index, new IntegerConstant(arrayId, 32));
//			input.put(index, newArray);
//			return newArray;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#triggerMethod(int, int, boolean)
	 */
	@Override
	public void triggerMethod(int methodNumber, int triggerIndex, boolean isStatic) {
		if (!getRecordingMode()) {
			setRecordingMode(mayRecord);
			if (getRecordingMode()) {
				log.trace(">>> symbolic record mode switched on");
				mayRecord = false;
				setTrackingMode(true);
				Expression thisValue = isStatic ? null : peek();
				frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
				if (!isStatic) {
					setLocal(0, thisValue);
				}
				dumpFrames();
				triggeringIndex = triggerIndex;
			}
		}
		broker.publishThread("enter-method", methodNumber);
	}

	/**
	 * A small stack where method parameters are stored temporarily.
	 */
	private final Stack<Expression> params = new Stack<>();

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
		log.trace(">>> transferring arguments");
		if (frames.isEmpty()) {
			frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
			for (int i = 0; i < argCount; i++) {
				setLocal(1, IntegerConstant.ZERO32);
			}
		} else {
			assert params.isEmpty();
			for (int i = 0; i < argCount; i++) {
				params.push(pop());
			}
			frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
			for (int i = 0; i < argCount; i++) {
				setLocal(i, params.pop());
			}
		}
		dumpFrames();
		broker.publishThread("enter-method", methodNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(boolean)
	 */
	@Override
	public void returnValue(boolean returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = returnValue ? IntegerConstant.ONE32 : IntegerConstant.ZERO32;
			pushExtraCondition(Operation.eq(peek(), value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(char)
	 */
	@Override
	public void returnValue(char returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 16);
			pushExtraCondition(Operation.eq(peek(), value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(double)
	 */
	@Override
	public void returnValue(double returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE double");
		System.exit(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(float)
	 */
	@Override
	public void returnValue(float returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE float");
		System.exit(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(int)
	 */
	@Override
	public void returnValue(int returnValue) {
		if (!getTrackingMode()) {
			return;
		}
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 32);
			pushExtraCondition(Operation.eq(peek(), value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(long)
	 */
	@Override
	public void returnValue(long returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE long");
		System.exit(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#returnValue(short)
	 */
	@Override
	public void returnValue(short returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntegerConstant(returnValue, 32);
			pushExtraCondition(Operation.eq(peek(), value));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#linenumber(int, int)
	 */
	@Override
	public void linenumber(int instr, int line) {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("### LINENUMBER {}", line);
		broker.publishThread("linenumber", new Tuple(instr, line));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#label(int, java.lang.String)
	 */
	@Override
	public void label(int instr, String label) {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("### LABEL {}", label);
		broker.publishThread("label", new Tuple(instr, label));
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
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ACONST_NULL:
			push(IntegerConstant.ZERO32, 32);
			break;
		case Opcodes.ICONST_M1:
			push(new IntegerConstant(-1, 32), 32);
			break;
		case Opcodes.ICONST_0:
			push(IntegerConstant.ZERO32, 32);
			break;
		case Opcodes.ICONST_1:
			push(IntegerConstant.ONE32, 32);
			break;
		case Opcodes.ICONST_2:
			push(new IntegerConstant(2, 32), 32);
			break;
		case Opcodes.ICONST_3:
			push(new IntegerConstant(3, 32), 32);
			break;
		case Opcodes.ICONST_4:
			push(new IntegerConstant(4, 32), 32);
			break;
		case Opcodes.ICONST_5:
			push(new IntegerConstant(5, 32), 32);
			break;
		case Opcodes.LCONST_0:
			push(IntegerConstant.ZERO64, 64);
			break;
		case Opcodes.LCONST_1:
			push(IntegerConstant.ONE64, 64);
			break;
		case Opcodes.FCONST_0:
			push(RealConstant.ZERO32, 32);
			break;
		case Opcodes.FCONST_1:
			push(new RealConstant(1, 32), 32);
			break;
		case Opcodes.FCONST_2:
			push(new RealConstant(2, 32), 32);
			break;
		case Opcodes.DCONST_0:
			push(RealConstant.ZERO64, 64);
			break;
		case Opcodes.DCONST_1:
			push(new RealConstant(1, 64), 64);
			break;
		case Opcodes.IALOAD:
			// check if i is a variable, if a variable add noExceptionExpression 0 <= i <
			// size
			int i = 0;
			Expression idx = pop();
			if (idx instanceof IntegerVariable) {
				String name = ((IntegerVariable) idx).getName();
				i = (int) new IntegerConstant((Long) input.get(name), 32).getValue();
			} else {
				i = (int) ((IntegerConstant) idx).getValue();
			}
			int a = (int) ((IntegerConstant) pop()).getValue();
			push(getArrayValue(a, i));
			noExceptionExpression.add(
					Operation.and(Operation.le(IntegerConstant.ZERO32, idx), Operation.lt(idx, getField(a, "length"))));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = new IntegerConstant(i, 32);
			noException();
			break;
		case Opcodes.AALOAD:
			i = (int) ((IntegerConstant) pop()).getValue();
			a = (int) ((IntegerConstant) pop()).getValue();
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
			idx = pop();
			if (idx instanceof IntegerVariable) {
				String name = ((IntegerVariable) idx).getName();
				i = (int) new IntegerConstant((Long) input.get(name), 32).getValue();
			} else {
				i = (int) ((IntegerConstant) idx).getValue();
			}
			a = (int) ((IntegerConstant) pop()).getValue();
			setArrayValue(a, i, e);
			noExceptionExpression.add(
					Operation.and(Operation.le(IntegerConstant.ZERO32, idx), Operation.lt(idx, getField(a, "length"))));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = new IntegerConstant(i, 32);
			noException();
			break;
		case Opcodes.AASTORE:
			e = pop();
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
		case Opcodes.LNEG:
			push(Operation.mul(pop(), new IntegerConstant(-1, 64)));
			break;
		case Opcodes.INEG:
			push(Operation.mul(pop(), new IntegerConstant(-1, 32)));
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
		case Opcodes.LUSHR:
		case Opcodes.IUSHR:
			e = pop();
			push(Operation.lshr(pop(), e));
			break;
		case Opcodes.LSHR:
		case Opcodes.ISHR:
			e = pop();
			push(Operation.ashr(pop(), e));
			break;
		case Opcodes.LSHL:
		case Opcodes.ISHL:
			e = pop();
			push(Operation.shl(pop(), e));
			break;
		case Opcodes.LOR:
		case Opcodes.IOR:
			e = pop();
			push(Operation.bitor(pop(), e));
			break;
		case Opcodes.LAND:
		case Opcodes.IAND:
			e = pop();
			push(Operation.bitand(pop(), e));
			break;
		case Opcodes.LXOR:
		case Opcodes.IXOR:
			e = pop();
			push(Operation.bitxor(pop(), e));
			break;
		case Opcodes.IDIV:
			e = pop();
			Expression idiv = Operation.div(pop(), e);
			push(idiv);
			noExceptionExpression.add(Operation.ne(e, IntegerConstant.ZERO32));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.LDIV:
			e = pop();
			Expression ldiv = Operation.div(pop(), e);
			push(ldiv);
			noExceptionExpression.add(Operation.ne(e, IntegerConstant.ZERO64));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO64;
			break;
		case Opcodes.FDIV:
			e = pop();
			Expression fdiv = Operation.div(pop(), e);
			push(fdiv);
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO32));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.DDIV:
			e = pop();
			Expression ddiv = Operation.div(pop(), e);
			push(ddiv);
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO64));
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO64;
			break;
		case Opcodes.LREM:
			e = pop();
			Expression rem = Operation.rem(pop(), e);
			push(rem);
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO64));
			// Add ExceptionExpression to noExceptionExpressionList
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO64;
			break;
		case Opcodes.IREM:
			e = pop();
			rem = Operation.rem(pop(), e);
			push(rem);
			noExceptionExpression.add(Operation.ne(e, RealConstant.ZERO32));
			// Add ExceptionExpression to noExceptionExpressionList
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = IntegerConstant.ZERO32;
			break;
		case Opcodes.LSUB:
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
		case Opcodes.I2S:
			push(Operation.i2s(pop()));
			break;
		case Opcodes.I2B:
			push(Operation.i2b(pop()));
			break;
		case Opcodes.I2C:
			push(Operation.i2c(pop()));
			break;
//		case Opcodes.L2I:
//			push(Operation.l2i(pop()));
//			break;

		// case Opcodes.D2F:
		// break;
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
		case Opcodes.LRETURN:
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
			// broker.publish("assert-failed", new Tuple(this, null));
			break;
		case Opcodes.MONITORENTER:
			pop();
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#intInsn(int, int, int)
	 */
	@Override
	public void intInsn(int instr, int opcode, int operand) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), operand);
		broker.publishThread("int-insn", new Tuple(instr, opcode, operand));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.BIPUSH:
			push(new IntegerConstant(operand, 32), 32);
			break;
		case Opcodes.SIPUSH:
			push(new IntegerConstant(operand, 32), 32);
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
			int n = 0;
			if (e instanceof IntegerVariable) {
				String name = ((IntegerVariable) e).getName();
				n = (int) new IntegerConstant((Long) input.get(name), 32).getValue();
			} else {
				n = (int) ((IntegerConstant) e).getValue();
			}
			int id = createArray();
			setArrayType(id, operand);
			setArrayLength(id, n);
			for (int i = 0; i < n; i++) {
				setArrayValue(id, i, init);
			}
			push(new IntegerConstant(id, 32), 32);
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} (opcode: {})", instr, Bytecodes.toString(opcode), operand,
					opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#varInsn(int, int, int)
	 */
	@Override
	public void varInsn(int instr, int opcode, int var) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), var);
		broker.publishThread("var-insn", new Tuple(instr, opcode, var));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ILOAD:
		case Opcodes.ALOAD:
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#typeInsn(int, int)
	 */
	@Override
	public void typeInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("type-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.NEW:
			int id = incrAndGetNewObjectId();
			push(new IntegerConstant(id, 32), 32);
			break;
		case Opcodes.CHECKCAST:
			break;
		case Opcodes.ANEWARRAY:
			int size = (int) ((IntegerConstant) pop()).getValue();
			id = incrAndGetNewObjectId();
			setArrayLength(id, size);
			push(new IntegerConstant(id, 32), 32);
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
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
		if (!getTrackingMode()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#methodInsn(int, int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {} {} {} {}", instr, Bytecodes.toString(opcode), owner, name, descriptor);
		broker.publishThread("method-insn", new Tuple(instr, opcode, owner, name, descriptor));
		checkLimitConjuncts();
		lastInvokingInstruction = instr;
		switch (opcode) {
		case Opcodes.INVOKEVIRTUAL:
		case Opcodes.INVOKESPECIAL:
		case Opcodes.INVOKEINTERFACE:
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#invokeDynamicInsn(int, int)
	 */
	@Override
	public void invokeDynamicInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(int, int, int)
	 */
	@Override
	public void jumpInsn(int value, int instr, int opcode) throws SymbolicException {
		jumpInsn(instr, opcode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(java.lang.Object, int, int)
	 */
	@Override
	public void jumpInsn(Object value, int instr, int opcode) throws SymbolicException {
		jumpInsn(instr, opcode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#jumpInsn(int, int, int, int)
	 */
	@Override
	public void jumpInsn(int value1, int value2, int instr, int opcode) throws SymbolicException {
		jumpInsn(instr, opcode);
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
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("jump-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		if (getRecordingMode()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#postJumpInsn(int, int)
	 */
	@Override
	public void postJumpInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		if (getRecordingMode()) {
			log.trace("(POST) {}", Bytecodes.toString(opcode));
			log.trace(">>> previous conjunct is false");
			broker.publishThread("post-jump-insn", new Tuple(instr, opcode));
			Choice lastChoice = path.getChoice();
			Branch lastBranch = lastChoice.getBranch();
			assert lastBranch instanceof SegmentedPC.Binary;
			long otherAlternative = 1 - lastChoice.getAlternative();
			path = new Path(path.getParent(), new Choice(lastBranch, otherAlternative));
			checkLimitConjuncts();
			log.trace(">>> path is now: {}", path.getPathCondition().toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#ldcInsn(int, int, java.lang.Object)
	 */
	/*
	 * Note: This is a partial implementation. It only handles some of the possible
	 * values of value. The value can be "a non null Integer, a Float, a Long, a
	 * Double, a String, a Type of OBJECT or ARRAY sort for .class constants, for
	 * classes whose version is 49, a Type of METHOD sort for MethodType, a Handle
	 * for MethodHandle constants, for classes whose version is 51 or a
	 * ConstantDynamic for a constant dynamic for classes whose version is 55."
	 */
	@Override
	public void ldcInsn(int instr, int opcode, Object value) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {} {} {}", instr, Bytecodes.toString(opcode), value, value.getClass().getSimpleName());
		broker.publishThread("ldc-insn", new Tuple(instr, opcode, value));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.LDC:
			if (value instanceof Integer) {
				push(new IntegerConstant((int) value, 32), 32);
			} else if (value instanceof Long) {
				push(new IntegerConstant((long) value, 64), 64);
			} else if (value instanceof Float) {
				push(new RealConstant((float) value, 32), 32);
			} else if (value instanceof Double) {
				push(new RealConstant((double) value, 64), 64);
			} else if (value instanceof String) {
				String s = (String) value;
				int id = createArray();
				putField(id, "length", new IntegerConstant(s.length(), 32));
				for (int i = 0; i < s.length(); i++) {
					setArrayValue(id, i, new IntegerConstant(s.charAt(i), 32));
				}
				push(new IntegerConstant(id, 32), 32);
			} else {
				push(IntegerConstant.ZERO32, 32);
			}
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#iincInsn(int, int, int)
	 */
	@Override
	public void iincInsn(int instr, int var, int increment) throws SymbolicException {
		final int opcode = 132;
		if (!getTrackingMode()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#tableSwitchInsn(int, int)
	 */
	@Override
	public void tableSwitchInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("table-switch-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		pendingSwitch.push(pop());
		dumpFrames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#tableCaseInsn(int, int, int)
	 */
	@Override
	public void tableCaseInsn(int min, int max, int value) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		if (getRecordingMode()) {
			log.trace("CASE {} FOR TABLESWITCH ({} .. {})", value, min, max);
			checkLimitConjuncts();
			if (!pendingSwitch.isEmpty()) {
				pushConjunct(pendingSwitch.pop(), min, max, value);
			}
			dumpFrames();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#lookupSwitchInsn(int, int)
	 */
	@Override
	public void lookupSwitchInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#multiANewArrayInsn(int, int)
	 */
	@Override
	public void multiANewArrayInsn(int instr, int opcode) throws SymbolicException {
		if (!getTrackingMode()) {
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
		if (!getTrackingMode()) {
			return;
		}
		log.trace(">>> no exception");
		for (Expression e : noExceptionExpression) {
			pushConjunct(e);
		}
		noExceptionExpression.clear();
		checkLimitConjuncts();
		String p = (path == null) ? null : path.getPathCondition().toString();
		log.trace(">>> path is now: {}", p);
		dumpFrames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#startCatch(int)
	 */
	@Override
	public void startCatch(int instr) throws SymbolicException {
		if (!getTrackingMode()) {
			return;
		}
		log.trace(">>> exception occurred");
		int catchDepth = Thread.currentThread().getStackTrace().length;
		int deltaDepth = exceptionDepth - catchDepth;
		if (deltaDepth >= frames.size()) {
			log.trace(">>> symbolic record mode switched off");
			setRecordingMode(false);
			mayRecord = false;
			setTrackingMode(trackAll);
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
		String p = (path == null) ? null : path.getPathCondition().toString();
		log.trace(">>> path is now: {}", p);
		dumpFrames();
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
		if (argType == boolean.class) {
			repr.append((Boolean) value);
		} else if (argType == byte.class) {
			repr.append((Integer) value);
		} else if (argType == short.class) {
			repr.append((Integer) value);
		} else if (argType == char.class) {
			repr.append('\'').append(StringEscapeUtils.escapeJava("" + (Character) value)).append('\'');
		} else if (argType == int.class) {
			repr.append((Integer) value);
		} else if (argType == long.class) {
			repr.append((Long) value);
		} else if (argType == float.class) {
			repr.append((Float) value);
		} else if (argType == double.class) {
			repr.append((Double) value);
		} else if (argType == String.class) {
			repr.append('"').append(StringEscapeUtils.escapeJava((String) value)).append('"');
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
		pop();
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
		pop();
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
		pop();
		broker.publishThread("mark", marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#printPC(java.lang.String)
	 */
	@Override
	public void printPC(String label) {
		if (!getTrackingMode()) {
			return;
		}
		final String pc = path.getPathCondition().toString();
		broker.publish("print-pc", new Tuple(this, label, pc));
		log.trace("{}: {}", label, pc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.State#printPC()
	 */
	@Override
	public void printPC() {
		if (!getTrackingMode()) {
			return;
		}
		final String pc = path.getPathCondition().toString();
		broker.publish("print-pc", new Tuple(this, null, pc));
		log.trace("{}: {}", null, pc);
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
		InstrumentationClassManager.loadClasses(classLoader, descriptor);
	}

}
