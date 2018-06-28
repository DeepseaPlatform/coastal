package examples.arrays;

public class MinMaxSorting {

	private static final int N = 20;

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
	
	public static void sort(int[] arr) {
		boolean sorted = false;
		while (!sorted) {
			for (int i = 1, j = 0; i < N; i++, j++) {
				int t = maxOf(arr[j], arr[i]);
				arr[j] = minOf(arr[j], arr[i]);
				arr[i] = t;
			}
			sorted = true;
			for (int i = 1, j = 0; i < N; i++, j++) {
				if (arr[j] > arr[i]) {
					sorted = false;
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = (i * 3019) % 2003;
		}
		sort(a);
		System.out.print(a[0]);
		for (int i = 1; i < N; i++) {
			System.out.print(" " + a[i]);
		}
		System.out.println(" :: " + (System.currentTimeMillis() - t0));
	}

}
