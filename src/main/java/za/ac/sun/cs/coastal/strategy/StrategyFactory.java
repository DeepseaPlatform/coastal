package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
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

		@Override
		public Void call() throws Exception {
			log.trace("^^^ strategy task starting");
			try {
				while (true) {
					long t0 = System.currentTimeMillis();
					SegmentedPC spc = coastal.getNextPc();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					log.trace("+++ starting refinement");
					List<Model> mdls = refine(spc);
					int d = -1;
					if (mdls != null) {
						coastal.addDiverModels(mdls);
						d = mdls.size() - 1;
					}
					log.trace("+++ added {} models", d);
					coastal.updateWork(d);
				}
			} catch (InterruptedException e) {
				log.trace("^^^ strategy task canceled");
				throw e;
			}
		}

		protected abstract List<Model> refine(SegmentedPC spc);

	}

}
