package za.ac.sun.cs.coastal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.Message;

import za.ac.sun.cs.coastal.Configuration.Trigger;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.Listener;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.strategy.Strategy;

public class ConfigurationBuilder {

	private static final String COASTAL_PROPERTY_FILE = "coastal.properties";

	private static final String COASTAL_DIRECTORY = ".coastal";

	private static final String HOME_DIRECTORY = System.getProperty("user.home");

	private static final String HOME_COASTAL_DIRECTORY = HOME_DIRECTORY + File.separator + COASTAL_DIRECTORY;

	private static final String HOME_PROPERTY_FILE = HOME_COASTAL_DIRECTORY + File.separator + COASTAL_PROPERTY_FILE;

	private final String version; // version of COASTAL
	private final Logger log; // logger
	private final ReporterManager reporterManager; // manager for reporters
	private String main = null; // fully qualified of the main class
	private String args = null; // arguments for the main class
	private final List<String> targets = new ArrayList<>(); // prefix/classes to instrument
	private final List<Trigger> triggers = new ArrayList<>(); // methods that switch on symbolic mode
	private final Map<String, Integer> minBounds = new HashMap<>(); // map: variable name -> lower bound
	private final Map<String, Integer> maxBounds = new HashMap<>(); // map: variable name -> upper bound
	private final Map<String, Object> delegates = new HashMap<>(); // map: method -> delegate
	private Strategy strategy = null; // strategy to refine paths
	private long limitRuns = 0; // cap on the number of runs executed
	private long limitTime = 0; // cap on the time use
	private long limitPaths = 0; // cap on the number of paths to explore
	private long limitConjuncts = 0; // cap on the number of conjuncts per path condition
	private boolean echoOutput = false; // is main program output displayed
	private boolean obeyStops = false; // are symbolic termination calls obeyed
	private boolean recordMarks = false; // are symbolic markers are recorded
	private boolean dumpInstrumenter = false; // dump instrumentation info?
	private boolean dumpAsm = false; // dump instrumented code at end?
	private boolean dumpTrace = false; // dump instructions when executed?
	private boolean dumpFrame = false; // dump symbolic frames?
	private boolean dumpPaths = false; // dump path tree after each dive?
	private boolean dumpConfig = false; // dump configuration before runs?
	private boolean dumpAll = false; // dump everything?
	private final List<Listener> listeners = new ArrayList<>(); // listeners for various events
	private final Properties originalProperties = new Properties(); // non-coastal properties

	public ConfigurationBuilder(final Logger log, final String version, final ReporterManager reporterManager) {
		assert log != null;
		this.log = log;
		processPendingLogMessages();
		assert version != null;
		this.version = version;
		assert reporterManager != null;
		this.reporterManager = reporterManager;
		registerListener(reporterManager);
	}

	public boolean isMainSet() {
		return (main != null);
	}

	public ConfigurationBuilder setMain(String main) {
		if (main != null) {
			this.main = main;
		}
		return this;
	}

	public ConfigurationBuilder setArgs(String args) {
		if (args != null) {
			this.args = args;
		}
		return this;
	}

	public ConfigurationBuilder addTarget(String prefix) {
		if (prefix != null) {
			targets.add(prefix);
		}
		return this;
	}

	public ConfigurationBuilder addTrigger(Trigger trigger) {
		if (trigger != null) {
			triggers.add(trigger);
		}
		return this;
	}

	public ConfigurationBuilder setMinBound(String variable, int min) {
		minBounds.put(variable, min);
		return this;
	}

	public ConfigurationBuilder setMaxBound(String variable, int max) {
		maxBounds.put(variable, max);
		return this;
	}

	public ConfigurationBuilder addDelegate(String target, Object delegate) {
		delegates.put(target, delegate);
		return this;
	}

