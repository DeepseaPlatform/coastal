package za.ac.sun.cs.coastal.listener;

import java.util.Properties;

import za.ac.sun.cs.coastal.Configuration;

public interface ConfigurationListener extends Listener {

	void configurationLoaded(Configuration configuration);

	void collectProperties(Properties properties);

}
