package arrays;

public class ANewArray {

	public static void main(String[] args) {
		int z = run(5);
		System.out.println(z);
	}

	public static int run(int x) {
		String[] strings = new String[4];
		strings[0] = "a";
		strings[1] = "b";
		strings[2] = "c";
		if (strings[3] == null) {
			return 6;
		} else {
			return strings[2].length() - x;
		}
	}
	
}
