package za.ac.sun.cs.coastal.listener.coverage;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.ConfigurationX;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.reporting.Reporter;

public class InstructionCoverage implements InstructionListener, Reporter, ConfigurationListener {

	private Logger log;

	private InstrumentationClassManager classManager = null;

	private final BitSet covered = new BitSet();

	private final BitSet potentials = new BitSet();

	public InstructionCoverage() {
		// We expect configurationLoaded(...) to be called shortly.
		// This will initialize this instance.
	}

	@Override
	public void configurationLoaded(ConfigurationX configuration) {
		log = configuration.getLog();
		configuration.getReporterManager().register(this);
	}

	@Override
	public void collectProperties(Properties properties) {
		// do nothing
	}

	@Override
	public void changeInstrumentationManager(InstrumentationClassManager classManager) {
		this.classManager = classManager;
	}

	@Override
	public void enterMethod(int methodNumber) {
		Integer first = classManager.getFirstInstruction(methodNumber);
		Integer last = classManager.getLastInstruction(methodNumber);
		if ((first != null) && (last != null)) {
			for (int i = first; i <= last; i++) {
				potentials.set(i);
			}
		}
	}

	@Override
	public void exitMethod(int methodNumber) {
		// do nothing
	}

	@Override
	public void linenumber(int instr, int opcode) {
		// do nothing
	}

	@Override
	public void insn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void intInsn(int instr, int opcode, int operand) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void varInsn(int instr, int opcode, int var) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void typeInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void invokeDynamicInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void jumpInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void postJumpInsn(int instr, int opcode) {
		// do nothing
	}

	@Override
	public void ldcInsn(int instr, int opcode, Object value) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void iincInsn(int instr, int var, int increment) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void tableSwitchInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void lookupSwitchInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	@Override
	public void multiANewArrayInsn(int instr, int opcode) {
		log.trace("+++ {}", instr);
		covered.set(instr);
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
	public void report(PrintWriter info, PrintWriter trace) {
		int coveredCount = covered.cardinality();
		int coveredTotal = potentials.cardinality();
		double percentage = coveredCount * 100.0 / coveredTotal;
		info.printf("  Covered %d of %d instructions == %.2f%%\n", coveredCount, coveredTotal, percentage);
	}

}
