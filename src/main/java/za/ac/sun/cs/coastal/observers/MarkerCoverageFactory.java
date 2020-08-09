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
package za.ac.sun.cs.coastal.observers;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class MarkerCoverageFactory implements ObserverFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "#M#";

	public MarkerCoverageFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_TASK;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new MarkerCoverageManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return new MarkerCoverageObserver(coastal, manager);
	}

	// ======================================================================
	//
	// MANAGER FOR MARKER COVERAGE
	//
	// ======================================================================

	private static class MarkerCoverageManager implements ObserverManager {

		private final Broker broker;

		private final Map<String, Integer> markers = new HashMap<>();

		MarkerCoverageManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		public synchronized void update(Map<String, Integer> markers) {
			for (Map.Entry<String, Integer> entry : markers.entrySet()) {
				String key = entry.getKey();
				int value = entry.getValue();
				Integer myValue = this.markers.get(key);
				if (myValue == null) {
					this.markers.put(key, value);
				} else {
					this.markers.put(key, value + myValue);
				}
			}
		}

		public void report(Object object) {
			broker.publish("marker-coverage-report", null);
			if (markers.size() > 0) {
				for (Map.Entry<String, Integer> entry : markers.entrySet()) {
					broker.publish("report",
							new Tuple("MarkerCoverage.marker[" + entry.getKey() + "]", entry.getValue()));
				}
			}
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String[] getPropertyNames() {
			return null;
		}

		@Override
		public Object[] getPropertyValues() {
			return null;
		}

	}

	// ======================================================================
	//
	// OBSERVER FOR MARKER COVERAGE
	//
	// ======================================================================

	private static class MarkerCoverageObserver implements Observer {

		private final Logger log;

		private final MarkerCoverageManager manager;

		private final Map<String, Integer> markers = new HashMap<>();

		MarkerCoverageObserver(COASTAL coastal, ObserverManager manager) {
			log = coastal.getLog();
			this.manager = (MarkerCoverageManager) manager;
			Broker broker = coastal.getBroker();
			broker.subscribe("marker-coverage-report", this::update);
			broker.subscribeThread("mark", this::mark);
		}

		public void mark(Object object) {
			String marker;
			if (object instanceof Integer) {
				marker = Integer.toString((Integer) object);
			} else {
				marker = (String) object;
			}
			log.trace("{} mark hit {}", LOG_PREFIX, marker);
			Integer n = markers.get(marker);
			if (n == null) {
				markers.put(marker, 1);
			} else {
				markers.put(marker, n + 1);
			}
		}

		public void update(Object object) {
			manager.update(markers);
		}

	}

}
