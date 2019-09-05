package simple;

public class Generational {

	public static void main(String[] args) {
		compute(0, 0, 0, 0, 0);
	}

	private static void compute(int x, int y, int z, int p, int q) {
		if (x > 0) {
			if (y > 0) {
				System.out.println("X>0 Y>0");
			} else {
				System.out.println("X>0 Y<=0");
			}
		} else {
			if (y > 0) {
				System.out.println("X<=0 Y>0");
			} else {
				System.out.println("X<=0 Y<=0");
			}
		}
	}

}
