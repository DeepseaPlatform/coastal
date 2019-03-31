---
title: CybridFuzzerFactory.TraceCollection
permalink: /api/CybridFuzzerFactory.TraceCollection/
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
<h2><a class="anchor" name="capacity"></a>capacity</h2>
<div markdown="1">
~~~java
protected final int capacity
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="scores"></a>scores</h2>
<div markdown="1">
~~~java
protected final int scores
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="traces"></a>traces</h2>
<div markdown="1">
~~~java
protected final surfer.Trace traces
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
protected int size
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="TraceCollection"></a>TraceCollection</h2>
<div markdown="1">
~~~java
public TraceCollection(int capacity)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
capacity<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="clear"></a>clear</h2>
<div markdown="1">
~~~java
public void clear()
~~~
</div>
<h2><a class="anchor" name="add"></a>add</h2>
<div markdown="1">
~~~java
public void add(int score, surfer.Trace trace)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
score<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
trace<br/><span class="paramtype">surfer.Trace</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
public int size()
~~~
</div>
<h2><a class="anchor" name="traces"></a>traces</h2>
<div markdown="1">
~~~java
public Iterable traces()
~~~
</div>
<h2><a class="anchor" name="getScore"></a>getScore</h2>
<div markdown="1">
~~~java
public int getScore(int index)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getTrace"></a>getTrace</h2>
<div markdown="1">
~~~java
public surfer.Trace getTrace(int index)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="toString"></a>toString</h2>
<div markdown="1">
~~~java
public String toString()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#capacity">capacity</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#scores">scores</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#size">size</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#traces">traces</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#TraceCollection">TraceCollection(int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#add">add(int, Trace)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#clear">clear()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#getScore">getScore(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#getTrace">getTrace(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#size">size()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#toString">toString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/CybridFuzzerFactory.TraceCollection/' | relative_url }}#traces">traces()</a></li>
</ul>
</li>

</ul>
</section>
