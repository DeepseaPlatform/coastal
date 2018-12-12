package za.ac.sun.cs.coastal;

import java.util.concurrent.Callable;

import za.ac.sun.cs.coastal.Reporter.Reportable;

public interface TaskFactory {

	TaskManager createManager(COASTAL coastal);

	<T extends TaskManager> Task createTask(COASTAL coastal, T manager);

	// ======================================================================
	//
	// TASK MANAGER INTERFACE
	//
	// ======================================================================

	public interface TaskManager extends Reportable {

	}

	// ======================================================================
	//
	// TASK INTERFACE
	//
	// ======================================================================

	public interface Task extends Callable<Void> {
	}

}
