package observers;

import za.ac.sun.cs.coastal.Symbolic;

public class StopController01 {

	public static void main(String[] args) {
		compute(3, 4, 5);
	}

	private static void compute(int x, int y, int z) {
		if (x < 5) {
			if (y < 7) {
				if (z == 9) {
					Symbolic.stop();
					System.out.println("<5 <7 ==9");
				} else {
					System.out.println("<5 <7 !=9");
				}
			} else {
				if (z == 9) {
					System.out.println("<5 >=7 ==9");
				} else {
					System.out.println("<5 >=7 !=9");
				}
			}
		} else {
			if (y < 7) {
				if (z == 9) {
					System.out.println(">=5 <7 ==9");
				} else {
					System.out.println(">=5 <7 !=9");
				}
			} else {
				if (z == 9) {
					System.out.println(">=5 >=7 ==9");
				} else {
					System.out.println(">=5 >=7 !=9");
				}
			}
		}
	}

}
