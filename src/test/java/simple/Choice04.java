package simple;

public class Choice04 {

	public static void main(String[] args) {
		int result = run(3, 4, 5);
		System.out.println(result);
	}

	private static int run(int x, int y, int z) {
		if (x < 10) {
			if (y < 10) {
				if (z < 10) {
					return 1;
				} else {
					if (x == y) {
						return 2;
					} else {
						return 3;
					}
				}
			}
		} else {
			if (x == 5) {
				return 5;
			} else {
				return 6;
			}
		}
		return 4;
	}

}
