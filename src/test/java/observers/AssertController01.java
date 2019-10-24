package observers;

public class AssertController01 {

	public static void main(String[] args) {
		compute(3);
	}

	private static void compute(int x) {
		if (x < 10) {
			assert (x > 11); // MUST REPORT VIOLATION
			// assert (x < 11); // MUST NOT REPORT VIOLATION
		}
	}

}
