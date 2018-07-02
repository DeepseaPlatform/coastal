package za.ac.sun.cs.coastal.strategy;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.symbolic.PathTree;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public class DepthFirstStrategy implements Strategy {

	private static final Logger lgr = Configuration.getLogger();

	private static final boolean dumpTrace = Configuration.getDumpTrace();

	private static final Logger greenLgr = LogManager.getLogger("GREEN");

	private static final Green green = new Green("COASTAL", greenLgr);

	private static final Set<String> visitedModels = new HashSet<>();

	private static int infeasibleCount = 0;

	private long pathLimit = 0;

	private long totalTime = 0, solverTime = 0;

	public DepthFirstStrategy() {
		Reporters.register(this);
		Properties greenProperties = new Properties();
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.canonizer",
				"za.ac.sun.cs.green.service.canonizer.ModelCanonizerService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3JavaService");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
		pathLimit = Configuration.getPathLimit();
		if (pathLimit == 0) {
			pathLimit = Long.MIN_VALUE;
		}
	}

	@Override
	public Map<String, Constant> refine() {
		long t0 = System.currentTimeMillis();
		SegmentedPC spc = SymbolicState.getSegmentedPathCondition();
		lgr.info("explored <" + spc.getSignature() + "> " + SegmentedPC.constraintBeautify(spc.getPathCondition().toString()));
		boolean infeasible = false;
		while (true) {
			if (--pathLimit < 0) {
				lgr.warn("path limit reached");
				return null;
			}
			spc = PathTree.insertPath(spc, infeasible);
			if (spc == null) {
				totalTime += System.currentTimeMillis() - t0;
				lgr.info("no further paths");
				return null;
			}
			infeasible = false;
			Expression pc = spc.getPathCondition();
			String sig = spc.getSignature();
			lgr.info("trying   <" + sig + "> " + SegmentedPC.constraintBeautify(pc.toString()));
			Instance instance = new Instance(green, null, pc);
			long t1 = System.currentTimeMillis();
			@SuppressWarnings("unchecked")
			Map<IntVariable, Object> model = (Map<IntVariable, Object>) instance.request("model");
			solverTime += System.currentTimeMillis() - t1;
			if (model == null) {
				lgr.info("no model");
				if (dumpTrace) {
					lgr.info("(The spc is {})", spc.getPathCondition().toString());
				}
				infeasible = true;
				infeasibleCount++;
			} else {
				Map<String, Constant> newModel = new HashMap<>();
				for (IntVariable variable : model.keySet()) {
					String name = variable.getName();
					Constant value = new IntConstant((Integer) model.get(variable));
					newModel.put(name, value);
				}
				String modelString = newModel.entrySet().stream().filter(p -> !p.getKey().startsWith("$"))
						.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())).toString();
				lgr.info("new model: {}", SegmentedPC.modelBeautify(modelString));
				if (visitedModels.add(modelString)) {
					totalTime += System.currentTimeMillis() - t0;
					return newModel;
				} else {
					lgr.info("model {} has been visited before, retrying", modelString);
					spc = PathTree.insertPath(spc, false);
				}
			}
		}
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public String getName() {
		return "DepthFirstStrategy";
	}

	@Override
	public void report(PrintWriter out) {
		out.println("  Inserted paths: " + PathTree.getPathCount());
		out.println("  Revisited paths: " + PathTree.getRevisitCount());
		out.println("  Infeasible paths: " + infeasibleCount);
		out.println("  Solver time: " + solverTime);
		out.println("  Overall strategy time: " + totalTime);
	}

}
