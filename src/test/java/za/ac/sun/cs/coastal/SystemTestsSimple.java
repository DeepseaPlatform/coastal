package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsSimple extends SystemTests {

	/*
	 * NOTE: Tests previously included
	 * 
	 * assertEquals(abc,
	 * reporter.getStatLong("Instrumentation.pre-instrumented-size"));
	 * assertEquals(xyz,
	 * reporter.getStatLong("Instrumentation.post-instrumented-size"));
	 * 
	 * but Eclipse and gradle uses different compilers that cause such tests to fail
	 * in one or the other.
	 */

	@Test
	public void testStraight01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "simple/Straight01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 1);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 0, 0, 0);
		checkInstrumentation(reporter, 12, 0, 1);
	}

	@Test
	public void testChoice01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "simple/Choice01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 2, 0, 0);
		checkInstrumentation(reporter, 24, 5, 1);
	}

	@Test
	public void testChoice02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "simple/Choice02.properties" });
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
	public void testChoice03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "simple/Choice03.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 96, 35, 1);
	}

	@Test
	public void testChoice04() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "simple/Choice04.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 1);
		checkInstrumentation(reporter, 60, 20, 1);
	}

}
