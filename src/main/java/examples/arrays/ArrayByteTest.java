package examples.arrays;

public class ArrayByteTest {

	public static int test(byte x, byte y) {
		byte[] arr = new byte[2];
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
	
	public static int test2(byte[] arr) {
		if (arr[0] < arr[1]) {
			return -1;
		} else if (arr[0] > arr[1]) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main(String[] args) {
		byte[] a = new byte[2];
		a[0] = 0;
		a[1] = 0;
		test2(a);
		//if (test2(a)!=test(a[0], a[1])) {
		//	System.out.println("FAILED");
		//}
	}

}
