package simple;

public class Switching2 {

	public static void main(String[] args) {
		tryitA(3);
		// tryitA(3, 4);
	}

	public static void tryitA(int a) {
		int c = 99;
		switch (a) {
		case 1:
			c = 1;
		case 2:
			c = 2;
			break;
		default:
			c = 3;
			break;
		}
		System.out.println(c);
	}

//	public static void tryitC(int a, int b) {
//		int c = 99;
//		switch (a) {
//		case 1:
//			c = 1;
//			break;
//		case 2:
//			c = 2;
//			switch (b) {
//			case 1:
//				c += 10;
//				break;
//			case 2:
//				c += 20;
//				break;
//			}
//			break;
//		}
//		System.out.println(c);
//	}
}
