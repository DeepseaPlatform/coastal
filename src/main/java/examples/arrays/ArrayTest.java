package examples.arrays;

public class ArrayTest {

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
	
	public static int test2(int[] arr) {
		if (arr[0] < arr[1]) {
			return -1;
		} else if (arr[0] > arr[1]) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main(String[] args) {
		int[] a = new int[2];
		a[0] = 1;
		a[1] = 2;
//		test(a[0], a[1]);
		test2(a);
	}

}
