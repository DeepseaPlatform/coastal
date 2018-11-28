package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;

public interface Strategy {

	List<Model> refine(SegmentedPC spc);

}
