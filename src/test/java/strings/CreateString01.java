package strings;

import org.sosy_lab.sv_benchmarks.Verifier;

import za.ac.sun.cs.coastal.Symbolic;

public class CreateString01 {

	public static void main(String[] args) {
		String s = Verifier.nondetString();
		Symbolic.mark(0);
		if (s.charAt(0) == 'a') {
			Symbolic.mark(1);
		} else {
			Symbolic.mark(2);
		}
		Symbolic.mark(3);
	}

}
