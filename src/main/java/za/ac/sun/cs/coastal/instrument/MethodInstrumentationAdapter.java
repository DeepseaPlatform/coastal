package za.ac.sun.cs.coastal.instrument;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Configuration.Trigger;

public class MethodInstrumentationAdapter extends MethodVisitor {

	private final Configuration configuration;

	private final Logger log;

	private final boolean dumpInstrumenter;

	private static final String SYMBOLIC = "za/ac/sun/cs/coastal/Symbolic";
	
	private static final String LIBRARY = "za/ac/sun/cs/coastal/symbolic/SymbolicState";

	private static int instructionCounter = 0;

	private static int methodCounter = 0;

	private final int triggerIndex;

	private final boolean isStatic;

	private final int argCount;

	private static Map<Integer, Integer> firstInstruction = new TreeMap<>();

	private static Map<Integer, Integer> lastInstruction = new TreeMap<>();

	private static Map<Integer, BitSet> branchInstructions = new TreeMap<>();

	private static Map<Label, Stack<Tuple>> caseLabels = new HashMap<>();

	private static final class Tuple {
		final int min, max, cur;

		Tuple(int min, int max, int cur) {
			this.min = min;
			this.max = max;
			this.cur = cur;
		}
	}

	private static BitSet currentBranchInstructions;

	public MethodInstrumentationAdapter(Configuration configuration, MethodVisitor cv, int triggerIndex,
			boolean isStatic, int argCount) {
		super(Opcodes.ASM6, cv);
		this.configuration = configuration;
		this.log = configuration.getLog();
		this.dumpInstrumenter = configuration.getDumpInstrumenter();
		this.triggerIndex = triggerIndex;
		this.isStatic = isStatic;
		this.argCount = argCount;
	}

	public static Integer getFirstInstruction(int methodNumber) {
		return firstInstruction.get(methodNumber);
	}

	public static Integer getLastInstruction(int methodNumber) {
		return lastInstruction.get(methodNumber);
	}

	public static BitSet getJumpPoints(int methodNumber) {
		return branchInstructions.get(methodNumber);
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
		} else if (type == int[].class) {
			mv.visitLdcInsn(triggerIndex);
			mv.visitLdcInsn(index);
			mv.visitLdcInsn(address);
			mv.visitIntInsn(Opcodes.ALOAD, address);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteIntArray", "(III[I)[I", false);
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
		if (dumpInstrumenter) {
			log.trace("visitLineNumber(line:{}, label:{})", line, start);
		}
		mv.visitLdcInsn(instructionCounter);
		mv.visitLdcInsn(line);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "linenumber", "(II)V", false);
		mv.visitLineNumber(line, start);
	}

	@Override
	public void visitEnd() {
		if (dumpInstrumenter) {
			log.trace("visitEnd()");
		}
		lastInstruction.put(methodCounter, instructionCounter);
		branchInstructions.put(methodCounter, currentBranchInstructions);
		mv.visitEnd();
	}

	@Override
	public void visitCode() {
		if (dumpInstrumenter) {
			log.trace("visitCode()");
		}
		if (triggerIndex >= 0) {
			//--- IF (symbolicMode) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getSymbolicMode", "()Z", false);
			Label label = new Label();
			mv.visitJumpInsn(Opcodes.IFNE, label);
			//---   triggerMethod()
			mv.visitLdcInsn(++methodCounter);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "triggerMethod", "(I)V", false);
			//---   GENERATE PARAMETER OVERRIDES
			Trigger trigger = configuration.getTrigger(triggerIndex);
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
			mv.visitLdcInsn(methodCounter);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(II)V", false);
			//--- }
			mv.visitLabel(end);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		} else {
			mv.visitLdcInsn(++methodCounter);
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(II)V", false);
		}
		firstInstruction.put(methodCounter, instructionCounter + 1);
		currentBranchInstructions = new BitSet();
		mv.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		if (dumpInstrumenter) {
			log.trace("visitInsn(opcode:{})", opcode);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(II)V", false);
		mv.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		if (dumpInstrumenter) {
			log.trace("visitIntInsn(opcode:{}, operand:{})", opcode, operand);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(operand);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "intInsn", "(III)V", false);
		mv.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		if (dumpInstrumenter) {
			log.trace("visitVarInsn(opcode:{}, var:{})", opcode, var);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(var);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "varInsn", "(III)V", false);
		mv.visitVarInsn(opcode, var);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		if (dumpInstrumenter) {
			log.trace("visitTypeInsn(opcode:{}, type:{})", opcode, type);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "typeInsn", "(II)V", false);
		mv.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		if (dumpInstrumenter) {
			log.trace("visitFieldInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(owner);
		mv.visitLdcInsn(name);
		mv.visitLdcInsn(descriptor);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "fieldInsn",
				"(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
		mv.visitFieldInsn(opcode, owner, name, descriptor);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		if (dumpInstrumenter) {
			log.trace("visitMethodInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		}
		if (owner.equals(SYMBOLIC)) {
			mv.visitMethodInsn(opcode, LIBRARY, name, descriptor, isInterface);
		} else {
			mv.visitLdcInsn(++instructionCounter);
			mv.visitLdcInsn(opcode);
			mv.visitLdcInsn(owner);
			mv.visitLdcInsn(name);
			mv.visitLdcInsn(descriptor);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "methodInsn",
					"(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
			mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
		}
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
			Object... bootstrapMethodArguments) {
		if (dumpInstrumenter) {
			log.trace("visitInvokeDynamicInsn(name:{})", name);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(186);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "invokeDynamicInsn", "(II)V", false);
		mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if (dumpInstrumenter) {
			log.trace("visitJumpInsn(opcode:{}, label:{})", opcode, label);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(II)V", false);
		mv.visitJumpInsn(opcode, label);
		if (opcode != Opcodes.GOTO) {
			mv.visitLdcInsn(instructionCounter);
			mv.visitLdcInsn(opcode);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "postJumpInsn", "(II)V", false);
			currentBranchInstructions.set(instructionCounter);
		}
	}

	@Override
	public void visitLdcInsn(Object value) {
		if (dumpInstrumenter) {
			log.trace("visitLdcInsn(value:{})", value);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(18);
		mv.visitLdcInsn(value);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "ldcInsn", "(IILjava/lang/Object;)V", false);
		mv.visitLdcInsn(value);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		if (dumpInstrumenter) {
			log.trace("visitJumpInsn(var:{}, increment:{})", var, increment);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(var);
		mv.visitLdcInsn(increment);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "iincInsn", "(III)V", false);
		mv.visitIincInsn(var, increment);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		if (dumpInstrumenter) {
			log.trace("visitTableSwitchInsn(min:{}, max:{}, dflt:{})", min, max, dflt);
		}
		mv.visitLdcInsn(++instructionCounter);
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
		mv.visitLabel(label);
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

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (dumpInstrumenter) {
			log.trace("visitLookupSwitchInsn(dflt:{})", dflt);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(171);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "lookupSwitchInsn", "(II)V", false);
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		if (dumpInstrumenter) {
			log.trace("visitMultiANewArrayInsn(numDimensions:{})", numDimensions);
		}
		mv.visitLdcInsn(++instructionCounter);
		mv.visitLdcInsn(197);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "multiANewArrayInsn", "(II)V", false);
		mv.visitMultiANewArrayInsn(descriptor, numDimensions);
	}

	public static int getInstructionCount() {
		return instructionCounter;
	}

}
