package examples.simple;

public class MaxChoice4 {

	public static void main(String[] args) {
		int result = compute(3, 4, 5);
		System.out.println(result);
	}

	public static int compute2(int x, int y, int z) {
		if (Math.max(x, y) < 5) {
			if (x + y > 10) {
				return -1;
				// this is "reachable" since we think we can solve to get us here, but we cannot
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

	public static int summaryMax(int x, int y) {
		if (x > y) {
			return x;
		} else {
			return y;
			/*
			 * if this was a summary we would have had something like
			 * ((x > y) && ret = x) \/ ((x <= y) && ret = y)
			 */
		}
	}

	public static int compute(int x, int y, int z) {
		if (summaryMax(x, y) < 5) {
			if (x + y > 10) {
				return -1;
				// now just plain unreachable
				// (x+y>10 && ((x > y) && ret = x) \/ ((x <= y) && ret = y) && ret < 5 is unsat
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
