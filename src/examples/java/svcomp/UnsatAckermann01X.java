/*
 * Origin of the benchmark:
 *     license: MIT (see /java/jayhorn-recursive/LICENSE)
 *     repo: https://github.com/jayhorn/cav_experiments.git
 *     branch: master
 *     root directory: benchmarks/recursive
 * The benchmark was taken from the repo: 24 January 2018
 */
package svcomp;

import org.sosy_lab.sv_benchmarks.Verifier;

public class UnsatAckermann01X {

  static int ackermann(int m, int n) {
    if (m == 0) {
      return n + 1;
    }
    if (n == 0) {
      return ackermann(m - 1, 1);
    }
    return ackermann(m - 1, ackermann(m, n - 1));
  }

  public static void main(String[] args) {
    int m = Verifier.nondetInt();
    int n = Verifier.nondetInt();
    int result = ackermann(m, n);
    if (m < 2 || result >= 4) {
      return;
    } else {
      assert false;
    }
  }
}
