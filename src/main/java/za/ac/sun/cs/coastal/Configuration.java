package za.ac.sun.cs.coastal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.strategy.Strategy;

public class Configuration {

	// ======================================================================
	//
	// VERSION
	//
	// Reads version from "app.properties" file.  This gives "unspecified"
	// in Eclipse, but works when built with gradle & return the git version.
	//
	// ======================================================================

	public static final String VERSION;

	static {
		String version = "unspecified";
		String resourceName = "app.properties";
		Properties props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
			String v = props.getProperty("version");
			if ((v != null) && (v.charAt(0) != '%')) {
				version = v;
			}
		} catch (IOException x) {
			// ignore
		}
		VERSION = version;
	}

	// ======================================================================
	//
	// LOGGING
	//
	// ======================================================================

	/**
	 * Logger to write to.
	 */
	protected static final Logger LOGGER = LogManager.getLogger("COASTAL");

	public static Logger getLogger() {
		return LOGGER;
	}

	// ======================================================================
	//
	// CONFIGURATION PROPERTIES
	//
	// ======================================================================

	private static final int DEFAULT_MIN_INT_VALUE = 0;

	private static final int DEFAULT_MAX_INT_VALUE = 9999;

	/**
	 * Properties associated with these settings.
	 */
	protected static Properties properties = null;

	/**
	 * The fully qualified of the main class. This class should contain a Java
	 * {@code main} method and it is the class that is run during each dive.
	 */
	protected static String main = null;

	/**
	 * Arguments pass to the target class when it is run during a dive.
	 */
	private static String args = null;

	/**
	 * List of prefixes of classes that must instrumented.
	 */
	protected static final List<String> targets = new ArrayList<>();

	/**
	 * The database of triggers. Each trigger is a method that will switch the
	 * dive to symbolic mode. The trigger also describes which arguments are
	 * treated symbolically, and which arguments stay concrete.
	 */
	private static final List<Trigger> triggers = new ArrayList<>();

	/**
	 * Maps variable names to lower bounds.
	 */
	private static final Map<String, Integer> minBounds = new HashMap<>();

	/**
	 * Maps variable names to upper bounds.
	 */
	private static final Map<String, Integer> maxBounds = new HashMap<>();

	/**
	 * The strategy that directs the investigation of target programs.
	 */
	private static Strategy strategy;

	/**
	 * Cap on the number of runs executed.
	 */
	private static long runLimit = 0;
	
	/**
	 * Cap on the time use.
	 */
	private static long timeLimit = 0;
	
	/**
	 * Cap on the number of paths to explore.
	 */
	private static long pathLimit = 0;
	
	/**
	 * Whether or not the instrumentation trace is dumped.
	 */
	private static boolean dumpInstrumenter = false;

	/**
	 * Whether or not the instrumented code should be dumped to the log.
	 */
	private static boolean dumpAsm = false;
	
	/**
	 * Whether or not instructions are logged as they execute.
	 */
	private static boolean dumpTrace = false;
	
	/**
	 * Whether or not the symbolic frame is printed as instructions execute.
	 */
	private static boolean dumpFrame = false;
	
	/**
	 * Whether or not the settings should be dumped to the log.
	 */
	private static boolean dumpConfig = false;

	/**
	 * Whether or not the output of the target program should be displayed
	 * ({@code true}) or suppressed ({@code false}).
	 */
	private static boolean echoOutput = false;

	// ======================================================================
	//
	// GETTERS AND SETTERS FOR PROPERTIES
	//
	// ======================================================================

	public static String getMain() {
		return main;
	}

	public static void setMain(String main) {
		Configuration.main = main;
	}

	public static String getArgs() {
		return args;
	}

	public static void setArgs(String args) {
		Configuration.args = args;
	}

	public static boolean isTarget(String name) {
		for (String t : targets) {
			if (name.startsWith(t)) {
				return true;
			}
		}
		return false;
	}

	public static void addTarget(String prefix) {
		targets.add(prefix);
	}

	public static Trigger getTrigger(int index) {
		return triggers.get(index);
	}

	public static int isTrigger(String name, String signature) {
		int index = 0;
		for (Trigger t : triggers) {
			if (t.match(name, signature)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public static void addTrigger(Trigger trigger) {
		if (trigger != null) {
			triggers.add(trigger);
		}
	}

	public static int getDefaultMinBound() {
		return DEFAULT_MIN_INT_VALUE;
	}
	
	public static int getMinBound(String variable) {
		return getMinBound(variable, DEFAULT_MIN_INT_VALUE);
	}

	public static int getMinBound(String variable1, String variable2) {
		return getMinBound(variable1, getMinBound(variable2, DEFAULT_MIN_INT_VALUE));
	}
	
	public static int getMinBound(String variable, int defaultValue) {
		Integer min = minBounds.get(variable);
		if (min == null) {
			min = defaultValue;
		}
		return min;
	}

	public static void setMinBound(String variable, int min) {
		minBounds.put(variable, min);
	}

	public static int getDefaultMaxBound() {
		return DEFAULT_MAX_INT_VALUE;
	}
	
	public static int getMaxBound(String variable) {
		return getMaxBound(variable, DEFAULT_MAX_INT_VALUE);
	}

	public static int getMaxBound(String variable1, String variable2) {
		return getMaxBound(variable1, getMaxBound(variable2, DEFAULT_MAX_INT_VALUE));
	}
	
	public static int getMaxBound(String variable, int defaultValue) {
		Integer max = maxBounds.get(variable);
		if (max == null) {
			max = defaultValue;
		}
		return max;
	}

	public static void setMaxBound(String variable, int max) {
		maxBounds.put(variable, max);
	}

	public static Strategy getStrategy() {
		return strategy;
	}

	public static void setStrategy(Strategy strategy) {
		Configuration.strategy = strategy;
	}

	public static long getRunLimit() {
		return runLimit;
	}
	
	public static void setRunLimit(long runLimit) {
		Configuration.runLimit = runLimit;
	}
	
	public static long getTimeLimit() {
		return timeLimit;
	}
	
	public static void setTimeLimit(long timeLimit) {
		Configuration.timeLimit = timeLimit;
	}
	
	public static long getPathLimit() {
		return pathLimit;
	}

	public static void setPathLimit(long pathLimit) {
		Configuration.pathLimit = pathLimit;
	}

	public static boolean getDumpInstrumenter() {
		return dumpInstrumenter;
	}
	
	public static void setDumpInstrumenter(boolean dumpInstrumenter) {
		Configuration.dumpInstrumenter = dumpInstrumenter;
	}
	
	public static boolean getDumpAsm() {
		return dumpAsm;
	}
	
	public static void setDumpAsm(boolean dumpAsm) {
		Configuration.dumpAsm = dumpAsm;
	}
	
	public static boolean getDumpTrace() {
		return dumpTrace;
	}
	
	public static void setDumpTrace(boolean dumpTrace) {
		Configuration.dumpTrace = dumpTrace;
	}
	
	public static boolean getDumpFrame() {
		return dumpFrame;
	}
	
	public static void setDumpFrame(boolean dumpFrame) {
		Configuration.dumpFrame = dumpFrame;
	}
	
	public static boolean getDumpConfig() {
		return dumpConfig;
	}

	public static void setDumpConfig(boolean dumpConfig) {
		Configuration.dumpConfig = dumpConfig;
	}

	public static boolean getEchoOutput() {
		return echoOutput;
	}

	public static void setEchoOutput(boolean echoOutput) {
		Configuration.echoOutput = echoOutput;
	}

	// ======================================================================
	//
	// LOAD AND PARSE CONFIGURATION PROPERTIES
	//
	// ======================================================================

	public static boolean processProperties(String filename) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filename));
		} catch (IOException x) {
			return false;
		}
		return processProperties(properties);
	}

	public static boolean processProperties(Properties properties) {
		LOGGER.debug("Loading configuration...");
		Configuration.properties = properties;

		// Process coastal.main = ...
		String p = properties.getProperty("coastal.main");
		if (p != null) {
			setMain(p);
		}

		// Process coastal.args = ...
		p = properties.getProperty("coastal.args");
		if (p != null) {
			setArgs(p);
		}

		// Process coastal.targets = ...
		p = properties.getProperty("coastal.targets");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTarget(t.trim());
			}
		}

		// Process coastal.triggers = ...
		p = properties.getProperty("coastal.triggers");
		if (p != null) {
			for (String t : p.trim().split(";")) {
				addTrigger(Trigger.createTrigger(t.trim()));
			}
		}

		// Process coastal.bounds = ...
		for (Object key : properties.keySet()) {
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
						LOGGER.warn("BOUNDS IN \"" + k + "\" IS MALFORMED AND IGNORED");
					}
				}
			}
		}

		// Process coastal.limit.run = ...
		setRunLimit(getLongProperty(properties, "coastal.limit.run", getRunLimit()));
		
		// Process coastal.limit.time = ...
		setTimeLimit(getLongProperty(properties, "coastal.limit.time", getTimeLimit()));
		
		// Process coastal.limit.path = ...
		// NOTE: Initialize before coastal.strategy setting
		setPathLimit(getLongProperty(properties, "coastal.limit.path", getPathLimit()));
		
		// Process coastal.strategy = ...
		p = properties.getProperty("coastal.strategy");
		if (p != null) {
			setStrategy((Strategy) createInstance(p));
		}

		// Process coastal.dump
		Boolean dumpAll = getBooleanProperty(properties, "coastal.dump");
		if (dumpAll != null) {
			setDumpInstrumenter(dumpAll);
			setDumpAsm(dumpAll);
			setDumpTrace(dumpAll);
			setDumpFrame(dumpAll);
			setDumpConfig(dumpAll);
		}
		
		// Process coastal.dump.instrumenter = ...
		setDumpInstrumenter(getBooleanProperty(properties, "coastal.dump.instrumenter", getDumpInstrumenter()));

		// Process coastal.dump.asm = ...
		setDumpAsm(getBooleanProperty(properties, "coastal.dump.asm", getDumpAsm()));
		
		// Process coastal.dump.trace = ...
		setDumpTrace(getBooleanProperty(properties, "coastal.dump.trace", getDumpTrace()));
		
		// Process coastal.dump.frame = ...
		setDumpFrame(getBooleanProperty(properties, "coastal.dump.frame", getDumpFrame()));
		
		// Process coastal.dump.config = ...
		setDumpConfig(getBooleanProperty(properties, "coastal.dump.config", getDumpConfig()));

		// Process coastal.echooutput = ...
		setEchoOutput(getBooleanProperty(properties, "coastal.echooutput", getEchoOutput()));

		// All done
		LOGGER.debug("Configuration loaded");
		dump();
		return true;
	}

	public static boolean getBooleanProperty(Properties properties, String key, boolean defaultValue) {
		String s = properties.getProperty(key, Boolean.toString(defaultValue)).trim();
		try {
			if (s.equalsIgnoreCase("true")) {
				return true;
			} else if (s.equalsIgnoreCase("yes")) {
				return true;
			} else if (s.equalsIgnoreCase("on")) {
				return true;
			} else if (s.equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
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
				if (s.equalsIgnoreCase("true")) {
					return true;
				} else if (s.equalsIgnoreCase("yes")) {
					return true;
				} else if (s.equalsIgnoreCase("on")) {
					return true;
				} else if (s.equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
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
	
	private static Object createInstance(String objectName) {
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
				return constructor.newInstance();
			} catch (NoSuchMethodException x) {
				LOGGER.error("CONSTRUCTOR NOT FOUND: " + objectName, x);
			}
		} catch (SecurityException x) {
			LOGGER.error("CONSTRUCTOR SECURITY ERROR: " + objectName, x);
		} catch (IllegalArgumentException x) {
			LOGGER.error("CONSTRUCTOR ARGUMENT ERROR: " + objectName, x);
		} catch (InstantiationException x) {
			LOGGER.error("CONSTRUCTOR INSTANTIATION ERROR: " + objectName, x);
		} catch (IllegalAccessException x) {
			LOGGER.error("CONSTRUCTOR ACCESS ERROR: " + objectName, x);
		} catch (InvocationTargetException x) {
			LOGGER.error("CONSTRUCTOR INVOCATION ERROR: " + objectName, x);
		}
		return null;
	}

	private static Class<?> loadClass(String className) {
		Class<?> clas = null;
		if ((className != null) && (className.length() > 0)) {
			try {
				ClassLoader cl = Configuration.class.getClassLoader();
				clas = cl.loadClass(className);
			} catch (ClassNotFoundException x) {
				LOGGER.error("CLASS NOT FOUND: " + className, x);
			} catch (ExceptionInInitializerError x) {
				LOGGER.error("CLASS NOT FOUND: " + className, x);
			}
		}
		return clas;
	}

	private static void dump() {
		if (!getDumpConfig()) {
			return;
		}
		if (getMain() != null) {
			LOGGER.info("coastal.main = {}", getMain());
		}
		if (getArgs() != null) {
			LOGGER.info("coastal.args = {}", getArgs());
		}
		Strategy s = getStrategy();
		if (s != null) {
			LOGGER.info("coastal.strategy = {}", s.getClass().getName());
		}
		int t = triggers.size(), i = t;
		for (Trigger trigger : triggers) {
			String pre = (i == t) ? "coastal.triggers = " : "\t";
			String post = (i > 1) ? ";\\" : "";
			LOGGER.info("{}{}{}", pre, trigger.toString(), post);
			i--;
		}
		Set<String> vars = new TreeSet<>(minBounds.keySet());
		vars.addAll(maxBounds.keySet());
		for (String var : vars) {
			if (!minBounds.containsKey(var)) {
				LOGGER.info("coastal.bounds.{}.max = {}", var, maxBounds.get(var));
			} else if (!maxBounds.containsKey(var)) {
				LOGGER.info("coastal.bounds.{}.min = {}", var, minBounds.get(var));
			} else {
				LOGGER.info("coastal.bounds.{} = {}..{}", var, minBounds.get(var), maxBounds.get(var));
			}
		}
		LOGGER.info("coastal.echooutput = {}", getEchoOutput());
		LOGGER.info("coastal.limit.run = {}", getRunLimit());
		LOGGER.info("coastal.limit.time = {}", getTimeLimit());
		LOGGER.info("coastal.limit.path = {}", getPathLimit());
		LOGGER.info("coastal.dump.config = {}", getDumpConfig());
		LOGGER.info("coastal.dump.asm = {}", getDumpAsm());
		LOGGER.info("coastal.dump.trace = {}", getDumpTrace());
		LOGGER.info("coastal.dump.frame = {}", getDumpFrame());
		LOGGER.info("coastal.dump.instrumenter = {}", getDumpInstrumenter());
	}

	// // --- DELEGATES ---
	// int d = getNumberOfDelegateTargets(), j = d;
	// for (String target : getDelegateTargets()) {
	// Object delegate = findDelegate(target);
	// String pre = (j == d) ? "deepsea.delegate = " : "\t";
	// String post = (j > 1) ? ";\\" : "";
	// LOGGER.log(CONF, "{}{}:{}{}", pre, target, delegate.getClass().getName(),
	// post);
	// j--;
	// }

	// ======================================================================
	//
	// TRIGGER METHODS
	//
	// ======================================================================

	public static class Trigger {

		public static Trigger createTrigger(String desc) {
			final Set<String> names = new HashSet<>();
			int s = desc.indexOf('('), e = desc.indexOf(')', s);
			assert s != -1;
			assert e != -1;
			String m = desc.substring(0, s).trim();
			String ps = desc.substring(s + 1, e).trim();
			int n = 0;
			String[] pn;
			Class<?>[] pt;
			if (ps.length() > 0) {
				String[] pz = ps.split(",");
				n = pz.length;
				pn = new String[n];
				pt = new Class<?>[n];
				for (int i = 0; i < n; i++) {
					String p = pz[i].trim();
					int c = p.indexOf(':');
					if (c != -1) {
						pn[i] = p.substring(0, c).trim();
						if (names.contains(pn[i])) {
							new Banner('@').println("COASTAL PROBLEM\n")
									.println("IGNORED TRIGGER WITH DUPLICATES \"" + desc + "\"").display(System.out);
							return null;
						}
						names.add(pn[i]);
					}
					pt[i] = parseType(p.substring(c + 1).trim());
				}
			} else {
				pn = new String[0];
				pt = new Class<?>[0];
			}
			if (names.size() == 0) {
				new Banner('@').println("COASTAL PROBLEM\n").println("IGNORED NON-SYMBOLIC TRIGGER \"" + desc + "\"")
						.display(System.out);
				return null;
			}
			return new Trigger(m, pn, pt);
		}

		private static Class<?> parseType(String type) {
			int i = type.indexOf('[');
			if (i > -1) {
				String arrayType = type.substring(0, i);
				if (arrayType.equals("int")) {
					return int[].class;
				} else {
					return Object[].class;
				}
			} else if (type.equals("int")) {
				return int.class;
			} else {
				return Object.class;
			}
		}

		private final String methodName;

		private final String[] paramNames;

		private final Class<?>[] paramTypes;

		private final String signature;

		private String stringRepr = null;

		public Trigger(String methodName, String[] paramNames, Class<?>[] paramTypes) {
			this.methodName = methodName;
			assert paramNames.length == paramTypes.length;
			this.paramNames = paramNames;
			this.paramTypes = paramTypes;
			StringBuilder sb = new StringBuilder().append('(');
			for (Class<?> c : paramTypes) {
				sb.append(Type.getDescriptor(c));
			}
			signature = sb.append(')').toString();
		}

		public boolean match(String methodName, String signature) {
			return methodName.equals(this.methodName) && signature.startsWith(this.signature);
		}

		public int getParamCount() {
			return paramNames.length;
		}

		public String getParamName(int index) {
			return paramNames[index];
		}

		public Class<?> getParamType(int index) {
			return paramTypes[index];
		}

		@Override
		public String toString() {
			if (stringRepr == null) {
				StringBuilder sb = new StringBuilder();
				sb.append(methodName).append('(');
				for (int i = 0; i < paramNames.length; i++) {
					if (i > 0) {
						sb.append(", ");
					}
					if (paramNames[i] != null) {
						sb.append(paramNames[i]).append(": ");
					}
					if (paramTypes[i] == null) {
						sb.append('?');
					} else if (paramTypes[i] == boolean.class) {
						sb.append("boolean");
					} else if (paramTypes[i] == int.class) {
						sb.append("int");
					} else if (paramTypes[i] == String.class) {
						sb.append("string");
					} else if (paramTypes[i] == int[].class) {
						sb.append("int[]");
					} // int[5]? int[4]?...
					else {
						sb.append('*');
					}
				}
				stringRepr = sb.append(')').toString();
			}
			return stringRepr;
		}

	}

}
