package za.ac.sun.cs.coastal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationUtils;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.symbolic.Diver;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

/**
 * A COASTAL analysis run. The main function (or some outside client) constructs
 * an instance of this class to execute one analysis run of the system.
 */
public class COASTAL {

	/**
	 * The logger for this analysis run. This is not created but set by the
	 * outside world.
	 */
	private final Logger log;

	/**
	 * The configuration for this run.
	 */
	private final ImmutableConfiguration config;

	/**
	 * The single broker that will manage all messages for this analysis run.
	 */
	private final Broker broker;

	/**
	 * The single reporter that collects information about the analysis run to
	 * display at the end.
	 */
	private final Reporter reporter;

	/**
	 * The manager of all classes loaded during the analysis run.
	 */
	private final InstrumentationClassManager classManager;

	/**
	 * A list of all targets (prefixes of classes that will be instrumented).
	 */
	private final List<String> targets = new ArrayList<>();

	/**
	 * A list of all triggers that switch on symbolic execution.
	 */
	private final List<Trigger> triggers = new ArrayList<>();

	/**
	 * A map from variable names to their lower bounds.
	 */
	private final Map<String, Integer> minBounds = new HashMap<>();

	/**
	 * A map from variable names to their lower bounds.
	 */
	private final Map<String, Integer> maxBounds = new HashMap<>();

	/**
	 * The default minimum bound for integers.
	 */
	private final int defaultMinIntValue;

	/**
	 * The default maximum bound for integers.
	 */
	private final int defaultMaxIntValue;
	
	/**
	 * A map from method names to delegate objects (which are instances of the modelling classes).
	 */
	private final Map<String, Object> delegates = new HashMap<>();

	/**
	 * Initialize the final fields for this analysis run of COASTAL.
	 * 
	 * @param log
	 *            the logger to use for this analysis run
	 * @param config
	 *            the configuration to use for this analysis run
	 */
	public COASTAL(Logger log, ImmutableConfiguration config) {
		this.log = log;
		this.config = config;
		broker = new Broker();
		reporter = new Reporter(this);
		classManager = new InstrumentationClassManager(this, System.getProperty("java.class.path"));
		parseConfig();
		defaultMinIntValue = config.getInt("coastal.bound.int.min", Integer.MIN_VALUE);
		defaultMaxIntValue = config.getInt("coastal.bound.int.max", Integer.MAX_VALUE);
	}

	/**
	 * Return the logger for this run of COASTAL.
	 * 
	 * @return the one and only logger for this analysis run
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * Return the configuration for this analysis of COASTAL. This configuration
	 * is immutable.
	 * 
	 * @return the configuration
	 */
	public ImmutableConfiguration getConfig() {
		return config;
	}

	/**
	 * Return the message broker for this analysis run of COASTAL.
	 * 
	 * @return the message broker
	 */
	public Broker getBroker() {
		return broker;
	}

	/**
	 * Return the class manager for this analysis run of COASTAL.
	 * 
	 * @return the class manager
	 */
	public InstrumentationClassManager getClassManager() {
		return classManager;
	}

