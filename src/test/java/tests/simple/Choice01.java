package tests.simple;

public class Choice01 {

	public static void main(String[] args) {
		run(10);
		System.out.println("done");
	}

	private static void run(int x) {
		if (x < 5) {
			System.out.println("<5");
		} else {
			System.out.println(">=5");
		}
	}

}
