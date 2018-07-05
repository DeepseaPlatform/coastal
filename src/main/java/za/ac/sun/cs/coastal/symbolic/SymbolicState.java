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

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Configuration.Trigger;
import za.ac.sun.cs.coastal.instrument.Bytecodes;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;
import za.ac.sun.cs.green.expr.Operation;
import za.ac.sun.cs.green.expr.Operation.Operator;

public class SymbolicState {

	private static final String FIELD_SEPARATOR = "/";

	private static final String INDEX_SEPARATOR = "_D_"; // "$"
	
	private static final String CHAR_SEPARATOR = "_H_"; // "#"
	
	public static final String NEW_VAR_PREFIX = "U_D_"; // "$"
	
	private static final Logger lgr = Configuration.getLogger();

	private static final boolean dumpTrace = Configuration.getDumpTrace();

	private static final boolean dumpFrame = Configuration.getDumpFrame();

	private static boolean symbolicMode = false;

	private static final Stack<SymbolicFrame> frames = new Stack<>();

	private static int objectIdCount = 0;

	private static int newVariableCount = 0;

	private static final Map<String, Expression> instanceData = new HashMap<>();

	private static final Stack<Expression> args = new Stack<>();

	private static SegmentedPC spc = null;

	private static Expression pendingExtraConjunct = null;

	private static boolean isPreviousConjunctConstant = false;

	private static boolean isPreviousConjunctDuplicate = false;

	private static final Set<String> conjunctSet = new HashSet<>();

	private static Map<String, Constant> concreteValues = null;

	private static boolean mayContinue = true;

	public static void reset(Map<String, Constant> concreteValues) {
		symbolicMode = false;
		frames.clear();
		objectIdCount = 0;
		// newVariableCount must NOT be reset
		instanceData.clear();
		args.clear();
		spc = null;
		pendingExtraConjunct = null;
		conjunctSet.clear();
		SymbolicState.concreteValues = concreteValues;
	}

	public static boolean getSymbolicMode() {
		return symbolicMode;
	}

	public static boolean mayContinue() {
		return mayContinue;
	}

	public static SegmentedPC getSegmentedPathCondition() {
		return spc;
	}

	public static void push(Expression expr) {
		frames.peek().push(expr);
	}

	public static Expression pop() {
		return frames.peek().pop();
	}

	private static Expression peek() {
		return frames.peek().peek();
	}

	private static Expression getLocal(int index) {
		return frames.peek().getLocal(index);
	}

	private static void setLocal(int index, Expression value) {
		frames.peek().setLocal(index, value);
	}

	private static void putField(int objectId, String fieldName, Expression value) {
		String fullFieldName = objectId + FIELD_SEPARATOR + fieldName;
		instanceData.put(fullFieldName, value);
	}

	public static Expression getField(int objectId, String fieldName) {
		String fullFieldName = objectId + FIELD_SEPARATOR + fieldName;
		Expression value = instanceData.get(fullFieldName);
		if (value == null) {
			int min = Configuration.getDefaultMinIntValue();
			int max = Configuration.getDefaultMaxIntValue();
			value = new IntVariable(getNewVariableName(), min, max);
			instanceData.put(fullFieldName, value);
		}
		return value;
	}

	// Arrays are just objects
	public static int createArray() {
		return incrAndGetNewObjectId();
	}

	public static int getArrayLength(int arrayId) {
		return ((IntConstant) getField(arrayId, "length")).getValue();
	}
	
	public static void setArrayLength(int arrayId, int length) {
		putField(arrayId, "length", new IntConstant(length));
	}
	
	public static Expression getArrayValue(int arrayId, int index) {
		return getField(arrayId, "" + index);
	}

	private static void setArrayValue(int arrayId, int index, Expression value) {
		putField(arrayId, "" + index, value);
	}

	// Strings are just objects
	public static int createString() {
		return incrAndGetNewObjectId();
	}

	public static Expression getStringLength(int stringId) {
		return getField(stringId, "length");
	}

	public static void setStringLength(int stringId, Expression length) {
		putField(stringId, "length", length);
	}

	public static Expression getStringChar(int stringId, int index) {
		return getField(stringId, "" + index);
	}

	public static void setStringChar(int stringId, int index, Expression value) {
		putField(stringId, "" + index, value);
	}

