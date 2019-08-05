package kmp;

public class KMP {

	public static int search(int[] haystack, int[] needle) {
		// return KMPAnuvrat.search(haystack, needle);
		return KMPLang.search(haystack, needle);
		// return KMPSanfoundry.search(haystack, needle);
		// return KMPScgilardi.search(haystack, needle);
		// return KMPSchoenig.search(haystack, needle);
		// return KMPVboutchkova.search(haystack, needle);
	}

	public static void main(String[] args) {
		int[] haystack = { 1, 2, 3, 4, 5, 6, 7 };
		int[] needle = { 1, 2, 3, 4 };
		System.out.println(search(haystack, needle));
	}

}
