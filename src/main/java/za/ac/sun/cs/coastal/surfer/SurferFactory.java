/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.ac.sun.cs.coastal.surfer;

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
import za.ac.sun.cs.coastal.messages.FreqTuple;
import za.ac.sun.cs.coastal.messages.TimeTuple;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverFactory.ObserverManager;
import za.ac.sun.cs.coastal.symbolic.Execution;
import za.ac.sun.cs.coastal.symbolic.Input;
import za.ac.sun.cs.coastal.symbolic.exceptions.AbortedRunException;
import za.ac.sun.cs.coastal.symbolic.exceptions.LimitConjunctException;
import za.ac.sun.cs.coastal.symbolic.exceptions.CompletedRunException;
import za.ac.sun.cs.coastal.symbolic.exceptions.ErrorException;
import za.ac.sun.cs.coastal.symbolic.exceptions.SystemExitException;
import za.ac.sun.cs.coastal.symbolic.exceptions.UnsupportedOperationException;

public class SurferFactory implements TaskFactory {

	/**
	 * The number of surfer tasks started (ever).
	 */
	private AtomicInteger surferTaskCount = new AtomicInteger(0);

	private int getSurferTaskCount() {
		return surferTaskCount.get();
	}

	@Override
	public SurferManager createManager(COASTAL coastal) {
		return new SurferManager(coastal);
	}

