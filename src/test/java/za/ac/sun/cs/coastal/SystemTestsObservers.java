package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsObservers {

	@Test
	public void testInstructionCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/InstructionCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(7, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(8, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(56, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
		assertEquals(176, reporter.getStatLong("InstructionCoverage.covered-count"));
		assertEquals(304, reporter.getStatLong("InstructionCoverage.potential-total"));
	}

	@Test
	public void testMarkerCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/MarkerCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.dive-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(7, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(8, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(56, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
		assertEquals(0, reporter.getStatLong("MarkerCoverage.marker[0]"));
		assertEquals(8, reporter.getStatLong("MarkerCoverage.marker[1]"));
		assertEquals(4, reporter.getStatLong("MarkerCoverage.marker[10]"));
		assertEquals(4, reporter.getStatLong("MarkerCoverage.marker[11]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[100]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[101]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[110]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[111]"));
	}
	
}
