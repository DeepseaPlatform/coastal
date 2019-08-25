package simple;

import org.sosy_lab.sv_benchmarks.Verifier;

public final class TriggerGlobals {

	int field;
	static TriggerGlobals sfield;
	
	private TriggerGlobals(int p) {
		field = p;
	}

	public static void main(String[] args) {
		sfield = new TriggerGlobals(Verifier.nondetInt());
		//o.test();
		//test2(o);
		runit();
	}

	public static void runit() {
		sfield.test();
	}
	
	public void test() { // TRIGGER METHOD
		if (field < 10) {
			System.out.println("<10");
		} else {
			System.out.println(">=10");
		}
	}
	
	public static void test2(TriggerGlobals o) { // TRIGGER METHOD
		if (o.field < 10) {
			System.out.println("<10");
		} else {
			System.out.println(">=10");
		}
	}

}
