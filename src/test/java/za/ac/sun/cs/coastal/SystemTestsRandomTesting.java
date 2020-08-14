/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

/**
 * System tests that the random testing strategy works as expected.
 */
@Ignore
public class SystemTestsRandomTesting extends SystemTests {

	@Test
	public void testRandomTesting01A() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 1");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkMarkerCoverage(reporter, 53, 1);
		checkMarkerCoverage(reporter, 48, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}

	@Test
	public void testRandomTesting01B() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 12");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 73);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 73, 69, 0);
		checkMarkerCoverage(reporter, 39, 1);
		checkMarkerCoverage(reporter, 34, 2);
		checkMarkerCoverage(reporter, 2, 3);
		checkMarkerCoverage(reporter, 71, 4);
	}
	
	@Test
	public void testRandomTesting01C() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 123");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 97, 0);
		checkMarkerCoverage(reporter, 53, 1);
		checkMarkerCoverage(reporter, 48, 2);
		checkMarkerCoverage(reporter, 1, 3);
		checkMarkerCoverage(reporter, 100, 4);
	}
	
	@Test
	public void testRandomTesting01D() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 1234");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkMarkerCoverage(reporter, 50, 1);
		checkMarkerCoverage(reporter, 51, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}
	
	@Test
	public void testRandomTesting01E() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.strategies.S.seed = 12345");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 97, 0);
		checkMarkerCoverage(reporter, 45, 1);
		checkMarkerCoverage(reporter, 56, 2);
		checkMarkerCoverage(reporter, 1, 3);
		checkMarkerCoverage(reporter, 100, 4);
	}
	
	@Test
	public void testRandomTesting01F() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log,
				new String[] { "Test01.properties", "randomtesting/RandomTesting01.properties" },
				"coastal.bounds.a.min = 10");
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		Reporter reporter = coastal.getReporter();
		checkDivers(reporter, 0, 0);
		checkSurfers(reporter, 1, 101);
		checkRandomTestingStrategy(reporter, 1);
		checkPathTree(reporter, 100, 98, 0);
		checkMarkerCoverage(reporter, 83, 1);
		checkMarkerCoverage(reporter, 18, 2);
		checkMarkerCoverage(reporter, 0, 3);
		checkMarkerCoverage(reporter, 101, 4);
	}
	
}
