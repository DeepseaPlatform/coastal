package za.ac.sun.cs.coastal.observers;

import java.util.BitSet;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class InstructionCoverageFactory implements ObserverFactory {

	public InstructionCoverageFactory(COASTAL coastal, ImmutableConfiguration options) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_TASK;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new InstructionCoverageManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return new InstructionCoverageObserver(coastal, manager);
	}

	// ======================================================================
	//
	// MANAGER FOR INSTRUCTION COVERAGE
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "covered", "potential" };

	private static class InstructionCoverageManager implements ObserverManager {

		private final Broker broker;

		private final BitSet covered = new BitSet();

		private final BitSet potentials = new BitSet();

		InstructionCoverageManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		public synchronized void update(BitSet covered, BitSet potentials) {
			this.covered.or(covered);
			this.potentials.or(potentials);
		}

		public void report(Object object) {
			broker.publish("instruction-coverage-report", null);
			int coveredCount = covered.cardinality();
			int potentialTotal = potentials.cardinality();
			double percentage = coveredCount * 100.0 / potentialTotal;
			broker.publish("report", new Tuple("InstructionCoverage.covered-count", coveredCount));
			broker.publish("report", new Tuple("InstructionCoverage.potential-total", potentialTotal));
			broker.publish("report", new Tuple("InstructionCoverage.percentage", percentage));
			if (coveredCount < potentialTotal) {
				BitSet uncovered = new BitSet();
				uncovered.or(potentials);
				uncovered.andNot(covered);
				broker.publish("report", new Tuple("InstructionCoverage.uncovered", uncovered));
			}
		}

		@Override
		public String getName() {
			return "InstructionCoverage";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[2];
			propertyValues[0] = covered.cardinality();
			propertyValues[1] = potentials.cardinality();
			return propertyValues;
		}

	}

	// ======================================================================
	//
	// OBSERVER FOR INSTRUCTION COVERAGE
	//
	// ======================================================================

	private static class InstructionCoverageObserver implements Observer {

		private static final int UPDATE_FREQUENCY = 100;

		private final Logger log;

		private final InstructionCoverageManager manager;

		private final InstrumentationClassManager classManager;

		private final BitSet covered = new BitSet();

		private final BitSet potentials = new BitSet();

		private int updateCounter = UPDATE_FREQUENCY;

		InstructionCoverageObserver(COASTAL coastal, ObserverManager manager) {
			log = coastal.getLog();
			this.manager = (InstructionCoverageManager) manager;
			this.classManager = coastal.getClassManager();
			Broker broker = coastal.getBroker();
			broker.subscribe("instruction-coverage-report", this::update);
			broker.subscribeThread("enter-method", this::enterMethod);
			broker.subscribeThread("insn", this::insn);
			broker.subscribeThread("int-insn", this::insn);
			broker.subscribeThread("var-insn", this::insn);
			broker.subscribeThread("type-insn", this::insn);
			broker.subscribeThread("field-insn", this::insn);
			broker.subscribeThread("method-insn", this::insn);
			broker.subscribeThread("invoke-dynamic-insn", this::insn);
			broker.subscribeThread("jump-insn", this::insn);
			broker.subscribeThread("ldc-insn", this::insn);
			broker.subscribeThread("iinc-insn", this::insn);
			broker.subscribeThread("table-switch-insn", this::insn);
			broker.subscribeThread("lookup-switch-insn", this::insn);
			broker.subscribeThread("multi-anew-array-insn", this::insn);
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
			if (--updateCounter < 0) {
				update(null);
			}
		}

		public void insn(Object object) {
			int instr = (Integer) ((Tuple) object).get(0);
			log.trace("+++ {}", instr);
			covered.set(instr);
			if (--updateCounter < 0) {
				update(null);
			}
		}

		public void update(Object object) {
			updateCounter = UPDATE_FREQUENCY;
			manager.update(covered, potentials);
		}

	}

}
