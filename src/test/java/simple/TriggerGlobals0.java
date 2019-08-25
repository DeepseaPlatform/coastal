package simple;

public final class TriggerGlobals0 {

	int field;
	
	private TriggerGlobals0(int p) {
		field = p;
	}

	public static void main(String[] args) {
		TriggerGlobals0 o = new TriggerGlobals0(5);
		o.test(10);
	}

	public void test(int limit) { // TRIGGER METHOD
		if (field < limit) {
			System.out.println("<limit");
		} else {
			System.out.println(">=limit");
		}
	}
	
}
