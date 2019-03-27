package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;

/**
 * An encapsulation of a branch taken during an execution of the
 * system-under-test.
 */
public final class Choice {

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
		assert (alternative >= 0) && (alternative < branch.getNumberOfAlternatives());
		this.alternative = alternative;
	}

	/**
	 * The branch associated with this choice.
	 * 
	 * @return the branch associated with this choice
	 */
	public final Branch getBranch() {
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
	 * The active conjunct associated with this choice.
	 * 
	 * @return the active conjunct associated with this choice
	 */
	public final Expression getActiveConjunct() {
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
	 * Return the contribution of this choice to the signature of a path of which it forms
	 * part.
	 * 
	 * @return the choice's contribution to the signature
	 */
	public String getSignatureContribution() {
		return getBranch().getAlternativeRepr(alternative);
	}
	
}
