package za.ac.sun.cs.coastal.surfer;

public abstract class Trace {

	public static final Trace NULL = new TraceIf(null, true);
	
	private final Trace parent;

	private final int depth;

	public Trace(Trace parent) {
		this.parent = parent;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	public Trace getParent() {
		return parent;
	}

	public int getDepth() {
		return depth;
	}

	public abstract String getSignature();

	public abstract int getNrOfOutcomes();

	public abstract int getOutcomeIndex();

	public abstract String getOutcome(int index);

	public abstract Trace getChild(int index, Trace parent);

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
