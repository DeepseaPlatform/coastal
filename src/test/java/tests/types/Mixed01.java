package tests.types;

import za.ac.sun.cs.coastal.Symbolic;

public class Mixed01 {

	public static void main(String[] args) {
		short z = 0;
		run(z, 0);
		Symbolic.mark(10);
	}

	private static void run(short x, int y) {
		if (x == y) {
			Symbolic.mark(15);
			if (x > 33333) {
				Symbolic.mark(17);
			} else if (y > x) {
				Symbolic.mark(19);
			} else {
				Symbolic.mark(21);
			}
		} else {
			Symbolic.mark(24);
			if (x < 32000) {
				Symbolic.mark(26);
			} else if (y > x + 1000) {
				Symbolic.mark(28);
			} else {
				Symbolic.mark(30);
			}
		}
	}

}
