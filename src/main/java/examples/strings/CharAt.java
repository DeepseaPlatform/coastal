package examples.strings;

public class CharAt {

	public static void main(String[] args) {
		test("abc", 1);
	}

	private static void test(String str, int ofs) {
		if (str.charAt(ofs) == 'a') {
			System.out.println("Found");
		} else {
			System.out.println("Not found");
		}
	}

}
