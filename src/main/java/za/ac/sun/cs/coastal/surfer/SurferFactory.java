package za.ac.sun.cs.coastal.surfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.TaskFactory;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverFactory.ObserverManager;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.exceptions.AbortedRunException;
import za.ac.sun.cs.coastal.symbolic.exceptions.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SystemExitException;

public class SurferFactory implements TaskFactory {

	/**
	 * Prefix added to log messages.
	 */
	private static final String LOG_PREFIX = "...";

	/**
	 * The number of surfer tasks started (ever).
	 */
	private int surferTaskCount = 0;

	@Override
	public SurferManager createManager(COASTAL coastal) {
		return new SurferManager(coastal);
	}

	@Override
	public Surfer[] createTask(COASTAL coastal, TaskManager manager) {
		surferTaskCount++;
		return new Surfer[] { new Surfer(coastal, (SurferManager) manager) };
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

		/**
		 * The number of aborted surfs. 
		 */
		private final AtomicLong abortCount = new AtomicLong(0);

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

		/**
		 * Increment and return the abort counter.
		 * 
		 * @return the incremented value of the abort counter
		 */
		public long incrementAbortCount() {
			return abortCount.incrementAndGet();
		}

		public void report(Object object) {
			double swt = surferWaitTime.get() / surferWaitCount.doubleValue();
			broker.publish("report", new Tuple("Surfers.tasks", surferTaskCount));
			broker.publish("report", new Tuple("Surfers.count", getSurfCount()));
			broker.publish("report", new Tuple("Surfers.aborted", abortCount.get()));
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
			long c = getSurfCount();
			long t = surferTime.get();
			propertyValues[0] = surferTaskCount;
			propertyValues[1] = String.format("%d (%.1f/sec)", c, c / (0.001 * t));
			propertyValues[2] = swt;
			propertyValues[3] = t;
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

		private final Broker broker;

		private final SurferManager manager;

		private final boolean safeMode;

		public Surfer(COASTAL coastal, SurferManager manager) {
			this.coastal = coastal;
			log = coastal.getLog();
			broker = coastal.getBroker();
			this.manager = manager;
			safeMode = !coastal.getConfig().getBoolean("coastal.settings.trace-all", false);
		}

		@Override
		public void run() {
			log.trace("{} surfer task starting", LOG_PREFIX);
			for (Tuple observer : coastal.getObserversPerTask()) {
				ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
				ObserverManager observerManager = (ObserverManager) observer.get(1);
				observerFactory.createObserver(coastal, observerManager);
			}
			for (Tuple observer : coastal.getObserversPerSurfer()) {
				ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
				ObserverManager observerManager = (ObserverManager) observer.get(1);
				observerFactory.createObserver(coastal, observerManager);
			}
			try {
				Method meth = null;
				Trigger trigger = coastal.getMainEntrypoint();
				TraceState traceState = new TraceState(coastal, null);
				while (!Thread.currentThread().isInterrupted()) {
					long t0 = System.currentTimeMillis();
					Input input = coastal.getNextSurferInput();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					String banner = "starting surf " + manager.getNextSurfCount(); // + " @" + Banner.getElapsed(coastal)
					log.trace(Banner.getBannerLine(banner, '-'));
					if (input == null) {
						log.trace(Banner.getBannerLine("NO CONCRETE VALUES", '*'));
					} else {
						log.trace(Banner.getBannerLine(input.toString(), '*'));
					}
					boolean aborted = false;
					// ------- BEGIN MANUAL INLINE
					// performRun(classLoader, trigger);
					if (safeMode && (meth != null)) {
						traceState.reset(input);
						try {
							meth.invoke(null, coastal.getMainArguments());
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException x) {
							x.printStackTrace(coastal.getSystemErr());
						} catch (InvocationTargetException x) {
							Throwable t = x.getCause();
							log.trace("{} InvocationTargetException, t.class={}", LOG_PREFIX, t.getClass().getName());
//							if ((t != null) && t.getClass().getName().equals("java.lang.NullPointerException")) {
//								x.printStackTrace(coastal.getSystemErr());
//								System.out.println("Hello world!");
//							}
							if ((t == null) || !(t instanceof SymbolicException)) {
								// x.printStackTrace();
								try {
									traceState.startCatch(-1);
								} catch (LimitConjunctException e) {
									// ignore, since run is over in any case
								} catch (SymbolicException e) {
									// should we do something here?
								}
							} else if (t instanceof AbortedRunException) {
								aborted = true;
							} else if (!(t instanceof SymbolicException)) {
								log.info("P R O G R A M   E X C E P T I O N:", t);
								if (t instanceof AssertionError) {
									broker.publish("assert-failed", new Tuple(this, null));
								}
								try {
								traceState.startCatch(-1);
								} catch (SymbolicException e) {
									// ignore, since run is over in any case
								}
							}
						}
					} else {
						traceState = new TraceState(coastal, input);
						ClassLoader classLoader = coastal.getClassManager().createLightClassLoader(traceState);
						try {
							Class<?> clas = classLoader.loadClass(trigger.getClassName());
							meth = clas.getMethod(trigger.getMethodName(), trigger.getParamTypes());
							meth.setAccessible(true);
							meth.invoke(null, coastal.getMainArguments());
						} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException x) {
							x.printStackTrace(coastal.getSystemErr());
						} catch (InvocationTargetException x) {
							Throwable t = x.getCause();
							log.trace("{} InvocationTargetException, t.class={}", LOG_PREFIX, t.getClass().getName());
//							if ((t != null) && t.getClass().getName().equals("java.lang.NullPointerException")) {
//								x.printStackTrace(coastal.getSystemErr());
//								System.out.println("Hello world!");
//							}
							if ((t == null) || !(t instanceof SymbolicException)) {
								// x.printStackTrace();
								try {
									traceState.startCatch(-1);
								} catch (LimitConjunctException e) {
									// ignore, since run is over in any case
								} catch (SymbolicException e) {
									// should we do something here?
								}
							} else if (t instanceof SystemExitException) {
								broker.publish("system-exit", new Tuple(this, null));
							} else if (t instanceof AbortedRunException) {
								aborted = true;
							} else if (!(t instanceof SymbolicException)) {
								log.info("P R O G R A M   E X C E P T I O N:", t);
								if (t instanceof AssertionError) {
									broker.publish("assert-failed", new Tuple(this, null));
								}
								try {
									traceState.startCatch(-1);
								} catch (SymbolicException e) {
									// ignore, since run is over in any case
								}
							}
						}
					}
					// ------- END MANUAL INLINE
					manager.recordSurferTime(System.currentTimeMillis() - t1);
					if (aborted) {
						log.trace("{} execution aborted, fully explored", LOG_PREFIX);
						manager.incrementAbortCount();
					} else {
						Execution execution = traceState.getExecution();
						execution.copyPayload(input);
						coastal.addTrace(execution);
					}
					broker.publishThread("surfer-end", this);
					if (!traceState.mayContinue()) {
						coastal.stopWork();
					}
				}
			} catch (InterruptedException e) {
				// broker.publishThread("surfer-task-end", this);
				// log.trace("{} surfer task canceled", LOG_PREFIX);
				// throw e;
			}
			broker.publishThread("surfer-task-end", this);
			log.trace("{} surfer task canceled", LOG_PREFIX);
			// return null;
		}

	}

}
