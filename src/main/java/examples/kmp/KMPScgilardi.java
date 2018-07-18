package examples.kmp;

// https://github.com/scgilardi/kmp-search/blob/master/java/src/kmp_search/Context.jav
public class KMPScgilardi {

	public static int search(int[] haystack, int[] needle) {
		int[] table = computeTable(needle);
		int hl = haystack.length;
		int nl = needle.length;
		int i = 0;
		int j = 0;
		for (; i < hl && j < nl; ++i, ++j) {
			int b = haystack[i];
			while (j != -1 && needle[j] != b) {
				j = table[j];
			}
		}
		if (j == nl) {
			j = table[j];
			return i - nl;
		} else {
			return -1;
		}
	}

	public static int[] computeTable(int[] needle) {
		int nl = needle.length;
		int[] table = new int[nl + 1];
		int i, j;
		for (i = 0, j = -1; i < nl; ++i, ++j) {
			table[i] = j;
			int v = needle[i];
			while (j != -1 && needle[j] != v) {
				j = table[j];
			}
		}
		table[i] = j;
		return table;
	}

}
