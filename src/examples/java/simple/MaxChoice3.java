package simple;

import za.ac.sun.cs.coastal.Symbolic;

public class MaxChoice3 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		System.out.println(result);
	}

	private static int compute(int x, int y, int z) {
		Symbolic.mark(77);
		if (Math.max(x, y) < z) {
			Symbolic.mark("p1");
			if (x + y > 5) {
				Symbolic.mark("p1.1");
				return x;
			} else {
				Symbolic.mark("p1.2");
				Symbolic.stop("my message");
				return 10;
			}
		} else {
			Symbolic.mark("p2");
			if (y < 10) {
				Symbolic.mark("p2.1");
				return z;
			} else {
				Symbolic.mark("p2.2");
				return x + y + z;
			}
		}
	}

}
