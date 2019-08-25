package arrays;

public class Array01 {

	public static void main(String[] args) {
		int[] a = new int[2];
		a[0] = 0;
		a[1] = 0;
		test(a[0], a[1]);
	}

	public static int test(int x, int y) {
		int[] arr = new int[2];
		arr[0] = x;
		arr[1] = y;
		if (arr[0] < arr[1]) {
			return -1;
		} else if (arr[0] > arr[1]) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
