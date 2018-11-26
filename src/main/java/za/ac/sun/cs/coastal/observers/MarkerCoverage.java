package za.ac.sun.cs.coastal.observers;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class MarkerCoverage {

	private final Logger log;

	private final Broker broker;

	private final Map<String, Integer> markers = new HashMap<>();

	public MarkerCoverage(COASTAL coastal) {
		log = coastal.getLog();
		broker = coastal.getBroker();
		broker.subscribe("mark", this::mark);
		broker.subscribe("coastal-stop", this::report);
	}

	public void mark(Object object) {
		String marker;
		if (object instanceof Integer) {
			marker = Integer.toString((Integer) object);
		} else {
			marker = (String) object;
		}
		log.trace("%%% mark hit {}", marker);
		Integer n = markers.get(marker);
		if (n == null) {
			markers.put(marker, 1);
		} else {
			markers.put(marker, n + 1);
		}
	}

	public void report(Object object) {
		if (markers.size() > 0) {
			for (Map.Entry<String, Integer> entry : markers.entrySet()) {
				broker.publish("report", new Tuple("MarkerCoverage." + entry.getKey(), entry.getValue()));
			}
		}
	}

}
