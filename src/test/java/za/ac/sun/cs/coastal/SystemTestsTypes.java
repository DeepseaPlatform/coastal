package za.ac.sun.cs.coastal;

import org.junit.Ignore;
import org.junit.Test;

public class SystemTestsTypes extends SystemTests {

	@Test
	public void testBoolean01() {
		Reporter reporter = setup("Test01.properties", "types/Boolean01.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 2);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 9, 18, 29);
		checkMarkerCoverage(reporter, 1, 16, 20, 25, 27);
		checkMarkerCoverage(reporter, 2, 14, 23);
	}

	@Test
	public void testByte01() {
		Reporter reporter = setup("Test01.properties", "types/Byte01.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 2);
		checkInstrumentation(reporter, 40, 15, 1);
		checkMarkerCoverage(reporter, 0, 9, 16, 25);
		checkMarkerCoverage(reporter, 1, 18, 20, 27, 29);
		checkMarkerCoverage(reporter, 2, 14, 23);
	}

	@Test
	public void testChar01() {
		Reporter reporter = setup("Test01.properties", "types/Char01.properties");
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 1);
		checkInstrumentation(reporter, 50, 20, 1);
		checkMarkerCoverage(reporter, 0, 9, 25);
		checkMarkerCoverage(reporter, 1, 16, 18, 20, 27, 29);
		checkMarkerCoverage(reporter, 2, 23);
		checkMarkerCoverage(reporter, 3, 14);
	}
	
	@Test
	public void testShort01() {
		Reporter reporter = setup("Test01.properties", "types/Short01.properties");
		checkDivers(reporter, 1, 3);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 3);
		checkInstrumentation(reporter, 31, 10, 1);
		checkMarkerCoverage(reporter, 0, 10, 17, 19, 28);
		checkMarkerCoverage(reporter, 1, 15, 21, 26, 30);
		checkMarkerCoverage(reporter, 2, 24);
	}

	@Test
	public void testInt01() {
		Reporter reporter = setup("Test01.properties", "types/Int01.properties");
		checkDivers(reporter, 1, 13);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 13, 0, 0);
		checkInstrumentation(reporter, 142, 60, 1);
		checkMarkerCoverage(reporter, 0, 0);
		checkMarkerCoverage(reporter, 1, 1);
		checkMarkerCoverage(reporter, 3, 5, 6, 7, 8);
		checkMarkerCoverage(reporter, 4, 3, 4);
		checkMarkerCoverage(reporter, 12, 2);
	}
	
	@Test
	public void testLong01() {
		Reporter reporter = setup("Test01.properties", "types/Long01.properties");
		checkDivers(reporter, 1, 6);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 0);
		checkInstrumentation(reporter, 64, 25, 1);
		checkMarkerCoverage(reporter, 0, 9);
		checkMarkerCoverage(reporter, 1, 17, 19, 24, 26, 28, 30);
		checkMarkerCoverage(reporter, 2, 15);
		checkMarkerCoverage(reporter, 4, 22);
	}

	@Ignore @Test
	public void testFloat01() {
		Reporter reporter = setup("Test01.properties", "types/Float01.properties");
		checkDivers(reporter, 1, 5);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 1);
		checkInstrumentation(reporter, 32, 8, 1);
		checkMarkerCoverage(reporter, 0, 1000, 1004);
		checkMarkerCoverage(reporter, 1, 1002, 1003, 1006, 1007);
		checkMarkerCoverage(reporter, 2, 1001);
		checkMarkerCoverage(reporter, 3, 1005);
	}
	
	@Ignore @Test
	public void testFloat02() {
		Reporter reporter = setup("Test01.properties", "types/Float02.properties");
		checkDivers(reporter, 1, 6);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 0);
		checkInstrumentation(reporter, 36, 10, 1);
		checkMarkerCoverage(reporter, 0, 9);
		checkMarkerCoverage(reporter, 1, 17, 19, 21, 26, 28);
		checkMarkerCoverage(reporter, 3, 14, 24);
	}

	@Test
	public void testDouble01() {
		Reporter reporter = setup("Test01.properties", "types/Double01.properties");
		checkDivers(reporter, 1, 6);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 0);
		checkInstrumentation(reporter, 66, 25, 1);
		checkMarkerCoverage(reporter, 0, 0, 21);
		checkMarkerCoverage(reporter, 1, 222, 333, 444, 666, 777);
		checkMarkerCoverage(reporter, 3, 111, 555);
	}
	
	@Test
	public void testMixed01() {
		Reporter reporter = setup("Test01.properties", "types/Mixed01.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 2);
		checkInstrumentation(reporter, 41, 15, 1);
		checkMarkerCoverage(reporter, 0, 10, 17, 19);
		checkMarkerCoverage(reporter, 1, 15, 21, 26, 28, 30);
		checkMarkerCoverage(reporter, 3, 24);
	}

	@Test
	public void testMixed02() {
		Reporter reporter = setup("Test01.properties", "types/Mixed02.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 2);
		checkInstrumentation(reporter, 41, 15, 1);
		checkMarkerCoverage(reporter, 0, 1000, 1002, 1003);
		checkMarkerCoverage(reporter, 1, 1001, 1004, 1006, 1007, 1008);
		checkMarkerCoverage(reporter, 3, 1005);
	}

	@Test
	public void testMixed03() {
		Reporter reporter = setup("Test01.properties", "types/Mixed03.properties");
		checkDivers(reporter, 1, 4);
		checkSurfers(reporter, 0, 0);
		checkDFStrategy(reporter, 1);
		checkPathTree(reporter, 6, 0, 2);
		checkInstrumentation(reporter, 44, 15, 1);
		checkMarkerCoverage(reporter, 0, 1000, 1002, 1003);
		checkMarkerCoverage(reporter, 1, 1001, 1004, 1006, 1007, 1008);
		checkMarkerCoverage(reporter, 3, 1005);
	}
	
}
