package za.ac.sun.cs.coastal.symbolic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Conversion;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.green.expr.Constant;

public class Diver {

	private final COASTAL coastal;
	
	private final Logger log;
	
	private final ImmutableConfiguration config;

	private final Broker broker;

	private int runs = 0;

	private long time = 0;

	public Diver(COASTAL coastal) {
		this.coastal = coastal;
		log = coastal.getLog();
		config = coastal.getConfig();
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
	}

	public void dive() {
//		List<InstructionListener> instructionListeners = new ArrayList<>();
//		for (InstructionListener listener : config.<InstructionListener>getListeners(InstructionListener.class)) {
//			listener.changeInstrumentationManager(classManager);
//			instructionListeners.add(listener);
//		}
//		List<MarkerListener> markerListeners = new ArrayList<>();
//		for (MarkerListener listener : config.<MarkerListener>getListeners(MarkerListener.class)) {
//			markerListeners.add(listener);
//		}
//		List<PathListener> pathListeners = new ArrayList<>();
//		for (PathListener listener : config.<PathListener>getListeners(PathListener.class)) {
//			pathListeners.add(listener);
//		}
		String strategyName = config.getString("coastal.strategy");
		Object strategyObject = null;
		Strategy strategy = null;
		if (strategyName != null) {
			strategyObject = Conversion.createInstance(coastal, strategyName);
		}
		if ((strategyObject != null) && (strategyObject instanceof Strategy)) {
			strategy = (Strategy) strategyObject;
		} else {
			log.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}
		Map<String, Constant> concreteValues = null;
		long runLimit = config.getLong("coastal.limit.runs", Long.MAX_VALUE);
		long timeLimit = config.getLong("coastal.limit.time", Long.MAX_VALUE);
		long tl0 = System.currentTimeMillis();
		do {
			if ((System.currentTimeMillis() - tl0) / 1000 > timeLimit) {
				log.warn("time limit reached");
				return;
			}
			if (--runLimit < 0) {
				log.warn("run limit reached");
				return;
			}
			runs++;
			log.info(Banner.getBannerLine("starting dive " + runs, '-'));
			long t0 = System.currentTimeMillis();
			SymbolicState symbolicState = new SymbolicState(coastal, concreteValues);
			SymbolicVM.setState(symbolicState);
			performRun();
			time += System.currentTimeMillis() - t0;
//			for (PathListener listener : pathListeners) {
//				listener.visit(symbolicState);
//			}
			concreteValues = strategy.refine(symbolicState);
			if (!symbolicState.mayContinue()) {
				break;
			}
		} while (concreteValues != null);
	}

	public int getRuns() {
		return runs;
	}

	private static final PrintStream NUL = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// do nothing
		}
	});

	private void performRun() {
		ClassLoader classLoader = coastal.getClassManager().createClassLoader();
		PrintStream out = System.out, err = System.err;
		try {
			Class<?> clas = classLoader.loadClass(config.getString("coastal.main"));
			Method meth = clas.getMethod("main", String[].class);
			// Redirect System.out/System.err
			if (!config.getBoolean("coastal.echo-output", false)) {
				System.setOut(NUL);
				System.setErr(NUL);
			}
			meth.invoke(null, new Object[] { new String[0] });
			System.setOut(out);
			System.setErr(err);
		} catch (ClassNotFoundException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (SecurityException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (IllegalArgumentException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			System.setOut(out);
			System.setErr(err);
			Throwable t = x.getCause();
			if ((t == null) || !(t instanceof LimitConjunctException)) {
				// x.printStackTrace();
				try {
					SymbolicVM.startCatch(-1);
				} catch (LimitConjunctException e) {
					// ignore, since run is over in any case
				}
			}
		}
	}

	public void report(Object object) {
		broker.publish("report", new Tuple("Diver.runs", runs));
		broker.publish("report", new Tuple("Diver.time", time));
	}

}
