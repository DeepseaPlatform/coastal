package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsObservers extends SystemTests {

	@Test
	public void testInstructionCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/InstructionCoverage01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 96, 35, 1);
		assertEquals(38, reporter.getLong("InstructionCoverage.covered-count"));
		assertEquals(38, reporter.getLong("InstructionCoverage.potential-total"));
	}

	@Test
	public void testLineCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/LineCoverage01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 3);
		checkInstrumentation(reporter, 60, 20, 1);
		assertEquals(9, reporter.getLong("LineCoverage.covered-count"));
		assertEquals(10, reporter.getLong("LineCoverage.potential-total"));
		assertEquals("{21}", reporter.getString("LineCoverage.uncovered"));
	}

	@Test
	public void testMarkerCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/MarkerCoverage01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 96, 35, 1);
		assertEquals(0, reporter.getLong("MarkerCoverage.marker[0]"));
		assertEquals(8, reporter.getLong("MarkerCoverage.marker[1]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[10]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[11]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[100]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[101]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[110]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[111]"));
	}

	@Test
	public void testMarkerCoverage02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/MarkerCoverage02.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 96, 35, 1);
		assertEquals(0, reporter.getLong("MarkerCoverage.marker[0]"));
		assertEquals(8, reporter.getLong("MarkerCoverage.marker[1]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[10]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[11]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[100]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[101]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[110]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[111]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1000]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1001]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1010]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1011]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1100]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1101]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1110]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[1111]"));
	}

	@Test
	public void testMarkerCoverage03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/MarkerCoverage03.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 96, 35, 1);
		assertEquals(0, reporter.getLong("MarkerCoverage.marker[never]"));
		assertEquals(8, reporter.getLong("MarkerCoverage.marker[compute-8]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[<5-4]"));
		assertEquals(4, reporter.getLong("MarkerCoverage.marker[>=5-4]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[<5<7-2]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[<5>=7-2]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[>=5<7-2]"));
		assertEquals(2, reporter.getLong("MarkerCoverage.marker[>=5>=7-2]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[<5<7==9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[<5<7!=9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[<5>=7==9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[<5>=7!=9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[>=5<7==9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[>=5<7!=9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[>=5>=7==9-1]"));
		assertEquals(1, reporter.getLong("MarkerCoverage.marker[>=5>=7!=9-1]"));
	}

	@Test
	public void testStopController01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "observers/StopController01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		// checkPathTree(reporter, 1, 0, 0);
		checkInstrumentation(reporter, 5, 1);
		assertTrue(reporter.getBool("StopController.was-stopped"));
	}

}
