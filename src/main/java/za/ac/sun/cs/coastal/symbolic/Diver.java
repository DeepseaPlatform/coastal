package za.ac.sun.cs.coastal.symbolic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.instrument.InstrumentationClassLoader;
import za.ac.sun.cs.coastal.reporting.Banner;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;
import za.ac.sun.cs.coastal.strategy.Strategy;
import za.ac.sun.cs.green.expr.Constant;

public class Diver implements Reporter {

	private static final Logger lgr = Configuration.getLogger();

	private int runs = 0;

	private long time = 0;
	
	private final InstrumentationClassLoader instrumentationClassLoader;

	public Diver() {
		Reporters.register(this);
		String cp = System.getProperty("java.class.path");
		instrumentationClassLoader = new InstrumentationClassLoader(cp);
	}

	public void dive() {
		Strategy strategy = Configuration.getStrategy();
		if (strategy == null) {
			lgr.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}
		Map<String, Constant> concreteValues = null;
		long runLimit = Configuration.getLimitRuns();
		if (runLimit == 0) { runLimit = Long.MIN_VALUE; }
		long timeLimit = Configuration.getLimitTime();
		if (timeLimit == 0) { timeLimit = Long.MAX_VALUE; }
		long tl0 = System.currentTimeMillis();
		do {
			if ((System.currentTimeMillis() - tl0) / 1000 > timeLimit) {
				lgr.warn("time limit reached");
				return;
			}
			if (--runLimit < 0) {
				lgr.warn("run limit reached");
				return;
			}
			runs++;
			lgr.info(Banner.getBannerLine("starting dive " + runs, '-'));
			long t0 = System.currentTimeMillis();
			SymbolicState.reset(concreteValues);
			performRun();
			time += System.currentTimeMillis() - t0;
			concreteValues = strategy.refine();
		} while ((concreteValues != null) && SymbolicState.mayContinue());
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
		PrintStream out = System.out, err = System.err;
		try {
			Class<?> clas = instrumentationClassLoader.loadClass(Configuration.getMain());
			Method meth = clas.getMethod("main", String[].class);
			// Redirect System.out/System.err
			if (!Configuration.getEchoOutput()) {
				System.setOut(NUL); System.setErr(NUL);
			}
			meth.invoke(null, new Object[] { new String[0] });
			System.setOut(out); System.setErr(err);
		} catch (ClassNotFoundException x) {
			System.setOut(out); System.setErr(err);
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			System.setOut(out); System.setErr(err);
			x.printStackTrace();
		} catch (SecurityException x) {
			System.setOut(out); System.setErr(err);
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			System.setOut(out); System.setErr(err);
			x.printStackTrace();
		} catch (IllegalArgumentException x) {
			System.setOut(out); System.setErr(err);
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			System.setOut(out); System.setErr(err);
			Throwable t = x.getCause();
			if ((t != null) && (t instanceof LimitConjunctException)) {
				// limit on nr of conjuncts has been reached
			} else {
				x.printStackTrace();
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
		return "Dive";
	}

	@Override
	public void report(PrintWriter out) {
		Map<String, Integer> markers = SymbolicState.getMarkers();
		if (markers.size() > 0) {
			out.println("  === MARKERS ===");
			for (Map.Entry<String, Integer> entry : markers.entrySet()) {
				out.printf("    %-10s %6d\n", entry.getKey(), entry.getValue());
			}
		}
		out.println("  Runs: " + runs);
		out.println("  Overall dive time: " + time);
	}

}
