package examples.kmp;

// https://www.fmi.uni-sofia.bg/fmi/logic/vboutchkova/sources/KMPMatch_java.html
public class KMPVboutchkova {

	public static int search(int[] haystack, int[] needle) {
		int[] table = computeTable(needle);
		int j = 0;
		if (haystack.length == 0) {
			return -1;
		}
		for (int i = 0; i < haystack.length; i++) {
			while (j > 0 && needle[j] != haystack[i]) {
				j = table[j - 1];
			}
			if (needle[j] == haystack[i]) {
				j++;
			}
			if (j == needle.length) {
				return i - needle.length + 1;
			}
		}
		return -1;
	}

	public static int[] computeTable(int[] needle) {
		int[] table = new int[needle.length + 1];
		int j = 0;
		for (int i = 1; i < needle.length; i++) {
			while (j > 0 && needle[j] != needle[i]) {
				j = table[j - 1];
			}
			if (needle[j] == needle[i]) {
				j++;
			}
			table[i] = j;
		}
		return table;
	}

}
