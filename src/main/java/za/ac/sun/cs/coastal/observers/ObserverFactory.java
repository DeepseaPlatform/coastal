package za.ac.sun.cs.coastal.observers;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Reporter.Reportable;

public interface ObserverFactory {

	int ONCE_PER_RUN = 0;

	int ONCE_PER_TASK = 1;

	int ONCE_PER_DIVER = 2;

	int ONCE_PER_SURFER = 3;
	
	int getFrequency();

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
