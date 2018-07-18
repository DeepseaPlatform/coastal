package examples.kmp;

// http://www.sanfoundry.com/java-program-knuth-morris-pratt-algorithm/
public class KMPSanfoundry {

	public static int search(int[] haystack, int[] needle) {
		int[] table = computeTable(needle);
		int i = 0, j = 0;
		int hl = haystack.length;
		int nl = needle.length;
		while (i < hl && j < nl) {
			if (haystack[i] == needle[j]) {
				i++;
				j++;
			} else if (j == 0) {
				i++;
			} else {
				j = table[j - 1] + 1;
			}
		}
		return ((j == nl) ? (i - nl) : -1);
	}

	public static int[] computeTable(int[] needle) {
		int nl = needle.length;
		int[] table = new int[nl];
		table[0] = -1;
		for (int j = 1; j < nl; j++) {
			int i = table[j - 1];
			while ((needle[j] != needle[i + 1]) && i >= 0) {
				i = table[i];
			}
			if (needle[j] == needle[i + 1]) {
				table[j] = i + 1;
			} else {
				table[j] = -1;
			}
		}
		return table;
	}

}
