---
title: InputMap
permalink: /api/InputMap/
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
The inputs to a run of the system-under-test in the form of a mapping from
 variable indices (as <code><a href="{{ '/api/Integer/' | relative_url }}">Integer</a>
</code>s) to variable values (as <code><a href="{{ '/api/Object/' | relative_url }}">Object</a>
</code>s).<h2><a class="anchor" name="inputMap"></a>inputMap</h2>
<div markdown="1">
~~~java
private final Map inputMap
~~~
</div>
<p>
Mapping from variable indices to variable values.</p>
<h2><a class="anchor" name="InputMap()"></a>InputMap</h2>
<div markdown="1">
~~~java
public InputMap()
~~~
</div>
Construct a new, empty set of inputs.<h2><a class="anchor" name="InputMap(InputMap)"></a>InputMap</h2>
<div markdown="1">
~~~java
public InputMap(symbolic.InputMap inputVector)
~~~
</div>
Construct a new set of inputs based on an existing set.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
inputVector<br/><span class="paramtype">symbolic.InputMap</span></td>
<td>
the older set of inputs</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getSize()"></a>getSize</h2>
<div markdown="1">
~~~java
public int getSize()
~~~
</div>
<h2><a class="anchor" name="get(String)"></a>get</h2>
<div markdown="1">
~~~java
public Object get(String name)
~~~
</div>
Return the variable value associated with a name.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the name of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value of the variable (or <code>null</code>)</p>
<h2><a class="anchor" name="put(String, Object)"></a>put</h2>
<div markdown="1">
~~~java
public Object put(String name, Object value)
~~~
</div>
Associates a new value with a variable name.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the new of the variable</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
the new value of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the old value of the variable (or <code>null</code>)</p>
<h2><a class="anchor" name="getNames()"></a>getNames</h2>
<div markdown="1">
~~~java
public Set getNames()
~~~
</div>
Returns the set of all names that are mapped.<h4>Returns</h4>
<p>
all names with values</p>
<h2><a class="anchor" name="toString()"></a>toString</h2>
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
<a href="{{ '/api/InputMap/' | relative_url }}#inputMap">inputMap</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#InputMap()">InputMap()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#InputMap(InputMap)">InputMap(InputMap)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#get(String)">get(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#getNames()">getNames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#getSize()">getSize()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#put(String, Object)">put(String, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InputMap/' | relative_url }}#toString()">toString()</a></li>
</ul>
</li>

</ul>
</section>
