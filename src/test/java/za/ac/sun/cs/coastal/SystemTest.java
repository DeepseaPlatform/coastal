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
	protected void runCoastal(Properties props, String expectedFilename) {
		final Logger log = new TestLogger();
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
		assertTrue(cb.readFromProperties(props));
		new COASTAL(cb.construct()).start();
		checkOutput(log, expectedFilename);
	}

	/**
	 * Compare the contents of the logger and the given file and check whether
	 * they "match up". This means that all of the lines in the file occur in
	 * the log, in the same order. The log may contain intervening lines, but
	 * this routine is not very clever in how it matches the two outputs. In
	 * particular, if an actual line in the output does not match the next
	 * expected line, the actual line is discarded and the comparison moves on
	 * to the *same* expected line but the *next* actual line.
	 * 
	 * @param log
	 *            the logger with the COASTAL output
	 * @param expectedFilename
	 *            the file with the expected output
	 */
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
			String exp = expected[i].trim();
			String act = actual[j].trim();
			System.out.println("MATCHING " + i + ":" + prefix(exp) + " WITH " + j + ":" + prefix(act));
			if (act.startsWith(exp)) {
				i++;
			}
			j++;
		}
		if (j < m) {
			assertEquals(actual[j], "");
		} else if (i < n) {
			assertEquals(expected[i], "");
		}
	}

	/**
	 * Return a prefix (of at most 5 characters) of a string.
	 *  
	 * @param s the string to process
	 * @return the prefix
	 */
	private static String prefix(String s) {
		if (s.length() > 5) {
			return s.substring(0, 5);
		} else {
			return s;
		}
	}
}
