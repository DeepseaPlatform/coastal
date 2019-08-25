package types;

import za.ac.sun.cs.coastal.Symbolic;

public class Char01 {

	public static void main(String[] args) {
		run('a', 'z');
		Symbolic.mark(9);
	}

	private static void run(char x, char y) {
		if (x == y) {
			Symbolic.mark(14);
			if (x == 'z') {
				Symbolic.mark(16);
			} else if (y == '\t') {
				Symbolic.mark(18);
			} else {
				Symbolic.mark(20);
			}
		} else {
			Symbolic.mark(23);
			if (x < 0) {
				Symbolic.mark(25);
			} else if (y == '\u2126') {
				Symbolic.mark(27);
			} else {
				Symbolic.mark(29);
			}
		}
	}

}
