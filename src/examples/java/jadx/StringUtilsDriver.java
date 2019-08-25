package jadx;

import java.util.Arrays;

public class StringUtilsDriver {
	public static void mainProcess(char ch1, char ch2, char ch3, char ch4, char ch5, char ch6, char ch7, char ch8, char ch9) {
		char[] q = {ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9};
		try {
			SStringUtils su = new SStringUtils();
			char[] res = su.unescapeString(q);
			char[] res1 = SStringUtils.escape(q);
			char[] res2 = SStringUtils.escapeXML(q);
			char[] res3 = SStringUtils.escapeResValue(q);
			char[] res4 = SStringUtils.escapeResStrValue(q);
			System.out.println(">> " + Arrays.toString(res) + "\n "
					+ Arrays.toString(res1) + "\n " + Arrays.toString(res2)
					+ Arrays.toString(res3) + "\n " + Arrays.toString(res4));
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		mainProcess('&', '\n', '$', '*', '<', '!', '?', '>', '/');
	}
}
