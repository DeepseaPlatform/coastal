---
title: Operation.Operator
permalink: /api/Operation.Operator/
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
<h2><a class="anchor" name="string"></a>string</h2>
<div markdown="1">
~~~java
private final String string
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="maxArity"></a>maxArity</h2>
<div markdown="1">
~~~java
private final int maxArity
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="fix"></a>fix</h2>
<div markdown="1">
~~~java
private final solver.Operation.Fix fix
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="bvop"></a>bvop</h2>
<div markdown="1">
~~~java
private final String bvop
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="fpop"></a>fpop</h2>
<div markdown="1">
~~~java
private final String fpop
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="Operator"></a>Operator</h2>
<div markdown="1">
~~~java
private Operator(String string, int maxArity, solver.Operation.Fix fix, String bvop, String fpop)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
string<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
maxArity<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
fix<br/><span class="paramtype">solver.Operation.Fix</span></td>
<td>
</td>
</tr>
<tr>
<td>
bvop<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
fpop<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="values"></a>values</h2>
<div markdown="1">
~~~java
public static solver.Operation.Operator values()
~~~
</div>
<h2><a class="anchor" name="valueOf"></a>valueOf</h2>
<div markdown="1">
~~~java
public static solver.Operation.Operator valueOf(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="getArity"></a>getArity</h2>
<div markdown="1">
~~~java
public int getArity()
~~~
</div>
<h2><a class="anchor" name="getFix"></a>getFix</h2>
<div markdown="1">
~~~java
public solver.Operation.Fix getFix()
~~~
</div>
<h2><a class="anchor" name="getBVOp"></a>getBVOp</h2>
<div markdown="1">
~~~java
public String getBVOp()
~~~
</div>
<h2><a class="anchor" name="getFPOp"></a>getFPOp</h2>
<div markdown="1">
~~~java
public String getFPOp()
~~~
</div>
<h2><a class="anchor" name="toString"></a>toString</h2>
<div markdown="1">
~~~java
public final String toString()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#bvop">bvop</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#fix">fix</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#fpop">fpop</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#maxArity">maxArity</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#string">string</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#Operator">Operator(String, int, Operation.Fix, String, String)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#getArity">getArity()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#getBVOp">getBVOp()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#getFix">getFix()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#getFPOp">getFPOp()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#toString">toString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#valueOf">valueOf(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation.Operator/' | relative_url }}#values">values()</a></li>
</ul>
</li>

</ul>
</section>
