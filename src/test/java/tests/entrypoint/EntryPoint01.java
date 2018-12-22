package tests.entrypoint;

import za.ac.sun.cs.coastal.Symbolic;

public class EntryPoint01 {

	public static void main(String[] args) {
		test(1, 2, 3);
		Symbolic.mark(0);
	}

	public static void test(int a, int b, int c) {
		test1(b + c, a + c, a + b);
		Symbolic.mark(1);
	}

	public static void test1(int x, int y, int z) {
		if (x > y + z) {
			Symbolic.mark(2);
		} else if (y == x + z) {
			Symbolic.mark(3);
		} else if (z < x + y) {
			Symbolic.mark(4);
		} else {
			Symbolic.mark(5);
		}
	}
	
}
