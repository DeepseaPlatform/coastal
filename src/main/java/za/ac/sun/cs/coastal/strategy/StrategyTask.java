package za.ac.sun.cs.coastal.strategy;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.Model;

public class StrategyTask implements Callable<Void> {

	private final COASTAL coastal;

	private final Logger log;

	private final StrategyManager manager;
	
	private final Strategy strategy;

	public StrategyTask(COASTAL coastal) {
		this.coastal = coastal;
		log = coastal.getLog();
		StrategyFactory factory = coastal.getStrategyFactory();
		manager = coastal.getStrategyManager();
		strategy = factory.createStrategy(coastal, manager);
	}

	@Override
	public Void call() throws Exception {
		log.trace("^^^ strategy task starting");
		try {
			while (true) {
				long t0 = System.currentTimeMillis();
				SegmentedPC spc = coastal.getNextPc();
				long t1 = System.currentTimeMillis();
				coastal.recordStrategyWaitTime(t1 - t0);
				log.trace("+++ starting refinement");
				List<Model> mdls = strategy.refine(spc);
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

}
