package za.ac.sun.cs.coastal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

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
	private long threadsDiver = 1; // number of Diver threads
	private long threadsStrategy = 1; // number of Strategy threads
	private boolean traceAll; // should all everything be traced?
	private boolean echoOutput = false; // is main program output displayed
	private boolean drawPaths = false; // display path trees in detailed log
	private boolean useConcreteValues = true; // use actual values as returns from functions
	private final List<Listener> listeners = new ArrayList<>(); // listeners for various events
	private final Properties originalProperties = new Properties(); // non-coastal properties

	public ConfigurationBuilder(final Logger log, final String version, final ReporterManager reporterManager) {
		assert log != null;
		this.log = log;
		assert version != null;
		this.version = version;
		assert reporterManager != null;
		this.reporterManager = reporterManager;
		registerListener(reporterManager);
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

	public ConfigurationBuilder setThreadsDiver(long threadsDiver) {
		this.threadsDiver = threadsDiver;
		return this;
	}
	
	public ConfigurationBuilder setThreadsStrategy(long threadsStrategy) {
		this.threadsStrategy = threadsStrategy;
		return this;
	}
	
	public ConfigurationBuilder setTraceAll(boolean traceAll) {
		this.traceAll = traceAll;
		return this;
	}

	public ConfigurationBuilder setEchoOutput(boolean echoOutput) {
		this.echoOutput = echoOutput;
		return this;
	}

	public ConfigurationBuilder setDrawPaths(boolean drawPaths) {
		this.drawPaths = drawPaths;
		return this;
	}

	public ConfigurationBuilder setUseConcreteValues(boolean useConcreteValues) {
		this.useConcreteValues = useConcreteValues;
		return this;
	}

	public ConfigurationBuilder addListener(Listener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
		return this;
	}

	public Configuration construct() {
		processProperties();
		return new Configuration(version, log, reporterManager, main, args, targets, triggers, minBounds, maxBounds,
				delegates, strategy, limitRuns, limitTime, limitPaths, limitConjuncts, threadsDiver, threadsStrategy,
				traceAll, echoOutput, drawPaths, useConcreteValues, listeners, configurationListeners,
				originalProperties);
	}

	// ======================================================================
	//
	// READ FILE/STREAM/PROPERTIES
	//
	// ======================================================================

	public boolean read(String filename) {
		boolean a = readFromResource(COASTAL_PROPERTY_FILE);
		boolean b = readFromFile(HOME_PROPERTY_FILE);
		boolean c = readFromFile(filename);
		return a || b || c;
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
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
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
		return true;
	}

	private boolean processProperties() {
		setMain(originalProperties.getProperty("coastal.main"));
		setArgs(originalProperties.getProperty("coastal.args"));
		String p = originalProperties.getProperty("coastal.targets");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTarget(t.trim());
			}
		}
		p = originalProperties.getProperty("coastal.triggers");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTrigger(Trigger.createTrigger(t.trim()));
			}
		}
		for (Object key : originalProperties.keySet()) {
			if (!(key instanceof String)) {
				continue;
			}
			String k = (String) key;
			if (k.startsWith("coastal.bounds.")) {
				String var = k.substring("coastal.bounds.".length());
				if (var.endsWith(".min")) {
					Integer min = getIntegerProperty(originalProperties, k);
					if (min != null) {
						setMinBound(var.substring(0, var.length() - 4), min);
					}
				} else if (var.endsWith(".max")) {
					Integer max = getIntegerProperty(originalProperties, k);
					if (max != null) {
						setMaxBound(var.substring(0, var.length() - 4), max);
					}
				} else {
					String[] bounds = originalProperties.getProperty(k).split("\\.\\.");
					assert bounds.length >= 2;
					try {
						setMinBound(var, Integer.parseInt(bounds[0].trim()));
						setMaxBound(var, Integer.parseInt(bounds[1].trim()));
					} catch (NumberFormatException x) {
						log.warn("BOUNDS IN \"{}\" IS MALFORMED AND IGNORED", k);
					}
				}
			}
		}
		setLimitRuns(getLongProperty(originalProperties, "coastal.limit.runs", limitRuns));
		setLimitTime(getLongProperty(originalProperties, "coastal.limit.time", limitTime));
		setLimitPaths(getLongProperty(originalProperties, "coastal.limit.paths", limitPaths));
		setLimitConjuncts(getLongProperty(originalProperties, "coastal.limit.conjuncts", limitConjuncts));
		setThreadsDiver(getLongProperty(originalProperties, "coastal.threads.diver", threadsDiver));
		setThreadsStrategy(getLongProperty(originalProperties, "coastal.threads.strategy", threadsStrategy));
		setTraceAll(getBooleanProperty(originalProperties, "coastal.trace.all", traceAll));
		setEchoOutput(getBooleanProperty(originalProperties, "coastal.echooutput", echoOutput));
		setDrawPaths(getBooleanProperty(originalProperties, "coastal.draw.paths", drawPaths));
		setUseConcreteValues(getBooleanProperty(originalProperties, "coastal.concrete.values", useConcreteValues));
		p = originalProperties.getProperty("coastal.strategy");
		if (p != null) {
			Object str = createInstance(p);
			if (str instanceof Strategy) {
				setStrategy((Strategy) str);
			} else {
				log.warn("CLASS \"{}\" IS NOT A STRATEGY, IGNORED", p);
			}
		}
		p = originalProperties.getProperty("coastal.delegates");
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
		p = originalProperties.getProperty("coastal.listeners");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				Object lst = createInstance(t.trim());
				if (lst instanceof Listener) {
					addListener((Listener) lst);
				} else {
					log.warn("CLASS \"{}\" IS NOT A LISTENER, IGNORED", t);
				}
			}
		}
		return true;
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
				log.info("CONSTRUCTOR NOT FOUND: " + objectName, x);
			}
		} catch (SecurityException x) {
			log.warn("CONSTRUCTOR SECURITY ERROR: " + objectName, x);
		} catch (IllegalArgumentException x) {
			log.warn("CONSTRUCTOR ARGUMENT ERROR: " + objectName, x);
		} catch (InstantiationException x) {
			log.warn("CONSTRUCTOR INSTANTIATION ERROR: " + objectName, x);
		} catch (IllegalAccessException x) {
			log.warn("CONSTRUCTOR ACCESS ERROR: " + objectName, x);
		} catch (InvocationTargetException x) {
			log.warn("CONSTRUCTOR INVOCATION ERROR: " + objectName, x);
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
				log.warn("CLASS NOT FOUND: " + className, x);
			} catch (ExceptionInInitializerError x) {
				log.warn("CLASS NOT FOUND: " + className, x);
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
