package za.ac.sun.cs.coastal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.listener.Listener;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.ReporterManager;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class Configuration {

	// TODO these should also be properties!
	private static final int DEFAULT_MIN_INT_VALUE = 0;
	private static final int DEFAULT_MAX_INT_VALUE = 9999;

	// ======================================================================
	//
	// CONFIGURATION PROPERTIES
	//
	// ======================================================================

	private final String version; // version of COASTAL
	private final Logger log; // logger
	private final ReporterManager reporterManager; // manager for reporters
	private final String main; // fully qualified of the main class
	private final String args; // arguments for the main class
	private final List<String> targets; // prefix/classes to instrument
	private final List<Trigger> triggers; // methods that switch on symbolic mode
	private final Map<String, Integer> minBounds; // map: variable name -> lower bound
	private final Map<String, Integer> maxBounds; // map: variable name -> upper bound
	private final Map<String, Object> delegates; // map: method -> delegate
	private final Strategy strategy; // strategy to refine paths
	private final long limitRuns; // cap on the number of runs executed
	private final long limitTime; // cap on the time use
	private final long limitPaths; // cap on the number of paths to explore
	private final long limitConjuncts; // cap on the number of conjuncts per path condition
	private final boolean traceAll; // should all everything be traced?
	private final boolean echoOutput; // is main program output displayed
	private final boolean drawPaths; // is main program output displayed
	private final boolean useConcreteValues; // use actual values as returns from functions
	private final List<Listener> listeners; // listeners for various events
	private final Properties originalProperties; // original properties

	private final List<ConfigurationListener> configurationListeners;
	private final Properties properties;

	// ======================================================================
	//
	// CONSTRUCTOR
	//
	// ======================================================================

	public Configuration(final String version, final Logger log, final ReporterManager reporterManager,
			final String main, final String args, final List<String> targets, final List<Trigger> triggers,
			final Map<String, Integer> minBounds, final Map<String, Integer> maxBounds,
			final Map<String, Object> delegates, final Strategy strategy, final long limitRuns, final long limitTime,
			final long limitPaths, final long limitConjuncts, final boolean traceAll, final boolean echoOutput,
			final boolean drawPaths, final boolean useConcreteValues, final List<Listener> listeners,
			final List<ConfigurationListener> configurationListeners, final Properties originalProperties) {
		this.version = version;
		this.log = log;
		this.reporterManager = reporterManager;
		this.main = main;
		this.args = args;
		this.targets = new ArrayList<>(targets);
		this.triggers = new ArrayList<>(triggers);
		this.minBounds = new HashMap<>(minBounds);
		this.maxBounds = new HashMap<>(maxBounds);
		this.delegates = new HashMap<>(delegates);
		this.strategy = strategy;
		this.limitRuns = limitRuns;
		this.limitTime = limitTime;
		this.limitPaths = limitPaths;
		this.limitConjuncts = limitConjuncts;
		this.traceAll = traceAll;
		this.echoOutput = echoOutput;
		this.drawPaths = drawPaths;
		this.useConcreteValues = useConcreteValues;
		this.listeners = new ArrayList<>(listeners);
		this.originalProperties = new Properties(originalProperties);

		this.configurationListeners = new ArrayList<>(configurationListeners);
		// now notify all configuration listeners:
		for (ConfigurationListener listener : configurationListeners) {
			listener.configurationLoaded(this);
		}
		properties = createProperties();
	}

	// ======================================================================
	//
	// GETTERS
	//
	// ======================================================================

	public String getVersion() {
		return version;
	}

	public Logger getLog() {
		return log;
	}

	public ReporterManager getReporterManager() {
		return reporterManager;
	}

	public String getMain() {
		return main;
	}

	public String getArgs() {
		return args;
	}

	public boolean isTarget(String name) {
		for (String t : targets) {
			if (name.startsWith(t)) {
				return true;
			}
		}
		return false;
	}

	public Trigger getTrigger(int index) {
		return triggers.get(index);
	}

	public int isTrigger(String name, String signature) {
		int index = 0;
		for (Trigger t : triggers) {
			if (t.match(name, signature)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int getDefaultMinIntValue() {
		return DEFAULT_MIN_INT_VALUE;
	}

	public int getMinBound(String variable) {
		return getMinBound(variable, getDefaultMinIntValue());
	}

	public int getMinBound(String variable1, String variable2) {
		return getMinBound(variable1, getMinBound(variable2, getDefaultMinIntValue()));
	}

	public int getMinBound(String variable, int defaultValue) {
		Integer min = minBounds.get(variable);
		if (min == null) {
			min = defaultValue;
		}
		return min;
	}

	public int getDefaultMaxIntValue() {
		return DEFAULT_MAX_INT_VALUE;
	}

	public int getMaxBound(String variable) {
		return getMaxBound(variable, getDefaultMaxIntValue());
	}

	public int getMaxBound(String variable1, String variable2) {
		return getMaxBound(variable1, getMinBound(variable2, getDefaultMaxIntValue()));
	}

	public int getMaxBound(String variable, int defaultValue) {
		Integer max = maxBounds.get(variable);
		if (max == null) {
			max = defaultValue;
		}
		return max;
	}

	public Object findDelegate(String target) {
		return delegates.get(target);
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public long getLimitRuns() {
		return limitRuns;
	}

	public long getLimitTime() {
		return limitTime;
	}

	public long getLimitPaths() {
		return limitPaths;
	}

	public long getLimitConjuncts() {
		return limitConjuncts;
	}

	public boolean getTraceAll() {
		return traceAll;
	}

	public boolean getEchoOutput() {
		return echoOutput;
	}

	public boolean getDrawPaths() {
		return drawPaths;
	}

	public boolean getUseConcreteValues() {
		return useConcreteValues;
	}
	
	@SuppressWarnings("unchecked")
	public <L extends Listener> List<L> getListeners(Class<L> clas) {
		List<L> result = new ArrayList<>();
		for (Listener listener : listeners) {
			if (clas.isInstance(listener)) {
				result.add((L) listener);
			}
		}
		return result;
	}

	//	/**
	//	 * List of instruction listeners to load
	//	 */
	//	private static final List<Listener> listeners = new ArrayList<>();

	public Properties getOriginalProperties() {
		return new Properties(originalProperties);
	}

	public boolean getBooleanProperty(String key, boolean defaultValue) {
		return ConfigurationBuilder.getBooleanProperty(originalProperties, key, defaultValue);
	}

	public Boolean getBooleanProperty(String key) {
		return ConfigurationBuilder.getBooleanProperty(originalProperties, key);
	}

	public int getIntegerProperty(String key, int defaultValue) {
		return ConfigurationBuilder.getIntegerProperty(originalProperties, key, defaultValue);
	}

	public Integer getIntegerProperty(String key) {
		return ConfigurationBuilder.getIntegerProperty(originalProperties, key);
	}

	public long getLongProperty(String key, long defaultValue) {
		return ConfigurationBuilder.getLongProperty(originalProperties, key, defaultValue);
	}

	public Long getLongProperty(String key) {
		return ConfigurationBuilder.getLongProperty(originalProperties, key);
	}

	// ======================================================================
	//
	// CONVERT TO PROPERTIES
	//
	// ======================================================================
	
	private static final Class<?>[] EMPTY_PARAMETERS = new Class<?>[0];

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

	// ======================================================================
	//
	// CONVERT TO PROPERTIES
	//
	// ======================================================================

	private Properties createProperties() {
		Properties properties = new Properties();
		if (main != null) {
			properties.put("coastal.main", main);
		}
		if (args != null) {
			properties.put("coastal.args", args);
		}
		if (!targets.isEmpty()) {
			properties.put("coastal.targets", String.join(";", targets));
		}
		if (!triggers.isEmpty()) {
			String t = triggers.stream().map(x -> x.toString()).collect(Collectors.joining(";"));
			properties.put("coastal.triggers", t);
		}
		Set<String> vars = new TreeSet<>(minBounds.keySet());
		vars.addAll(maxBounds.keySet());
		for (String var : vars) {
			if (!minBounds.containsKey(var)) {
				properties.put("coastal.bounds." + var + ".max", maxBounds.get(var));
			} else if (!maxBounds.containsKey(var)) {
				properties.put("coastal.bounds." + var + ".min", minBounds.get(var));
			} else {
				properties.put("coastal.bounds." + var, minBounds.get(var) + " .. " + maxBounds.get(var));
			}
		}
		if (!delegates.isEmpty()) {
			String d = delegates.entrySet().stream().map(x -> x.getKey().toString() + ":" + x.getValue().toString())
					.collect(Collectors.joining(";"));
			properties.put("coastal.delegates", d);
		}
		if (strategy != null) {
			properties.put("coastal.strategy", strategy.getClass().getName());
		}
		properties.put("coastal.limit.runs", limitRuns);
		properties.put("coastal.limit.time", limitTime);
		properties.put("coastal.limit.paths", limitPaths);
		properties.put("coastal.limit.conjuncts", limitConjuncts);
		properties.put("coastal.trace.all", traceAll);
		properties.put("coastal.echooutput", echoOutput);
		properties.put("coastal.draw.paths", drawPaths);
		properties.put("coastal.concrete.values", useConcreteValues);
		if (!listeners.isEmpty()) {
			String l = listeners.stream().map(x -> x.getClass().getName()).collect(Collectors.joining(";"));
			properties.put("coastal.triggers", l);
		}
		// Collect additional properties from configuration listeners.
		// We expect these properties to start with "coastal.".
		for (ConfigurationListener listener : configurationListeners) {
			Properties subProperties = new Properties();
			listener.collectProperties(subProperties);
			properties.putAll(subProperties);
		}
		return properties;
	}

	public Properties toProperties() {
		return properties;
	}

	public void dump() {
		Properties properties = toProperties();
		SortedSet<String> keys = new TreeSet<>();
		for (Object key : properties.keySet()) {
			keys.add((String) key);
		}
		for (Object key : keys) {
			log.trace("{} = {}", key, properties.get(key));
		}
	}

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
							Banner bn = new Banner('@');
							bn.println("COASTAL PROBLEM\n");
							bn.println("IGNORED TRIGGER WITH DUPLICATES \"" + desc + "\"");
							bn.display(System.out);
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
				Banner bn = new Banner('@');
				bn.println("COASTAL PROBLEM\n");
				bn.println("IGNORED NON-SYMBOLIC TRIGGER \"" + desc + "\"");
				bn.display(System.out);
				return null;
			}
			return new Trigger(m, pn, pt);
		}

		// To implement a new type:
		// (1) Add it here
		// (2) Add a case to "toString()" method below
		// (3) Add it to MethodInstrumentationAdapter.visitParameter(...)
		// (4) Add a "getConcreteXXX(...)" method to SymbolicState.java
		// (5) Add a "getConcreteXXX(...)" method to SymbolicVM.java
		private static Class<?> parseType(String type) {
			int i = type.indexOf('[');
			if (i > -1) {
				String arrayType = type.substring(0, i);
				if (arrayType.equals("int")) {
					return int[].class;
		 		} else if (arrayType.equals("char")) {
					return char[].class;
				} else {
					return Object[].class;
				}
			} else if (type.equals("int")) {
				return int.class;
			} else if (type.equals("char")) {
				return char.class;
			} else if (type.equals("String")) {
				return String.class;
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
					} else if (paramTypes[i] == char[].class) {
						sb.append("char[]");
					} else {
						sb.append('*');
					}
					// MISSING: check for overridden length arrays int[5]? int[4]?...
				}
				stringRepr = sb.append(')').toString();
			}
			return stringRepr;
		}

	}

}
