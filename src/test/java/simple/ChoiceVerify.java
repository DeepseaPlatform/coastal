package simple;

import org.sosy_lab.sv_benchmarks.*;

public class ChoiceVerify {

	private static void testSimple() {
		
		boolean b1 = Verifier.nondetBoolean();
		boolean b2 = Verifier.nondetBoolean();
		
		if (b1 == b2) {
			b1 = b2;
		} else {
			if (b1) {
				b1 = true;
			} else if (b2) {
				b2 = true;
			}
			
		}
		System.out.println(" b1 = " + b1 + " b2 = " + b2);
	}
	
	private static void testLoop() {
		int i = 0;
		
		while (i < 2) {
			boolean b1 = Verifier.nondetBoolean();
			boolean b2 = Verifier.nondetBoolean();
		
			if (b1 == b2) {
				b1 = b2;
			} else {
				if (b1) {
					b1 = true;
				} else if (b2) {
					b2 = true;
				}
			}
			
			System.out.println(i + " b1 = " + b1 + " b2 = " + b2);
			i++;
		}
	}
	
	public static void main(String[] args) {
		//testSimple();
		testLoop();
	}

}
