package examples.simple;

public class Exceptions3 {

	public static void main(String[] args) {
		int result = op0(0, 0, 0);
		System.out.println(result);
	}

	private static int op0(int x, int d, int e) {
		// System.out.println("op0(" + x + ", " + d + ", " + e + ")");
		try {
			return op1(x, d, e);
		} catch (ArithmeticException ae) {
			// ae.printStackTrace();
			System.out.println("DIVZERO1");
		}
		return 0;
	}

	private static int op1(int x, int d, int e) throws ArithmeticException {
		if (d == 0) {
			// System.out.println("op1(" + x + ", " + d + ", " + e + ") NOTRY");
			if (e > 4) {
				return op2(x);
			} else {
				return 10 + op1(x, 1 - d, e + 1);
			}
		} else {
			// System.out.println("op1(" + x + ", " + d + ", " + e + ") TRY");
			try {
				if (e > 4) {
					return op2(x);
				} else {
					return 100 + op1(x, 1 - d, e + 1);
				}
			} catch (ArithmeticException ae) {
				// ae.printStackTrace();
				System.out.println("DIVZERO2");
			}
			return 1000;
		}
	}

	private static int op2(int x) throws ArithmeticException {
		// System.out.println("op2(" + x + ")");
		return 100 / x;
	}

}
