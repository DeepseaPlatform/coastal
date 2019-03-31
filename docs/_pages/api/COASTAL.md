---
title: COASTAL
permalink: /api/COASTAL/
toc: true
---

<section class="sidetoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
<a class="top" href="{{ '/api/' | relative_url }}">API home</a>
</li>
<li class="toc-entry toc-h2">
COASTAL<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal/' | relative_url }}">za.ac.sun.cs.coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.diver/' | relative_url }}">za.ac.sun.cs.coastal.diver</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.instrument/' | relative_url }}">za.ac.sun.cs.coastal.instrument</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.messages/' | relative_url }}">za.ac.sun.cs.coastal.messages</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.model/' | relative_url }}">za.ac.sun.cs.coastal.model</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.observers/' | relative_url }}">za.ac.sun.cs.coastal.observers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.pathtree/' | relative_url }}">za.ac.sun.cs.coastal.pathtree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.solver/' | relative_url }}">za.ac.sun.cs.coastal.solver</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.strategy/' | relative_url }}">za.ac.sun.cs.coastal.strategy</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.strategy.hybrid/' | relative_url }}">za.ac.sun.cs.coastal.strategy.hybrid</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.strategy.pathbased/' | relative_url }}">za.ac.sun.cs.coastal.strategy.pathbased</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.strategy.tracebased/' | relative_url }}">za.ac.sun.cs.coastal.strategy.tracebased</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.surfer/' | relative_url }}">za.ac.sun.cs.coastal.surfer</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.symbolic/' | relative_url }}">za.ac.sun.cs.coastal.symbolic</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.symbolic.exceptions/' | relative_url }}">za.ac.sun.cs.coastal.symbolic.exceptions</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/za.ac.sun.cs.coastal.utility/' | relative_url }}">za.ac.sun.cs.coastal.utility</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Examples<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.bintree/' | relative_url }}">examples.bintree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.fuzzing/' | relative_url }}">examples.fuzzing</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.jcute/' | relative_url }}">examples.jcute</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.kmp/' | relative_url }}">examples.kmp</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.learning/' | relative_url }}">examples.learning</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.remainder/' | relative_url }}">examples.remainder</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.simple/' | relative_url }}">examples.simple</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.statik/' | relative_url }}">examples.statik</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/examples.strings/' | relative_url }}">examples.strings</a></li>
</ul>
</li>
</ul>
</section>
<section class="main class">
<h1>{{ page.title | escape }}</h1>
A COASTAL analysis run. The main function (or some outside client) constructs
 an instance of this class to execute one analysis run of the system.<h2><a class="anchor" name="NUL"></a>NUL</h2>
<div markdown="1">
~~~java
private static final PrintStream NUL
~~~
</div>
<p>
A null <code><a href="{{ '/api/PrintStream/' | relative_url }}">PrintStream</a>
</code> for suppressing output and error.</p>
<h2><a class="anchor" name="EMPTY_PARAMETERS"></a>EMPTY_PARAMETERS</h2>
<div markdown="1">
~~~java
private static final Class EMPTY_PARAMETERS
~~~
</div>
<p>
A convenient constant for use in
 <code><a href="{{ '/api/EMPTY_PARAMETERS/' | relative_url }}#findDelegate(String, String, String)">findDelegate(String, String, String)</a>
</code>. This represents the empty list
 of parameters.</p>
<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
private final Logger log
~~~
</div>
<p>
The logger for this analysis run. This is not created but set by the outside
 world.</p>
<h2><a class="anchor" name="config"></a>config</h2>
<div markdown="1">
~~~java
private final ImmutableConfiguration config
~~~
</div>
<p>
The configuration for this run.</p>
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
private final messages.Broker broker
~~~
</div>
<p>
The single broker that will manage all messages for this analysis run.</p>
<h2><a class="anchor" name="reporter"></a>reporter</h2>
<div markdown="1">
~~~java
private final Reporter reporter
~~~
</div>
<p>
The single reporter that collects information about the analysis run to
 display at the end.</p>
<h2><a class="anchor" name="pathTree"></a>pathTree</h2>
<div markdown="1">
~~~java
private final pathtree.PathTree pathTree
~~~
</div>
<p>
The shared path tree for all strategies, divers, and surfers.</p>
<h2><a class="anchor" name="classManager"></a>classManager</h2>
<div markdown="1">
~~~java
private final instrument.InstrumentationClassManager classManager
~~~
</div>
<p>
The manager of all classes loaded during the analysis run.</p>
<h2><a class="anchor" name="prefixes"></a>prefixes</h2>
<div markdown="1">
~~~java
private final List prefixes
~~~
</div>
<p>
A list of all prefixes of classes that will be instrumented.</p>
<h2><a class="anchor" name="triggers"></a>triggers</h2>
<div markdown="1">
~~~java
private final List triggers
~~~
</div>
<p>
A list of all triggers that switch on symbolic execution.</p>
<h2><a class="anchor" name="parameters"></a>parameters</h2>
<div markdown="1">
~~~java
private final Map parameters
~~~
</div>
<p>
Mapping from parameter names (of all triggers) to their types.</p>
<h2><a class="anchor" name="parameterSize"></a>parameterSize</h2>
<div markdown="1">
~~~java
private final Map parameterSize
~~~
</div>
<p>
Mapping from parameter names (of all triggers) to their sizes (for arrays).</p>
<h2><a class="anchor" name="mainEntrypoint"></a>mainEntrypoint</h2>
<div markdown="1">
~~~java
private Trigger mainEntrypoint
~~~
</div>
<p>
A trigger that describes the entry point. This is (basically) the name of the
 main method and the parameter types.</p>
