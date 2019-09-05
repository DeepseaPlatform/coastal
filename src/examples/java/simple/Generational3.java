package simple;

public class Generational3 {

	public static void main(String[] args) {
		compute(0, 0, 0, 0, 0);
	}

	private static void compute(int x, int y, int z, int p, int q) {
		int r = p + q;
		if (x > 0) {
			if (y > 0) {
				if (z > 0) {
					r += 1;
					System.out.println("X>0 Y>0 Z>0");
				} else {
					r += 2;
					System.out.println("X>0 Y>0 Z<=0");
				}
			} else {
				if (z > 0) {
					r += 3;
					System.out.println("X>0 Y<=0 Z>0");
				} else {
					r += 4;
					System.out.println("X>0 Y<=0 Z<=0");
				}
			}
		} else {
			if (y > 0) {
				if (z > 0) {
					r += 5;
					System.out.println("X>0 Y>0 Z>0");
				} else {
					r += 6;
					System.out.println("X>0 Y>0 Z<=0");
				}
			} else {
				if (z > 0) {
					r += 7;
					System.out.println("X>0 Y<=0 Z>0");
				} else {
					r += 8;
					System.out.println("X>0 Y<=0 Z<=0");
				}
			}
		}
		if ((p > 2) && (q > p) && (r < 10)) {
			System.out.println("P>2 Q>P R<10");
		} else {
			System.out.println("P>2 Q>P R>=10");
		}
	}

}
