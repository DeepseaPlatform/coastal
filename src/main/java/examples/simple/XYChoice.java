package examples.simple;

public class XYChoice {

	public static void main(String[] args) {
		int result = choose(3, 4);
		System.out.println(result);
	}

	private static int choose(int x, int y) {
		if (x < y) {
			if (y > 5) {
				//System.out.println("CHOOSE 1");
				return 1000 + x - 1;
			} else {
				//System.out.println("CHOOSE 2");
				return 2000 + y + 1;
			}
		} else if (x + y < 10) {
			//System.out.println("CHOOSE 3");
			return 3000;
		} else {
			//System.out.println("CHOOSE 4");
			return 4000 + x - y;
		}
	}

}
