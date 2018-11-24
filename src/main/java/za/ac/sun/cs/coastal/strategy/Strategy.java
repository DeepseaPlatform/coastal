package za.ac.sun.cs.coastal.strategy;

import java.util.List;

import za.ac.sun.cs.coastal.run.Model;
import za.ac.sun.cs.coastal.run.SegmentedPC;

/**
 * A strategy transforms an explored path condition into a list of models. There
 * are some expectations that may or may not be met:
 * 
 * <ul>
 * 
 * <li>Almost all strategies maintain a tree of path conditions.</li>
 * 
 * <li>A particular strategy might insert the path condition into the path tree
 * and then consider the tree as a whole to find new models.</li>
 * 
 * <li>In many cases, a strategy will find a new path to explore and generate
 * one or more models that it believes will explore the path.</li>
 * 
 * </ul>
 */
public interface Strategy {

	/**
	 * Transform a path condition into a list of models
	 * 
	 * @param spc
	 *            the most recently explored path condition
	 * @return a list of (prioritized) models
	 */
	List<Model> refine(SegmentedPC spc);

}