	public ConfigurationBuilder setStrategy(Strategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public ConfigurationBuilder setLimitRuns(long limitRuns) {
		this.limitRuns = limitRuns;
		return this;
	}

	public ConfigurationBuilder setLimitTime(long limitTime) {
		this.limitTime = limitTime;
		return this;
	}

	public ConfigurationBuilder setLimitPaths(long limitPaths) {
		this.limitPaths = limitPaths;
		return this;
	}

	public ConfigurationBuilder setLimitConjuncts(long limitConjuncts) {
		this.limitConjuncts = limitConjuncts;
		return this;
	}

	public ConfigurationBuilder setEchoOutput(boolean echoOutput) {
		this.echoOutput = echoOutput;
		return this;
	}

	public ConfigurationBuilder setObeyStops(boolean obeyStops) {
		this.obeyStops = obeyStops;
		return this;
	}

	public ConfigurationBuilder setRecordMarks(boolean recordMarks) {
		this.recordMarks = recordMarks;
		return this;
	}

	public ConfigurationBuilder setDumpInstrumenter(boolean dumpInstrumenter) {
		this.dumpInstrumenter = dumpInstrumenter;
		return this;
	}

	public ConfigurationBuilder setDumpAsm(boolean dumpAsm) {
		this.dumpAsm = dumpAsm;
		return this;
	}

	public ConfigurationBuilder setDumpTrace(boolean dumpTrace) {
		this.dumpTrace = dumpTrace;
		return this;
	}

	public ConfigurationBuilder setDumpFrame(boolean dumpFrame) {
		this.dumpFrame = dumpFrame;
		return this;
	}

	public ConfigurationBuilder setDumpPaths(boolean dumpPaths) {
		this.dumpPaths = dumpPaths;
		return this;
	}

	public ConfigurationBuilder setDumpConfig(boolean dumpConfig) {
		this.dumpConfig = dumpConfig;
		return this;
	}

	public ConfigurationBuilder setDumpAll(boolean dumpAll) {
		this.dumpAll = dumpAll;
		return this;
	}

	public ConfigurationBuilder addListener(Listener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
		return this;
	}

	public Configuration construct() {
		return new Configuration(version, log, reporterManager, main, args, targets, triggers, minBounds, maxBounds, delegates, strategy,
				limitRuns, limitTime, limitPaths, limitConjuncts, echoOutput, obeyStops, recordMarks, dumpInstrumenter,
				dumpAsm, dumpTrace, dumpFrame, dumpPaths, dumpConfig, dumpAll, listeners, configurationListeners,
				originalProperties);
	}

	// ======================================================================
	//
	// READ FILE/STREAM/PROPERTIES
	//
	// ======================================================================

	public boolean read(String filename) {
		return readFromResource(COASTAL_PROPERTY_FILE) && readFromFile(HOME_PROPERTY_FILE) && readFromFile(filename);
	}

	public boolean readFromFile(String filename) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filename));
		} catch (IOException x) {
			return false;
		}
		return readFromProperties(properties);
	}

	public boolean readFromResource(String resourceName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			if (resourceStream != null) {
				return readFromStream(resourceStream);
			}
		} catch (IOException x) {
			// ignore
		}
		return false;
	}

	public boolean readFromStream(InputStream inputStream) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException x) {
			return false;
		}
		return readFromProperties(properties);
	}

	public boolean readFromProperties(Properties properties) {
		originalProperties.putAll(properties);
		setMain(properties.getProperty("coastal.main"));
		setArgs(properties.getProperty("coastal.args"));
		String p = properties.getProperty("coastal.targets");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTarget(t.trim());
			}
		}
		p = properties.getProperty("coastal.triggers");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTrigger(Trigger.createTrigger(t.trim()));
			}
		}
		for (Object key : properties.keySet()) {
			if (!(key instanceof String)) {
				continue;
			}
			String k = (String) key;
			if (k.startsWith("coastal.bounds.")) {
				String var = k.substring("coastal.bounds.".length());
				if (var.endsWith(".min")) {
					Integer min = getIntegerProperty(properties, k);
					if (min != null) {
						setMinBound(var.substring(0, var.length() - 4), min);
					}
				} else if (var.endsWith(".max")) {
					Integer max = getIntegerProperty(properties, k);
					if (max != null) {
						setMaxBound(var.substring(0, var.length() - 4), max);
					}
				} else {
					String[] bounds = properties.getProperty(k).split("\\.\\.");
					assert bounds.length >= 2;
					try {
						setMinBound(var, Integer.parseInt(bounds[0].trim()));
						setMaxBound(var, Integer.parseInt(bounds[1].trim()));
					} catch (NumberFormatException x) {
						report(new FormattedMessage("BOUNDS IN \"{}\" IS MALFORMED AND IGNORED", k));
					}
				}
			}
		}
		setLimitRuns(getLongProperty(properties, "coastal.limit.runs", limitRuns));
		setLimitTime(getLongProperty(properties, "coastal.limit.time", limitTime));
		setLimitPaths(getLongProperty(properties, "coastal.limit.paths", limitPaths));
		setLimitConjuncts(getLongProperty(properties, "coastal.limit.conjuncts", limitConjuncts));
		setDumpAll(getBooleanProperty(properties, "coastal.dump", dumpAll));
		setDumpInstrumenter(getBooleanProperty(properties, "coastal.dump.instrumenter", dumpInstrumenter));
		setDumpAsm(getBooleanProperty(properties, "coastal.dump.asm", dumpAsm));
		setDumpTrace(getBooleanProperty(properties, "coastal.dump.trace", dumpTrace));
		setDumpFrame(getBooleanProperty(properties, "coastal.dump.frame", dumpFrame));
		setDumpPaths(getBooleanProperty(properties, "coastal.dump.paths", dumpPaths));
		setDumpConfig(getBooleanProperty(properties, "coastal.dump.config", dumpConfig));
		setEchoOutput(getBooleanProperty(properties, "coastal.echooutput", echoOutput));
		setObeyStops(getBooleanProperty(properties, "coastal.obeystops", obeyStops));
		setRecordMarks(getBooleanProperty(properties, "coastal.recordmarks", recordMarks));
		p = properties.getProperty("coastal.strategy");
		if (p != null) {
			Object str = createInstance(p);
			if (str instanceof Strategy) {
				setStrategy((Strategy) str);
			} else {
				report(new FormattedMessage("CLASS \"{}\" IS NOT A STRATEGY, IGNORED", p));
			}
		}
		p = properties.getProperty("coastal.delegates");
		if (p != null) {
			String[] delegates = p.trim().split(";");
			for (String delegate : delegates) {
				String[] pair = delegate.split(":");
				Object to = createInstance(pair[1].trim());
				if (to != null) {
					addDelegate(pair[0].trim(), to);
				}
			}
		}
		p = properties.getProperty("coastal.listeners");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				Object lst = createInstance(t.trim());
				if (lst instanceof Listener) {
					addListener((Listener) lst);
				} else {
					report(new FormattedMessage("CLASS \"{}\" IS NOT A LISTENER, IGNORED", t));
				}
			}
		}
		return true;
	}

	// ======================================================================
	//
	// PRE-LOGGING
	//
	// ======================================================================

	private final Queue<Message> pendingLogMessages = new LinkedList<>();

	private void report(Message message) {
		if (log != null) {
			log.warn(message);
		} else {
			pendingLogMessages.add(message);
		}
	}

	private void processPendingLogMessages() {
		if (log != null) {
			while (!pendingLogMessages.isEmpty()) {
				log.warn(pendingLogMessages.poll());
			}
		}
	}

	// ======================================================================
	//
	// CONFIGURATION LISTENERS
	//
	// ======================================================================

	private final List<ConfigurationListener> configurationListeners = new ArrayList<>();

	public void registerListener(ConfigurationListener listener) {
		configurationListeners.add(listener);
	}

	// ======================================================================
	//
	// CREATE OBJECT INSTANCES
	//
	// ======================================================================

	private Object createInstance(String objectName) {
		Class<?> classx = loadClass(objectName);
		if (classx == null) {
			return null;
		}
		try {
			Constructor<?> constructor = null;
			try {
				constructor = classx.getConstructor();
				if (constructor == null) {
					return null;
				}
				Object instance = constructor.newInstance();
				if (instance instanceof ConfigurationListener) {
					registerListener((ConfigurationListener) instance);
				}
				return instance;
			} catch (NoSuchMethodException x) {
				report(new FormattedMessage("CONSTRUCTOR NOT FOUND: " + objectName, x));
			}
		} catch (SecurityException x) {
			report(new FormattedMessage("CONSTRUCTOR SECURITY ERROR: " + objectName, x));
		} catch (IllegalArgumentException x) {
			report(new FormattedMessage("CONSTRUCTOR ARGUMENT ERROR: " + objectName, x));
		} catch (InstantiationException x) {
			report(new FormattedMessage("CONSTRUCTOR INSTANTIATION ERROR: " + objectName, x));
		} catch (IllegalAccessException x) {
			report(new FormattedMessage("CONSTRUCTOR ACCESS ERROR: " + objectName, x));
		} catch (InvocationTargetException x) {
			report(new FormattedMessage("CONSTRUCTOR INVOCATION ERROR: " + objectName, x));
		}
		return null;
	}

	private Class<?> loadClass(String className) {
		Class<?> clas = null;
		if ((className != null) && (className.length() > 0)) {
			try {
				ClassLoader cl = ConfigurationBuilder.class.getClassLoader();
				clas = cl.loadClass(className);
			} catch (ClassNotFoundException x) {
				report(new FormattedMessage("CLASS NOT FOUND: " + className, x));
			} catch (ExceptionInInitializerError x) {
				report(new FormattedMessage("CLASS NOT FOUND: " + className, x));
			}
		}
		return clas;
	}

	// ======================================================================
	//
	// LOAD CONFIGURATION PROPERTIES
	//
	// ======================================================================

	public static boolean getBooleanProperty(Properties properties, String key, boolean defaultValue) {
		String s = properties.getProperty(key, Boolean.toString(defaultValue)).trim();
		try {
			return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")
					|| s.equalsIgnoreCase("1");
		} catch (NumberFormatException x) {
			// ignore
		}
		return defaultValue;
	}

	public static Boolean getBooleanProperty(Properties properties, String key) {
		String s = properties.getProperty(key);
		if (s != null) {
			s = s.trim();
			try {
				return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")
						|| s.equalsIgnoreCase("1");
			} catch (NumberFormatException x) {
				// ignore
			}
		}
		return null;
	}

	public static int getIntegerProperty(Properties properties, String key, int defaultValue) {
		String s = properties.getProperty(key, Integer.toString(defaultValue));
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException x) {
			// ignore
		}
		return defaultValue;
	}

	public static Integer getIntegerProperty(Properties properties, String key) {
		String s = properties.getProperty(key);
		if (s != null) {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException x) {
				// ignore
			}
		}
		return null;
	}

	public static long getLongProperty(Properties properties, String key, long defaultValue) {
		String s = properties.getProperty(key, Long.toString(defaultValue));
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException x) {
			// ignore
		}
		return defaultValue;
	}

	public static Long getLongProperty(Properties properties, String key) {
		String s = properties.getProperty(key);
		if (s != null) {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException x) {
				// ignore
			}
		}
		return null;
	}

}
