package za.ac.sun.cs.coastal;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SVComp {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				// working
				{ "jpf-regression", "ExLazy_true", true }, { "jpf-regression", "ExLazy_false", false },
				{ "jpf-regression", "ExGenSymExe_true", true }, { "jpf-regression", "ExGenSymExe_false", false },
				{ "jpf-regression", "ExSymExe1_true", true }, { "jpf-regression", "ExSymExe1_false", false },
				{ "jpf-regression", "ExSymExe2_true", true }, { "jpf-regression", "ExSymExe2_false", false },
				{ "jpf-regression", "ExSymExe3_true", true }, { "jpf-regression", "ExSymExe3_false", false },
				{ "jpf-regression", "ExSymExe4_true", true }, { "jpf-regression", "ExSymExe4_false", false },
				{ "jpf-regression", "ExSymExe5_true", true }, { "jpf-regression", "ExSymExe5_false", false },
				{ "jpf-regression", "ExSymExe6_true", true }, { "jpf-regression", "ExSymExe7_true", true },
				{ "jpf-regression", "ExSymExe7_false", false }, { "jpf-regression", "ExSymExe8_true", true },
				{ "jpf-regression", "ExSymExe8_false", false }, { "jpf-regression", "ExSymExe9_true", true },
				{ "jpf-regression", "ExSymExe9_false", false }, { "jpf-regression", "ExSymExe12_true", true },
				{ "jpf-regression", "ExSymExe12_false", false }, { "jpf-regression", "ExSymExe13_true", true },
				{ "jpf-regression", "ExSymExe13_false", false }, { "jpf-regression", "ExSymExe14_true", true },
				{ "jpf-regression", "ExSymExe14_false", false }, { "jpf-regression", "ExSymExe15_true", true },
				{ "jpf-regression", "ExSymExe15_false", false }, { "jpf-regression", "ExSymExe16_true", true },
				{ "jpf-regression", "ExSymExe16_false", false }, { "jpf-regression", "ExSymExe17_true", true },
				{ "jpf-regression", "ExSymExe17_false", false }, { "jpf-regression", "ExSymExe18_true", true },
				{ "jpf-regression", "ExSymExe18_false", false }, { "jpf-regression", "ExSymExe25_true", true },
				{ "jpf-regression", "ExSymExe25_false", false }, { "jpf-regression", "ExSymExe27_true", true },
				{ "jpf-regression", "ExSymExe27_false", false }, { "jpf-regression", "ExSymExe28_true", true },
				{ "jpf-regression", "ExSymExe28_false", false }, { "jpf-regression", "ExSymExeArrays_true", true },
				{ "jpf-regression", "ExSymExeArrays_false", false }, { "jpf-regression", "ExSymExeBool_true", true },
				{ "jpf-regression", "ExSymExeBool_false", false }, { "jpf-regression", "ExSymExeSuzette_true", true },
				{ "jpf-regression", "ExSymExeSuzette_false", false },
				{ "jpf-regression", "ExSymExeTestAssignments_true", true },
				{ "jpf-regression", "ExSymExeTestAssignments_false", false },
				{ "jpf-regression", "ExSymExeTestClassFields_true", true },
				{ "jpf-regression", "ExSymExeTestClassFields_false", false },

				// Not working
//			{ "jpf-regression", "ExSymExe6_false", false }, // Divide by zero
//			{ "jpf-regression", "ExSymExeGetStatic_true", true }, // NoClassDefFoundError
//			{ "jpf-regression", "ExSymExeGetStatic_false", false }, // NoClassDefFoundError 

		});
	}

	private String testDir;

	private String testSubDir;

	private boolean testExpectedOutcome;

	public SVComp(String dir, String subDir, boolean expectedOutcome) {
		this.testDir = dir;
		this.testSubDir = subDir;
		this.testExpectedOutcome = expectedOutcome;
	}

	@Test
	public void testSVComp() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		ImmutableConfiguration config = ConfigHelper.loadConfiguration(log,
				new String[] { "tests/jars/" + testDir + ".xml" }, "<coastal><target><jar directory=\"tmp/" + testSubDir
						+ "\">tests/jars/" + testDir + ".jar</jar></target></coastal>");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		assertNotEquals(reporter.getBool("AssertController.assert-failed"), testExpectedOutcome);
	}
}
