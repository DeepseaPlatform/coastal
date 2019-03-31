---
title: SegmentedPC.Nary
permalink: /api/SegmentedPC.Nary/
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
Representation of an n-ary branch.<h2><a class="anchor" name="expression"></a>expression</h2>
<div markdown="1">
~~~java
private final solver.Expression expression
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="min"></a>min</h2>
<div markdown="1">
~~~java
private final long min
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="max"></a>max</h2>
<div markdown="1">
~~~java
private final long max
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="Nary"></a>Nary</h2>
<div markdown="1">
~~~java
public Nary(solver.Expression expression, long min, long max, solver.Expression passiveConjunct)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expression<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
min<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
passiveConjunct<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNumberOfAlternatives"></a>getNumberOfAlternatives</h2>
<div markdown="1">
~~~java
public long getNumberOfAlternatives()
~~~
</div>
<h2><a class="anchor" name="getAlternativeRepr"></a>getAlternativeRepr</h2>
<div markdown="1">
~~~java
public String getAlternativeRepr(long alternative)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getAlternative"></a>getAlternative</h2>
<div markdown="1">
~~~java
public solver.Expression getAlternative(long alternative)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPCContribution"></a>getPCContribution</h2>
<div markdown="1">
~~~java
public solver.Expression getPCContribution(long alternative)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getExpression"></a>getExpression</h2>
<div markdown="1">
~~~java
public solver.Expression getExpression()
~~~
</div>
<h2><a class="anchor" name="toString0"></a>toString0</h2>
<div markdown="1">
~~~java
protected String toString0()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#expression">expression</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#max">max</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#min">min</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#Nary">Nary(Expression, long, long, Expression)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#getAlternative">getAlternative(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#getAlternativeRepr">getAlternativeRepr(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#getExpression">getExpression()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#getNumberOfAlternatives">getNumberOfAlternatives()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#getPCContribution">getPCContribution(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC.Nary/' | relative_url }}#toString0">toString0()</a></li>
</ul>
</li>

</ul>
</section>
