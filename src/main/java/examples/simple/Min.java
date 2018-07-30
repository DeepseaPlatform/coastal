package examples.simple;

public class Min {

	public static void main(String[] args) {
		//run(0, 0, 0);
		run(0, 1, 2);
		System.out.println("DONE");
	}

	private static void run(int x, int y, int z) {
		int q = Math.min(x, Math.min(y, z));
		if (q == x) {
			System.out.println("X IS MIN");
		} else if (q == y) {
			System.out.println("Y IS MIN");
		} else {
			System.out.println("Z IS MIN");
		}
	}

}
