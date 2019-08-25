package fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing04 {

	public static void main(String[] args) {
		run((short) 0, (short) 1, (short) 2, (short) 3, (short) 4, (short) 5);
		Symbolic.mark(0);
	}

	private static void run(short a, short b, short c, short d, short e, short f) {
		Symbolic.mark(111);
		if (a > 999) {
			Symbolic.mark(1);
			if (b < 222) {
				Symbolic.mark(2);
				if (c > 333) {
					Symbolic.mark(3);
					if (d < 444) {
						Symbolic.mark(4);
						if (e > 555) {
							Symbolic.mark(5);
							if (f < 666) {
								Symbolic.mark(6);
							} else {
								Symbolic.mark(60);
							}
						} else {
							Symbolic.mark(50);
						}
					} else {
						Symbolic.mark(40);
					}
				} else {
					Symbolic.mark(30);
				}
			} else {
				Symbolic.mark(20);
			}
		} else if (a > 111) {
			Symbolic.mark(7);
			if (b < 888) {
				Symbolic.mark(8);
				if (c > 777) {
					Symbolic.mark(9);
					if (d < 666) {
						Symbolic.mark(10);
						if (e > 444) {
							Symbolic.mark(11);
							if (f < 333) {
								Symbolic.mark(12);
							} else {
								Symbolic.mark(12);
							}
						} else {
							Symbolic.mark(120);
						}
					} else {
						Symbolic.mark(110);
					}
				} else {
					Symbolic.mark(100);
				}
			} else {
				Symbolic.mark(90);
			}
		} else {
			Symbolic.mark(80);
		}
		Symbolic.mark(999);
	}

}
