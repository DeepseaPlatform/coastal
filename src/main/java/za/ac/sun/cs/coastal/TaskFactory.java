/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal;

import za.ac.sun.cs.coastal.Reporter.Reportable;

/**
 * Base interface for diver, surfer, and strategy factories.
 */
public interface TaskFactory {

	/**
	 * Create a new manager for this class of tasks. Only one manager should ever be
	 * created, but this singleton rule is not enforced in any way.
	 * 
	 * @param coastal
	 *                instance of COASTAL
	 * @return new task manager
	 */
	TaskManager createManager(COASTAL coastal);

	/**
	 * Create a new task. Several tasks may be created for each manager (depending
	 * on the user configuration) and each task may actually consist of more than
	 * one task component. Each component runs in a separate thread.
	 * 
	 * @param coastal
	 *                instance of COASTAL
	 * @param manager
	 *                the manager that controls
	 * @return array of new task instances
	 */
	Task[] createTask(COASTAL coastal, TaskManager manager);

	// ======================================================================
	//
	// TASK MANAGER INTERFACE
	//
	// ======================================================================

	/**
	 * A general interface for task managers. The managers distribute and collate
	 * information that are relevant to many instances of the task at hand.
	 */
	public interface TaskManager extends Reportable {
	}

	// ======================================================================
	//
	// TASK INTERFACE
	//
	// ======================================================================

	/**
	 * A general interface for tasks. Each task that will be run inside its own
	 * thread.
	 */
	public interface Task extends Runnable {
	}

}
