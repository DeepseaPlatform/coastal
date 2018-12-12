package za.ac.sun.cs.coastal;

import static org.junit.Assert.*;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SVComp {
	
	@Test
	public void testJar00() {
		final Logger log = LogManager.getLogger("COASTAL");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/jars/jpf-regression.xml" },
				"<coastal><jar directory=\"tmp/ExLazy_true\">tests/jars/jpf-regression.jar</jar></coastal>");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		System.out.println("test");
		coastal.start(false);
		System.out.println("test2");
		Reporter reporter = coastal.getReporter();
		assertFalse(reporter.getStatBool("AssertController.assert-failed"));
//		assertEquals(4, reporter.getStatLong("COASTAL.dive-count"));
//		assertEquals(1, reporter.getStatLong("COASTAL.diver-tasks"));
//		assertEquals(1, reporter.getStatLong("COASTAL.strategy-tasks"));
//		assertEquals(6, reporter.getStatLong("Instrumentation.cache-hit-count"));
//		assertEquals(1, reporter.getStatLong("Instrumentation.instrumented-count"));
//		assertEquals(28, reporter.getStatLong("Instrumentation.requests-count"));
//		assertEquals(0, reporter.getStatLong("Strategy.infeasible-count"));
//		assertEquals(4, reporter.getStatLong("Strategy.inserted-paths"));
//		assertEquals(0, reporter.getStatLong("Strategy.revisited-paths"));
	}
}
