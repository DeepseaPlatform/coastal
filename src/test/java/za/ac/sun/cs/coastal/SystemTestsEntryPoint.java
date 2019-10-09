package za.ac.sun.cs.coastal;

import org.junit.Test;

public class SystemTestsEntryPoint extends SystemTests {

	@Test
	public void testEntryPoint01A() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-A.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}

	@Test
	public void testEntryPoint01B() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-B.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01C() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-C.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01D() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-D.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01E() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-E.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01F() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-F.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01G() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-G.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint02A() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint02-A.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 44, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint03A() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint03-A.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 44, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}

	@Test
	public void testEntryPoint03B() {
		Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint03-B.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 44, 15, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
		checkMarkerCoverage(reporter, 4, 6);
	}
	
}
