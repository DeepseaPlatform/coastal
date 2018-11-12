package za.ac.sun.cs.coastal.listener.coverage;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class MarkerCoverage implements MarkerListener, Reporter, ConfigurationListener {

	private Logger log;

	private final Map<String, Integer> markers = new HashMap<>();

	public MarkerCoverage() {
		// We expect configurationLoaded(...) to be called shortly.
		// This will initialize this instance.
	}

	@Override
	public void configurationLoaded(Configuration configuration) {
		log = configuration.getLog();
		configuration.getReporterManager().register(this);
	}

	@Override
	public void collectProperties(Properties properties) {
		// do nothing
	}

	@Override
	public void mark(SymbolicState symbolicState, int marker) {
		mark(symbolicState, Integer.toString(marker));
	}

	@Override
	public void mark(SymbolicState symbolicState, String marker) {
		log.trace("%%% mark hit {}", marker);
		String key = marker;
		Integer n = markers.get(key);
		if (n == null) {
			markers.put(key, 1);
		} else {
			markers.put(key, n + 1);
		}
	}

	@Override
	public void stop(SymbolicState symbolicState) {
		// do nothing
	}
	
	@Override
	public void stop(SymbolicState symbolicState, String message) {
		// do nothing
	}
	
	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public int getOrder() {
		return 555;
	}
	
	@Override
	public String getName() {
		return "MarkerCoverage";
	}

	@Override
	public void report(PrintWriter info, PrintWriter trace) {
		if (markers.size() > 0) {
			for (Map.Entry<String, Integer> entry : markers.entrySet()) {
				info.printf("  %-10s %6d\n", entry.getKey(), entry.getValue());
			}
		}
	}

}
