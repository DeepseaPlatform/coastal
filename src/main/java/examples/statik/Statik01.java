package examples.statik;

public class Statik01 {

	public static int n = 5;
	
	public static void main(String[] args) {
		run(5);
	}

	private static void run(int x) {
		if (x > n) {
			n++;
			System.out.println(">5");
		} else {
			System.out.println("<=5");
		}
	}
	
}
