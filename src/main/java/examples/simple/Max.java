package examples.simple;

public class Max {

	public static void main(String[] args) {
		run(1, 0);
		System.out.println("DONE");
	}

	private static void run(int x, int y) {
		int z = Math.max(x, y);
		if (z == x) {
			System.out.println("X IS MAX");
		} else {
			System.out.println("X NOT MAX");
		}
	}

}
