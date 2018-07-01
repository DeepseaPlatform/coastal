package examples.simple;

public class MaxChoice3 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		System.out.println(result);
	}

	private static int compute(int x, int y, int z) {
		if (Math.max(x, y) < z) {
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

}
