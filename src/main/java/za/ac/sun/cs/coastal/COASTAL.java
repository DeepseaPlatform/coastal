package za.ac.sun.cs.coastal;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.Recorder;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.symbolic.Model;

public class COASTAL implements Reporter {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

	private final Configuration configuration;
	
	private final ReporterManager reporterManager;

	private Calendar started, stopped;

	private long overallTime;

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
		Disposition disposition = new Disposition(configuration);
		disposition.addFirstModel(new Model(0, null));
		try {
			disposition.addDiverTask();
			disposition.addStrategyTask();
			while (!disposition.workIsDone()) {
				disposition.idle(500);
			}
		} catch (InterruptedException e) {
			log.info(Banner.getBannerLine("main thread interrupted", '!'));
		} finally {
			disposition.shutdown();
		}
		stopped = Calendar.getInstance();
		overallTime = stopped.getTimeInMillis() - started.getTimeInMillis();
		reporterManager.record();
		reporterManager.report();
		if (disposition.getDiveCount() < 2) {
			Banner bn = new Banner('@');
			bn.println("ONLY A SINGLE RUN EXECUTED\n");
			bn.println("CHECK YOUR SETTINGS -- THERE MIGHT BE A PROBLEM SOMEWHERE");
			bn.display(log);
		}
		if (showBanner) {
			new Banner('~').println("COASTAL DONE").display(log);
		}
	}

	public long getOverallTime() {
		return overallTime;
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
	public void record(Recorder recorder) {
		recorder.record(getName(), "started", DATE_FORMAT.format(started.getTime()));
		recorder.record(getName(), "stopped", DATE_FORMAT.format(stopped.getTime()));
		recorder.record(getName(), "time", getOverallTime());
	}

	@Override
	public void report(PrintWriter info, PrintWriter trace) {
		info.println("  Started: " + DATE_FORMAT.format(started.getTime()));
		info.println("  Stopped: " + DATE_FORMAT.format(stopped.getTime()));
		info.println("  Overall time: " + getOverallTime());
	}

}
