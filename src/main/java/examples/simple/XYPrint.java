package examples.simple;

public class XYPrint {

	public static void main(String[] args) {
		print(3, 4);
		// print(5, false);
	}

	private static void print(int x, int y) {
		int z = x + y;
		if (x < y) {
			System.out.println(z);
		} else {
			System.out.println(z - 2 * y);
		}
	}

	public static void print(int x, boolean y) {
		if (y) {
			System.out.println(x + 2);
		} else {
			System.out.println(x - 2);
		}
	}

}
