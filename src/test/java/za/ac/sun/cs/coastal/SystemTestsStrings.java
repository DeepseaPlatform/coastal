package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsStrings extends SystemTests {

	@Test
	public void testCharAt01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "tests/Test01.xml", "tests/strings/CharAt01.xml" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 2);
		checkSurfers(reporter, 0, 0);
		checkStrategy(reporter, 1);
		checkPathTree(reporter, 2, 0, 0);
		checkInstrumentation(reporter, 14, 2, 1);
	}

}
