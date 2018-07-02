package examples.kmp;

public class KMP {

	public static int search(int[] haystack, int[] needle) {
		// return KMP_Anuvrat.search(haystack, needle);
		// return KMP_Lang.search(haystack, needle);
		// return KMP_Sanfoundry.search(haystack, needle);
		// return KMP_Scgilardi.search(haystack, needle);
		return KMP_Schoenig.search(haystack, needle);
		// return KMP_Vboutchkova.search(haystack, needle);
	}

	public static void main(String[] args) {
		int[] haystack = { 1, 2, 3, 4, 5 };
		int[] needle = { 1, 2, 3, 4, 5 };
		System.out.println(search(haystack, needle));
	}

}

/*

Results - 2018.07.02
--------------------

runs/paths/infeas/time

              H4/N2          H4/N3             H4/N4
Anuvrat     | 6/6/0/348    | 16/16/0/566     | 40/40/0/1138
Lang        | 9/10/1/426   | 42/51/9/1466    | 229/280/51/7686
Sanfoundry  | 35/41/6/2246 | ??? 2/4/2/407   | ??? 2/4/2/358
Scgilardi   | 35/39/4/1085 | 93/112/19/2864  | 229/280/51/7723
Schoenig    | 3/3/0/247    | -               | -
Vboutchkova | 16/16/0/589  | 31/31/0/927     | 47/47/0/1405

              H5/N2          H5/N3             H5/N4               H5/N5
Anuvrat     | 6/6/0/299    | 16/16/0/591     | 40/40/0/1126      | 96/96/0/2440
Lang        | 9/10/1/380   | 42/51/9/1414    | 229/280/51/7741   | 1255/1518/263/37234
Sanfoundry  | 67/76/9/2079 | ??? 2/4/2/267   | ??? 2/4/2/240     | ??? 2/4/2/237
Scgilardi   | 67/74/7/1942 | 205/244/39/5965 | 535/648/113/15210 | 1255/1518/263/38009
Schoenig    | 3/3/0/228    | !!!             | !!!               | !!!
Vboutchkova | 20/20/0/664  | 40/40/0/1165    | 63/63/0/1781      | 86/86/0/2538

*/