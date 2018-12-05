package za.ac.sun.cs.coastal.symbolic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverManager;
import za.ac.sun.cs.green.expr.Constant;

public class Diver implements Callable<Void> {

	private final COASTAL coastal;

	private final Logger log;

	private final ImmutableConfiguration config;

	private final Broker broker;

	public Diver(COASTAL coastal) {
		this.coastal = coastal;
		log = coastal.getLog();
		config = coastal.getConfig();
		broker = coastal.getBroker();
	}

	@Override
	public Void call() throws Exception {
		log.trace("^^^ diver task starting");
		for (Tuple observer : coastal.getObserversPerTask()) {
			ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
			ObserverManager observerManager = (ObserverManager) observer.get(1);
			observerFactory.createObserver(coastal, observerManager);
		}
		try {
			while (true) {
				long t0 = System.currentTimeMillis();
				Map<String, Constant> concreteValues = coastal.getNextModel();
				long t1 = System.currentTimeMillis();
				coastal.recordDiveWaitTime(t1 - t0);
				SymbolicState symbolicState = new SymbolicState(coastal, concreteValues);
				String banner = "starting dive " + coastal.getNextDiveCount() + " @" + Banner.getElapsed(coastal);
				log.trace(Banner.getBannerLine(banner, '-'));
				ClassLoader classLoader = coastal.getClassManager().createClassLoader(symbolicState);
				// SymbolicVM.setState(symbolicState);
				performRun(classLoader);
				coastal.recordDiveTime(System.currentTimeMillis() - t1);
				// ----> disposition.notifyPathListeners(symbolicState);
				SegmentedPC spc = symbolicState.getSegmentedPathCondition();
				coastal.addPc(spc);
				broker.publishThread("dive-end", this);
				if (!symbolicState.mayContinue()) {
					coastal.stopWork();
				}
			}
		} catch (InterruptedException e) {
			broker.publishThread("diver-task-end", this);
			log.trace("^^^ diver task canceled");
			throw e;
		}
	}

	private static final PrintStream NUL = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// do nothing
		}
	});

	private void performRun(ClassLoader classLoader) {
		for (Tuple observer : coastal.getObserversPerDive()) {
			ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
			ObserverManager observerManager = (ObserverManager) observer.get(1);
			observerFactory.createObserver(coastal, observerManager);
		}
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

}
