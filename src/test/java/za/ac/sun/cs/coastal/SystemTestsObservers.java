package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsObservers extends SystemTests {

	@Test
	public void testInstructionCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/InstructionCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 56, 14, 1);
		assertEquals(38, reporter.getLong("InstructionCoverage.covered-count"));
		assertEquals(38, reporter.getLong("InstructionCoverage.potential-total"));
	}

	@Test
	public void testLineCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/LineCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 3);
		checkInstrumentation(reporter, 35, 8, 1);
		assertEquals(9, reporter.getLong("LineCoverage.covered-count"));
		assertEquals(10, reporter.getLong("LineCoverage.potential-total"));
		assertEquals("{21}", reporter.getString("LineCoverage.uncovered"));
	}
	
	@Test
	public void testMarkerCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/MarkerCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 56, 14, 1);
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
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/MarkerCoverage02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 56, 14, 1);
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
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/MarkerCoverage03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 56, 14, 1);
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
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/observers/StopController01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 1, 0, 0);
		checkInstrumentation(reporter, 2, 1);
		assertTrue(reporter.getBool("StopController.was-stopped"));
	}

}
