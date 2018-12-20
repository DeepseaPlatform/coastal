package examples.simple;

public class Array02 {

	public static void main(String[] args) {
		test(new int[] {0, 0}, 0, 0, 1);
	}

	public static void test(int[] arr, int lo, int hi, int target) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		if (sum < lo) {
			System.out.println("Too low");
		} else if (sum > hi) {
			System.out.println("Too high");
		} else {
			System.out.println("Just right");
		}
		if (sum == target) {
			System.out.println("Target hit");
		} else {
			System.out.println("Target missed");
		}
	}

}
