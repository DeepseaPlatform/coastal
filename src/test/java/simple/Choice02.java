package simple;

public class Choice02 {

	public static void main(String[] args) {
		run(10, 20);
		System.out.println("done");
	}

	private static void run(int x, int y) {
		if (x < 5) {
			if (y < 7) {
				System.out.println("<5 <7");
			} else {
				System.out.println("<5 >=7");
			}
		} else {
			if (y < 7) {
				System.out.println(">=5 <7");
			} else {
				System.out.println(">=5 >=7");
			}
		}
	}

}
