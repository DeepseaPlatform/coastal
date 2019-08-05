package simple;

public class SimpleMaxChoice {

	public static void main(String[] args) {
		int result = max(3, 4);
		System.out.println(result);
	}

	private static int max(int x, int y) {
		if (x + 1 > y + 2) {
			return x - 1;
		} else {
			return y;
		}
	}

}
