package strings;

import za.ac.sun.cs.coastal.Symbolic;

public class Mystery {
	
	public static void main(String[] args) {
		preserveSomeHtmlTagsAndRemoveWhitespaces("012345678901234567");
		// runExample();
	}

	public static void runExample() {		
		char[] s = new char[18];
		s[0] = 0; s[1] = 0; s[2] = 0; s[3] = 60; s[4] = 97; s[5] = 32; s[6] = 104; s[7] = 114; s[8] = 101;
		s[9] = 102; s[10] = 61; s[11] = 34; s[12] = 50; s[13] = 51; s[14] = 52; s[15] = 53; s[16] = 54; s[17] = 55;
		String str = new String(s);
		System.out.println("Input : " + str);
		String result = preserveSomeHtmlTagsAndRemoveWhitespaces(str);
		System.out.println("output: " + result);
	}
	
	public static void printInput(String s) {
		char[] str = s.toCharArray();
		String output = "";
		for (int i = 0; i < str.length; i++) {
			output += "s[" + i + "] = " + (int) str[i] + ";"; 
 		}
		System.out.println("OUPUT: " + output);
	}
	
	public static String preserveSomeHtmlTagsAndRemoveWhitespaces(String body) {
		if (body == null) {
			return body;
		}
		int len = body.length();
		int i = 0;
		int old = i - 1;
		while (i < len) {
			//assert i >= old: "Infinite loop";
			if (i <= old) {
				//for (int idx = 0; idx < body.length(); idx++) {
				//	Debug.printPC("Current PC: ");
				//}
				// throw new RuntimeException("Infinite loop");
				// printInput(body);
				Symbolic.printPC("HERE");
				Symbolic.stop("BUG");
				return "EXCEPTION: Infinite loop";
			}
			old = i;
			if (body.charAt(i) == '<') {
				if (i + 14 < len
				&&
				(body.charAt(i + 8) == '\"')
				&&
				(body.charAt(i + 7) == '=')
				&&
				(body.charAt(i + 6) == 'f' || body.charAt(i + 6) == 'F')
				&&
				(body.charAt(i + 5) == 'e' || body.charAt(i + 5) == 'E')
				&&
				(body.charAt(i + 4) == 'r' || body.charAt(i + 4) == 'R')
				&&
				(body.charAt(i + 3) == 'h' || body.charAt(i + 3) == 'H')
				&&
				(body.charAt(i + 2) == ' ')
				&&
				(body.charAt(i + 1) == 'a' || body.charAt(i + 1) == 'A')
				) {
					int idx = i + 9;
					int idx2 = body.indexOf("'", idx);
					int idxStart = body.indexOf(">", idx2);
					int idxEnd = body.indexOf("</a>", idxStart);
					if (idxEnd == -1) {
						idxEnd = body.indexOf("</A>", idxStart);
					}
					i = idxEnd + 4;
					continue;
				}
			}
			i++;
		}
		return body;
	}
	
}
