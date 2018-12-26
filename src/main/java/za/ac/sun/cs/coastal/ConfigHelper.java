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

/**
 * A collection of routines to aid in loading the COASTAL configuration.
 */
public class ConfigHelper {

	// ======================================================================
	//
	// INSTANCE CREATION
	//
	// ======================================================================

	/**
	 * Create an instance of the class with the given name if it has a
	 * constructor that takes a single parameter: an instance of COASTAL. The
	 * given instance of COASTAL is passed to the constructor.
	 * 
	 * @param coastal
	 *            the instance of COASTAL to pass to the constructor
	 * @param className
	 *            the name of the class to create
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

	/**
	 * Create an instance of the class with the given name if it has a
	 * constructor that takes a two parameters: an instance of COASTAL and a
	 * (sub)configuration. The given instance of COASTAL and the configuration
	 * are passed to the constructor. This is used for components that may read
	 * additional options from the configuration.
	 * 
	 * @param coastal
	 *            the instance of COASTAL to pass to the constructor
	 * @param options
	 *            the (sub)configuration relevant to the instance
	 * @param className
	 *            the name of the class to create
	 * @return a new instance of the class (or {@code null} if it could not be
	 *         instantiated)
	 */
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

	/**
	 * Load the class specified by the class name.
	 * 
	 * @param coastal
	 *            instance of COASTAL
	 * @param className
	 *            the name of the class
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
				log.warn("CLASS NOT FOUND: " + className, x);
			} catch (ExceptionInInitializerError x) {
				log.warn("CLASS NOT FOUND: " + className, x);
			}
		}
		return clas;
	}

	// ======================================================================
	//
	// CONFIGURATION VALUE UTILITIES
	//
	// ======================================================================

	/**
	 * Return the {@code int} value "clamped" to lie between the minimum and
	 * maximum values.
	 * 
	 * @param value
	 *            the original value
	 * @param min
	 *            the minimum value allowed
	 * @param max
	 *            the maximum value allowed
	 * @return the clamped {@code int} value
	 */
	public static int minmax(int value, int min, int max) {
		return (value < min) ? min : (value > max) ? max : value;
	}

	/**
	 * Return the {@code long} value "clamped" to lie between the minimum and
	 * maximum values.
	 * 
	 * @param value
	 *            the original value
	 * @param min
	 *            the minimum value allowed
	 * @param max
	 *            the maximum value allowed
	 * @return the clamped {@code long} value
	 */
	public static long minmax(long value, long min, long max) {
		return (value < min) ? min : (value > max) ? max : value;
	}

	/**
	 * Return the {@code double} value "clamped" to lie between the minimum and
	 * maximum values.
	 * 
	 * @param value
	 *            the original value
	 * @param min
	 *            the minimum value allowed
	 * @param max
	 *            the maximum value allowed
	 * @return the clamped {@code double} value
	 */
	public static double minmax(double value, double min, double max) {
		return (value < min) ? min : (value > max) ? max : value;
	}
	
	/**
	 * Return the {@code int} value or, if it is equal to zero, a replacement
	 * value.
	 * 
	 * @param value
	 *            the original value
	 * @param zero
	 *            the replacement value if the first parameter is equal to zero
	 * @return the original {@code int} value or its zero replacement
	 */
	public static int zero(int value, int zero) {
		return (value == 0) ? zero : value;
	}

	/**
	 * Return the {@code long} value or, if it is equal to zero, a replacement
	 * value.
	 * 
	 * @param value
	 *            the original value
	 * @param zero
	 *            the replacement value if the first parameter is equal to zero
	 * @return the original {@code long} value or its zero replacement
	 */
	public static long zero(long value, long zero) {
		return (value == 0) ? zero : value;
	}

	/**
	 * The {@code int} value associated with the key in the supplied
	 * configuration. The value is clamped to minimum and maximum {@code int}
	 * values. If the key is not present, the maximum {@code int} value is
	 * returned.
	 * 
	 * @param config
	 *            the configuration to consult
	 * @param key
	 *            the key of the value
	 * @return the {@code int} value associated with the key
	 */
	public static int limitInt(ImmutableConfiguration config, String key) {
		return zero(minmax(config.getInt(key, Integer.MAX_VALUE), 0, Integer.MAX_VALUE), Integer.MAX_VALUE);
	}

	/**
	 * The {@code long} value associated with the key in the supplied
	 * configuration. The value is clamped to minimum and maximum {@code long}
	 * values. If the key is not present, the maximum {@code long} value is
	 * returned.
	 * 
	 * @param config
	 *            the configuration to consult
	 * @param key
	 *            the key of the value
	 * @return the {@code long} value associated with the key
	 */
	public static long limitLong(ImmutableConfiguration config, String key) {
		return zero(minmax(config.getLong(key, Long.MAX_VALUE), 0, Long.MAX_VALUE), Long.MAX_VALUE);
	}

	// ======================================================================
	//
	// XML NODE COMBINER
	//
	// ======================================================================

	/**
	 * Special combiner that merge (not overwrite) selected nodes in an Apache
	 * XML configuration.
	 */
	public static class ConfigCombiner extends NodeCombiner {

