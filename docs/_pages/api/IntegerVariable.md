---
title: IntegerVariable
permalink: /api/IntegerVariable/
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
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
protected final int size
~~~
</div>
<p>
The number of bits in this variable: either 32 or 64.</p>
<h2><a class="anchor" name="lowerBound"></a>lowerBound</h2>
<div markdown="1">
~~~java
protected final long lowerBound
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="upperBound"></a>upperBound</h2>
<div markdown="1">
~~~java
protected final long upperBound
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="IntegerVariable"></a>IntegerVariable</h2>
<div markdown="1">
~~~java
public IntegerVariable(String name, int size, long lowerBound, long upperBound)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
size<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
lowerBound<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
upperBound<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getSize"></a>getSize</h2>
<div markdown="1">
~~~java
public int getSize()
~~~
</div>
<h2><a class="anchor" name="getLowerBound"></a>getLowerBound</h2>
<div markdown="1">
~~~java
public long getLowerBound()
~~~
</div>
<h2><a class="anchor" name="getUpperBound"></a>getUpperBound</h2>
<div markdown="1">
~~~java
public long getUpperBound()
~~~
</div>
<h2><a class="anchor" name="accept"></a>accept</h2>
<div markdown="1">
~~~java
public void accept(solver.Visitor visitor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
visitor<br/><span class="paramtype">solver.Visitor</span></td>
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
<a href="{{ '/api/IntegerVariable/' | relative_url }}#lowerBound">lowerBound</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#size">size</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#upperBound">upperBound</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#IntegerVariable">IntegerVariable(String, int, long, long)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#accept">accept(Visitor)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#getLowerBound">getLowerBound()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#getSize">getSize()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/IntegerVariable/' | relative_url }}#getUpperBound">getUpperBound()</a></li>
</ul>
</li>

</ul>
</section>
