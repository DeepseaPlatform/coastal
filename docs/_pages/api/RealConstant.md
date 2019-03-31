---
title: RealConstant
permalink: /api/RealConstant/
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
<h2><a class="anchor" name="ZERO32"></a>ZERO32</h2>
<div markdown="1">
~~~java
public static final solver.Constant ZERO32
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="ZERO64"></a>ZERO64</h2>
<div markdown="1">
~~~java
public static final solver.Constant ZERO64
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="value"></a>value</h2>
<div markdown="1">
~~~java
private final double value
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
protected final int size
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="RealConstant"></a>RealConstant</h2>
<div markdown="1">
~~~java
public RealConstant(double value, int size)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
<tr>
<td>
size<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getValue"></a>getValue</h2>
<div markdown="1">
~~~java
public double getValue()
~~~
</div>
<h2><a class="anchor" name="getSize"></a>getSize</h2>
<div markdown="1">
~~~java
public int getSize()
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
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#ZERO32">ZERO32</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#ZERO64">ZERO64</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#size">size</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#value">value</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#RealConstant">RealConstant(double, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#accept">accept(Visitor)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#getSize">getSize()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#getValue">getValue()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/RealConstant/' | relative_url }}#toString0">toString0()</a></li>
</ul>
</li>

</ul>
</section>
