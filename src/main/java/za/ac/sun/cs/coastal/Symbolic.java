package za.ac.sun.cs.coastal;

import za.ac.sun.cs.coastal.observers.MarkerCoverageFactory;
import za.ac.sun.cs.coastal.observers.StopControllerFactory;
import za.ac.sun.cs.coastal.symbolic.VM;

/**
 * A placeholder class that can be used in an SUT (system under test) to
 * interact with COASTAL.
 */
public class Symbolic {

	/**
	 * A placeholder method that signals to COASTAL that the analysis run should (or
	 * could) be terminated. Instrumentation will replace calls of this method with
	 * calls to {@link VM#stop()}. Internally, the latter method emits a message on
	 * the publish-subscribe system; unless an appropriate observer listens for such
	 * messages, no other action is taken.
	 * 
	 * @see StopControllerFactory
	 */
	public static void stop() {
		// placeholder method
	}

	/**
	 * Similar to {@link #stop()}, but with an additional user specified message.
	 * Instrumentation will replace calls of this method with calls to
	 * {@link VM#stop(String)}.
	 * 
	 * @param message a message to justify the action
	 * 
	 * @see StopControllerFactory
	 */
	public static void stop(String message) {
		// placeholder method
	}

	/**
	 * A placeholder method that signals to COASTAL that a particular execution
	 * point has been reached. Instrumentation will replace calls of this method
	 * with calls to {@link VM#mark(int)}. Internally, the latter method emits a
	 * message on the publish-subscribe system; unless an appropriate observer
	 * listens for such messages, no other action is taken.
	 * 
	 * @param marker an integer to identify the execution point
	 * 
	 * @see MarkerCoverageFactory
	 */
	public static void mark(int marker) {
		// placeholder method
	}

	/**
	 * Similar to {@link #mark(int)}, but with a string instead of integer marker
	 * identifier. Instrumentation will replace calls of this method with calls to
	 * {@link VM#mark(int)}. Internally, the latter method emits a message on the
	 * publish-subscribe system; unless an appropriate observer listens for such
	 * messages, no other action is taken.
	 * 
	 * @param marker a string to identify the execution point
	 * 
	 * @see MarkerCoverageFactory
	 */
	public static void mark(String marker) {
		// placeholder method
	}

	/**
	 * Quick-and-dirty mehod to display the path condition in the log file.
	 */
	public static void printPC() {
		// placeholder method
	}

	/**
	 * Quick-and-dirty mehod to display the path condition in the log file.
	 * 
	 * @param label a string to identify the PC
	 */
	public static void printPC(String label) {
		// placeholder method
	}

	public static boolean makeSymbolicBoolean(String name) {
		return false;
	}

	public static byte makeSymbolicByte(String name) {
		return (byte) 0;
	}

	public static char makeSymbolicChar(String name) {
		return '\0';
	}

	public static short makeSymbolicShort(String name) {
		return (short) 0;
	}

	public static int makeSymbolicInt(String name) {
		return 0;
	}

	public static long makeSymbolicLong(String name) {
		return 0L;
	}

	public static float makeSymbolicFloat(String name) {
		return 0F;
	}

	public static double makeSymbolicDouble(String name) {
		return 0.0;
	}

}
