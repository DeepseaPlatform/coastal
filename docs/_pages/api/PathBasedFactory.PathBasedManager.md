---
title: PathBasedFactory.PathBasedManager
permalink: /api/PathBasedFactory.PathBasedManager/
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
<h2><a class="anchor" name="refineCount"></a>refineCount</h2>
<div markdown="1">
~~~java
protected final AtomicLong refineCount
~~~
</div>
<p>
Counter of number of refinements.</p>
<h2><a class="anchor" name="solverTime"></a>solverTime</h2>
<div markdown="1">
~~~java
protected final AtomicLong solverTime
~~~
</div>
<p>
Accumulator of all the solver times.</p>
<h2><a class="anchor" name="extractionTime"></a>extractionTime</h2>
<div markdown="1">
~~~java
protected final AtomicLong extractionTime
~~~
</div>
<p>
Accumulator of all the model extraction times.</p>
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
<h2><a class="anchor" name="PathBasedManager"></a>PathBasedManager</h2>
<div markdown="1">
~~~java
public PathBasedManager(COASTAL coastal)
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
<h2><a class="anchor" name="insertPath"></a>insertPath</h2>
<div markdown="1">
~~~java
public boolean insertPath(symbolic.Path path, boolean infeasible)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
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
<h2><a class="anchor" name="incrementRefinements"></a>incrementRefinements</h2>
<div markdown="1">
~~~java
public void incrementRefinements()
~~~
</div>
Increment the number of refinements.<h2><a class="anchor" name="recordSolverTime"></a>recordSolverTime</h2>
<div markdown="1">
~~~java
public void recordSolverTime(long time)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="recordExtractionTime"></a>recordExtractionTime</h2>
<div markdown="1">
~~~java
public void recordExtractionTime(long time)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="recordTime"></a>recordTime</h2>
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
<h2><a class="anchor" name="getTaskCount"></a>getTaskCount</h2>
<div markdown="1">
~~~java
protected abstract int getTaskCount()
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
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#PROPERTY_NAMES">PROPERTY_NAMES</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#extractionTime">extractionTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#pathTree">pathTree</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#refineCount">refineCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#solverTime">solverTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#strategyTime">strategyTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#strategyWaitCount">strategyWaitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#strategyWaitTime">strategyWaitTime</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#PathBasedManager">PathBasedManager(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#getPathTree">getPathTree()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#getPropertyNames">getPropertyNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#getPropertyValues">getPropertyValues()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#getTaskCount">getTaskCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#incrementRefinements">incrementRefinements()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#insertPath">insertPath(Execution, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#insertPath">insertPath(Path, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#insertPath0">insertPath0(Execution, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#recordExtractionTime">recordExtractionTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#recordSolverTime">recordSolverTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#recordTime">recordTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#recordWaitTime">recordWaitTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedManager/' | relative_url }}#report">report(Object)</a></li>
</ul>
</li>

</ul>
</section>
