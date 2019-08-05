package simple;

public class Min {

	public static void main(String[] args) {
		//run(0, 0, 0); // OK, BUT INSERTED PATHS WRONG
		//run(0, 1, 2); // BROKEN
		run(0, 2, 1); // OK
		//run(1, 0, 2); // BROKEN
		//run(1, 2, 0); // OK
		//run(2, 0, 1); // BROKEN
		//run(2, 1, 0); // OK
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
