---
title: Execution
permalink: /api/Execution/
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
Summary of a single execution of a system-under-test. The most important
 parts of the execution are:
 
 <ul>
 <li>the path (either a trace or a path condition)</li>
 <li>the input that triggered the execution</li>
 </ul>
 
 Not all executions arise from the system-under-test. COASTAL may construct a
 new path which it wants to explore, but it may be that the path has no model.
 In other words, there are no variable assignments that satisfy the path. In
 this case, the system may construct an "infeasible" execution to insert in
 the path tree.<h2><a class="anchor" name="NULL"></a>NULL</h2>
<div markdown="1">
~~~java
public static final symbolic.Execution NULL
~~~
</div>
<p>
A sentinel execution that is used in situations where <code>null</code> is not
 appropriate.</p>
<h2><a class="anchor" name="path"></a>path</h2>
<div markdown="1">
~~~java
protected final symbolic.Path path
~~~
</div>
<p>
The path that describes the branches taken during the execution.</p>
<h2><a class="anchor" name="input"></a>input</h2>
<div markdown="1">
~~~java
protected final symbolic.Input input
~~~
</div>
<p>
The input that triggered the execution.</p>
<h2><a class="anchor" name="Execution()"></a>Execution</h2>
<div markdown="1">
~~~java
private Execution()
~~~
</div>
Construct the sentinel execution.<h2><a class="anchor" name="Execution(Path, Input)"></a>Execution</h2>
<div markdown="1">
~~~java
public Execution(symbolic.Path path, symbolic.Input input)
~~~
</div>
Construct the execution information.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the path taken by the execution</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
the input that triggered the execution</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getPath()"></a>getPath</h2>
<div markdown="1">
~~~java
public symbolic.Path getPath()
~~~
</div>
Return the path associated with the execution.<h4>Returns</h4>
<p>
the execution path</p>
<h2><a class="anchor" name="getInput()"></a>getInput</h2>
<div markdown="1">
~~~java
public symbolic.Input getInput()
~~~
</div>
Return the input that triggered the execution.<h4>Returns</h4>
<p>
the execution input</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#NULL">NULL</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#input">input</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#path">path</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#Execution()">Execution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#Execution(Path, Input)">Execution(Path, Input)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#getInput()">getInput()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Execution/' | relative_url }}#getPath()">getPath()</a></li>
</ul>
</li>

</ul>
</section>
