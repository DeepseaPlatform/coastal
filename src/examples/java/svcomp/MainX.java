package svcomp;

import org.sosy_lab.sv_benchmarks.Verifier;

public class MainX {

	public static void main(String[] args) {
		long x = Verifier.nondetInt();
		x = x > 0 ? -x : x;
		long y = 5;
		MainX inst = new MainX();
		inst.test(x, y);
	}

	/*
	 * test LADD, LCMP, LMUL, LNEG, LSUB , Invokestatic bytecodes no globals
	 */

	public static void test(long x, long z) { // invokestatic

		System.out.println("Testing ExSymExeLongBytecodes");

		long a = x;
		long b = z;
		long c = 34565;

		long negate = -z; // LNEG

		long sum = a + b; // LADD
		long sum2 = z + 9090909L; // LADD
		long sum3 = 90908877L + z; // LADD

		long diff = a - b; // LSUB
		long diff2 = b - 19999999999L; // LSUB
		long diff3 = 9999999999L - a; // LSUB

		long mul = a * b; // LMUL
		long mul2 = a * 19999999999L; // LMUL
		long mul3 = 19999999999L * b; // LMUL

		if (diff > c) {
			assert false;
			System.out.println("branch diff > c");
		} else
			System.out.println("branch diff <= c");
		if (sum < z)
			System.out.println("branch sum < z");
		else
			System.out.println("branch sum >= z");
	}
}
