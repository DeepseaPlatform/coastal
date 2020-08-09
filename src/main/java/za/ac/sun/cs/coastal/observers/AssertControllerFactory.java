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

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class AssertControllerFactory implements ObserverFactory {

	public AssertControllerFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_RUN;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new AssertManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return null;
	}

	// ======================================================================
	//
	// MANAGER FOR ASSERT CONTROL
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "assert-failed", "message" };

	private static class AssertManager implements ObserverManager {

		private final Logger log;

		private final COASTAL coastal;

		private final Broker broker;

		private boolean wasStopped = false;

		private String stopMessage = null;

		AssertManager(COASTAL coastal) {
			log = coastal.getLog();
			this.coastal = coastal;
			broker = coastal.getBroker();
			broker.subscribe("assert-failed", this::stop);
			broker.subscribe("coastal-stop", this::report);
			if (!COASTAL.class.desiredAssertionStatus()) {
				new Banner('@').println("WARNING:\nassertions are disabled").display(log);
			}
		}

		public void report(Object object) {
			broker.publish("report", new Tuple("AssertController.assert-failed", wasStopped));
			if (stopMessage != null) {
				broker.publish("report", new Tuple("AssertController.message", stopMessage));
			}
		}

//		public void fail(Object object) {
//			Tuple tuple = (Tuple) object;
//			wasStopped = true;
//		}

		public void stop(Object object) {
			Tuple tuple = (Tuple) object;
			wasStopped = true;
			coastal.stopWork();
			stopMessage = (String) tuple.get(1);
			if (stopMessage == null) {
				new Banner('!').println("PROGRAM TERMINATION POINT REACHED").display(log);
			} else {
				new Banner('!').println("PROGRAM TERMINATION POINT REACHED").println(stopMessage).display(log);
			}
		}

		@Override
		public String getName() {
			return "AssertController";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[2];
			propertyValues[0] = wasStopped;
			propertyValues[1] = (stopMessage == null) ? "?" : stopMessage;
			return propertyValues;
		}

	}

}
