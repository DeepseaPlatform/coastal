package types;

import za.ac.sun.cs.coastal.Symbolic;

public class Float01 {

	public static void main(String[] args) {
		run(0, 1);
		//run(3.4000000953674316f, 3.4000000953674316f);
		Symbolic.mark(1000);
	}

	private static void run(float x, float y) {
		if (x == y) {
			Symbolic.mark(1001);
			float z = (x + y) / 2;
			if (z < 3.4) {
				Symbolic.mark(1002);
			} else if (z > 3.4) {
				Symbolic.mark(1003);
			} else {
				Symbolic.mark(1004);
			}
		} else {
			Symbolic.mark(1005);
			if (x < 7.5) {
				Symbolic.mark(1006);
			} else if (3 * x > 5.5 * y) {
				Symbolic.mark(1007);
			}
		}
	}

}