<h2><a class="anchor" name="mainArguments"></a>mainArguments</h2>
<div markdown="1">
~~~java
private Object mainArguments
~~~
</div>
<p>
Actual values that must be passed as command line parameters.</p>
<h2><a class="anchor" name="defaultMinBounds"></a>defaultMinBounds</h2>
<div markdown="1">
~~~java
private final Map defaultMinBounds
~~~
</div>
<p>
The default minimum bound for various types.</p>
<h2><a class="anchor" name="defaultMaxBounds"></a>defaultMaxBounds</h2>
<div markdown="1">
~~~java
private final Map defaultMaxBounds
~~~
</div>
<p>
The default maximum bound for various types.</p>
<h2><a class="anchor" name="minBounds"></a>minBounds</h2>
<div markdown="1">
~~~java
private final Map minBounds
~~~
</div>
<p>
A map from variable names to their lower bounds.</p>
<h2><a class="anchor" name="maxBounds"></a>maxBounds</h2>
<div markdown="1">
~~~java
private final Map maxBounds
~~~
</div>
<p>
A map from variable names to their lower bounds.</p>
<h2><a class="anchor" name="tasks"></a>tasks</h2>
<div markdown="1">
~~~java
private final List tasks
~~~
</div>
<p>
Collection of task summaries.</p>
<h2><a class="anchor" name="diverManager"></a>diverManager</h2>
<div markdown="1">
~~~java
private diver.DiverFactory.DiverManager diverManager
~~~
</div>
<p>
The manager for all divers. Divers are also included in the task summaries
 (<code><a href="{{ '/api/diverManager/' | relative_url }}#tasks">tasks</a>
</code>), but it is handy to have direct access to their manager.</p>
<h2><a class="anchor" name="surferManager"></a>surferManager</h2>
<div markdown="1">
~~~java
private surfer.SurferFactory.SurferManager surferManager
~~~
</div>
<p>
The manager for all surfers. Surfers are also included in the task summaries
 (<code><a href="{{ '/api/surferManager/' | relative_url }}#tasks">tasks</a>
</code>), but it is handy to have direct access to their manager.</p>
<h2><a class="anchor" name="allObservers"></a>allObservers</h2>
<div markdown="1">
~~~java
private final List allObservers
~~~
</div>
<p>
A list of all observer factories and managers.</p>
<h2><a class="anchor" name="observersPerRun"></a>observersPerRun</h2>
<div markdown="1">
~~~java
private final List observersPerRun
~~~
</div>
<p>
A list of observer factories and managers that must be started once per run.</p>
<h2><a class="anchor" name="observersPerTask"></a>observersPerTask</h2>
<div markdown="1">
~~~java
private final List observersPerTask
~~~
</div>
<p>
A list of observer factories and managers that must be started once per task.</p>
<h2><a class="anchor" name="observersPerDiver"></a>observersPerDiver</h2>
<div markdown="1">
~~~java
private final List observersPerDiver
~~~
</div>
<p>
A list of observer factories and managers that must be started once for each
 diver.</p>
<h2><a class="anchor" name="observersPerSurfer"></a>observersPerSurfer</h2>
<div markdown="1">
~~~java
private final List observersPerSurfer
~~~
</div>
<p>
A list of observer factories and managers that must be started once for each
 surfer.</p>
<h2><a class="anchor" name="delegates"></a>delegates</h2>
<div markdown="1">
~~~java
private final Map delegates
~~~
</div>
<p>
A map from method names to delegate objects (which are instances of the
 modelling classes).</p>
<h2><a class="anchor" name="systemOut"></a>systemOut</h2>
<div markdown="1">
~~~java
private final PrintStream systemOut
~~~
</div>
<p>
The normal standard output.</p>
<h2><a class="anchor" name="systemErr"></a>systemErr</h2>
<div markdown="1">
~~~java
private final PrintStream systemErr
~~~
</div>
<p>
The normal standard error.</p>
<h2><a class="anchor" name="visitedDiverInputs"></a>visitedDiverInputs</h2>
<div markdown="1">
~~~java
protected final Set visitedDiverInputs
~~~
</div>
<p>
Cache of all diver models that have been enqueued.</p>
<h2><a class="anchor" name="diverInputQueue"></a>diverInputQueue</h2>
<div markdown="1">
~~~java
private final BlockingQueue diverInputQueue
~~~
</div>
<p>
A queue of models produced by strategies and consumed by divers.</p>
<h2><a class="anchor" name="visitedSurferInputs"></a>visitedSurferInputs</h2>
<div markdown="1">
~~~java
protected final Set visitedSurferInputs
~~~
</div>
<p>
Cache of all surfer models that have been enqueued.</p>
<h2><a class="anchor" name="surferInputQueue"></a>surferInputQueue</h2>
<div markdown="1">
~~~java
private final BlockingQueue surferInputQueue
~~~
</div>
<p>
A queue of models produced by strategies and consumed by surfers.</p>
<h2><a class="anchor" name="pcQueue"></a>pcQueue</h2>
<div markdown="1">
~~~java
private final BlockingQueue pcQueue
~~~
</div>
<p>
A queue of executions produced by divers and consumed by strategies.</p>
<h2><a class="anchor" name="traceQueue"></a>traceQueue</h2>
<div markdown="1">
~~~java
private final BlockingQueue traceQueue
~~~
</div>
<p>
A queue of executions produced by surfers and consumed by strategies.</p>
<h2><a class="anchor" name="startingTime"></a>startingTime</h2>
<div markdown="1">
~~~java
private Calendar startingTime
~~~
</div>
<p>
The wall-clock-time that the analysis run was started.</p>
<h2><a class="anchor" name="stoppingTime"></a>stoppingTime</h2>
<div markdown="1">
~~~java
private Calendar stoppingTime
~~~
</div>
<p>
The wall-clock-time that the analysis run was stopped.</p>
<h2><a class="anchor" name="nextReportingTime"></a>nextReportingTime</h2>
<div markdown="1">
~~~java
private long nextReportingTime
~~~
</div>
<p>
The number of milliseconds of elapsed time when we next write a console
 update.</p>
