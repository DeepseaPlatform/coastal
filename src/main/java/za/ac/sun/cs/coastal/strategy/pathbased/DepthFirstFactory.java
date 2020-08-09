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
package za.ac.sun.cs.coastal.strategy.pathbased;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.pathtree.PathTreeNode;
import za.ac.sun.cs.coastal.symbolic.Path;

public class DepthFirstFactory extends PathBasedFactory {

	public DepthFirstFactory(COASTAL coastal, Configuration config) {
	}

	@Override
	public StrategyManager createManager(COASTAL coastal) {
		return new DepthFirstStrategyManager(coastal);
	}

	@Override
	public Strategy[] createTask(COASTAL coastal, TaskManager manager) {
		((DepthFirstStrategyManager) manager).incrementTaskCount();
		return new Strategy[] { new DepthFirstStrategy(coastal, (StrategyManager) manager) };
	}

	// ======================================================================
	//
	// DEPTH-FIRST SEARCH STRATEGY MANAGER
	//
	// ======================================================================

	private static class DepthFirstStrategyManager extends PathBasedManager {

		protected int taskCount = 0;

		DepthFirstStrategyManager(COASTAL coastal) {
			super(coastal);
		}

		protected void incrementTaskCount() {
			taskCount++;
		}

		@Override
		public String getName() {
			return "DepthFirstStrategy";
		}

		@Override
		protected int getTaskCount() {
			return taskCount;
		}

	}

	// ======================================================================
	//
	// STRATEGY THAT FINDS NEW PATHS WITH DEPTH-FIRST SEARCH
	//
	// ======================================================================

	private static class DepthFirstStrategy extends PathBasedStrategy {

		DepthFirstStrategy(COASTAL coastal, StrategyManager manager) {
			super(coastal, manager);
		}

		@Override
		public Path findNewPath(PathTree pathTree) {
			PathTreeNode curNode = pathTree.getRoot();
			if (curNode == null) {
				return null;
			}
			outer: while (true) {
				int n = curNode.getChildCount();
				for (int i = 0; i < n; i++) {
					PathTreeNode childNode = curNode.getChild(i);
					if ((childNode != null) && !childNode.isComplete()) {
						curNode = childNode;
						continue outer;
					}
				}
				for (int i = 0; i < n; i++) {
					PathTreeNode childNode = curNode.getChild(i);
					if (childNode == null) {
						return curNode.getPathForChild(i);
					}
				}
				return null;
			}
		}

	}

}
