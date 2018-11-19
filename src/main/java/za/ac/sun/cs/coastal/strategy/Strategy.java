package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.run.Model;
import za.ac.sun.cs.coastal.run.SegmentedPC;

public interface Strategy extends Reporter {

	List<Model> refine(SegmentedPC spc);

}