<h2><a class="anchor" name="timeLimit"></a>timeLimit</h2>
<div markdown="1">
~~~java
private final long timeLimit
~~~
</div>
<p>
The maximum number of SECONDS the coastal run is allowed to execute.</p>
<h2><a class="anchor" name="maxThreads"></a>maxThreads</h2>
<div markdown="1">
~~~java
private final int maxThreads
~~~
</div>
<p>
The maximum number of threads to use for divers and strategies.</p>
<h2><a class="anchor" name="executor"></a>executor</h2>
<div markdown="1">
~~~java
private final ExecutorService executor
~~~
</div>
<p>
The task manager for concurrent divers and strategies.</p>
<h2><a class="anchor" name="completionService"></a>completionService</h2>
<div markdown="1">
~~~java
private final CompletionService completionService
~~~
</div>
<p>
The task completion manager that collects the "results" from divers and
 strategies. In this case, there are no actual results; instead, all products
 are either consumed or collected by the reporter.</p>
<h2><a class="anchor" name="futures"></a>futures</h2>
<div markdown="1">
~~~java
private final List futures
~~~
</div>
<p>
A list of outstanding tasks.</p>
<h2><a class="anchor" name="work"></a>work</h2>
<div markdown="1">
~~~java
private final AtomicLong work
~~~
</div>
<p>
The outstanding number of models that have not been processed by divers, or
 whose resulting path conditions have not been processed by a strategy. This
 is not merely the number of items in the models queue or the pcs queue: work
 may also be presented by a model of path condition currently being processed.
 The strategy adjusts the work after it has processed a path condition.</p>
<h2><a class="anchor" name="workDone"></a>workDone</h2>
<div markdown="1">
~~~java
private final AtomicBoolean workDone
~~~
</div>
<p>
A flag to indicate that either (1) all work is done, or (2) symbolic
 execution must stop because a "stop" point was reached.</p>
<h2><a class="anchor" name="quietLogging"></a>quietLogging</h2>
<div markdown="1">
~~~java
private static boolean quietLogging
~~~
</div>
<p>
Whether or not logging should be reduced.</p>
<h2><a class="anchor" name="COASTAL(Logger, ImmutableConfiguration)"></a>COASTAL</h2>
<div markdown="1">
~~~java
public COASTAL(Logger log, ImmutableConfiguration config)
~~~
</div>
Initialize the final fields for this analysis run of COASTAL.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to use for this analysis run</td>
</tr>
<tr>
<td>
config<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
the configuration to use for this analysis run</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="parseConfig()"></a>parseConfig</h2>
<div markdown="1">
~~~java
private void parseConfig()
~~~
</div>
Parse the COASTAL configuration and extract the targets, triggers, delegates,
 bounds, and observers.<h2><a class="anchor" name="parseConfigTarget()"></a>parseConfigTarget</h2>
<div markdown="1">
~~~java
private void parseConfigTarget()
~~~
</div>
Parse the COASTAL configuration to extract the target information.<h2><a class="anchor" name="unescape(String)"></a>unescape</h2>
<div markdown="1">
~~~java
public static String unescape(String st)
~~~
</div>
Unescapes a string that contains standard Java escape sequences.
 <ul>
 <li><strong>&#92;b &#92;f &#92;n &#92;r &#92;t &#92;" &#92;'</strong> : BS,
 FF, NL, CR, TAB, double and single quote.</li>
 <li><strong>&#92;X &#92;XX &#92;XXX</strong> : Octal character specification
 (0 - 377, 0x00 - 0xFF).</li>
 <li><strong>&#92;uXXXX</strong> : Hexadecimal based Unicode character.</li>
 </ul><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
