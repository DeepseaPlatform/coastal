package statik;

public class Statik03A {

	public static int n = 5;
	
	public static void main(String[] args) {
		run(3);
	}

	private static void run(int x) {
		System.out.println(Statik03B.value);
		if (x > 5) {
			Statik03B.value = x + 1;
		} else {
			Statik03B.value = x - 1;
		}
		System.out.println(Statik03B.value);
	}
	
}
