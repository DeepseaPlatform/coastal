package types;

import za.ac.sun.cs.coastal.Symbolic;

public class Float02 {

	public static void main(String[] args) {
		run(0f, 1f);
		Symbolic.mark(9);
	}

	private static void run(float x, float y) {
		if (x == y) {
			Symbolic.mark(14);
			float z = (x + y) / 2f;
			if (z < 3.4f) {
				Symbolic.mark(17);
			} else if (z > 3.4f) {
				Symbolic.mark(19);
			} else {
				Symbolic.mark(21);
			}
		} else {
			Symbolic.mark(24);
			if (x < 7.5f) {
				Symbolic.mark(26);
			} else if (3f * x > 5.5f * y) {
				Symbolic.mark(28);
			}
		}
	}

}
