package za.ac.sun.cs.coastal.solver;

public class Visitor {

	private void preVisit(Expression expression) throws VisitorException {
	}
	
	private void postVisit(Expression expression) throws VisitorException {
	}
	
	// ======================================================================
	//
	// OPERATIONS
	//
	// ======================================================================

	public void preVisit(Operation operation) throws VisitorException {
		preVisit((Expression) operation);
	}

	public void postVisit(Operation operation) throws VisitorException {
		postVisit((Expression) operation);
	}
	
	// ======================================================================
	//
	// VARIABLES
	//
	// ======================================================================
	
	public void preVisit(RealVariable realVariable) throws VisitorException {
		preVisit((Variable) realVariable);
	}
	
	public void postVisit(RealVariable realVariable) throws VisitorException {
		postVisit((Variable) realVariable);
	}
	
	public void preVisit(IntegerVariable integerVariable) throws VisitorException {
		preVisit((Variable) integerVariable);
	}
	
	public void postVisit(IntegerVariable integerVariable) throws VisitorException {
		postVisit((Variable) integerVariable);
	}

	public void preVisit(Variable variable) throws VisitorException {
		preVisit((Expression) variable);
	}

	public void postVisit(Variable variable) throws VisitorException {
		postVisit((Expression) variable);
	}
	
	// ======================================================================
	//
	// CONSTANTS
	//
	// ======================================================================
	
	public void preVisit(RealConstant realConstant) throws VisitorException {
		preVisit((Constant) realConstant);
	}

	public void postVisit(RealConstant realConstant) throws VisitorException {
		postVisit((Constant) realConstant);
	}
	
	public void preVisit(IntegerConstant integerConstant) throws VisitorException {
		preVisit((Constant) integerConstant);
	}
	
	public void postVisit(IntegerConstant integerConstant) throws VisitorException {
		postVisit((Constant) integerConstant);
	}
	
	private void preVisit(Constant constant) throws VisitorException {
		preVisit((Expression) constant);
	}
	
	private void postVisit(Constant constant) throws VisitorException {
		postVisit((Expression) constant);
	}
	
}
