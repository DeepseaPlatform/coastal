package za.ac.sun.cs.coastal.strategy.tracebased;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.strategy.StrategyFactory.Strategy;
import za.ac.sun.cs.coastal.strategy.StrategyFactory.StrategyManager;
import za.ac.sun.cs.coastal.surfer.Trace;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Model;

public abstract class TraceBasedStrategy extends Strategy {

	protected final TraceBasedManager manager;
	
	protected final Broker broker;
	
    protected final Set<String> visitedModels = new HashSet<>();

    public TraceBasedStrategy(COASTAL coastal, StrategyManager manager) {
    	super(coastal, manager);
		this.manager = (TraceBasedManager) manager;
		broker = coastal.getBroker(); 
	}

	@Override
	public List<Model> refine(Execution execution) {
		long t0 = System.currentTimeMillis();
		List<Model> newModels = refine0((Trace) execution);
		manager.recordRefineTime(System.currentTimeMillis() - t0);
		return newModels;
	}

	protected List<Model> refine0(Trace trace) {
		if ((trace == null) || (trace == Trace.NULL)) {
			return null;
		}
		return null;
//		log.trace("... explored <{}>", trace.getSignature());
//		manager.insertPath(trace, false); // ignore revisited return value
//		while (true) {
//			trace = findNewPath(manager.getPathTree());
//			if (trace == null) {
//				log.trace("... no further paths");
//				return null;
//				// log.trace("...Tree shape: {}", pathTree.getShape());
//			}
//			Expression pc = trace.getPathCondition();
//			String sig = trace.getSignature();
//			log.trace("... trying   <{}> {}", sig, pc.toString());
//			Map<String, Constant> model = findModel(pc);
//			if (model == null) {
//				log.trace("... no model");
//				log.trace("(The spc is {})", trace.getPathCondition().toString());
//				manager.insertPath(trace, true);
//			} else {
//				String modelString = model.toString();
//				log.trace("... new model: {}", modelString);
//				if (visitedModels.add(modelString)) {
//					return Collections.singletonList(new Model(0, model));
//				} else {
//					manager.insertPath(trace, false);
//					log.trace("... model {} has been visited before, retrying", modelString);
//				}
//			}
//		}
	}

}
