package za.ac.sun.cs.coastal.observers;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Reporter.Reportable;

public interface ObserverFactory {

	int ONCE_PER_RUN = 1;

	int ONCE_PER_TASK = 2;

	int ONCE_PER_DIVER = 4;

	int ONCE_PER_SURFER = 8;
	
	int getFrequencyflags();

	ObserverManager createManager(COASTAL coastal);

	Observer createObserver(COASTAL coastal, ObserverManager manager);

	// ======================================================================
	//
	// OBSERVER MANAGER INTERFACE
	//
	// ======================================================================

	public interface ObserverManager extends Reportable {
	}

	// ======================================================================
	//
	// OBSERVER INTERFACE
	//
	// ======================================================================

	public interface Observer {
	}

}
