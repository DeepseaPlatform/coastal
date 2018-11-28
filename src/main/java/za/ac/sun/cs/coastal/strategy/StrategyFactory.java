package za.ac.sun.cs.coastal.strategy;

import za.ac.sun.cs.coastal.COASTAL;

public interface StrategyFactory {

	StrategyManager createManager(COASTAL coastal);
	
	Strategy createStrategy(COASTAL coastal, StrategyManager manager);

}
