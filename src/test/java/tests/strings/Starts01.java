package tests.strings;

import za.ac.sun.cs.coastal.Symbolic;

public class Starts01 {

	public static void main(String[] args) {
		test("hello");
		Symbolic.mark(0);
	}

	public static void test(String str) {
		if (str.startsWith("bl", 1)) {
			if (str.endsWith("ue")) {
				Symbolic.mark(1);
			} else {
				Symbolic.mark(2);
			}
		} else {
			Symbolic.mark(3);
		}
	}

}
