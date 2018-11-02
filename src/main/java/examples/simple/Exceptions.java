package examples.simple;

public class Exceptions {

	public static void main(String[] args) {
		int result = op0(1, 4);
		System.out.println(result);
	}

	private static int op0(int x, int y) throws ArithmeticException {
		return op1(x - 1, y + 1);
	}
	
	private static int op1(int x, int y) throws ArithmeticException {
		int z = x / y;
		try {
			int q = op2(y + 1, x - 1) / x;
			return q;
		} catch (ArithmeticException e) {
			System.out.println("DIVZERO1");
		}
		return z;
	}

	private static int op2(int x, int y) throws ArithmeticException {
		int z = x / y;
		try {
			int q = y / x;
			return q;
		} catch (ArithmeticException e) {
			System.out.println("DIVZERO2");
		}
		return z;
	}
	
}
