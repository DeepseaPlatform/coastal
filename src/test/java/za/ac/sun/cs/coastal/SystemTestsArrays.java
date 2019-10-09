package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTestsArrays extends SystemTests {

	@Test
	public void testArray01() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array01.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
	}

	@Test
	public void testArray02() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array02.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 39, 10, 1);
	}
	
	@Test
	public void testArray03() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array03.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
	}
	
	@Test
	public void testArray04() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array04.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
	}
	
	@Test
	public void testArray05() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array05.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
	}
	
	@Test
	public void testArray06() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Array06.properties" });
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 3, 0, 0);
		checkInstrumentation(reporter, 30, 10, 1);
	}
	
//	@Test
//	public void testSorting01() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "Test01.properties", "arrays/Sorting01.properties" });
//		assertNotNull(config);
//		COASTAL coastal = new COASTAL(log, config);
//		coastal.start(false);
//		Reporter reporter = coastal.getReporter();
//		checkDivers(reporter, 1, 11);
//		checkSurfers(reporter, 0, 0);
//		checkStrategy(reporter, 1);
//		checkPathTree(reporter, 33, 0, 22);
//		checkInstrumentation(reporter, 88, 20, 1);
//	}
//	
//	@Test
//	public void testSorting02() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		ImmutableConfiguration config = COASTAL.loadConfiguration(log, new String[] { "Test01.properties", "arrays/Sorting02.properties" });
//		assertNotNull(config);
//		COASTAL coastal = new COASTAL(log, config);
//		coastal.start(false);
//		Reporter reporter = coastal.getReporter();
//		checkDivers(reporter, 1, 11);
//		checkSurfers(reporter, 0, 0);
//		checkStrategy(reporter, 1);
//		checkPathTree(reporter, 33, 0, 22);
//		checkInstrumentation(reporter, 99, 30, 2);
//	}
	
}
