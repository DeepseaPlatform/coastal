package simple;

public class DepthLimitTest {

	public static void main(String[] args) {
		loop(1);
	}

	private static void loop(int iters) {
		if (iters > 15) {
			iters = 15;
		}
		int t = 0;
		for (int i = 0; i < iters; i++) {
			t += i * i;
		}
		System.out.println(t);
	}

}
