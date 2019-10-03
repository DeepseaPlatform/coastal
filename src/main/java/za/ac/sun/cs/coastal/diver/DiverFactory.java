package za.ac.sun.cs.coastal.diver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Banner;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.TaskFactory;
import za.ac.sun.cs.coastal.Trigger;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverFactory.ObserverManager;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.exceptions.SymbolicException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SystemExitException;

public class DiverFactory implements TaskFactory {

	/**
	 * The number of diver tasks started (ever).
	 */
	private AtomicInteger diverTaskCount = new AtomicInteger(0);

	private int getDiverTaskCount() {
		return diverTaskCount.get();
	}

	@Override
	public DiverManager createManager(COASTAL coastal) {
		return new DiverManager(coastal);
	}

	@Override
	public Diver[] createTask(COASTAL coastal, TaskManager manager) {
		return new Diver[] { new Diver(coastal, (DiverManager) manager, diverTaskCount.getAndIncrement()) };
	}

	// ======================================================================
	//
	// DIVER MANAGER
	//
	// ======================================================================

	private static final String[] PROPERTY_NAMES = new String[] { "#tasks", "#dives", "waiting time", "total time" };

	public class DiverManager implements TaskManager {

		protected final Broker broker;

		/**
		 * Counter for the number of dives undertaken.
		 */
		private final AtomicLong diveCount = new AtomicLong(0);

		/**
		 * Accumulator of all the dive times.
		 */
		private final AtomicLong diverTime = new AtomicLong(0);

		/**
		 * Accumulator of all the dive waiting times.
		 */
		private final AtomicLong diverWaitTime = new AtomicLong(0);

		/**
		 * Counter for the dive waiting times.
		 */
		private final AtomicLong diverWaitCount = new AtomicLong(0);

		DiverManager(COASTAL coastal) {
			broker = coastal.getBroker();
			broker.subscribe("coastal-stop", this::report);
		}

		/**
		 * Increment and return the diver counter.
		 * 
		 * @return the incremented value of the diver counter
		 */
		public long getNextDiveCount() {
			return diveCount.incrementAndGet();
		}

		/**
		 * Return the current value of the diver counter.
		 * 
		 * @return the value of the diver counter
		 */
		public long getDiveCount() {
			return diveCount.get();
		}

		/**
		 * Add a reported dive time to the accumulator that tracks how long the dives
		 * took.
		 * 
		 * @param time
		 *             the time for this dive
		 */
		public void recordTime(long time) {
			diverTime.addAndGet(time);
		}

		/**
		 * Add a reported dive wait time. This is used to determine if it makes sense to
		 * create additional threads (or destroy them).
		 * 
		 * @param time
		 *             the wait time for this dive
		 */
		public void recordWaitTime(long time) {
			diverWaitTime.addAndGet(time);
			diverWaitCount.incrementAndGet();
		}

		public void report(Object object) {
			double dwt = diverWaitTime.get() / diverWaitCount.doubleValue();
			broker.publish("report", new Tuple("Divers.tasks", getDiverTaskCount()));
			broker.publish("report", new Tuple("Divers.count", getDiveCount()));
			broker.publish("report", new Tuple("Divers.time", diverTime.get()));
			broker.publish("report", new Tuple("Divers.wait-time", dwt));
		}

		@Override
		public String getName() {
			return "Diver";
		}

		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}

