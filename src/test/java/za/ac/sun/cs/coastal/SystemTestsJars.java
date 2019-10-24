package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SystemTestsJars extends SystemTests {

	@Test
	public void testJar00() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "jars/testJar00.properties" },
				"coastal.target.jars = jars/testJar.jar\ncoastal.target.jars.directory = ZZ");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 48, 15, 1);
	}

	@Test
	public void testJar01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "jars/testJar01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 7);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 1);
		checkInstrumentation(reporter, 84, 30, 1);
	}

	@Test
	public void testJar02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "jars/testJar02.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 48, 15, 1);
	}

	@Test
	public void testJar03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "jars/testJar03.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 48, 15, 1);
	}
	
}
