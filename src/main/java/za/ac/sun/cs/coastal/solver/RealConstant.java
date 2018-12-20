package za.ac.sun.cs.coastal.solver;

public class RealConstant extends Constant {

	public static final Constant ZERO32 = new RealConstant(0, 32);

	public static final Constant ZERO64 = new RealConstant(0, 64);
	
	private final double value;

	protected final int size;
	
	public RealConstant(final double value, final int size) {
		this.value = value;
		this.size = size;
	}

	public double getValue() {
		return value;
	}

	public int getSize() {
		return size;
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

	@Override
	public String toString0() {
		return Double.toString(value);
	}

}
