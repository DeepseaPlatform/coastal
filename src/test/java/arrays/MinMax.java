package arrays;

public class MinMax {

	public static int minOf(int x, int y) {
		if (x < y) {
			return x;
		} else {
			return y;
		}
	}
	
	public static int maxOf(int x, int y) {
		if (x > y) {
			return x;
		} else {
			return y;
		}
	}
	
}
