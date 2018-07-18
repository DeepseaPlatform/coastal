package za.ac.sun.cs.coastal.listener.coverage;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.MethodInstrumentationAdapter;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class ConditionCoverage implements InstructionListener, Reporter, ConfigurationListener {

	private Logger log;

	private final Map<Integer, Long> reached = new HashMap<>();

	private final Map<Integer, Long> falseTaken = new HashMap<>();

	private final BitSet jumpPoints = new BitSet();

	private boolean dumpCoverage = false;

	private boolean reportCoverage = false;

	public ConditionCoverage() {
		// We expect configurationLoaded(...) to be called shortly.
		// This will initialize this instance.
	}

	@Override
	public void configurationLoaded(Configuration configuration) {
		SymbolicState.registerListener(this);
		log = configuration.getLog();
		configuration.getReporterManager().register(this);
		dumpCoverage = configuration.getBooleanProperty("coastal.coverage.dump", dumpCoverage);
		reportCoverage = configuration.getBooleanProperty("coastal.coverage.report", reportCoverage);
	}

	@Override
	public void collectProperties(Properties properties) {
		properties.setProperty("coastal.coverage.dump", Boolean.toString(dumpCoverage));
		properties.setProperty("coastal.coverage.report", Boolean.toString(reportCoverage));
	}

	@Override
	public void enterMethod(int methodNumber) {
		BitSet newJumpPoints = MethodInstrumentationAdapter.getJumpPoints(methodNumber);
		if (newJumpPoints != null) {
			jumpPoints.or(newJumpPoints);
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
		if (opcode != Opcodes.GOTO) {
			if (dumpCoverage) {
				log.trace("+++ {}", instr);
			}
			incrementMap(reached, instr);
		}
	}

	@Override
	public void postJumpInsn(int instr, int opcode) {
		if (dumpCoverage) {
			log.trace("+++F {}", instr);
		}
		incrementMap(falseTaken, instr);
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

	private void incrementMap(Map<Integer, Long> map, int key) {
		Long l = map.get(key);
		if (l == null) {
			map.put(key, 1L);
		} else {
			map.put(key, l + 1);
		}
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
		long unknownJumpPoints = 0;
		long onlyFalseCount = 0;
		long onlyTrueCount = 0;
		long neitherCount = 0;
		long bothCount = 0;
		BitSet reachedSet = mapKeysToBitSet(reached);
		BitSet falseSet = mapKeysToBitSet(falseTaken);
		BitSet allKeys = new BitSet();
		allKeys.or(reachedSet);
		allKeys.or(falseSet);
		allKeys.or(jumpPoints);
		for (int k = allKeys.nextSetBit(0); k >= 0; k = allKeys.nextSetBit(k + 1)) {
			long r = getValue(reached, k);
			long f = getValue(falseTaken, k);
			if (jumpPoints.get(k)) {
				if (r == 0) {
					assert f == 0;
					neitherCount++;
				} else if (f == 0) {
					onlyTrueCount++;
				}
				if (f == r) {
					onlyFalseCount++;
				} else {
					bothCount++;
				}
			} else {
				unknownJumpPoints++;
			}
		}
		int totalCount = jumpPoints.cardinality();
		out.printf("  Both:       %6d of %6d instructions == %6.2f%%\n", bothCount, totalCount,
				bothCount * 100.0 / totalCount);
		out.printf("  Neither:    %6d of %6d instructions == %6.2f%%\n", neitherCount, totalCount,
				neitherCount * 100.0 / totalCount);
		out.printf("  Only true:  %6d of %6d instructions == %6.2f%%\n", onlyTrueCount, totalCount,
				onlyTrueCount * 100.0 / totalCount);
		out.printf("  Only false: %6d of %6d instructions == %6.2f%%\n", onlyFalseCount, totalCount,
				onlyFalseCount * 100.0 / totalCount);
		if (unknownJumpPoints > 0) {
			out.printf("  Unknown decisions: %d\n", unknownJumpPoints);
		}
		if (reportCoverage) {
			SortedSet<Integer> keys = new TreeSet<>(reached.keySet());
			keys.addAll(falseTaken.keySet());
			for (int i : keys) {
				Long d = reached.get(i);
				if (d == null) {
					d = 0L;
				}
				Long f = falseTaken.get(i);
				if (f == null) {
					f = 0L;
				}
				out.printf("  Decision #%04d reached %6d times, false taken %6d times\n", i, d, f);
			}
		}
	}

	private long getValue(Map<Integer, Long> map, int key) {
		Long l = map.get(key);
		if (l == null) {
			return 0L;
		} else {
			return l;
		}
	}

	private BitSet mapKeysToBitSet(Map<Integer, Long> map) {
		BitSet bs = new BitSet();
		for (int key : map.keySet()) {
			bs.set(key);
		}
		return bs;
	}

}
