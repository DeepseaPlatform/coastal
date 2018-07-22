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
		int n = expected.length, i = 0; 
		int m = actual.length, j = 0;
		while ((i < n) && (j < m)) {
			boolean mustMatch = true;
			String exp = expected[i].trim();
			String act = actual[j].trim();
			if (exp.charAt(0) == '?') {
				mustMatch = false;
				exp = expected[i].substring(1).trim();
			}
			if (act.startsWith(exp)) {
				i++;
				j++;
			} else if (!mustMatch) {
				i++;
			} else if (exp.length() < act.length()) {
				act = actual[j].substring(0, exp.length());
				assertEquals(exp, act);
			} else {
				assertEquals(exp, act);
			}
		}
		if (n > m) {
			assertEquals(expected[m], "");
		} else if (m > n) {
			assertEquals("", actual[n]);
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
