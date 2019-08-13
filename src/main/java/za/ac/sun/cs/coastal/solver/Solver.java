package za.ac.sun.cs.coastal.solver;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.Input;

public abstract class Solver {

	protected final COASTAL coastal;

	protected final Configuration configuration;

	protected static Solver solver = null;

	public Solver(COASTAL coastal, Configuration configuration) {
		this.coastal = coastal;
		this.configuration = configuration;
	}

	public abstract Input solve(Expression expression);

	public static Solver getSolver(COASTAL coastal) {
		if (solver == null) {
			String solverName = coastal.getConfig().getString("coastal.settings.solver");
			if (solverName != null) {
				Configuration solverConfiguration = coastal.getConfig().subset("coastal.settings.solver");
				Object s = Configuration.createInstance(coastal, solverConfiguration, solverName.trim());
				if ((s != null) && (s instanceof Solver)) {
					solver = (Solver) s;
				}
			}
		}
		return solver;
	}

	public static void report() {
		if (solver != null) {
			solver.issueReport();
		}
	}
	
	public void issueReport() { }

}
