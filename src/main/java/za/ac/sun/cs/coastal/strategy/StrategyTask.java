package za.ac.sun.cs.coastal.strategy;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

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
		log.info("^^^ strategy task starting");
		try {
			while (true) {
				SegmentedPC spc = coastal.getNextPc();
				log.info("+++ starting refinement");
				List<Model> mdls = strategy.refine(spc);
				int d = -1;
				if (mdls != null) {
					coastal.addModels(mdls);
					d = mdls.size() - 1;
				}
				log.info("+++ added {} models", d);
				coastal.updateWork(d);
			}
		} catch (InterruptedException e) {
			log.info("^^^ strategy task canceled");
			throw e;
		}
	}

}
