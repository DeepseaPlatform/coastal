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
 * System tests for arrays.
 */
public class SystemTestsArrays extends SystemTests {

    @Test
    public void testArray01() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array01.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
    }

    @Ignore
    @Test
    public void testArray02() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array02.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 6);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 9, 3, 0);
    }

    @Test
    public void testArray03() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array03.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
    }

    @Test
    public void testArray04() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array04.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
    }

    @Test
    public void testArray05() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array05.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
    }

    @Test
    public void testArray06() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log, new String[]{"Test01.properties", "arrays/Array06.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
    }

//	@Test
//	public void testSorting01() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		Configuration config = Configuration.load(log, new String[] { "Test01.properties", "arrays/Sorting01.properties" });
//		assertNotNull(config);
//		COASTAL coastal = new COASTAL(log, config);
//		coastal.start(false);
//		Reporter reporter = coastal.getReporter();
//		checkDivers(reporter, 1, 11);
//		checkSurfers(reporter, 0, 0);
//		checkPathTree(reporter, 33, 0, 22);
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
