---
title: Solver.Translator
permalink: /api/Solver.Translator/
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
<h2><a class="anchor" name="stack"></a>stack</h2>
<div markdown="1">
~~~java
private final Stack stack
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="variableDefs"></a>variableDefs</h2>
<div markdown="1">
~~~java
private final List variableDefs
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="variables"></a>variables</h2>
<div markdown="1">
~~~java
private final Map variables
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="addedLcmp"></a>addedLcmp</h2>
<div markdown="1">
~~~java
private boolean addedLcmp
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="addedFcmpl"></a>addedFcmpl</h2>
<div markdown="1">
~~~java
private boolean addedFcmpl
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="addedFcmpg"></a>addedFcmpg</h2>
<div markdown="1">
~~~java
private boolean addedFcmpg
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="addedDcmpl"></a>addedDcmpl</h2>
<div markdown="1">
~~~java
private boolean addedDcmpl
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="addedDcmpg"></a>addedDcmpg</h2>
<div markdown="1">
~~~java
private boolean addedDcmpg
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="Translator"></a>Translator</h2>
<div markdown="1">
~~~java
private Translator()
~~~
</div>
<h2><a class="anchor" name="getVariables"></a>getVariables</h2>
<div markdown="1">
~~~java
public Map getVariables()
~~~
</div>
<h2><a class="anchor" name="getTranslation"></a>getTranslation</h2>
<div markdown="1">
~~~java
public String getTranslation()
~~~
</div>
<h2><a class="anchor" name="join"></a>join</h2>
<div markdown="1">
~~~java
private static String join(Collection s, String delimiter)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
s<br/><span class="paramtype">Collection</span></td>
<td>
</td>
</tr>
<tr>
<td>
delimiter<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.IntegerConstant c)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
c<br/><span class="paramtype">solver.IntegerConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.RealConstant c)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
c<br/><span class="paramtype">solver.RealConstant</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.IntegerVariable v)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
v<br/><span class="paramtype">solver.IntegerVariable</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postVisit"></a>postVisit</h2>
<div markdown="1">
~~~java
public void postVisit(solver.RealVariable v)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
v<br/><span class="paramtype">solver.RealVariable</span></td>
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
<h2><a class="anchor" name="addLcmp"></a>addLcmp</h2>
<div markdown="1">
~~~java
private void addLcmp()
~~~
</div>
<h2><a class="anchor" name="addFcmpl"></a>addFcmpl</h2>
<div markdown="1">
~~~java
private void addFcmpl()
~~~
</div>
<h2><a class="anchor" name="addFcmpg"></a>addFcmpg</h2>
<div markdown="1">
~~~java
private void addFcmpg()
~~~
</div>
<h2><a class="anchor" name="addDcmpl"></a>addDcmpl</h2>
<div markdown="1">
~~~java
private void addDcmpl()
~~~
</div>
<h2><a class="anchor" name="addDcmpg"></a>addDcmpg</h2>
<div markdown="1">
~~~java
private void addDcmpg()
~~~
</div>
<h2><a class="anchor" name="setOperator"></a>setOperator</h2>
<div markdown="1">
~~~java
private String setOperator(solver.Operation.Operator operator, Class type)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
operator<br/><span class="paramtype">solver.Operation.Operator</span></td>
<td>
</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">Class</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="literal"></a>literal</h2>
<div markdown="1">
~~~java
private String literal(long v, int size)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
v<br/><span class="paramtype">long</span></td>
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
<h2><a class="anchor" name="literal"></a>literal</h2>
<div markdown="1">
~~~java
private String literal(double v, int size)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
v<br/><span class="paramtype">double</span></td>
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
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addedDcmpg">addedDcmpg</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addedDcmpl">addedDcmpl</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addedFcmpg">addedFcmpg</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addedFcmpl">addedFcmpl</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addedLcmp">addedLcmp</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#stack">stack</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#variableDefs">variableDefs</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#variables">variables</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#Translator">Translator()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addDcmpg">addDcmpg()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addDcmpl">addDcmpl()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addFcmpg">addFcmpg()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addFcmpl">addFcmpl()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#addLcmp">addLcmp()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#getTranslation">getTranslation()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#getVariables">getVariables()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#join">join(Collection<String>, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#literal">literal(double, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#literal">literal(long, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#postVisit">postVisit(IntegerConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#postVisit">postVisit(IntegerVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#postVisit">postVisit(Operation)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#postVisit">postVisit(RealConstant)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#postVisit">postVisit(RealVariable)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Solver.Translator/' | relative_url }}#setOperator">setOperator(Operation.Operator, Class<? extends Expression>)</a></li>
</ul>
</li>

</ul>
</section>
