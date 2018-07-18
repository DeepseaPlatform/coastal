package examples.kmp;

// https://gist.github.com/anuvrat/2382245
public class KMPAnuvrat {

	public static int search(int[] haystack, int[] needle) {
		int m = 0;
		int i = 0;
		int[] table = computeTable(needle);
		while (m + i < haystack.length) {
			if (needle[i] == haystack[m + i]) {
				if (i == needle.length - 1) {
					return m;
				} else {
					i++;
				}
			} else {
				m = m + i - table[i];
				if (table[i] > -1) {
					i = table[i];
				} else {
					i = 0;
				}
			}
		}
		return -1;
	}

	public static int[] computeTable(int[] needle) {
		int nl = needle.length;
		if (nl == 0) {
			return new int[0];
		}
		int[] table = new int[nl + 1];
		table[0] = -1;
		table[1] = 0;
		int first = needle[0];
		for (int i = 1; i < nl; i++) {
			int v = table[i];
			if (v == 0) {
				if (needle[i] == first) {
					table[i + 1] = 1;
				} else {
					table[i + 1] = 0;
				}
			} else if (needle[i] == needle[v]) {
				table[i + 1] = v + 1;
			} else {
				table[i + 1] = 0;
			}
		}
		return table;
	}

}
