---
title: HeavyClassLoader
permalink: /api/HeavyClassLoader/
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
<h2><a class="anchor" name="VM_NAME"></a>VM_NAME</h2>
<div markdown="1">
~~~java
private static final String VM_NAME = "za.ac.sun.cs.coastal.symbolic.VM"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="SYMBOLIC_STATE_NAME"></a>SYMBOLIC_STATE_NAME</h2>
<div markdown="1">
~~~java
private static final String SYMBOLIC_STATE_NAME = "za.ac.sun.cs.coastal.symbolic.SymbolicState"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="STATE_NAME"></a>STATE_NAME</h2>
<div markdown="1">
~~~java
private static final String STATE_NAME = "za.ac.sun.cs.coastal.symbolic.State"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="STATE_FIELD_NAME"></a>STATE_FIELD_NAME</h2>
<div markdown="1">
~~~java
private static final String STATE_FIELD_NAME = "state"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
private final COASTAL coastal
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
private final Logger log
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="manager"></a>manager</h2>
<div markdown="1">
~~~java
private final instrument.InstrumentationClassManager manager
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="symbolicState"></a>symbolicState</h2>
<div markdown="1">
~~~java
private final diver.SymbolicState symbolicState
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="HeavyClassLoader"></a>HeavyClassLoader</h2>
<div markdown="1">
~~~java
public HeavyClassLoader(COASTAL coastal, instrument.InstrumentationClassManager manager, diver.SymbolicState symbolicState)
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
manager<br/><span class="paramtype">instrument.InstrumentationClassManager</span></td>
<td>
</td>
</tr>
<tr>
<td>
symbolicState<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="loadClass"></a>loadClass</h2>
<div markdown="1">
~~~java
public Class loadClass(String name, boolean resolve)
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
resolve<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="loadClass0"></a>loadClass0</h2>
<div markdown="1">
~~~java
public Class loadClass0(String name, boolean resolve)
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
resolve<br/><span class="paramtype">boolean</span></td>
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
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#STATE_FIELD_NAME">STATE_FIELD_NAME</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#STATE_NAME">STATE_NAME</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#SYMBOLIC_STATE_NAME">SYMBOLIC_STATE_NAME</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#VM_NAME">VM_NAME</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#manager">manager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#symbolicState">symbolicState</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#HeavyClassLoader">HeavyClassLoader(COASTAL, InstrumentationClassManager, SymbolicState)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#loadClass">loadClass(String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyClassLoader/' | relative_url }}#loadClass0">loadClass0(String, boolean)</a></li>
</ul>
</li>

</ul>
</section>
