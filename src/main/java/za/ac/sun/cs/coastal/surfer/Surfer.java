package za.ac.sun.cs.coastal.surfer;

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
import za.ac.sun.cs.coastal.symbolic.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.VM;
import za.ac.sun.cs.green.expr.Constant;

public class Surfer implements Callable<Void> {

	private final COASTAL coastal;

	private final Logger log;

	private final ImmutableConfiguration config;

	private final Broker broker;

	public Surfer(COASTAL coastal) {
		this.coastal = coastal;
		log = coastal.getLog();
		config = coastal.getConfig();
		broker = coastal.getBroker();
	}

	@Override
	public Void call() throws Exception {
		log.trace("^^^ surfer task starting");
		for (Tuple observer : coastal.getObserversPerTask()) {
			ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
			ObserverManager observerManager = (ObserverManager) observer.get(1);
			observerFactory.createObserver(coastal, observerManager);
		}
		try {
			while (true) {
				long t0 = System.currentTimeMillis();
				Map<String, Constant> concreteValues = coastal.getNextSurferModel();
				long t1 = System.currentTimeMillis();
				coastal.recordSurferTime(t1 - t0);
				TraceState traceState = new TraceState(coastal, concreteValues);
				String banner = "starting surf " + coastal.getNextSurferCount() + " @" + Banner.getElapsed(coastal);
				log.trace(Banner.getBannerLine(banner, '-'));
				ClassLoader classLoader = coastal.getClassManager().createLightClassLoader(traceState);
				performRun(classLoader);
				coastal.recordSurferTime(System.currentTimeMillis() - t1);
				coastal.addTrace(traceState.getTrace());
				broker.publishThread("surfer-end", this);
				if (!traceState.mayContinue()) {
					coastal.stopWork();
				}
			}
		} catch (InterruptedException e) {
			broker.publishThread("surfer-task-end", this);
			log.trace("^^^ surfer task canceled");
			throw e;
		}
	}

	private void performRun(ClassLoader classLoader) {
		for (Tuple observer : coastal.getObserversPerSurfer()) {
			ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
			ObserverManager observerManager = (ObserverManager) observer.get(1);
			observerFactory.createObserver(coastal, observerManager);
		}
		try {
			Class<?> clas = classLoader.loadClass(config.getString("coastal.main"));
			Method meth = clas.getMethod("main", String[].class);
			meth.invoke(null, new Object[] { new String[0] });
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException x) {
			x.printStackTrace(coastal.getSystemErr());
		} catch (InvocationTargetException x) {
			Throwable t = x.getCause();
			if ((t == null) || !(t instanceof LimitConjunctException)) {
				// x.printStackTrace();
				try {
					VM.startCatch(-1);
				} catch (LimitConjunctException e) {
					// ignore, since run is over in any case
				}
			}
		}
	}

}
