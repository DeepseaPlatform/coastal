package examples.kmp;

// https://gist.github.com/shoenig/1430733
public class KMP_Schoenig {

	public static int search(int[] haystack, int[] needle) {
		if (needle.length == 0) {
			return 0;
		}
		if (haystack.length == 0) {
			return -1;
		}
		int m = 0; // index of beg. of current match in haystack
		int i = 0; // pos. of cur value in needle
		int[] T = computeTable(needle);
		while (m + i < haystack.length) {
			if (needle[i] == haystack[m + i]) {
				if (i == needle.length - 1) {
					return m;
				}
				i += 1;
			} else {
				m = m + i - T[i];
				if (T[i] > -1) {
					i = T[i];
				} else {
					i = 0;
				}
			}
		}
		return -1;
	}

	public static int[] computeTable(int[] needle) {
		int[] table = new int[needle.length];
		int pos = 2; // cur pos to compute in T
		int cnd = 0; // index of needle of next character of cur candidate substr
		table[0] = -1; // table[0] := -1
		table[1] = 0; // table[1] := 0
		while (pos < needle.length) {
			if (needle[pos - 1] == needle[cnd]) {
				table[pos] = cnd;
				cnd += 1;
				pos += 1;
			} else if (cnd > 0) {
				cnd = table[cnd];
			} else {
				table[pos] = 0;
				pos += 1;
			}
		}
		return table;
	}

}
