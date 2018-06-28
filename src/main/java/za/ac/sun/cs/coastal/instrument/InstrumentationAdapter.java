package za.ac.sun.cs.coastal.instrument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;

public class InstrumentationAdapter extends ClassVisitor implements Reporter {

	private final String name;

	private final StringWriter swriter = new StringWriter();

	private final PrintWriter pwriter = new PrintWriter(swriter);

	public InstrumentationAdapter(String name, ClassVisitor cv) {
		super(Opcodes.ASM6, cv);
		this.name = name;
		if (Configuration.getDumpAsm()) {
			Reporters.register(this);
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		int triggerIndex = Configuration.isTrigger(this.name + "." + name, desc);
		boolean isStatic = (access & Opcodes.ACC_STATIC) > 0;
		int argCount = countArguments(desc);
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if ((mv != null) && Configuration.getDumpAsm()) {
			Printer p = new Textifier(Opcodes.ASM6) {
				@Override
				public void visitCode() {
					pwriter.println("------ " + name + " ------");
				}

				@Override
				public void visitMethodEnd() {
					print(pwriter);
				}
			};
			mv = new TraceMethodVisitor(mv, p);
		}
		if (mv != null) {
			mv = new MethodInstrumentationAdapter(mv, triggerIndex, isStatic, argCount);
		}
		return mv;
	}

	private static final Pattern allParamsPattern = Pattern.compile("(\\(.*?\\))");
	private static final Pattern paramsPattern = Pattern.compile("(\\[?)(C|Z|S|I|J|F|D|(:?L[^;]+;))");

	private static int countArguments(String desc) {
		Matcher m0 = allParamsPattern.matcher(desc);
		if (!m0.find()) {
			return 0;
		}
		Matcher m1 = paramsPattern.matcher(m0.group(1));
		int count = 0;
		while (m1.find()) {
			count++;
		}
		return count;
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void report(PrintWriter out) {
		out.print(swriter.toString());
	}

}
