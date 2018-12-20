package tests.types;

import za.ac.sun.cs.coastal.Symbolic;

public class Long01 {

	public static void main(String[] args) {
		run(0, 1);
		Symbolic.mark(9);
	}

	private static void run(long x, long y) {
		long z = x + y;
		if (x == y) {
			Symbolic.mark(15);
			if (z > x) {
				Symbolic.mark(17);
			} else {
				Symbolic.mark(19);
			}
		} else {
			Symbolic.mark(22);
			if (2 * x > 3 * y) {
				Symbolic.mark(24);
			} else if (5 * x < 7 * y) {
				Symbolic.mark(26);
			} else if (9 * x == 11 * y) {
				Symbolic.mark(28);
			} else {
				Symbolic.mark(30);
			}
		}
	}

}
