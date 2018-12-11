package za.ac.sun.cs.coastal.symbolic;

public interface Execution {

	Execution getParent();

	int getDepth();

	String getSignature();

	int getNrOfOutcomes();

	int getOutcomeIndex();

	String getOutcome(int index);

	Execution getChild(int index, Execution parent);

}
