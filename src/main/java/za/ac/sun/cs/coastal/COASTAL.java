package za.ac.sun.cs.coastal;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Pair;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.symbolic.Diver;

/**
 * A COASTAL run. The main function (or some outside client) constructs an
 * instance of this class to execute one run of the system.
 */
public class COASTAL {

	/**
	 * The configuration for this run.
	 */
	private final Configuration configuration;

	/**
	 * The single broker that will manage all messages for this run.
	 */
	private final Broker broker;

	/**
	 * The single reporter that collects information about the run to display at
	 * the end.
	 */
	private final Reporter reporter;

	//private final ReporterManager reporterManager;

	public COASTAL(Configuration configuration) {
		this.configuration = configuration;
		broker = new Broker();
		reporter = new Reporter(this);
		//this.reporterManager = configuration.getReporterManager();
		// this.reporterManager.register(this);
	}

	public Logger getLog() {
		return configuration.getLog();
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public Broker getBroker() {
		return broker;
	}

	public void start() {
		start(true);
	}

	private void start(boolean showBanner) {
		Calendar started = Calendar.getInstance();
		broker.publish("coastal-start", this);
		broker.publish("report", new Pair("COASTAL.start", started));
		final String version = configuration.getVersion();
		final Logger log = configuration.getLog();
		if (showBanner) {
			new Banner('~').println("COASTAL version " + version).display(log);
		}
		configuration.dump();
		Diver d = new Diver(this);
		d.dive();
		Calendar stopped = Calendar.getInstance();
		broker.publish("report", new Pair("COASTAL.stop", stopped));
		broker.publish("report", new Pair("COASTAL.time", stopped.getTimeInMillis() - started.getTimeInMillis()));
		broker.publish("coastal-stop", this);
		reporter.report();
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

	//	@Override
	//	public String getName() {
	//		return "COASTAL";
	//	}
	//
	//	@Override
	//	public void report(PrintWriter info, PrintWriter trace) {
	//		stopped = Calendar.getInstance();
	//		info.println("  Started: " + DATE_FORMAT.format(started.getTime()));
	//		info.println("  Stopped: " + DATE_FORMAT.format(stopped.getTime()));
	//		info.println("  Overall time: " + (stopped.getTimeInMillis() - started.getTimeInMillis()));
	//	}

	// ======================================================================
	//
	// MAIN FUNCTION
	//
	// ======================================================================

	public static void main(String[] args) {
		final Logger log = LogManager.getLogger("COASTAL");
		final String version = new Version().read();
		final ReporterManager reporterManager = new ReporterManager();
		final ConfigurationBuilder builder = new ConfigurationBuilder(log, version, reporterManager);
		new Banner('~').println("COASTAL version " + version).display(log);

		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			return;
		}
		if (!builder.read(args[0])) {
			Banner bn = new Banner('@');
			bn.println("COASTAL PROBLEM\n");
			bn.println("COULD NOT READ PROPERTIES FILE \"" + args[0] + "\"");
			bn.display(log);
			return;
		}

		final Configuration configuration = builder.construct();
		if (configuration.getMain() == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .properties FILE?");
			bn.display(log);
			return;
		}

		new COASTAL(configuration).start(false);
		new Banner('~').println("COASTAL DONE").display(log);
	}

}
