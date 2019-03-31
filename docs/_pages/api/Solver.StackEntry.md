---
title: Solver.StackEntry
permalink: /api/Solver.StackEntry/
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
<h2><a class="anchor" name="entry"></a>entry</h2>
<div markdown="1">
~~~java
private final String entry
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="type"></a>type</h2>
<div markdown="1">
~~~java
private final Class type
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
private final int size
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="stringRepr"></a>stringRepr</h2>
<div markdown="1">
~~~java
private String stringRepr
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="StackEntry"></a>StackEntry</h2>
<div markdown="1">
~~~java
 StackEntry(String entry, Class type, int size)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
entry<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
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
<h2><a class="anchor" name="getEntry"></a>getEntry</h2>
<div markdown="1">
~~~java
public String getEntry()
~~~
</div>
<h2><a class="anchor" name="getType"></a>getType</h2>
<div markdown="1">
~~~java
public Class getType()
~~~
</div>
<h2><a class="anchor" name="getSize"></a>getSize</h2>
<div markdown="1">
~~~java
public int getSize()
~~~
</div>
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
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#entry">entry</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#size">size</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#stringRepr">stringRepr</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#type">type</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#StackEntry">StackEntry(String, Class<? extends Expression>, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#getEntry">getEntry()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#getSize">getSize()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#getType">getType()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.StackEntry/' | relative_url }}#toString">toString()</a></li>
</ul>
</li>

</ul>
</section>
