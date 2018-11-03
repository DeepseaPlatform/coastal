package examples.simple;

public class MaxMin {

	public static void main(String[] args) {
		run(1, 0);
		System.out.println("DONE");
	}

	private static void run(int x, int y) {
		int z = Math.max(x, y);
		int q = Math.min(z, 5);
		if (q == x) {
			System.out.println("X IS MIN OF MAX");
		} else {
			System.out.println("X NOT MIN OF MAX");
		}
	}

}
