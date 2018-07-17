package za.ac.sun.cs.coastal;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.symbolic.Diver;

public class COASTAL {

	private static final Logger lgr = Configuration.getLogger();

	public static void main(String[] args) {
		String version = new Version().read();

		// Display banner to start with
		new Banner('~').println("COASTAL version " + version).display(lgr, Level.INFO);
		new CoastReporter();

		// Load and check configuration
		if (args.length < 1) {
			new Banner('@').println("COASTAL PROBLEM\nMISSING PROPERTIES FILE\n")
					.println("USAGE: coastal <properties file>").display(lgr, Level.INFO);
			return;
		}
		if (!Configuration.processProperties(args[0])) {
			new Banner('@').println("COASTAL PROBLEM\n").println("COULD NOT READ PROPERTIES FILE \"" + args[0] + "\"")
					.display(lgr, Level.INFO);
			return;
		}
		if (Configuration.getMain() == null) {
			new Banner('@').println("SUSPICIOUS PROPERTIES FILE\n")
					.println("ARE YOU SURE THAT THE ARGUMENT IS A .properties FILE?").display(lgr, Level.INFO);
			return;
		}

		// Configuration has now been loaded and seems OK
		Diver d = new Diver();
		d.dive();
		Reporters.report();
		if (d.getRuns() < 2) {
			new Banner('@').println("ONLY A SINGLE RUN EXECUTED\n")
					.println("CHECK YOUR SETTINGS -- THERE MIGHT BE A PROBLEM SOMEWHERE").display(lgr, Level.INFO);
		}
		new Banner('~').println("COASTAL DONE").display(lgr, Level.INFO);
	}

	private static class CoastReporter implements Reporter {
		private static final DateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
		private Calendar started = Calendar.getInstance(), stopped;
		private long startTime = System.currentTimeMillis();

		public CoastReporter() {
			Reporters.register(this);
		}

		@Override
		public String getName() {
			return "COASTAL";
		}

		@Override
		public void report(PrintWriter out) {
			stopped = Calendar.getInstance();
			out.println("  Started: " + dateFormat.format(started.getTime()));
			out.println("  Stopped: " + dateFormat.format(stopped.getTime()));
			out.println("  Overall time: " + (System.currentTimeMillis() - startTime));
		}

	}

}
