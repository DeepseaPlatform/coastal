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
		do {
			runs++;
			lgr.info(Banner.getBannerLine("starting dive " + runs, '-'));
			long t0 = System.currentTimeMillis();
			SymbolicState.reset(concreteValues);
			performRun();
			time += System.currentTimeMillis() - t0;
			concreteValues = strategy.refine();
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
		PrintStream out = System.out, err = System.err;
		try {
			Class<?> clas = instrumentationClassLoader.loadClass(Configuration.getMain());
			Method meth = clas.getMethod("main", String[].class);
			// Redirect System.out/System.err
			if (!Configuration.getEchoOutput()) {
				System.setOut(NUL); System.setErr(NUL);
			}
			meth.invoke(null, new Object[] { new String[0] });
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		} catch (SecurityException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (IllegalArgumentException x) {
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			x.printStackTrace();
		}
		System.setOut(out); System.setErr(err);
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
		out.println("  Runs: " + runs);
		out.println("  overall dive time: " + time);
	}

}
