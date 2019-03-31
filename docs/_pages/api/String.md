---
title: String
permalink: /api/String/
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
A model of some operations of <a href="{{ '/api/java.lang.String/' | relative_url }}">java.lang.String</a>. This implementation
 exploits the fact that string instances enjoy a special status in Java and
 are also treated specially by COASTAL. Wherever possible, COASTAL mirrors
 Java string instances with much simpler array-like symbolic values.<h2><a class="anchor" name="MONE"></a>MONE</h2>
<div markdown="1">
~~~java
private static final solver.Expression MONE
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="minChar"></a>minChar</h2>
<div markdown="1">
~~~java
private final int minChar
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="maxChar"></a>maxChar</h2>
<div markdown="1">
~~~java
private final int maxChar
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="String"></a>String</h2>
<div markdown="1">
~~~java
public String(COASTAL coastal, ImmutableConfiguration options)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
</td>
</tr>
<tr>
<td>
options<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="length____I"></a>length____I</h2>
<div markdown="1">
~~~java
public boolean length____I(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="charAt__I__C"></a>charAt__I__C</h2>
<div markdown="1">
~~~java
public boolean charAt__I__C(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="indexOf__C__I"></a>indexOf__C__I</h2>
<div markdown="1">
~~~java
public boolean indexOf__C__I(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="indexOf__CI__I"></a>indexOf__CI__I</h2>
<div markdown="1">
~~~java
public boolean indexOf__CI__I(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="firstOccurrenceGuard"></a>firstOccurrenceGuard</h2>
<div markdown="1">
~~~java
private solver.Expression firstOccurrenceGuard(diver.SymbolicState state, int strAddress, solver.Expression chr, solver.Expression rval, int ofs, int len)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
<tr>
<td>
strAddress<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
chr<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
rval<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
ofs<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
len<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startsWith__Ljava_1lang_1String_2__Z"></a>startsWith__Ljava_1lang_1String_2__Z</h2>
<div markdown="1">
~~~java
public boolean startsWith__Ljava_1lang_1String_2__Z(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startsWith__Ljava_1lang_1String_2I__Z"></a>startsWith__Ljava_1lang_1String_2I__Z</h2>
<div markdown="1">
~~~java
public boolean startsWith__Ljava_1lang_1String_2I__Z(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="endsWith__Ljava_1lang_1String_2__Z"></a>endsWith__Ljava_1lang_1String_2__Z</h2>
<div markdown="1">
~~~java
public boolean endsWith__Ljava_1lang_1String_2__Z(diver.SymbolicState state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="intConstantValue"></a>intConstantValue</h2>
<div markdown="1">
~~~java
private long intConstantValue(solver.Expression expr)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expr<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#MONE">MONE</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#maxChar">maxChar</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#minChar">minChar</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#String">String(COASTAL, ImmutableConfiguration)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#charAt__I__C">charAt__I__C(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#endsWith__Ljava_1lang_1String_2__Z">endsWith__Ljava_1lang_1String_2__Z(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#firstOccurrenceGuard">firstOccurrenceGuard(SymbolicState, int, Expression, Expression, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#indexOf__C__I">indexOf__C__I(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#indexOf__CI__I">indexOf__CI__I(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#intConstantValue">intConstantValue(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#length____I">length____I(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#startsWith__Ljava_1lang_1String_2__Z">startsWith__Ljava_1lang_1String_2__Z(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/String/' | relative_url }}#startsWith__Ljava_1lang_1String_2I__Z">startsWith__Ljava_1lang_1String_2I__Z(SymbolicState)</a></li>
</ul>
</li>

</ul>
</section>
