package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemTests {

	protected Reporter setup(String basic, String special) {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { basic, special });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		return coastal.getReporter();
	}

	protected void checkDivers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getLong("Divers.tasks"));
		assertEquals(count, reporter.getLong("Divers.count"));
	}

	protected void checkSurfers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getLong("Surfers.tasks"));
		assertEquals(count, reporter.getLong("Surfers.count"));
	}

	protected void checkDFStrategy(Reporter reporter, int tasks) {
		checkStrategy(reporter, "DepthFirstStrategy", tasks);
	}

	protected void checkRandomTestingStrategy(Reporter reporter, int tasks) {
		checkStrategy(reporter, "RandomTesting", tasks);
	}
	
	protected void checkStrategy(Reporter reporter, String strategy, int tasks) {
		assertEquals(tasks, reporter.getLong(strategy + ".tasks"));
	}

	protected void checkPathTree(Reporter reporter, int inserted, int revisit, int infeasible) {
		assertEquals(inserted, reporter.getLong("PathTree.inserted-count"));
		assertEquals(revisit, reporter.getLong("PathTree.revisit-count"));
		assertEquals(infeasible, reporter.getLong("PathTree.infeasible-count"));
	}

	protected void checkInstrumentation(Reporter reporter, int requests, int cache, int instrumented) {
		assertEquals(requests, reporter.getLong("Instrumentation.requests-count"));
		assertEquals(cache, reporter.getLong("Instrumentation.cache-hit-count"));
		assertEquals(instrumented, reporter.getLong("Instrumentation.instrumented-count"));
	}

	protected void checkInstrumentation(Reporter reporter, int cache, int instrumented) {
		assertEquals(cache, reporter.getLong("Instrumentation.cache-hit-count"));
		assertEquals(instrumented, reporter.getLong("Instrumentation.instrumented-count"));
	}

	protected void checkMarkerCoverage(Reporter reporter, int count, int... markers) {
		for (int marker : markers) {
			assertEquals(count, reporter.getLong("MarkerCoverage.marker[" + marker + "]"));
		}
	}

	protected void checkMarkerCoverage(Reporter reporter, int count, String... markers) {
		for (String marker : markers) {
			assertEquals(count, reporter.getLong("MarkerCoverage.marker[" + marker + "]"));
		}
	}
	
}
