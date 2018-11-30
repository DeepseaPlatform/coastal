package tests.observers;

import za.ac.sun.cs.coastal.Symbolic;

public class MarkerCoverage02 {

	public static void main(String[] args) {
		compute(3, 4, 5);
		Symbolic.mark(0);
	}

	private static void compute(int x, int y, int z) {
		Symbolic.mark(1);
		if (x < 5) {
			Symbolic.mark(10);
			if (y < 7) {
				Symbolic.mark(100);
				if (z == 9) {
					Symbolic.mark(1000);
					System.out.println("<5 <7 ==9");
				} else {
					Symbolic.mark(1001);
					System.out.println("<5 <7 !=9");
				}
			} else {
				Symbolic.mark(101);
				if (z == 9) {
					Symbolic.mark(1010);
					System.out.println("<5 >=7 ==9");
				} else {
					Symbolic.mark(1011);
					System.out.println("<5 >=7 !=9");
				}
			}
		} else {
			Symbolic.mark(11);
			if (y < 7) {
				Symbolic.mark(110);
				if (z == 9) {
					Symbolic.mark(1100);
					System.out.println(">=5 <7 ==9");
				} else {
					Symbolic.mark(1101);
					System.out.println(">=5 <7 !=9");
				}
			} else {
				Symbolic.mark(111);
				if (z == 9) {
					Symbolic.mark(1110);
					System.out.println(">=5 >=7 ==9");
				} else {
					Symbolic.mark(1111);
					System.out.println(">=5 >=7 !=9");
				}
			}
		}
	}

}
