package fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

/**
 * Figure 2
 * 
 * SAGE: Whitebox Fuzzing for Security Testing Godefroid, Levin, Molnar CACM,
 * Vol 55(3), 40--44, March 2012
 */
public class Fuzzing08 {

	public static void main(String[] args) {
		top(new char[] { 'a', 'b', 'c', 'd' });
	}

	public static void top(char[] input) {
		int cnt = 0;
		if (input[0] == 'b') {
			cnt++;
		}
		if (input[1] == 'a') {
			cnt++;
		}
		if (input[2] == 'd') {
			cnt++;
		}
		if (input[3] == '!') {
			cnt++;
		}
		if (cnt >= 4) {
			Symbolic.mark("ERROR");
			System.exit(1);
		}
	}

}
