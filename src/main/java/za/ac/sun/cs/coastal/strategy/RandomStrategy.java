package za.ac.sun.cs.coastal.strategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public class RandomStrategy implements Strategy {

	private final Logger log;

	private final Broker broker;

	private final Green green;

	private final Set<String> visitedModels = new HashSet<>();

	private int infeasibleCount = 0;

	private final RandomPathTree pathTree;

	private final long randomSeed;

	private final long pathLimit;

	private long totalTime = 0, solverTime = 0, pathTreeTime = 0, modelExtractionTime = 0;

	public RandomStrategy(COASTAL coastal) {
		log = coastal.getLog();
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		ImmutableConfiguration config = coastal.getConfig();
		long p = config.getLong("coastal.limits.paths", 0);
		pathLimit = (p == 0) ? Long.MAX_VALUE : p;
		pathTree = new RandomPathTree(coastal);
		randomSeed = config.getLong("coastal.random-seed", 987654321);
		pathTree.setSeed(randomSeed);
		// Set up green
		green = new Green("COASTAL", LogManager.getLogger("GREEN"));
		Properties greenProperties = new Properties();
		config.getKeys("green.").forEachRemaining(k -> greenProperties.setProperty(k, config.getString(k)));
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
	}

	@Override
	public Map<String, Constant> refine(SymbolicState symbolicState) {
		long t0 = System.currentTimeMillis();
		Map<String, Constant> refinement = refine0(symbolicState);
		totalTime += System.currentTimeMillis() - t0;
		return refinement;
	}

	private Map<String, Constant> refine0(SymbolicState symbolicState) {
		long t;
		SegmentedPC spc = symbolicState.getSegmentedPathCondition();
		log.info("explored <{}> {}", spc.getSignature(), spc.getPathCondition().toString());
		boolean infeasible = false;
		long pathCount = 0;
		while (true) {
			if (++pathCount > pathLimit) {
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
					return newModel;
				} else {
					log.info("model {} has been visited before, retrying", modelString);
				}
			}
		}
	}

	// ======================================================================
	//
	// RandomPathTree
	//
	// ======================================================================

	private static class RandomPathTree extends PathTree {

		private final Random rng = new Random();

		RandomPathTree(COASTAL coastal) {
			super(coastal);
		}

		public void setSeed(long seed) {
			rng.setSeed(seed);
		}

		@Override
		public SegmentedPC findNewPath() {
			SegmentedPC pc = null;
			PathTreeNode cur = getRoot();
			outer: while (true) {
				int n = cur.getChildCount();
				int i = rng.nextInt(n);
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode ch = cur.getChild(i);
					if ((ch != null) && !ch.isComplete()) {
						pc = cur.getPcForChild(i, pc);
						cur = ch;
						continue outer;
					}
				}
				for (int j = 0; j < n; j++, i = (i + 1) % n) {
					PathTreeNode ch = cur.getChild(i);
					if (ch == null) {
						return cur.getPcForChild(i, pc);
					}
				}
				return null;
			}
		}

	}

	public void report(Object object) {
		broker.publish("report", new Tuple("Strategy.path-count", pathTree.getPathCount()));
		broker.publish("report", new Tuple("Strategy.revisit-count", pathTree.getRevisitCount()));
		broker.publish("report", new Tuple("Strategy.infeasible-count", infeasibleCount));
		broker.publish("report", new Tuple("Strategy.solver-time", solverTime));
		broker.publish("report", new Tuple("Strategy.pathtree-time", pathTreeTime));
		broker.publish("report", new Tuple("Strategy.model-extraction-time", modelExtractionTime));
		broker.publish("report", new Tuple("Strategy.time", totalTime));
	}

}
