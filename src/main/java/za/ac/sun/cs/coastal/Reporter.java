package za.ac.sun.cs.coastal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.messages.Tuple;

/**
 * A collector of runtime information and statistics about various aspects of a
 * COASTAL analysis run. Such a collector relies on three messages:
 * 
 * <ul>
 * <li>{@code "coastal-stop"} signals to components that it they must publish
 * any information they wish, by emitting {@code "report"} messages.</li>
 * <li>{@code "report"} signals to this reporter that a piece of information
 * should be recorded.</li>
 * <li>{@code "coastal-report"} signals to this reporter that it must display
 * all the collected information (to the log file).</li>
 * </ul>
 * 
 * <p>
 * Once the information has been distributed, collected, and displayed, the
 * reporter can also be interrogated to obtain the published information. This
 * is the basis of the testing framework.
 * </p>
 */
public class Reporter {

	/**
	 * A format for all timestamps.
	 */
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

	/**
	 * The COASTAL log.
	 */
	private final Logger log;

	/**
	 * All collected information.
	 */
	private final List<Tuple> stats = new LinkedList<>();

	/**
	 * All collected information in a format that can be interrogated.
	 */
	private final Map<String, Object> statMap = new HashMap<>();

	/**
	 * Construct a new reporter.
	 * 
	 * @param coastal
	 *            an instance of COASTAL
	 */
	public Reporter(COASTAL coastal) {
		log = coastal.getLog();
		coastal.getBroker().subscribe("report", o -> {
			if (o instanceof Tuple) {
				stats.add((Tuple) o);
			}
		});
		coastal.getBroker().subscribe("coastal-report", this::report);
	}

	/**
	 * Display all collected information (to the logger) and also record the
	 * information in {@link #statMap} so that it can be interrogated.
	 * 
	 * @param object
	 *            dummy message parameter
	 */
	private void report(Object object) {
		stats.sort((a, b) -> ((String) a.get(0)).compareTo((String) b.get(0)));
		String curPrefix = "";
		for (Tuple stat : stats) {
			String key = (String) stat.get(0), prefix = "", suffix = key;
			int n = key.indexOf('.');
			if (n != -1) {
				prefix = key.substring(0, n);
				suffix = key.substring(n + 1);
			}
			if (!prefix.equals(curPrefix)) {
				log.info(Banner.getBannerLine(prefix, '='));
				curPrefix = prefix;
			}
			Object value = stat.get(1);
			if (value instanceof Calendar) {
				log.info("  {}: {}", suffix, DATE_FORMAT.format(((Calendar) value).getTime()));
			} else {
				log.info("  {}: {}", suffix, value);
			}
			statMap.put(key, value);
		}
		((COASTAL) object).getBroker().publish("reporting-done", object);
	}

	/**
	 * Interrogate the collected information and return the value that
	 * corresponds to the given key parameter as a long value.
	 * 
	 * @param key
	 *            the key to search for
	 * @return the collected long value associated with the key
	 */
	public long getLong(String key) {
		Object value = statMap.get(key);
		if (value == null) {
			return 0;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else {
			return (Long) value;
		}
	}

	/**
	 * Interrogate the collected information and return the value that
	 * corresponds to the given key parameter as a boolean value.
	 * 
	 * @param key
	 *            the key to search for
	 * @return the collected boolean value associated with the key
	 */
	public boolean getBool(String key) {
		Object value = statMap.get(key);
		if (value == null) {
			return false;
		} else {
			return (Boolean) value;
		}
	}

	/**
	 * Interrogate the collected information and return the value that
	 * corresponds to the given key parameter as a string value.
	 * 
	 * @param key
	 *            the key to search for
	 * @return the collected string value associated with the key
	 */
	public String getString(String key) {
		Object value = statMap.get(key);
		if (value == null) {
			return "";
		} else {
			return (String) value;
		}
	}

	// ======================================================================
	//
	// REPORTABLE INTERFACE
	//
	// ======================================================================

	/**
	 * An interface to allow one component of COASTAL (such as an observer) to
	 * interrogate other components with regard to any information they wish to
	 * distribute. Strategies, observers, and other similar actors are expected
	 * to implement this interface.
	 */
	public interface Reportable {

		/**
		 * Return the name of this component. A return value of {@code null}
		 * indicates that the component has no information to distribute.
		 * 
		 * @return the name of this component
		 */
		String getName();

		/**
		 * Return the names of the properties that a component wishes to
		 * distribute.
		 * 
		 * @return an array of property names
		 */
		String[] getPropertyNames();

		/**
		 * Return the values of the properties that a components wishes to
		 * distribute.
		 * 
		 * @return an array of property values
		 */
		Object[] getPropertyValues();

	}

}
