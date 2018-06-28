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
	
	public MethodInstrumentationAdapter(MethodVisitor cv, int triggerIndex, boolean isStatic, int argCount) {
		super(Opcodes.ASM6, cv);
		this.triggerIndex = triggerIndex;
		this.isStatic = isStatic;
		this.argCount = argCount;
	}

// XX_ILOAD_XX, LLOAD, FLOAD, DLOAD, ALOAD
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
		} else {
			lgr.fatal("UNHANDLED PARAMETER TYPE");
			System.exit(1);
		}
	}

	@Override
	public void visitCode() {
		lgr.trace("visitCode()");
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
			//--- }
			Label end = new Label();
			mv.visitJumpInsn(Opcodes.GOTO, end);
			mv.visitLabel(label);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(I)V", false);
			mv.visitLabel(end);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
//			mv.visitLdcInsn(triggerIndex);
//			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "triggerMethod", "(II)V", false);
//			assert triggerIndex >= 0;
//			int n = trigger.getParamCount();
//			for (int i = 0; i < n; i++) {
//				String pn = trigger.getParamName(i);
//			}
////			setLocal(0, new IntConstant(3));
////			setLocal(1, new IntConstant(4));
//			setLocal(0, new IntVariable("X", 0, 99));
//			setLocal(1, new IntVariable("Y", 0, 99));
		} else {
			mv.visitLdcInsn(argCount + (isStatic ? 0 : 1));
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "startMethod", "(I)V", false);
		}
		mv.visitCode();
	}

	@Override
	public void visitInsn(int opcode) {
		lgr.trace("visitInsn(opcode:{})", opcode);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		lgr.trace("visitIntInsn(opcode:{}, operand:{})", opcode, operand);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(operand);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "intInsn", "(II)V", false);
		mv.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		lgr.trace("visitMethodInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		lgr.trace("visitVarInsn(opcode:{}, var:{})", opcode, var);
		mv.visitLdcInsn(opcode);
		mv.visitLdcInsn(var);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "varInsn", "(II)V", false);
		mv.visitVarInsn(opcode, var);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		lgr.trace("visitFieldInsn(opcode:{}, owner:{}, name:{})", opcode, owner, name);
		mv.visitFieldInsn(opcode, owner, name, descriptor);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		lgr.trace("visitJumpInsn(opcode:{}, label:{})", opcode, label);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "jumpInsn", "(I)V", false);
		mv.visitJumpInsn(opcode, label);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "postJumpInsn", "(I)V", false);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		lgr.trace("visitJumpInsn(var:{}, increment:{})", var, increment);
		mv.visitLdcInsn(132);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitIincInsn(var, increment);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		lgr.trace("visitInvokeDynamicInsn(name:{})", name);
		mv.visitLdcInsn(186);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	}

	@Override
	public void visitLdcInsn(Object value) {
		lgr.trace("visitLdcInsn(value:{})", value);
		mv.visitLdcInsn(18);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitLdcInsn(value);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		lgr.trace("visitLookupSwitchInsn(dflt:{})", dflt);
		mv.visitLdcInsn(171);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		lgr.trace("visitMultiANewArrayInsn(numDimensions:{})", numDimensions);
		mv.visitLdcInsn(197);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitMultiANewArrayInsn(descriptor, numDimensions);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		lgr.trace("visitTableSwitchInsn(min:{}, max:{}, dflt:{})", min, max, dflt);
		mv.visitLdcInsn(170);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		lgr.trace("visitTypeInsn(opcode:{}, type:{})", opcode, type);
		mv.visitLdcInsn(opcode);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, LIBRARY, "insn", "(I)V", false);
		mv.visitTypeInsn(opcode, type);
	}

}
