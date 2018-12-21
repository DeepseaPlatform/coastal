package tests.strings;

import za.ac.sun.cs.coastal.Symbolic;

public class CharAt01 {

	public static void main(String[] args) {
		test("abc", 1);
		Symbolic.mark(0);
	}

	private static void test(String str, int ofs) {
		if (str.charAt(ofs) == 'a') {
			Symbolic.mark(1);
		} else {
			Symbolic.mark(2);
		}
	}

}
