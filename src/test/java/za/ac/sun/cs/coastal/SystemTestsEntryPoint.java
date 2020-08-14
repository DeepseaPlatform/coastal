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

import org.junit.Test;

/**
 * System tests for different entry points.  This checks that COASTAL starts its
 * runs in the right place (by default main()) and with the correct arguments.
 */
public class SystemTestsEntryPoint extends SystemTests {

    @Test
    public void testEntryPoint01A() {
        Reporter reporter = setup("Test01.properties", "entrypoint/EntryPoint01-A.properties");
        checkDivers(reporter, 1, 4);
        checkSurfers(reporter, 0, 0);
        checkDFStrategy(reporter, 1);
        checkPathTree(reporter, 4, 0, 0);
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
        checkMarkerCoverage(reporter, 0, 0, 1);
        checkMarkerCoverage(reporter, 1, 2, 3, 4, 5);
        checkMarkerCoverage(reporter, 4, 6);
    }

}
