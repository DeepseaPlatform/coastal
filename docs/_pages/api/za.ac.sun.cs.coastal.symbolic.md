---
title: za.ac.sun.cs.coastal.symbolic
permalink: /api/za.ac.sun.cs.coastal.symbolic/
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
<section class="main package">
<h1>{{ page.title | escape }}</h1>
<h2>Interfaces</h2>
<table class="classes">
<tbody>
<tr>
<td>
<a href="{{ '/api/PayloadCarrier/' | relative_url }}">PayloadCarrier</a></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2>Classes</h2>
<table class="classes">
<tbody>
<tr>
<td>
<a href="{{ '/api/Branch/' | relative_url }}">Branch</a></td>
<td>
An encapsulation of a potential branching point encountered during an
 execution of the system-under-test.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Choice/' | relative_url }}">Choice</a></td>
<td>
An encapsulation of a branch taken during an execution of the
 system-under-test.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Execution/' | relative_url }}">Execution</a></td>
<td>
Summary of a single execution of a system-under-test.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Input/' | relative_url }}">Input</a></td>
<td>
The inputs to a run of the system-under-test.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/InputMap/' | relative_url }}">InputMap</a></td>
<td>
The inputs to a run of the system-under-test in the form of a mapping from
 variable indices (as <code><a href="{{ '/api/Integer/' | relative_url }}">Integer</a>
</code>s) to variable values (as <code><a href="{{ '/api/Object/' | relative_url }}">Object</a>
</code>s).</td>
</tr>
<tr>
<td>
<a href="{{ '/api/InputVector/' | relative_url }}">InputVector</a></td>
<td>
The inputs to a run of the system-under-test in the form of a mapping from
 variable indices (as <code><a href="{{ '/api/Integer/' | relative_url }}">Integer</a>
</code>s) to variable values (as
 <code><a href="{{ '/api/Object/' | relative_url }}">Object</a>
</code>s).</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Model/' | relative_url }}">Model</a></td>
<td>
An encapsulation of a model (a mapping from symbolic variables to concrete
 values) and their priorities.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Path/' | relative_url }}">Path</a></td>
<td>
A path is a description of an execution that contains the branches taken
 during the execution.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/PayloadCarrierImpl/' | relative_url }}">PayloadCarrierImpl</a></td>
<td>
Implementation of the <code><a href="{{ '/api/PayloadCarrier/' | relative_url }}">PayloadCarrier</a>
</code> interface.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/State/' | relative_url }}">State</a></td>
<td>
The contract that defines the behaviour of an abstract state.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/VM/' | relative_url }}">VM</a></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
