package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsStrings extends SystemTests {

	@Test
	public void testCharAt01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "strings/CharAt01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 2, 0, 0);
		checkInstrumentation(reporter, 20, 5, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2);
	}

	@Test
	public void testStarts01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "strings/Starts01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2, 3);
	}

	@Test
	public void testStartsEnds01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "strings/StartsEnds01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2, 3);
	}

}
