---
title: LineCoverageFactory.LineCoverageManager
permalink: /api/LineCoverageFactory.LineCoverageManager/
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
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
private final messages.Broker broker
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
<h2><a class="anchor" name="LineCoverageManager(COASTAL)"></a>LineCoverageManager</h2>
<div markdown="1">
~~~java
 LineCoverageManager(COASTAL coastal)
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
</tbody>
</table>
<h2><a class="anchor" name="update(BitSet, BitSet)"></a>update</h2>
<div markdown="1">
~~~java
public synchronized void update(BitSet covered, BitSet potentials)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
covered<br/><span class="paramtype">BitSet</span></td>
<td>
</td>
</tr>
<tr>
<td>
potentials<br/><span class="paramtype">BitSet</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report(Object)"></a>report</h2>
<div markdown="1">
~~~java
public void report(Object object)
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
<h2><a class="anchor" name="getName()"></a>getName</h2>
<div markdown="1">
~~~java
public String getName()
~~~
</div>
<h2><a class="anchor" name="getPropertyNames()"></a>getPropertyNames</h2>
<div markdown="1">
~~~java
public String getPropertyNames()
~~~
</div>
<h2><a class="anchor" name="getPropertyValues()"></a>getPropertyValues</h2>
<div markdown="1">
~~~java
public Object getPropertyValues()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#covered">covered</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#potentials">potentials</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#LineCoverageManager(COASTAL)">LineCoverageManager(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#getName()">getName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#getPropertyNames()">getPropertyNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#getPropertyValues()">getPropertyValues()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#report(Object)">report(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LineCoverageFactory.LineCoverageManager/' | relative_url }}#update(BitSet, BitSet)">update(BitSet, BitSet)</a></li>
</ul>
</li>

</ul>
</section>
