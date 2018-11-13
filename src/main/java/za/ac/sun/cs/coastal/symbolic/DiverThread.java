package za.ac.sun.cs.coastal.symbolic;

public class DiverThread {

}

/*

package jaco;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Logger;

public class Diver implements Callable<Void> {

	private final Logger log;

	private final BlockingQueue<Integer> models;

	private final BlockingQueue<Integer> pcs;
	
	public Diver(Logger log, BlockingQueue<Integer> models, BlockingQueue<Integer> pcs) {
		this.log = log;
		this.models = models;
		this.pcs = pcs;
	}

	@Override
	public Void call() throws Exception {
		log.info("Diver {} starting", Thread.currentThread().getId());
		while (true) {
			int mdl = models.take();
			log.info("Diver {} removed mdl=={}", Thread.currentThread().getId(), mdl);
			DiverTask task = new DiverTask(mdl);
			int pc = task.calculate();
			log.info("Diver {} added pc=={}", Thread.currentThread().getId(), pc);
			pcs.put(pc);
		}
		// log.info("Diver {} stopping", Thread.currentThread().getId());
		// return null;
	}

}

*/