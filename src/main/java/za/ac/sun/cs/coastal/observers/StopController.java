package za.ac.sun.cs.coastal.observers;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class StopController {

	private final Logger log;

	public StopController(COASTAL coastal) {
		log = coastal.getLog();
		coastal.getBroker().subscribe("stop", this::stop);
	}

	public void stop(Object object) {
		Tuple tuple = (Tuple) object;
		((SymbolicState) tuple.get(0)).discontinue();
		String message = (String) tuple.get(1);
		if (message == null) {
			new Banner('!').println("PROGRAM TERMINATION POINT REACHED").display(log);
		} else {
			new Banner('!').println("PROGRAM TERMINATION POINT REACHED").println(message).display(log);
		}
	}
	
}
