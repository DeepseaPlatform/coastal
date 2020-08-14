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
import org.junit.Test;

/**
 * System tests for strings.
 *
 * !NOTE: This class needs additional tests for newer methods.
 */
public class SystemTestsStrings extends SystemTests {

    @Test
    public void testCharAt01() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log,
                new String[]{"Test01.properties", "strings/CharAt01.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 2);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 2, 0, 0);
        checkMarkerCoverage(reporter, 0, 0);
        checkMarkerCoverage(reporter, 1, 1, 2);
    }

    @Test
    public void testCreateString01() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log,
                new String[]{"Test01.properties", "strings/CreateString01.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 2);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 2, 0, 0);
        checkMarkerCoverage(reporter, 1, 1, 2);
        checkMarkerCoverage(reporter, 2, 0, 3);
    }

    @Test
    public void testStarts01() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log,
                new String[]{"Test01.properties", "strings/Starts01.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
        checkMarkerCoverage(reporter, 0, 0);
        checkMarkerCoverage(reporter, 1, 1, 2, 3);
    }

    @Test
    public void testStartsEnds01() {
        final Logger log = LogManager.getLogger("COASTAL-TEST");
        Configuration config = Configuration.load(log,
                new String[]{"Test01.properties", "strings/StartsEnds01.properties"});
        assertNotNull(config);
        COASTAL coastal = new COASTAL(log, config);
        coastal.start(false);
        Reporter reporter = coastal.getReporter();
        checkDivers(reporter, 1, 3);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 3, 0, 0);
        checkMarkerCoverage(reporter, 0, 0);
        checkMarkerCoverage(reporter, 1, 1, 2, 3);
    }

}
