package za.ac.sun.cs.coastal.listener;

import java.util.Properties;

import za.ac.sun.cs.coastal.ConfigurationX;

public interface ConfigurationListener extends Listener {

	void configurationLoaded(ConfigurationX configuration);

	void collectProperties(Properties properties);

}
