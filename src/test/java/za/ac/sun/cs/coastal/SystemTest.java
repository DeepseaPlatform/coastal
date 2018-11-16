package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.reporting.ReporterManager;

/**
 * Shared routines for system testing. 
 */
public class SystemTest {

	/**
	 * Run COASTAL and compare its log output to the expected output.
	 * 
	 * @param props
	 *            COASTAL properties
	 * @param expectedFilename
	 *            name of file with expected output
	 */
	protected COASTAL runCoastal(Properties props) {
		final Logger log = new TestLogger();
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertTrue(cb.readFromProperties(props));
		COASTAL coastal = new COASTAL(cb.construct());
		coastal.start();
		return coastal;
	}

}
