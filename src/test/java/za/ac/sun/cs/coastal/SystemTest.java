package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.reporting.ReporterManager;

public class SystemTest {

	private void checkOutput(Logger log, String expectedFilename) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String expectedOutput = "";
		try (InputStream resourceStream = loader.getResourceAsStream(expectedFilename)) {
			if (resourceStream != null) {
				try (Scanner s = new Scanner(resourceStream)) {
					s.useDelimiter("\\A");
					if (s.hasNext()) {
						expectedOutput = s.next();
					}
				}
			}
		} catch (IOException x) {
			fail();
		}
		String[] expected = expectedOutput.split("\n");
		String[] actual = log.toString().split("\n");
		int n = expected.length; 
		assertEquals(n, actual.length);
		for (int i = 0; i < n; i++) {
			String prefix = actual[i].substring(0, expected[i].length());
			assertEquals(expected[i], prefix);
		}
	}

	protected void runCoastal(Properties props, String expectedFilename) {
		final Logger log = new TestLogger();
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertTrue(cb.readFromProperties(props));
		new COASTAL(cb.construct()).start();
		checkOutput(log, expectedFilename);
	}

}
