package simple;

public class MaxChoice2 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		System.out.println(result);
	}

	private static int compute(int x, int y, int z) {
		if (Maxer.max(x, y) < z) {
			if (x + y > 5) {
				return x;
			} else {
				return 10;
			}
		} else {
			if (y < 10) {
				return z;
			} else {
				return x + y + z;
			}
		}
	}

	private static class Maxer {

		private static int max(int x, int y) {
			if (x + 1 > y + 2) {
				return x - 1;
			} else {
				return y;
			}
		}

	}

}
