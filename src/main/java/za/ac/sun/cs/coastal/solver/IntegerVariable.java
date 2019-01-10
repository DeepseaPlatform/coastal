package za.ac.sun.cs.coastal.solver;

public class IntegerVariable extends Variable {

	/**
	 * The number of bits in this variable: either 32 or 64.
	 */
	protected final int size;
	
	protected final long lowerBound;

	protected final long upperBound;
	
	public IntegerVariable(final String name, final int size, final long lowerBound, final long upperBound) {
		super(name);
		assert (size == 32) || (size == 64) || (size == 8) || (size == 16);
		this.size = size;
		assert (lowerBound <= upperBound);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getSize() {
		return size;
	}
	
	public long getLowerBound() {
		return lowerBound;
	}

	public long getUpperBound() {
		return upperBound;
	}
	
	// ======================================================================
	//
	// VISITOR
	//
	// ======================================================================
	
	@Override
	public void accept(Visitor visitor) throws VisitorException {
		visitor.preVisit(this);
		visitor.postVisit(this);
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

}
