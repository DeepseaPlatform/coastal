package flink;

public class IntParserDriver {

	public static void mainProcess(char a, char b, char c, char d, char e, char f, char g, char h, char i) {
		byte[] q = {(byte) a, (byte) b, (byte) c, (byte) d, (byte) e, (byte) f, (byte) g, (byte) h, (byte) i};
		int res = IntParser.parseField(q, 0, q.length);
		System.out.println(">> " + res);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			mainProcess('1', '2', '3', '4', '5', '6', '7', '8', '9');
		}
	}
}