	/**
	 * Check is a potential target is an actual target. The potential target is
	 * simply a class name that is compared to all known targets to see if any
	 * are prefixes of the potential target.
	 * 
	 * @param potentialTarget
	 *            the name of class
	 * @return true if and only if the potential target is prefixed by a known
	 *         target
	 */
	public boolean isTarget(String potentialTarget) {
		for (String target : targets) {
			if (potentialTarget.startsWith(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Find the index of the trigger with the corresponding name and signature.
	 * If no such trigger exists, return -1.
	 * 
	 * @param name
	 *            the method name of the (potential) trigger
	 * @param signature
	 *            the signature of the (potential) trigger
	 * @return the index of the trigger in the list of triggers, or -1
	 */
	public int findTrigger(String name, String signature) {
		int index = 0;
		for (Trigger t : triggers) {
			if (t.match(name, signature)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Return the trigger with a specified index.
	 * 
	 * @param index
	 *            the index we are searching for
	 * @return the corresponding trigger or {@code null} if there is no such
	 *         trigger
	 */
	public Trigger getTrigger(int index) {
		return triggers.get(index);
	}

	/**
	 * Find a delegate for a specified class name.
	 * 
	 * @param className the name of the class to search for
	 * @return the delegate object or {@code null} if there is none
	 */
	public Object findDelegate(String className) {
		return delegates.get(className);
	}

	/**
	 * A convenient constant for use in {@link #findDelegate(String, String, String)}.  This represents the empty list of parameters.
	 */
	private static final Class<?>[] EMPTY_PARAMETERS = new Class<?>[0];

	/**
	 * Find a delegate method.
	 * 
	 * @param owner the method class name
	 * @param name the method name
	 * @param descriptor the method signature
	 * @return a Java reflection of the method or {@code null} if it was not found
	 */
	public Method findDelegate(String owner, String name, String descriptor) {
		Object delegate = findDelegate(owner);
		if (delegate == null) {
			return null;
		}
		String methodName = name + SymbolicState.getAsciiSignature(descriptor);
		Method delegateMethod = null;
		try {
			delegateMethod = delegate.getClass().getDeclaredMethod(methodName, EMPTY_PARAMETERS);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
		assert delegateMethod != null;
		return delegateMethod;
	}

	/**
	 * Parse the COASTAL configuration and extract the targets, triggers, and
	 * delegates.
	 */
	public void parseConfig() {
		// PARSE TARGETS
		targets.addAll(getConfig().getList(String.class, "coastal.target"));
		// PARSE TRIGGERS
		String[] triggerNames = getConfig().getStringArray("coastal.trigger");
		for (int i = 0; i < triggerNames.length; i++) {
			triggers.add(Trigger.createTrigger(triggerNames[i].trim()));
		}
		// PARSE DELEGATES
//		p = originalProperties.getProperty("coastal.delegates");
//		if (p != null) {
//			String[] delegates = p.trim().split(";");
//			for (String delegate : delegates) {
//				String[] pair = delegate.split(":");
//				Object to = createInstance(pair[1].trim());
//				if (to != null) {
//					addDelegate(pair[0].trim(), to);
//				}
//			}
//		}
		// PARSE BOUNDS
	}

	/**
	 * Return the lower bound for symbolic integer variables with an explicit bound of their own.
	 * 
	 * @return the lower bound for symbolic integers
	 */
	public int getDefaultMinIntValue() {
		return defaultMinIntValue;
	}

	/**
	 * Return the lower bound for the specified symbolic integer variable.
	 * 
	 * @param variable the name of the variable
	 * @return the lower bound for the variable
	 */
	public int getMinBound(String variable) {
		return getMinBound(variable, getDefaultMinIntValue());
	}

	/**
	 * Return the lower bound for a specific variable, or -- if there is no explicit bound -- for another variable. If there is no explicit bound, the specified default value is returned.
	 * 
	 * This is used for array where the specific variable is the array index and the more general variable is the array as a whole.
	 * 
	 * @param variable1 the name of the specific variable 
	 * @param variable2 the name of the more general variable
	 * @return the lower bound for either variable
	 */
	public int getMinBound(String variable1, String variable2) {
		return getMinBound(variable1, getMinBound(variable2, getDefaultMinIntValue()));
	}

	/**
	 * Return the lower bound for the specified symbolic integer variable.  If there is no explicit bound, the specified default value is returned.
	 * 
	 * @param variable the name of the variable
	 * @param defaultValue the default lower bound
	 * @return the lower bound for the variable
	 */
	public int getMinBound(String variable, int defaultValue) {
		Integer min = minBounds.get(variable);
		if (min == null) {
			min = defaultValue;
		}
		return min;
	}

	/**
	 * Return the upper bound for symbolic integer variables with an explicit bound of their own.
	 * 
	 * @return the upper bound for symbolic integers
	 */
	public int getDefaultMaxIntValue() {
		return defaultMaxIntValue;
	}
	
	/**
	 * Return the upper bound for the specified symbolic integer variable.
	 * 
	 * @param variable the name of the variable
	 * @return the upper bound for the variable
	 */
	public int getMaxBound(String variable) {
		return getMaxBound(variable, getDefaultMaxIntValue());
	}

	/**
	 * Return the upper bound for a specific variable, or -- if there is no explicit bound -- for another variable. If there is no explicit bound, the specified default value is returned.
	 * 
	 * This is used for array where the specific variable is the array index and the more general variable is the array as a whole.
	 * 
	 * @param variable1 the name of the specific variable 
	 * @param variable2 the name of the more general variable
	 * @return the upper bound for either variable
	 */
	public int getMaxBound(String variable1, String variable2) {
		return getMaxBound(variable1, getMaxBound(variable2, getDefaultMaxIntValue()));
	}

	/**
	 * Return the upper bound for the specified symbolic integer variable.  If there is no explicit bound, the specified default value is returned.
	 * 
	 * @param variable the name of the variable
	 * @param defaultValue the default upper bound
	 * @return the upper bound for the variable
	 */
	public int getMaxBound(String variable, int defaultValue) {
		Integer max = maxBounds.get(variable);
		if (max == null) {
			max = defaultValue;
		}
		return max;
	}

	/**
	 * Start the analysis run, showing all banners by default.
	 */
	public void start() {
		start(true);
	}

	/**
	 * Start the analysis run, and show banners if and only the parameter flag
	 * is true.
	 * 
	 * @param showBanner
	 *            a flag to tell whether or not to show banners
	 */
	private void start(boolean showBanner) {
		Calendar started = Calendar.getInstance();
		broker.publish("coastal-start", this);
		broker.publish("report", new Tuple("COASTAL.start", started));
		//		final String version = config.getVersion();
		final String version = "XXX";
		if (showBanner) {
			new Banner('~').println("COASTAL version " + version).display(log);
		}
		//		config.dump();
		Diver d = new Diver(this);
		d.dive();
		Calendar stopped = Calendar.getInstance();
		broker.publish("report", new Tuple("COASTAL.stop", stopped));
		broker.publish("report", new Tuple("COASTAL.time", stopped.getTimeInMillis() - started.getTimeInMillis()));
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

	// ======================================================================
	//
	// MAIN FUNCTION
	//
	// ======================================================================

	/**
	 * The main function and entry point for COASTAL.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {
		final Logger log = LogManager.getLogger("COASTAL");
		final String version = new Version().read();
		new Banner('~').println("COASTAL version " + version).display(log);
		ImmutableConfiguration config = loadConfiguration(log, args);
		if (config != null) {
			new COASTAL(log, config).start(false);
		}
		new Banner('~').println("COASTAL DONE").display(log);
	}

	private static final String COASTAL_CONFIGURATION = "coastal.xml";

	private static final String COASTAL_DIRECTORY = ".coastal";

	private static final String HOME_DIRECTORY = System.getProperty("user.home");

	private static final String HOME_COASTAL_DIRECTORY = HOME_DIRECTORY + File.separator + COASTAL_DIRECTORY;

	private static final String HOME_CONFIGURATION = HOME_COASTAL_DIRECTORY + File.separator + COASTAL_CONFIGURATION;

	/**
	 * Load the COASTAL configuration.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param args
	 *            the command-line arguments
	 * @return an immutable configuration
	 */
	private static ImmutableConfiguration loadConfiguration(Logger log, String[] args) {
		// Configurations configs = new Configurations();
		CombinedConfiguration config = new CombinedConfiguration();
		Configuration cfg1 = loadConfigFromResource(log, COASTAL_CONFIGURATION);
		if (cfg1 != null) {
			config.addConfiguration(cfg1);
		}
		Configuration cfg2 = loadConfigFromFile(log, HOME_CONFIGURATION);
		if (cfg2 != null) {
			config.addConfiguration(cfg2);
		}
		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			return null;
		}
		String filename = args[0];
		if (filename.endsWith(".java")) {
			filename = filename.substring(0, filename.length() - 4) + "xml";
		}
		Configuration cfg3 = loadConfigFromFile(log, filename);
		if (cfg3 != null) {
			config.addConfiguration(cfg3);
		} else {
			Banner bn = new Banner('@');
			bn.println("COASTAL PROBLEM\n");
			bn.println("COULD NOT READ CONFIGURATION FILE \"" + filename + "\"");
			bn.display(log);
			return null;
		}
		if (config.getString("coastal.main") == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .xml FILE?");
			bn.display(log);
			return null;
		}
		return ConfigurationUtils.unmodifiableConfiguration(config);
	}

	private static Configuration loadConfigFromFile(Logger log, String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
			Configuration cfg = loadConfigFromStream(log, inputStream);
			log.trace("loaded default configuration from {}", filename);
			return cfg;
		} catch (FileNotFoundException | ConfigurationException x) {
			log.trace("tried to load default configuration from " + filename + " but failed", x);
		}
		return null;
	}

	private static Configuration loadConfigFromResource(Logger log, String resourceName) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(COASTAL_CONFIGURATION)) {
			if (resourceStream != null) {
				Configuration cfg = loadConfigFromStream(log, resourceStream);
				log.trace("loaded default configuration from {}", resourceName);
				return cfg;
			}
		} catch (IOException | ConfigurationException x) {
			log.trace("tried to load default configuration from " + resourceName + " but failed", x);
		}
		return null;
	}
	
	private static Configuration loadConfigFromStream(Logger log, InputStream inputStream) throws ConfigurationException {
		XMLConfiguration cfg = new BasicConfigurationBuilder<>(XMLConfiguration.class).configure(new Parameters().xml()).getConfiguration();
		FileHandler fh = new FileHandler(cfg);
		fh.load(inputStream);
		return cfg;
	}

}
