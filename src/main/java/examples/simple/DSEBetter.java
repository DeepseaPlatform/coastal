package examples.simple;

public class DSEBetter {

	public static int identity(int x) {
		return x * x * x;
	}

	public static void test2(int x, int y) {
		if (x * x * x > 0) {
			System.out.println("1");
		} else {
			System.out.println("2");
		}
	}

	public static void test(int b, int x, int y) {
		System.out.println("b = " + b + " x = " + x + " y = " + y);
		if (b == 1) {
			if (y <= 0) {
				System.out.println("1");
			} else {
				System.out.println("2");
			}
		} else {
			if (x <= 0 && identity(y) == 1) {
				System.out.println("3");
			} else {
				System.out.println("4");
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test(1, 0, 0); // this doesn't work since we don't carry over values from the previous models, why not?
		// test2(0,0); // fails but test2(1,1) works ... this is correct behaviour
	}

}
