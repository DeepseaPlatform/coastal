package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsJars {

	@Test
	public void testJar00() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/jars/testJar00.xml" },
				"<coastal><jar directory=\"ZZ\">tests/jars/testJar.jar</jar></coastal>");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(4, reporter.getStatLong("COASTAL.diver-count"));
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
	public void testJar01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/jars/testJar01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(7, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(12, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(49, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(1, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(8, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

	@Test
	public void testJar02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/jars/testJar02.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertEquals(4, reporter.getStatLong("COASTAL.diver-count"));
		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
		assertEquals(6, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
		assertEquals(28, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
		assertEquals(4, reporter.getStatLong("Strategy.inserted-paths"));
		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}

}