st<br/><span class="paramtype">String</span></td>
<td>
A string optionally containing standard java escape sequences.</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
The translated string.</p>
<h2><a class="anchor" name="parseConfigBounds()"></a>parseConfigBounds</h2>
<div markdown="1">
~~~java
private void parseConfigBounds()
~~~
</div>
Parse the COASTAL configuration to extract the type and variable bounds.<h2><a class="anchor" name="addBound(Map<String, Object>, String, String)"></a>addBound</h2>
<div markdown="1">
~~~java
private void addBound(Map bounds, String key, String var)
~~~
</div>
Add a minimum/maximum bounds for a variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
bounds<br/><span class="paramtype">Map</span></td>
<td>
the mapping of variable names to bounds</td>
</tr>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the configuration key that stores the bound</td>
</tr>
<tr>
<td>
var<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="parseConfigStrategies()"></a>parseConfigStrategies</h2>
<div markdown="1">
~~~java
private void parseConfigStrategies()
~~~
</div>
Parse the COASTAL configuration to extract the strategies.<h2><a class="anchor" name="parseConfigObservers()"></a>parseConfigObservers</h2>
<div markdown="1">
~~~java
private void parseConfigObservers()
~~~
</div>
Parse the COASTAL configuration to extract the observers.<h2><a class="anchor" name="parseConfigDelegates()"></a>parseConfigDelegates</h2>
<div markdown="1">
~~~java
private void parseConfigDelegates()
~~~
</div>
Parse the COASTAL configuration to extract the delegates.<h2><a class="anchor" name="getLog()"></a>getLog</h2>
<div markdown="1">
~~~java
public Logger getLog()
~~~
</div>
Return the logger for this run of COASTAL.<h4>Returns</h4>
<p>
the one and only logger for this analysis run</p>
<h2><a class="anchor" name="getConfig()"></a>getConfig</h2>
<div markdown="1">
~~~java
public ImmutableConfiguration getConfig()
~~~
</div>
Return the configuration for this analysis of COASTAL. This configuration is
 immutable.<h4>Returns</h4>
<p>
the configuration</p>
<h2><a class="anchor" name="getBroker()"></a>getBroker</h2>
<div markdown="1">
~~~java
public messages.Broker getBroker()
~~~
</div>
Return the message broker for this analysis run of COASTAL.<h4>Returns</h4>
<p>
the message broker</p>
<h2><a class="anchor" name="getReporter()"></a>getReporter</h2>
<div markdown="1">
~~~java
public Reporter getReporter()
~~~
</div>
Return the reporter for this analysis run of COASTAL. This is used mainly for
 testing purposes.<h4>Returns</h4>
<p>
the reporter</p>
<h2><a class="anchor" name="getPathTree()"></a>getPathTree</h2>
<div markdown="1">
~~~java
public pathtree.PathTree getPathTree()
~~~
</div>
Return the path tree for this analysis run of COASTAL.<h4>Returns</h4>
<p>
the path tree</p>
<h2><a class="anchor" name="getPathTreeReportable()"></a>getPathTreeReportable</h2>
<div markdown="1">
~~~java
public Reporter.Reportable getPathTreeReportable()
~~~
</div>
Return an instance of <code><a href="{{ '/api/Reportable/' | relative_url }}">Reportable</a>
</code> that reports information about the
 path tree.<h4>Returns</h4>
<p>
a reporter for path tree properties</p>
<h2><a class="anchor" name="getCoastalReportable()"></a>getCoastalReportable</h2>
<div markdown="1">
~~~java
public Reporter.Reportable getCoastalReportable()
~~~
</div>
Return an instance of <code><a href="{{ '/api/Reportable/' | relative_url }}">Reportable</a>
</code> that reports information about the
 COASTAL analysis run.<h4>Returns</h4>
<p>
a reporter for analysis run properties</p>
<h2><a class="anchor" name="getStartingTime()"></a>getStartingTime</h2>
<div markdown="1">
~~~java
public long getStartingTime()
~~~
</div>
Return the starting time of the analysis run in milliseconds.<h4>Returns</h4>
<p>
the starting time in milliseconds</p>
<h2><a class="anchor" name="getClassManager()"></a>getClassManager</h2>
<div markdown="1">
~~~java
public instrument.InstrumentationClassManager getClassManager()
~~~
</div>
Return the class manager for this analysis run of COASTAL.<h4>Returns</h4>
<p>
the class manager</p>
<h2><a class="anchor" name="isTarget(String)"></a>isTarget</h2>
<div markdown="1">
~~~java
public boolean isTarget(String potentialTarget)
~~~
</div>
Check is a potential target is an actual target. The potential target is
 simply a class name that is compared to all known targets to see if any are
 prefixes of the potential target.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
potentialTarget<br/><span class="paramtype">String</span></td>
<td>
the name of class</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
true if and only if the potential target is prefixed by a known
         target</p>
<h2><a class="anchor" name="findTrigger(String, String)"></a>findTrigger</h2>
<div markdown="1">
~~~java
public int findTrigger(String name, String signature)
~~~
</div>
Find the index of the trigger with the corresponding name and signature. If
 no such trigger exists, return -1.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the method name of the (potential) trigger</td>
</tr>
<tr>
<td>
signature<br/><span class="paramtype">String</span></td>
<td>
the signature of the (potential) trigger</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the index of the trigger in the list of triggers, or -1</p>
<h2><a class="anchor" name="getTrigger(int)"></a>getTrigger</h2>
<div markdown="1">
~~~java
public Trigger getTrigger(int index)
~~~
</div>
Return the trigger with a specified index.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index we are searching for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the corresponding trigger or <code>null</code> if there is no such trigger</p>
<h2><a class="anchor" name="getParameters()"></a>getParameters</h2>
<div markdown="1">
~~~java
public Map getParameters()
~~~
</div>
Return the mapping of variable names to types. This contains all the symbolic
 parameters mentioned in triggers (as well as any additional symbolic
 variables created during some previous run of the program).<h4>Returns</h4>
