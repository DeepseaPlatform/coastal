package za.ac.sun.cs.coastal.solver;

import org.apache.commons.configuration2.ImmutableConfiguration;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.ConfigHelper;
import za.ac.sun.cs.coastal.symbolic.Input;

public abstract class Solver {

	protected final COASTAL coastal;
	
	protected final ImmutableConfiguration config;

	public Solver(COASTAL coastal, ImmutableConfiguration config) {
		this.coastal = coastal;
		this.config = config;
	}

	public abstract Input solve(Expression expression);

	public static Solver getSolver(COASTAL coastal) {
		for (int i = 0; true; i++) {
			String key = "coastal.settings.solver(" + i + ")";
			String solverName = coastal.getConfig().getString(key);
			if (solverName == null) {
				return null;
			}
			Object solver = ConfigHelper.createInstance(coastal, coastal.getConfig().immutableSubset(key + ".options"),
					solverName.trim());
			if ((solver != null) && (solver instanceof Solver)) {
				return (Solver) solver;
			}
		}
	}

}
