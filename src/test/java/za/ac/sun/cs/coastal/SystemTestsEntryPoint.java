package za.ac.sun.cs.coastal;

import org.junit.Test;

public class SystemTestsEntryPoint extends SystemTests {

	@Test
	public void testEntryPoint01A() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-A.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}

	@Test
	public void testEntryPoint01B() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-B.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01C() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-C.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01D() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-D.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01E() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-E.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01F() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-F.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint01G() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint01-G.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 20, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint02A() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint02-A.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 4, 0, 0);
		checkInstrumentation(reporter, 24, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}
	
	@Test
	public void testEntryPoint03A() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint03-A.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 4);
		checkInstrumentation(reporter, 24, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
	}

	@Test
	public void testEntryPoint03B() {
		Reporter reporter = setup("tests/Test01.xml", "tests/entrypoint/EntryPoint03-B.xml");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 8, 0, 4);
		checkInstrumentation(reporter, 24, 6, 1);
		checkMarkerCoverage(reporter, 0, 0, 1);
		checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
		checkMarkerCoverage(reporter, 4, 6);
	}
	
}
