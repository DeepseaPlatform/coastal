package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.Model;

public interface Strategy {

	List<Model> refine(SegmentedPC spc);

}
