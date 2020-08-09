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
package za.ac.sun.cs.coastal.strategy;


import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;

public interface StrategyFactory extends TaskFactory {

	@Override
	StrategyManager createManager(COASTAL coastal);

	@Override
	Strategy[] createTask(COASTAL coastal, TaskManager manager);

	// ======================================================================
	//
	// STRATEGY MANAGER INTERFACE
	//
	// ======================================================================

	public interface StrategyManager extends TaskManager {

		void recordWaitTime(long l);

	}

	// ======================================================================
	//
	// TASK INTERFACE
	//
	// ======================================================================

	abstract class Strategy implements Task {

		protected final COASTAL coastal;

		protected final Logger log;

		protected final StrategyManager manager;

		protected Strategy(COASTAL coastal, StrategyManager manager) {
			this.coastal = coastal;
			log = coastal.getLog();
			this.manager = manager;
		}

	}

}
