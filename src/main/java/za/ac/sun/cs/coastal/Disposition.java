package za.ac.sun.cs.coastal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
import za.ac.sun.cs.coastal.listener.PathListener;
import za.ac.sun.cs.coastal.reporting.Recorder;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyThread;
import za.ac.sun.cs.coastal.symbolic.Diver;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.expr.Constant;

/**
 * Container for a range of runtime information including
 * 
 * <ul>
 * <li>Synchronization objects</li>
 * <li>Summary results from divers and strategies</li>
 * </ul>
 */
public class Disposition implements Reporter {

	private final Configuration configuration;

	private final Logger log;
	
	private final Strategy strategy;

	private final InstrumentationClassManager classManager;
	
	private final List<InstructionListener> instructionListeners = new ArrayList<>();
	
	private final List<MarkerListener> markerListeners = new ArrayList<>();
	
	private final List<PathListener> pathListeners = new ArrayList<>();

	private final int threadsMax;

	private final ExecutorService executor;

	private final CompletionService<Void> cs;

	private final List<Future<Void>> futures;

	private final BlockingQueue<Model> models;

	private final BlockingQueue<SegmentedPC> pcs;

	/**
	 * The outstanding number of models that have not been processed by divers,
	 * or whose resulting path conditions have not been processed by a strategy.
	 * This is not merely the number of items in the models queue or the pcs
	 * queue: work may also be presented by a model of path condition currently
	 * being processed. The strategy adjusts the work after it has processed a
	 * path condition.
	 */
	private final AtomicLong work = new AtomicLong(0);

	/**
	 * A flag to indicate that either (1) all work is done, or (2) symbolic
	 * execution must stop because a "stop" point was reached.
	 */
	private final AtomicBoolean workDone = new AtomicBoolean(false);

	private final AtomicLong diveCounter = new AtomicLong(0);

	private final AtomicLong diveTime = new AtomicLong(0);

	private long diveThreadCount = 0;

	private final long timeLimit;

	private long startingTime = System.currentTimeMillis();

	public Disposition(Configuration configuration) {
		this.configuration = configuration;
		this.log = configuration.getLog();
		// Prepare the strategy
		strategy = configuration.getStrategy();
		if (strategy == null) {
			log.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}
		// Prepare the class manager
		String cp = System.getProperty("java.class.path");
		classManager = new InstrumentationClassManager(configuration, cp);
		// Prepare the listeners
		configuration.<InstructionListener>getListeners(InstructionListener.class).forEach(l -> {
			l.changeInstrumentationManager(classManager);
			instructionListeners.add(l);
		});
		configuration.<MarkerListener>getListeners(MarkerListener.class).forEach(l -> markerListeners.add(l));
		configuration.<PathListener>getListeners(PathListener.class).forEach(l -> pathListeners.add(l));
		// Prepare the threads and synchronization objects
//		int threadsDiver = 1;
//		int threadsStrategy = 1;
		threadsMax = (int) configuration.getThreadsMax();
		executor = Executors.newCachedThreadPool();
		cs = new ExecutorCompletionService<Void>(executor);
		futures = new ArrayList<Future<Void>>(threadsMax);
		models = new PriorityBlockingQueue<>(10, (Model m1, Model m2) -> m1.getPriority() - m2.getPriority());
		pcs = new LinkedBlockingQueue<>();
		configuration.getReporterManager().register(this);
		timeLimit = configuration.getLimitTime();
	}

	/**
	 * Return the number of outstanding models that are still to be fully
	 * processed.
	 * 
	 * @return the number of unprocessed models
	 */
	public long getWork() {
		return work.get();
	}

	/**
	 * Wait for a change in the status of the workDone flag or until a specified
	 * time has elapsed.
	 * 
	 * @param delay
	 *            time to wait in milliseconds
	 */
	public void idle(long delay) throws InterruptedException {
		if ((System.currentTimeMillis() - startingTime) / 1000 > timeLimit) {
			log.warn("time limit reached");
			stopWork();
		} else {
			synchronized (workDone) {
				workDone.wait(delay);
			}
		}
	}

	/**
	 * Return the value of the workDone flag.
	 * 
	 * @return the boolean value of the workDone flag
	 */
	public boolean workIsDone() {
		return workDone.get();
	}

	public void addFirstModel(Model firstModel) {
		try {
			models.put(firstModel);
			work.incrementAndGet();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void addDiverTask() {
		futures.add(cs.submit(new Diver(this)));
		diveThreadCount++;
	}

	public void addStrategyTask() {
		futures.add(cs.submit(new StrategyThread(configuration, strategy, models, pcs, work, workDone)));
	}

	public void shutdown() {
		futures.forEach(f -> f.cancel(true));
		executor.shutdownNow();
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public Map<String, Constant> getNextModel() throws InterruptedException {
		return models.take().getConcreteValues();
	}

	public long getNextDiveCount() {
		return diveCounter.incrementAndGet();
	}

	public long getDiveCount() {
		return diveCounter.get();
	}
	
	public void stopWork() {
		workDone.set(true);
		workDone.notifyAll();
	}

	public void addPc(SegmentedPC spc) {
		try {
			pcs.put(spc);
		} catch (InterruptedException e) {
			// ignore silently
		}
	}

	public ClassLoader createClassLoader(SymbolicState symbolicState) {
		return classManager.createClassLoader(symbolicState);
	}

	public void notifyPathListeners(SymbolicState symbolicState) {
		pathListeners.forEach(l -> l.visit(symbolicState));		
	}

	public void recordDiveTime(long time) {
		diveTime.addAndGet(time);
	}

	public long getDiveTime() {
		return diveTime.get();
	}
	
	public long getDiveThreadCount() {
		return diveThreadCount;
	}
	
	public List<InstructionListener> getInstructionListeners() {
		return instructionListeners;
	}

	public List<MarkerListener> getMarkerListeners() {
		return markerListeners;
	}
	
	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public int getOrder() {
		return 888;
	}

	@Override
	public String getName() {
		return "Dive";
	}
	
	@Override
	public void record(Recorder recorder) {
		recorder.record(getName(), "runs", getDiveCount());
		recorder.record(getName(), "threads", getDiveThreadCount());
		recorder.record(getName(), "time", getDiveTime());
	}

	@Override
	public void report(PrintWriter info, PrintWriter trace) {
		info.println("  Runs: " + getDiveCount());
		info.println("  Diver threads: " + getDiveThreadCount());
		info.println("  Overall dive time: " + getDiveTime());
	}

}
