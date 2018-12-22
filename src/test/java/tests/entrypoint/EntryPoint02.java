package tests.entrypoint;

import za.ac.sun.cs.coastal.Symbolic;

public class EntryPoint02 {

	public static void main(String[] args) {
		test(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
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
