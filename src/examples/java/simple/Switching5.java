package simple;

public class Switching5 {

	public static void main(String[] args) {
		tryitB(1, 1, 1);
	}

	public static void tryitB(int a, int b, int d) {
		int c = 99;
		switch (a) {
		case 1:
			c = 1;
			switch (d) {
			case 1:
				c += 100;
				break;
			case 2:
				c += 200;
				break;
			}
			break;
		case 2:
			c = 2;
			if (b > 3) {
				switch (b) {
				case 1:
					c += 10;
					break;
				case 2:
					c += 20;
					break;
				}
			}
			break;
		}
		System.out.println(c);
	}

}
