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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.solver.Solver;

public class TaintObserverFactory implements ObserverFactory {

	public TaintObserverFactory(COASTAL coastal, Configuration options) {
	}

	@Override
	public int getFrequencyflags() {
		return ObserverFactory.ONCE_PER_TASK;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new TaintCoverageManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return new TaintCoverageObserver(coastal, manager);
	}

	// ======================================================================
	//
	// MANAGER FOR TAINT COVERAGE
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "leak, conditional-leak" };

	private static class TaintCoverageManager implements ObserverManager {

		private final Broker broker;

		// Line number to taint leak information
		private final HashMap<Integer, TaintInfo> taintedPaths = new HashMap<Integer, TaintInfo>();

		TaintCoverageManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		public synchronized void update(ArrayList<TaintInfo> info) {
			for (TaintInfo taint : info) {
				if (this.taintedPaths.containsKey(taint.line)) {
					continue;
				}
				taintedPaths.put(taint.line, taint);
			}
		}

		public void report(Object object) {
			broker.publish("tainted-coverage-report", null);
			for (Map.Entry<Integer, TaintInfo> entry : taintedPaths.entrySet()) {
				if (entry.getKey() == -1) {
					broker.publish("report", new Tuple("Tainted Coverage.No taint violations found!", ""));
				} else {
					broker.publish("report",
							new Tuple("Tainted Coverage.Leak: Line " + entry.getKey() + " -> ", entry.getValue()));
				}

			}
		}

		@Override
		public String getName() {
			return "Taint Coverage";
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[1];
			propertyValues[0] = 5;
			return propertyValues;
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

	}

	// ======================================================================
	//
	// OBSERVER FOR INSTRUCTION COVERAGE
	//
	// ======================================================================

	private static class TaintCoverageObserver implements Observer {

		private final Logger log;

		private final COASTAL coastal;

		private final TaintCoverageManager manager;

		private final InstrumentationClassManager classManager;

		protected final Solver solver; // Solver, changed so observer calculates

		private int currentLine;

		private final ArrayList<TaintInfo> taintLeaks = new ArrayList<TaintInfo>();

		TaintCoverageObserver(COASTAL coastal, ObserverManager manager) {
			log = coastal.getLog();
			this.coastal = coastal;
			this.solver = Solver.getSolver(coastal);
			this.manager = (TaintCoverageManager) manager;
			this.classManager = coastal.getClassManager();
			Broker broker = coastal.getBroker();

			// Trigger method / enter method
			broker.subscribe("tainted-coverage-report", this::update);
			broker.subscribeThread("enter-method", this::triggerMethod);
			broker.subscribeThread("linenumber", this::linenumber);
			broker.subscribeThread("taint-parameter", this::taintParameter);
			broker.subscribeThread("taint-merge", this::taintMerge);
			broker.subscribeThread("taint-leaked", this::taintLeaked);
			broker.subscribeThread("local-overwritten", this::taintOverwritten);
			broker.subscribeThread("taint-sanitised", this::taintSanitised);
			broker.subscribeThread("taint-introduced", this::taintIntroduced);
			broker.subscribeThread("taint-propagation", this::taintPropagation);
			broker.subscribeThread("potential-taint-propagation", this::potentialTaintPropagation);
			broker.subscribeThread("taint-arguments", this::taintArguments);
			broker.subscribeThread("visit-end-insn", this::update);
		}

		public void triggerMethod(Object object) {
			this.currentLine = 0;
		}

		public void linenumber(Object object) {
			currentLine = (int) ((Tuple) object).get(1);
		}

		public void taintParameter(Object object) {
			// Tuple data = (Tuple) object;
			// String varName = (String) data.get(0);
			// System.out.println("[taint-parameter]: " + varName + " on line: " + currentLine);
		}

		public void taintMerge(Object object) {
			// Tuple data = (Tuple) object;
			// Expression e1 = (Expression) data.get(0);
			// Expression e2 = (Expression) data.get(1);
			// if ((this.solver.solve(e1) != null) || (this.solver.solve(e2) != null)) {
			//	// System.out.println("[taint-merge]: " + e1 + " was merged with " + e2 + " on line: " + currentLine);
			// }
		}

		public void taintLeaked(Object object) {
			Tuple data = (Tuple) object;
			String input = (String) data.get(0);
			// int size = (int) data.get(2);
			String classAndMethodName = (String) data.get(1);
			this.taintLeaks.add(new TaintInfo(input, classAndMethodName, currentLine));
			// System.out.println("[taint-leaked]: " + classAndMethodName + " on line: " + currentLine + " || " + size);
		}

		public void taintOverwritten(Object object) {
			// Tuple data = (Tuple) object;
			// int line = (int) data.get(0);
			// // System.out.println("A tainted variable was overwrriten at: " + line);
		}

		public void taintArguments(Object object) {
			// Tuple data = (Tuple) object;
			// String classAndMethodName = (String) data.get(0);
			// String msg = (String) data.get(1);
			// System.out.println("[taint-arguments]: " + " in: " + classAndMethodName);
		}

		public void taintSanitised(Object object) {
			// Tuple data = (Tuple) object;
			// String varName = (String) data.get(0);
			// String classAndMethodName = (String) data.get(1);
			// System.out.println("[taint-sanitised]: " + varName + " was sanitised with: "
			// + classAndMethodName + " on line: " + currentLine);
		}

		public void taintIntroduced(Object object) {
			// Tuple data = (Tuple) object;
			// String varName = (String) data.get(0);
			// String classAndMethodName = (String) data.get(1);
			// System.out.println("[taint-introduced]: " + varName + " with source: "
			// + classAndMethodName + " on line: " + currentLine);
		}

		public void taintPropagation(Object object) {
			// Tuple data = (Tuple) object;
			// String varName = (String) data.get(0);
			// String classAndMethodName = (String) data.get(1);
			// System.out.println("[taint-propagated]: " + varName + " with method: "
			// + classAndMethodName + " on line: " + currentLine);
		}

		public void potentialTaintPropagation(Object object) {
			// Tuple data = (Tuple) object;
			// String varName = (String) data.get(0);
			// String classAndMethodName = (String) data.get(1);
			// System.out.println("[potential-taint-propagated]: " + varName + " with method: "
			// + classAndMethodName + " on line: " + currentLine);
		}

		public void update(Object object) {
			if (this.taintLeaks.isEmpty()) {
				this.taintLeaks.add(new TaintInfo("", "No taint violations located!", -1));
			}
			manager.update(this.taintLeaks);
		}

	}

	private static class TaintInfo {

		String input;
		int line;
		String methodName;

		TaintInfo(String input, String methodName, int line) {
			this.input = input;
			this.methodName = methodName;
			this.line = line;
		}

		TaintInfo(String methodName, int line) {
			this.methodName = methodName;
			this.line = line;
		}

		public String toString() {
			if (this.input != null || !this.input.equals("")) {
				return input + ", " + methodName;
			} else {
				return methodName;
			}
		}

	}

}
