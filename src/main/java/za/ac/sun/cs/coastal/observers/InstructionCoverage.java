package za.ac.sun.cs.coastal.observers;

import java.util.BitSet;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class InstructionCoverage {

	private final Logger log;

	private final Broker broker;
	
	private InstrumentationClassManager classManager = null;

	private final BitSet covered = new BitSet();

	private final BitSet potentials = new BitSet();

	public InstructionCoverage(COASTAL coastal) {
		log = coastal.getLog();
		broker = coastal.getBroker();
		broker.subscribe("change-instrumentation-manager", this::changeInstrumentationManager);
		broker.subscribe("enter-method", this::enterMethod);
		broker.subscribe("insn", this::insn);
		broker.subscribe("int-insn", this::insn);
		broker.subscribe("var-insn", this::insn);
		broker.subscribe("type-insn", this::insn);
		broker.subscribe("field-insn", this::insn);
		broker.subscribe("method-insn", this::insn);
		broker.subscribe("invoke-dynamic-insn", this::insn);
		broker.subscribe("jump-insn", this::insn);
		broker.subscribe("ldc-insn", this::insn);
		broker.subscribe("iinc-insn", this::insn);
		broker.subscribe("table-switch-insn", this::insn);
		broker.subscribe("lookup-switch-insn", this::insn);
		broker.subscribe("multi-anew-array-insn", this::insn);
		broker.subscribe("coastal-stop", this::report);
	}

	public void changeInstrumentationManager(Object object) {
		classManager = (InstrumentationClassManager) object;
	}

	public void enterMethod(Object object) {
		int methodNumber = (Integer) object;
		Integer first = classManager.getFirstInstruction(methodNumber);
		Integer last = classManager.getLastInstruction(methodNumber);
		if ((first != null) && (last != null)) {
			for (int i = first; i <= last; i++) {
				potentials.set(i);
			}
		}
	}

	public void insn(Object object) {
		int instr = (Integer) ((Tuple) object).get(0);
		log.trace("+++ {}", instr);
		covered.set(instr);
	}

	public void report(Object object) {
		int coveredCount = covered.cardinality();
		int potentialTotal = potentials.cardinality();
		double percentage = coveredCount * 100.0 / potentialTotal;
		broker.publish("report", new Tuple("InstructionCoverage.covered-count", coveredCount));
		broker.publish("report", new Tuple("InstructionCoverage.potential-total", potentialTotal));
		broker.publish("report", new Tuple("InstructionCoverage.percentage", percentage));
	}

}
