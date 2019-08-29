package simple;

public class Switching6 {

	public static void main(String[] args) {
		tryit(3);
	}

	public static void tryit(int a) {
		int c = 99;
		switch (a) {
		case 1:
			c = 1;
			break;
		case 10:
			c = 2;
			break;
		case 100:
			c = 3;
			break;
		case 1000:
			c = 4;
			break;
		default:
			c = 5;
			break;
		}
		System.out.println(c);
	}

}
