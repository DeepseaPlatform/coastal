package svcomp;

interface A {

	public void f();
}

class B implements A {

	public void f() {
		assert false; // should fail
	}
}

class C implements A {

	public void f() {
	}
}

public class Main {

	public static void main(String[] args) {
		A b = new B();
		A c = new C();
		b.f(); // really calls B.f
	}
}
