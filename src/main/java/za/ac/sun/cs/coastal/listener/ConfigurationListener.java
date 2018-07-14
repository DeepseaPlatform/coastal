package za.ac.sun.cs.coastal.listener;

import java.util.Properties;

public interface ConfigurationListener extends Listener {

	void configurationLoaded(Properties properties);

	void configurationDump();

}
