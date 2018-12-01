package za.ac.sun.cs.coastal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationUtils;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.instrument.InstrumentationClassManager;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.observers.ObserverFactory;
import za.ac.sun.cs.coastal.observers.ObserverManager;
import za.ac.sun.cs.coastal.strategy.StrategyFactory;
import za.ac.sun.cs.coastal.strategy.StrategyManager;
import za.ac.sun.cs.coastal.strategy.StrategyTask;
import za.ac.sun.cs.coastal.symbolic.Diver;
import za.ac.sun.cs.coastal.symbolic.Model;
import za.ac.sun.cs.coastal.symbolic.SegmentedPC;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;
import za.ac.sun.cs.green.expr.Constant;

/**
 * A COASTAL analysis run. The main function (or some outside client) constructs
 * an instance of this class to execute one analysis run of the system.
 */
public class COASTAL {

	/**
	 * The logger for this analysis run. This is not created but set by the
	 * outside world.
	 */
	private final Logger log;

	/**
	 * The configuration for this run.
	 */
	private final ImmutableConfiguration config;

	/**
	 * The single broker that will manage all messages for this analysis run.
	 */
	private final Broker broker;

	/**
	 * The single reporter that collects information about the analysis run to
	 * display at the end.
	 */
	private final Reporter reporter;

	/**
	 * The manager of all classes loaded during the analysis run.
	 */
	private final InstrumentationClassManager classManager;

	/**
	 * A list of all targets (prefixes of classes that will be instrumented).
	 */
	private final List<String> targets = new ArrayList<>();

	/**
	 * A list of all triggers that switch on symbolic execution.
	 */
	private final List<Trigger> triggers = new ArrayList<>();

	/**
	 * The default minimum bound for integers.
	 */
	private final int defaultMinIntValue;

	/**
	 * The default maximum bound for integers.
	 */
	private final int defaultMaxIntValue;

	/**
	 * A map from variable names to their lower bounds.
	 */
	private final Map<String, Integer> minBounds = new HashMap<>();

	/**
	 * A map from variable names to their lower bounds.
	 */
	private final Map<String, Integer> maxBounds = new HashMap<>();

	/**
	 * A map from method names to delegate objects (which are instances of the
	 * modelling classes).
	 */
	private final Map<String, Object> delegates = new HashMap<>();

	/**
	 * A list of observer factories and managers that must be started once per
	 * run.
	 */
	private final List<Tuple> observersPerRun = new ArrayList<>();

	/**
	 * A list of observer factories and managers that must be started once per
	 * task.
	 */
	private final List<Tuple> observersPerTask = new ArrayList<>();

	/**
	 * A list of observer factories and managers that must be started once per
	 * dive.
	 */
	private final List<Tuple> observersPerDive = new ArrayList<>();

	/**
	 * The wall-clock-time that the analysis run was started.
	 */
	private Calendar startingTime;

	/**
	 * The wall-clock-time that the analysis run was stopped.
	 */
	private Calendar stoppingTime;

	/**
	 * The maximum number of SECONDS the coastal run is allowed to execute.
	 */
	private final long timeLimit;

	/**
	 * The maximum number of threads to use for divers and strategies.
	 */
	private final int maxThreads;

	/**
	 * Counter for the number of dives undertaken.
	 */
	private final AtomicLong diveCount = new AtomicLong(0);

	/**
	 * Accumulator of all the dive times.
	 */
	private final AtomicLong diveTime = new AtomicLong(0);

	/**
	 * The number of diver tasks started (ever).
	 */
	private int diverTaskCount = 0;

	/**
	 * The number of strategy tasks started (ever).
	 */
	private int strategyTaskCount = 0;

	/**
	 * The task manager for concurrent divers and strategies.
	 */
	private final ExecutorService executor;

	/**
	 * The task completion manager that collects the "results" from divers and
	 * strategies. In this case, there are no actual results; instead, all
	 * products are either consumed or collected by the reporter.
	 */
	private final CompletionService<Void> completionService;

	/**
	 * A list of outstanding tasks.
	 */
	private final List<Future<Void>> futures;

	/**
	 * A queue of models produced by strategies and consumed by divers.
	 */
	private final BlockingQueue<Model> models;

