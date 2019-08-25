package observers;

import za.ac.sun.cs.coastal.Symbolic;

public class MarkerCoverage01 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		Symbolic.mark(0);
		System.out.println(result);
	}

	private static int compute(int x, int y, int z) {
		Symbolic.mark(1);
		if (max(x, y) < z) {
			Symbolic.mark(10);
			if (x + y > 5) {
				Symbolic.mark(100);
				return x;
			} else {
				Symbolic.mark(101);
				return 10;
			}
		} else {
			Symbolic.mark(11);
			if (y < 10) {
				Symbolic.mark(110);
				return z;
			} else {
				Symbolic.mark(111);
				return x + y + z;
			}
		}
	}

	private static int max(int x, int y) {
		Symbolic.mark(2);
		if (x + 1 > y + 2) {
			Symbolic.mark(20);
			return x - 1;
		} else {
			Symbolic.mark(21);
			return y;
		}
	}

}
