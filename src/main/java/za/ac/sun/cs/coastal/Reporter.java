package za.ac.sun.cs.coastal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.messages.Pair;
import za.ac.sun.cs.coastal.reporting.Banner;

public class Reporter {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

	private final Logger log;
	
	private final List<Pair> stats = new LinkedList<>();
	
	public Reporter(COASTAL coastal) {
		log = coastal.getLog(); 
		coastal.getBroker().subscribe("report", o -> {
			if (o instanceof Pair) {
				stats.add((Pair) o);
			}
		});
	}

	public void report() {
		stats.sort((a, b) -> ((String) a.getKey()).compareTo((String) b.getKey()));
		String curPrefix = "";
		for (Pair stat : stats) {
			String key = (String) stat.getKey(), prefix = "", suffix = key; 
			int n = key.indexOf('.');
			if (n != -1) {
				prefix = key.substring(0, n);
				suffix = key.substring(n + 1);
			}
			if (!prefix.equals(curPrefix)) {
				log.info(Banner.getBannerLine(prefix, '='));
				curPrefix = prefix;
			}
			Object value = stat.getValue();
			if (value instanceof Calendar) {
				log.info("  {}: {}", suffix, DATE_FORMAT.format(((Calendar) value).getTime())); 
			} else {
				log.info("  {}: {}", suffix, value); 
			}
		}
	}

}
