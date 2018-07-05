package za.ac.sun.cs.coastal.instrument;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Configuration.Trigger;

public class MethodInstrumentationAdapter extends MethodVisitor {

	private static final Logger lgr = Configuration.getLogger();
	
	private static final String LIBRARY = "za/ac/sun/cs/coastal/symbolic/SymbolicState";

	private final int triggerIndex;

	private final boolean isStatic;
	
	private final int argCount;
	
	private static boolean dumpInstrumenter = Configuration.getDumpInstrumenter();

	public MethodInstrumentationAdapter(MethodVisitor cv, int triggerIndex, boolean isStatic, int argCount) {
		super(Opcodes.ASM6, cv);
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
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getConcreteString", "(IIILjava/lang/String;)Ljava/lang/String;", false);
			mv.visitIntInsn(Opcodes.ASTORE, address);
		} else {
			lgr.fatal("UNHANDLED PARAMETER TYPE");
			System.exit(1);
		}
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		if (dumpInstrumenter) {
			lgr.trace("visitLineNumber(line:{}, label:{})", line, start);
		}
		mv.visitLdcInsn(line);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "reportLinenumber", "(I)V", false);
		mv.visitLineNumber(line, start);
	}
	
	@Override
	public void visitCode() {
		if (dumpInstrumenter) {
			lgr.trace("visitCode()");
		}
		if (triggerIndex >= 0) {
			//--- IF (symbolicMode) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "getSymbolicMode", "()Z", false);
			Label label = new Label();
			mv.visitJumpInsn(Opcodes.IFNE, label);
			//---   triggerMethod()
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "triggerMethod", "()V", false);
			//---   GENERATE PARAMETER OVERRIDES
			Trigger trigger = Configuration.getTrigger(triggerIndex);
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
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(I)V", false);
			//--- }
			mv.visitLabel(end);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		} else {
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(I)V", false);
		}
		mv.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		if (dumpInstrumenter) {
			lgr.trace("visitInsn(opcode:{})", opcode);
		}
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		if (dumpInstrumenter) {
			lgr.trace("visitIntInsn(opcode:{}, operand:{})", opcode, operand);
		}
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(operand);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "intInsn", "(II)V", false);
		mv.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		if (dumpInstrumenter) {
			lgr.trace("visitVarInsn(opcode:{}, var:{})", opcode, var);
		}
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(var);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "varInsn", "(II)V", false);
		mv.visitVarInsn(opcode, var);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		if (dumpInstrumenter) {
			lgr.trace("visitTypeInsn(opcode:{}, type:{})", opcode, type);
		}
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "typeInsn", "(I)V", false);
		mv.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		if (dumpInstrumenter) {
			lgr.trace("visitFieldInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		}
		mv.visitFieldInsn(opcode, owner, name, descriptor);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(owner);
		mv.visitLdcInsn(name);
		mv.visitLdcInsn(descriptor);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "fieldInsn", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		if (dumpInstrumenter) {
			lgr.trace("visitMethodInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		}
		if (owner.equals(LIBRARY) && name.equals("stop")) {
			mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
		} else {
			mv.visitLdcInsn(opcode);
			mv.visitLdcInsn(owner);
			mv.visitLdcInsn(name);
			mv.visitLdcInsn(descriptor);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "methodInsn", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
			mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
		}
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		if (dumpInstrumenter) {
			lgr.trace("visitInvokeDynamicInsn(name:{})", name);
		}
		mv.visitLdcInsn(186);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "invokeDynamicInsn", "(I)V", false);
		mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if (dumpInstrumenter) {
			lgr.trace("visitJumpInsn(opcode:{}, label:{})", opcode, label);
		}
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(I)V", false);
		mv.visitJumpInsn(opcode, label);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "postJumpInsn", "(I)V", false);
	}

	@Override
	public void visitLdcInsn(Object value) {
		if (dumpInstrumenter) {
			lgr.trace("visitLdcInsn(value:{})", value);
		}
		mv.visitLdcInsn(18);
		mv.visitLdcInsn(value);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "ldcInsn", "(ILjava/lang/Object;)V", false);
		mv.visitLdcInsn(value);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		if (dumpInstrumenter) {
			lgr.trace("visitJumpInsn(var:{}, increment:{})", var, increment);
		}
		mv.visitLdcInsn(var);
		mv.visitLdcInsn(increment);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "iincInsn", "(II)V", false);
		mv.visitIincInsn(var, increment);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		if (dumpInstrumenter) {
			lgr.trace("visitTableSwitchInsn(min:{}, max:{}, dflt:{})", min, max, dflt);
		}
		mv.visitLdcInsn(170);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "tableSwitchInsn", "(I)V", false);
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (dumpInstrumenter) {
			lgr.trace("visitLookupSwitchInsn(dflt:{})", dflt);
		}
		mv.visitLdcInsn(171);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "lookupSwitchInsn", "(I)V", false);
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		if (dumpInstrumenter) {
			lgr.trace("visitMultiANewArrayInsn(numDimensions:{})", numDimensions);
		}
		mv.visitLdcInsn(197);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "multiANewArrayInsn", "(I)V", false);
		mv.visitMultiANewArrayInsn(descriptor, numDimensions);
	}

}
