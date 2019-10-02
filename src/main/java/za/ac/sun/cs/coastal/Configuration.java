/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.Logger;

/**
 * Encapsulation of COASTAL properties.
 */
public final class Configuration {

	// ======================================================================
	//
	// CONFIGURATION PARAMETERS
	//
	// ======================================================================

	/**
	 * The name of COASTAL configuration file, both the resource (part of the
	 * project, providing sensible defaults), and the user's own configuration file
	 * (providing overriding personalizations).
	 */
	private static final String COASTAL_CONFIGURATION = "coastal.properties";

	/**
	 * The subdirectory in the user's home directory where the personal coastal file
	 * is searched for.
	 */
	private static final String COASTAL_DIRECTORY = ".coastal";

	/**
	 * The user's home directory.
	 */
	private static final String HOME_DIRECTORY = System.getProperty("user.home");

	/**
	 * The full name of the subdirectory where the personal file is searched for.
	 */
	private static final String HOME_COASTAL_DIRECTORY = HOME_DIRECTORY + File.separator + COASTAL_DIRECTORY;

	/**
	 * The full name of the personal configuration file.
	 */
	private static final String HOME_CONFIGURATION = HOME_COASTAL_DIRECTORY + File.separator + COASTAL_CONFIGURATION;

	// ======================================================================
	//
	// FIELDS
	//
	// ======================================================================

	/**
	 * Representation of configurton options.
	 */
	private final Properties properties;

	// ======================================================================
	//
	// CONSTRUCTOR AND METHODS
	//
	// ======================================================================

	/**
	 * Create a COASTAL configuration object.
	 */
	private Configuration() {
		properties = new Properties();
	}

	/**
	 * Create a COASTAL configuration object.
	 * 
	 * @param properties
	 *                   configuration settings
	 */
	private Configuration(Properties properties) {
		this.properties = properties;
	}

