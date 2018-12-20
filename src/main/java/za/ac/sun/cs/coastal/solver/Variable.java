package za.ac.sun.cs.coastal.solver;

public abstract class Variable extends Expression {

	private final String name;

	private final Object original;

	public Variable(final String name) {
		this.name = name;
		this.original = null;
	}
	
	public Variable(final String name, final Object original) {
		this.name = name;
		this.original = original;
	}

	public final String getName() {
		return name;
	}

	public final Object getOriginal() {
		return original;
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	@Override
	public final String toString0() {
		return getName();
	}
	
}
