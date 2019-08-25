package za.ac.sun.cs.coastal.solver;

public class IntegerConstant extends Constant {

	public static final Constant ZERO32 = new IntegerConstant(0, 32);

	public static final Constant ZERO64 = new IntegerConstant(0, 64);
	
	public static final Constant ONE32 = new IntegerConstant(1, 32);
	
	public static final Constant ONE64 = new IntegerConstant(1, 64);
	
	public static final Constant MONE32 = new IntegerConstant(-1, 32);
	
	public static final Constant MONE64 = new IntegerConstant(-1, 64);
	
	protected final long value;

	protected final int size;
	
	public IntegerConstant(final long value, final int size) {
		this.value = value;
		this.size = size;
	}

	public long getValue() {
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
		return Long.toString(value);
	}

}