	/**
	 * A queue of path conditions produced by divers and consumed by strategies.
	 */
	private final BlockingQueue<SegmentedPC> pcs;

	/**
	 * The one-and-only strategy factory for this analysis run.
	 */
	private final StrategyFactory strategyFactory;

	/**
	 * The one-and-only strategy manager for this analysis run.
	 */
	private final StrategyManager strategyManager;

	/**
	 * The outstanding number of models that have not been processed by divers,
	 * or whose resulting path conditions have not been processed by a strategy.
	 * This is not merely the number of items in the models queue or the pcs
	 * queue: work may also be presented by a model of path condition currently
	 * being processed. The strategy adjusts the work after it has processed a
	 * path condition.
	 */
	private final AtomicLong work = new AtomicLong(0);

	/**
	 * A flag to indicate that either (1) all work is done, or (2) symbolic
	 * execution must stop because a "stop" point was reached.
	 */
	private final AtomicBoolean workDone = new AtomicBoolean(false);

	/**
	 * Initialize the final fields for this analysis run of COASTAL.
	 * 
	 * @param log
	 *            the logger to use for this analysis run
	 * @param config
	 *            the configuration to use for this analysis run
	 */
	public COASTAL(Logger log, ImmutableConfiguration config) {
		this.log = log;
		this.config = config;
		broker = new Broker();
		broker.subscribe("coastal-stop", this::report);
		reporter = new Reporter(this);
		classManager = new InstrumentationClassManager(this, System.getProperty("java.class.path"));
		defaultMinIntValue = Conversion.minmax(getConfig().getInt("coastal.bound.int.min", Integer.MIN_VALUE),
				Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
		defaultMaxIntValue = Conversion.minmax(getConfig().getInt("coastal.bound.int.max", Integer.MAX_VALUE),
				Integer.MIN_VALUE + 2, Integer.MAX_VALUE);
		parseConfig();
		timeLimit = Conversion.limitLong(getConfig(), "coastal.limits.time");
		maxThreads = Conversion.minmax(getConfig().getInt("coastal.max-threads", 2), 2, Short.MAX_VALUE);
		executor = Executors.newCachedThreadPool();
		completionService = new ExecutorCompletionService<Void>(executor);
		futures = new ArrayList<Future<Void>>(maxThreads);
		models = new PriorityBlockingQueue<>(10, (Model m1, Model m2) -> m1.getPriority() - m2.getPriority());
		pcs = new LinkedBlockingQueue<>();
		StrategyFactory sf = null;
		String strategyName = config.getString("coastal.strategy");
		if (strategyName != null) {
			Object sfObject = Conversion.createInstance(this, strategyName);
			if ((sfObject != null) && (sfObject instanceof StrategyFactory)) {
				sf = (StrategyFactory) sfObject;
			}
		}
		if (sf == null) {
			log.fatal("NO STRATEGY SPECIFIED -- TERMINATING");
			System.exit(1);
		}
		strategyFactory = sf;
		strategyManager = strategyFactory.createManager(this);
	}

	/**
	 * Return the logger for this run of COASTAL.
	 * 
	 * @return the one and only logger for this analysis run
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * Return the configuration for this analysis of COASTAL. This configuration
	 * is immutable.
	 * 
	 * @return the configuration
	 */
	public ImmutableConfiguration getConfig() {
		return config;
	}

	/**
	 * Return the message broker for this analysis run of COASTAL.
	 * 
	 * @return the message broker
	 */
	public Broker getBroker() {
		return broker;
	}

	/**
	 * Return the reporter for this analysis run of COASTAL. This is used mainly
	 * for testing purposes.
	 * 
	 * @return the reporter
	 */
	public Reporter getReporter() {
		return reporter;
	}

	/**
	 * Return the class manager for this analysis run of COASTAL.
	 * 
	 * @return the class manager
	 */
	public InstrumentationClassManager getClassManager() {
		return classManager;
	}

	/**
	 * Check is a potential target is an actual target. The potential target is
	 * simply a class name that is compared to all known targets to see if any
	 * are prefixes of the potential target.
	 * 
	 * @param potentialTarget
	 *            the name of class
	 * @return true if and only if the potential target is prefixed by a known
	 *         target
	 */
	public boolean isTarget(String potentialTarget) {
		for (String target : targets) {
			if (potentialTarget.startsWith(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Find the index of the trigger with the corresponding name and signature.
	 * If no such trigger exists, return -1.
	 * 
	 * @param name
	 *            the method name of the (potential) trigger
	 * @param signature
	 *            the signature of the (potential) trigger
	 * @return the index of the trigger in the list of triggers, or -1
	 */
	public int findTrigger(String name, String signature) {
		int index = 0;
		for (Trigger t : triggers) {
			if (t.match(name, signature)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Return the trigger with a specified index.
	 * 
	 * @param index
	 *            the index we are searching for
	 * @return the corresponding trigger or {@code null} if there is no such
	 *         trigger
	 */
	public Trigger getTrigger(int index) {
		return triggers.get(index);
	}

	/**
	 * Find a delegate for a specified class name.
	 * 
	 * @param className
	 *            the name of the class to search for
	 * @return the delegate object or {@code null} if there is none
	 */
	public Object findDelegate(String className) {
		return delegates.get(className);
	}

	/**
	 * A convenient constant for use in
	 * {@link #findDelegate(String, String, String)}. This represents the empty
	 * list of parameters.
	 */
	private static final Class<?>[] EMPTY_PARAMETERS = new Class<?>[0];

	/**
	 * Find a delegate method.
	 * 
	 * @param owner
	 *            the method class name
	 * @param name
	 *            the method name
	 * @param descriptor
	 *            the method signature
	 * @return a Java reflection of the method or {@code null} if it was not
	 *         found
	 */
	public Method findDelegate(String owner, String name, String descriptor) {
		Object delegate = findDelegate(owner);
		if (delegate == null) {
			return null;
		}
		String methodName = name + SymbolicState.getAsciiSignature(descriptor);
		Method delegateMethod = null;
		try {
			delegateMethod = delegate.getClass().getDeclaredMethod(methodName, EMPTY_PARAMETERS);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
		assert delegateMethod != null;
		return delegateMethod;
	}

	/**
	 * Parse the COASTAL configuration and extract the targets, triggers,
	 * delegates, bounds, and observers.
	 */
	public void parseConfig() {
		// PARSE TARGETS
		targets.addAll(getConfig().getList(String.class, "coastal.target"));
		// PARSE TRIGGERS
		String[] triggerNames = getConfig().getStringArray("coastal.trigger");
		for (int i = 0; i < triggerNames.length; i++) {
			triggers.add(Trigger.createTrigger(triggerNames[i].trim()));
		}
		// PARSE DELEGATES
		for (int i = 0; true; i++) {
			String key = "coastal.delegate(" + i + ")";
			String target = getConfig().getString(key + ".target");
			if (target == null) {
				break;
			}
			String model = getConfig().getString(key + ".model");
			Object modelObject = Conversion.createInstance(this, model.trim());
			if (modelObject != null) {
				delegates.put(target.trim(), modelObject);
			}
		}
		// PARSE BOUNDS
		for (int i = 0; true; i++) {
			String key = "coastal.bound(" + i + ")";
			String var = getConfig().getString(key + "[@name]");
			if (var == null) {
				break;
			}
			minBounds.put(var, getConfig().getInt(key + "[@min]", defaultMinIntValue));
			maxBounds.put(var, getConfig().getInt(key + "[@max]", defaultMaxIntValue));
		}
		// PARSE OBSERVERS
		for (int i = 0; true; i++) {
			String key = "coastal.observer(" + i + ")";
			String observerName = getConfig().getString(key);
			if (observerName == null) {
				break;
			}
			Object observerFactory = Conversion.createInstance(this, observerName.trim());
			if ((observerFactory != null) && (observerFactory instanceof ObserverFactory)) {
				ObserverFactory factory = (ObserverFactory) observerFactory;
				ObserverManager manager = ((ObserverFactory) observerFactory).createManager(this);
				int frequency = factory.getFrequency();
				if (frequency == ObserverFactory.ONCE_PER_RUN) {
					observersPerRun.add(new Tuple(observerFactory, manager));
				} else if (frequency == ObserverFactory.ONCE_PER_TASK) {
					observersPerTask.add(new Tuple(observerFactory, manager));
				} else {
					observersPerDive.add(new Tuple(observerFactory, manager));
				}
			}
		}
	}

	public Iterable<Tuple> getObserversPerRun() {
		return observersPerRun;
	}

	public Iterable<Tuple> getObserversPerTask() {
		return observersPerTask;
	}

	public Iterable<Tuple> getObserversPerDive() {
		return observersPerDive;
	}

	/**
	 * Return the lower bound for symbolic integer variables with an explicit
	 * bound of their own.
	 * 
	 * @return the lower bound for symbolic integers
	 */
	public int getDefaultMinIntValue() {
		return defaultMinIntValue;
	}

	/**
	 * Return the lower bound for the specified symbolic integer variable.
	 * 
	 * @param variable
	 *            the name of the variable
	 * @return the lower bound for the variable
	 */
	public int getMinBound(String variable) {
		return getMinBound(variable, getDefaultMinIntValue());
	}

	/**
	 * Return the lower bound for a specific variable, or -- if there is no
	 * explicit bound -- for another variable. If there is no explicit bound,
	 * the specified default value is returned.
	 * 
	 * This is used for array where the specific variable is the array index and
	 * the more general variable is the array as a whole.
	 * 
	 * @param variable1
	 *            the name of the specific variable
	 * @param variable2
	 *            the name of the more general variable
	 * @return the lower bound for either variable
	 */
	public int getMinBound(String variable1, String variable2) {
		return getMinBound(variable1, getMinBound(variable2, getDefaultMinIntValue()));
	}

	/**
	 * Return the lower bound for the specified symbolic integer variable. If
	 * there is no explicit bound, the specified default value is returned.
	 * 
	 * @param variable
	 *            the name of the variable
	 * @param defaultValue
	 *            the default lower bound
	 * @return the lower bound for the variable
	 */
	public int getMinBound(String variable, int defaultValue) {
		Integer min = minBounds.get(variable);
		if (min == null) {
			min = defaultValue;
		}
		return min;
	}

	/**
	 * Return the upper bound for symbolic integer variables with an explicit
	 * bound of their own.
	 * 
	 * @return the upper bound for symbolic integers
	 */
	public int getDefaultMaxIntValue() {
		return defaultMaxIntValue;
	}

	/**
	 * Return the upper bound for the specified symbolic integer variable.
	 * 
	 * @param variable
	 *            the name of the variable
	 * @return the upper bound for the variable
	 */
	public int getMaxBound(String variable) {
		return getMaxBound(variable, getDefaultMaxIntValue());
	}

	/**
	 * Return the upper bound for a specific variable, or -- if there is no
	 * explicit bound -- for another variable. If there is no explicit bound,
	 * the specified default value is returned.
	 * 
	 * This is used for array where the specific variable is the array index and
	 * the more general variable is the array as a whole.
	 * 
	 * @param variable1
	 *            the name of the specific variable
	 * @param variable2
	 *            the name of the more general variable
	 * @return the upper bound for either variable
	 */
	public int getMaxBound(String variable1, String variable2) {
		return getMaxBound(variable1, getMaxBound(variable2, getDefaultMaxIntValue()));
	}

	/**
	 * Return the upper bound for the specified symbolic integer variable. If
	 * there is no explicit bound, the specified default value is returned.
	 * 
	 * @param variable
	 *            the name of the variable
	 * @param defaultValue
	 *            the default upper bound
	 * @return the upper bound for the variable
	 */
	public int getMaxBound(String variable, int defaultValue) {
		Integer max = maxBounds.get(variable);
		if (max == null) {
			max = defaultValue;
		}
		return max;
	}

	/**
	 * Increment and return the dive counter.
	 * 
	 * @return the incremented value of the dive counter
	 */
	public long getNextDiveCount() {
		return diveCount.incrementAndGet();
	}

	/**
	 * Add a reported dive time to the accumulator that tracks how long the
	 * dives took.
	 * 
	 * @param time
	 *            the time for this dive
	 */
	public void recordDiveTime(long time) {
		diveTime.addAndGet(time);
	}

	/**
	 * Add the first model to the queue of models. This kicks off the analysis
	 * run.
	 * 
	 * @param firstModel
	 *            the very first model to add
	 */
	public void addFirstModel(Model firstModel) {
		try {
			models.put(firstModel);
			work.incrementAndGet();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Add a list of models to the queue.
	 * 
	 * @param mdls
	 *            the list of models
	 */
	public void addModels(List<Model> mdls) {
		mdls.forEach(m -> {
			try {
				models.put(m);
				// log.info(Banner.getBannerLine("added model " + m, '-'));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Return the next available model.
	 * 
	 * @return the model as a variable-value mapping
	 * @throws InterruptedException
	 *             if the task requesting the model was interrupted
	 */
	public Map<String, Constant> getNextModel() throws InterruptedException {
		return models.take().getConcreteValues();
	}

	/**
	 * Add a new entry to the queue of path conditions.
	 * 
	 * @param spc
	 *            the path condition to add
	 */
	public void addPc(SegmentedPC spc) {
		try {
			if (spc == null) {
				pcs.put(SegmentedPC.NULL);
			} else {
				pcs.put(spc);
			}
		} catch (InterruptedException e) {
			// ignore silently
		}
	}

	/**
	 * Return the next available path condition
	 * 
	 * @return the next path condition
	 * @throws InterruptedException
	 *             if the task requesting the model was interrupted
	 */
	public SegmentedPC getNextPc() throws InterruptedException {
		return pcs.take();
	}

	/**
	 * Return the strategy factory.
	 * 
	 * @return the strategy factory
	 */
	public StrategyFactory getStrategyFactory() {
		return strategyFactory;
	}

	/**
	 * Return the strategy manager.
	 * 
	 * @return the strategy manager
	 */
	public StrategyManager getStrategyManager() {
		return strategyManager;
	}

	/**
	 * Create and add a new diver task.
	 */
	public void addDiverTask() {
		futures.add(completionService.submit(new Diver(this)));
		diverTaskCount++;
	}

	/**
	 * Create and add a new strategy task.
	 */
	public void addStrategyTask() {
		futures.add(completionService.submit(new StrategyTask(this)));
		strategyTaskCount++;
	}

	/**
	 * Wait for a change in the status of the workDone flag or until a specified
	 * time has elapsed.
	 * 
	 * @param delay
	 *            time to wait in milliseconds
	 */
	public void idle(long delay) throws InterruptedException {
		if ((System.currentTimeMillis() - startingTime.getTimeInMillis()) / 1000 > timeLimit) {
			log.warn("time limit reached");
			stopWork();
		} else {
			synchronized (workDone) {
				workDone.wait(delay);
			}
		}
	}

	/**
	 * Update the count of outstanding work items by a given amount.
	 * 
	 * @param delta
	 *            how much to add to the number of work items
	 */
	public void updateWork(long delta) {
		long w = work.addAndGet(delta);
		if (w == 0) {
			stopWork();
		}
	}

	/**
	 * Set the flag to indicate that the analysis run must stop.
	 */
	public void stopWork() {
		workDone.set(true);
		synchronized (workDone) {
			workDone.notifyAll();
		}
	}

	/**
	 * Stop the still-executing tasks and the taks manager itself.
	 */
	public void shutdown() {
		futures.forEach(f -> f.cancel(true));
		executor.shutdownNow();
	}

	/**
	 * Start the analysis run, showing all banners by default.
	 */
	public void start() {
		start(true);
	}

	/**
	 * Start the analysis run, and show banners if and only the parameter flag
	 * is true.
	 * 
	 * @param showBanner
	 *            a flag to tell whether or not to show banners
	 */
	public void start(boolean showBanner) {
		startingTime = Calendar.getInstance();
		getBroker().publish("coastal-start", this);
		if (showBanner) {
			new Banner('~').println("COASTAL version " + Version.read()).display(log);
		}
		// Dump the configuration
		config.getKeys().forEachRemaining(k -> log.info("{} = {}", k, config.getString(k)));
		// Now we can start by creating run-level observers
		for (Tuple observer : getObserversPerRun()) {
			ObserverFactory observerFactory = (ObserverFactory) observer.get(0);
			ObserverManager observerManager = (ObserverManager) observer.get(1);
			observerFactory.createObserver(this, observerManager);
		}
		addFirstModel(new Model(0, null));
		try {
			addDiverTask();
			addStrategyTask();
			while (!workDone.get()) {
				idle(500);
				// idle(10000);
				// log.info("TICK-TOCK");
				// TO DO ----> balance the threads
			}
		} catch (InterruptedException e) {
			log.info(Banner.getBannerLine("main thread interrupted", '!'));
		} finally {
			shutdown();
		}
		stoppingTime = Calendar.getInstance();
		getBroker().publish("coastal-stop", this);
		getBroker().publish("coastal-report", this);
		if (diveCount.get() < 2) {
			Banner bn = new Banner('@');
			bn.println("ONLY A SINGLE RUN EXECUTED\n");
			bn.println("CHECK YOUR SETTINGS -- THERE MIGHT BE A PROBLEM SOMEWHERE");
			bn.display(log);
		}
		if (showBanner) {
			new Banner('~').println("COASTAL DONE").display(log);
		}
	}

	public void report(Object object) {
		getBroker().publish("report", new Tuple("COASTAL.start", startingTime));
		getBroker().publish("report", new Tuple("COASTAL.stop", stoppingTime));
		long duration = stoppingTime.getTimeInMillis() - startingTime.getTimeInMillis();
		getBroker().publish("report", new Tuple("COASTAL.time", duration));
		getBroker().publish("report", new Tuple("COASTAL.diver-tasks", diverTaskCount));
		getBroker().publish("report", new Tuple("COASTAL.dive-count", diveCount.get()));
		getBroker().publish("report", new Tuple("COASTAL.dive-time", diveTime.get()));
		getBroker().publish("report", new Tuple("COASTAL.strategy-tasks", strategyTaskCount));
	}

	// ======================================================================
	//
	// MAIN FUNCTION
	//
	// ======================================================================

	/**
	 * The main function and entry point for COASTAL.
	 * 
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {
		final Logger log = LogManager.getLogger("COASTAL");
		new Banner('~').println("COASTAL version " + Version.read()).display(log);
		ImmutableConfiguration config = loadConfiguration(log, args);
		if (config != null) {
			new COASTAL(log, config).start(false);
		}
		new Banner('~').println("COASTAL DONE (" + FilenameUtils.getName(args[0]) + ")").display(log);
	}

	/**
	 * The name of COASTAL configuration file, both the resource (part of the
	 * project, providing sensible defaults), and the user's own configuration
	 * file (providing overriding personalizations).
	 */
	private static final String COASTAL_CONFIGURATION = "coastal.xml";

	/**
	 * The subdirectory in the user's home directory where the personal coastal
	 * file is searched for.
	 */
	private static final String COASTAL_DIRECTORY = ".coastal";

	/**
	 * The user's home directory.
	 */
	private static final String HOME_DIRECTORY = System.getProperty("user.home");

	/**
	 * The full name of the subdirectory where the personal file is searched
	 * for.
	 */
	private static final String HOME_COASTAL_DIRECTORY = HOME_DIRECTORY + File.separator + COASTAL_DIRECTORY;

	/**
	 * The full name of the personal configuration file.
	 */
	private static final String HOME_CONFIGURATION = HOME_COASTAL_DIRECTORY + File.separator + COASTAL_CONFIGURATION;

	/**
	 * Load the COASTAL configuration. Three sources are consulted:
	 * 
	 * <ul>
	 * <li>a project resource</li>
	 * <li>the user's personal configuration file</li>
	 * <li>the configuration file specified on the command line</li>
	 * </ul>
	 * 
	 * The second source overrides the first, and the third source overrides the
	 * first two sources.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param args
	 *            the command-line arguments
	 * @return an immutable configuration
	 */
	public static ImmutableConfiguration loadConfiguration(Logger log, String[] args) {
		return loadConfiguration(log, args, null);
	}

	public static ImmutableConfiguration loadConfiguration(Logger log, String[] args, String extra) {
		CombinedConfiguration config = new CombinedConfiguration();
		Configuration cfg1 = loadConfigFromResource(log, COASTAL_CONFIGURATION);
		if (cfg1 != null) {
			config.addConfiguration(cfg1);
		}
		Configuration cfg2 = loadConfigFromFile(log, HOME_CONFIGURATION);
		if (cfg2 != null) {
			config.addConfiguration(cfg2);
		}
		if (args.length < 1) {
			Banner bn = new Banner('@');
			bn.println("MISSING PROPERTIES FILE\n");
			bn.println("USAGE: coastal <properties file>");
			bn.display(log);
			return null;
		}
		String filename = args[0];
		if (filename.endsWith(".java")) {
			filename = filename.substring(0, filename.length() - 4) + "xml";
		}
		Configuration cfg3 = loadConfigFromFile(log, filename);
		if (cfg3 == null) {
			cfg3 = loadConfigFromResource(log, filename);
		}
		if (cfg3 != null) {
			config.addConfiguration(cfg3);
		} else {
			Banner bn = new Banner('@');
			bn.println("COASTAL PROBLEM\n");
			bn.println("COULD NOT READ CONFIGURATION FILE \"" + filename + "\"");
			bn.display(log);
			return null;
		}
		Configuration cfg4 = loadConfigFromString(log, extra);
		if (cfg4 != null) {
			config.addConfiguration(cfg4);
		}
		if (config.getString("coastal.main") == null) {
			Banner bn = new Banner('@');
			bn.println("SUSPICIOUS PROPERTIES FILE\n");
			bn.println("ARE YOU SURE THAT THE ARGUMENT IS A .xml FILE?");
			bn.display(log);
			return null;
		}
		return ConfigurationUtils.unmodifiableConfiguration(config);
	}

	/**
	 * Load a COASTAL configuration from a file.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param filename
	 *            the name of the file
	 * @return an immutable configuration or {@code null} if the file was not
	 *         found
	 */
	private static Configuration loadConfigFromFile(Logger log, String filename) {
		try {
			InputStream inputStream = new FileInputStream(filename);
			Configuration cfg = loadConfigFromStream(log, inputStream);
			log.trace("loaded configuration from {}", filename);
			return cfg;
		} catch (FileNotFoundException | ConfigurationException x) {
			log.trace("failed to load configuration from {}", filename);
			// We used to do the following, but showing the exception
			// looks pretty ugly in the log.
			// log.trace("tried to load configuration from " + filename + " but failed", x);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from a Java resource.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param resourceName
	 *            the name of the resource
	 * @return an immutable configuration or {@code null} if the resource was
	 *         not found
	 */
	private static Configuration loadConfigFromResource(Logger log, String resourceName) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			if (resourceStream != null) {
				Configuration cfg = loadConfigFromStream(log, resourceStream);
				log.trace("loaded configuration from {}", resourceName);
				return cfg;
			}
		} catch (IOException | ConfigurationException x) {
			log.trace("failed to load configuration from {}", resourceName);
			// We used to do the following, but showing the exception
			// looks pretty ugly in the log.
			// log.trace("tried to load configuration from " + resourceName + " but failed", x);
		}
		return null;
	}

	/**
	 * Load a COASTAL configuration from an input stream.
	 * 
	 * @param log
	 *            the logger to which to report
	 * @param inputStream
	 *            the stream from which to read
	 * @return an immutable configuration
	 * @throws ConfigurationException
	 *             if anything went wrong during the loading of the
	 *             configuration
	 */
	private static Configuration loadConfigFromStream(Logger log, InputStream inputStream)
			throws ConfigurationException {
		XMLConfiguration cfg = new BasicConfigurationBuilder<>(XMLConfiguration.class).configure(new Parameters().xml())
				.getConfiguration();
		FileHandler fh = new FileHandler(cfg);
		fh.load(inputStream);
		return cfg;
	}

	private static Configuration loadConfigFromString(Logger log, String configString) {
		if (configString == null) {
			return null;
		}
		try {
			XMLConfiguration cfg = new BasicConfigurationBuilder<>(XMLConfiguration.class)
					.configure(new Parameters().xml()).getConfiguration();
			FileHandler fh = new FileHandler(cfg);
			String finalString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"
					+ "<!DOCTYPE configuration PUBLIC \"-//DEEPSEA//COASTAL configuration//EN\" "
					+ "\"https://deepseaplatform.github.io/coastal/coastal.dtd\">" + "<configuration>" + configString
					+ "</configuration>";
			fh.load(new ByteArrayInputStream(finalString.getBytes()));
			return cfg;
		} catch (ConfigurationException x) {
			// ignore
		}
		return null;
	}

}
