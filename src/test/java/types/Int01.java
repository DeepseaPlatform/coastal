package types;

import za.ac.sun.cs.coastal.Symbolic;

public class Int01 {

	public static void main(String[] args) {
		run(0, 1);
		Symbolic.mark(0);
	}

	private static void run(int x, int y) {
		if (x == y) {
			Symbolic.mark(1);
		} else {
			Symbolic.mark(2);
			if (x < 1000000) {
				Symbolic.mark(3);
			} else if (1000000 * x > y) {
				Symbolic.mark(4);
			}
			if (1000 * x > 10000 * y) {
				Symbolic.mark(5);
			} else if (100000 * x > 1000000 * y) {
				Symbolic.mark(6);
			} else if (10000000 * x > 100000000 * y) {
				Symbolic.mark(7);
			} else {
				Symbolic.mark(8);
			}
		}
	}

}
