package za.ac.sun.cs.coastal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class Version {

	protected static final String DEFAULT_APP_PROPERTIES = "app.properties";

	protected static final String DEFAULT_VERSION = "unspecified";

	protected static String version = null;

	public static String read() {
		if (version == null) {
			version = read(DEFAULT_APP_PROPERTIES);
		}
		return version;
	}

	protected static String read(String resourceName) {
		return read(resourceName, true);
	}
	
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
	
	protected static String read(InputStream inputStream) {
		return read(inputStream, true);
	}

	protected static String read(InputStream inputStream, boolean tryJgit) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException x) {
			// ignore
		}
		return read(properties, tryJgit);
	}

	protected static String read(Properties properties) {
		return read(properties, true);
	}

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
