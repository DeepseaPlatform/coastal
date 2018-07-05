package examples.simple;

import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class MaxChoice3 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		System.out.println(result);
	}

	private static int compute(int x, int y, int z) {
		SymbolicState.mark(77);
		if (Math.max(x, y) < z) {
			SymbolicState.mark("p1");
			if (x + y > 5) {
				SymbolicState.mark("p1.1");
				return x;
			} else {
				SymbolicState.mark("p1.2");
				SymbolicState.stop("my message");
				return 10;
			}
		} else {
			SymbolicState.mark("p2");
			if (y < 10) {
				SymbolicState.mark("p2.1");
				return z;
			} else {
				SymbolicState.mark("p2.2");
				return x + y + z;
			}
		}
	}

}
