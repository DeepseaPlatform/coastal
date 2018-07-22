package za.ac.sun.cs.coastal.symbolic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Configuration.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
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

	private final Configuration configuration;

	private final Logger log;

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

	private boolean isPreviousConstant = false;

	private boolean isPreviousDuplicate = false;

	private final Set<String> conjunctSet = new HashSet<>();

	private final Map<String, Constant> concreteValues;

	private boolean mayContinue = true;

	private final Stack<Expression> pendingSwitch = new Stack<>();

	private final List<InstructionListener> instructionListeners;

	private final List<MarkerListener> markerListeners;

	public SymbolicState(Configuration configuration, Map<String, Constant> concreteValues,
			List<InstructionListener> instructionListeners, List<MarkerListener> markerListeners) {
		this.configuration = configuration;
		this.log = configuration.getLog();
		long lc = configuration.getLimitConjuncts();
		this.limitConjuncts = (lc == 0) ? Long.MAX_VALUE : lc;
		this.traceAll = configuration.getTraceAll();
		this.concreteValues = concreteValues;
		this.instructionListeners = instructionListeners;
		this.markerListeners = markerListeners;
		symbolicMode = traceAll;
	}

	//	public boolean getSymbolicMode() {
	//		return symbolicMode;
	//	}

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
			int min = configuration.getDefaultMinIntValue();
			int max = configuration.getDefaultMaxIntValue();
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

	private void pushConjunct(Expression conjunct) {
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
			spc = new SegmentedPCIf(spc, conjunct, pendingExtraConjunct, true);
			pendingExtraConjunct = null;
			log.trace(">>> adding conjunct: {}", c);
			log.trace(">>> spc is now: {}", spc.getPathCondition().toString());
		} else {
			log.trace(">>> duplicate conjunct ignored: {}", c);
			isPreviousDuplicate = true;
		}
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
		notifyExitMethod(methodNumber);
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
			log.trace("--> st{} locals:{}", frames.get(i).stack, frames.get(i).locals);
		}
		log.trace("--> data:{}", instanceData);
	}

	private static final Class<?>[] EMPTY_PARAMETERS = new Class<?>[0];

	private static final Object[] EMPTY_ARGUMENTS = new Object[0];

	private boolean executeDelegate(String owner, String name, String descriptor) {
		Object delegate = configuration.findDelegate(owner);
		if (delegate == null) {
			return false;
		}
		String methodName = name + getAsciiSignature(descriptor);
		Method delegateMethod = null;
		try {
			delegateMethod = delegate.getClass().getDeclaredMethod(methodName, EMPTY_PARAMETERS);
		} catch (NoSuchMethodException | SecurityException e) {
			log.trace("@@@ no delegate: {}", methodName);
			return false;
		}
		assert delegateMethod != null;
		log.trace("@@@ found delegate: {}", methodName);
		try {
			if ((boolean) delegateMethod.invoke(delegate, EMPTY_ARGUMENTS)) {
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
		for (MarkerListener listener : markerListeners) {
			listener.stop(this);
		}
	}

	public void stop(String message) {
		for (MarkerListener listener : markerListeners) {
			listener.stop(this, message);
		}
	}

	public void mark(int marker) {
		for (MarkerListener listener : markerListeners) {
			listener.mark(this, marker);
		}
	}

	public void mark(String marker) {
		for (MarkerListener listener : markerListeners) {
			listener.mark(this, marker);
		}
	}

	// ======================================================================
	//
	// SEMI-INSTRUCTIONS
	//
	// ======================================================================

	public int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		Trigger trigger = configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		int min = configuration.getMinBound(name);
		int max = configuration.getMaxBound(name);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : concrete.getValue();
	}

	public char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		Trigger trigger = configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		int min = configuration.getMinBound(name);
		int max = configuration.getMaxBound(name);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : (char) concrete.getValue();
	}

	public String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		Trigger trigger = configuration.getTrigger(triggerIndex);
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
		Trigger trigger = configuration.getTrigger(triggerIndex);
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
				int min = configuration.getMinBound(entryName, name);
				int max = configuration.getMaxBound(entryName, name);
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
				frames.push(new SymbolicFrame(methodNumber));
				dumpFrames();
			}
		}
		notifyEnterMethod(methodNumber);
	}

	public void startMethod(int methodNumber, int argCount) {
		if (!symbolicMode) {
			return;
		}
		log.trace(">>> transferring arguments");
		if (frames.isEmpty()) {
			frames.push(new SymbolicFrame(methodNumber));
			for (int i = 0; i < argCount; i++) {
				setLocal(1, Operation.ZERO);
			}
		} else {
			assert args.isEmpty();
			for (int i = 0; i < argCount; i++) {
				args.push(pop());
			}
			frames.push(new SymbolicFrame(methodNumber));
			for (int i = 0; i < argCount; i++) {
				setLocal(i, args.pop());
			}
		}
		dumpFrames();
		notifyEnterMethod(methodNumber);
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

	public void linenumber(int instr, int line) {
		if (!symbolicMode) {
			return;
		}
		log.trace("### LINENUMBER {}", line);
		notifyLinenumber(instr, line);
	}

	public void insn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{}", () -> Bytecodes.toString(opcode));
		notifyInsn(instr, opcode);
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
		case Opcodes.IASTORE:
			Expression e = pop();
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
			push(new Operation(Operator.MUL, pop(), e));
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
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void intInsn(int instr, int opcode, int operand) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{} {}", Bytecodes.toString(opcode), operand);
		notifyIntInsn(instr, opcode, operand);
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.BIPUSH:
			push(new IntConstant(operand));
			break;
		case Opcodes.SIPUSH:
			push(new IntConstant(operand));
			break;
		case Opcodes.NEWARRAY:
			assert operand == Opcodes.T_INT;
			Expression e = pop();
			int n = ((IntConstant) e).getValue();
			int id = createArray();
			setArrayLength(id, n);
			for (int i = 0; i < n; i++) {
				setArrayValue(id, i, Operation.ZERO);
			}
			push(new IntConstant(id));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} {} (opcode: {})", Bytecodes.toString(opcode), operand, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void varInsn(int instr, int opcode, int var) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{} {}", Bytecodes.toString(opcode), var);
		notifyVarInsn(instr, opcode, var);
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
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void typeInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{}", Bytecodes.toString(opcode));
		notifyTypeInsn(instr, opcode);
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.NEW:
			int id = incrAndGetNewObjectId();
			push(new IntConstant(id));
			break;
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{} {} {} {}", Bytecodes.toString(opcode), owner, name, descriptor);
		notifyFieldInsn(instr, opcode, owner, name, descriptor);
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
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name,
					descriptor, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
			throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{} {} {} {}", Bytecodes.toString(opcode), owner, name, descriptor);
		notifyMethodInsn(instr, opcode, owner, name, descriptor);
		checkLimitConjuncts();
		switch (opcode) {
		case Opcodes.INVOKESPECIAL:
		case Opcodes.INVOKEVIRTUAL:
			String className = owner.replace('/', '.');
			if (!configuration.isTarget(className)) {
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
			if (!configuration.isTarget(className)) {
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
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name,
					descriptor, opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void invokeDynamicInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{}", Bytecodes.toString(opcode));
		notifyInvokeDynamicInsn(instr, opcode);
		checkLimitConjuncts();
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	/* Missing offset because destination not yet known. */
	public void jumpInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{}", Bytecodes.toString(opcode));
		notifyJumpInsn(instr, opcode);
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
				log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
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
				log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
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
				notifyPostJumpInsn(instr, opcode);
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
		log.trace("{} {}", Bytecodes.toString(opcode), value);
		notifyLdcInsn(instr, opcode, value);
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
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void iincInsn(int instr, int var, int increment) throws LimitConjunctException {
		final int opcode = 132;
		if (!symbolicMode) {
			return;
		}
		log.trace("{} {}", Bytecodes.toString(opcode), increment);
		notifyIincInsn(instr, var, increment);
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
		log.trace("{}", Bytecodes.toString(opcode));
		notifyTableSwitchInsn(instr, opcode);
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
		log.trace("{}", Bytecodes.toString(opcode));
		notifyLookupSwitchInsn(instr, opcode);
		checkLimitConjuncts();
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	public void multiANewArrayInsn(int instr, int opcode) throws LimitConjunctException {
		if (!symbolicMode) {
			return;
		}
		log.trace("{}", Bytecodes.toString(opcode));
		notifyMultiANewArrayInsn(instr, opcode);
		checkLimitConjuncts();
		switch (opcode) {
		default:
			log.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		dumpFrames();
	}

	// ======================================================================
	//
	// LISTENERS
	//
	// ======================================================================

	private void notifyEnterMethod(int methodNumber) {
		for (InstructionListener listener : instructionListeners) {
			listener.enterMethod(methodNumber);
		}
	}

	private void notifyExitMethod(int methodNumber) {
		for (InstructionListener listener : instructionListeners) {
			listener.exitMethod(methodNumber);
		}
	}

	private void notifyLinenumber(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.linenumber(instr, opcode);
		}
	}

	private void notifyInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.insn(instr, opcode);
		}
	}

	private void notifyIntInsn(int instr, int opcode, int operand) {
		for (InstructionListener listener : instructionListeners) {
			listener.intInsn(instr, opcode, operand);
		}
	}

	private void notifyVarInsn(int instr, int opcode, int var) {
		for (InstructionListener listener : instructionListeners) {
			listener.varInsn(instr, opcode, var);
		}
	}

	private void notifyTypeInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.typeInsn(instr, opcode);
		}
	}

	private void notifyFieldInsn(int instr, int opcode, String owner, String name, String descriptor) {
		for (InstructionListener listener : instructionListeners) {
			listener.fieldInsn(instr, opcode, owner, name, descriptor);
		}
	}

	private void notifyMethodInsn(int instr, int opcode, String owner, String name, String descriptor) {
		for (InstructionListener listener : instructionListeners) {
			listener.methodInsn(instr, opcode, owner, name, descriptor);
		}
	}

	private void notifyInvokeDynamicInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.invokeDynamicInsn(instr, opcode);
		}
	}

	private void notifyJumpInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.jumpInsn(instr, opcode);
		}
	}

	private void notifyPostJumpInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.postJumpInsn(instr, opcode);
		}
	}

	private void notifyLdcInsn(int instr, int opcode, Object value) {
		for (InstructionListener listener : instructionListeners) {
			listener.ldcInsn(instr, opcode, value);
		}
	}

	private void notifyIincInsn(int instr, int var, int increment) {
		for (InstructionListener listener : instructionListeners) {
			listener.iincInsn(instr, var, increment);
		}
	}

	private void notifyTableSwitchInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.tableSwitchInsn(instr, opcode);
		}
	}

	private void notifyLookupSwitchInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.lookupSwitchInsn(instr, opcode);
		}
	}

	private void notifyMultiANewArrayInsn(int instr, int opcode) {
		for (InstructionListener listener : instructionListeners) {
			listener.multiANewArrayInsn(instr, opcode);
		}
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

	private static String getReturnType(String descriptor) {
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

	private String getAsciiSignature(String descriptor) {
		return descriptor.replace('/', '_').replace("_", "_1").replace(";", "_2").replace("[", "_3").replace("(", "__")
				.replace(")", "__");
	}

}
