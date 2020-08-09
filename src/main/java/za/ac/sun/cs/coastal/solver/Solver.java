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
package za.ac.sun.cs.coastal.solver;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.Input;

public abstract class Solver {

	protected final COASTAL coastal;

	protected final Configuration configuration;

	protected static Solver solver = null;

	public Solver(COASTAL coastal, Configuration configuration) {
		this.coastal = coastal;
		this.configuration = configuration;
	}

	public abstract Input solve(Expression expression);

	public static Solver getSolver(COASTAL coastal) {
		if (solver == null) {
			String solverName = coastal.getConfig().getString("coastal.settings.solver");
			if (solverName != null) {
				Configuration solverConfiguration = coastal.getConfig().subset("coastal.settings.solver");
				Object s = Configuration.createInstance(coastal, solverConfiguration, solverName.trim());
				if ((s != null) && (s instanceof Solver)) {
					solver = (Solver) s;
				}
			}
		}
		return solver;
	}

	public static void report() {
		if (solver != null) {
			solver.issueReport();
		}
	}
	
	public void issueReport() { }

}
