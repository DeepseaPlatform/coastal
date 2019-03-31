---
title: Visitor
permalink: /api/Visitor/
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
<h2><a class="anchor" name="Visitor"></a>Visitor</h2>
<div markdown="1">
~~~java
public Visitor()
~~~
</div>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
private void preVisit(solver.Expression expression)
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
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
private void postVisit(solver.Expression expression)
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
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.Operation operation)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
operation<br/><span class="paramtype">solver.Operation</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.Operation operation)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
operation<br/><span class="paramtype">solver.Operation</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.RealVariable realVariable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
realVariable<br/><span class="paramtype">solver.RealVariable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.RealVariable realVariable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
realVariable<br/><span class="paramtype">solver.RealVariable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.IntegerVariable integerVariable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
integerVariable<br/><span class="paramtype">solver.IntegerVariable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.IntegerVariable integerVariable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
integerVariable<br/><span class="paramtype">solver.IntegerVariable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.Variable variable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">solver.Variable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.Variable variable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
variable<br/><span class="paramtype">solver.Variable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.RealConstant realConstant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
realConstant<br/><span class="paramtype">solver.RealConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.RealConstant realConstant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
realConstant<br/><span class="paramtype">solver.RealConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
public void preVisit(solver.IntegerConstant integerConstant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
integerConstant<br/><span class="paramtype">solver.IntegerConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.IntegerConstant integerConstant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
integerConstant<br/><span class="paramtype">solver.IntegerConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="preVisit"></a>preVisit</h2>
<div markdown="1">
~~~java
private void preVisit(solver.Constant constant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
constant<br/><span class="paramtype">solver.Constant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
private void postVisit(solver.Constant constant)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
constant<br/><span class="paramtype">solver.Constant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#Visitor">Visitor()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(Constant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(IntegerConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(IntegerVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(Operation)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(RealConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(RealVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#postVisit">postVisit(Variable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(Constant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(IntegerConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(IntegerVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(Operation)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(RealConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(RealVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Visitor/' | relative_url }}#preVisit">preVisit(Variable)</a></li>
</ul>
</li>

</ul>
</section>
