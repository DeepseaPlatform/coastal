package simple;

/*
 * Origin of the benchmark:
 *     license: 4-clause BSD (see /java/jbmc-regression/LICENSE)
 *     repo: https://github.com/diffblue/cbmc.git
 *     branch: develop
 *     directory: regression/cbmc-java/tableswitch1
 * The benchmark was taken from the repo: 24 January 2018
 */

@SuppressWarnings("serial")
class A extends RuntimeException { }

@SuppressWarnings("serial")
class B extends A { }

@SuppressWarnings("serial")
class C extends B { }

public class Main {
  static void foo() {
    try {
      A b = new A();
      throw b;
    } catch (A exc) {
      assert false;
    }
  }

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    try {
      A a = new A();
      foo();
    } catch (B exc) {
      assert false;
    }
  }
}
