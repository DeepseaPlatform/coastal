/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.messages.FreqTuple;
import za.ac.sun.cs.coastal.messages.TimeTuple;
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
	 *                an instance of COASTAL
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
	 *               dummy message parameter
	 */
	private void report(Object object) {
		double totalTime = -1;
		for (Tuple stat : stats) {
			String key = (String) stat.get(0);
			if ("COASTAL.time".equals(key)) {
				totalTime = (Long) stat.get(1) / 1000.0;
				break;
			}
		}
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
			} else if (stat instanceof TimeTuple) {
				if (value instanceof Long) {
					value = String.format("%.3f", 1.0 * (long) value);
				} else if (value instanceof Integer) {
					value = String.format("%.3f", 1.0 * (int) value);
				} else if (value instanceof Double) {
					value = String.format("%.3f", (double) value);
				} else if (value instanceof String) {
					value = String.format("%.3f", Double.parseDouble((String) value));
				}
				log.info("  {}: {} ms", suffix, value);
			} else if ((stat instanceof FreqTuple) && (totalTime > 0)) {
				if (value instanceof Long) {
					value = String.format("%s (%.3f/s)", value, (long) value / totalTime);
				} else if (value instanceof Integer) {
					value = String.format("%s (%.3f/s)", value, (int) value / totalTime);
				} else if (value instanceof Double) {
					value = String.format("%s (%.3f/s)", value, (double) value / totalTime);
				} else if (value instanceof String) {
					value = String.format("%s (%.3f/s)", value, Double.parseDouble((String) value) / totalTime);
				}
				log.info("  {}: {}", suffix, value);
			} else {
				log.info("  {}: {}", suffix, value);
			}
			statMap.put(key, value);
		}
		((COASTAL) object).getBroker().publish("reporting-done", object);
	}

	/**
	 * Interrogate the collected information and return the value that corresponds
	 * to the given key parameter as a long value.
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
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof String) {
			String str = ((String) value).split(" ")[0];
			return Long.parseLong(str);
		} else {
			return 0;
		}
	}

	/**
	 * Interrogate the collected information and return the value that corresponds
	 * to the given key parameter as a boolean value.
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
	 * Interrogate the collected information and return the value that corresponds
	 * to the given key parameter as a string value.
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
	 * An interface to allow one component of COASTAL to interrogate other
	 * components with regard to any information they wish to distribute.
	 * Strategies, observers, and similar actors are expected to implement this
	 * interface. For example, an observer may implement the interface so that, at
	 * the end of the COASTAL run, a {@link Reporter} can obtain information (in the
	 * form of a list of properties) about the execution of the observer during the
	 * run.
	 */
	public interface Reportable {

		/**
		 * Return the name of this component. A return value of {@code null} indicates
		 * that the component has no information to distribute.
		 * 
		 * @return name of this component
		 */
		String getName();

		/**
		 * Return the names of the properties that a component wishes to distribute.
		 * 
		 * @return array of property names
		 */
		String[] getPropertyNames();

		/**
		 * Return the values of the properties that a component wishes to distribute.
		 * 
		 * @return array of property values
		 */
		Object[] getPropertyValues();

	}

}