		@Override
		public Object[] getPropertyValues() {
			Object[] propertyValues = new Object[4];
			double dwt = diverWaitTime.get() / diverWaitCount.doubleValue();
			int dtc = getDiverTaskCount();
			long c = getDiveCount();
			long t = diverTime.get();
			propertyValues[0] = dtc;
			propertyValues[1] = String.format("%d (%.1f/sec)", c, c / (0.001 * t));
			propertyValues[2] = dwt;
			propertyValues[3] = t;
			return propertyValues;
		}

	}

	// ======================================================================
	//
	// DIVER TASK
	//
	// ======================================================================

	public class Diver implements Task {

		protected final COASTAL coastal;

		protected final Logger log;

		protected final Configuration configuration;

		protected final Broker broker;

		protected final DiverManager manager;

		protected final int diverTaskId;

		public Diver(COASTAL coastal, DiverManager manager, int id) {
			this.coastal = coastal;
			log = coastal.getLog();
			configuration = coastal.getConfig();
			broker = coastal.getBroker();
			this.manager = manager;
			diverTaskId = id;
		}

		@Override
		public void run() {
			log.trace("starting diver task, diverTaskId={}", diverTaskId);
			for (Tuple observer : coastal.getObserversPerTask()) {
				ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
				ObserverManager observerManager = (ObserverManager) observer.get(1);
				observerFactory.createObserver(coastal, observerManager);
			}
			try {
				while (true) {
					long t0 = System.currentTimeMillis();
					Input input = coastal.getNextDiverInput();
					long t1 = System.currentTimeMillis();
					manager.recordWaitTime(t1 - t0);
					SymbolicState symbolicState = null;
					ClassLoader classLoader = null;
					symbolicState = new SymbolicState(coastal, input);
					String banner = "(" + diverTaskId + ") starting dive " + manager.getNextDiveCount() + " @"
							+ Banner.getElapsed(coastal);
					log.trace(Banner.getBannerLine(banner, '-'));
					if (input == null) {
						log.trace(Banner.getBannerLine("NO CONCRETE VALUES", '*'));
					} else {
						log.trace(Banner.getBannerLine(input.toString(), '*'));
					}
					classLoader = coastal.getClassManager().createHeavyClassLoader(symbolicState);
					performRun(symbolicState, classLoader);
					manager.recordTime(System.currentTimeMillis() - t1);
					coastal.addPc(symbolicState.getExecution());
					broker.publishThread("dive-end", this);
					if (!symbolicState.mayContinue()) {
						coastal.stopWork();
					}
				}
			} catch (InterruptedException e) {
				broker.publishThread("diver-task-end", this);
				log.trace("stopping diver task, diverTaskId={}", diverTaskId);
				// throw e;
			}
		}

		private void performRun(SymbolicState symbolicState, ClassLoader classLoader) {
			log.trace("start run, diverTaskCount={}, symbolicState={}", diverTaskCount, symbolicState);
			for (Tuple observer : coastal.getObserversPerDiver()) {
				ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
				ObserverManager observerManager = (ObserverManager) observer.get(1);
				observerFactory.createObserver(coastal, observerManager);
			}
			try {
				Trigger entryPoint = coastal.getMainEntrypoint();
				Class<?> clas = classLoader.loadClass(entryPoint.getClassName());
				Method meth = clas.getMethod(entryPoint.getMethodName(), entryPoint.getParamTypes());
				meth.setAccessible(true);
				meth.invoke(null, coastal.getMainArguments());
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException x) {
				x.printStackTrace(coastal.getSystemErr());
			} catch (InvocationTargetException x) {
				Throwable t = x.getCause();
				if (t == null) {
					try {
						symbolicState.startCatch(-1);
					} catch (SymbolicException e) {
						// ignore, since run is over in any case
					}
				} else if (t instanceof SystemExitException) {
					broker.publish("system-exit", new Tuple(this, null));
				} else if (!(t instanceof SymbolicException)) {
					log.trace("exception in run, diverTaskCount={}, symbolicState={} frames={}", diverTaskCount,
							symbolicState.hashCode(), symbolicState.frames.hashCode());
					log.trace("P R O G R A M   E X C E P T I O N:", t);
					if (t instanceof AssertionError) {
						broker.publish("assert-failed", new Tuple(this, null));
					}
					try {
						symbolicState.startCatch(-1);
					} catch (SymbolicException e) {
						// ignore, since run is over in any case
					}
				}
			}
			log.trace("end of run, diverTaskCount={}, symbolicState={}", diverTaskCount, symbolicState);
		}

	}

}
