package za.ac.sun.cs.coastal.listener;

import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public interface MarkerListener extends Listener {

	void mark(SymbolicState symbolicState, int marker);

	void mark(SymbolicState symbolicState, String marker);
	
}
