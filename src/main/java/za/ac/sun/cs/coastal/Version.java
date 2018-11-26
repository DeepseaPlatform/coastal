package za.ac.sun.cs.coastal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class Version {

	protected static final String DEFAULT_APP_PROPERTIES = "app.properties";

	protected static final String DEFAULT_VERSION = "unspecified";

	protected String version = null;

	public String read() {
		if (version == null) {
			version = read(DEFAULT_APP_PROPERTIES);
		}
		return version;
	}

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

	protected String read(InputStream inputStream) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException x) {
			// ignore
		}
		return read(properties);
	}

	protected String read(Properties properties) {
		String v = properties.getProperty("version");
		if ((v != null) && (v.charAt(0) != '%')) {
			return v;
		}
		try {
			Repository repo = new RepositoryBuilder().readEnvironment().findGitDir().build();
			RefDatabase refs = repo.getRefDatabase();
			Collection<Ref> tagList = refs.getRefsByPrefix(Constants.R_TAGS);
//			String version = Integer.toHexString(System.identityHashCode(repo));
			@SuppressWarnings("resource")
			String version = new Git(repo).describe().setTags(true).call();
			return version;
		} catch (IOException | GitAPIException x) {
			// ignore
		}
		return DEFAULT_VERSION;
	}

}
