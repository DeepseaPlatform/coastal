---
title: Broker
permalink: /api/Broker/
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
Message broker for COASTAL publish-subscribe implementation.<h2><a class="anchor" name="subscribers"></a>subscribers</h2>
<div markdown="1">
~~~java
private final Map subscribers
~~~
</div>
<p>
Map from topics (strings) to lists of subscribers.</p>
<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
private final ReentrantReadWriteLock lock
~~~
</div>
<p>
A lock to update the map of topics to subscriber lists.</p>
<h2><a class="anchor" name="Broker"></a>Broker</h2>
<div markdown="1">
~~~java
public Broker()
~~~
</div>
<h2><a class="anchor" name="publish"></a>publish</h2>
<div markdown="1">
~~~java
public void publish(String topic, Object message)
~~~
</div>
Publish a message on a certain topic.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
topic<br/><span class="paramtype">String</span></td>
<td>
the topic to publish</td>
</tr>
<tr>
<td>
message<br/><span class="paramtype">Object</span></td>
<td>
the message as a generic object</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="publishThread"></a>publishThread</h2>
<div markdown="1">
~~~java
public void publishThread(String topic, Object message)
~~~
</div>
Publish a message on a certain topic that is local to the current thread.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
topic<br/><span class="paramtype">String</span></td>
<td>
the topic to publish</td>
</tr>
<tr>
<td>
message<br/><span class="paramtype">Object</span></td>
<td>
the message as a generic object</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="subscribe"></a>subscribe</h2>
<div markdown="1">
~~~java
public void subscribe(String topic, Consumer subscriber)
~~~
</div>
Subscribe to a topic.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
topic<br/><span class="paramtype">String</span></td>
<td>
the topic to subscribe to</td>
</tr>
<tr>
<td>
subscriber<br/><span class="paramtype">Consumer</span></td>
<td>
a callback that will be invoked for all new message on the
            topic</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="subscribeThread"></a>subscribeThread</h2>
<div markdown="1">
~~~java
public void subscribeThread(String topic, Consumer subscriber)
~~~
</div>
Subscribe to a topic that is local to the current thread.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
topic<br/><span class="paramtype">String</span></td>
<td>
the topic to subscribe to</td>
</tr>
<tr>
<td>
subscriber<br/><span class="paramtype">Consumer</span></td>
<td>
a callback that will be invoked for all new message on the
            topic</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#lock">lock</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#subscribers">subscribers</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#Broker">Broker()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#publish">publish(String, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#publishThread">publishThread(String, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#subscribe">subscribe(String, Consumer<Object>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Broker/' | relative_url }}#subscribeThread">subscribeThread(String, Consumer<Object>)</a></li>
</ul>
</li>

</ul>
</section>