	@Override
	public Surfer[] createTask(COASTAL coastal, TaskManager manager) {
		return new Surfer[] { new Surfer(coastal, (SurferManager) manager, surferTaskCount.getAndIncrement()) };
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
		 * Add a reported surf time to the accumulator that tracks how long the surfs
		 * took.
		 * 
		 * @param time
		 *             the time for this surf
		 */
		public void recordSurferTime(long time) {
			surferTime.addAndGet(time);
		}

		/**
		 * Add a reported surf wait time. This is used to determine if it makes sense to
		 * create additional threads (or destroy them).
		 * 
		 * @param time
		 *             the wait time for this surf
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
			broker.publish("report", new FreqTuple("Surfers.count", getSurfCount()));
			broker.publish("report", new Tuple("Surfers.aborted", abortCount.get()));
			broker.publish("report", new TimeTuple("Surfers.total-time", surferTime.get()));
			broker.publish("report", new TimeTuple("Surfers.wait-time", swt));
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
			int stc = getSurferTaskCount();
			long c = getSurfCount();
			long t = surferTime.get();
			propertyValues[0] = stc;
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

		protected final COASTAL coastal;

		protected final Logger log;

		protected final Configuration configuration;

		protected final Broker broker;

		protected final SurferManager manager;

		protected final int surferTaskId;

		protected final boolean safeMode;

		public Surfer(COASTAL coastal, SurferManager manager, int id) {
			this.coastal = coastal;
			log = coastal.getLog();
			configuration = coastal.getConfig();
			broker = coastal.getBroker();
			this.manager = manager;
			surferTaskId = id;
			safeMode = !coastal.getConfig().getBoolean("coastal.settings.trace-all", false);
		}

		@Override
		public void run() {
			log.trace("starting surfer task, surferTaskId={}", surferTaskId);
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
					String banner = "(" + surferTaskId + ") starting surf " + manager.getNextSurfCount() + " @"
							+ Banner.getElapsed(coastal);
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
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException
								| TypeNotPresentException x) {
							log.trace("class loading or execution exception");
							log.trace("exception details: ", x);
							x.printStackTrace(coastal.getSystemErr());
							System.exit(1);
						} catch (InvocationTargetException x) {
							Throwable t = x.getCause();
							if (t instanceof AbortedRunException) {
								aborted = true;
								log.trace("exception: aborted run");
							} else if (t instanceof CompletedRunException) {
								log.trace("exception: completed run");
							} else if (t instanceof LimitConjunctException) {
								log.trace("exception: conjunct limit reached");
							} else if (t instanceof SystemExitException) {
								log.trace("exception: System.exit() invoked");
								broker.publish("system-exit", new Tuple(this, null));
							} else if (t instanceof UnsupportedOperationException) {
								log.trace("exception: unsupported operation: {}", t.getMessage());
								log.info("UNSUPPORTED OPERATION: {}", t.getMessage());
								System.exit(1);
							} else if (t instanceof ErrorException) {
								log.fatal("*** I N T E R N A L   E R R O R ***", t.getCause());
								System.exit(1);
							} else {
								log.trace("exception in run, surferTaskCount={}", surferTaskCount);
								log.trace("exception details: ", t);
								if (t instanceof AssertionError) {
									broker.publish("assert-failed", new Tuple(this, null));
								}
								try {
									traceState.startCatch(-1);
								} catch (AbortedRunException e) {
									log.trace("exception: aborted run");
								} catch (CompletedRunException e) {
									log.trace("exception: completed run");
								} catch (LimitConjunctException e) {
									log.trace("exception: conjunct limit reached");
								} catch (SystemExitException e) {
									log.trace("exception: System.exit() invoked");
								} catch (UnsupportedOperationException e) {
									log.trace("exception: unsupported operation: {}", e.getMessage());
									log.info("UNSUPPORTED OPERATION: {}", t.getMessage());
									System.exit(1);
								} catch (Exception e) {
									log.trace("exception (cause unknown): ", e);
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
						} catch (ClassNotFoundException | NoSuchMethodException | SecurityException
								| IllegalAccessException | IllegalArgumentException | TypeNotPresentException x) {
							log.trace("class loading or execution exception");
							log.trace("exception details: ", x);
							x.printStackTrace(coastal.getSystemErr());
							System.exit(1);
						} catch (InvocationTargetException x) {
							Throwable t = x.getCause();
							if (t instanceof AbortedRunException) {
								aborted = true;
								log.trace("exception: aborted run");
							} else if (t instanceof CompletedRunException) {
								log.trace("exception: completed run");
							} else if (t instanceof LimitConjunctException) {
								log.trace("exception: conjunct limit reached");
							} else if (t instanceof SystemExitException) {
								log.trace("exception: System.exit() invoked");
								broker.publish("system-exit", new Tuple(this, null));
							} else if (t instanceof UnsupportedOperationException) {
								log.trace("exception: unsupported operation: {}", t.getMessage());
								log.info("UNSUPPORTED OPERATION: {}", t.getMessage());
								System.exit(1);
							} else if (t instanceof ErrorException) {
								log.fatal("*** I N T E R N A L   E R R O R ***", t.getCause());
								System.exit(1);
							} else {
								log.trace("exception in run, surferTaskCount={}", surferTaskCount);
								log.trace("exception details: ", t);
								if (t instanceof AssertionError) {
									broker.publish("assert-failed", new Tuple(this, null));
								}
								try {
									traceState.startCatch(-1);
								} catch (AbortedRunException e) {
									log.trace("exception: aborted run");
								} catch (CompletedRunException e) {
									log.trace("exception: completed run");
								} catch (LimitConjunctException e) {
									log.trace("exception: conjunct limit reached");
								} catch (SystemExitException e) {
									log.trace("exception: System.exit() invoked");
								} catch (UnsupportedOperationException e) {
									log.trace("exception: unsupported operation: {}", e.getMessage());
									log.info("UNSUPPORTED OPERATION: {}", t.getMessage());
									System.exit(1);
								} catch (Exception e) {
									log.trace("exception (cause unknown): ", e);
								}
							}
						}
					}
					// ------- END MANUAL INLINE
					manager.recordSurferTime(System.currentTimeMillis() - t1);
					if (aborted) {
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
				// ignore
			}
			broker.publishThread("surfer-task-end", this);
			log.trace("end of run, surferTaskCount={}", surferTaskCount);
		}

	}

}
