package tests.observers;

import za.ac.sun.cs.coastal.Symbolic;

public class LineCoverage01 {

	public static void main(String[] args) {
		compute(1, 1, 1);
	}

	private static void compute(int x, int y, int z) {
		int q = 0;
		if (x < y) {
			if (y == z) {
				q = 122;
			} else if (y < z) {
				q = 123;
			} else if (x < z) {
				q = 132;
			} else {
				q = 231;
			}
		} else if (x == y) {
			if (y == z) {
				q = 111;
			} else {
				q = 221;
			}
		} else if (x == z) {
			q = 212;
		} else if (x < z) {
			q = 213;
		} else if (y < z) {
			q = 312;
		} else {
			q = 321;
		}
		int w = x * 100 + y * 10 + z;
		if (w != q) {
			Symbolic.stop("INCORRECT SORTING");
		}
	}

}
