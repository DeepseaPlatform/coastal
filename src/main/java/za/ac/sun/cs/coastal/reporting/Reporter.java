package za.ac.sun.cs.coastal.reporting;

import java.io.PrintWriter;

public interface Reporter {

	String getName();

	void report(PrintWriter info, PrintWriter trace);

}
