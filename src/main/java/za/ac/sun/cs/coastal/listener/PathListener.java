package za.ac.sun.cs.coastal.listener;

import za.ac.sun.cs.coastal.run.SymbolicState;

public interface PathListener extends Listener {

	void visit(SymbolicState symbolicState);

}
