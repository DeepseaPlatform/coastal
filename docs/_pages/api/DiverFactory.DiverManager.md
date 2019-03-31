---
title: DiverFactory.DiverManager
permalink: /api/DiverFactory.DiverManager/
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
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
protected final messages.Broker broker
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="diverCount"></a>diverCount</h2>
<div markdown="1">
~~~java
private final AtomicLong diverCount
~~~
</div>
<p>
Counter for the number of dives undertaken.</p>
<h2><a class="anchor" name="diverTime"></a>diverTime</h2>
<div markdown="1">
~~~java
private final AtomicLong diverTime
~~~
</div>
<p>
Accumulator of all the dive times.</p>
<h2><a class="anchor" name="diverWaitTime"></a>diverWaitTime</h2>
<div markdown="1">
~~~java
private final AtomicLong diverWaitTime
~~~
</div>
<p>
Accumulator of all the dive waiting times.</p>
<h2><a class="anchor" name="diverWaitCount"></a>diverWaitCount</h2>
<div markdown="1">
~~~java
private final AtomicLong diverWaitCount
~~~
</div>
<p>
Counter for the dive waiting times.</p>
<h2><a class="anchor" name="DiverManager"></a>DiverManager</h2>
<div markdown="1">
~~~java
 DiverManager(COASTAL coastal)
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
<h2><a class="anchor" name="getNextDiveCount"></a>getNextDiveCount</h2>
<div markdown="1">
~~~java
public long getNextDiveCount()
~~~
</div>
Increment and return the diver counter.<h4>Returns</h4>
<p>
the incremented value of the diver counter</p>
<h2><a class="anchor" name="getDiveCount"></a>getDiveCount</h2>
<div markdown="1">
~~~java
public long getDiveCount()
~~~
</div>
Return the current value of the diver counter.<h4>Returns</h4>
<p>
the value of the diver counter</p>
<h2><a class="anchor" name="recordTime"></a>recordTime</h2>
<div markdown="1">
~~~java
public void recordTime(long time)
~~~
</div>
Add a reported dive time to the accumulator that tracks how long the
 dives took.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the time for this dive</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="recordWaitTime"></a>recordWaitTime</h2>
<div markdown="1">
~~~java
public void recordWaitTime(long time)
~~~
</div>
Add a reported dive wait time. This is used to determine if it makes
 sense to create additional threads (or destroy them).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the wait time for this dive</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report"></a>report</h2>
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
<h2><a class="anchor" name="getName"></a>getName</h2>
<div markdown="1">
~~~java
public String getName()
~~~
</div>
<h2><a class="anchor" name="getPropertyNames"></a>getPropertyNames</h2>
<div markdown="1">
~~~java
public String getPropertyNames()
~~~
</div>
<h2><a class="anchor" name="getPropertyValues"></a>getPropertyValues</h2>
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
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#diverCount">diverCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#diverTime">diverTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#diverWaitCount">diverWaitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#diverWaitTime">diverWaitTime</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#DiverManager">DiverManager(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#getDiveCount">getDiveCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#getName">getName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#getNextDiveCount">getNextDiveCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#getPropertyNames">getPropertyNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#getPropertyValues">getPropertyValues()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#recordTime">recordTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#recordWaitTime">recordWaitTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/DiverFactory.DiverManager/' | relative_url }}#report">report(Object)</a></li>
</ul>
</li>

</ul>
</section>
