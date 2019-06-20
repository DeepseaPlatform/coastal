package tests.simple;

public final class TriggerGlobals0 {

	int field;
	
	private TriggerGlobals0(int p) {
		field = p;
	}

	public static void main(String[] args) {
		TriggerGlobals0 o = new TriggerGlobals0(5);
		o.test();
	}

	public void test() { // TRIGGER METHOD
		if (field < 10) {
			System.out.println("<10");
		} else {
			System.out.println(">=10");
		}
	}
	
}
