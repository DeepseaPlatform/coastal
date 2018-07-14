package za.ac.sun.cs.coastal.listener;

import java.util.Properties;

public interface ConfigurationListener {

	void configurationLoaded(Properties properties);

	void configurationDump();

}
