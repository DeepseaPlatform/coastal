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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * General framework for system tests (as opposed to unit tests).
 */
public class SystemTests {

	/**
	 * Create an run an instance of COASTAL and return the reporter that
	 * captures the output.
	 *
	 * @param basic   name of the file-under-test
	 * @param special name of the corresponding properties file
	 * @return a reporter that has captured the COASTAL output
	 */
	protected Reporter setup(String basic, String special) {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		Configuration config = Configuration.load(log, new String[]{basic, special});
		assertNotNull(config);
		COASTAL coastal = new COASTAL(log, config);
		coastal.start(false);
		return coastal.getReporter();
	}

	/**
	 * Verify that the information reported for divers is correct.  This is done
	 * by {@code assert}ing that the reported numbers are equal to the expected
	 * values passed as parameters.
	 *
	 * @param reporter COASTAL report
	 * @param tasks    expected number of diver tasks
	 * @param count    expected number of divers
	 */
	protected void checkDivers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getLong("Divers.tasks"));
		assertEquals(count, reporter.getLong("Divers.count"));
	}

	/**
	 * Verify that the information reported for surfers is correct.  This is
	 * done by {@code assert}ing that the reported numbers are equal to the
	 * expected values passed as parameters.
	 *
	 * @param reporter COASTAL report
	 * @param tasks    expected number of surfer tasks
	 * @param count    expected number of surfers
	 */
	protected void checkSurfers(Reporter reporter, int tasks, int count) {
		assertEquals(tasks, reporter.getLong("Surfers.tasks"));
		assertEquals(count, reporter.getLong("Surfers.count"));
	}

	/**
	 * Verify that the number of depth-first strategy tasks is correct.
	 *
	 * @param reporter COASTAL report
	 * @param tasks    expected number of DF strategy tasks
	 */
	protected void checkDFStrategy(Reporter reporter, int tasks) {
		checkStrategy(reporter, "DepthFirstStrategy", tasks);
	}

	protected void checkRandomTestingStrategy(Reporter reporter, int tasks) {
		checkStrategy(reporter, "RandomTesting", tasks);
	}

	protected void checkStrategy(Reporter reporter, String strategy, int tasks) {
		assertEquals(tasks, reporter.getLong(strategy + ".tasks"));
	}

	protected void checkPathTree(Reporter reporter, int inserted, int revisit, int infeasible) {
		assertEquals(inserted, reporter.getLong("PathTree.inserted-count"));
		assertEquals(revisit, reporter.getLong("PathTree.revisit-count"));
		assertEquals(infeasible, reporter.getLong("PathTree.infeasible-count"));
	}

	protected void checkMarkerCoverage(Reporter reporter, int count, int... markers) {
		for (int marker : markers) {
			assertEquals(count, reporter.getLong("MarkerCoverage.marker[" + marker + "]"));
		}
	}

	protected void checkMarkerCoverage(Reporter reporter, int count, String... markers) {
		for (String marker : markers) {
			assertEquals(count, reporter.getLong("MarkerCoverage.marker[" + marker + "]"));
		}
	}

}
