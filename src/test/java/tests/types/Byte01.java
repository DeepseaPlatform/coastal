package tests.types;

import za.ac.sun.cs.coastal.Symbolic;

public class Byte01 {

	public static void main(String[] args) {
		run((byte) 0, (byte) 0);
		Symbolic.mark(9);
	}

	private static void run(byte x, byte y) {
		if (x == y) {
			Symbolic.mark(14);
			if (x > 127) {
				Symbolic.mark(16);
			} else if (y > 0) {
				Symbolic.mark(18);
			} else {
				Symbolic.mark(20);
			}
		} else {
			Symbolic.mark(23);
			if (x < -128) {
				Symbolic.mark(25);
			} else if (y < x) {
				Symbolic.mark(27);
			} else {
				Symbolic.mark(29);
			}
		}
	}

}
