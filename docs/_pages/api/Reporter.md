---
title: Reporter
permalink: /api/Reporter/
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
A collector of runtime information and statistics about various aspects of a
 COASTAL analysis run. Such a collector relies on three messages:
 
 <ul>
 <li><code>"coastal-stop"</code> signals to components that it they must publish
 any information they wish, by emitting <code>"report"</code> messages.</li>
 <li><code>"report"</code> signals to this reporter that a piece of information
 should be recorded.</li>
 <li><code>"coastal-report"</code> signals to this reporter that it must display
 all the collected information (to the log file).</li>
 </ul>
 
 <p>
 Once the information has been distributed, collected, and displayed, the
 reporter can also be interrogated to obtain the published information. This
 is the basis of the testing framework.
 </p><h2><a class="anchor" name="DATE_FORMAT"></a>DATE_FORMAT</h2>
<div markdown="1">
~~~java
private static final java.text.DateFormat DATE_FORMAT
~~~
</div>
<p>
A format for all timestamps.</p>
<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
private final Logger log
~~~
</div>
<p>
The COASTAL log.</p>
<h2><a class="anchor" name="stats"></a>stats</h2>
<div markdown="1">
~~~java
private final List stats
~~~
</div>
<p>
All collected information.</p>
<h2><a class="anchor" name="statMap"></a>statMap</h2>
<div markdown="1">
~~~java
private final Map statMap
~~~
</div>
<p>
All collected information in a format that can be interrogated.</p>
<h2><a class="anchor" name="Reporter(COASTAL)"></a>Reporter</h2>
<div markdown="1">
~~~java
public Reporter(COASTAL coastal)
~~~
</div>
Construct a new reporter.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
an instance of COASTAL</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report(Object)"></a>report</h2>
<div markdown="1">
~~~java
private void report(Object object)
~~~
</div>
Display all collected information (to the logger) and also record the
 information in <code><a href="{{ '/api/report/' | relative_url }}#statMap">statMap</a>
</code> so that it can be interrogated.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
dummy message parameter</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getLong(String)"></a>getLong</h2>
<div markdown="1">
~~~java
public long getLong(String key)
~~~
</div>
Interrogate the collected information and return the value that
 corresponds to the given key parameter as a long value.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key to search for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the collected long value associated with the key</p>
<h2><a class="anchor" name="getBool(String)"></a>getBool</h2>
<div markdown="1">
~~~java
public boolean getBool(String key)
~~~
</div>
Interrogate the collected information and return the value that
 corresponds to the given key parameter as a boolean value.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key to search for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the collected boolean value associated with the key</p>
<h2><a class="anchor" name="getString(String)"></a>getString</h2>
<div markdown="1">
~~~java
public String getString(String key)
~~~
</div>
Interrogate the collected information and return the value that
 corresponds to the given key parameter as a string value.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key to search for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the collected string value associated with the key</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#DATE_FORMAT">DATE_FORMAT</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#statMap">statMap</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#stats">stats</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#Reporter(COASTAL)">Reporter(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#getBool(String)">getBool(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#getLong(String)">getLong(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#getString(String)">getString(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Reporter/' | relative_url }}#report(Object)">report(Object)</a></li>
</ul>
</li>

</ul>
</section>