	public List<String> getKeys() {
		List<String> keys = new ArrayList<>();
		for (Object key : properties.keySet()) {
			if (key instanceof String) {
				keys.add((String) key);
			}
		}
		keys.sort(new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		return keys;
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	/**
	 * Return the configuration setting associated with the key.
	 *
	 * @param key
	 *            setting key
	 * @return setting value for given key
	 */
	public String getString(String key) {
		return (String) properties.get(key);
	}

	/**
	 * Return the configuration setting associated with the key.
	 *
	 * @param key
	 *            setting key
	 * @return setting value for given key
	 */
	public String getString(String key, String defaultValue) {
		String value = getString(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * Return the boolean configuration setting associated with the key.
	 *
	 * @param key
	 *                     setting key
	 * @param defaultValue
	 *                     default value to return
	 * @return the boolean value of the key, if found, or the supplied default value
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getString(key);
		if (value == null) {
			return defaultValue;
		} else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")
				|| value.equals("1")) {
			return true;
		} else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("off")
				|| value.equals("0")) {
			return false;
		} else {
			return defaultValue;
		}
	}

	public Integer getInt(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Integer(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	/**
	 * Return the integer configuration setting associated with the key. If the key
	 * is not present, the given default value is used.
	 *
	 * @param key
	 *                     setting key
	 * @param defaultValue
	 *                     default value to return
	 * @return the integer value of the key, if found, or the supplied default value
	 */
	public int getInt(String key, int defaultValue) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return defaultValue;
	}

	/**
	 * Return the integer configuration setting associated with the key. If the key
	 * is not present, the given default value is used. In all cases, if the value
	 * is zero, the given zero value is returned instead
	 *
	 * @param key
	 *                     setting key
	 * @param defaultValue
	 *                     default value to use
	 * @param zeroValue
	 *                     value to return if computed value is zero
	 * @return the integer value of the key, if found, or the supplied default value
	 */
	public int getInt(String key, int defaultValue, int zeroValue) {
		int value = getInt(key, defaultValue);
		if (value == 0) {
			value = zeroValue;
		}
		return value;
	}

	public int getInt(String key, int defaultValue, int minValue, int maxValue) {
		int value = getInt(key, defaultValue);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return value;
	}

	public Short getShort(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Short(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	public Byte getByte(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Byte(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	public Long getLong(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Long(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	public Float getFloat(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Float(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	public Double getDouble(String key) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return new Double(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return null;
	}

	/**
	 * Return the long integer configuration setting associated with the key.
	 *
	 * @param key
	 *                     setting key
	 * @param defaultValue
	 *                     default value to return
	 * @return the long integer value of the key, if found, or the supplied default
	 *         value
	 */
//	public Long getLong(String key) {
//		String value = (String) properties.get(key);
//		if (value != null) {
//			try {
//				return Long.parseLong(value);
//			} catch (NumberFormatException x) {
//				// do nothing
//			}
//		}
//		return null;
//	}

	public long getLong(String key, long defaultValue) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return defaultValue;
	}

	public long getLong(String key, long defaultValue, long zeroValue) {
		long value = getLong(key, defaultValue);
		if (value == 0) {
			value = zeroValue;
		}
		return value;
	}

	public long getLong(String key, long defaultValue, long minValue, long maxValue) {
		long value = getLong(key, defaultValue);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return value;
	}

	public long getLong(String key, long defaultValue, long zeroValue, long minValue, long maxValue) {
		long value = getLong(key, defaultValue, zeroValue);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return value;
	}

	public long getLongMaxed(String key) {
		return getLong(key, Long.MAX_VALUE, Long.MAX_VALUE, 0, Long.MAX_VALUE);
	}

	public float getFloat(String key, float defaultValue) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return defaultValue;
	}

	public double getDouble(String key, double defaultValue) {
		String value = (String) properties.get(key);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException x) {
				// do nothing
			}
		}
		return defaultValue;
	}

	public double getDouble(String key, double defaultValue, double zeroValue) {
		double value = getDouble(key, defaultValue);
		if (value == 0) {
			value = zeroValue;
		}
		return value;
	}

	public double getDouble(String key, double defaultValue, double minValue, double maxValue) {
		double value = getDouble(key, defaultValue);
		if (value < minValue) {
			value = minValue;
		} else if (value > maxValue) {
			value = maxValue;
		}
		return value;
	}

	/**
	 * The {@code int} value associated with the key in the supplied configuration.
	 * The value is clamped to minimum and maximum {@code int} values. If the key is
	 * not present, the maximum {@code int} value is returned.
	 * 
	 * @param configuration
	 *                      the configuration to consult
	 * @param key
	 *                      the key of the value
	 * @return the {@code int} value associated with the key
	 */
//	public int limitInt(String key) {
//		return zero(minmax(getInt(key, Integer.MAX_VALUE), 0, Integer.MAX_VALUE), Integer.MAX_VALUE);
//	}

	/**
	 * The {@code long} value associated with the key in the supplied configuration.
	 * The value is clamped to minimum and maximum {@code long} values. If the key
	 * is not present, the maximum {@code long} value is returned.
	 * 
	 * @param configuration
	 *                      the configuration to consult
	 * @param key
	 *                      the key of the value
	 * @return the {@code long} value associated with the key
	 */
//	public long limitLong(String key) {
//		return zero(minmax(getLong(key, Long.MAX_VALUE), 0, Long.MAX_VALUE), Long.MAX_VALUE);
//	}
//
//	public int zeroInt(String key, int zero) {
//		int value = getInt(key, 0);
//		return (value == 0) ? zero : value;
//	}
//
//	public long zeroLong(String key, long zero) {
//		long value = getLong(key, 0);
//		return (value == 0) ? zero : value;
//	}

	public Configuration subset(String prefix) {
		prefix += ".";
		Configuration configuration = new Configuration();
		for (Enumeration<?> keys = properties.propertyNames(); keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				configuration.properties.put(key.substring(prefix.length()), properties.getProperty(key));
			}
		}
		return configuration;
	}

	public Properties extract(String prefix) {
		prefix += ".";
		Properties newProperties = new Properties();
		for (Enumeration<?> keys = properties.propertyNames(); keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				newProperties.put(key, properties.getProperty(key));
			}
		}
		return newProperties;
	}

	// ======================================================================
	//
	// CONFIGURATION VALUE UTILITIES
	//
	// ======================================================================

	/**
	 * Return the {@code int} value "clamped" to lie between the minimum and maximum
	 * values.
	 * 
	 * @param value
	 *              the original value
	 * @param min
	 *              the minimum value allowed
	 * @param max
	 *              the maximum value allowed
	 * @return the clamped {@code int} value
	 */
//	public static int minmax(int value, int min, int max) {
//		return (value < min) ? min : (value > max) ? max : value;
//	}

	/**
	 * Return the {@code long} value "clamped" to lie between the minimum and
	 * maximum values.
	 * 
	 * @param value
	 *              the original value
	 * @param min
	 *              the minimum value allowed
	 * @param max
	 *              the maximum value allowed
	 * @return the clamped {@code long} value
	 */
//	public static long minmax(long value, long min, long max) {
//		return (value < min) ? min : (value > max) ? max : value;
//	}

	/**
	 * Return the {@code double} value "clamped" to lie between the minimum and
	 * maximum values.
	 * 
	 * @param value
	 *              the original value
	 * @param min
	 *              the minimum value allowed
	 * @param max
	 *              the maximum value allowed
	 * @return the clamped {@code double} value
	 */
//	public static double minmax(double value, double min, double max) {
//		return (value < min) ? min : (value > max) ? max : value;
//	}

	/**
	 * Return the {@code int} value or, if it is equal to zero, a replacement value.
	 * 
	 * @param value
	 *              the original value
	 * @param zero
	 *              the replacement value if the first parameter is equal to zero
	 * @return the original {@code int} value or its zero replacement
	 */
//	public static int zero(int value, int zero) {
//		return (value == 0) ? zero : value;
//	}

	/**
	 * Return the {@code long} value or, if it is equal to zero, a replacement
	 * value.
	 * 
	 * @param value
	 *              the original value
	 * @param zero
	 *              the replacement value if the first parameter is equal to zero
	 * @return the original {@code long} value or its zero replacement
	 */
//	public static long zero(long value, long zero) {
//		return (value == 0) ? zero : value;
//	}

	// ======================================================================
	//
	// BITSET PRINTING
	//
	// ======================================================================

	/**
	 * Convert a Java {@link java.util.BitSet} instance to a {@link String}. This is
	 * somewhat out of place, but not entirely. A bit set such as {@code {1, 3, 4,
	 * 5, 7, 9, 10}} is formatted as {@code "{1, 3-5, 7, 9-10}"}.
	 * 
	 * @param bs
	 *           the bit set to format
	 * @return a string representation of the bit set
	 */
	public static String toString(BitSet bs) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		boolean isFirst = true;
		Integer previousStart = null, previousEnd = null;
		for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
			if (previousStart == null) {
				previousStart = i;
				previousEnd = i;
			} else if (i == previousEnd + 1) {
				previousEnd = i;
			} else if (previousStart == previousEnd) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(", ");
				}
				sb.append(previousStart);
				previousStart = i;
				previousEnd = i;
			} else {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(", ");
				}
				sb.append(previousStart).append('-').append(previousEnd);
				previousStart = i;
				previousEnd = i;
			}
			if (i == Integer.MAX_VALUE) {
				break;
			}
		}
		if (previousStart == previousEnd) {
			if (!isFirst) {
				sb.append(", ");
			}
			sb.append(previousStart);
		} else {
			if (!isFirst) {
				sb.append(", ");
			}
			sb.append(previousStart).append('-').append(previousEnd);
		}
		return sb.append('}').toString();
	}

	// ======================================================================
	//
	// LOADERS
	//
	// ======================================================================

	/**
	 * Load a COASTAL configuration from a configuration file.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param arg
	 *            the name of the configuration file
	 * @return an immutable configuration or {@code null} if some configuration
	 *         error occurred
	 */
	public static Configuration load(Logger log, String arg) {
		return load(log, new String[] { arg }, null);
	}

	/**
	 * Load the COASTAL configuration. Three sources are consulted:
	 * 
	 * <ul>
	 * <li>a project resource</li>
	 * <li>the user's personal configuration file</li>
	 * <li>the configuration file specified on the command line</li>
	 * </ul>
	 * 
	 * The second source overrides the first, and the third source overrides the
	 * first two sources.
	 * 
	 * @param log
	 *             the logger to which to report
	 * @param args
	 *             the command-line arguments
	 * @return an immutable configuration
	 */
	public static Configuration load(Logger log, String[] args) {
		return load(log, args, null);
	}

	/**
	 * Load a COASTAL configuration from a configuration file and a configuration
	 * string.
	 * 
	 * @param log
	 *              the logger to which to report
	 * @param arg
	 *              the name of the configuration file
	 * @param extra
	 *              additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if some configuration
	 *         error occurred
	 */
	public static Configuration load(Logger log, String arg, String extra) {
		return load(log, new String[] { arg }, extra);
	}

	/**
	 * Load a COASTAL configuration from a variety of sources.
	 * 
	 * @param log
	 *              the logger to which to report
	 * @param args
	 *              an array of filenames or resources that refer to configurations
	 * @param extra
	 *              additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if no configuration was
	 *         found
	 */
	public static Configuration load(Logger log, String[] args, String extra) {
		String runName = (args.length > 0) ? new File(args[args.length - 1]).getName() : null;
		return load(log, runName, args, extra);
	}

	/**
	 * Load a COASTAL configuration from a variety of sources.
	 * 
	 * @param log
	 *                the logger to which to report
	 * @param runName
	 *                the "name" for this COASTAL run
	 * @param args
	 *                an array of filenames or resources that refer to
	 *                configurations
	 * @param extra
	 *                additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if no configuration was
	 *         found
	 */
	private static Configuration load(Logger log, String runName, String[] args, String extra) {
		if (runName != null) {
			if (runName.endsWith(".properties")) {
				runName = runName.substring(0, runName.length() - 11);
			}
			String runNameSetting = "\ncoastal.run-name = " + runName;
			if (extra == null) {
				extra = runNameSetting;
			} else {
				extra += runNameSetting;
			}
		}
		Configuration cfg1 = loadFromResource(log, COASTAL_CONFIGURATION);
		Configuration cfg2 = loadFromFile(log, HOME_CONFIGURATION);
		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			return null;
		}
		Configuration[] cfg3 = new Configuration[args.length];
		int cfgIndex = args.length;
		for (String arg : args) {
			String filename = arg;
			if (filename.endsWith(".java")) {
				filename = filename.substring(0, filename.length() - 4) + "properties";
			}
			cfg3[--cfgIndex] = loadFromFile(log, filename);
			if (cfg3[cfgIndex] == null) {
				cfg3[cfgIndex] = loadFromResource(log, filename);
			}
			if (cfg3[cfgIndex] == null) {
				Banner bn = new Banner('@');
				bn.println("COASTAL PROBLEM\n");
				bn.println("COULD NOT READ CONFIGURATION FILE \"" + filename + "\"");
				bn.display(log);
			}
		}
		Configuration cfg4 = loadFromString(log, extra);
		// Combine the configurations
		Configuration config = new Configuration();
		if (cfg1 != null) {
			config.combine(cfg1);
		}
		if (cfg2 != null) {
			config.combine(cfg2);
		}
		for (Configuration cfg : cfg3) {
			if (cfg != null) {
				config.combine(cfg);
			}
		}
		if (cfg4 != null) {
			config.combine(cfg4);
		}
		if (config.getString("coastal.target.trigger") == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .properties FILE?");
			bn.display(log);
			return null;
		}
		return config;
	}

	/**
	 * Load a COASTAL configuration from a file.
	 * 
	 * @param log
	 *                 the logger to which to report
	 * @param filename
	 *                 the name of the file
	 * @return an immutable configuration or {@code null} if the file was not found
	 */
	private static Configuration loadFromFile(Logger log, String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
			Configuration configuration = loadFromStream(log, inputStream);
			if (configuration == null) {
				log.trace("failed to load configuration from {}", filename);
			}
			return configuration;
		} catch (FileNotFoundException e) {
			log.trace("failed to load configuration from {}", filename);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from a Java resource.
	 * 
	 * @param log
	 *                     the logger to which to report
	 * @param resourceName
	 *                     the name of the resource
	 * @return an immutable configuration or {@code null} if the resource was not
	 *         found
	 */
	private static Configuration loadFromResource(Logger log, String resourceName) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader.getResourceAsStream(resourceName);
		Configuration configuration = loadFromStream(log, resourceStream);
		if (configuration == null) {
			log.trace("failed to load configuration from {}", resourceName);
		}
		return configuration;
	}

	/**
	 * Load a COASTAL configuration from an input stream.
	 * 
	 * @param log
	 *                    the logger to which to report
	 * @param inputStream
	 *                    the stream from which to read
	 * @return an immutable configuration
	 */
	private static Configuration loadFromStream(Logger log, InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		try {
			Properties properties = new Properties();
			properties.load(inputStream);
			return new Configuration(properties);
		} catch (IOException x) {
			log.trace("IO ERROR", x);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from a string.
	 * 
	 * @param log
	 *                     the logger to which to report
	 * @param configString
	 *                     the string that contains the configuration
	 * @return an immutable configuration
	 */
	private static Configuration loadFromString(Logger log, String configString) {
		if (configString == null) {
			log.trace("configString is null");
			return null;
		}
		InputStream in = new ByteArrayInputStream(configString.getBytes());
		return loadFromStream(log, in);
	}

	private Configuration combine(Configuration configuration) {
		for (Enumeration<?> keys = configuration.properties.propertyNames(); keys.hasMoreElements();) {
			String key = (String) keys.nextElement();
			String value = configuration.properties.getProperty(key);
			if (value.contains("!!")) {
				value.replace("!!", properties.getProperty(key, ""));
			}
			properties.put(key, value);
		}
		return this;
	}

	// ======================================================================
	//
	// INSTANCE CREATION
	//
	// ======================================================================

	/**
	 * Create an instance of the class with the given name if it has a constructor
	 * that takes a single parameter: an instance of COASTAL. The given instance of
	 * COASTAL is passed to the constructor.
	 * 
	 * @param coastal
	 *                  the instance of COASTAL to pass to the constructor
	 * @param className
	 *                  the name of the class to create
	 * @return a new instance of the class (or {@code null} if it could not be
	 *         instantiated)
	 */
	public static Object createInstance(COASTAL coastal, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = loadClass(coastal, className);
		if (clas == null) {
			return null;
		}
		try {
			Constructor<?> constructor = null;
			try {
				constructor = clas.getConstructor(COASTAL.class);
				if (constructor == null) {
					return null;
				}
				return constructor.newInstance(coastal);
			} catch (NoSuchMethodException x) {
				log.info("CONSTRUCTOR NOT FOUND: " + className, x);
			}
		} catch (SecurityException x) {
			log.trace("CONSTRUCTOR SECURITY ERROR: " + className, x);
		} catch (IllegalArgumentException x) {
			log.trace("CONSTRUCTOR ARGUMENT ERROR: " + className, x);
		} catch (InstantiationException x) {
			log.trace("CONSTRUCTOR INSTANTIATION ERROR: " + className, x);
		} catch (IllegalAccessException x) {
			log.trace("CONSTRUCTOR ACCESS ERROR: " + className, x);
		} catch (InvocationTargetException x) {
			log.trace("CONSTRUCTOR INVOCATION ERROR: " + className, x);
		}
		return null;
	}

	/**
	 * Create an instance of the class with the given name if it has a constructor
	 * that takes a two parameters: an instance of COASTAL and a (sub)configuration.
	 * The given instance of COASTAL and the configuration are passed to the
	 * constructor. This is used for components that may read additional options
	 * from the configuration.
	 * 
	 * @param coastal
	 *                  the instance of COASTAL to pass to the constructor
	 * @param options
	 *                  the (sub)configuration relevant to the instance
	 * @param className
	 *                  the name of the class to create
	 * @return a new instance of the class (or {@code null} if it could not be
	 *         instantiated)
	 */
	public static Object createInstance(COASTAL coastal, Configuration options, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = loadClass(coastal, className);
		if (clas == null) {
			return null;
		}
		try {
			Constructor<?> constructor = null;
			try {
				constructor = clas.getConstructor(COASTAL.class, Configuration.class);
				if (constructor == null) {
					return null;
				}
				return constructor.newInstance(coastal, options);
			} catch (NoSuchMethodException x) {
				log.info("CONSTRUCTOR NOT FOUND: " + className, x);
			}
		} catch (SecurityException x) {
			log.trace("CONSTRUCTOR SECURITY ERROR: " + className, x);
		} catch (IllegalArgumentException x) {
			log.trace("CONSTRUCTOR ARGUMENT ERROR: " + className, x);
		} catch (InstantiationException x) {
			log.trace("CONSTRUCTOR INSTANTIATION ERROR: " + className, x);
		} catch (IllegalAccessException x) {
			log.trace("CONSTRUCTOR ACCESS ERROR: " + className, x);
		} catch (InvocationTargetException x) {
			log.trace("CONSTRUCTOR INVOCATION ERROR: " + className, x);
		}
		return null;
	}

	/**
	 * Load the class specified by the class name.
	 * 
	 * @param coastal
	 *                  instance of COASTAL
	 * @param className
	 *                  the name of the class
	 * @return the class (or {@code null} if it could not be loaded)
	 */
	public static Class<?> loadClass(COASTAL coastal, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = null;
		if ((className != null) && (className.length() > 0)) {
			try {
				ClassLoader cl = COASTAL.class.getClassLoader();
				clas = cl.loadClass(className);
			} catch (ClassNotFoundException x) {
				log.trace("CLASS NOT FOUND: " + className, x);
			} catch (ExceptionInInitializerError x) {
				log.trace("CLASS NOT FOUND: " + className, x);
			}
		}
		return clas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		SortedSet<String> keys = new TreeSet<>();
		for (Object key : properties.keySet()) {
			if (key instanceof String) {
				keys.add((String) key);
			}
		}
		boolean isFirst = true;
		for (String key : keys) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append('\n');
			}
			sb.append(key).append(" = ").append(properties.get(key));
		}
		return sb.toString();
	}

}
