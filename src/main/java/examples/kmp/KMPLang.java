package examples.kmp;

// http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm
public class KMPLang {

	public static int search(int[] haystack, int[] needle) {
		int nl = needle.length;
		int[] table = computeTable(needle);
		int i = 0, j = 0;
		while (i < nl) {
			while (j >= 0 && haystack[i] != needle[j]) {
				j = table[j];
			}
			i++;
			j++;
			if (j == nl) {
				return i - j;
			}
		}
		return -1;
	}

	public static int[] computeTable(int[] needle) {
		int nl = needle.length;
		int[] table = new int[nl + 1];
		int i = 0, j = -1;
		table[i] = j;
		while (i < nl) {
			while (j >= 0 && needle[i] != needle[j]) {
				j = table[j];
			}
			i++;
			j++;
			table[i] = j;
		}
		return table;
	}

}
