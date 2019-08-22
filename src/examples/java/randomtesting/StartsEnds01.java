package randomtesting;

import za.ac.sun.cs.coastal.Symbolic;

public class StartsEnds01 {

	public static void main(String[] args) {
		test("hell");
		Symbolic.mark(0);
	}

	public static void test(String str) {
		if (str.startsWith("bl")) {
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
