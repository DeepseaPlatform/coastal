package za.ac.sun.cs.coastal.symbolic;

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

	protected static final Logger lgr = Configuration.getLogger();

	protected static boolean symbolicMode = false;

	protected static final Stack<SymbolicFrame> frames = new Stack<>();

	private static int objectIdCount = 0;

	private static int newVariableCount = 0;
	
	private static final Stack<Expression> args = new Stack<>();

	private static SegmentedPC spc = SegmentedPC.ZERO;

	private static Expression pendingExtraConjunct = null;

	private static final Set<String> conjunctSet = new HashSet<>();

	private static Map<String, Constant> concreteValues = null;
	
	public static void reset(Map<String, Constant> concreteValues) {
		symbolicMode = false;
		frames.clear();
		objectIdCount = 0;
		// newVariableCount must NOT be reset
		args.clear();
		spc = SegmentedPC.ZERO;
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

	private static void pushConjunct(Expression conjunct) {
		String c = conjunct.toString();
		if (isConstantConjunct(conjunct)) {
			lgr.trace(">>> constant conjunct ignored: {}", c);
		} else if (conjunctSet.add(c)) {
			spc = new SegmentedPC(spc, conjunct, pendingExtraConjunct, '1');
			pendingExtraConjunct = null;
			lgr.trace(">>> adding conjunct: {}", c);
		} else {
			lgr.trace(">>> duplicate conjunct ignored: {}", c);
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
		return "$" + newVariableCount++;
	}

	// ======================================================================
	//
	// INSTRUCTIONS
	//
	// ======================================================================

	public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue) {
		Trigger trigger = Configuration.getTrigger(triggerIndex);
		String name = trigger.getParamName(index);
		Constant concrete = ((name == null) || (concreteValues == null)) ? null : concreteValues.get(name);
		int value = (concrete == null) ? currentValue : ((IntConstant) concrete).getValue();
		if (name == null) {
			setLocal(address, new IntConstant(value));
		} else {
			setLocal(address, new IntVariable(name, 0, 99));
		}
		return value;
	}

	public static void triggerMethod() {
		if (!symbolicMode) {
			lgr.trace(">>> symbolic mode switched on");
			symbolicMode = true;
			frames.push(new SymbolicFrame());
		}
	}

	public static void startMethod(int argCount) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace(">>> transferring arguments");
		assert args.isEmpty();
		for (int i = 0; i < argCount; i++) {
			args.push(pop());
		}
		frames.push(new SymbolicFrame());
		for (int i = 0; i < argCount; i++) {
			setLocal(i, args.pop());
		}
	}

	public static void insn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("{}", () -> Bytecodes.toString(opcode));
		switch (opcode) {
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
		case Opcodes.DUP:
			push(peek());
			break;
		case Opcodes.IADD:
			Expression e0 = pop();
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
	}

	public static void intInsn(int opcode, int operand) {
		if (!symbolicMode) {
			return;
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
	}

	public static void methodInsn(int opcode, String owner, String name, String descriptor) {
		if (!symbolicMode) {
			return;
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
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name, descriptor, opcode);
			System.exit(1);
		}
	}
	
	public static void fieldInsn(int opcode, String owner, String name, String descriptor) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		case Opcodes.GETSTATIC:
			push(Operation.ZERO);
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} {} {} (opcode: {})", Bytecodes.toString(opcode), owner, name, descriptor, opcode);
			System.exit(1);
		}
	}
	
	public static void iincInsn(int opcode, int increment) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} (opcode: {})", Bytecodes.toString(opcode), increment, opcode);
			System.exit(1);
		}
	}
	
	public static void invokeDynamicInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}
	
	public static void ldcInsn(int opcode, Object value) {
		if (!symbolicMode) {
			return;
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
	}
	
	public static void lookupSwitchInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}
	
	public static void multiANewArrayInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}
	
	public static void tableSwitchInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		switch (opcode) {
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}
	
	public static void typeInsn(int opcode) {
		if (!symbolicMode) {
			return;
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
	}
	
	/* Missing offset because destination not yet known. */
	public static void jumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("{}", Bytecodes.toString(opcode));
		switch (opcode) {
		case Opcodes.GOTO:
			// do nothing
			break;
		case Opcodes.IF_ICMPGE:
			Expression e0 = pop();
			pushConjunct(new Operation(Operator.GE, pop(), e0));
			break;
		case Opcodes.IF_ICMPLE:
			e0 = pop();
			pushConjunct(new Operation(Operator.LE, pop(), e0));
			break;
		case Opcodes.IF_ICMPNE:
			e0 = pop();
			pushConjunct(new Operation(Operator.NE, pop(), e0));
			break;
		case Opcodes.IFGT:
			e0 = pop();
			pushConjunct(new Operation(Operator.GT, e0, Operation.ZERO));
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
		}
	}

	public static void postJumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("(POST) {}", Bytecodes.toString(opcode));
		lgr.trace(">>> previous conjunct is false");
		spc = spc.negate();
	}

	public static void varInsn(int opcode, int var) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("{} {}", Bytecodes.toString(opcode), var);
		switch (opcode) {
		case Opcodes.ILOAD:
			push(getLocal(var));
			break;
		case Opcodes.ISTORE:
			setLocal(var, pop());
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", Bytecodes.toString(opcode), opcode);
			System.exit(1);
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
