package examples.strings;

public class StartsWith {

	public static int test(String str) {
		if (str.startsWith("bl", 1)) {
			if (str.endsWith("ue")) {
				System.out.println("Match");
				return 0;
			} else {
				System.out.println("No match (A)");
				return 1;
			}
		} else {
			System.out.println("No match (B)");
			return 2;
		}
	}

	public static void main(String[] args) {
		test("hello");
	}

}
