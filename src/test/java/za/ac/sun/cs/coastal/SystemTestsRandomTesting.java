package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SystemTestsRandomTesting extends SystemTests {

	@Test
	public void testRandomTesting01A() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 1");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkInstrumentation(reporter, 505, 200, 1);
		checkMarkerCoverage(reporter, 53, 1);
		checkMarkerCoverage(reporter, 48, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}

	@Test
	public void testRandomTesting01B() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 12");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 73);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 73, 69, 0);
		checkInstrumentation(reporter, 365, 144, 1);
		checkMarkerCoverage(reporter, 39, 1);
		checkMarkerCoverage(reporter, 34, 2);
		checkMarkerCoverage(reporter, 2, 3);
		checkMarkerCoverage(reporter, 71, 4);
	}
	
	@Test
	public void testRandomTesting01C() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 123");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 97, 0);
		checkInstrumentation(reporter, 505, 200, 1);
		checkMarkerCoverage(reporter, 53, 1);
		checkMarkerCoverage(reporter, 48, 2);
		checkMarkerCoverage(reporter, 1, 3);
		checkMarkerCoverage(reporter, 100, 4);
	}
	
	@Test
	public void testRandomTesting01D() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 1234");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkInstrumentation(reporter, 505, 200, 1);
		checkMarkerCoverage(reporter, 50, 1);
		checkMarkerCoverage(reporter, 51, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}
	
	@Test
	public void testRandomTesting01E() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 12345");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 97, 0);
		checkInstrumentation(reporter, 505, 200, 1);
		checkMarkerCoverage(reporter, 45, 1);
		checkMarkerCoverage(reporter, 56, 2);
		checkMarkerCoverage(reporter, 1, 3);
		checkMarkerCoverage(reporter, 100, 4);
	}
	
	@Test
	public void testRandomTesting01F() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.bounds.a.min = 10");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkInstrumentation(reporter, 505, 200, 1);
		checkMarkerCoverage(reporter, 83, 1);
		checkMarkerCoverage(reporter, 18, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}
	
}
