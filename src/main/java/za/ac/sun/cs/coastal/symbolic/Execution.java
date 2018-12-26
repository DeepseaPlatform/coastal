package za.ac.sun.cs.coastal.symbolic;

public interface Execution {

	int getDepth();
	
	Execution getParent();

	String getSignature();

	int getNrOfOutcomes();

	int getOutcomeIndex();

	String getOutcome(int index);

	Execution getChild(int index, Execution parent);
	
	Payload getPayload();

}
