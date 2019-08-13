package za.ac.sun.cs.coastal.observers;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class PCMonitorFactory implements ObserverFactory {

	public PCMonitorFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_RUN;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new PCMonitorManager(coastal);
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

	private static class PCMonitorManager implements ObserverManager {

		private final Logger log;

		// private final COASTAL coastal;

		private final Broker broker;

		PCMonitorManager(COASTAL coastal) {
			log = coastal.getLog();
			// this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("print-pc", this::printPC);
		}

		public void printPC(Object object) {
			Tuple tuple = (Tuple) object;
			String message = (String) tuple.get(1);
			String pc = (String) tuple.get(2);
			Banner banner = new Banner('+');
			if (message != null) {
				banner.println(message);
			}
			banner.println(pc).display(log);
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String[] getPropertyNames() {
			return null;
		}

		@Override
		public Object[] getPropertyValues() {
			return null;
		}

	}

}
