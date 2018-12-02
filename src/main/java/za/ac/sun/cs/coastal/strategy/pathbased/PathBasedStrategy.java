package za.ac.sun.cs.coastal.strategy.pathbased;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyManager;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public abstract class PathBasedStrategy implements Strategy {

	protected final COASTAL coastal;
	
	protected final Logger log;

	protected final PathBasedManager manager;
	
	protected final Broker broker;
	
	protected final Green green;

    protected final Set<String> visitedModels = new HashSet<>();

    public PathBasedStrategy(COASTAL coastal, StrategyManager manager) {
		this.coastal = coastal;
		this.manager = (PathBasedManager) manager;
		log = coastal.getLog();
		broker = coastal.getBroker(); 
		green = new Green("COASTAL", LogManager.getLogger("GREEN"));
		Properties greenProperties = new Properties();
		ImmutableConfiguration config = coastal.getConfig();
		config.getKeys("green.").forEachRemaining(k -> greenProperties.setProperty(k, config.getString(k)));
		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(bounder modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
		new za.ac.sun.cs.green.util.Configuration(green, greenProperties).configure();
	}

	@Override
	public List<Model> refine(SegmentedPC spc) {
		long t0 = System.currentTimeMillis();
		List<Model> newModels = refine0(spc);
		manager.recordRefineTime(System.currentTimeMillis() - t0);
		return newModels;
	}

	protected List<Model> refine0(SegmentedPC spc) {
		if ((spc == null) || (spc == SegmentedPC.NULL)) {
			return null;
		}
		log.info("explored <{}> {}", spc.getSignature(), spc.getPathCondition().toString());
		manager.insertPath(spc, false); // ignore revisited return value
		while (true) {
			spc = findNewPath(manager.getPathTree());
			if (spc == null) {
				log.info("no further paths");
				return null;
				// log.trace("Tree shape: {}", pathTree.getShape());
			}
			Expression pc = spc.getPathCondition();
			String sig = spc.getSignature();
			log.info("trying   <{}> {}", sig, pc.toString());
			Map<String, Constant> model = findModel(pc);
			if (model == null) {
				log.info("no model");
				log.trace("(The spc is {})", spc.getPathCondition().toString());
				manager.insertPath(spc, true);
				manager.incrementInfeasibleCount();
			} else {
				String modelString = model.toString();
				log.info("new model: {}", modelString);
				if (visitedModels.add(modelString)) {
					return Collections.singletonList(new Model(0, model));
				} else {
					log.info("model {} has been visited before, retrying", modelString);
				}
			}
		}
	}

	protected abstract SegmentedPC findNewPath(PathTree pathTree);

	protected Map<String, Constant> findModel(Expression pc) {
		Instance instance = new Instance(green, null, pc);
		long t = System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		Map<IntVariable, IntConstant> model = (Map<IntVariable, IntConstant>) instance.request("model");
		manager.recordSolverTime(System.currentTimeMillis() - t);
		if (model == null) {
			return null;
		}
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
		manager.recordExtractionTime(System.currentTimeMillis() - t);
		return newModel;
	}


}
