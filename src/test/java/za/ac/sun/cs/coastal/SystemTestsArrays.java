package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsArrays {

	@Test
	public void testArray01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/arrays/Array01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(3, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(4, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(15, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(3, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

	@Test
	public void testArray02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/arrays/Array02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(3, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(4, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(15, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(3, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
	@Test
	public void testArray03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/arrays/Array03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(3, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(4, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(15, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(3, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
	@Test
	public void testArray04() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/arrays/Array04.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(3, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(4, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(15, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(3, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
}
