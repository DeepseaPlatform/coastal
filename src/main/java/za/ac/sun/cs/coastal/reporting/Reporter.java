package za.ac.sun.cs.coastal.reporting;

import java.io.PrintWriter;

/**
 * Contract that is followed by all components that write a report at the end of
 * a COASTAL run.
 */
public interface Reporter {

	/**
	 * Returns a name for this reporter.
	 * 
	 * @return the name of the reporter
	 */
	public String getName();

	/**
	 * Writes a report that summarizes the execution of a component at the end
	 * of a COASTAL run.
	 * 
	 * @param out
	 *            the destination to which the report must be written
	 */
	public void report(PrintWriter out);

}
