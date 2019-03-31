---
title: PayloadCarrierImpl
permalink: /api/PayloadCarrierImpl/
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
Implementation of the <code><a href="{{ '/api/PayloadCarrier/' | relative_url }}">PayloadCarrier</a>
</code> interface. Many of the classes
 that implement it do not have superclasses, and can therefore extend this
 implementation.<h2><a class="anchor" name="payload"></a>payload</h2>
<div markdown="1">
~~~java
protected final Map payload
~~~
</div>
<p>
Additional payload information.</p>
<h2><a class="anchor" name="PayloadCarrierImpl()"></a>PayloadCarrierImpl</h2>
<div markdown="1">
~~~java
public PayloadCarrierImpl()
~~~
</div>
<h2><a class="anchor" name="getPayload(String)"></a>getPayload</h2>
<div markdown="1">
~~~java
public Object getPayload(String key)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPayload(String, int)"></a>getPayload</h2>
<div markdown="1">
~~~java
public int getPayload(String key, int defaultValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
defaultValue<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setPayload(String, Object)"></a>setPayload</h2>
<div markdown="1">
~~~java
public void setPayload(String key, Object value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPayload()"></a>getPayload</h2>
<div markdown="1">
~~~java
public Map getPayload()
~~~
</div>
<h2><a class="anchor" name="copyPayload(PayloadCarrier)"></a>copyPayload</h2>
<div markdown="1">
~~~java
public void copyPayload(symbolic.PayloadCarrier carrier)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
carrier<br/><span class="paramtype">symbolic.PayloadCarrier</span></td>
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
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#payload">payload</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#PayloadCarrierImpl()">PayloadCarrierImpl()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#copyPayload(PayloadCarrier)">copyPayload(PayloadCarrier)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#getPayload()">getPayload()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#getPayload(String)">getPayload(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#getPayload(String, int)">getPayload(String, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}#setPayload(String, Object)">setPayload(String, Object)</a></li>
</ul>
</li>

</ul>
</section>
