package za.ac.sun.cs.coastal.surfer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Payload;

public abstract class Trace implements Execution {

	public static final Trace NULL = new TraceIf(null, "ORIGIN", true);
	
	protected final Trace parent;

	protected final Trace root;
	
	protected final String block;
	
	protected final int depth;

	protected Payload payload;

	protected Map<String, Object> model = null;

	protected Set<Integer> setValues = null;
	
	protected Set<Integer> incValues = null;
	
	public Trace(Trace parent, String block) {
		this.parent = parent;
		this.block = block;
		this.root = (parent == null) ? this : parent.root;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
	}

	@Override
	public Trace getParent() {
		return parent;
	}

	public String getBlock() {
		return block;
	}
	
	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public Payload getPayload() {
		return root.payload;
	}

	public void setPayload(Payload payload) {
		root.payload = payload;
	}
	
	public Map<String, Object> getModel() {
		return root.model;
	}
	
	public void setModel(Map<String, Object> model) {
		root.model = model;
	}

	public Set<Integer> getSetValues() {
		return root.setValues;
	}
	
	public void setSetValues(Set<Integer> setValues) {
		root.setValues = new HashSet<>(setValues);
	}
	
	public Set<Integer> getIncValues() {
		return root.incValues;
	}
	
	public void setIncValues(Set<Integer> incValues) {
		root.incValues = new HashSet<>(incValues);
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

		protected final boolean value;

		protected final String signature;

		protected Trace negated = null;

		public TraceIf(Trace parent, String block, boolean value) {
			super(parent, block);
			this.value = value;
			if (this.value) {
				Trace p = getParent();
				this.signature = (p == null) ? "1[" + block + "]" : (p.getSignature() + "1[" + block + "]");
			} else {
				Trace p = getParent();
				this.signature = (p == null) ? "0[" + block + "]" : (p.getSignature() + "0[" + block + "]");
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
				return new TraceIf((Trace) parent, block, index == 1);
			}
		}

		public Trace negate(String block) {
			if (negated == null) {
				negated = new TraceIf(getParent(), block, !getValue());
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
