package examples.strings;

public class Mystery {
	
	public static void main(String[] args) {
		System.out.println("start");
		//preserveSomeHtmlTagsAndRemoveWhitespaces("<<<<<a href=\">    @");
		//preserveSomeHtmlTagsAndRemoveWhitespaces("blah");
		//preserveSomeHtmlTagsAndRemoveWhitespaces(" <<  <a hRef=\"\">    ");
		preserveSomeHtmlTagsAndRemoveWhitespaces("<<<<<<<<<<<<<<<");
		System.out.println ("end");
	}

	public static String preserveSomeHtmlTagsAndRemoveWhitespaces(String body) {
		if (body == null)
			return body;
		int len = body.length();
		int i = 0;
		int old = i - 1;
		while (i < len) {
			//assert i >= old: "Infinite loop";
			if (i < old) {
				//for (int idx = 0; idx < body.length(); idx++) {
				//	Debug.printPC("Current PC: ");
				//}
				// throw new RuntimeException("Infinite loop");
				return "EXCEPTION: Infinite loop";
			}
			old = i;
			if (body.charAt(i) == '<') {
				if (i + 14 < len &&
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
					if (idxEnd == -1)
						idxEnd = body.indexOf("</A>", idxStart);
					i = idxEnd + 4;
					continue;
				}
			}
			i++;
			

		}
		return body;
	}
	
}