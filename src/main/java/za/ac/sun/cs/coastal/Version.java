package za.ac.sun.cs.coastal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
			return read(resourceStream);
		} catch (IOException x) {
			// ignore
		}
		return DEFAULT_VERSION;
	}

	protected String read(InputStream inStream) {
		Properties props = new Properties();
		try {
			props.load(inStream);
		} catch (IOException x) {
			// ignore
		}
		return read(props);
	}

	protected String read(Properties props) {
		String v = props.getProperty("version");
		if ((v != null) && (v.charAt(0) != '%')) {
			return v;
		}
		return DEFAULT_VERSION;
	}

}