<p>
the variable/type mapping</p>
<h2><a class="anchor" name="getParameterSize(String)"></a>getParameterSize</h2>
<div markdown="1">
~~~java
public int getParameterSize(String name)
~~~
</div>
Return the recorded size of the parameter. This is used for arrays.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the name of the parameter</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the recorded size of the parameter or zero</p>
<h2><a class="anchor" name="setParameterSize(String, int)"></a>setParameterSize</h2>
<div markdown="1">
~~~java
public void setParameterSize(String name, int size)
~~~
</div>
Record size of a parameter. This is used for arrays.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the name of the parameter</td>
</tr>
<tr>
<td>
size<br/><span class="paramtype">int</span></td>
<td>
the size of the parameter</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getMainEntrypoint()"></a>getMainEntrypoint</h2>
<div markdown="1">
~~~java
public Trigger getMainEntrypoint()
~~~
</div>
Return the trigger that represents the main entry point for this analysis
 run.<h4>Returns</h4>
<p>
the main entry point</p>
<h2><a class="anchor" name="getMainArguments()"></a>getMainArguments</h2>
<div markdown="1">
~~~java
public Object getMainArguments()
~~~
</div>
Return the actual arguments that should be passed to the main entry point in
 the main class for this run.<h4>Returns</h4>
<p>
the arguments for the main entry point</p>
<h2><a class="anchor" name="findDelegate(String)"></a>findDelegate</h2>
<div markdown="1">
~~~java
public Object findDelegate(String className)
~~~
</div>
Find a delegate for a specified class name.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
the name of the class to search for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the delegate object or <code>null</code> if there is none</p>
<h2><a class="anchor" name="findDelegate(String, String, String)"></a>findDelegate</h2>
<div markdown="1">
~~~java
public Method findDelegate(String owner, String name, String descriptor)
~~~
</div>
Find a delegate method.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
owner<br/><span class="paramtype">String</span></td>
<td>
the method class name</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the method name</td>
</tr>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the method signature</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a Java reflection of the method or <code>null</code> if it was not found</p>
<h2><a class="anchor" name="getObserversPerRun()"></a>getObserversPerRun</h2>
<div markdown="1">
~~~java
public Iterable getObserversPerRun()
~~~
</div>
Return an iterable collection of observers that are flagged to be
 instantiated once for the entire analysis run.<h4>Returns</h4>
<p>
collection of observers started once for the analysis run</p>
<h2><a class="anchor" name="getObserversPerTask()"></a>getObserversPerTask</h2>
<div markdown="1">
~~~java
public Iterable getObserversPerTask()
~~~
</div>
Return an iterable collection of observers that are flagged to be
 instantiated once for each task (strategy), ecluding the divers and surfers.<h4>Returns</h4>
<p>
collection of observers started once for each task</p>
<h2><a class="anchor" name="getObserversPerDiver()"></a>getObserversPerDiver</h2>
<div markdown="1">
~~~java
public Iterable getObserversPerDiver()
~~~
</div>
Return an iterable collection of observers that are flagged to be
 instantiated once for each diver.<h4>Returns</h4>
<p>
collection of observers started once for each diver</p>
<h2><a class="anchor" name="getObserversPerSurfer()"></a>getObserversPerSurfer</h2>
<div markdown="1">
~~~java
public Iterable getObserversPerSurfer()
~~~
</div>
Return an iterable collection of observers that are flagged to be
 instantiated once for each surfer.<h4>Returns</h4>
<p>
collection of observers started once for each surfer</p>
<h2><a class="anchor" name="getAllObservers()"></a>getAllObservers</h2>
<div markdown="1">
~~~java
public Iterable getAllObservers()
~~~
</div>
Return an iterable collection of all loaded observers.<h4>Returns</h4>
<p>
collection of all observers</p>
<h2><a class="anchor" name="getDefaultMinValue(Class<?>)"></a>getDefaultMinValue</h2>
<div markdown="1">
~~~java
public Object getDefaultMinValue(Class type)
~~~
</div>
Return the lower bound for symbolic variables without an explicit bound of
 their own.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the lower bound for symbolic variables</p>
<h2><a class="anchor" name="getMinBound(String, Class<?>)"></a>getMinBound</h2>
<div markdown="1">
~~~java
public Object getMinBound(String variable, Class type)
~~~
</div>
Return the lower bound for the specified symbolic variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the lower bound for the variable</p>
<h2><a class="anchor" name="getMinBound(String, String, Class<?>)"></a>getMinBound</h2>
<div markdown="1">
~~~java
public Object getMinBound(String variable1, String variable2, Class type)
~~~
</div>
Return the lower bound for a specific variable, or -- if there is no explicit
 bound -- for another variable. If there is no explicit bound, the specified
 default value is returned.
 
 This is used for array where the specific variable is the array index and the
 more general variable is the array as a whole.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable1<br/><span class="paramtype">String</span></td>
<td>
the name of the specific variable</td>
</tr>
<tr>
<td>
variable2<br/><span class="paramtype">String</span></td>
<td>
the name of the more general variable</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variables</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the lower bound for either variable</p>
<h2><a class="anchor" name="getMinBound(String, Object)"></a>getMinBound</h2>
<div markdown="1">
~~~java
public Object getMinBound(String variable, Object defaultValue)
~~~
</div>
Return the lower bound for the specified symbolic variable. If there is no
 explicit bound, the specified default value is returned.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
