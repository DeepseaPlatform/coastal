package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		assertEquals(8, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(14, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(56, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
		assertEquals(38, reporter.getStatLong("InstructionCoverage.covered-count"));
		assertEquals(38, reporter.getStatLong("InstructionCoverage.potential-total"));
	}

	@Test
	public void testMarkerCoverage01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/MarkerCoverage01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(14, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
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
	
	@Test
	public void testMarkerCoverage02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/MarkerCoverage02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(14, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
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
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1000]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1001]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1010]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1011]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1100]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1101]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1110]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[1111]"));
	}
	
	@Test
	public void testMarkerCoverage03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/MarkerCoverage03.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(8, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(14, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(56, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
		assertEquals(0, reporter.getStatLong("MarkerCoverage.marker[never]"));
		assertEquals(8, reporter.getStatLong("MarkerCoverage.marker[compute-8]"));
		assertEquals(4, reporter.getStatLong("MarkerCoverage.marker[<5-4]"));
		assertEquals(4, reporter.getStatLong("MarkerCoverage.marker[>=5-4]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[<5<7-2]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[<5>=7-2]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[>=5<7-2]"));
		assertEquals(2, reporter.getStatLong("MarkerCoverage.marker[>=5>=7-2]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[<5<7==9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[<5<7!=9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[<5>=7==9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[<5>=7!=9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[>=5<7==9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[>=5<7!=9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[>=5>=7==9-1]"));
		assertEquals(1, reporter.getStatLong("MarkerCoverage.marker[>=5>=7!=9-1]"));
	}
	
	@Test
	public void testStopController01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/observers/StopController01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(2, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(2, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		long requestDelta = Math.abs(reporter.getStatLong("Instrumentation.requests-count") - 12);
		assertTrue(requestDelta < 4);
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(1, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
	
}
