package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsSimple {

	/*
	 * NOTE: Tests previously included
	 * 
	 * assertEquals(abc, reporter.getStatLong("Instrumentation.pre-instrumented-size"));
	 * assertEquals(xyz, reporter.getStatLong("Instrumentation.post-instrumented-size"));
	 * 
	 * but Eclipse and gradle uses different compilers that cause such tests to
	 * fail in one or the other.
	 */

	@Test
	public void testStraight01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/simple/Straight01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(1, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatInt("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatInt("COASTAL.strategy-tasks"));
		assertEquals(0, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(7, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(0, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

	@Test
	public void testChoice01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/simple/Choice01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(2, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatInt("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatInt("COASTAL.strategy-tasks"));
		assertEquals(1, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(2, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(14, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(2, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

}
