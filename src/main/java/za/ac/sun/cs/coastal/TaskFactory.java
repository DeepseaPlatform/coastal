package za.ac.sun.cs.coastal;

import java.util.concurrent.Callable;

import za.ac.sun.cs.coastal.Reporter.Reportable;

/**
 * Base class for diver, surfer, and strategy factories.
 */
public interface TaskFactory {

	/**
	 * Create a new manager for this class of tasks. Only one manager should
	 * ever be created, but this singleton rule is not enforced in any way.
	 * 
	 * @param coastal
	 *            instance of COASTAL
	 * @return a new task manager
	 */
	TaskManager createManager(COASTAL coastal);

	/**
	 * Create a new task. Many tasks can be created and each is expected to run
	 * in a separate thread.
	 * 
	 * @param coastal
	 *            instance of COASTAL
	 * @param manager
	 *            the manager that controls
	 * @return a new task instance
	 */
	Task createTask(COASTAL coastal, TaskManager manager);

	// ======================================================================
	//
	// TASK MANAGER INTERFACE
	//
	// ======================================================================

	/**
	 * A general interface for task managers. The managers distribute and
	 * collate information that are relevant to many instances of the task at
	 * hand.
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
	public interface Task extends Callable<Void> {
	}

}
