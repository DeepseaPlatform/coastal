---
title: SurferFactory.SurferManager
permalink: /api/SurferFactory.SurferManager/
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
<h2><a class="anchor" name="surferCount"></a>surferCount</h2>
<div markdown="1">
~~~java
private final AtomicLong surferCount
~~~
</div>
<p>
Counter for the number of surfs undertaken.</p>
<h2><a class="anchor" name="surferTime"></a>surferTime</h2>
<div markdown="1">
~~~java
private final AtomicLong surferTime
~~~
</div>
<p>
Accumulator of all the surf times.</p>
<h2><a class="anchor" name="surferWaitTime"></a>surferWaitTime</h2>
<div markdown="1">
~~~java
private final AtomicLong surferWaitTime
~~~
</div>
<p>
Accumulator of all the surf waiting times.</p>
<h2><a class="anchor" name="surferWaitCount"></a>surferWaitCount</h2>
<div markdown="1">
~~~java
private final AtomicLong surferWaitCount
~~~
</div>
<p>
Counter for the surf waiting times.</p>
<h2><a class="anchor" name="abortCount"></a>abortCount</h2>
<div markdown="1">
~~~java
private final AtomicLong abortCount
~~~
</div>
<p>
The number of aborted surfs.</p>
<h2><a class="anchor" name="SurferManager"></a>SurferManager</h2>
<div markdown="1">
~~~java
 SurferManager(COASTAL coastal)
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
<h2><a class="anchor" name="getNextSurfCount"></a>getNextSurfCount</h2>
<div markdown="1">
~~~java
public long getNextSurfCount()
~~~
</div>
Increment and return the surfer counter.<h4>Returns</h4>
<p>
the incremented value of the surfer counter</p>
<h2><a class="anchor" name="getSurfCount"></a>getSurfCount</h2>
<div markdown="1">
~~~java
public long getSurfCount()
~~~
</div>
Return the current value of the surfer counter.<h4>Returns</h4>
<p>
the value of the surfer counter</p>
<h2><a class="anchor" name="recordSurferTime"></a>recordSurferTime</h2>
<div markdown="1">
~~~java
public void recordSurferTime(long time)
~~~
</div>
Add a reported surf time to the accumulator that tracks how long the
 surfs took.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the time for this surf</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="recordWaitTime"></a>recordWaitTime</h2>
<div markdown="1">
~~~java
public void recordWaitTime(long time)
~~~
</div>
Add a reported surf wait time. This is used to determine if it makes
 sense to create additional threads (or destroy them).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
the wait time for this surf</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="incrementAbortCount"></a>incrementAbortCount</h2>
<div markdown="1">
~~~java
public long incrementAbortCount()
~~~
</div>
Increment and return the abort counter.<h4>Returns</h4>
<p>
the incremented value of the abort counter</p>
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
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#abortCount">abortCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#surferCount">surferCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#surferTime">surferTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#surferWaitCount">surferWaitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#surferWaitTime">surferWaitTime</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#SurferManager">SurferManager(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#getName">getName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#getNextSurfCount">getNextSurfCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#getPropertyNames">getPropertyNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#getPropertyValues">getPropertyValues()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#getSurfCount">getSurfCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#incrementAbortCount">incrementAbortCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#recordSurferTime">recordSurferTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#recordWaitTime">recordWaitTime(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SurferFactory.SurferManager/' | relative_url }}#report">report(Object)</a></li>
</ul>
</li>

</ul>
</section>
