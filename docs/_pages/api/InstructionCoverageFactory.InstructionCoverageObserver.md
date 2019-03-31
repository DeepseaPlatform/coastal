---
title: InstructionCoverageFactory.InstructionCoverageObserver
permalink: /api/InstructionCoverageFactory.InstructionCoverageObserver/
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
<section class="main class">
<h1>{{ page.title | escape }}</h1>
<h2><a class="anchor" name="UPDATE_FREQUENCY"></a>UPDATE_FREQUENCY</h2>
<div markdown="1">
~~~java
private static final int UPDATE_FREQUENCY = 100
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
private final observers.InstructionCoverageFactory.InstructionCoverageManager manager
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="classManager"></a>classManager</h2>
<div markdown="1">
~~~java
private final instrument.InstrumentationClassManager classManager
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="covered"></a>covered</h2>
<div markdown="1">
~~~java
private final BitSet covered
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="potentials"></a>potentials</h2>
<div markdown="1">
~~~java
private final BitSet potentials
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="updateCounter"></a>updateCounter</h2>
<div markdown="1">
~~~java
private int updateCounter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="InstructionCoverageObserver(COASTAL, ObserverFactory.ObserverManager)"></a>InstructionCoverageObserver</h2>
<div markdown="1">
~~~java
 InstructionCoverageObserver(COASTAL coastal, observers.ObserverFactory.ObserverManager manager)
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
manager<br/><span class="paramtype">observers.ObserverFactory.ObserverManager</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="enterMethod(Object)"></a>enterMethod</h2>
<div markdown="1">
~~~java
public void enterMethod(Object object)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="insn(Object)"></a>insn</h2>
<div markdown="1">
~~~java
public void insn(Object object)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="update(Object)"></a>update</h2>
<div markdown="1">
~~~java
public void update(Object object)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
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
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#UPDATE_FREQUENCY">UPDATE_FREQUENCY</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#classManager">classManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#covered">covered</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#manager">manager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#potentials">potentials</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#updateCounter">updateCounter</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#InstructionCoverageObserver(COASTAL, ObserverFactory.ObserverManager)">InstructionCoverageObserver(COASTAL, ObserverFactory.ObserverManager)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#enterMethod(Object)">enterMethod(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#insn(Object)">insn(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstructionCoverageFactory.InstructionCoverageObserver/' | relative_url }}#update(Object)">update(Object)</a></li>
</ul>
</li>

</ul>
</section>
