package examples.kmp;

public class KMP {

	public static int search(int[] haystack, int[] needle) {
		// return KMP_Anuvrat.search(haystack, needle);
		// return KMP_Lang.search(haystack, needle);
		// return KMP_Sanfoundry.search(haystack, needle);
		// return KMP_Scgilardi.search(haystack, needle);
		// return KMP_Schoenig.search(haystack, needle);
		return KMP_Vboutchkova.search(haystack, needle);
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
Darwin Geld.local 17.6.0 Darwin Kernel Version 17.6.0: Tue May  8 15:22:16 PDT 2018; root:xnu-4570.61.1~1/RELEASE_X86_64 x86_64
Intel(R) Core(TM) i7-3615QM CPU @ 2.30GHz

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



Results - 2018.07.03 
--------------------
Darwin Geld.local 17.6.0 Darwin Kernel Version 17.6.0: Tue May  8 15:22:16 PDT 2018; root:xnu-4570.61.1~1/RELEASE_X86_64 x86_64
Intel(R) Core(TM) i7-3615QM CPU @ 2.30GHz

runs/paths/revisit/infeas/time

              H4/N2            H4/N3              H4/N4
Anuvrat     | 5/8/1/1/239    | 8/18/2/2/346     | 11/22/3/1/509
Lang        | 9/10/0/1/296   | 42/51/0/9/985    | 229/280/0/51/4564
Sanfoundry  | 35/41/0/6/821  | ??? 2/4/0/2/249  | ??? 2/4/0/2/177
Scgilardi   | 35/39/0/4/789  | 93/112/0/19/2162 | 229/280/0/51/4790
Schoenig    | 3/5/0/0/188    | 5/13/1/0/223     | !!!
Vboutchkova | 35/39/0/4/784  | 68/95/8/9/1563   | 160/209/19/9/3211

              H5/N2            H5/N3                H5/N4                 H5/N5
Anuvrat     | 5/8/1/1/348    | 8/18/2/2/615       | 11/24/2/3/495       | 23/48/6/3/789
Lang        | 9/10/0/1/392   | 42/51/0/9/1026     | 229/280/0/51/4780   | 1255/1518/0/263/23610
Sanfoundry  | 67/76/0/9/1712 | ??? 2/4/0/2/182    | ??? 2/4/0/2/303     | ??? 2/4/0/2/208
Scgilardi   | 67/74/0/7/1450 | 205/244/0/39/4155  | 535/648/0/113/10770 | 1255/1518/0/263/23992
Schoenig    | 3/5/0/0/400    | 5/13/1/0/306       | !!!                 | !!!
Vboutchkova | 67/74/0/7/1348 | 130/183/18/15/2914 | 320/423/43/15/6295  | 615/817/84/30/11942



$ uname -a
Darwin Geld.local 17.6.0 Darwin Kernel Version 17.6.0: Tue May  8 15:22:16 PDT 2018; root:xnu-4570.61.1~1/RELEASE_X86_64 x86_64

$ sysctl -n machdep.cpu.brand_string
Intel(R) Core(TM) i7-3615QM CPU @ 2.30GHz

*/