	private static void pushConjunct(Expression conjunct) {
		String c = conjunct.toString();
		isPreviousConjunctConstant = isConstantConjunct(conjunct);
		isPreviousConjunctDuplicate = false;
		if (isPreviousConjunctConstant) {
			if (dumpTrace) {
				lgr.trace(">>> constant conjunct ignored: {}", c);
			}
		} else if (conjunctSet.add(c)) {
			spc = new SegmentedPC(spc, conjunct, pendingExtraConjunct, false);
			pendingExtraConjunct = null;
			if (dumpTrace) {
				lgr.trace(">>> adding conjunct: {}", c);
				lgr.trace(">>> spc is now: {}", spc.getPathCondition().toString());
			}
		} else {
			if (dumpTrace) {
				lgr.trace(">>> duplicate conjunct ignored: {}", c);
			}
			isPreviousConjunctDuplicate = true;
		}
	}

	public static void pushExtraConjunct(Expression extraConjunct) {
		if (!isConstantConjunct(extraConjunct)) {
			if (pendingExtraConjunct == null) {
				pendingExtraConjunct = extraConjunct;
			} else {
				pendingExtraConjunct = new Operation(Operator.AND, extraConjunct, pendingExtraConjunct);
			}
		}
	}

	private static boolean methodReturn() {
		assert symbolicMode;
		assert !frames.isEmpty();
		frames.pop();
		if (frames.isEmpty()) {
			lgr.trace(">>> symbolic mode switched off");
			symbolicMode = false;
		}
		return symbolicMode;
	}

	private static int incrAndGetNewObjectId() {
		return ++objectIdCount;
	}

	public static String getNewVariableName() {
		return NEW_VAR_PREFIX + newVariableCount++;
	}

	private static void dumpFrames() {
		int n = frames.size();
		for (int i = n - 1; i >= 0; i--) {
			lgr.trace("--> st{} locals:{}", frames.get(i).stack, frames.get(i).locals);
		}
		lgr.trace("--> data:{}", instanceData);
	}

	private static final Class<?>[] EMPTY_PARAMETERS = new Class<?>[0];

	private static final Object[] EMPTY_ARGUMENTS = new Object[0];

	private static boolean executeDelegate(String owner, String name, String descriptor) {
		Object delegate = Configuration.findDelegate(owner);
		if (delegate == null) {
			return false;
		}
		String methodName = name + getAsciiSignature(descriptor);
		Method delegateMethod = null;
		try {
			delegateMethod = delegate.getClass().getDeclaredMethod(methodName, EMPTY_PARAMETERS);
		} catch (NoSuchMethodException | SecurityException e) {
			if (dumpTrace) {
				lgr.trace("@@@ no delegate: {}", methodName);
			}
			return false;
		}
		assert delegateMethod != null;
		if (dumpTrace) {
			lgr.trace("@@@ found delegate: {}", methodName);
		}
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
	
	public static void stop() {
		if (Configuration.getObeyStops()) {
			mayContinue = false;
			lgr.info("!!! PROGRAM TERMINATION POINT REACHED");
		}
	}

	public static void stop(String message) {
		if (Configuration.getObeyStops()) {
			mayContinue = false;
			lgr.info("!!! PROGRAM TERMINATION POINT REACHED");
			lgr.info("!!! {}",  message);
		}
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		int min = Configuration.getMinBound(name);
		int max = Configuration.getMaxBound(name);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : concrete.getValue();
	}

	public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		if (name == null) { // not symbolic
			setLocal(address, new IntConstant(currentValue));
			return currentValue;
		}
		int min = Configuration.getMinBound(name);
		int max = Configuration.getMaxBound(name);
		setLocal(address, new IntVariable(name, min, max));
		IntConstant concrete = (IntConstant) (concreteValues == null ? null : concreteValues.get(name));
		return (concrete == null) ? currentValue : (char) concrete.getValue();
	}

