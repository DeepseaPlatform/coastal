package observers;

import za.ac.sun.cs.coastal.Symbolic;

public class MarkerCoverage03 {

	public static void main(String[] args) {
		compute(3, 4, 5);
		Symbolic.mark("never");
	}

	private static void compute(int x, int y, int z) {
		Symbolic.mark("compute-8");
		if (x < 5) {
			Symbolic.mark("<5-4");
			if (y < 7) {
				Symbolic.mark("<5<7-2");
				if (z == 9) {
					Symbolic.mark("<5<7==9-1");
					System.out.println("<5 <7 ==9");
				} else {
					Symbolic.mark("<5<7!=9-1");
					System.out.println("<5 <7 !=9");
				}
			} else {
				Symbolic.mark("<5>=7-2");
				if (z == 9) {
					Symbolic.mark("<5>=7==9-1");
					System.out.println("<5 >=7 ==9");
				} else {
					Symbolic.mark("<5>=7!=9-1");
					System.out.println("<5 >=7 !=9");
				}
			}
		} else {
			Symbolic.mark(">=5-4");
			if (y < 7) {
				Symbolic.mark(">=5<7-2");
				if (z == 9) {
					Symbolic.mark(">=5<7==9-1");
					System.out.println(">=5 <7 ==9");
				} else {
					Symbolic.mark(">=5<7!=9-1");
					System.out.println(">=5 <7 !=9");
				}
			} else {
				Symbolic.mark(">=5>=7-2");
				if (z == 9) {
					Symbolic.mark(">=5>=7==9-1");
					System.out.println(">=5 >=7 ==9");
				} else {
					Symbolic.mark(">=5>=7!=9-1");
					System.out.println(">=5 >=7 !=9");
				}
			}
		}
	}

}
