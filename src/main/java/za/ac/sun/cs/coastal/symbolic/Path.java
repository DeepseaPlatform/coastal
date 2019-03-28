package za.ac.sun.cs.coastal.symbolic;

import za.ac.sun.cs.coastal.solver.Expression;
import za.ac.sun.cs.coastal.solver.Operation;
import za.ac.sun.cs.coastal.solver.Operation.Operator;

/**
 * A path is a description of an execution that contains the branches taken
 * during the execution.
 */
public final class Path {

	/**
	 * The prefix of this path, or {@code null} is this is the root of other paths.
	 * Many paths may have this path as a prefix.
	 */
	protected final Path parent;

	/**
	 * The depth of this path. In essence, this is a count of the number of branches
	 * along the path.
	 */
	protected final int depth;

	/**
	 * The last choice made on this path.
	 */
	protected final Choice choice;

	/**
	 * Construct a new path.
	 * 
	 * @param parent the prefix of the path
	 * @param choice the last choice made on the path
	 */
	public Path(Path parent, Choice choice) {
		this.parent = parent;
		this.depth = (parent == null) ? 1 : (1 + parent.getDepth());
		this.choice = choice;
	}

	/**
	 * Return the prefix of this path.
	 * 
	 * @return the prefix of this path
	 */
	public Path getParent() {
		return parent;
	}

	/**
	 * Return the depth (or length) of this path.
	 * 
	 * @return the depth of this path
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Return the last choice of this path.
	 * 
	 * @return the last choice of this path
	 */
	public Choice getChoice() {
		return choice;
	}

	// ======================================================================
	//
	// PATH CONDITIONS
	//
	// ======================================================================

	/**
	 * The computed path condition that is the combination of the last choice's contribution and the path condition of the parent. This
	 * is only computed when needed.
	 */
	protected Expression pathCondition = null;
	
	/**
	 * Return the path condition that corresponds to this path.
	 * 
	 * @return the path's path condition
	 */
	public Expression getPathCondition() {
		if (pathCondition == null) {
			Path parent = getParent();
			if (parent != null) {
				pathCondition = parent.getPathCondition();
			}
			Expression c = getChoice().getPCContribution();
			if (c != null) {
				if (pathCondition == null) {
					pathCondition = c;
				} else {
					while ((c != null) && (c instanceof Operation) && (((Operation) c).getOperator() == Operator.AND)) {
						pathCondition = Operation.and(((Operation) c).getOperand(0), pathCondition);
						c = ((Operation) c).getOperand(1);
					}
					if (c != null) {
						pathCondition = Operation.and(c, pathCondition);
					}
				}
			}
		}
		return pathCondition;
	}
	
	// ======================================================================
	//
	// SIGNATURES
	//
	// ======================================================================
	
	/**
	 * The computed signature of the path.  A signature is a short description of the "turns and twists" that the path follows.  It
	 * is only computed when needed.
	 */
	protected String signature = null;

	/**
	 * Return the signature that corresponds to this path.
	 * 
	 * @return the path's signature
	 */
	public String getSignature() {
		if (signature == null) {
			Path parent = getParent();
			if (parent != null) {
				signature = parent.getSignature();
			}
			String c = getChoice().getSignatureContribution();
			if (c != null) {
				if (signature == null) {
					signature = c;
				} else {
					signature = c + signature;
				}
			}
		}
		return signature;
	}

	// ======================================================================
	//
	// STRING REPRESENTATION
	//
	// ======================================================================

	/**
	 * String representation of this path.
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
	 * Return a string representation of this path.
	 * 
	 * @return a string representation of this path
	 */
	private String toString0() {
		StringBuilder rep = new StringBuilder();
		rep.append('[').append(getChoice());
		if (getParent() != null) {
			rep.append(',').append(getParent().toString());
		}
		rep.append(']');
		return rep.toString();
	}

}
