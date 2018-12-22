package tests.randomtesting;

import za.ac.sun.cs.coastal.Symbolic;

public class RandomTesting01 {

	public static void main(String[] args) {
		test(1, 2, 3);
	}

	public static void test(int a, int b, int c) {
		if (a > b) {
			Symbolic.mark(1);
		} else {
			Symbolic.mark(2);
		}
		if (c == 0) {
			Symbolic.mark(3);
		} else {
			Symbolic.mark(4);
		}
	}

}
