package za.ac.sun.cs.coastal.surfer;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.symbolic.Branch;

public abstract class Trace extends Branch {

	// ======================================================================
	//
	// BINARY CHOICES
	//
	// ======================================================================

	/**
	 * Representation of a binary branch.
	 */
	public static final class Binary extends Trace {

		/* (non-Javadoc)
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getNumberOfAlternatives()
		 */
		@Override
		public long getNumberOfAlternatives() {
			return 2;
		}

		/* (non-Javadoc)
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#getAlternativeRepr(long)
		 */
		@Override
		public String getAlternativeRepr(long alternative) {
			assert (alternative == 0) || (alternative == 1);
			return (alternative == 0) ? "F" : "T";
		}

		@Override
		public Expression getAlternative(long alternative) {
			return null;
		}

		@Override
		protected Expression getPCContribution(long alternative) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see za.ac.sun.cs.coastal.symbolic.Branch#toString0()
		 */
		@Override
		protected String toString0() {
			return "IF";
		}

	}

//	public static final Trace NULL = new TraceIf(null, "ORIGIN", true);
//	
//	protected final Trace parent;
//
//	protected final Trace root;
//	
//	protected final String block;
//	
//	protected final int depth;
//
//	protected Payload payload;
//
//	protected Input model = null;
//
//	protected Set<Integer> setValues = null;
//	
//	protected Set<Integer> incValues = null;
//	
//	public Trace(Trace parent, String block) {
//		this.parent = parent;
//		this.block = block;
//		this.root = (parent == null) ? this : parent.root;
//		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
//	}
//
//	@Override
//	public Trace getParent() {
//		return parent;
//	}
//
//	public String getBlock() {
//		return block;
//	}
//	
//	public Input getModel() {
//		return root.model;
//	}
//	
//	public void setModel(Input model) {
//		root.model = model;
//	}
//
//	public Set<Integer> getSetValues() {
//		return root.setValues;
//	}
//	
//	public void setSetValues(Set<Integer> setValues) {
//		root.setValues = new HashSet<>(setValues);
//	}
//	
//	public Set<Integer> getIncValues() {
//		return root.incValues;
//	}
//	
//	public void setIncValues(Set<Integer> incValues) {
//		root.incValues = new HashSet<>(incValues);
//	}
//	
//	private String stringRep = null;
//
//	@Override
//	public String toString() {
//		if (stringRep == null) {
//			stringRep = toString0();
//		}
//		return stringRep;
//	}
//
//	public abstract String toString0();
//
//	// ======================================================================
//	//
//	// BINARY CHOICES
//	//
//	// ======================================================================
//
//	public static class TraceIf extends Trace {
//
//		protected final boolean value;
//
//		protected final String signature;
//
//		protected Trace negated = null;
//
//		public TraceIf(Trace parent, String block, boolean value) {
//			super(parent, block);
//			this.value = value;
//			if (this.value) {
//				Trace p = getParent();
//				this.signature = (p == null) ? "1[" + block + "]" : (p.getSignature() + "1[" + block + "]");
//			} else {
//				Trace p = getParent();
//				this.signature = (p == null) ? "0[" + block + "]" : (p.getSignature() + "0[" + block + "]");
//			}
//		}
//
//		public boolean getValue() {
//			return value;
//		}
//
//		@Override
//		public String getSignature() {
//			return signature;
//		}
//
//		@Override
//		public int getNrOfOutcomes() {
//			return 2;
//		}
//
//		@Override
//		public int getOutcomeIndex() {
//			return value ? 1 : 0;
//		}
//
//		@Override
//		public String getOutcome(int index) {
//			if (index == 0) {
//				return "F";
//			} else {
//				return "T";
//			}
//		}
//
//		@Override
//		public Trace getChild(int index, Execution parent) {
//			if (getValue() == (index == 1)) {
//				return this;
//			} else {
//				return new TraceIf((Trace) parent, block, index == 1);
//			}
//		}
//
//		public Trace negate(String block) {
//			if (negated == null) {
//				negated = new TraceIf(getParent(), block, !getValue());
//			}
//			return negated;
//		}
//
//		@Override
//		public String toString0() {
//			if (this == Trace.NULL) {
//				return "Null-Trace";
//			} else {
//				return getSignature();
//			}
//		}
//
//	}

}
