package za.ac.sun.cs.coastal.listener.coverage;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.MethodInstrumentationAdapter;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class InstructionCoverage implements InstructionListener, Reporter, ConfigurationListener {

	private static final Logger lgr = Configuration.getLogger();

	private final BitSet covered = new BitSet();

	private final BitSet potentials = new BitSet();
	
	private boolean dumpCoverage = false;

	public InstructionCoverage() {
		Reporters.register(this);
		Configuration.registerListener(this);
		SymbolicState.registerListener(this);
	}

	@Override
	public void enterMethod(int methodNumber) {
		Integer first = MethodInstrumentationAdapter.getFirstInstruction(methodNumber);
		Integer last = MethodInstrumentationAdapter.getLastInstruction(methodNumber);
		if ((first != null) && (last != null)) {
			for (int i = first; i <= last; i++) {
				potentials.set(i);
			}
		}
	}

	@Override
	public void exitMethod(int methodNumber) {
		// ignore
	}
	
	@Override
	public void linenumber(int instr, int opcode) {
		// ignore
	}

	@Override
	public void insn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void intInsn(int instr, int opcode, int operand) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void varInsn(int instr, int opcode, int var) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void typeInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void invokeDynamicInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void jumpInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void postJumpInsn(int instr, int opcode) {
		// ignore
	}

	@Override
	public void ldcInsn(int instr, int opcode, Object value) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void iincInsn(int instr, int var, int increment) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void tableSwitchInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void lookupSwitchInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	@Override
	public void multiANewArrayInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		covered.set(instr);
	}

	// ======================================================================
	//
	// CONFIGURATION
	//
	// ======================================================================

	@Override
	public void configurationLoaded(Properties properties) {
		boolean dc = Configuration.getBooleanProperty(properties, "coastal.dump.coverage", dumpCoverage);
		dumpCoverage = dc || Configuration.getDumpAll();
	}

	@Override
	public void configurationDump() {
		lgr.info("coastal.dump.coverage = {}", dumpCoverage);
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public String getName() {
		return "InstructionCoverage";
	}

	@Override
	public void report(PrintWriter out) {
		int coveredCount = covered.cardinality();
		int coveredTotal = potentials.cardinality();
		double percentage = coveredCount * 100.0 / coveredTotal;
		out.printf("  Covered %d of %d instructions == %.2f%%\n", coveredCount, coveredTotal, percentage);
	}

}
