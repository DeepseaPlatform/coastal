package za.ac.sun.cs.coastal.symbolic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Conversion;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

public class SymbolicState {

	private static final String FIELD_SEPARATOR = "/";

	private static final String INDEX_SEPARATOR = "_D_"; // "$"

	public static final String CHAR_SEPARATOR = "_H_"; // "#"

	public static final String NEW_VAR_PREFIX = "U_D_"; // "$"

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

	private Expression noExceptionExpression = null;

	private int exceptionDepth = 0;

	private Expression throwable = null;

	private boolean isPreviousConstant = false;

	private boolean isPreviousDuplicate = false;

	private final Set<String> conjunctSet = new HashSet<>();

	private final Map<String, Constant> concreteValues;

	private boolean mayContinue = true;

	private boolean justExecutedDelegate = false;

	private int lastInvokingInstruction = 0;

	private final Stack<Expression> pendingSwitch = new Stack<>();

	public SymbolicState(COASTAL coastal, Map<String, Constant> concreteValues) throws InterruptedException {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		limitConjuncts = Conversion.limitLong(coastal.getConfig(), "coastal.limit.conjuncts");
		traceAll = coastal.getConfig().getBoolean("coastal.trace-all", false);
		this.concreteValues = concreteValues;
		symbolicMode = traceAll;
	}

	public boolean getSymbolicMode() {
		return symbolicMode;
	}

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

