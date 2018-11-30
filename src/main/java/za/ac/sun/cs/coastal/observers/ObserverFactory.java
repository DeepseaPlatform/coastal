package za.ac.sun.cs.coastal.observers;

import za.ac.sun.cs.coastal.COASTAL;

public interface ObserverFactory {

	static final int ONCE_PER_RUN = 0;

	static final int ONCE_PER_TASK = 1;
	
	static final int ONCE_PER_DIVE = 2;
	
	int getFrequency();
	
	ObserverManager createManager(COASTAL coastal);
	
	Observer createObserver(COASTAL coastal, ObserverManager manager);

}
