package za.ac.sun.cs.coastal.symbolic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.listener.InstructionListener;
import za.ac.sun.cs.coastal.listener.MarkerListener;
import za.ac.sun.cs.coastal.listener.PathListener;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Pair;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.green.expr.Constant;

public class Diver {

	private final Logger log;
	
	private final Configuration configuration;

	private final Broker broker;

	private int runs = 0;

	private long time = 0;

	private final InstrumentationClassManager classManager;

	public Diver(COASTAL coastal) {
		this.log = coastal.getLog();
		this.configuration = coastal.getConfiguration();
		this.broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		// configuration.getReporterManager().register(this);
		String cp = System.getProperty("java.class.path");
		classManager = new InstrumentationClassManager(configuration, cp);
	}

	public void dive() {
		List<InstructionListener> instructionListeners = new ArrayList<>();
		for (InstructionListener listener : configuration.<InstructionListener>getListeners(InstructionListener.class)) {
			listener.changeInstrumentationManager(classManager);
			instructionListeners.add(listener);
		}
		List<MarkerListener> markerListeners = new ArrayList<>();
		for (MarkerListener listener : configuration.<MarkerListener>getListeners(MarkerListener.class)) {
			markerListeners.add(listener);
		}
		List<PathListener> pathListeners = new ArrayList<>();
		for (PathListener listener : configuration.<PathListener>getListeners(PathListener.class)) {
			pathListeners.add(listener);
		}
		Strategy strategy = configuration.getStrategy();
		if (strategy == null) {
			log.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}
		Map<String, Constant> concreteValues = null;
		long runLimit = configuration.getLimitRuns();
		if (runLimit == 0) {
			runLimit = Long.MIN_VALUE;
		}
		long timeLimit = configuration.getLimitTime();
		if (timeLimit == 0) {
			timeLimit = Long.MAX_VALUE;
		}
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
			SymbolicState symbolicState = new SymbolicState(configuration, concreteValues, instructionListeners,
					markerListeners);
			SymbolicVM.setState(symbolicState);
			performRun();
			time += System.currentTimeMillis() - t0;
			for (PathListener listener : pathListeners) {
				listener.visit(symbolicState);
			}
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
		ClassLoader classLoader = classManager.createClassLoader();
		PrintStream out = System.out, err = System.err;
		try {
			Class<?> clas = classLoader.loadClass(configuration.getMain());
			Method meth = clas.getMethod("main", String[].class);
			// Redirect System.out/System.err
			if (!configuration.getEchoOutput()) {
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
		broker.publish("report", new Pair("Diver.runs", runs));
		broker.publish("report", new Pair("Diver.time", time));
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

//	@Override
//	public String getName() {
//		return "Dive";
//	}
//
//	@Override
//	public void report(PrintWriter info, PrintWriter trace) {
//		info.println("  Runs: " + runs);
//		info.println("  Overall dive time: " + time);
//	}

}
