package svcomp;

import org.sosy_lab.sv_benchmarks.Verifier;

class MainX {
	  public static void main(String[] args) {
		    StringBuilder sb = new StringBuilder(Verifier.nondetString());
		    sb.append("Z");
		    String s = sb.toString();
		    assert(s.equals("fg"));
		  }
		}

/*
class MainX {

	public static char[] f(char c[]) {
		if (c != null && c.length > 0) {
			c[0] = 's';
		}
		return c;
	}

	public static void main(String[] args) {
		String arg = Verifier.nondetString();
		if (arg.length() != 5)
			return;
		char[] c = f(arg.toCharArray());
		String s = new String("HELLO") + new String(c, 0, c.length);
		assert s.charAt(5) == 's';
	}
}
*/