package za.ac.sun.cs.coastal.observers;

import java.util.BitSet;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class LineCoverageFactory implements ObserverFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "#L#";

	public LineCoverageFactory(COASTAL coastal, Configuration options) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_TASK;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new LineCoverageManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return new LineCoverageObserver(coastal, manager);
	}

	// ======================================================================
	//
	// MANAGER FOR LINE COVERAGE
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "covered", "potential" };

	private static class LineCoverageManager implements ObserverManager {

		private final Broker broker;

		private final BitSet covered = new BitSet();

		private final BitSet potentials = new BitSet();

		LineCoverageManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		public synchronized void update(BitSet covered, BitSet potentials) {
			this.covered.or(covered);
			this.potentials.or(potentials);
		}

		public void report(Object object) {
			broker.publish("line-coverage-report", null);
			int coveredCount = covered.cardinality();
			int potentialTotal = potentials.cardinality();
			double percentage = coveredCount * 100.0 / potentialTotal;
			broker.publish("report", new Tuple("LineCoverage.covered-count", coveredCount));
			broker.publish("report", new Tuple("LineCoverage.potential-total", potentialTotal));
			broker.publish("report", new Tuple("LineCoverage.percentage", percentage));
			if (coveredCount < potentialTotal) {
				BitSet uncovered = new BitSet();
				uncovered.or(potentials);
				uncovered.andNot(covered);
				broker.publish("report", new Tuple("LineCoverage.uncovered", Configuration.toString(uncovered)));
				//broker.publish("report", new Tuple("LineCoverage.covered", Configuration.toString(covered)));
				//broker.publish("report", new Tuple("LineCoverage.potentials", Configuration.toString(potentials)));
			}
		}

		@Override
		public String getName() {
			return "LineCoverage";
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
	// OBSERVER FOR LINE COVERAGE
	//
	// ======================================================================

	private static class LineCoverageObserver implements Observer {

		private static final int UPDATE_FREQUENCY = 80;

		private final Logger log;

		private final LineCoverageManager manager;

		private final InstrumentationClassManager classManager;

		private final BitSet covered = new BitSet();

		private final BitSet potentials = new BitSet();

		private int updateCounter = UPDATE_FREQUENCY;

		LineCoverageObserver(COASTAL coastal, ObserverManager manager) {
			log = coastal.getLog();
			this.manager = (LineCoverageManager) manager;
			this.classManager = coastal.getClassManager();
			Broker broker = coastal.getBroker();
			broker.subscribe("line-coverage-report", this::update);
			broker.subscribeThread("enter-method", this::enterMethod);
			broker.subscribeThread("linenumber", this::line);
		}

		public void enterMethod(Object object) {
			int methodNumber = (Integer) object;
			BitSet linenumbers = classManager.getLineNumbers(methodNumber);
			if (linenumbers != null) {
				potentials.or(linenumbers);
			}
			if (--updateCounter < 0) {
				update(null);
			}
		}

		public void line(Object object) {
			int lineno = (Integer) ((Tuple) object).get(1);
			log.trace("{} L{}", LOG_PREFIX, lineno);
			covered.set(lineno);
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
