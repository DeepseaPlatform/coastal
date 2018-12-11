package za.ac.sun.cs.coastal.surfer;

import za.ac.sun.cs.coastal.symbolic.Execution;

public abstract class Trace implements Execution {

	public static final Trace NULL = new TraceIf(null, true);
	
	private final Trace parent;

	private final int depth;

	public Trace(Trace parent) {
		this.parent = parent;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	@Override
	public Trace getParent() {
		return parent;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	private String stringRep = null;

	@Override
	public String toString() {
		if (stringRep == null) {
			stringRep = toString0();
		}
		return stringRep;
	}

	public abstract String toString0();

}
