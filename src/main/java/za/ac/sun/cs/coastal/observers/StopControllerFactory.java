package za.ac.sun.cs.coastal.observers;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Tuple;

public class StopControllerFactory implements ObserverFactory {

	public StopControllerFactory(COASTAL coastal) {
	}

	@Override
	public int getFrequency() {
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

	private static class StopManager implements ObserverManager {

		private final Logger log;

		private final COASTAL coastal;

		StopManager(COASTAL coastal) {
			log = coastal.getLog();
			this.coastal = coastal;
			coastal.getBroker().subscribe("stop", this::stop);
		}

		public void stop(Object object) {
			Tuple tuple = (Tuple) object;
			coastal.stopWork();
			String message = (String) tuple.get(1);
			if (message == null) {
				new Banner('!').println("PROGRAM TERMINATION POINT REACHED").display(log);
			} else {
				new Banner('!').println("PROGRAM TERMINATION POINT REACHED").println(message).display(log);
			}
		}

	}

}
