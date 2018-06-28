package za.ac.sun.cs.coastal.symbolic;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Configuration.Trigger;
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

	private static final Stack<Expression> args = new Stack<>();

	private static SegmentedPC spc = SegmentedPC.ZERO;

	private static Expression pendingExtraConjunct = null;

	private static final Set<String> conjunctSet = new HashSet<>();

	private static Map<String, Constant> concreteValues = null;
	
	public static void reset(Map<String, Constant> concreteValues) {
		symbolicMode = false;
		frames.clear();
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
		lgr.trace("{}", () -> opcode2str(opcode));
		switch (opcode) {
		case Opcodes.ICONST_1:
			push(new IntConstant(1));
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
		case Opcodes.IADD:
			push(new Operation(Operator.ADD, pop(), pop()));
			break;
		case Opcodes.IRETURN:
			Expression e0 = pop();
			if (methodReturn()) {
				push(e0);
			}
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", opcode2str(opcode), opcode);
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
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} {} (opcode: {})", opcode2str(opcode), operand, opcode);
			System.exit(1);
		}
	}

	/* Missing offset because destination not yet known. */
	public static void jumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("{}", opcode2str(opcode));
		switch (opcode) {
		case Opcodes.IF_ICMPLE:
			Expression e0 = pop();
			pushConjunct(new Operation(Operator.LE, pop(), e0));
			break;
		case Opcodes.IF_ICMPGE:
			e0 = pop();
			pushConjunct(new Operation(Operator.GE, pop(), e0));
			break;
		case Opcodes.IF_ICMPNE:
			e0 = pop();
			pushConjunct(new Operation(Operator.NE, pop(), e0));
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", opcode2str(opcode), opcode);
			System.exit(1);
		}
	}

	public static void postJumpInsn(int opcode) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("(POST) {}", opcode2str(opcode));
		lgr.trace(">>> previous conjunct is false");
		spc = spc.negate();
	}

	public static void varInsn(int opcode, int var) {
		if (!symbolicMode) {
			return;
		}
		lgr.trace("{} {}", opcode2str(opcode), var);
		switch (opcode) {
		case Opcodes.ILOAD:
			push(getLocal(var));
			break;
		case Opcodes.ISTORE:
			setLocal(var, pop());
			break;
		default:
			lgr.fatal("UNIMPLEMENTED INSTRUCTION: {} (opcode: {})", opcode2str(opcode), opcode);
			System.exit(1);
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

	private static String opcode2str(int opcode) {
		switch (opcode) {
		case 0:
			return "NOP";
		case 1:
			return "ACONST_NULL";
		case 2:
			return "ICONST_M1";
		case 3:
			return "ICONST_0";
		case 4:
			return "ICONST_1";
		case 5:
			return "ICONST_2";
		case 6:
			return "ICONST_3";
		case 7:
			return "ICONST_4";
		case 8:
			return "ICONST_5";
		case 9:
			return "LCONST_0";
		case 10:
			return "LCONST_1";
		case 11:
			return "FCONST_0";
		case 12:
			return "FCONST_1";
		case 13:
			return "FCONST_2";
		case 14:
			return "DCONST_0";
		case 15:
			return "DCONST_1";
		case 16:
			return "BIPUSH";
		case 17:
			return "SIPUSH";
		case 18:
			return "LDC";
		case 21:
			return "ILOAD";
		case 22:
			return "LLOAD";
		case 23:
			return "FLOAD";
		case 24:
			return "DLOAD";
		case 25:
			return "ALOAD";
		case 46:
			return "IALOAD";
		case 47:
			return "LALOAD";
		case 48:
			return "FALOAD";
		case 49:
			return "DALOAD";
		case 50:
			return "AALOAD";
		case 51:
			return "BALOAD";
		case 52:
			return "CALOAD";
		case 53:
			return "SALOAD";
		case 54:
			return "ISTORE";
		case 55:
			return "LSTORE";
		case 56:
			return "FSTORE";
		case 57:
			return "DSTORE";
		case 58:
			return "ASTORE";
		case 79:
			return "IASTORE";
		case 80:
			return "LASTORE";
		case 81:
			return "FASTORE";
		case 82:
			return "DASTORE";
		case 83:
			return "AASTORE";
		case 84:
			return "BASTORE";
		case 85:
			return "CASTORE";
		case 86:
			return "SASTORE";
		case 87:
			return "POP";
		case 88:
			return "POP2";
		case 89:
			return "DUP";
		case 90:
			return "DUP_X1";
		case 91:
			return "DUP_X2";
		case 92:
			return "DUP2";
		case 93:
			return "DUP2_X1";
		case 94:
			return "DUP2_X2";
		case 95:
			return "SWAP";
		case 96:
			return "IADD";
		case 97:
			return "LADD";
		case 98:
			return "FADD";
		case 99:
			return "DADD";
		case 100:
			return "ISUB";
		case 101:
			return "LSUB";
		case 102:
			return "FSUB";
		case 103:
			return "DSUB";
		case 104:
			return "IMUL";
		case 105:
			return "LMUL";
		case 106:
			return "FMUL";
		case 107:
			return "DMUL";
		case 108:
			return "IDIV";
		case 109:
			return "LDIV";
		case 110:
			return "FDIV";
		case 111:
			return "DDIV";
		case 112:
			return "IREM";
		case 113:
			return "LREM";
		case 114:
			return "FREM";
		case 115:
			return "DREM";
		case 116:
			return "INEG";
		case 117:
			return "LNEG";
		case 118:
			return "FNEG";
		case 119:
			return "DNEG";
		case 120:
			return "ISHL";
		case 121:
			return "LSHL";
		case 122:
			return "ISHR";
		case 123:
			return "LSHR";
		case 124:
			return "IUSHR";
		case 125:
			return "LUSHR";
		case 126:
			return "IAND";
		case 127:
			return "LAND";
		case 128:
			return "IOR";
		case 129:
			return "LOR";
		case 130:
			return "IXOR";
		case 131:
			return "LXOR";
		case 132:
			return "IINC";
		case 133:
			return "I2L";
		case 134:
			return "I2F";
		case 135:
			return "I2D";
		case 136:
			return "L2I";
		case 137:
			return "L2F";
		case 138:
			return "L2D";
		case 139:
			return "F2I";
		case 140:
			return "F2L";
		case 141:
			return "F2D";
		case 142:
			return "D2I";
		case 143:
			return "D2L";
		case 144:
			return "D2F";
		case 145:
			return "I2B";
		case 146:
			return "I2C";
		case 147:
			return "I2S";
		case 148:
			return "LCMP";
		case 149:
			return "FCMPL";
		case 150:
			return "FCMPG";
		case 151:
			return "DCMPL";
		case 152:
			return "DCMPG";
		case 153:
			return "IFEQ";
		case 154:
			return "IFNE";
		case 155:
			return "IFLT";
		case 156:
			return "IFGE";
		case 157:
			return "IFGT";
		case 158:
			return "IFLE";
		case 159:
			return "IF_ICMPEQ";
		case 160:
			return "IF_ICMPNE";
		case 161:
			return "IF_ICMPLT";
		case 162:
			return "IF_ICMPGE";
		case 163:
			return "IF_ICMPGT";
		case 164:
			return "IF_ICMPLE";
		case 165:
			return "IF_ACMPEQ";
		case 166:
			return "IF_ACMPNE";
		case 167:
			return "GOTO";
		case 168:
			return "JSR";
		case 169:
			return "RET";
		case 170:
			return "TABLESWITCH";
		case 171:
			return "LOOKUPSWITCH";
		case 172:
			return "IRETURN";
		case 173:
			return "LRETURN";
		case 174:
			return "FRETURN";
		case 175:
			return "DRETURN";
		case 176:
			return "ARETURN";
		case 177:
			return "RETURN";
		case 178:
			return "GETSTATIC";
		case 179:
			return "PUTSTATIC";
		case 180:
			return "GETFIELD";
		case 181:
			return "PUTFIELD";
		case 182:
			return "INVOKEVIRTUAL";
		case 183:
			return "INVOKESPECIAL";
		case 184:
			return "INVOKESTATIC";
		case 185:
			return "INVOKEINTERFACE";
		case 186:
			return "INVOKEDYNAMIC";
		case 187:
			return "NEW";
		case 188:
			return "NEWARRAY";
		case 189:
			return "ANEWARRAY";
		case 190:
			return "ARRAYLENGTH";
		case 191:
			return "ATHROW";
		case 192:
			return "CHECKCAST";
		case 193:
			return "INSTANCEOF";
		case 194:
			return "MONITORENTER";
		case 195:
			return "MONITOREXIT";
		case 197:
			return "MULTIANEWARRAY";
		case 198:
			return "IFNULL";
		case 199:
			return "IFNONNULL";
		default:
			return "???";
		}
	}

}
