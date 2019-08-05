package statik;

public class Statik02 {

	public static final int N;
	
	static {
		int m = 0;
		for (int i = 0; i < 10; i++) {
			m += i * i * i;
		}
		N = m;
	}
	
	public static void main(String[] args) {
		run(5);
	}

	private static void run(int x) {
		if (x > N) {
			System.out.println(">5");
		} else {
			System.out.println("<=5");
		}
	}
	
}
