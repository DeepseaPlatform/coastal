package tests.fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing02 {

	public static void main(String[] args) {
		run(0, 1, 2, 3, 4, 5);
		Symbolic.mark(0);
	}

	private static void run(int a, int b, int c, int d, int e, int f) {
		if (a == 111) {
			Symbolic.mark(1);
			if (b == 222) {
				Symbolic.mark(2);
				if (c == 333) {
					Symbolic.mark(3);
					if (d == 444) {
						Symbolic.mark(4);
						if (e == 555) {
							Symbolic.mark(5);
							if (f == 666) {
								Symbolic.mark(6);
							}
						}
					}
				}
			}
		} else if (a == 999) {
			Symbolic.mark(7);
			if (b == 888) {
				Symbolic.mark(8);
				if (c == 777) {
					Symbolic.mark(9);
					if (d == 666) {
						Symbolic.mark(10);
						if (e == 444) {
							Symbolic.mark(11);
							if (f == 333) {
								Symbolic.mark(12);
							}
						}
					}
				}
			}
		}
	}

}