	public static String getConcreteString(int triggerIndex, int index, int address, String currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
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

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
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
				int min = Configuration.getMinBound(entryName, name);
				int max = Configuration.getMaxBound(entryName, name);
				Expression entryExpr = new IntVariable(entryName, min, max);
				setArrayValue(arrayId, i, entryExpr);
			}
		}
		setLocal(index, new IntConstant(arrayId));
		return value;
	}

	public static void triggerMethod() {
		if (!symbolicMode) {
			if (dumpTrace) {
				lgr.trace(">>> symbolic mode switched on");
			}
			symbolicMode = true;
			frames.push(new SymbolicFrame());
			if (dumpFrame) {
				dumpFrames();
			}
		}
	}

	public static void startMethod(int argCount) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace(">>> transferring arguments");
		}
		assert args.isEmpty();
		for (int i = 0; i < argCount; i++) {
			args.push(pop());
		}
		frames.push(new SymbolicFrame());
		for (int i = 0; i < argCount; i++) {
			setLocal(i, args.pop());
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}
	
	public static void reportLinenumber(int line) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("### LINENUMBER {}", line);
		}
	}

	public static void insn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", () -> Bytecodes.toString(opcode));
		}
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void intInsn(int opcode, int operand) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {}", Bytecodes.toString(opcode), operand);
		}
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} (opcode: {})", Bytecodes.toString(opcode), operand, opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void varInsn(int opcode, int var) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {}", Bytecodes.toString(opcode), var);
		}
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void typeInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
		switch (opcode) {
		case Opcodes.NEW:
			int id = incrAndGetNewObjectId();
			push(new IntConstant(id));
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void fieldInsn(int opcode, String owner, String name, String descriptor) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {} {} {}", Bytecodes.toString(opcode), owner, name, descriptor);
		}
		switch (opcode) {
		case Opcodes.GETSTATIC:
			push(Operation.ZERO);
			break;
		case Opcodes.GETFIELD:
			int id = ((IntConstant) pop()).getValue();
			push(getField(id, name));
			break;
		case Opcodes.PUTFIELD:
			Expression e = pop();
			id = ((IntConstant) pop()).getValue();
			putField(id, name, e);
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name,
					descriptor, opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void methodInsn(int opcode, String owner, String name, String descriptor) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {} {} {}", Bytecodes.toString(opcode), owner, name, descriptor);
		}
		switch (opcode) {
		case Opcodes.INVOKESPECIAL:
		case Opcodes.INVOKEVIRTUAL:
			String className = owner.replace('/', '.');
			if (!Configuration.isTarget(className)) {
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
			if (!Configuration.isTarget(className)) {
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name,
					descriptor, opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void invokeDynamicInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	/* Missing offset because destination not yet known. */
	public static void jumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void postJumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("(POST) {}", Bytecodes.toString(opcode));
		}
		if (!isPreviousConjunctConstant && !isPreviousConjunctDuplicate) {
			if (dumpTrace) {
				lgr.trace(">>> previous conjunct is false");
			}
			spc = spc.negate();
			if (dumpTrace) {
				lgr.trace(">>> spc is now: {}", spc.getPathCondition().toString());
			}
		}
	}

	public static void ldcInsn(int opcode, Object value) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {}", Bytecodes.toString(opcode), value);
		}
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void iincInsn(int var, int increment) {
		final int opcode = 132;
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{} {}", Bytecodes.toString(opcode), increment);
		}
		Expression e0 = getLocal(var);
		Expression e1 = new IntConstant(increment);
		setLocal(var, Operation.apply(Operator.ADD, e0, e1));
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void tableSwitchInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void lookupSwitchInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	public static void multiANewArrayInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		if (dumpTrace) {
			lgr.trace("{}", Bytecodes.toString(opcode));
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
		if (dumpFrame) {
			dumpFrames();
		}
	}

	// ======================================================================
	//
	// UTILITIES
	//
	// ======================================================================

	private static boolean isConstantConjunct(Expression conjunct) {
		if (conjunct instanceof Operation) {
			Operation operation = (Operation) conjunct;
			int n = operation.getOperatandCount();
			for (int i = 0; i < n; i++) {
				if (!isConstantConjunct(operation.getOperand(i))) {
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
			} else if (ch == '[') {
				// we can just ignore this
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
			} else {
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

	public static String getAsciiSignature(String descriptor) {
		return descriptor.replace('/', '_').replace("_", "_1").replace(";", "_2").replace("[", "_3").replace("(", "__")
				.replace(")", "__");
	}

}
