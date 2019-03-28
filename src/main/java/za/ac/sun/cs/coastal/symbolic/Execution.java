package za.ac.sun.cs.coastal.symbolic;

/**
 * Summary of a single execution of a system-under-test. The most important
 * parts of the execution are:
 * 
 * <ul>
 * <li>the path (either a trace or a path condition)</li>
 * <li>the input that triggered the execution</li>
 * </ul>
 * 
 * Not all executions arise from the system-under-test. COASTAL may construct a
 * new path which it wants to explore, but it may be that the path has no model.
 * In other words, there are no variable assignments that satisfy the path. In
 * this case, the system may construct an "infeasible" execution to insert in
 * the path tree.
 */
public final class Execution extends PayloadCarrierImpl {

	/**
	 * A sentinel execution that is used in situations where {@code null} is not
	 * appropriate.
	 */
	public static final Execution NULL = new Execution();

	/**
	 * The path that describes the branches taken during the execution.
	 */
	protected final Path path;

	/**
	 * The input that triggered the execution.
	 */
	protected final Input input;

	/**
	 * Construct the sentinel execution.
	 */
	private Execution() {
		this.path = null;
		this.input = null;
	}

	/**
	 * Construct the execution information.
	 * 
	 * @param path  the path taken by the execution
	 * @param input the input that triggered the execution
	 */
	public Execution(Path path, Input input) {
		this.path = path;
		this.input = input;
	}

	/**
	 * Return the path associated with the execution.
	 * 
	 * @return the execution path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Return the input that triggered the execution.
	 * 
	 * @return the execution input
	 */
	public Input getInput() {
		return input;
	}

}
