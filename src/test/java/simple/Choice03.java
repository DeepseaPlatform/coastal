package simple;

public class Choice03 {

	public static void main(String[] args) {
		run(10, 20, 30);
		System.out.println("done");
	}

	private static void run(int x, int y, int z) {
		if (x < 5) {
			if (y < 7) {
				if (z == 9) {
					System.out.println("<5 <7 ==9");
				} else {
					System.out.println("<5 <7 !=9");
				}
			} else {
				if (z == 9) {
					System.out.println("<5 >=7 ==9");
				} else {
					System.out.println("<5 >=7 !=9");
				}
			}
		} else {
			if (y < 7) {
				if (z == 9) {
					System.out.println(">=5 <7 ==9");
				} else {
					System.out.println(">=5 <7 !=9");
				}
			} else {
				if (z == 9) {
					System.out.println(">=5 >=7 ==9");
				} else {
					System.out.println(">=5 >=7 !=9");
				}
			}
		}
	}

}
