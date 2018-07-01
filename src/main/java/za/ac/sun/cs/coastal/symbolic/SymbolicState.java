package za.ac.sun.cs.coastal.symbolic;

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

	public static SegmentedPC getSegmentedPathCondition() {
		return spc;
	}

	private static void push(Expression expr) {
		frames.peek().push(expr);
	}

	private static Expression pop() {
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

	private static Expression getField(int objectId, String fieldName) {
		String fullFieldName = objectId + FIELD_SEPARATOR + fieldName;
		Expression value = instanceData.get(fullFieldName);
		if (value == null) {
			int min = Configuration.getDefaultMinBound();
			int max = Configuration.getDefaultMaxBound();
			value = new IntVariable(getNewVariableName(), min, max);
			instanceData.put(fullFieldName, value);
		}
		return value;
	}

	private static Expression getArrayValue(int arrayId, int index) {
		return getField(arrayId, "" + index);
	}

	private static void addArrayValue(int arrayId, int index, Expression value) {
		putField(arrayId, "" + index, value);
	}

	private static void pushConjunct(Expression conjunct) {
		String c = conjunct.toString();
		isPreviousConjunctConstant = isConstantConjunct(conjunct);
		isPreviousConjunctDuplicate = false;
		if (isPreviousConjunctConstant) {
			lgr.trace(">>> constant conjunct ignored: {}", c);
		} else if (conjunctSet.add(c)) {
			spc = new SegmentedPC(spc, conjunct, pendingExtraConjunct, false);
			pendingExtraConjunct = null;
			lgr.trace(">>> adding conjunct: {}", c);
			if (dumpTrace) {
				lgr.trace(">>> spc is now: {}", spc.getPathCondition().toString());
			}
		} else {
			lgr.trace(">>> duplicate conjunct ignored: {}", c);
			isPreviousConjunctDuplicate = true;
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

	private static String getNewVariableName() {
		return "$" + newVariableCount++;
	}

	private static void dumpFrames() {
		int n = frames.size();
		for (int i = n - 1; i >= 0; i--) {
			lgr.trace("--> st{} locals:{}", frames.get(i).stack, frames.get(i).locals);
		}
		lgr.trace("--> data:{}", instanceData);
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
		Constant concrete = (concreteValues == null) ? null : concreteValues.get(name);
		int value = (concrete == null) ? currentValue : ((IntConstant) concrete).getValue();
		int min = Configuration.getMinBound(name);
		int max = Configuration.getMaxBound(name);
		setLocal(address, new IntVariable(name, min, max));
		return value;
	}

	public static int[] getConcreteIntArray(int triggerIndex, int index, int address, int[] currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		int length = currentValue.length;
		int arrayId = incrAndGetNewObjectId();
		int[] value;
		if (name == null) { // not symbolic
			value = currentValue;
			for (int i = 0; i < length; i++) {
				putField(arrayId, "" + i, new IntConstant(value[i]));
			}
		} else {
			value = new int[length];
			for (int i = 0; i < length; i++) {
				String entryName = name + "$" + i;
				Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(entryName);
				if ((concrete != null) && (concrete instanceof IntConstant)) {
					value[i] = ((IntConstant) concrete).getValue();
				} else {
					value[i] = currentValue[i];
				}
				int min = Configuration.getMinBound(entryName, name);
				int max = Configuration.getMaxBound(entryName, name);
				Expression entryExpr = new IntVariable(entryName, min, max);
				putField(arrayId, "" + i, entryExpr);
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
			Expression e0 = pop();
			i = ((IntConstant) pop()).getValue();
			a = ((IntConstant) pop()).getValue();
			addArrayValue(a, i, e0);
			break;
		case Opcodes.POP:
			pop();
			break;
		case Opcodes.DUP:
			push(peek());
			break;
		case Opcodes.IADD:
			e0 = pop();
			push(new Operation(Operator.ADD, pop(), e0));
			break;
		case Opcodes.IMUL:
			e0 = pop();
			push(new Operation(Operator.MUL, pop(), e0));
			break;
		case Opcodes.ISUB:
			e0 = pop();
			push(new Operation(Operator.SUB, pop(), e0));
			break;
		case Opcodes.IRETURN:
			e0 = pop();
			if (methodReturn()) {
				push(e0);
			}
			break;
		case Opcodes.RETURN:
			methodReturn();
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
			if (!Configuration.isTarget(owner.replace('/', '.'))) {
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
			break;
		case Opcodes.INVOKESTATIC:
			if (!Configuration.isTarget(owner.replace('/', '.'))) {
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
			lgr.trace(">>> previous conjunct is false");
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
			// TODO lgr.info("$$$$$$$$$$$$$ " + value.getClass().getName() + "$$$$" + value.toString());
			push(Operation.ZERO);
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

}