<tr>
<td>
defaultValue<br/><span class="paramtype">Object</span></td>
<td>
the default lower bound</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the lower bound for the variable</p>
<h2><a class="anchor" name="getDefaultMaxValue(Class<?>)"></a>getDefaultMaxValue</h2>
<div markdown="1">
~~~java
public Object getDefaultMaxValue(Class type)
~~~
</div>
Return the upper bound for symbolic variables without an explicit bound of
 their own.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the upper bound for symbolic variables</p>
<h2><a class="anchor" name="getMaxBound(String, Class<?>)"></a>getMaxBound</h2>
<div markdown="1">
~~~java
public Object getMaxBound(String variable, Class type)
~~~
</div>
Return the upper bound for the specified symbolic variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the upper bound for the variable</p>
<h2><a class="anchor" name="getMaxBound(String, String, Class<?>)"></a>getMaxBound</h2>
<div markdown="1">
~~~java
public Object getMaxBound(String variable1, String variable2, Class type)
~~~
</div>
Return the upper bound for a specific variable, or -- if there is no explicit
 bound -- for another variable. If there is no explicit bound, the specified
 default value is returned.
 
 This is used for array where the specific variable is the array index and the
 more general variable is the array as a whole.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable1<br/><span class="paramtype">String</span></td>
<td>
the name of the specific variable</td>
</tr>
<tr>
<td>
variable2<br/><span class="paramtype">String</span></td>
<td>
the name of the more general variable</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
the type of the variables</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the upper bound for either variable</p>
<h2><a class="anchor" name="getMaxBound(String, Object)"></a>getMaxBound</h2>
<div markdown="1">
~~~java
public Object getMaxBound(String variable, Object defaultValue)
~~~
</div>
Return the upper bound for the specified symbolic integer variable. If there
 is no explicit bound, the specified default value is returned.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
<tr>
<td>
defaultValue<br/><span class="paramtype">Object</span></td>
<td>
the default upper bound</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the upper bound for the variable</p>
<h2><a class="anchor" name="getTasks()"></a>getTasks</h2>
<div markdown="1">
~~~java
public List getTasks()
~~~
</div>
Return the collection of task managers (extracted from the task summaries
 <code><a href="{{ '/api/getTasks/' | relative_url }}#tasks">tasks</a>
</code>) as a list.<h4>Returns</h4>
<p>
a list of task managers</p>
<h2><a class="anchor" name="getSystemOut()"></a>getSystemOut</h2>
<div markdown="1">
~~~java
public PrintStream getSystemOut()
~~~
</div>
Return the normal standard output. This is recorded at the start of the run
 and (possibly) set to null to suppress the program's normal output.<h4>Returns</h4>
<p>
the pre-recorded standard output</p>
<h2><a class="anchor" name="getSystemErr()"></a>getSystemErr</h2>
<div markdown="1">
~~~java
public PrintStream getSystemErr()
~~~
</div>
Return the normal standard error. This is recorded at the start of the run
 and (possibly) set to null to suppress the program's normal output.<h4>Returns</h4>
<p>
the pre-recorded standard error</p>
<h2><a class="anchor" name="getDiverModelQueueLength()"></a>getDiverModelQueueLength</h2>
<div markdown="1">
~~~java
public int getDiverModelQueueLength()
~~~
</div>
Return the length of the diver model queue.<h4>Returns</h4>
<p>
the length of the diver model queue</p>
<h2><a class="anchor" name="addDiverInputs(List<Input>)"></a>addDiverInputs</h2>
<div markdown="1">
~~~java
public int addDiverInputs(List inputs)
~~~
</div>
Add a list of models to the diver model queue. Only those models that have
 not been enqueued before are added to the queue.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
inputs<br/><span class="paramtype">List</span></td>
<td>
the list of models to add</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the number of models actually added to the queue</p>
<h2><a class="anchor" name="getNextDiverInput()"></a>getNextDiverInput</h2>
<div markdown="1">
~~~java
public symbolic.Input getNextDiverInput()
~~~
</div>
Return the next available diver model.<h4>Returns</h4>
<p>
the model as a variable-value mapping</p>
<h2><a class="anchor" name="addFirstModel(Input)"></a>addFirstModel</h2>
<div markdown="1">
~~~java
public void addFirstModel(symbolic.Input firstInput)
~~~
</div>
Add the first model to the queue of models. This kicks off the analysis run.
 
 THIS METHOD IS A PART OF THE DESIGN THAT NEEDS TO BE REFACTORED!!<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
firstInput<br/><span class="paramtype">symbolic.Input</span></td>
<td>
the very first model to add</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getSurferModelQueueLength()"></a>getSurferModelQueueLength</h2>
<div markdown="1">
~~~java
public int getSurferModelQueueLength()
~~~
</div>
Return the length of the surfer model queue.<h4>Returns</h4>
<p>
the length of the surfer model queue</p>
<h2><a class="anchor" name="addSurferInputs(List<Input>)"></a>addSurferInputs</h2>
<div markdown="1">
~~~java
public int addSurferInputs(List inputs)
~~~
</div>
Add a list of models to the surfer model queue. Only those models that have
 not been enqueued before are added to the queue.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
inputs<br/><span class="paramtype">List</span></td>
<td>
the list of models to add</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the number of models actually added to the queue</p>
<h2><a class="anchor" name="addSurferModel(Input)"></a>addSurferModel</h2>
<div markdown="1">
~~~java
public boolean addSurferModel(symbolic.Input input)
~~~
</div>
Add a single model to the surfer model queue. The operation will only succeed
 if the model has not been enqueued before.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
