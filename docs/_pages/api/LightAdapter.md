---
title: LightAdapter
permalink: /api/LightAdapter/
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
<h2><a class="anchor" name="ALL_PARAMS_PATTERN"></a>ALL_PARAMS_PATTERN</h2>
<div markdown="1">
~~~java
private static final Pattern ALL_PARAMS_PATTERN
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="PARAMS_PATTERN"></a>PARAMS_PATTERN</h2>
<div markdown="1">
~~~java
private static final Pattern PARAMS_PATTERN
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
private final COASTAL coastal
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="name"></a>name</h2>
<div markdown="1">
~~~java
private final String name
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="swriter"></a>swriter</h2>
<div markdown="1">
~~~java
private final StringWriter swriter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="pwriter"></a>pwriter</h2>
<div markdown="1">
~~~java
private final PrintWriter pwriter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="LightAdapter"></a>LightAdapter</h2>
<div markdown="1">
~~~java
public LightAdapter(COASTAL coastal, String name, org.objectweb.asm.ClassVisitor cv)
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
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
cv<br/><span class="paramtype">org.objectweb.asm.ClassVisitor</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitMethod"></a>visitMethod</h2>
<div markdown="1">
~~~java
public org.objectweb.asm.MethodVisitor visitMethod(int access, String name, String desc, String signature, String exceptions)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
access<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
desc<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
signature<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
exceptions<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="countArguments"></a>countArguments</h2>
<div markdown="1">
~~~java
private static int countArguments(String desc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
desc<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="showInstrumentation"></a>showInstrumentation</h2>
<div markdown="1">
~~~java
public void showInstrumentation()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#ALL_PARAMS_PATTERN">ALL_PARAMS_PATTERN</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#PARAMS_PATTERN">PARAMS_PATTERN</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#name">name</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#pwriter">pwriter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#swriter">swriter</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#LightAdapter">LightAdapter(COASTAL, String, ClassVisitor)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#countArguments">countArguments(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#showInstrumentation">showInstrumentation()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightAdapter/' | relative_url }}#visitMethod">visitMethod(int, String, String, String, String[])</a></li>
</ul>
</li>

</ul>
</section>
