package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;

public class SystemTests {

	protected void checkDivers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getStatLong("Divers.tasks"));
		assertEquals(count, reporter.getStatLong("Divers.count"));
	}

	protected void checkSurfers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getStatLong("Surfers.tasks"));
		assertEquals(count, reporter.getStatLong("Surfers.count"));
	}
	
	protected void checkStrategy(Reporter reporter, int tasks) {
		checkStrategy(reporter, "DepthFirstStrategy", tasks);
	}
	
	protected void checkStrategy(Reporter reporter, String strategy, int tasks) {
		assertEquals(tasks, reporter.getStatLong(strategy + ".tasks"));
	}
	
	protected void checkPathTree(Reporter reporter, int inserted, int revisit, int infeasible) {
		assertEquals(inserted, reporter.getStatLong("PathTree.inserted-count"));
		assertEquals(revisit, reporter.getStatLong("PathTree.revisit-count"));
		assertEquals(infeasible, reporter.getStatLong("PathTree.infeasible-count"));
	}
	
	protected void checkInstrumentation(Reporter reporter, int requests, int cache, int instrumented) {
		assertEquals(requests, reporter.getStatLong("Instrumentation.requests-count"));
		assertEquals(cache, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(instrumented, reporter.getStatLong("Instrumentation.instrumented-count"));
	}
	
	protected void checkInstrumentation(Reporter reporter, int cache, int instrumented) {
		assertEquals(cache, reporter.getStatLong("Instrumentation.cache-hit-count"));
		assertEquals(instrumented, reporter.getStatLong("Instrumentation.instrumented-count"));
	}
	
}
