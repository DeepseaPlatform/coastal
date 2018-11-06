package examples.simple;

public class Exceptions4 {

	public static void main(String[] args) throws MyException {
		int result = op0(0, 1);
		System.out.println(result);
	}

	private static int op0(int x, int y) throws MyException {
		if (x == y) {
			throw new MyException(55);
		} else {
			try {
				return op1(x + 1, y - 1);
			} catch (MyException e) {
				return e.getValue();
			}
		}
	}

	private static int op1(int x, int y) throws MyException {
		if (x > 5) {
			throw new MyException(99);
		}
		return 1000;
	}

	@SuppressWarnings("serial")
	private static class MyException extends Exception {
		private final int value;
		MyException(int v) {
			value = v;
		}
		public int getValue() {
			return value;
		}
	}

}
