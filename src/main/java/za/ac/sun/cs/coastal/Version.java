package za.ac.sun.cs.coastal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

/**
 * Static methods to compute the current version of COASTAL.
 */
public class Version {

	/**
	 * Name of the Java properties file that gradle uses to store the version
	 * number.
	 */
	protected static final String DEFAULT_APP_PROPERTIES = "app.properties";

	/**
	 * The version string used when the version cannot be computed in any other
	 * way.
	 */
	protected static final String DEFAULT_VERSION = "unspecified";

	/**
	 * The computed version for this run of COASTAL.
	 */
	protected static String version = null;

	/**
	 * Read and return the version.
	 * 
	 * @return the current version of COASTAL
	 */
	public static String read() {
		if (version == null) {
			version = read(DEFAULT_APP_PROPERTIES);
		}
		return version;
	}

	/**
	 * Read the version from the given resource.
	 * 
	 * @param resourceName
	 *            the name of the resource to read
	 * @return the current version of COASTAL as specified in the resource
	 */
	protected static String read(String resourceName) {
		return read(resourceName, true);
	}

	/**
	 * Read the version from a given resource or use Jgit if the second
	 * parameter is true.
	 * 
	 * @param resourceName
	 *            the name of the resource to read
	 * @param tryJgit
	 *            whether or not to use Jgit is the resource is not available
	 * @return the current version of COASTAL as specified in the resource
	 */
	protected static String read(String resourceName, boolean tryJgit) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			if (resourceStream != null) {
				return read(resourceStream, tryJgit);
			}
		} catch (IOException x) {
			// ignore
		}
		if (tryJgit) {
			return readJgitVersion();
		} else {
			return DEFAULT_VERSION;
		}
	}

	/**
	 * Read the version from the given input stream.
	 * 
	 * @param inputStream
	 *            the input stream to read
	 * @return the current version of COASTAL as it appears in the input stream
	 */
	protected static String read(InputStream inputStream) {
		return read(inputStream, true);
	}

	/**
	 * Read the version from the given input stream or use Jgit if the second
	 * parameter is true.
	 * 
	 * @param inputStream
	 *            the input stream to read
	 * @param tryJgit
	 *            whether or not to use Jgit is the resource is not available
	 * @return the current version of COASTAL as it appears in the input stream
	 */
	protected static String read(InputStream inputStream, boolean tryJgit) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException x) {
			// ignore
		}
		return read(properties, tryJgit);
	}

	/**
	 * Read the version from the given Java properties.
	 * 
	 * @param properties
	 *            the Java properties object to read
	 * @return the current version of COASTAL as it appears in the properties
	 */
	protected static String read(Properties properties) {
		return read(properties, true);
	}

	/**
	 * Read the version from the given Java properties or use Jgit if the second
	 * parameter is true.
	 * 
	 * @param properties
	 *            the Java properties object to read
	 * @param tryJgit
	 *            whether or not to use Jgit is the resource is not available
	 * @return the current version of COASTAL as it appears in the properties
	 */
	protected static String read(Properties properties, boolean tryJgit) {
		String v = properties.getProperty("version");
		if ((v != null) && (v.charAt(0) != '%')) {
			return v;
		}
		if (tryJgit) {
			return readJgitVersion();
		} else {
			return DEFAULT_VERSION;
		}
	}

	/**
	 * Use Jgit to compute the current version of COASTAL.
	 * 
	 * @return the current version of COASTAL as computed by Jgit
	 */
	private static String readJgitVersion() {
		try {
			Repository repo = new RepositoryBuilder().readEnvironment().findGitDir().build();
			if (repo != null) {
				@SuppressWarnings("resource")
				String version = new Git(repo).describe().setTags(true).call();
				if (version != null) {
					return version;
				}
			}
		} catch (IOException | GitAPIException x) {
			// ignore
		}
		return DEFAULT_VERSION;
	}

}
