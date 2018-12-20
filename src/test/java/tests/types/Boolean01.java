package tests.types;

import za.ac.sun.cs.coastal.Symbolic;

public class Boolean01 {

	public static void main(String[] args) {
		run(false, false);
		Symbolic.mark(9);
	}

	private static void run(boolean x, boolean y) {
		if (x == y) {
			Symbolic.mark(14);
			if (x) {
				Symbolic.mark(16);
			} else if (y) {
				Symbolic.mark(18);
			} else {
				Symbolic.mark(20);
			}
		} else {
			Symbolic.mark(23);
			if (x) {
				Symbolic.mark(25);
			} else if (y) {
				Symbolic.mark(27);
			} else {
				Symbolic.mark(29);
			}
		}
	}

}
