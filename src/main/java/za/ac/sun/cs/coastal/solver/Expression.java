package za.ac.sun.cs.coastal.solver;

public abstract class Expression implements Comparable<Expression> {

	protected String stringRepr = null;

	public abstract void accept(Visitor visitor) throws VisitorException;
	
	@Override
	public final int compareTo(Expression expression) {
		return toString().compareTo(expression.toString());
	}

	@Override
	public final boolean equals(Object object) {
		return (object != null) && (object.getClass() == this.getClass()) && toString().equals(object.toString());
	}

	@Override
	public final int hashCode() {
		return toString().hashCode();
	}

	@Override
	public final String toString() {
		if (stringRepr == null) {
			stringRepr = toString0();
		}
		return stringRepr;
	}

	protected abstract String toString0();

}
