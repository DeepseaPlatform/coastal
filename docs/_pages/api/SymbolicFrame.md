---
title: SymbolicFrame
permalink: /api/SymbolicFrame/
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
<h2><a class="anchor" name="methodNumber"></a>methodNumber</h2>
<div markdown="1">
~~~java
protected final int methodNumber
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="invokingInstruction"></a>invokingInstruction</h2>
<div markdown="1">
~~~java
protected final int invokingInstruction
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="stack"></a>stack</h2>
<div markdown="1">
~~~java
protected final Stack stack
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="locals"></a>locals</h2>
<div markdown="1">
~~~java
protected final Map locals
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="SymbolicFrame"></a>SymbolicFrame</h2>
<div markdown="1">
~~~java
public SymbolicFrame(int methodNumber, int invokingInstruction)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodNumber<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
invokingInstruction<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getMethodNumber"></a>getMethodNumber</h2>
<div markdown="1">
~~~java
public int getMethodNumber()
~~~
</div>
<h2><a class="anchor" name="getInvokingInstruction"></a>getInvokingInstruction</h2>
<div markdown="1">
~~~java
public int getInvokingInstruction()
~~~
</div>
<h2><a class="anchor" name="isEmpty"></a>isEmpty</h2>
<div markdown="1">
~~~java
public boolean isEmpty()
~~~
</div>
<h2><a class="anchor" name="clear"></a>clear</h2>
<div markdown="1">
~~~java
public void clear()
~~~
</div>
<h2><a class="anchor" name="pop"></a>pop</h2>
<div markdown="1">
~~~java
public solver.Expression pop()
~~~
</div>
<h2><a class="anchor" name="peek"></a>peek</h2>
<div markdown="1">
~~~java
public solver.Expression peek()
~~~
</div>
<h2><a class="anchor" name="peek"></a>peek</h2>
<div markdown="1">
~~~java
public solver.Expression peek(int index)
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
<h2><a class="anchor" name="push"></a>push</h2>
<div markdown="1">
~~~java
public void push(solver.Expression value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
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
<h2><a class="anchor" name="getLocal"></a>getLocal</h2>
<div markdown="1">
~~~java
public solver.Expression getLocal(int index)
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
<h2><a class="anchor" name="setLocal"></a>setLocal</h2>
<div markdown="1">
~~~java
public void setLocal(int index, solver.Expression value)
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
value<br/><span class="paramtype">solver.Expression</span></td>
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
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#invokingInstruction">invokingInstruction</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#locals">locals</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#methodNumber">methodNumber</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#stack">stack</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#SymbolicFrame">SymbolicFrame(int, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#clear">clear()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#getInvokingInstruction">getInvokingInstruction()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#getLocal">getLocal(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#getMethodNumber">getMethodNumber()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#isEmpty">isEmpty()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#peek">peek()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#peek">peek(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#pop">pop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#push">push(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#setLocal">setLocal(int, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#size">size()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicFrame/' | relative_url }}#toString">toString()</a></li>
</ul>
</li>

</ul>
</section>
