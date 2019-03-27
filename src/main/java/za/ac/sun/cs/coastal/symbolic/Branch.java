package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;

/**
 * An encapsulation of a potential branching point encountered during an
 * execution of the system-under-test.
 */
public abstract class Branch {

	/**
	 * Return the number of alternatives at this branch.
	 * 
	 * @return the number of alternatives
	 */
	public abstract long getNumberOfAlternatives();

	/**
	 * Return a string representation of a specific alternative at this branch.
	 * Typically, this would be "F" or "T" for a binary branch (if), or "1", "2",
	 * ... for a n-ary branch (switch).
	 * 
	 * @param alternative the number of the alternative
	 * @return a string representation of the alternative
	 */
	public abstract String getAlternativeRepr(long alternative);

	/**
	 * Return an expression that describes a specific alternative at this branch.
	 * 
	 * @param alternative number of the alternative
	 * @return expression for the alternative
	 */
	public abstract Expression getAlternative(long alternative);
	
//	/**
//	 * Return a {@link Choice} that represents an alternative taken at this branch.
//	 * 
//	 * @param alternative number of the alternative
//	 * @return choice that corresponds to the alternative
//	 */
//	public abstract Choice getChoice(long alternative);

	protected abstract Expression getPCContribution(long alternative);

//	/**
//	 * Compute (if necessary) and return the path condition that consists of
//	 * this {@code SegmentedPC} and the path condition of its parent.
//	 * 
//	 * @return the path condition for this instance
//	 */
//	public Expression getPathCondition() {
//		if (pathCondition == null) {
//			SegmentedPC p = getParent();
//			if (p != null) {
//				pathCondition = p.getPathCondition();
//			}
//			Expression apc = getActiveConjunct();
//			if ((apc != null)) { // && !isConstant(apc) && !p.contains(apc)) {
//				// Do we even need isConstant and p.contains()?
//				// Perhaps duplicate and constant conjuncts are no longer relevant?
//				pathCondition = (pathCondition == null) ? apc : Operation.and(apc, pathCondition);
//			}
//			Expression ppc = getPassiveConjunct();
//			if (ppc != null) {
//				pathCondition = (pathCondition == null) ? ppc : Operation.and(ppc, pathCondition);
//			}
//		}
//		return pathCondition;
//	}

}
