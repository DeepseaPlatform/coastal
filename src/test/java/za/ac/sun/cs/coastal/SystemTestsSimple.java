package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
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
	 * but Eclipse and gradle uses different compilers that cause such tests to
	 * fail in one or the other.
	 */

	@Test
	public void testStraight01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/simple/Straight01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 1);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 0, 0, 0);
		checkInstrumentation(reporter, 7, 0, 1);
	}

	@Test
	public void testChoice01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/simple/Choice01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 2, 0, 0);
		checkInstrumentation(reporter, 14, 2, 1);
	}

	@Test
	public void testChoice02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/simple/Choice02.xml" });
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
	public void testChoice03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/simple/Choice03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 8);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 0);
		checkInstrumentation(reporter, 56, 14, 1);
	}

	@Test
	public void testChoice04() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/Test01.xml", "tests/simple/Choice04.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 1);
		checkInstrumentation(reporter, 35, 8, 1);
	}

}
