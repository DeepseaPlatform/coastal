package types;

import za.ac.sun.cs.coastal.Symbolic;

public class Double01 {

	public static void main(String[] args) {
		run(0.0, 1.0);
		Symbolic.mark(000);
	}

	private static void run(double x, double y) {
		if (x == y) {
			Symbolic.mark(111);
			double z = (x + y) / 2;
			if (z < 3.4) {
				Symbolic.mark(222);
			} else if (z > 3.4) {
				Symbolic.mark(333);
			} else {
				Symbolic.mark(444);
			}
		} else {
			Symbolic.mark(555);
			if (x < 7.5) {
				Symbolic.mark(666);
			} else if (3 * x > 5.5 * y) {
				Symbolic.mark(777);
			}
		}
	}

}