		/**
		 * Mapping from node tags to key attributes. The children of nodes whose
		 * names (tags) appear in this mapping are merged if and only if they
		 * agree on the value of the key attribute. If they disagree on the key
		 * attribute they are combined.
		 * 
		 * For example, if this mapping contains the pair
		 * {@code "elems" -> "name"}, then the two {@code "elems"} elements
		 * 
		 * <pre>
		 * &lt;elems&gt;
		 *   &lt;elem name="aaa" max=5/&gt;
		 *   &lt;elem name="bbb" max=7/&gt;
		 * &lt;/elems&gt;
		 * 
		 * &lt;elems&gt;
		 *   &lt;elem name="ccc" min=1/&gt;
		 *   &lt;elem name="bbb" min=3/&gt;
		 * &lt;/elems&gt;
		 * </pre>
		 * 
		 * are merged as
		 * 
		 * <pre>
		 * &lt;elems&gt;
		 *   &lt;elem name="aaa" max=5/&gt;
		 *   &lt;elem name="bbb" max=7 min=3/&gt;
		 *   &lt;elem name="ccc" min=1/&gt;
		 * &lt;/elems&gt;
		 * </pre>
		 */
		private final Map<String, String> joinNodes;

		/**
		 * Construct a COASTAL configuration node combiner.
		 */
		public ConfigCombiner() {
			joinNodes = new HashMap<>();
		}

		/**
		 * Add a node name and key attribute to the mapping of "special" nodes.
		 * 
		 * @param nodeName
		 *            the name of the node
		 * @param key
		 *            the key attribute
		 */
		public void addJoinNode(final String nodeName, final String key) {
			joinNodes.put(nodeName, key);
		}

		/**
		 * Return the key attribute associated with the given node. If there is
		 * no such attribute, {@code null} is returned.
		 * 
		 * @param node
		 *            the node whose key attribute is searched for
		 * @return the key attribute or {@code null} if the node has no
		 *         registered key attribute
		 */
		public String getJoinKey(final ImmutableNode node) {
			return (node == null) ? null : joinNodes.get(node.getNodeName());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.apache.commons.configuration2.tree.NodeCombiner#combine(org.
		 * apache.commons.configuration2.tree.ImmutableNode,
		 * org.apache.commons.configuration2.tree.ImmutableNode)
		 */
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

		/**
		 * Merge the attributes of {@code node1} and {@code node2} into
		 * attributes for {@code result}.
		 * 
		 * @param result
		 *            the target node for the attributes
		 * @param node1
		 *            the first source node for attributes
		 * @param node2
		 *            the second source node for attributes
		 */
		protected void addAttributes(final ImmutableNode.Builder result, final ImmutableNode node1,
				final ImmutableNode node2) {
			result.addAttributes(node1.getAttributes());
			for (final String attr : node2.getAttributes().keySet()) {
				if (!node1.getAttributes().containsKey(attr)) {
					result.addAttribute(attr, HANDLER.getAttributeValue(node2, attr));
				}
			}
		}

		/**
		 * Check whether the child of node1 corresponds to (= can be combined
		 * with) some child of node2 (which is then returned) or whether it is
		 * unique (in which case it is added on its own).
		 * 
		 * @param node1
		 *            the first parent node
		 * @param node2
		 *            the second parent node
		 * @param child
		 *            some child of the first parent node
		 * @return the corresponding child of the second parent node or
		 *         {@code null}
		 */
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

	/**
	 * Convert a Java {@link java.util.BitSet} instance to a {@link String}.
	 * This is somewhat out of place, but not entirely. A bit set such as
	 * {@code {1, 3, 4, 5, 7, 9, 10}} is formatted as {@code "{1, 3-5, 7,
	 * 9-10}"}.
	 * 
	 * @param bs
	 *            the bit set to format
	 * @return a string representation of the bit set
	 */
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
	// LOAD COASTAL CONFIGURATION (WITHOUT PARSING IT)
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
	 * Load a COASTAL configuration from a configuration file.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param arg
	 *            the name of the configuration file
	 * @return an immutable configuration or {@code null} if some configuration
	 *         error occurred
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String arg) {
		return loadConfiguration(log, new String[] { arg }, null);
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
	 *            the logger to which to report
	 * @param args
	 *            the command-line arguments
	 * @return an immutable configuration
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String[] args) {
		return loadConfiguration(log, args, null);
	}

	/**
	 * Load a COASTAL configuration from a configuration file and a
	 * configuration string.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param arg
	 *            the name of the configuration file
	 * @param extra
	 *            additional configuration settings (in XML)
	 * @return an immutable configuration or {@code null} if some configuration
	 *         error occurred
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String arg, String extra) {
		return loadConfiguration(log, new String[] { arg }, extra);
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
		String runName = (args.length > 0) ? FilenameUtils.getName(args[args.length - 1]) : null;
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
		int cfgIndex = args.length;
		for (String arg : args) {
			String filename = arg;
			if (filename.endsWith(".java")) {
				filename = filename.substring(0, filename.length() - 4) + "xml";
			}
			cfg3[--cfgIndex] = loadConfigFromFile(log, filename);
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
