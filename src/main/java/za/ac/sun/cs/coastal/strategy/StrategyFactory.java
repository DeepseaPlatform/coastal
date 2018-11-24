package za.ac.sun.cs.coastal.strategy;

import za.ac.sun.cs.coastal.Disposition;

public interface StrategyFactory {

	StrategyManager createManager(Disposition disposition);

	Strategy createStrategy(Disposition disposition, StrategyManager manager);
	
}
