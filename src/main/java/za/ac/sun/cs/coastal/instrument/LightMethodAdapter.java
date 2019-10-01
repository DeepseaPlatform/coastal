package za.ac.sun.cs.coastal.instrument;

import java.util.BitSet;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Trigger;

public class LightMethodAdapter extends MethodVisitor {

	private static final String SYMBOLIC = "za/ac/sun/cs/coastal/Symbolic";

	private static final String LIBRARY = "za/ac/sun/cs/coastal/symbolic/VM";

	private static final String SYSTEM = "java/lang/System";

	private final COASTAL coastal;

	private final Logger log;

	private final InstrumentationClassManager classManager;

	private final String filename;

	private final int triggerIndex;

	private final boolean isStatic;

	private final int argCount;

	private BitSet currentLinenumbers;

	public LightMethodAdapter(COASTAL coastal, MethodVisitor cv, String filename, int triggerIndex, boolean isStatic, int argCount) {
		super(Opcodes.ASM6, cv);
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.classManager = coastal.getClassManager();
		this.filename = filename;
		this.triggerIndex = triggerIndex;
		this.isStatic = isStatic;
		this.argCount = argCount;
	}

	private int visitParameter(Trigger trigger, int triggerIndex, int index, int address) {
		Class<?> type = trigger.getParamType(index);
		int size = ((type == long.class) || (type == double.class)) ? 2 : 1;
		if (type == boolean.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteBoolean", "(IIIZ)Z", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == byte.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteByte", "(IIIB)B", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == short.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteShort", "(IIIS)S", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == char.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteChar", "(IIIC)C", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == int.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteInt", "(IIII)I", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == long.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.LLOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteLong", "(IIIJ)J", false);
			mv.visitIntInsn(Opcodes.LSTORE, address);
		} else if (type == float.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.FLOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteFloat", "(IIIF)F", false);
			mv.visitIntInsn(Opcodes.FSTORE, address);
		} else if (type == double.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.DLOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteDouble", "(IIID)D", false);
			mv.visitIntInsn(Opcodes.DSTORE, address);
		} else if (type == String.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteString",
					"(IIILjava/lang/String;)Ljava/lang/String;", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == boolean[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteBooleanArray", "(III[Z)[Z", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == byte[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteByteArray", "(III[B)[B", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == short[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteShortArray", "(III[S)[S", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == char[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteCharArray", "(III[C)[C", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == int[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteIntArray", "(III[I)[I", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == long[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteLongArray", "(III[J)[J", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == float[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteFloatArray", "(III[F)[F", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == double[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteDoubleArray", "(III[D)[D", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == String[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteStringArray",
					"(III[Ljava/lang/String;)[Ljava/lang/String;", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else {
			log.fatal("UNHANDLED PARAMETER TYPE");
			System.exit(1);
		}
		return size;
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		log.trace("Linstrument visitLineNumber(line:{}, label:{})", line, start);
		mv.visitLdcInsn(classManager.getInstructionCounter());
		mv.visitLdcInsn(line);
		mv.visitLdcInsn(filename);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "linenumber", "(IILjava/lang/String;)V", false);
		mv.visitLineNumber(line, start);
		currentLinenumbers.set(line);
	}

//	@Override
//	public void visitLabel(Label label) {
//		log.trace("visitLabel(label:{})", label);
//		mv.visitLdcInsn(classManager.getInstructionCounter());
//		mv.visitLdcInsn(label.toString());
//		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "label", "(ILjava/lang/String;)V", false);
//		mv.visitLabel(label);
//	}

	@Override
	public void visitEnd() {
		log.trace("Linstrument visitEnd()");
		classManager.registerLastInstruction();
		classManager.registerLinenumbers(currentLinenumbers);
		mv.visitEnd();
	}

	@Override
	public void visitCode() {
		log.trace("Linstrument visitCode()");
		if (triggerIndex >= 0) {
			//--- IF (symbolicMode) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getRecordingMode", "()Z", false);
			Label label = new Label();
			mv.visitJumpInsn(Opcodes.IFNE, label);
			//---   triggerMethod()
			mv.visitLdcInsn(classManager.getNextMethodCounter());
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(isStatic);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "triggerMethod", "(IIZ)V", false);
			//---   GENERATE PARAMETER OVERRIDES
			Trigger trigger = coastal.getTrigger(triggerIndex);
			int n = trigger.getParamCount();
			int offset = (isStatic ? 0 : 1);
			int address = offset;
			for (int i = 0; i < n; i++) {
				address += visitParameter(trigger, triggerIndex, i, address);
			}
			Label end = new Label();
			mv.visitJumpInsn(Opcodes.GOTO, end);
			mv.visitLabel(label);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			//--- } else {
			//---   startMethod()
			mv.visitLdcInsn(classManager.getMethodCounter());
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(II)V", false);
			//--- }
			mv.visitLabel(end);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		} else {
			mv.visitLdcInsn(classManager.getNextMethodCounter());
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(II)V", false);
		}
		classManager.registerFirstInstruction();
		currentLinenumbers = new BitSet();
		mv.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		log.trace("Linstrument visitInsn(opcode:{})", opcode);
		switch (opcode) {
		case Opcodes.IRETURN:
		case Opcodes.ARETURN:
		case Opcodes.RETURN:
			mv.visitLdcInsn(classManager.getNextInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(II)V", false);
			break;
		default:
			break;
		}
		mv.visitInsn(opcode);
		//		if (opcode == Opcodes.IDIV) {
		//			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "noException", "()V", false);
		//		}
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		log.trace("Linstrument visitMethodInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		if (owner.equals(SYMBOLIC)) {
			mv.visitMethodInsn(opcode, LIBRARY, name, descriptor, isInterface);
			// pop params !!!!!!!!!!!
		} else if (owner.equals(SYSTEM) && name.equals("exit")) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "systemExit", "(I)V", false);
		} else {
			mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
		}
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		log.trace("Linstrument visitJumpInsn(opcode:{}, label:{})", opcode, label);
		switch (opcode) {
		case Opcodes.IFEQ:
		case Opcodes.IFNE:
		case Opcodes.IFLT:
		case Opcodes.IFLE:
		case Opcodes.IFGT:
		case Opcodes.IFGE:
			mv.visitInsn(Opcodes.DUP);
			mv.visitLdcInsn(classManager.getNextInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(III)V", false);
			break;
		case Opcodes.IFNULL:
		case Opcodes.IFNONNULL:
			mv.visitInsn(Opcodes.DUP);
			mv.visitLdcInsn(classManager.getNextInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(Ljava/lang/Object;II)V", false);
			break;
		case Opcodes.IF_ACMPEQ:
		case Opcodes.IF_ACMPNE:
		case Opcodes.IF_ICMPEQ:
		case Opcodes.IF_ICMPNE:
		case Opcodes.IF_ICMPLT:
		case Opcodes.IF_ICMPGE:
		case Opcodes.IF_ICMPGT:
		case Opcodes.IF_ICMPLE:
			mv.visitInsn(Opcodes.DUP2);
			mv.visitLdcInsn(classManager.getNextInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(IIII)V", false);
			break;
		default:
			mv.visitLdcInsn(classManager.getNextInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(II)V", false);
			break;
		}
		mv.visitJumpInsn(opcode, label);
	}

}
