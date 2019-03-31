---
title: ConcolicFuzzerFactory.ConcolicFuzzerManager
permalink: /api/ConcolicFuzzerFactory.ConcolicFuzzerManager/
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
<section class="main">
<h1>{{ page.title | escape }}</h1>
<h2><a class="anchor" name="DEFAULT_QUEUE_LIMIT"></a>DEFAULT_QUEUE_LIMIT</h2>
<div markdown="1">
~~~java
private static final int DEFAULT_QUEUE_LIMIT = 10000000
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="NEW_EDGE_SCORE"></a>NEW_EDGE_SCORE</h2>
<div markdown="1">
~~~java
private static final int NEW_EDGE_SCORE = 100
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="NEW_BUCKET_SCORE"></a>NEW_BUCKET_SCORE</h2>
<div markdown="1">
~~~java
private static final int NEW_BUCKET_SCORE = 50
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="PROPERTY_NAMES"></a>PROPERTY_NAMES</h2>
<div markdown="1">
~~~java
private static final String PROPERTY_NAMES
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
protected final COASTAL coastal
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
protected final messages.Broker broker
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="pathTree"></a>pathTree</h2>
<div markdown="1">
~~~java
protected final pathtree.PathTree pathTree
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="queueLimit"></a>queueLimit</h2>
<div markdown="1">
~~~java
protected final int queueLimit
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="randomSeed"></a>randomSeed</h2>
<div markdown="1">
~~~java
protected final long randomSeed
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="attenuation"></a>attenuation</h2>
<div markdown="1">
~~~java
protected final double attenuation
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="mutationCount"></a>mutationCount</h2>
<div markdown="1">
~~~java
protected final int mutationCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="eliminationCount"></a>eliminationCount</h2>
<div markdown="1">
~~~java
protected final int eliminationCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="eliminationRatio"></a>eliminationRatio</h2>
<div markdown="1">
~~~java
protected final double eliminationRatio
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="keepTop"></a>keepTop</h2>
<div markdown="1">
~~~java
protected final int keepTop
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="drawTree"></a>drawTree</h2>
<div markdown="1">
~~~java
protected final boolean drawTree
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="taskCount"></a>taskCount</h2>
<div markdown="1">
~~~java
protected int taskCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="refineCount"></a>refineCount</h2>
<div markdown="1">
~~~java
protected final AtomicLong refineCount
~~~
</div>
<p>
Counter of number of refinements.</p>
<h2><a class="anchor" name="strategyTime"></a>strategyTime</h2>
<div markdown="1">
~~~java
protected final AtomicLong strategyTime
~~~
</div>
<p>
Accumulator of all the refinement times.</p>
<h2><a class="anchor" name="strategyWaitTime"></a>strategyWaitTime</h2>
<div markdown="1">
~~~java
private final AtomicLong strategyWaitTime
~~~
</div>
<p>
Accumulator of all the strategy waiting times.</p>
<h2><a class="anchor" name="strategyWaitCount"></a>strategyWaitCount</h2>
<div markdown="1">
~~~java
private final AtomicLong strategyWaitCount
~~~
</div>
<p>
Counter for the strategy waiting times.</p>
<h2><a class="anchor" name="edgesSeen"></a>edgesSeen</h2>
<div markdown="1">
~~~java
protected final Map edgesSeen
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="ConcolicFuzzerManager"></a>ConcolicFuzzerManager</h2>
<div markdown="1">
~~~java
public ConcolicFuzzerManager(COASTAL coastal, ImmutableConfiguration options)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
</td>
</tr>
<tr>
<td>
options<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPathTree"></a>getPathTree</h2>
<div markdown="1">
~~~java
public pathtree.PathTree getPathTree()
~~~
</div>
<h2><a class="anchor" name="insertPath0"></a>insertPath0</h2>
<div markdown="1">
~~~java
public pathtree.PathTreeNode insertPath0(symbolic.Execution execution, boolean infeasible)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
<tr>
<td>
infeasible<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="insertPath"></a>insertPath</h2>
<div markdown="1">
~~~java
public boolean insertPath(symbolic.Execution execution, boolean infeasible)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
<tr>
<td>
infeasible<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getQueueLimit"></a>getQueueLimit</h2>
<div markdown="1">
~~~java
protected int getQueueLimit()
~~~
</div>
<h2><a class="anchor" name="getRandomSeed"></a>getRandomSeed</h2>
<div markdown="1">
~~~java
protected long getRandomSeed()
~~~
</div>
<h2><a class="anchor" name="getAttenuation"></a>getAttenuation</h2>
<div markdown="1">
~~~java
protected double getAttenuation()
~~~
</div>
<h2><a class="anchor" name="getMutationCount"></a>getMutationCount</h2>
<div markdown="1">
~~~java
protected int getMutationCount()
~~~
</div>
<h2><a class="anchor" name="getEliminationCount"></a>getEliminationCount</h2>
<div markdown="1">
~~~java
protected int getEliminationCount()
~~~
</div>
<h2><a class="anchor" name="getEliminationRatio"></a>getEliminationRatio</h2>
<div markdown="1">
~~~java
protected double getEliminationRatio()
~~~
</div>
<h2><a class="anchor" name="getKeepTop"></a>getKeepTop</h2>
<div markdown="1">
~~~java
protected int getKeepTop()
~~~
</div>
<h2><a class="anchor" name="incrementRefinements"></a>incrementRefinements</h2>
<div markdown="1">
~~~java
public void incrementRefinements()
~~~
</div>
Increment the number of refinements.<h2><a class="anchor" name="recordTime"></a>recordTime</h2>
<div markdown="1">
~~~java
public void recordTime(long time)
~~~
</div>
Add a reported dive time to the accumulator that tracks how long the dives
 took.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the time for this dive</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="recordWaitTime"></a>recordWaitTime</h2>
