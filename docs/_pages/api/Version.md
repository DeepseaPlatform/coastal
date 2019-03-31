---
title: Version
permalink: /api/Version/
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
Static methods to compute the current version of COASTAL.<h2><a class="anchor" name="DEFAULT_APP_PROPERTIES"></a>DEFAULT_APP_PROPERTIES</h2>
<div markdown="1">
~~~java
protected static final String DEFAULT_APP_PROPERTIES = "app.properties"
~~~
</div>
<p>
Name of the Java properties file that gradle uses to store the version
 number.</p>
<h2><a class="anchor" name="DEFAULT_VERSION"></a>DEFAULT_VERSION</h2>
<div markdown="1">
~~~java
protected static final String DEFAULT_VERSION = "unspecified"
~~~
</div>
<p>
The version string used when the version cannot be computed in any other
 way.</p>
<h2><a class="anchor" name="version"></a>version</h2>
<div markdown="1">
~~~java
protected static String version
~~~
</div>
<p>
The computed version for this run of COASTAL.</p>
<h2><a class="anchor" name="Version()"></a>Version</h2>
<div markdown="1">
~~~java
public Version()
~~~
</div>
<h2><a class="anchor" name="read()"></a>read</h2>
<div markdown="1">
~~~java
public static String read()
~~~
</div>
Read and return the version.<h4>Returns</h4>
<p>
the current version of COASTAL</p>
<h2><a class="anchor" name="read(String)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(String resourceName)
~~~
</div>
Read the version from the given resource.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
resourceName<br/><span class="paramtype">String</span></td>
<td>
the name of the resource to read</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as specified in the resource</p>
<h2><a class="anchor" name="read(String, boolean)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(String resourceName, boolean tryJgit)
~~~
</div>
Read the version from a given resource or use Jgit if the second
 parameter is true.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
resourceName<br/><span class="paramtype">String</span></td>
<td>
the name of the resource to read</td>
</tr>
<tr>
<td>
tryJgit<br/><span class="paramtype">boolean</span></td>
<td>
whether or not to use Jgit is the resource is not available</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as specified in the resource</p>
<h2><a class="anchor" name="read(InputStream)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(InputStream inputStream)
~~~
</div>
Read the version from the given input stream.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
inputStream<br/><span class="paramtype">InputStream</span></td>
<td>
the input stream to read</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as it appears in the input stream</p>
<h2><a class="anchor" name="read(InputStream, boolean)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(InputStream inputStream, boolean tryJgit)
~~~
</div>
Read the version from the given input stream or use Jgit if the second
 parameter is true.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
inputStream<br/><span class="paramtype">InputStream</span></td>
<td>
the input stream to read</td>
</tr>
<tr>
<td>
tryJgit<br/><span class="paramtype">boolean</span></td>
<td>
whether or not to use Jgit is the resource is not available</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as it appears in the input stream</p>
<h2><a class="anchor" name="read(Properties)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(Properties properties)
~~~
</div>
Read the version from the given Java properties.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
properties<br/><span class="paramtype">Properties</span></td>
<td>
the Java properties object to read</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as it appears in the properties</p>
<h2><a class="anchor" name="read(Properties, boolean)"></a>read</h2>
<div markdown="1">
~~~java
protected static String read(Properties properties, boolean tryJgit)
~~~
</div>
Read the version from the given Java properties or use Jgit if the second
 parameter is true.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
properties<br/><span class="paramtype">Properties</span></td>
<td>
the Java properties object to read</td>
</tr>
<tr>
<td>
tryJgit<br/><span class="paramtype">boolean</span></td>
<td>
whether or not to use Jgit is the resource is not available</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the current version of COASTAL as it appears in the properties</p>
<h2><a class="anchor" name="readJgitVersion()"></a>readJgitVersion</h2>
<div markdown="1">
~~~java
private static String readJgitVersion()
~~~
</div>
Use Jgit to compute the current version of COASTAL.<h4>Returns</h4>
<p>
the current version of COASTAL as computed by Jgit</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#DEFAULT_APP_PROPERTIES">DEFAULT_APP_PROPERTIES</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#DEFAULT_VERSION">DEFAULT_VERSION</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#version">version</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#Version()">Version()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read()">read()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(InputStream)">read(InputStream)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(InputStream, boolean)">read(InputStream, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(Properties)">read(Properties)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(Properties, boolean)">read(Properties, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(String)">read(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#read(String, boolean)">read(String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Version/' | relative_url }}#readJgitVersion()">readJgitVersion()</a></li>
</ul>
</li>

</ul>
</section>
