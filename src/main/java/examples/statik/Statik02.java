package examples.statik;

public class Statik02 {

	public final static int n;
	
	static {
		int m = 0;
		for (int i = 0; i < 10; i++) {
			m += i * i * i;
		}
		n = m;
	}
	
	public static void main(String[] args) {
		run(5);
	}

	private static void run(int x) {
		if (x > n) {
			System.out.println(">5");
		} else {
			System.out.println("<=5");
		}
	}
	
}