the model to add</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
<code>true</code> if the model has been added successfully</p>
<h2><a class="anchor" name="getNextSurferInput()"></a>getNextSurferInput</h2>
<div markdown="1">
~~~java
public symbolic.Input getNextSurferInput()
~~~
</div>
Return the next available surfer model.<h4>Returns</h4>
<p>
the model</p>
<h2><a class="anchor" name="getPcQueueLength()"></a>getPcQueueLength</h2>
<div markdown="1">
~~~java
public int getPcQueueLength()
~~~
</div>
Return the length of the path condition queue.<h4>Returns</h4>
<p>
the length of the path condition queue</p>
<h2><a class="anchor" name="addPc(Execution)"></a>addPc</h2>
<div markdown="1">
~~~java
public void addPc(symbolic.Execution execution)
~~~
</div>
Add a new entry to the diver queue of executions.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution to add</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNextPc()"></a>getNextPc</h2>
<div markdown="1">
~~~java
public symbolic.Execution getNextPc()
~~~
</div>
Return the next available execution produced by a diver.<h4>Returns</h4>
<p>
the next execution</p>
<h2><a class="anchor" name="getTraceQueueLength()"></a>getTraceQueueLength</h2>
<div markdown="1">
~~~java
public int getTraceQueueLength()
~~~
</div>
Return the length of the trace queue.<h4>Returns</h4>
<p>
the length of the trace queue</p>
<h2><a class="anchor" name="addTrace(Execution)"></a>addTrace</h2>
<div markdown="1">
~~~java
public void addTrace(symbolic.Execution execution)
~~~
</div>
Add a new entry to the surfer queue of executions.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution to add</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNextTrace()"></a>getNextTrace</h2>
<div markdown="1">
~~~java
public symbolic.Execution getNextTrace()
~~~
</div>
Return the next available execution produced by a surfer.<h4>Returns</h4>
<p>
the next execution</p>
<h2><a class="anchor" name="getNextTrace(long)"></a>getNextTrace</h2>
<div markdown="1">
~~~java
public symbolic.Execution getNextTrace(long timeout)
~~~
</div>
Return the next available execution produced by a surfer, as long as a
 timeout has not expired. If the timeout expires, return <code>null</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
timeout<br/><span class="paramtype">long</span></td>
<td>
number of milliseconds to wait</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the next execution or <code>null</code></p>
<h2><a class="anchor" name="idle(long)"></a>idle</h2>
<div markdown="1">
~~~java
private void idle(long delay)
~~~
</div>
Wait for a change in the status of the workDone flag or until a specified
 time has elapsed.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
delay<br/><span class="paramtype">long</span></td>
<td>
time to wait in milliseconds</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="updateWork(long)"></a>updateWork</h2>
<div markdown="1">
~~~java
public void updateWork(long delta)
~~~
</div>
Update the count of outstanding work items by a given amount.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
delta<br/><span class="paramtype">long</span></td>
<td>
how much to add to the number of work items</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="stopWork()"></a>stopWork</h2>
<div markdown="1">
~~~java
public void stopWork()
~~~
</div>
Set the flag to indicate that the analysis run must stop.<h2><a class="anchor" name="stopWork(String)"></a>stopWork</h2>
<div markdown="1">
~~~java
public void stopWork(String message)
~~~
</div>
Set the flag to indicate that the analysis run must stop.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
message<br/><span class="paramtype">String</span></td>
<td>
information message to display in the log</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="shutdown()"></a>shutdown</h2>
<div markdown="1">
~~~java
public void shutdown()
~~~
</div>
Stop the still-executing tasks and the thread manager itself.<h2><a class="anchor" name="start()"></a>start</h2>
<div markdown="1">
~~~java
public void start()
~~~
</div>
Start the analysis run, showing all banners by default.<h2><a class="anchor" name="start(boolean)"></a>start</h2>
<div markdown="1">
~~~java
public void start(boolean showBanner)
~~~
</div>
Start the analysis run, and show banners if and only the parameter flag is
 true.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
showBanner<br/><span class="paramtype">boolean</span></td>
<td>
a flag to tell whether or not to show banners</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startTasks()"></a>startTasks</h2>
<div markdown="1">
~~~java
private void startTasks()
~~~
</div>
Start the tasks described by the task summaries <code><a href="{{ '/api/startTasks/' | relative_url }}#tasks">tasks</a>
</code>, as read from
 the configuration.<h2><a class="anchor" name="tick(Object)"></a>tick</h2>
<div markdown="1">
~~~java
private void tick(Object object)
~~~
</div>
Perform periodic reporting to the console.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
dummy object</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="emergencyStop(Object)"></a>emergencyStop</h2>
<div markdown="1">
~~~java
private void emergencyStop(Object object)
~~~
</div>
Execute an emergency stop.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
dummy object</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report(Object)"></a>report</h2>
<div markdown="1">
~~~java
private void report(Object object)
~~~
</div>
Reports some statistics about the analysis run at the end of the run.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
dummy object</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="main(String[])"></a>main</h2>
<div markdown="1">
~~~java
public static void main(String args)
~~~
</div>
The main function and entry point for COASTAL.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
args<br/><span class="paramtype">String</span></td>
<td>
the command-line arguments</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="parseOptions(String[])"></a>parseOptions</h2>
<div markdown="1">
~~~java
private static String parseOptions(String args)
~~~
</div>
Parse the command-line options. Meaningful options are processed, setting
 various internal flags. Unrecognized options are placed in a new array which
 is assumed to contain configuration files.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
