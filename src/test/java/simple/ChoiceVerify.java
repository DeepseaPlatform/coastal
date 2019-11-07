package simple;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ChoiceVerify {

	private static void testLoop() {
		int i = 0;
		int state = 0;

		while (i < 2) {
			boolean b = Verifier.nondetBoolean();
			if (state == 0) {
				if (b) {
					state = 1;
				} else {
					state = 2;
				}
			} else if (state == 1) {
				if (!b) {
					assert false;
				}
			} else {
				if (b) {
					assert false;
				}
			}
			i++;
		}
	}

	public static void main(String[] args) {
		testLoop();
	}

}
