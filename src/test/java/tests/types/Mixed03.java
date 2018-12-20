package tests.types;

import za.ac.sun.cs.coastal.Symbolic;

public class Mixed03 {

	public static void main(String[] args) {
		run(0, (byte) 0);
		Symbolic.mark(1000);
	}

	private static void run(long x, byte y) {
		if (x == y) {
			Symbolic.mark(1001);
			if (x + y > 33333) {
				Symbolic.mark(1002);
			} else if (y > x) {
				Symbolic.mark(1003);
			} else {
				Symbolic.mark(1004);
			}
		} else {
			Symbolic.mark(1005);
			if (x < 32000) {
				Symbolic.mark(1006);
			} else if (y > x + 1000) {
				Symbolic.mark(1007);
			} else {
				Symbolic.mark(1008);
			}
		}
	}

}
