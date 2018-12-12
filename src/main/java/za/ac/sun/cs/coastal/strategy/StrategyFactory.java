package za.ac.sun.cs.coastal.strategy;


import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;

public interface StrategyFactory extends TaskFactory {

	StrategyManager createManager(COASTAL coastal);

	Strategy createTask(COASTAL coastal, TaskManager manager);

	// ======================================================================
	//
	// STRATEGY MANAGER INTERFACE
	//
	// ======================================================================

	public interface StrategyManager extends TaskManager {

		void recordWaitTime(long l);

	}

	// ======================================================================
	//
	// TASK INTERFACE
	//
	// ======================================================================

	abstract class Strategy implements Task {

		protected final COASTAL coastal;

		protected final Logger log;

		protected final StrategyManager manager;

		protected Strategy(COASTAL coastal, StrategyManager manager) {
			this.coastal = coastal;
			log = coastal.getLog();
			this.manager = manager;
		}

	}

}
