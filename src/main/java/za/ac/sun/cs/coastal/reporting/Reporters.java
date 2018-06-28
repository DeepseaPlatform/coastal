package za.ac.sun.cs.coastal.reporting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;

public class Reporters {

	private static final Logger lgr = Configuration.getLogger();
	
	private static final List<Reporter> reporters = new LinkedList<>();

	public static void register(Reporter reporter) {
		reporters.add(0, reporter);
	}

	public static void report() {
		for (Reporter reporter : reporters) {
			report(reporter);
		}
	}

    private static final String LS = System.getProperty("line.separator");

    private static void report(Reporter reporter) {
    	lgr.info(Banner.getBannerLine(reporter, '='));
		final StringWriter reportWriter = new StringWriter();
		reporter.report(new PrintWriter(reportWriter));
		String[] reportLines = reportWriter.toString().split(LS);
		for (String line : reportLines) {
			lgr.info(line);
		}
	}

}
