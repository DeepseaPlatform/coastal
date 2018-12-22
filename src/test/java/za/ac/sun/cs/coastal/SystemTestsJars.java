package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsJars extends SystemTests {

	@Test
	public void testJar00() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, new String[] { "tests/Test01.xml", "tests/jars/testJar00.xml" },
				"<coastal><target><jar directory=\"ZZ\">tests/jars/testJar.jar</jar></target></coastal>");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 28, 6, 1);
	}

	@Test
	public void testJar01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, new String[] { "tests/Test01.xml", "tests/jars/testJar01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 7);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 1);
		checkInstrumentation(reporter, 49, 12, 1);
	}

	@Test
	public void testJar02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, new String[] { "tests/Test01.xml", "tests/jars/testJar02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 28, 6, 1);
	}

	@Test
	public void testJar03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, new String[] { "tests/Test01.xml", "tests/jars/testJar03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 28, 6, 1);
	}
	
}
