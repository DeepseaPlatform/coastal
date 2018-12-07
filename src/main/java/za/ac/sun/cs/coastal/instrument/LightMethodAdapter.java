package za.ac.sun.cs.coastal.instrument;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Trigger;

public class LightMethodAdapter extends MethodVisitor {

	private static final String LIBRARY = "za/ac/sun/cs/coastal/symbolic/VM";

	private final COASTAL coastal;

	private final Logger log;
	
	private final InstrumentationClassManager classManager;

	private final int triggerIndex;

	private final boolean isStatic;

	private final int argCount;

	private static final class Tuple {
		final int min, max, cur;

		Tuple(int min, int max, int cur) {
			this.min = min;
			this.max = max;
			this.cur = cur;
		}
	}

	private static Map<Label, Stack<Tuple>> caseLabels = new HashMap<>();

	private static Set<Label> catchLabels = new HashSet<>();
	
	public LightMethodAdapter(COASTAL coastal, MethodVisitor cv, int triggerIndex, boolean isStatic, int argCount) {
		super(Opcodes.ASM6, cv);
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.classManager = coastal.getClassManager();
		this.triggerIndex = triggerIndex;
		this.isStatic = isStatic;
		this.argCount = argCount;
	}

	private void visitParameter(Trigger trigger, int triggerIndex, int index, int address) {
		String name = trigger.getParamName(index);
		if (name == null) {
			return;
		}
		Class<?> type = trigger.getParamType(index);
		if (type == int.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteInt", "(IIII)I", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == char.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteChar", "(IIIC)C", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == byte.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ILOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteByte", "(IIIB)B", false);
			mv.visitIntInsn(Opcodes.ISTORE, address);
		} else if (type == int[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteIntArray", "(III[I)[I", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == char[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteCharArray", "(III[C)[C", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == byte[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteByteArray", "(III[B)[B", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else if (type == String.class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteString",
					"(IIILjava/lang/String;)Ljava/lang/String;", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else {
			log.fatal("UNHANDLED PARAMETER TYPE");
			System.exit(1);
		}
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		log.trace("visitLineNumber(line:{}, label:{})", line, start);
		mv.visitLdcInsn(classManager.getInstructionCounter());
		mv.visitLdcInsn(line);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "linenumber", "(II)V", false);
		mv.visitLineNumber(line, start);
	}

	@Override
	public void visitEnd() {
		log.trace("visitEnd()");
		classManager.registerLastInstruction();
		// branchInstructions.put(methodCounter, currentBranchInstructions);
		mv.visitEnd();
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		log.trace("visitTryCatchBlock(start:{}, end:{}, handler:{}, type:{})", start, end, handler, type);
		catchLabels.add(handler);
		mv.visitTryCatchBlock(start, end, handler, type);
	}
	
	@Override
	public void visitCode() {
		log.trace("visitCode()");
		if (triggerIndex >= 0) {
			//--- IF (symbolicMode) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getRecordMode", "()Z", false);
			Label label = new Label();
			mv.visitJumpInsn(Opcodes.IFNE, label);
			//---   triggerMethod()
			mv.visitLdcInsn(classManager.getNextMethodCounter());
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "triggerMethod", "(I)V", false);
			//---   GENERATE PARAMETER OVERRIDES
			Trigger trigger = coastal.getTrigger(triggerIndex);
			int n = trigger.getParamCount();
			int offset = (isStatic ? 0 : 1);
			for (int i = 0; i < n; i++) {
				visitParameter(trigger, triggerIndex, i, i + offset);
			}
			Label end = new Label();
			mv.visitJumpInsn(Opcodes.GOTO, end);
			mv.visitLabel(label);
			//--- } else {
			//---   startMethod()
			mv.visitLdcInsn(classManager.getMethodCounter());
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
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
		// currentBranchInstructions = new BitSet();
		mv.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		log.trace("visitInsn(opcode:{})", opcode);
		mv.visitLdcInsn(classManager.getNextInstructionCounter());
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(II)V", false);
		mv.visitInsn(opcode);
		if (opcode == Opcodes.IDIV) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "noException", "()V", false);
		}
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		// do nothing
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		// do nothing
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		// do nothing
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		// do nothing
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		// do nothing
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
			Object... bootstrapMethodArguments) {
		// do nothing
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		log.trace("visitJumpInsn(opcode:{}, label:{})", opcode, label);
		mv.visitLdcInsn(classManager.getNextInstructionCounter());
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(II)V", false);
		mv.visitJumpInsn(opcode, label);
		if (opcode != Opcodes.GOTO) {
			mv.visitLdcInsn(classManager.getInstructionCounter());
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "postJumpInsn", "(II)V", false);
			// currentBranchInstructions.set(instructionCounter);
		}
	}

	@Override
	public void visitLdcInsn(Object value) {
		// do nothing
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		// do nothing
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		log.trace("visitTableSwitchInsn(min:{}, max:{}, dflt:{})", min, max, dflt);
		mv.visitLdcInsn(classManager.getNextInstructionCounter());
		mv.visitLdcInsn(170);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "tableSwitchInsn", "(II)V", false);
		assert labels.length == (max - min + 1);
		for (int value = min; value <= max; value++) {
			Stack<Tuple> pending = caseLabels.get(labels[value - min]);
			if (pending == null) {
				pending = new Stack<>();
				caseLabels.put(labels[value - min], pending);
			}
			pending.push(new Tuple(min, max, value));
		}
		Stack<Tuple> pending = caseLabels.get(dflt);
		if (pending == null) {
			pending = new Stack<>();
			caseLabels.put(dflt, pending);
		}
		pending.push(new Tuple(min, max, min - 1));
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitLabel(Label label) {
		log.trace("visitLabel(label:{})", label);
		mv.visitLabel(label);
		if (catchLabels.contains(label)) {
			mv.visitLdcInsn(classManager.getInstructionCounter());
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startCatch", "(I)V", false);
		} else {
			Stack<Tuple> pending = caseLabels.get(label);
			if (pending != null) {
				while (!pending.isEmpty()) {
					Tuple t = pending.pop();
					mv.visitLdcInsn(t.min);
					mv.visitLdcInsn(t.max);
					mv.visitLdcInsn(t.cur);
					mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "tableCaseInsn", "(III)V", false);
				}
			}
		}
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		log.trace("visitLookupSwitchInsn(dflt:{})", dflt);
		mv.visitLdcInsn(classManager.getNextInstructionCounter());
		mv.visitLdcInsn(171);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "lookupSwitchInsn", "(II)V", false);
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		// do nothing
	}

}
