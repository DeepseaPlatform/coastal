package za.ac.sun.cs.coastal.surfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverFactory.ObserverManager;
import za.ac.sun.cs.coastal.symbolic.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.VM;

public class SurferFactory implements TaskFactory {

	/**
	 * The number of surfer tasks started (ever).
	 */
	private int surferTaskCount = 0;

	@Override
	public SurferManager createManager(COASTAL coastal) {
		return new SurferManager(coastal);
	}

	@Override
	public Surfer createTask(COASTAL coastal, TaskManager manager) {
		surferTaskCount++;
		return new Surfer(coastal, (SurferManager) manager);
	}

	// ======================================================================
	//
	// SURFER MANAGER
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#surfs", "waiting time", "total time" };

	public class SurferManager implements TaskManager {

		protected final Broker broker;

		/**
		 * Counter for the number of surfs undertaken.
		 */
		private final AtomicLong surferCount = new AtomicLong(0);

		/**
		 * Accumulator of all the surf times.
		 */
		private final AtomicLong surferTime = new AtomicLong(0);

		/**
		 * Accumulator of all the surf waiting times.
		 */
		private final AtomicLong surferWaitTime = new AtomicLong(0);

		/**
		 * Counter for the surf waiting times.
		 */
		private final AtomicLong surferWaitCount = new AtomicLong(0);

		SurferManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		/**
		 * Increment and return the surfer counter.
		 * 
		 * @return the incremented value of the surfer counter
		 */
		public long getNextSurfCount() {
			return surferCount.incrementAndGet();
		}

		/**
		 * Return the current value of the surfer counter.
		 * 
		 * @return the value of the surfer counter
		 */
		public long getSurfCount() {
			return surferCount.get();
		}

		/**
		 * Add a reported surf time to the accumulator that tracks how long the
		 * surfs took.
		 * 
		 * @param time
		 *            the time for this surf
		 */
		public void recordSurferTime(long time) {
			surferTime.addAndGet(time);
		}

		/**
		 * Add a reported surf wait time. This is used to determine if it makes
		 * sense to create additional threads (or destroy them).
		 * 
		 * @param time
		 *            the wait time for this surf
		 */
		public void recordWaitTime(long time) {
			surferWaitTime.addAndGet(time);
			surferWaitCount.incrementAndGet();
		}

		public void report(Object object) {
			double swt = surferWaitTime.get() / surferWaitCount.doubleValue();
			broker.publish("report", new Tuple("Surfers.tasks", surferTaskCount));
			broker.publish("report", new Tuple("Surfers.count", getSurfCount()));
			broker.publish("report", new Tuple("Surfers.total-time", surferTime.get()));
			broker.publish("report", new Tuple("Surfers.wait-time", swt));
		}

		@Override
		public String getName() {
			return "Surfer";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[4];
			double swt = surferWaitTime.get() / surferWaitCount.doubleValue();
			propertyValues[0] = surferTaskCount;
			propertyValues[1] = getSurfCount();
			propertyValues[2] = swt;
			propertyValues[3] = surferTime.get();
			return propertyValues;
		}

	}

	// ======================================================================
	//
	// SURFER TASK
	//
	// ======================================================================

	public class Surfer implements Task {

		private final COASTAL coastal;

		private final Logger log;

		private final ImmutableConfiguration config;

		private final Broker broker;

		private final SurferManager manager;

		public Surfer(COASTAL coastal, SurferManager manager) {
			this.coastal = coastal;
			log = coastal.getLog();
			config = coastal.getConfig();
			broker = coastal.getBroker();
			this.manager = manager;
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
					Map<String, Object> concreteValues = coastal.getNextSurferModel();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					TraceState traceState = new TraceState(coastal, concreteValues);
					String banner = "starting surf " + manager.getNextSurfCount() + " @" + Banner.getElapsed(coastal);
					log.trace(Banner.getBannerLine(banner, '-'));
					ClassLoader classLoader = coastal.getClassManager().createLightClassLoader(traceState);
					performRun(classLoader);
					manager.recordSurferTime(System.currentTimeMillis() - t1);
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
				Class<?> clas = classLoader.loadClass(config.getString("coastal.target.main"));
				Method meth = clas.getMethod("main", String[].class);
				meth.invoke(null, new Object[] { new String[0] });
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException x) {
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

}
