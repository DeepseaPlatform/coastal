package za.ac.sun.cs.coastal;

import za.ac.sun.cs.coastal.Reporter.Reportable;

/**
 * Base class for diver, surfer, and strategy factories.
 */
public interface TaskFactory {

	/**
	 * Create a new manager for this class of tasks. Only one manager should ever be
	 * created, but this singleton rule is not enforced in any way.
	 * 
	 * @param coastal instance of COASTAL
	 * @return a new task manager
	 */
	TaskManager createManager(COASTAL coastal);

	/**
	 * Create a new task. Several tasks may be created for each manager (depending
	 * on the user configuration) and each task may actually consist of more than
	 * one task component. Each component runs in a separate thread.
	 * 
	 * @param coastal instance of COASTAL
	 * @param manager the manager that controls
	 * @return an array of new task instances
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
	 * A task that will be run inside its own threads.
	 */
	public interface Task extends Runnable {
	}

}
