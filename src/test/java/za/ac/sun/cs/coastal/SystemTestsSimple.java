package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import za.ac.sun.cs.coastal.reporting.Recorder;

public class SystemTestsSimple extends SystemTest {

	@Test
	public void testStraight01() {
		final Properties props = new Properties();
		props.setProperty("coastal.main", "tests.simple.Straight01");
		props.setProperty("coastal.targets", "tests.simple");
		props.setProperty("coastal.triggers", "tests.simple.Straight01.run(X: int)");
		props.setProperty("coastal.strategy", "za.ac.sun.cs.coastal.strategy.DepthFirstStrategy");
		Recorder recorder = runCoastal(props).getRecorder();
		assertEquals(6, recorder.getLong("instrumentation", "load-requests"));
		assertEquals(0, recorder.getLong("instrumentation", "cache-hits"));
		assertEquals(1, recorder.getLong("instrumentation", "instrumented-classes"));
		assertEquals(722, recorder.getLong("instrumentation", "pre-instrumented-size"));
		assertEquals(1684, recorder.getLong("instrumentation", "post-instrumented-size"));
		assertEquals(2, recorder.getLong("depthfirststrategy", "inserted-paths"));
		assertEquals(0, recorder.getLong("depthfirststrategy", "revisited-paths"));
		assertEquals(1, recorder.getLong("depthfirststrategy", "infeasible-paths"));
	}

	@Test
	public void testChoice01() {
		final Properties props = new Properties();
		props.setProperty("coastal.main", "tests.simple.Choice01");
		props.setProperty("coastal.targets", "tests.simple");
		props.setProperty("coastal.triggers", "tests.simple.Choice01.run(X: int)");
		props.setProperty("coastal.strategy", "za.ac.sun.cs.coastal.strategy.DepthFirstStrategy");
		//runCoastal(props, "tests/simple/Choice01.out");
	}
	
}