args<br/><span class="paramtype">String</span></td>
<td>
the original command-line arguments</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
unprocessed command-line arguments</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#EMPTY_PARAMETERS">EMPTY_PARAMETERS</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#NUL">NUL</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#allObservers">allObservers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#classManager">classManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#completionService">completionService</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#config">config</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#defaultMaxBounds">defaultMaxBounds</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#defaultMinBounds">defaultMinBounds</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#delegates">delegates</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#diverInputQueue">diverInputQueue</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#diverManager">diverManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#executor">executor</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#futures">futures</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#mainArguments">mainArguments</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#mainEntrypoint">mainEntrypoint</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#maxBounds">maxBounds</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#maxThreads">maxThreads</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#minBounds">minBounds</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#nextReportingTime">nextReportingTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#observersPerDiver">observersPerDiver</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#observersPerRun">observersPerRun</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#observersPerSurfer">observersPerSurfer</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#observersPerTask">observersPerTask</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parameters">parameters</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parameterSize">parameterSize</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#pathTree">pathTree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#pcQueue">pcQueue</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#prefixes">prefixes</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#quietLogging">quietLogging</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#reporter">reporter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#startingTime">startingTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#stoppingTime">stoppingTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#surferInputQueue">surferInputQueue</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#surferManager">surferManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#systemErr">systemErr</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#systemOut">systemOut</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#tasks">tasks</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#timeLimit">timeLimit</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#traceQueue">traceQueue</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#triggers">triggers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#visitedDiverInputs">visitedDiverInputs</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#visitedSurferInputs">visitedSurferInputs</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#work">work</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#workDone">workDone</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#COASTAL(Logger, ImmutableConfiguration)">COASTAL(Logger, ImmutableConfiguration)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addBound(Map<String, Object>, String, String)">addBound(Map<String, Object>, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addDiverInputs(List<Input>)">addDiverInputs(List<Input>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addFirstModel(Input)">addFirstModel(Input)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addPc(Execution)">addPc(Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addSurferInputs(List<Input>)">addSurferInputs(List<Input>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addSurferModel(Input)">addSurferModel(Input)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#addTrace(Execution)">addTrace(Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#emergencyStop(Object)">emergencyStop(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#findDelegate(String)">findDelegate(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#findDelegate(String, String, String)">findDelegate(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#findTrigger(String, String)">findTrigger(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getAllObservers()">getAllObservers()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getBroker()">getBroker()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getClassManager()">getClassManager()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getCoastalReportable()">getCoastalReportable()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getConfig()">getConfig()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getDefaultMaxValue(Class<?>)">getDefaultMaxValue(Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getDefaultMinValue(Class<?>)">getDefaultMinValue(Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getDiverModelQueueLength()">getDiverModelQueueLength()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getLog()">getLog()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMainArguments()">getMainArguments()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMainEntrypoint()">getMainEntrypoint()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMaxBound(String, Class<?>)">getMaxBound(String, Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMaxBound(String, Object)">getMaxBound(String, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMaxBound(String, String, Class<?>)">getMaxBound(String, String, Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMinBound(String, Class<?>)">getMinBound(String, Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMinBound(String, Object)">getMinBound(String, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getMinBound(String, String, Class<?>)">getMinBound(String, String, Class<?>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getNextDiverInput()">getNextDiverInput()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getNextPc()">getNextPc()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getNextSurferInput()">getNextSurferInput()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getNextTrace()">getNextTrace()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getNextTrace(long)">getNextTrace(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getObserversPerDiver()">getObserversPerDiver()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getObserversPerRun()">getObserversPerRun()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getObserversPerSurfer()">getObserversPerSurfer()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getObserversPerTask()">getObserversPerTask()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getParameters()">getParameters()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getParameterSize(String)">getParameterSize(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getPathTree()">getPathTree()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getPathTreeReportable()">getPathTreeReportable()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getPcQueueLength()">getPcQueueLength()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getReporter()">getReporter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getStartingTime()">getStartingTime()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getSurferModelQueueLength()">getSurferModelQueueLength()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getSystemErr()">getSystemErr()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getSystemOut()">getSystemOut()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getTasks()">getTasks()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getTraceQueueLength()">getTraceQueueLength()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#getTrigger(int)">getTrigger(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#idle(long)">idle(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#isTarget(String)">isTarget(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#main(String[])">main(String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfig()">parseConfig()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigBounds()">parseConfigBounds()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigDelegates()">parseConfigDelegates()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigObservers()">parseConfigObservers()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigStrategies()">parseConfigStrategies()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigTarget()">parseConfigTarget()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#parseOptions(String[])">parseOptions(String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#report(Object)">report(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#setParameterSize(String, int)">setParameterSize(String, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#shutdown()">shutdown()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#start()">start()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#start(boolean)">start(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#startTasks()">startTasks()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#stopWork()">stopWork()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#stopWork(String)">stopWork(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#tick(Object)">tick(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#unescape(String)">unescape(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL/' | relative_url }}#updateWork(long)">updateWork(long)</a></li>
</ul>
</li>

</ul>
</section>
