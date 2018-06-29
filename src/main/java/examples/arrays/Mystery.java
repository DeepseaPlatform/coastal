package examples.arrays;

//import gov.nasa.jpf.symbc.Debug;

public class Mystery {
	
	public static class SString {
		int[] str = null;
		//public SString(String s) {
		//	str = s.toCharArray();
		//}
		
		public SString(int[] chars) {
			str = chars;
		}
		
		private boolean matchSub(int idx, char[] s2) {
			int s2i = idx;		
			if (idx < 0 || s2.length + idx >= length())
				return false;
			for (int i = 0; i < s2.length; i++) {
				if (s2[i] != str[s2i])
					return false;
				s2i++;
			}
			return true;
		}
		
		public int indexOf(String s, int idx) {
			char[] s2 = s.toCharArray();
			for (int i = idx; i < str.length; i++) {
				if (matchSub(i,s2)) 
					return i;
			}
			return -1;
		}
		
		public int indexOf(char c, int idx) {
			return indexOf(""+c,idx);
		}
		
		public int[] get() {
			return str;
		}
		
		public int length() {
			return str.length;
		}
		
		public int charAt(int i) {
			return str[i];
		}
		
		
	}
	
	public static boolean matchAt(int[] s, int[] q, int idx) {
		int i = idx;
		while ((i < s.length) && (i < q.length)) {
			if (s[i] != q[i]) { return false; }
			i++;
		}
		return (i >= q.length);
	}
	
	public static int indexOf(int[] s, int[] q, int idx) {
		int i = idx;
		while (i < s.length) {
			if (matchAt(s, q, i)) { return i; }
			i++;
		}
		return -1;
	}
	
	public static void main (String[] args) {
		//Mystery mqm = new Mystery();
		System.out.println("start");
		
		int[] input = new int[5];
		for (int i = 0; i < 5; i++) 
			input[i] = 0;
		
		int[] in = (new SString(input)).get();
		
		preserveSomeHtmlTagsAndRemoveWhitespaces(in);
				
		//preserveSomeHtmlTagsAndRemoveWhitespaces(mqm.new SString("<<<<<a href=\">    @"));
		//preserveSomeHtmlTagsAndRemoveWhitespaces(mqm.new SString("blah"));
		//preserveSomeHtmlTagsAndRemoveWhitespaces(mqm.new SString(" <<  <a hRef=\"\">    "));
		System.out.println ("end");
	}
	/*
	private static char[] buildInput(int size) {
		char[] in = new char[size];
		for (int i=0;i<size;i++) {
			in[i]=Debug.makeSymbolicChar("in"+i);		
		}
		return in;
	}
	*/
	
	public static int[] preserveSomeHtmlTagsAndRemoveWhitespaces(int[] body) {
		if (body == null)
			return body;
		int len = body.length;
		int i = 0;
		int old = i - 1;
		while (i < len) {
			//assert i >= old: "Infinite loop";
			if (i < old) {
				//for (int idx = 0; idx < body.length(); idx++) {
				//	Debug.printPC("Current PC: ");
				//}
				throw new RuntimeException("Infinite loop");
			}
			old = i;
			if (body[i] == '<') {
				if (i + 14 < len &&
				(body[i + 8] == '\"')
				&&
				(body[i + 7] == '=')
				&&
				(body[i + 6] == 'f' || body[i + 6] == 'F')
				&&
				(body[i + 5] == 'e' || body[i + 5] == 'E')
				&&
				(body[i + 4] == 'r' || body[i + 4] == 'R')
				&&
				(body[i + 3] == 'h' || body[i + 3] == 'H')
				&&
				(body[i + 2] == ' ')
				&&
				(body[i + 1] == 'a' || body[i + 1] == 'A')
				) {
					int idx = i + 9;
					int idx2 = indexOf(body,new int[] {'"'}, idx);
					int idxStart = indexOf(body,new int[] {'>'}, idx2);
					int idxEnd = indexOf(body, new int[] {'<','/','a','>'}, idxStart);
					if (idxEnd == -1)
						idxEnd = indexOf(body,new int[] {'<','/','A','>'}, idxStart);
					i = idxEnd + 4;
					continue;
				}
			}
			i++;
			

		}
		return body;
	}
}