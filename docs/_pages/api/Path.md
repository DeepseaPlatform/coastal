---
title: Path
permalink: /api/Path/
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
A path is a description of an execution that contains the branches taken
 during the execution.<h2><a class="anchor" name="parent"></a>parent</h2>
<div markdown="1">
~~~java
protected final symbolic.Path parent
~~~
</div>
<p>
The prefix of this path, or <code>null</code> is this is the root of other paths.
 Many paths may have this path as a prefix.</p>
<h2><a class="anchor" name="depth"></a>depth</h2>
<div markdown="1">
~~~java
protected final int depth
~~~
</div>
<p>
The depth of this path. In essence, this is a count of the number of branches
 along the path.</p>
<h2><a class="anchor" name="choice"></a>choice</h2>
<div markdown="1">
~~~java
protected final symbolic.Choice choice
~~~
</div>
<p>
The last choice made on this path.</p>
<h2><a class="anchor" name="pathCondition"></a>pathCondition</h2>
<div markdown="1">
~~~java
protected solver.Expression pathCondition
~~~
</div>
<p>
The computed path condition that is the combination of the last choice's contribution and the path condition of the parent. This
 is only computed when needed.</p>
<h2><a class="anchor" name="signature"></a>signature</h2>
<div markdown="1">
~~~java
protected String signature
~~~
</div>
<p>
The computed signature of the path.  A signature is a short description of the "turns and twists" that the path follows.  It
 is only computed when needed.</p>
<h2><a class="anchor" name="stringRep"></a>stringRep</h2>
<div markdown="1">
~~~java
private String stringRep
~~~
</div>
<p>
String representation of this path.</p>
<h2><a class="anchor" name="Path(Path, Choice)"></a>Path</h2>
<div markdown="1">
~~~java
public Path(symbolic.Path parent, symbolic.Choice choice)
~~~
</div>
Construct a new path.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
parent<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the prefix of the path</td>
</tr>
<tr>
<td>
choice<br/><span class="paramtype">symbolic.Choice</span></td>
<td>
the last choice made on the path</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getParent()"></a>getParent</h2>
<div markdown="1">
~~~java
public symbolic.Path getParent()
~~~
</div>
Return the prefix of this path.<h4>Returns</h4>
<p>
the prefix of this path</p>
<h2><a class="anchor" name="getDepth()"></a>getDepth</h2>
<div markdown="1">
~~~java
public int getDepth()
~~~
</div>
Return the depth (or length) of this path.<h4>Returns</h4>
<p>
the depth of this path</p>
<h2><a class="anchor" name="getChoice()"></a>getChoice</h2>
<div markdown="1">
~~~java
public symbolic.Choice getChoice()
~~~
</div>
Return the last choice of this path.<h4>Returns</h4>
<p>
the last choice of this path</p>
<h2><a class="anchor" name="getPathCondition()"></a>getPathCondition</h2>
<div markdown="1">
~~~java
public solver.Expression getPathCondition()
~~~
</div>
Return the path condition that corresponds to this path.<h4>Returns</h4>
<p>
the path's path condition</p>
<h2><a class="anchor" name="getSignature()"></a>getSignature</h2>
<div markdown="1">
~~~java
public String getSignature()
~~~
</div>
Return the signature that corresponds to this path.<h4>Returns</h4>
<p>
the path's signature</p>
<h2><a class="anchor" name="toString()"></a>toString</h2>
<div markdown="1">
~~~java
public String toString()
~~~
</div>
<h2><a class="anchor" name="toString0()"></a>toString0</h2>
<div markdown="1">
~~~java
private String toString0()
~~~
</div>
Return a string representation of this path.<h4>Returns</h4>
<p>
a string representation of this path</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#choice">choice</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#depth">depth</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#parent">parent</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#pathCondition">pathCondition</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#signature">signature</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#stringRep">stringRep</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#Path(Path, Choice)">Path(Path, Choice)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#getChoice()">getChoice()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#getDepth()">getDepth()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#getParent()">getParent()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#getPathCondition()">getPathCondition()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#getSignature()">getSignature()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#toString()">toString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Path/' | relative_url }}#toString0()">toString0()</a></li>
</ul>
</li>

</ul>
</section>
