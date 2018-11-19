package za.ac.sun.cs.coastal.strategy;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.listener.ConfigurationListener;
import za.ac.sun.cs.coastal.reporting.Recorder;
import za.ac.sun.cs.coastal.run.Model;
import za.ac.sun.cs.coastal.run.SegmentedPC;
import za.ac.sun.cs.coastal.run.SymbolicState;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public class DepthFirstStrategy implements Strategy, ConfigurationListener {

	private Logger log;

	private Green green;

	private final Set<String> visitedModels = new HashSet<>();

	private int infeasibleCount = 0;

	private DFPathTree pathTree;

	private long pathLimit = 0;

	private long totalTime = 0, solverTime = 0, pathTreeTime = 0, modelExtractionTime = 0;

	public DepthFirstStrategy() {
		// We expect configurationLoaded(...) to be called shortly.
		// This will initialize this instance.
	}

	@Override
	public void configurationLoaded(Configuration configuration) {
		log = configuration.getLog();
		configuration.getReporterManager().register(this);
		pathLimit = configuration.getLimitPaths();
		if (pathLimit == 0) {
			pathLimit = Long.MIN_VALUE;
		}
		pathTree = new DFPathTree(configuration);
		// Set up green
		green = new Green("COASTAL", LogManager.getLogger("GREEN"));
		Properties greenProperties = configuration.getOriginalProperties();
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
	}

	@Override
	public void collectProperties(Properties properties) {
		// do nothing
	}

	@Override
	public List<Model> refine(SegmentedPC spc) {
		long t0 = System.currentTimeMillis();
		List<Model> refinement = refine0(spc);
		totalTime += System.currentTimeMillis() - t0;
		return refinement;
	}

	private List<Model> refine0(SegmentedPC spc) {
		long t;
		if (spc == null) {
			return null;
		}
		log.info("explored <{}> {}", spc.getSignature(), spc.getPathCondition().toString());
		boolean infeasible = false;
		while (true) {
			if (--pathLimit < 0) {
				log.warn("path limit reached");
				return null;
			}
			t = System.currentTimeMillis();
			spc = pathTree.insertPath(spc, infeasible);
			pathTreeTime += System.currentTimeMillis() - t;
			if (spc == null) {
				log.info("no further paths");
				log.trace("Tree shape: {}", pathTree.getShape());
				return null;
			}
			infeasible = false;
			Expression pc = spc.getPathCondition();
			String sig = spc.getSignature();
			log.info("trying   <{}> {}", sig, pc.toString());
			Instance instance = new Instance(green, null, pc);
			t = System.currentTimeMillis();
			@SuppressWarnings("unchecked")
			Map<IntVariable, IntConstant> model = (Map<IntVariable, IntConstant>) instance.request("model");
			solverTime += System.currentTimeMillis() - t;
			if (model == null) {
				log.info("no model");
				log.trace("(The spc is {})", spc.getPathCondition().toString());
				infeasible = true;
				infeasibleCount++;
			} else {
				t = System.currentTimeMillis();
				Map<String, Constant> newModel = new HashMap<>();
				for (IntVariable variable : model.keySet()) {
					String name = variable.getName();
					if (name.startsWith(SymbolicState.NEW_VAR_PREFIX)) {
						continue;
					}
					Constant value = model.get(variable);
					newModel.put(name, value);
				}
				String modelString = newModel.toString();
				modelExtractionTime += System.currentTimeMillis() - t;
				log.info("new model: {}", modelString);
				if (visitedModels.add(modelString)) {
					List<Model> models = new LinkedList<>();
					models.add(new Model(0, newModel));
					return models;
				} else {
					log.info("model {} has been visited before, retrying", modelString);
				}
			}
		}
	}

	// ======================================================================
	//
	// DFPathTree
	//
	// ======================================================================

	private static class DFPathTree extends PathTree {

		DFPathTree(Configuration configuration) {
			super(configuration);
		}

		@Override
		public SegmentedPC findNewPath() {
			SegmentedPC pc = null;
			PathTreeNode cur = getRoot();
			outer: while (true) {
				int n = cur.getChildCount();
				for (int i = 0; i < n; i++) {
					PathTreeNode ch = cur.getChild(i);
					if (ch != null) {
						if (!ch.isComplete()) {
							pc = cur.getPcForChild(i, pc);
							cur = ch;
							continue outer;
						}
					}
				}
				for (int i = 0; i < n; i++) {
					PathTreeNode ch = cur.getChild(i);
					if (ch == null) {
						return cur.getPcForChild(i, pc);
					}
				}
				return null;
			}
		}

	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public int getOrder() {
		return 333;
	}
	
	@Override
	public String getName() {
		return "DepthFirstStrategy";
	}

	@Override
	public void record(Recorder recorder) {
		recorder.record(getName(), "inserted-paths", pathTree.getPathCount());
		recorder.record(getName(), "revisited-paths", pathTree.getRevisitCount());
		recorder.record(getName(), "infeasible-paths", infeasibleCount);
		recorder.record(getName(), "solver-time", solverTime);
		recorder.record(getName(), "pathtree-time", pathTreeTime);
		recorder.record(getName(), "extraction-time", modelExtractionTime);
		recorder.record(getName(), "overall-time", totalTime);
	}
	
	@Override
	public void report(PrintWriter info, PrintWriter trace) {
		info.println("  Inserted paths: " + pathTree.getPathCount());
		info.println("  Revisited paths: " + pathTree.getRevisitCount());
		info.println("  Infeasible paths: " + infeasibleCount);
		info.println("  Solver time: " + solverTime);
		info.println("  Path tree time: " + pathTreeTime);
		info.println("  Model extraction time: " + modelExtractionTime);
		info.println("  Overall strategy time: " + totalTime);
	}

}
