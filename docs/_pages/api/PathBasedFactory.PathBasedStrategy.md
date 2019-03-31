---
title: PathBasedFactory.PathBasedStrategy
permalink: /api/PathBasedFactory.PathBasedStrategy/
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
<h2><a class="anchor" name="manager"></a>manager</h2>
<div markdown="1">
~~~java
protected final strategy.pathbased.PathBasedFactory.PathBasedManager manager
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
<h2><a class="anchor" name="solver"></a>solver</h2>
<div markdown="1">
~~~java
protected final solver.Solver solver
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="visitedInputs"></a>visitedInputs</h2>
<div markdown="1">
~~~java
protected final Set visitedInputs
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="PathBasedStrategy"></a>PathBasedStrategy</h2>
<div markdown="1">
~~~java
public PathBasedStrategy(COASTAL coastal, strategy.StrategyFactory.StrategyManager manager)
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
manager<br/><span class="paramtype">strategy.StrategyFactory.StrategyManager</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="call"></a>call</h2>
<div markdown="1">
~~~java
public Void call()
~~~
</div>
<h2><a class="anchor" name="refine"></a>refine</h2>
<div markdown="1">
~~~java
protected List refine(symbolic.Execution execution)
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
<h2><a class="anchor" name="refine0"></a>refine0</h2>
<div markdown="1">
~~~java
protected List refine0(symbolic.Execution execution)
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
<h2><a class="anchor" name="refine1"></a>refine1</h2>
<div markdown="1">
~~~java
protected List refine1()
~~~
</div>
<h2><a class="anchor" name="findNewPath"></a>findNewPath</h2>
<div markdown="1">
~~~java
protected abstract symbolic.Path findNewPath(pathtree.PathTree pathTree)
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
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#manager">manager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#solver">solver</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#visitedInputs">visitedInputs</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#PathBasedStrategy">PathBasedStrategy(COASTAL, StrategyFactory.StrategyManager)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#call">call()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#findNewPath">findNewPath(PathTree)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#refine">refine(Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#refine0">refine0(Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathBasedFactory.PathBasedStrategy/' | relative_url }}#refine1">refine1()</a></li>
</ul>
</li>

</ul>
</section>
