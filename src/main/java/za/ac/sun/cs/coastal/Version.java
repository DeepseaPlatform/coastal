package za.ac.sun.cs.coastal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read and store the version number.
 * 
 * This does not work when COASTAL is run from inside Eclipse. It *does* work
 * when run from the command line or from a jar file.
 */
public class Version {

	/**
	 * Filename that stores the version number.
	 */
	protected static final String DEFAULT_APP_PROPERTIES = "app.properties";

	/**
	 * Default version if it cannot be read from a file.
	 */
	protected static final String DEFAULT_VERSION = "unspecified";

	/**
	 * The computed version.
	 */
	protected String version = null;

	/**
	 * Read (if necessary) and return the version.
	 * 
	 * @return the program version as a string
	 */
	public String read() {
		if (version == null) {
			version = read(DEFAULT_APP_PROPERTIES);
		}
		return version;
	}

	/**
	 * Read and return the version from a resource file.
	 * 
	 * @param resourceName
	 *            the name of the resource containing the version
	 * @return the program version as a string
	 */
	protected String read(String resourceName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			if (resourceStream != null) {
				return read(resourceStream);
			}
		} catch (IOException x) {
			// ignore
		}
		return DEFAULT_VERSION;
	}

	/**
	 * Read and return the version from an input stream.
	 * 
	 * @param resourceName
	 *            the input stream containing the version
	 * @return the program version as a string
	 */
	protected String read(InputStream inputStream) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException x) {
			// ignore
		}
		return read(properties);
	}

	/**
	 * Read and return the version from a Java properties object.
	 * 
	 * @param properties
	 *            the object containing the version property
	 * @return the program version as a string
	 */
	protected String read(Properties properties) {
		String v = properties.getProperty("version");
		if ((v != null) && (v.charAt(0) != '%')) {
			return v;
		}
		return DEFAULT_VERSION;
	}

}
