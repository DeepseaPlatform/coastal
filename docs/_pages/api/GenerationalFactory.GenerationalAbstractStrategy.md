---
title: GenerationalFactory.GenerationalAbstractStrategy
permalink: /api/GenerationalFactory.GenerationalAbstractStrategy/
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
<h2><a class="anchor" name="priorityStart"></a>priorityStart</h2>
<div markdown="1">
~~~java
private final int priorityStart
~~~
</div>
<p>
The priority of the longest generated new path condition.</p>
<h2><a class="anchor" name="priorityDelta"></a>priorityDelta</h2>
<div markdown="1">
~~~java
private final int priorityDelta
~~~
</div>
<p>
The change in priority for shorter and shorter generated path
 conditions.</p>
<h2><a class="anchor" name="GenerationalAbstractStrategy"></a>GenerationalAbstractStrategy</h2>
<div markdown="1">
~~~java
 GenerationalAbstractStrategy(COASTAL coastal, strategy.StrategyFactory.StrategyManager manager)
~~~
</div>
Construct a new task that implements the generational strategy.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
instance of COASTAL</td>
</tr>
<tr>
<td>
manager<br/><span class="paramtype">strategy.StrategyFactory.StrategyManager</span></td>
<td>
a generational task manager</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="refine0"></a>refine0</h2>
<div markdown="1">
~~~java
protected final List refine0(symbolic.Execution execution)
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
</tbody>
</table>
<h2><a class="anchor" name="generateAltPaths"></a>generateAltPaths</h2>
<div markdown="1">
~~~java
protected abstract List generateAltPaths(symbolic.Path path, symbolic.Path pointer, int depth)
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
pointer<br/><span class="paramtype">symbolic.Path</span></td>
<td>
</td>
</tr>
<tr>
<td>
depth<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="findNewPath"></a>findNewPath</h2>
<div markdown="1">
~~~java
public final symbolic.Path findNewPath(pathtree.PathTree pathTree)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
pathTree<br/><span class="paramtype">pathtree.PathTree</span></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#priorityDelta">priorityDelta</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#priorityStart">priorityStart</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#GenerationalAbstractStrategy">GenerationalAbstractStrategy(COASTAL, StrategyFactory.StrategyManager)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#findNewPath">findNewPath(PathTree)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#generateAltPaths">generateAltPaths(Path, Path, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GenerationalFactory.GenerationalAbstractStrategy/' | relative_url }}#refine0">refine0(Execution)</a></li>
</ul>
</li>

</ul>
</section>
