package simple;

import za.ac.sun.cs.coastal.symbolic.VM;

public class StateMachine {

	public static void main(String[] args) {
//		int[] inputs = new int[] { 1, 2, 3, 4, 5, 0 };
		int[] inputs = new int[6];
		drive(inputs);
	}

	public static void drive(int[] inputs) {
		int state = 0;
		int offset = 0;
		int inputIndex = 0;
		while (inputIndex < 6) {
			switch (inputs[inputIndex]) {
			case 0:
				if (offset > 3) {
					VM.stop("KA-CHING!");
					System.out.println("XXX");
					return;
				}
				break;
			case 1:
				if (state == 0) { state = 1; } else { state = 0; }
				break;
			case 2:
				if (state == 1) { state = 2; } else { state = 0; }
				break;
			case 3:
				if (state == 2) { state = 3; } else { state = 0; }
				break;
			case 4:
				if (state == 3) { state = 4; } else { state = 0; }
				break;
			case 5:
				if (state == 4) { state = 5; offset = 10; } else { state = 0; }
				break;
			default:
				System.out.println("UNKNOWN STATE");
				return;
			}
			inputIndex++;
		}
	}

}
