---
title: Trace.TraceIf
permalink: /api/Trace.TraceIf/
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
<h2><a class="anchor" name="value"></a>value</h2>
<div markdown="1">
~~~java
protected final boolean value
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="signature"></a>signature</h2>
<div markdown="1">
~~~java
protected final String signature
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="negated"></a>negated</h2>
<div markdown="1">
~~~java
protected surfer.Trace negated
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="TraceIf"></a>TraceIf</h2>
<div markdown="1">
~~~java
public TraceIf(surfer.Trace parent, String block, boolean value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
parent<br/><span class="paramtype">surfer.Trace</span></td>
<td>
</td>
</tr>
<tr>
<td>
block<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getValue"></a>getValue</h2>
<div markdown="1">
~~~java
public boolean getValue()
~~~
</div>
<h2><a class="anchor" name="getSignature"></a>getSignature</h2>
<div markdown="1">
~~~java
public String getSignature()
~~~
</div>
<h2><a class="anchor" name="getNrOfOutcomes"></a>getNrOfOutcomes</h2>
<div markdown="1">
~~~java
public int getNrOfOutcomes()
~~~
</div>
<h2><a class="anchor" name="getOutcomeIndex"></a>getOutcomeIndex</h2>
<div markdown="1">
~~~java
public int getOutcomeIndex()
~~~
</div>
<h2><a class="anchor" name="getOutcome"></a>getOutcome</h2>
<div markdown="1">
~~~java
public String getOutcome(int index)
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
<h2><a class="anchor" name="getChild"></a>getChild</h2>
<div markdown="1">
~~~java
public surfer.Trace getChild(int index, symbolic.Execution parent)
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
<tr>
<td>
parent<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="negate"></a>negate</h2>
<div markdown="1">
~~~java
public surfer.Trace negate(String block)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
block<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="toString0"></a>toString0</h2>
<div markdown="1">
~~~java
public String toString0()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#negated">negated</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#signature">signature</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#value">value</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#TraceIf">TraceIf(Trace, String, boolean)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getChild">getChild(int, Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getNrOfOutcomes">getNrOfOutcomes()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getOutcome">getOutcome(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getOutcomeIndex">getOutcomeIndex()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getSignature">getSignature()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#getValue">getValue()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#negate">negate(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trace.TraceIf/' | relative_url }}#toString0">toString0()</a></li>
</ul>
</li>

</ul>
</section>
