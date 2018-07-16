package examples.simple;

public class Choice2 {

	public static void main(String[] args) {
		int result = choose(3, 4);
		System.out.println(result);
	}

	private static int choose(int x, int y) {
		if (x < 10) {
			if (y < 10) {
				return -2;
			} else {
				return -1;
			}
		} else {
			if (y < 10) {
				return 1;
			} else {
				return 11;
			}
		}
	}

}
