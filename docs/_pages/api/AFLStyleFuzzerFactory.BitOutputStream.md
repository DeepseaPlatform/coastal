---
title: AFLStyleFuzzerFactory.BitOutputStream
permalink: /api/AFLStyleFuzzerFactory.BitOutputStream/
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
<h2><a class="anchor" name="INITIAL_SIZE"></a>INITIAL_SIZE</h2>
<div markdown="1">
~~~java
private static final int INITIAL_SIZE = 4
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="bytes"></a>bytes</h2>
<div markdown="1">
~~~java
private byte bytes
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="size"></a>size</h2>
<div markdown="1">
~~~java
private int size
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="free"></a>free</h2>
<div markdown="1">
~~~java
private int free
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="freeInByte"></a>freeInByte</h2>
<div markdown="1">
~~~java
private int freeInByte
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="firstFreeByte"></a>firstFreeByte</h2>
<div markdown="1">
~~~java
private int firstFreeByte
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="BitOutputStream"></a>BitOutputStream</h2>
<div markdown="1">
~~~java
public BitOutputStream()
~~~
</div>
<h2><a class="anchor" name="write"></a>write</h2>
<div markdown="1">
~~~java
public void write(long value, int bits)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
bits<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="resize"></a>resize</h2>
<div markdown="1">
~~~java
private void resize()
~~~
</div>
<h2><a class="anchor" name="toByteArray"></a>toByteArray</h2>
<div markdown="1">
~~~java
public byte toByteArray()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#INITIAL_SIZE">INITIAL_SIZE</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#bytes">bytes</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#firstFreeByte">firstFreeByte</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#free">free</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#freeInByte">freeInByte</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#size">size</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#BitOutputStream">BitOutputStream()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#resize">resize()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#toByteArray">toByteArray()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/AFLStyleFuzzerFactory.BitOutputStream/' | relative_url }}#write">write(long, int)</a></li>
</ul>
</li>

</ul>
</section>
