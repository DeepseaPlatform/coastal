package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;

/**
 * An encapsulation of a branch taken during an execution of the
 * system-under-test.
 */
public final class Choice extends PayloadCarrierImpl {

	/**
	 * The branching point associated with this choice.
	 */
	private final Branch branch;

	/**
	 * The index (0, 1, 2, ...) taken at the branching point.
	 */
	private final long alternative;

	/**
	 * Construct a choice.
	 * 
	 * @param branch      the branching point associated with this choice
	 * @param alternative the alternative taken at the branching point
	 */
	public Choice(Branch branch, long alternative) {
		this.branch = branch;
		assert (branch != null);
		assert (alternative >= 0) && (alternative < branch.getNumberOfAlternatives()) : ("alternative:" + alternative
				+ " branch.getNumberOfAlternatives():" + branch.getNumberOfAlternatives());
		this.alternative = alternative;
	}

	/**
	 * The branch associated with this choice.
	 * 
	 * @return the branch associated with this choice
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * Return the index of the alternative that was taken at this branch.
	 * 
	 * @return the index of alternative taken
	 */
	public long getAlternative() {
		return alternative;
	}

	/**
	 * Return an alternative choice.
	 * 
	 * @param alternative index of the alternative
	 * @return alternative choice
	 */
	public Choice getAlternative(long alternative) {
		return new Choice(branch, alternative);
	}

	/**
	 * The active conjunct associated with this choice.
	 * 
	 * @return the active conjunct associated with this choice
	 */
	public Expression getActiveConjunct() {
		return getBranch().getAlternative(alternative);
	}

	/**
	 * Return the contribution of this choice to a path condition of which it forms
	 * part.
	 * 
	 * @return the choice's contribution to a path condition
	 */
	public Expression getPCContribution() {
		return getBranch().getPCContribution(alternative);
	}

	/**
	 * Return the contribution of this choice to the signature of a path of which it
	 * forms part.
	 * 
	 * @return the choice's contribution to the signature
	 */
	public String getSignatureContribution() {
		return getBranch().getAlternativeRepr(alternative);
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	/**
	 * String representation of this choice.
	 */
	private String stringRep = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (stringRep == null) {
			stringRep = toString0();
		}
		return stringRep;
	}

	/**
	 * Return a string representation of this choice.
	 * 
	 * @return string representation of this choice
	 */
	private String toString0() {
		StringBuilder rep = new StringBuilder();
		rep.append(getBranch()).append('#').append(getAlternative());
		return rep.toString();
	}

}
