package examples.simple;

public class Choice1 {

	public static void main(String[] args) {
		int result = choose(3);
		System.out.println(result);
	}

	private static int choose(int x) {
		if (x < 10) {
			return -1;
		} else {
			return 1;
		}
	}

}
