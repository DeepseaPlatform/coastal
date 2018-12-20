package za.ac.sun.cs.coastal.solver;

public class RealVariable extends Variable {

	/**
	 * The number of bits in this variable: either 32 or 64.
	 */
	protected final int size;
	
	protected final double lowerBound;

	protected final double upperBound;
	
	public RealVariable(final String name, final int size, final double lowerBound, final double upperBound) {
		super(name);
		assert (size == 32) || (size == 64);
		this.size = size;
		assert (lowerBound <= upperBound);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getSize() {
		return size;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
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

}
