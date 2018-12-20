package tests.arrays;

public class Array02 {

	public static void main(String[] args) {
		int[] a = new int[2];
		a[0] = 0;
		a[1] = 0;
		test(a);
	}

	public static int test(int[] arr) {
		System.out.println(arr[0] + " " + arr[1]);
		if (arr[0] < arr[1]) {
			return -1;
		} else if (arr[0] > arr[1]) {
			return 1;
		} else {
			return 0;
		}
	}

}
