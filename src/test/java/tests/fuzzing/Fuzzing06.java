package tests.fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing06 {

	public static void main(String[] args) {
		run(0, 1, 2, 3);
		Symbolic.mark(0);
	}

	private static void run(int a, int b, int c, int d) {
		int p = 2 * a + b;
		int q = 3 * b - c;
		int r = 2 * c + 3 * d;
		Symbolic.mark(111);
		while ((p > 0) && (p < 100)) {
			if (r > 50) {
				p = p - q;
				q = q + r;
				r = r - 20;
			} else {
				p = p + q;
				q = q - r;
				r = r + 10;
			}
			Symbolic.mark(111);
		}
		if (p > q) {
			Symbolic.mark(222);
		} else if (q > r) {
			Symbolic.mark(333);
		}
		Symbolic.mark(999);
	}

}
