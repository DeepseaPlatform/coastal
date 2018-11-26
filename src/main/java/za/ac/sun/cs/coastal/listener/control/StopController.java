package za.ac.sun.cs.coastal.listener.control;

import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.ConfigurationX;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class StopController implements MarkerListener, ConfigurationListener {

	private Logger log;

	public StopController() {
		// We expect configurationLoaded(...) to be called shortly.
		// This will initialize this instance.
	}

	@Override
	public void configurationLoaded(ConfigurationX configuration) {
		log = configuration.getLog();
	}

	@Override
	public void collectProperties(Properties properties) {
		// do nothing
	}

	@Override
	public void mark(SymbolicState symbolicState, int marker) {
		// do nothing
	}

	@Override
	public void mark(SymbolicState symbolicState, String marker) {
		// do nothing
	}

	@Override
	public void stop(SymbolicState symbolicState) {
		symbolicState.discontinue();
		new Banner('!').println("PROGRAM TERMINATION POINT REACHED").display(log);
	}
	
	@Override
	public void stop(SymbolicState symbolicState, String message) {
		symbolicState.discontinue();
		new Banner('!').println("PROGRAM TERMINATION POINT REACHED").println(message).display(log);
	}
	
}
