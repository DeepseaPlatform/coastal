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
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
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
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(2, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(14, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(2, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

	@Test
	public void testChoice02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/simple/Choice02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(4, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(6, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(28, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(4, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
	@Test
	public void testChoice03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/simple/Choice03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(14, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(56, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
	@Test
	public void testChoice04() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/simple/Choice04.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(5, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(8, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(35, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(1, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(6, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
}
