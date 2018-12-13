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

	// ======================================================================
	//
	// BINARY CHOICES
	//
	// ======================================================================

	public static class TraceIf extends Trace {

		private final boolean value;

		private final String signature;

		private Trace negated = null;

		public TraceIf(Trace parent, boolean value) {
			super(parent);
			this.value = value;
			if (this.value) {
				Trace p = getParent();
				this.signature = (p == null) ? "1" : (p.getSignature() + "1");
			} else {
				Trace p = getParent();
				this.signature = (p == null) ? "0" : (p.getSignature() + "0");
			}
		}

		public boolean getValue() {
			return value;
		}

		@Override
		public String getSignature() {
			return signature;
		}

		@Override
		public int getNrOfOutcomes() {
			return 2;
		}

		@Override
		public int getOutcomeIndex() {
			return value ? 1 : 0;
		}

		@Override
		public String getOutcome(int index) {
			if (index == 0) {
				return "F";
			} else {
				return "T";
			}
		}

		@Override
		public Trace getChild(int index, Execution parent) {
			if (getValue() == (index == 1)) {
				return this;
			} else {
				return new TraceIf((Trace) parent, index == 1);
			}
		}

		public Trace negate() {
			if (negated == null) {
				negated = new TraceIf(getParent(), !getValue());
			}
			return negated;
		}

		@Override
		public String toString0() {
			if (this == Trace.NULL) {
				return "Null-Trace";
			} else {
				return getSignature();
			}
		}

	}

}
