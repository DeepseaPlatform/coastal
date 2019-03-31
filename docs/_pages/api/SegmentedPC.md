---
title: SegmentedPC
permalink: /api/SegmentedPC/
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
A class that implements segmented path conditions. It is called "segmented"
 <b>not</b> because it forms part of a chain of conjuncts (or segments), but
 because each link in the chain comprises more than one expression (these are
 the segments).
 
 There are two segments in each link:
 
 <ul>
 <li>a main segment (or "active conjunct") that represents a branch
 encountered during the execution, and</li>
 <li>an auxiliary segment (or "passive conjunct") that represent additional
 constraints on the variables.</li>
 </ul>
 
 Passive conjuncts are generated mainly by delegates.<h2><a class="anchor" name="passiveConjunct"></a>passiveConjunct</h2>
<div markdown="1">
~~~java
private final solver.Expression passiveConjunct
~~~
</div>
<p>
Additional conjuncts that additional constraints on the branch that will not
 be negated.</p>
<h2><a class="anchor" name="SegmentedPC"></a>SegmentedPC</h2>
<div markdown="1">
~~~java
private SegmentedPC(solver.Expression passiveConjunct)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
passiveConjunct<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPassiveConjunct"></a>getPassiveConjunct</h2>
<div markdown="1">
~~~java
protected final solver.Expression getPassiveConjunct()
~~~
</div>
Return the passive conjunct.<h4>Returns</h4>
<p>
passive conjunct</p>
<h2><a class="anchor" name="isConstant"></a>isConstant</h2>
<div markdown="1">
~~~java
public static final boolean isConstant(solver.Expression expression)
~~~
</div>
Check whether or not the given expression is constant (= consists only of
 constant expressions and operators).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expression<br/><span class="paramtype">solver.Expression</span></td>
<td>
the input expression to check</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
<code>true</code> if and only if the expression is a constant</p>
<h2><a class="anchor" name="getExpression"></a>getExpression</h2>
<div markdown="1">
~~~java
public abstract solver.Expression getExpression()
~~~
</div>
Return an expression for the active conjunct (in its positive form). This
 excludes the passive conjunct; callers are only interested in how decisions
 are made at this branching point.<h4>Returns</h4>
<p>
expression for the active conjunct</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC/' | relative_url }}#passiveConjunct">passiveConjunct</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC/' | relative_url }}#SegmentedPC">SegmentedPC(Expression)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC/' | relative_url }}#getExpression">getExpression()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC/' | relative_url }}#getPassiveConjunct">getPassiveConjunct()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SegmentedPC/' | relative_url }}#isConstant">isConstant(Expression)</a></li>
</ul>
</li>

</ul>
</section>
