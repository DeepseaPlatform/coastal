package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsStrings extends SystemTests {

	@Test
	public void testCharAt01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/strings/CharAt01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 2, 0, 0);
		checkInstrumentation(reporter, 10, 2, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2);
	}

	@Test
	public void testStarts01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/strings/Starts01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 15, 4, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2, 3);
	}
	
	@Test
	public void testStartsEnds01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/strings/StartsEnds01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 15, 4, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1, 2, 3);
	}

}
