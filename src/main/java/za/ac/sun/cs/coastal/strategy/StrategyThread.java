package za.ac.sun.cs.coastal.strategy;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public class StrategyThread implements Callable<Void> {

	private final Logger log;

	private final Strategy strategy;

	private final BlockingQueue<Model> models;

	private final BlockingQueue<SegmentedPC> pcs;

	private final AtomicLong work;

	private final AtomicBoolean workDone;

	public StrategyThread(Configuration configuration, Strategy strategy, BlockingQueue<Model> models,
			BlockingQueue<SegmentedPC> pcs, AtomicLong work, AtomicBoolean workDone) {
		this.log = configuration.getLog();
		this.strategy = strategy;
		this.models = models;
		this.pcs = pcs;
		this.work = work;
		this.workDone = workDone;
	}

	@Override
	public Void call() throws Exception {
		log.info("Strategy thread starting");
		while (true) {
			SegmentedPC spc = pcs.take();
			log.info(Banner.getBannerLine("starting refinement, spc == " + spc, '-'));
			List<Model> mdls = strategy.refine(spc);
			int d = -1;
			if (mdls != null) {
				mdls.forEach(m -> {
					try {
						models.put(m);
						log.info(Banner.getBannerLine("added model " + m, '-'));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				d = mdls.size() - 1;
			}
			log.info(Banner.getBannerLine("added models d == " + d, '-'));
			long n = work.addAndGet(d);
			if (n == 0) {
				workDone.set(true);
			}
		}
	}

}
