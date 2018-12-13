package za.ac.sun.cs.coastal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.HashMap;
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
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeCombiner;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

public class ConfigHelper {

	// ======================================================================
	//
	// 
	//
	// ======================================================================

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
			log.warn("CONSTRUCTOR SECURITY ERROR: " + className, x);
		} catch (IllegalArgumentException x) {
			log.warn("CONSTRUCTOR ARGUMENT ERROR: " + className, x);
		} catch (InstantiationException x) {
			log.warn("CONSTRUCTOR INSTANTIATION ERROR: " + className, x);
		} catch (IllegalAccessException x) {
			log.warn("CONSTRUCTOR ACCESS ERROR: " + className, x);
		} catch (InvocationTargetException x) {
			log.warn("CONSTRUCTOR INVOCATION ERROR: " + className, x);
		}
		return null;
	}

	public static Object createInstance(COASTAL coastal, ImmutableConfiguration options, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = loadClass(coastal, className);
		if (clas == null) {
			return null;
		}
		try {
			Constructor<?> constructor = null;
			try {
				constructor = clas.getConstructor(COASTAL.class, ImmutableConfiguration.class);
				if (constructor == null) {
					return null;
				}
				return constructor.newInstance(coastal, options);
			} catch (NoSuchMethodException x) {
				log.info("CONSTRUCTOR NOT FOUND: " + className, x);
			}
		} catch (SecurityException x) {
			log.warn("CONSTRUCTOR SECURITY ERROR: " + className, x);
		} catch (IllegalArgumentException x) {
			log.warn("CONSTRUCTOR ARGUMENT ERROR: " + className, x);
		} catch (InstantiationException x) {
			log.warn("CONSTRUCTOR INSTANTIATION ERROR: " + className, x);
		} catch (IllegalAccessException x) {
			log.warn("CONSTRUCTOR ACCESS ERROR: " + className, x);
		} catch (InvocationTargetException x) {
			log.warn("CONSTRUCTOR INVOCATION ERROR: " + className, x);
		}
		return null;
	}
	
	public static Class<?> loadClass(COASTAL coastal, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = null;
		if ((className != null) && (className.length() > 0)) {
			try {
				ClassLoader cl = COASTAL.class.getClassLoader();
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
	// 
	//
	// ======================================================================

	public static long minmax(long value, long min, long max) {
		return (value < min) ? min : (value > max) ? max : value;
	}

	public static long zero(long value, long zero) {
		return (value == 0) ? zero : value;
	}

	public static long limitLong(ImmutableConfiguration config, String key) {
		return zero(minmax(config.getLong(key, Long.MAX_VALUE), 0, Long.MAX_VALUE), Long.MAX_VALUE);
	}

	public static int minmax(int value, int min, int max) {
		return (value < min) ? min : (value > max) ? max : value;
	}

	public static int zero(int value, int zero) {
		return (value == 0) ? zero : value;
	}

	public static int limitInt(ImmutableConfiguration config, String key) {
		return zero(minmax(config.getInt(key, Integer.MAX_VALUE), 0, Integer.MAX_VALUE), Integer.MAX_VALUE);
	}

	// ======================================================================
	//
	// 
	//
	// ======================================================================

	public static class ConfigCombiner extends NodeCombiner {

		private final Map<String, String> joinNodes;

		public ConfigCombiner() {
			joinNodes = new HashMap<>();
		}

		public void addJoinNode(final String nodeName, final String key) {
			joinNodes.put(nodeName, key);
		}

		public String getJoinKey(final ImmutableNode node) {
			return joinNodes.get(node.getNodeName());
		}

		@Override
		public ImmutableNode combine(final ImmutableNode node1, final ImmutableNode node2) {
			final ImmutableNode.Builder result = new ImmutableNode.Builder();
			result.name(node1.getNodeName());
			String joinKey = getJoinKey(node1);
			if (joinKey != null) {
				final Map<Object, ImmutableNode> children2 = new HashMap<>();
				for (final ImmutableNode child2 : node2.getChildren()) {
					Map<String, Object> attrs2 = child2.getAttributes();
					if (!attrs2.containsKey(joinKey)) {
						result.addChild(child2);
					} else {
						children2.put(attrs2.get(joinKey), child2);
					}
				}
				for (final ImmutableNode child1 : node1.getChildren()) {
					Map<String, Object> attrs1 = child1.getAttributes();
					if (!attrs1.containsKey(joinKey)) {
						result.addChild(child1);
					} else if (children2.containsKey(attrs1.get(joinKey))) {
						Object joinAttr = attrs1.get(joinKey);
						result.addChild(combine(child1, children2.get(joinAttr)));
						children2.remove(joinAttr);
					} else {
						result.addChild(child1);
					}
				}
				for (final ImmutableNode child2 : children2.values()) {
					result.addChild(child2);
				}
			} else {
				for (final ImmutableNode child : node1.getChildren()) {
					final ImmutableNode child2 = canCombine(node1, node2, child);
					if (child2 != null) {
						result.addChild(combine(child, child2));
					} else {
						result.addChild(child);
					}
				}
				for (final ImmutableNode child : node2.getChildren()) {
					if (HANDLER.getChildrenCount(node1, child.getNodeName()) < 1) {
						result.addChild(child);
					}
				}
			}
			addAttributes(result, node1, node2);
			result.value((node1.getValue() != null) ? node1.getValue() : node2.getValue());
			return result.create();
		}

		protected void addAttributes(final ImmutableNode.Builder result, final ImmutableNode node1,
				final ImmutableNode node2) {
			result.addAttributes(node1.getAttributes());
			for (final String attr : node2.getAttributes().keySet()) {
				if (!node1.getAttributes().containsKey(attr)) {
					result.addAttribute(attr, HANDLER.getAttributeValue(node2, attr));
				}
			}
		}

		protected ImmutableNode canCombine(final ImmutableNode node1, final ImmutableNode node2,
				final ImmutableNode child) {
			if (HANDLER.getChildrenCount(node2, child.getNodeName()) == 1
					&& HANDLER.getChildrenCount(node1, child.getNodeName()) == 1 && !isListNode(child)) {
				return HANDLER.getChildren(node2, child.getNodeName()).get(0);
			}
			return null;
		}

	}

	// ======================================================================
	//
	// BITSET PRINTING
	//
	// ======================================================================

	public static final String toString(BitSet bs) {
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
	// 
	//
	// ======================================================================

	/**
	 * The name of COASTAL configuration file, both the resource (part of the
	 * project, providing sensible defaults), and the user's own configuration
	 * file (providing overriding personalizations).
	 */
	private static final String COASTAL_CONFIGURATION = "coastal.xml";

	/**
	 * The subdirectory in the user's home directory where the personal coastal
	 * file is searched for.
	 */
	private static final String COASTAL_DIRECTORY = ".coastal";

	/**
	 * The user's home directory.
	 */
	private static final String HOME_DIRECTORY = System.getProperty("user.home");

	/**
	 * The full name of the subdirectory where the personal file is searched
	 * for.
	 */
	private static final String HOME_COASTAL_DIRECTORY = HOME_DIRECTORY + File.separator + COASTAL_DIRECTORY;

	/**
	 * The full name of the personal configuration file.
	 */
	private static final String HOME_CONFIGURATION = HOME_COASTAL_DIRECTORY + File.separator + COASTAL_CONFIGURATION;

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
	 *            the logger to which to report
	 * @param args
	 *            the command-line arguments
	 * @return an immutable configuration
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String[] args) {
		return loadConfiguration(log, args, null);
	}

	/**
	 * Load a COASTAL configuration from a variety of sources.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param args
	 *            an array of filenames or resources that refer to
	 *            configurations
	 * @param extra
	 *            additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if no configuration
	 *         was found
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String[] args, String extra) {
		String runName = (args.length > 0) ? FilenameUtils.getName(args[0]) : null;
		return loadConfiguration(log, runName, args, extra);
	}

	/**
	 * Load a COASTAL configuration from a variety of sources.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param runName
	 *            the "name" for this COASTAL run
	 * @param args
	 *            an array of filenames or resources that refer to
	 *            configurations
	 * @param extra
	 *            additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if no configuration
	 *         was found
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String runName, String[] args, String extra) {
		if (runName != null) {
			String runNameSetting = "<run-name>" + runName + "</run-name>";
			if (extra == null) {
				extra = runNameSetting;
			} else {
				extra += runNameSetting;
			}
		}
		Configuration cfg1 = loadConfigFromResource(log, COASTAL_CONFIGURATION);
		Configuration cfg2 = loadConfigFromFile(log, HOME_CONFIGURATION);
		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			return null;
		}
		Configuration[] cfg3 = new Configuration[args.length];
		int cfgIndex = -1;
		for (String arg : args) {
			String filename = arg;
			if (filename.endsWith(".java")) {
				filename = filename.substring(0, filename.length() - 4) + "xml";
			}
			cfg3[++cfgIndex] = loadConfigFromFile(log, filename);
			if (cfg3[cfgIndex] == null) {
				cfg3[cfgIndex] = loadConfigFromResource(log, filename);
			}
			if (cfg3[cfgIndex] == null) {
				Banner bn = new Banner('@');
				bn.println("COASTAL PROBLEM\n");
				bn.println("COULD NOT READ CONFIGURATION FILE \"" + filename + "\"");
				bn.display(log);
			}
		}
		Configuration cfg4 = loadConfigFromString(log, extra);
		ConfigCombiner combiner = new ConfigCombiner();
		combiner.addJoinNode("bounds", "name");
		CombinedConfiguration config = new CombinedConfiguration(combiner);
		if (cfg4 != null) {
			config.addConfiguration(cfg4);
		}
		for (Configuration cfg : cfg3) {
			if (cfg != null) {
				config.addConfiguration(cfg);
			}
		}
		if (cfg2 != null) {
			config.addConfiguration(cfg2);
		}
		if (cfg1 != null) {
			config.addConfiguration(cfg1);
		}
		if (config.getString("coastal.target.trigger") == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .xml FILE?");
			bn.display(log);
			return null;
		}
		return ConfigurationUtils.unmodifiableConfiguration(config);
	}

	/**
	 * Load a COASTAL configuration from a file.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param filename
	 *            the name of the file
	 * @return an immutable configuration or {@code null} if the file was not
	 *         found
	 */
	public static Configuration loadConfigFromFile(Logger log, String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
			Configuration cfg = loadConfigFromStream(log, inputStream);
			log.trace("loaded configuration from {}", filename);
			return cfg;
		} catch (ConfigurationException x) {
			if (x.getCause() != null) {
				log.trace("configuration error: " + x.getCause().getMessage());
			}
		} catch (FileNotFoundException x) {
			log.trace("failed to load configuration from {}", filename);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from a Java resource.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param resourceName
	 *            the name of the resource
	 * @return an immutable configuration or {@code null} if the resource was
	 *         not found
	 */
	private static Configuration loadConfigFromResource(Logger log, String resourceName) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			if (resourceStream != null) {
				Configuration cfg = loadConfigFromStream(log, resourceStream);
				log.trace("loaded configuration from {}", resourceName);
				return cfg;
			}
		} catch (ConfigurationException x) {
			if (x.getCause() != null) {
				log.trace("configuration error: " + x.getCause().getMessage());
			}
		} catch (IOException x) {
			log.trace("failed to load configuration from {}", resourceName);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from an input stream.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param inputStream
	 *            the stream from which to read
	 * @return an immutable configuration
	 * @throws ConfigurationException
	 *             if anything went wrong during the loading of the
	 *             configuration
	 */
	private static Configuration loadConfigFromStream(Logger log, InputStream inputStream)
			throws ConfigurationException {
		//		XMLConfiguration cfg = new BasicConfigurationBuilder<>(XMLConfiguration.class)
		//				.configure(new Parameters().xml().setValidating(true)).getConfiguration();
		XMLConfiguration cfg = new BasicConfigurationBuilder<>(XMLConfiguration.class).configure(new Parameters().xml())
				.getConfiguration();
		FileHandler fh = new FileHandler(cfg);
		fh.load(inputStream);
		return cfg;
	}

	/**
	 * Load a COASTAL configuration from a string.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param configString
	 *            the stirng that contains the configuration
	 * @return an immutable configuration
	 */
	private static Configuration loadConfigFromString(Logger log, String configString) {
		if (configString == null) {
			return null;
		}
		try {
			String finalString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
					+ "<!DOCTYPE configuration PUBLIC \"-//DEEPSEA//COASTAL configuration//EN\" "
					+ "\"https://deepseaplatform.github.io/coastal/coastal.dtd\">" + "<configuration>" + configString
					+ "</configuration>";
			InputStream in = new ByteArrayInputStream(finalString.getBytes());
			return loadConfigFromStream(log, in);
		} catch (ConfigurationException x) {
			// ignore
		}
		return null;
	}

}
