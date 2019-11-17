package svcomp;

import org.sosy_lab.sv_benchmarks.Verifier;

public class MainX {

	public static boolean test(Integer i) {
		if (i instanceof Integer) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		assert (!test(null));
		assert (test(new Integer(1)));
	}
}
