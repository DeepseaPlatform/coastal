package fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing01 {

	public static void main(String[] args) {
		run(0);
		Symbolic.mark(0);
	}

	private static void run(int x) {
		if (x < 500) {
			Symbolic.mark(1);
		} else if (x > 5000) {
			Symbolic.mark(2);
		} else {
			Symbolic.mark(3);
		}
	}

}
