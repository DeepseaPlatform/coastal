package za.ac.sun.cs.coastal.observers;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class StopControllerFactory implements ObserverFactory {

	public StopControllerFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_RUN;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new StopManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return null;
	}

	// ======================================================================
	//
	// MANAGER FOR STOP CONTROL
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "was-stopped", "message", "trigger" };

	private static class StopManager implements ObserverManager {

		private final Logger log;

		private final COASTAL coastal;

		private final Broker broker;

		private boolean wasStopped = false;

		private String stopMessage = null;

		private String triggerValues = null;

		StopManager(COASTAL coastal) {
			log = coastal.getLog();
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("stop", this::stop);
			broker.subscribe("coastal-stop", this::report);
		}

		public void report(Object object) {
			broker.publish("report", new Tuple("StopController.was-stopped", wasStopped));
			if (stopMessage != null) {
				broker.publish("report", new Tuple("StopController.message", stopMessage));
			}
		}

		public void stop(Object object) {
			if (!coastal.workStopped()) {
				Tuple tuple = (Tuple) object;
				coastal.stopWork();
				stopMessage = (String) tuple.get(1);
				triggerValues = (String) tuple.get(2);
				Banner banner = new Banner('!').println("PROGRAM TERMINATION POINT REACHED");
				if (stopMessage != null) {
					banner.println("\n").println(stopMessage);
				}
				if (triggerValues != null) {
					banner.println("\nTRIGGER:").println(triggerValues);
				}
				banner.display(log);
				wasStopped = true;
			}
		}

		@Override
		public String getName() {
			return "StopController";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[3];
			propertyValues[0] = wasStopped;
			propertyValues[1] = (stopMessage == null) ? "?" : stopMessage;
			propertyValues[2] = (triggerValues == null) ? "?" : triggerValues;
			return propertyValues;
		}

	}

}
