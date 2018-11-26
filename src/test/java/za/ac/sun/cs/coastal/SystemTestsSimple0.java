package za.ac.sun.cs.coastal;

import java.util.Properties;

import org.junit.Test;

public class SystemTestsSimple0 extends SystemTest {

	@Test
	public void testStraight01() {
		final Properties props = new Properties();
		props.setProperty("coastal.main", "tests.simple.Straight01");
		props.setProperty("coastal.targets", "tests.simple");
		props.setProperty("coastal.triggers", "tests.simple.Straight01.run(X: int)");
		props.setProperty("coastal.strategy", "za.ac.sun.cs.coastal.strategy.DepthFirstStrategy");
		runCoastal(props, "tests/simple/Straight01.out");
	}

	@Test
	public void testChoice01() {
		final Properties props = new Properties();
		props.setProperty("coastal.main", "tests.simple.Choice01");
		props.setProperty("coastal.targets", "tests.simple");
		props.setProperty("coastal.triggers", "tests.simple.Choice01.run(X: int)");
		props.setProperty("coastal.strategy", "za.ac.sun.cs.coastal.strategy.DepthFirstStrategy");
		runCoastal(props, "tests/simple/Choice01.out");
	}
	
}