	public void push(Expression expr) {
		frames.peek().push(expr);
	}

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
			int min = coastal.getDefaultMinValue(int.class);
			int max = coastal.getDefaultMaxValue(int.class);
			value = new IntVariable(getNewVariableName(), min, max);
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
		putField(arrayId, "type", new IntConstant(type));
	}
	
	private void setArrayLength(int arrayId, int length) {
		putField(arrayId, "length", new IntConstant(length));
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

	public Expression getStringLength(int stringId) {
		return getField(stringId, "length");
	}

	private void setStringLength(int stringId, Expression length) {
		putField(stringId, "length", length);
	}

	public Expression getStringChar(int stringId, int index) {
		return getField(stringId, "" + index);
	}

	private void setStringChar(int stringId, int index, Expression value) {
		putField(stringId, "" + index, value);
	}

	private void pushConjunct(Expression conjunct, boolean truthValue) {
		String c = conjunct.toString();
		/*
		 * Set "isPreviousConstant" and "isPreviousDuplicate" so that if we
		 * encounter the corresponding else, we know to ignore it.
		 */
		isPreviousConstant = isConstant(conjunct);
		isPreviousDuplicate = false;
		if (isPreviousConstant) {
			log.trace(">>> constant conjunct ignored: {}", c);
		} else if (conjunctSet.add(c)) {
			spc = new SegmentedPCIf(spc, conjunct, pendingExtraConjunct, truthValue);
			pendingExtraConjunct = null;
			log.trace(">>> adding conjunct: {}", c);
			log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		} else {
			log.trace(">>> duplicate conjunct ignored: {}", c);
			isPreviousDuplicate = true;
		}
	}

	private void pushConjunct(Expression conjunct) {
		pushConjunct(conjunct, true);
	}

	private void pushConjunct(Expression expression, int min, int max, int cur) {
		Operation conjunct;
		if (cur < min) {
			Operation lo = new Operation(Operator.LT, expression, new IntConstant(min));
			Operation hi = new Operation(Operator.GT, expression, new IntConstant(max));
			conjunct = new Operation(Operator.OR, lo, hi);
		} else {
			conjunct = new Operation(Operator.EQ, expression, new IntConstant(cur));
		}
		String c = conjunct.toString();
		if (isConstant(conjunct)) {
			log.trace(">>> constant (switch) conjunct ignored: {}", c);
		} else if (conjunctSet.add(c)) {
			spc = new SegmentedPCSwitch(spc, expression, pendingExtraConjunct, min, max, cur);
			pendingExtraConjunct = null;
			log.trace(">>> adding (switch) conjunct: {}", c);
			log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		} else {
			log.trace(">>> duplicate (switch) conjunct ignored: {}", c);
		}
	}

	public void pushExtraConjunct(Expression extraConjunct) {
		if (!isConstant(extraConjunct)) {
			if (pendingExtraConjunct == null) {
				pendingExtraConjunct = extraConjunct;
			} else {
				pendingExtraConjunct = new Operation(Operator.AND, extraConjunct, pendingExtraConjunct);
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

	public String getNewVariableName() {
		return NEW_VAR_PREFIX + newVariableCount++;
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

	public void stop() {
		if (!symbolicMode) {
			return;
		}
		broker.publish("stop", new Tuple(this, null));
	}

	public void stop(String message) {
		if (!symbolicMode) {
			return;
		}
		broker.publish("stop", new Tuple(this, message));
	}

	public void mark(int marker) {
		if (!symbolicMode) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	public void mark(String marker) {
		if (!symbolicMode) {
			return;
		}
		broker.publishThread("mark", marker);
	}

	// ======================================================================
	//
	// SEMI-INSTRUCTIONS
	//
	// ======================================================================

	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		int min = coastal.getMinBound(name, type);
		int max = coastal.getMaxBound(name, type);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : concrete.getValue();
	}

	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		int min = coastal.getMinBound(name, type);
		int max = coastal.getMaxBound(name, type);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : (char) concrete.getValue();
	}

	public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		Class<?> type = trigger.getParamType(index);
		int min = coastal.getMinBound(name, type);
		int max = coastal.getMaxBound(name, type);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : (byte) concrete.getValue();
	}

	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length();
		int stringId = createString();
		setStringLength(stringId, new IntConstant(length));
		if (name == null) { // not symbolic
			for (int i = 0; i < length; i++) {
				IntConstant chValue = new IntConstant(currentValue.charAt(i));
				setStringChar(stringId, i, chValue);
			}
			setLocal(address, new IntConstant(stringId));
			return currentValue;
		} else {
			char[] chars = new char[length];
			currentValue.getChars(0, length, chars, 0); // copy string into chars[]
			for (int i = 0; i < length; i++) {
				String entryName = name + CHAR_SEPARATOR + i;
				Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				Expression entryExpr = new IntVariable(entryName, 0, 255);
				if ((concrete != null) && (concrete instanceof IntConstant)) {
					chars[i] = (char) ((IntConstant) concrete).getValue();
				}
				setArrayValue(stringId, i, entryExpr);
			}
			setLocal(address, new IntConstant(stringId));
			return new String(chars);
		}
	}

	public int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length;
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		int[] value;
		if (name == null) { // not symbolic
			value = currentValue;
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new IntConstant(value[i]));
			}
		} else {
			value = new int[length];
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof IntConstant)) {
					value[i] = ((IntConstant) concrete).getValue();
				} else {
					value[i] = currentValue[i];
				}
				Class<?> type = trigger.getParamType(index);
				int min = coastal.getMinBound(entryName, name, type);
				int max = coastal.getMaxBound(entryName, name, type);
				Expression entryExpr = new IntVariable(entryName, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
		}
		setLocal(index, new IntConstant(arrayId));
		return value;
	}

	public char[] getConcreteCharArray(int triggerIndex, int index, int address, char[] currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length;
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		char[] value;
		if (name == null) { // not symbolic
			value = currentValue;
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new IntConstant(value[i]));
			}
		} else {
			value = new char[length];
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof IntConstant)) {
					value[i] = (char) ((IntConstant) concrete).getValue();
				} else {
					value[i] = currentValue[i];
				}
				Class<?> type = trigger.getParamType(index);
				int min = coastal.getMinBound(entryName, name, type);
				int max = coastal.getMaxBound(entryName, name, type);
				Expression entryExpr = new IntVariable(entryName, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
		}
		setLocal(index, new IntConstant(arrayId));
		return value;
	}

	public byte[] getConcreteByteArray(int triggerIndex, int index, int address, byte[] currentValue) {
		Trigger trigger = coastal.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length;
		int arrayId = createArray();
		setArrayLength(arrayId, length);
		byte[] value;
		if (name == null) { // not symbolic
			value = currentValue;
			for (int i = 0; i < length; i++) {
				setArrayValue(arrayId, i, new IntConstant(value[i]));
			}
		} else {
			value = new byte[length];
			for (int i = 0; i < length; i++) {
				String entryName = name + INDEX_SEPARATOR + i;
				Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof IntConstant)) {
					value[i] = (byte) ((IntConstant) concrete).getValue();
				} else {
					value[i] = currentValue[i];
				}
				Class<?> type = trigger.getParamType(index);
				int min = coastal.getMinBound(entryName, name, type);
				int max = coastal.getMaxBound(entryName, name, type);
				Expression entryExpr = new IntVariable(entryName, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
		}
		setLocal(index, new IntConstant(arrayId));
		return value;
	}

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

	public void startMethod(int methodNumber, int argCount) {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> transferring arguments");
		if (frames.isEmpty()) {
			frames.push(new SymbolicFrame(methodNumber, lastInvokingInstruction));
			for (int i = 0; i < argCount; i++) {
				setLocal(1, Operation.ZERO);
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

	private void checkLimitConjuncts() throws LimitConjunctException {
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

	public void linenumber(int instr, int line) {
		if (!symbolicMode) {
			return;
		}
		log.trace("### LINENUMBER {}", line);
		broker.publishThread("linenumber", new Tuple(instr, line));
	}

	public void insn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ACONST_NULL:
			push(Operation.ZERO);
			break;
		case Opcodes.ICONST_M1:
			push(new IntConstant(-1));
			break;
		case Opcodes.ICONST_0:
			push(Operation.ZERO);
			break;
		case Opcodes.ICONST_1:
			push(Operation.ONE);
			break;
		case Opcodes.ICONST_2:
			push(new IntConstant(2));
			break;
		case Opcodes.ICONST_3:
			push(new IntConstant(3));
			break;
		case Opcodes.ICONST_4:
			push(new IntConstant(4));
			break;
		case Opcodes.ICONST_5:
			push(new IntConstant(5));
			break;
		case Opcodes.IALOAD:
			int i = ((IntConstant) pop()).getValue();
			int a = ((IntConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.BALOAD:
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.CALOAD:
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			push(getArrayValue(a, i));
			break;
		case Opcodes.IASTORE:
			Expression e = pop();
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.BASTORE:
			e = pop();
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.CASTORE:
			e = pop();
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			setArrayValue(a, i, e);
			break;
		case Opcodes.POP:
			pop();
			break;
		case Opcodes.DUP:
			push(peek());
			break;
		case Opcodes.IADD:
			e = pop();
			if (e instanceof IntConstant) {
				Expression f = pop();
				if (f instanceof IntConstant) {
					push(new IntConstant(((IntConstant) f).getValue() + ((IntConstant) e).getValue()));
				} else {
					push(new Operation(Operator.ADD, f, e));
				}
			} else {
				push(new Operation(Operator.ADD, pop(), e));
			}
			break;
		case Opcodes.IMUL:
			e = pop();
			if (e instanceof IntConstant) {
				Expression f = pop();
				if (f instanceof IntConstant) {
					push(new IntConstant(((IntConstant) f).getValue() * ((IntConstant) e).getValue()));
				} else {
					push(new Operation(Operator.MUL, f, e));
				}
			} else {
				push(new Operation(Operator.MUL, pop(), e));
			}
			break;
		case Opcodes.IDIV:
			e = pop();
			if (e instanceof IntConstant) {
				Expression f = pop();
				if (f instanceof IntConstant) {
					push(new IntConstant(((IntConstant) f).getValue() / ((IntConstant) e).getValue()));
				} else {
					push(new Operation(Operator.DIV, f, e));
				}
			} else {
				push(new Operation(Operator.DIV, pop(), e));
			}
			assert noExceptionExpression == null;
			noExceptionExpression = new Operation(Operator.NE, e, Operation.ZERO);
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = Operation.ZERO;
			break;
		case Opcodes.ISUB:
			e = pop();
			if (e instanceof IntConstant) {
				Expression f = pop();
				if (f instanceof IntConstant) {
					push(new IntConstant(((IntConstant) f).getValue() - ((IntConstant) e).getValue()));
				} else {
					push(new Operation(Operator.SUB, f, e));
				}
			} else {
				push(new Operation(Operator.SUB, pop(), e));
			}
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
			int id = ((IntConstant) pop()).getValue();
			push(getField(id, "length"));
			break;
		case Opcodes.ATHROW:
			assert noExceptionExpression == null;
			noExceptionExpression = new Operation(Operator.NE, Operation.ZERO, Operation.ZERO);
			exceptionDepth = Thread.currentThread().getStackTrace().length;
			throwable = pop();
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void intInsn(int instr, int opcode, int operand) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), operand);
		broker.publishThread("int-insn", new Tuple(instr, opcode, operand));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.BIPUSH:
			push(new IntConstant(operand));
			break;
		case Opcodes.SIPUSH:
			push(new IntConstant(operand));
			break;
		case Opcodes.NEWARRAY:
			assert (operand == Opcodes.T_INT) || (operand == Opcodes.T_CHAR) || (operand == Opcodes.T_BYTE);
			Expression e = pop();
			int n = ((IntConstant) e).getValue();
			int id = createArray();
			setArrayType(id, operand);
			setArrayLength(id, n);
			for (int i = 0; i < n; i++) {
				setArrayValue(id, i, Operation.ZERO);
			}
			push(new IntConstant(id));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} (opcode: {})", instr, Bytecodes.toString(opcode), operand,
					opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void varInsn(int instr, int opcode, int var) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), var);
		broker.publishThread("var-insn", new Tuple(instr, opcode, var));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.ALOAD:
			push(getLocal(var));
			break;
		case Opcodes.ILOAD:
			push(getLocal(var));
			break;
		case Opcodes.ASTORE:
			setLocal(var, pop());
			break;
		case Opcodes.ISTORE:
			setLocal(var, pop());
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void typeInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("type-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.NEW:
			int id = incrAndGetNewObjectId();
			push(new IntConstant(id));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
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
			int id = ((IntConstant) pop()).getValue();
			push(getField(id, name));
			break;
		case Opcodes.PUTFIELD:
			e = pop();
			id = ((IntConstant) pop()).getValue();
			putField(id, name, e);
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} {} {} {} (opcode: {})", instr, Bytecodes.toString(opcode),
					owner, name, descriptor, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
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
					char typeCh = getReturnType(descriptor).charAt(0);
					if ((typeCh == 'I') || (typeCh == 'Z')) {
						push(new IntVariable(getNewVariableName(), -1000, 1000));
					} else if ((typeCh != 'V') && (typeCh != '?')) {
						push(Operation.ZERO);
					}
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
					char typeCh = getReturnType(descriptor).charAt(0);
					if ((typeCh == 'I') || (typeCh == 'Z')) {
						push(new IntVariable(getNewVariableName(), -1000, 1000));
					} else if ((typeCh != 'V') && (typeCh != '?')) {
						push(Operation.ZERO);
					}
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

	public void returnValue(boolean returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = returnValue ? Operation.ONE : Operation.ZERO;
			pushExtraConjunct(new Operation(Operator.EQ, peek(), value));
		}
	}

	public void returnValue(char returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntConstant(returnValue);
			pushExtraConjunct(new Operation(Operator.EQ, peek(), value));
		}
	}

	public void returnValue(double returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE double");
		System.exit(1);
	}

	public void returnValue(float returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE float");
		System.exit(1);
	}

	public void returnValue(int returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntConstant(returnValue);
			pushExtraConjunct(new Operation(Operator.EQ, peek(), value));
		}
	}

	public void returnValue(long returnValue) {
		log.fatal("UNIMPLEMENTED RETURN VALUE OF TYPE long");
		System.exit(1);
	}

	public void returnValue(short returnValue) {
		if (justExecutedDelegate) {
			justExecutedDelegate = false;
		} else {
			Expression value = new IntConstant(returnValue);
			pushExtraConjunct(new Operation(Operator.EQ, peek(), value));
		}
	}

	public void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException {
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

	/* Missing offset because destination not yet known. */
	public void jumpInsn(int instr, int opcode) throws LimitConjunctException {
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
				pushConjunct(new Operation(Operator.EQ, e, Operation.ZERO));
				break;
			case Opcodes.IFNE:
				e = pop();
				pushConjunct(new Operation(Operator.NE, e, Operation.ZERO));
				break;
			case Opcodes.IFLT:
				e = pop();
				pushConjunct(new Operation(Operator.LT, e, Operation.ZERO));
				break;
			case Opcodes.IFGE:
				e = pop();
				pushConjunct(new Operation(Operator.GE, e, Operation.ZERO));
				break;
			case Opcodes.IFGT:
				e = pop();
				pushConjunct(new Operation(Operator.GT, e, Operation.ZERO));
				break;
			case Opcodes.IFLE:
				e = pop();
				pushConjunct(new Operation(Operator.LE, e, Operation.ZERO));
				break;
			case Opcodes.IF_ICMPEQ:
				e = pop();
				pushConjunct(new Operation(Operator.EQ, pop(), e));
				break;
			case Opcodes.IF_ICMPNE:
				e = pop();
				pushConjunct(new Operation(Operator.NE, pop(), e));
				break;
			case Opcodes.IF_ICMPLT:
				e = pop();
				pushConjunct(new Operation(Operator.LT, pop(), e));
				break;
			case Opcodes.IF_ICMPGE:
				e = pop();
				pushConjunct(new Operation(Operator.GE, pop(), e));
				break;
			case Opcodes.IF_ICMPGT:
				e = pop();
				pushConjunct(new Operation(Operator.GT, pop(), e));
				break;
			case Opcodes.IF_ICMPLE:
				e = pop();
				pushConjunct(new Operation(Operator.LE, pop(), e));
				break;
			case Opcodes.IF_ACMPEQ:
				e = pop();
				pushConjunct(new Operation(Operator.EQ, pop(), e));
				break;
			case Opcodes.IF_ACMPNE:
				e = pop();
				pushConjunct(new Operation(Operator.NE, pop(), e));
				break;
			case Opcodes.IFNULL:
				e = pop();
				pushConjunct(new Operation(Operator.EQ, e, Operation.ZERO));
				break;
			case Opcodes.IFNONNULL:
				e = pop();
				pushConjunct(new Operation(Operator.NE, e, Operation.ZERO));
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
			case Opcodes.IF_ICMPEQ:
			case Opcodes.IF_ICMPNE:
			case Opcodes.IF_ICMPLT:
			case Opcodes.IF_ICMPGE:
			case Opcodes.IF_ICMPGT:
			case Opcodes.IF_ICMPLE:
			case Opcodes.IF_ACMPEQ:
			case Opcodes.IF_ACMPNE:
			case Opcodes.IFNULL:
			case Opcodes.IFNONNULL:
				pop();
				break;
			default:
				log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
				System.exit(1);
			}
		}
		dumpFrames();
	}

	public void postJumpInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		if (recordMode) {
			log.trace("(POST) {}", Bytecodes.toString(opcode));
			if (!isPreviousConstant && !isPreviousDuplicate) {
				log.trace(">>> previous conjunct is false");
				broker.publishThread("post-jump-insn", new Tuple(instr, opcode));
				assert spc instanceof SegmentedPCIf;
				spc = ((SegmentedPCIf) spc).negate();
				checkLimitConjuncts();
				log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
			}
		}
	}

	public void ldcInsn(int instr, int opcode, Object value) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), value);
		broker.publishThread("ldc-insn", new Tuple(instr, opcode, value));
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.LDC:
			if (value instanceof Integer) {
				push(new IntConstant((int) value));
			} else if (value instanceof String) {
				String s = (String) value;
				int id = createArray();
				putField(id, "length", new IntConstant(s.length()));
				for (int i = 0; i < s.length(); i++) {
					setArrayValue(id, i, new IntConstant(s.charAt(i)));
				}
				push(new IntConstant(id));
			} else {
				push(Operation.ZERO);
			}
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: <{}> {} (opcode: {})", instr, Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void iincInsn(int instr, int var, int increment) throws LimitConjunctException {
		final int opcode = 132;
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {} {}", instr, Bytecodes.toString(opcode), increment);
		broker.publishThread("iinc-insn", new Tuple(instr, var, increment));
		checkLimitConjuncts();
		Expression e0 = getLocal(var);
		Expression e1 = new IntConstant(increment);
		setLocal(var, Operation.apply(Operator.ADD, e0, e1));
		dumpFrames();
	}

	public void tableSwitchInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("<{}> {}", instr, Bytecodes.toString(opcode));
		broker.publishThread("table-switch-insn", new Tuple(instr, opcode));
		checkLimitConjuncts();
		pendingSwitch.push(pop());
		dumpFrames();
	}

	public void tableCaseInsn(int min, int max, int value) throws LimitConjunctException {
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

	public void lookupSwitchInsn(int instr, int opcode) throws LimitConjunctException {
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

	public void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException {
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

	private static boolean isConstant(Expression conjunct) {
		if (conjunct instanceof Operation) {
			Operation operation = (Operation) conjunct;
			int n = operation.getOperatandCount();
			for (int i = 0; i < n; i++) {
				if (!isConstant(operation.getOperand(i))) {
					return false;
				}
			}
			return true;
		} else {
			return (conjunct instanceof Constant);
		}
	}

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

	public void noException() throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> no exception");
		assert noExceptionExpression != null;
		pushConjunct(noExceptionExpression);
		noExceptionExpression = null;
		checkLimitConjuncts();
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		dumpFrames();
	}

	public void startCatch(int instr) throws LimitConjunctException {
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
		assert noExceptionExpression != null;
		pushConjunct(noExceptionExpression, false);
		noExceptionExpression = null;
		checkLimitConjuncts();
		log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		dumpFrames();
	}

}
