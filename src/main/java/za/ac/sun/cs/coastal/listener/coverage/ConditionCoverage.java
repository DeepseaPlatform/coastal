package za.ac.sun.cs.coastal.listener.coverage;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.MethodInstrumentationAdapter;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class ConditionCoverage implements InstructionListener, Reporter, ConfigurationListener {

	private static final Logger lgr = Configuration.getLogger();

	private final Map<Integer, Long> coveredTrue = new HashMap<>();

	private final Map<Integer, Long> coveredFalse = new HashMap<>();

	private final BitSet potentials = new BitSet();

	private boolean dumpCoverage = false;

	public ConditionCoverage() {
		Reporters.register(this);
		Configuration.registerListener(this);
		SymbolicState.registerListener(this);
	}

	@Override
	public void enterMethod(int methodNumber) {
		BitSet newPotentials = MethodInstrumentationAdapter.getJumpPoints(methodNumber);
		if (newPotentials != null) {
			potentials.or(newPotentials);
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
		// ignore
	}

	@Override
	public void intInsn(int instr, int opcode, int operand) {
		// ignore
	}

	@Override
	public void varInsn(int instr, int opcode, int var) {
		// ignore
	}

	@Override
	public void typeInsn(int instr, int opcode) {
		// ignore
	}

	@Override
	public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor) {
		// ignore
	}

	@Override
	public void methodInsn(int instr, int opcode, String owner, String name, String descriptor) {
		// ignore
	}

	@Override
	public void invokeDynamicInsn(int instr, int opcode) {
		// ignore
	}

	@Override
	public void jumpInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++ {}", instr);
		}
		Long n = coveredTrue.get(instr);
		if (n == null) {
			n = 0L;
		}
		coveredTrue.put(instr, n + 1);
	}

	@Override
	public void postJumpInsn(int instr, int opcode) {
		if (dumpCoverage) {
			lgr.trace("+++F {}", instr);
		}
		Long n = coveredFalse.get(instr);
		if (n == null) {
			n = 0L;
		}
		coveredFalse.put(instr, n + 1);
		coveredTrue.put(instr, coveredTrue.get(instr) - 1);
	}

	@Override
	public void ldcInsn(int instr, int opcode, Object value) {
		// ignore
	}

	@Override
	public void iincInsn(int instr, int var, int increment) {
		// ignore
	}

	@Override
	public void tableSwitchInsn(int instr, int opcode) {
		// ignore
	}

	@Override
	public void lookupSwitchInsn(int instr, int opcode) {
		// ignore
	}

	@Override
	public void multiANewArrayInsn(int instr, int opcode) {
		// ignore
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
		return "ConditionCoverage";
	}

	@Override
	public void report(PrintWriter out) {
		BitSet trueSet = new BitSet();
		for (int i : coveredTrue.keySet()) {
			if (coveredTrue.get(i) > 0) {
				trueSet.set(i);
			}
		}
		BitSet falseSet = new BitSet();
		for (int i : coveredFalse.keySet()) {
			if (coveredFalse.get(i) > 0) {
				falseSet.set(i);
			}
		}
		trueSet.and(potentials);
		falseSet.and(potentials);
		BitSet x = new BitSet();
		x.or(trueSet);
		x.or(falseSet);
		int bothCount = x.cardinality();
		x.clear();
		x.or(potentials);
		x.andNot(trueSet);
		x.andNot(falseSet);
		int neitherCount = x.cardinality();
		x.clear();
		x.or(trueSet);
		x.andNot(falseSet);
		int onlyTrueCount = x.cardinality();
		x.clear();
		x.or(falseSet);
		x.andNot(trueSet);
		int onlyFalseCount = x.cardinality();
		int totalCount = potentials.cardinality();
		out.printf("  Both:       %d of %d instructions == %.2f%%\n", bothCount, totalCount, bothCount * 100.0 / totalCount);
		out.printf("  Neither:    %d of %d instructions == %.2f%%\n", neitherCount, totalCount, neitherCount * 100.0 / totalCount);
		out.printf("  Only true:  %d of %d instructions == %.2f%%\n", onlyTrueCount, totalCount, onlyTrueCount * 100.0 / totalCount);
		out.printf("  Only false: %d of %d instructions == %.2f%%\n", onlyFalseCount, totalCount, onlyFalseCount * 100.0 / totalCount);
	}

}