<div markdown="1">
~~~java
public void recordWaitTime(long time)
~~~
</div>
Add a reported strategy wait time. This is used to determine if it makes
 sense to create additional threads (or destroy them).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the wait time for this strategy</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="incrementTaskCount"></a>incrementTaskCount</h2>
<div markdown="1">
~~~java
protected void incrementTaskCount()
~~~
</div>
<h2><a class="anchor" name="getTaskCount"></a>getTaskCount</h2>
<div markdown="1">
~~~java
protected int getTaskCount()
~~~
</div>
<h2><a class="anchor" name="scoreEdges"></a>scoreEdges</h2>
<div markdown="1">
~~~java
protected synchronized int scoreEdges(Map edges)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
edges<br/><span class="paramtype">Map</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report"></a>report</h2>
<div markdown="1">
~~~java
public void report(Object object)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getName"></a>getName</h2>
<div markdown="1">
~~~java
public String getName()
~~~
</div>
<h2><a class="anchor" name="getPropertyNames"></a>getPropertyNames</h2>
<div markdown="1">
~~~java
public String getPropertyNames()
~~~
</div>
<h2><a class="anchor" name="getPropertyValues"></a>getPropertyValues</h2>
<div markdown="1">
~~~java
public Object getPropertyValues()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#DEFAULT_QUEUE_LIMIT">DEFAULT_QUEUE_LIMIT</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#NEW_BUCKET_SCORE">NEW_BUCKET_SCORE</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#NEW_EDGE_SCORE">NEW_EDGE_SCORE</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#PROPERTY_NAMES">PROPERTY_NAMES</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#attenuation">attenuation</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#drawTree">drawTree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#edgesSeen">edgesSeen</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#eliminationCount">eliminationCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#eliminationRatio">eliminationRatio</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#keepTop">keepTop</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#mutationCount">mutationCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#pathTree">pathTree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#queueLimit">queueLimit</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#randomSeed">randomSeed</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#refineCount">refineCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#strategyTime">strategyTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#strategyWaitCount">strategyWaitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#strategyWaitTime">strategyWaitTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#taskCount">taskCount</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#ConcolicFuzzerManager">ConcolicFuzzerManager(COASTAL, ImmutableConfiguration)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getAttenuation">getAttenuation()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getEliminationCount">getEliminationCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getEliminationRatio">getEliminationRatio()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getKeepTop">getKeepTop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getMutationCount">getMutationCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getName">getName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getPathTree">getPathTree()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getPropertyNames">getPropertyNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getPropertyValues">getPropertyValues()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getQueueLimit">getQueueLimit()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getRandomSeed">getRandomSeed()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#getTaskCount">getTaskCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#incrementRefinements">incrementRefinements()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#incrementTaskCount">incrementTaskCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#insertPath">insertPath(Execution, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#insertPath0">insertPath0(Execution, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#recordTime">recordTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#recordWaitTime">recordWaitTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#report">report(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConcolicFuzzerFactory.ConcolicFuzzerManager/' | relative_url }}#scoreEdges">scoreEdges(Map<String, Integer>)</a></li>
</ul>
</li>

</ul>
</section>
