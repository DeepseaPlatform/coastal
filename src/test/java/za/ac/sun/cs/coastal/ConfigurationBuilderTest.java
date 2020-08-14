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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests to check that the COASTAL configuration is constructed correctly.
 *
 * !!NOTE: Currently these tests are disabled.  They need to be re-enabled
 * at some point.
 */
public class ConfigurationBuilderTest {

	@Test
	public void testMainNotSet() {
		assertTrue(true);
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		final ConfigurationX c = cb.construct();
//		assertEquals(null, c.getMain());
	}

//	@Test
//	public void testMainSet() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		final ConfigurationBuilderX cb0 = cb.setMain("main");
//		assertEquals(cb, cb0);
//		final ConfigurationX c = cb.construct();
//		assertNotEquals(null, c.getMain());
//	}
//	
//	@Test
//	public void testStringProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setArgs("args"));
//		assertEquals(cb, cb.setMain("main"));
//		final ConfigurationX c = cb.construct();
//		assertEquals("args", c.getArgs());
//		assertEquals("main", c.getMain());
//	}
//	
//	@Test
//	public void testBooleanProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setEchoOutput(true));
//		final ConfigurationX c = cb.construct();
//		assertTrue(c.getEchoOutput());
//	}
//	
//	@Test
//	public void testLongProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setLimitConjuncts(123));
//		assertEquals(cb, cb.setLimitPaths(234));
//		assertEquals(cb, cb.setLimitRuns(345));
//		assertEquals(cb, cb.setLimitTime(456));
//		final ConfigurationX c = cb.construct();
//		assertEquals(123, c.getLimitConjuncts());
//		assertEquals(234, c.getLimitPaths());
//		assertEquals(345, c.getLimitRuns());
//		assertEquals(456, c.getLimitTime());
//	}
	
}
