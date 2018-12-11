package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Model;

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

		protected abstract List<Model> refine(Execution execution);

	}

}
