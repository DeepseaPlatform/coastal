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

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SegmentedPC;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.pathtree.PathTree;
import za.ac.sun.cs.coastal.strategy.StrategyFactory.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyFactory.StrategyManager;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.green.Green;
import za.ac.sun.cs.green.Instance;
import za.ac.sun.cs.green.expr.Constant;
import za.ac.sun.cs.green.expr.Expression;
import za.ac.sun.cs.green.expr.IntConstant;
import za.ac.sun.cs.green.expr.IntVariable;

public abstract class PathBasedStrategy extends Strategy {

	protected final PathBasedManager manager;
	
	protected final Broker broker;
	
	protected final Green green;

    protected final Set<String> visitedModels = new HashSet<>();

    public PathBasedStrategy(COASTAL coastal, StrategyManager manager) {
    	super(coastal, manager);
		this.manager = (PathBasedManager) manager;
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
	public Void call() throws Exception {
		log.trace("^^^ strategy task starting");
		try {
			while (true) {
				long t0 = System.currentTimeMillis();
				SegmentedPC spc = coastal.getNextPc();
				long t1 = System.currentTimeMillis();
				manager.recordWaitTime(t1 - t0);
				log.trace("+++ starting refinement");
				List<Model> mdls = refine(spc);
				int d = -1;
				if (mdls != null) {
					coastal.addDiverModels(mdls);
					d = mdls.size() - 1;
				}
				log.trace("+++ added {} models", d);
				coastal.updateWork(d);
			}
		} catch (InterruptedException e) {
			log.trace("^^^ strategy task canceled");
			throw e;
		}
	}

	@Override
	public List<Model> refine(Execution execution) {
		long t0 = System.currentTimeMillis();
		List<Model> newModels = refine0((SegmentedPC) execution);
		manager.recordRefineTime(System.currentTimeMillis() - t0);
		return newModels;
	}

	protected List<Model> refine0(SegmentedPC spc) {
		if ((spc == null) || (spc == SegmentedPC.NULL)) {
			return null;
		}
		log.trace("... explored <{}> {}", spc.getSignature(), spc.getPathCondition().toString());
		manager.insertPath(spc, false); // ignore revisited return value
		while (true) {
			spc = findNewPath(manager.getPathTree());
			if (spc == null) {
				log.trace("... no further paths");
				return null;
				// log.trace("...Tree shape: {}", pathTree.getShape());
			}
			Expression pc = spc.getPathCondition();
			String sig = spc.getSignature();
			log.trace("... trying   <{}> {}", sig, pc.toString());
			Map<String, Constant> model = findModel(pc);
			if (model == null) {
				log.trace("... no model");
				log.trace("(The spc is {})", spc.getPathCondition().toString());
				manager.insertPath(spc, true);
			} else {
				String modelString = model.toString();
				log.trace("... new model: {}", modelString);
				if (visitedModels.add(modelString)) {
					return Collections.singletonList(new Model(0, model));
				} else {
					manager.insertPath(spc, false);
					log.trace("... model {} has been visited before, retrying", modelString);
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
