package za.ac.sun.cs.coastal.reporting;

import java.io.PrintWriter;

public interface Reporter {

	int getOrder();

	String getName();

	void record(Recorder recorder);

	void report(PrintWriter info, PrintWriter trace);

}
