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
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.symbolic.Diver;

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
//		int threadsMax = (int) configuration.getThreadsMax();
//		int threadsDiver = 1;
//		int threadsStrategy = 1;
//		final ExecutorService executor = Executors.newCachedThreadPool();
//		final CompletionService<Void> cs = new ExecutorCompletionService<Void>(executor);
//		final List<Future<Void>> futures = new ArrayList<Future<Void>>(threadsMax);
//		final BlockingQueue<Integer> models = new PriorityBlockingQueue<>();
//		final BlockingQueue<Integer> pcs = new PriorityBlockingQueue<>();
//		final AtomicLong work = new AtomicLong(0);
//		try {
////			futures.add(cs.submit(new DiverThread(models, pcs)));
////			futures.add(cs.submit(new StrategyThread(models, pcs, work)));
//			long n = work.get();
//			while (n > 0) {
//				log.info(Banner.getBannerLine("work queue " + n, ':'));
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				n = work.get();
//			}
//		} finally {
//			futures.forEach(f -> f.cancel(true));
//		}
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
