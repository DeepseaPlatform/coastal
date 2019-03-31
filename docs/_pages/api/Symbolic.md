---
title: Symbolic
permalink: /api/Symbolic/
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
A placeholder class that can be used in an SUT (system under test) to interact
 with COASTAL.<h2><a class="anchor" name="Symbolic()"></a>Symbolic</h2>
<div markdown="1">
~~~java
public Symbolic()
~~~
</div>
<h2><a class="anchor" name="stop()"></a>stop</h2>
<div markdown="1">
~~~java
public static void stop()
~~~
</div>
A placeholder method that signals to COASTAL that the analysis run should
 (or could) be terminated. Instrumentation will replace calls of this
 method with calls to <code><a href="{{ '/api/VM/' | relative_url }}#stop()">VM#stop()</a>
</code>. Internally, the latter method
 emits a message on the publish-subscribe system; unless an appropriate
 observer listens for such messages, no other action is taken.<h2><a class="anchor" name="stop(String)"></a>stop</h2>
<div markdown="1">
~~~java
public static void stop(String message)
~~~
</div>
Similar to <code><a href="{{ '/api/stop/' | relative_url }}#stop()">stop()</a>
</code>, but with an additional user specified
 message. Instrumentation will replace calls of this method with calls to
 <code><a href="{{ '/api/VM/' | relative_url }}#stop(String)">VM#stop(String)</a>
</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
message<br/><span class="paramtype">String</span></td>
<td>
a message to justify the action</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(int)"></a>mark</h2>
<div markdown="1">
~~~java
public static void mark(int marker)
~~~
</div>
A placeholder method that signals to COASTAL that a particular execution
 point has been reached. Instrumentation will replace calls of this method
 with calls to <code><a href="{{ '/api/VM/' | relative_url }}#mark(int)">VM#mark(int)</a>
</code>. Internally, the latter method emits a
 message on the publish-subscribe system; unless an appropriate observer
 listens for such messages, no other action is taken.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">int</span></td>
<td>
an integer to identify the execution point</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(String)"></a>mark</h2>
<div markdown="1">
~~~java
public static void mark(String marker)
~~~
</div>
Similar to <code><a href="{{ '/api/mark/' | relative_url }}#mark(int)">mark(int)</a>
</code>, but with a string instead of integer
 marker identifier. Instrumentation will replace calls of this method with
 calls to <code><a href="{{ '/api/VM/' | relative_url }}#mark(int)">VM#mark(int)</a>
</code>. Internally, the latter method emits a
 message on the publish-subscribe system; unless an appropriate observer
 listens for such messages, no other action is taken.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">String</span></td>
<td>
a string to identify the execution point</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="printPC()"></a>printPC</h2>
<div markdown="1">
~~~java
public static void printPC()
~~~
</div>
Quick-and-dirty mehod to display the path condition in the log file.<h2><a class="anchor" name="printPC(String)"></a>printPC</h2>
<div markdown="1">
~~~java
public static void printPC(String label)
~~~
</div>
Quick-and-dirty mehod to display the path condition in the log file.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
label<br/><span class="paramtype">String</span></td>
<td>
a string to identify the PC</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#Symbolic()">Symbolic()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#mark(int)">mark(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#mark(String)">mark(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#printPC()">printPC()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#printPC(String)">printPC(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#stop()">stop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Symbolic/' | relative_url }}#stop(String)">stop(String)</a></li>
</ul>
</li>

</ul>
</section>
