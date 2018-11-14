package za.ac.sun.cs.coastal;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
import za.ac.sun.cs.coastal.listener.PathListener;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyThread;
import za.ac.sun.cs.coastal.symbolic.Diver;
import za.ac.sun.cs.coastal.symbolic.DiverThread;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public class COASTAL implements Reporter {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

	private final Configuration configuration;
	
	private final ReporterManager reporterManager;
	
	private Calendar started, stopped;

	public static void main(String[] args) {
		final Logger log = LogManager.getLogger("COASTAL");
		final String version = new Version().read();
		new Banner('~').println("COASTAL version " + version).display(log);
		new COASTAL(loadConfiguration(args, log, version)).start(false);
		new Banner('~').println("COASTAL DONE").display(log);
	}

	private static Configuration loadConfiguration(String[] args, Logger log, String version) {
		final ReporterManager reporterManager = new ReporterManager();
		final ConfigurationBuilder builder = new ConfigurationBuilder(log, version, reporterManager);
		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			System.exit(1);
		}
		if (!builder.read(args[0])) {
			Banner bn = new Banner('@');
			bn.println("COASTAL PROBLEM\n");
			bn.println("COULD NOT READ PROPERTIES FILE \"" + args[0] + "\"");
			bn.display(log);
			System.exit(1);
		}
		
		final Configuration configuration = builder.construct();
		if (configuration.getMain() == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .properties FILE?");
			bn.display(log);
			System.exit(1);
		}
		return configuration;
	}

	public COASTAL(Configuration configuration) {
		this.configuration = configuration;
		this.reporterManager = configuration.getReporterManager();
		this.reporterManager.register(this);
	}

	public void start() {
		start(true);
	}

	private void start(boolean showBanner) {
		started = Calendar.getInstance();
		final String version = configuration.getVersion();
		final Logger log = configuration.getLog();
		if (showBanner) {
			new Banner('~').println("COASTAL version " + version).display(log);
		}
		configuration.dump();
		
		// Prepare the class manager
		String cp = System.getProperty("java.class.path");
		InstrumentationClassManager classManager = new InstrumentationClassManager(configuration, cp);
		
		// Prepare the listeners
		List<InstructionListener> instructionListeners = new ArrayList<>();
		configuration.<InstructionListener>getListeners(InstructionListener.class).forEach(l -> {
			l.changeInstrumentationManager(classManager);
			instructionListeners.add(l);
		});
		List<MarkerListener> markerListeners = new ArrayList<>();
		configuration.<MarkerListener>getListeners(MarkerListener.class).forEach(l -> markerListeners.add(l));
		List<PathListener> pathListeners = new ArrayList<>();
		configuration.<PathListener>getListeners(PathListener.class).forEach(l -> pathListeners.add(l));

		// Prepare the strategy
		Strategy strategy = configuration.getStrategy();
		if (strategy == null) {
			log.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}

		// Prepare the threads and synchronization objects
		int threadsMax = (int) configuration.getThreadsMax();
//		int threadsDiver = 1;
//		int threadsStrategy = 1;
		final ExecutorService executor = Executors.newCachedThreadPool();
		final CompletionService<Void> cs = new ExecutorCompletionService<Void>(executor);
		final List<Future<Void>> futures = new ArrayList<Future<Void>>(threadsMax);
		final BlockingQueue<Model> models = new PriorityBlockingQueue<>(10, (Model m1, Model m2) -> m1.getPriority() - m2.getPriority());
		final BlockingQueue<SegmentedPC> pcs = new LinkedBlockingQueue<>();
		final AtomicLong work = new AtomicLong(0);
		final AtomicBoolean workDone = new AtomicBoolean(false);
		
		// Insert the first dummy model
		Model firstModel = new Model(0, null);
		try {
			models.put(firstModel);
			work.incrementAndGet();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// Start the threads and wait for the work to be completed
		try {
			futures.add(cs.submit(new DiverThread(configuration, models, pcs, classManager, instructionListeners, markerListeners, pathListeners)));
			futures.add(cs.submit(new StrategyThread(configuration, strategy, models, pcs, work, workDone)));
			synchronized (workDone) {
				while (!workDone.get()) {
					log.info(Banner.getBannerLine("work queue " + work.get(), ':'));
//					workDone.wait(500);
					workDone.wait(1000);
				}
			}
		} catch (InterruptedException e) {
			log.info(Banner.getBannerLine("main thread interrupted", '!'));
		} finally {
			futures.forEach(f -> f.cancel(true));
		}
		Diver d = new Diver(configuration);
		d.dive();
		reporterManager.report();
		if (d.getRuns() < 2) {
			Banner bn = new Banner('@');
			bn.println("ONLY A SINGLE RUN EXECUTED\n");
			bn.println("CHECK YOUR SETTINGS -- THERE MIGHT BE A PROBLEM SOMEWHERE");
			bn.display(log);
		}
		if (showBanner) {
			new Banner('~').println("COASTAL DONE").display(log);
		}
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public int getOrder() {
		return 999;
	}

	@Override
	public String getName() {
		return "COASTAL";
	}
	
	@Override
	public void report(PrintWriter info, PrintWriter trace) {
		stopped = Calendar.getInstance();
		info.println("  Started: " + DATE_FORMAT.format(started.getTime()));
		info.println("  Stopped: " + DATE_FORMAT.format(stopped.getTime()));
		info.println("  Overall time: " + (stopped.getTimeInMillis() - started.getTimeInMillis()));
	}

}
