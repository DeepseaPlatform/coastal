package za.ac.sun.cs.coastal.observers;

import za.ac.sun.cs.coastal.COASTAL;

public interface ObserverFactory {

	ObserverManager createManager(COASTAL coastal);
	
	Observer createObserver(COASTAL coastal, ObserverManager manager);

}
