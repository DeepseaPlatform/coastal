---
title: PathTreeNode.PathTreeNodeTerminal
permalink: /api/PathTreeNode.PathTreeNodeTerminal/
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
<h2><a class="anchor" name="execution"></a>execution</h2>
<div markdown="1">
~~~java
private final symbolic.Execution execution
~~~
</div>
<p>
The execution associated with this terminal node.</p>
<h2><a class="anchor" name="PathTreeNodeTerminal"></a>PathTreeNodeTerminal</h2>
<div markdown="1">
~~~java
private PathTreeNodeTerminal(pathtree.PathTreeNode.PathTreeNodeInner parent, long index, symbolic.Execution execution)
~~~
</div>
Create a new terminal node for the path tree.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
parent<br/><span class="paramtype">pathtree.PathTreeNode.PathTreeNodeInner</span></td>
<td>
the parent node</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">long</span></td>
<td>
the index of the leaf relative to its parent</td>
</tr>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getBranch"></a>getBranch</h2>
<div markdown="1">
~~~java
public final symbolic.Branch getBranch()
~~~
</div>
<h2><a class="anchor" name="getPath"></a>getPath</h2>
<div markdown="1">
~~~java
public final symbolic.Path getPath()
~~~
</div>
<h2><a class="anchor" name="getChildCount"></a>getChildCount</h2>
<div markdown="1">
~~~java
public final int getChildCount()
~~~
</div>
<h2><a class="anchor" name="getChild"></a>getChild</h2>
<div markdown="1">
~~~java
public final pathtree.PathTreeNode getChild(long childIndex)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
childIndex<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="isFullyExplored"></a>isFullyExplored</h2>
<div markdown="1">
~~~java
public final boolean isFullyExplored()
~~~
</div>
<h2><a class="anchor" name="setFullyExplored"></a>setFullyExplored</h2>
<div markdown="1">
~~~java
public final void setFullyExplored()
~~~
</div>
<h2><a class="anchor" name="hasBeenGenerated"></a>hasBeenGenerated</h2>
<div markdown="1">
~~~java
public final boolean hasBeenGenerated()
~~~
</div>
<h2><a class="anchor" name="setGenerated"></a>setGenerated</h2>
<div markdown="1">
~~~java
public final void setGenerated()
~~~
</div>
<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
public final void lock()
~~~
</div>
<h2><a class="anchor" name="unlock"></a>unlock</h2>
<div markdown="1">
~~~java
public final void unlock()
~~~
</div>
<h2><a class="anchor" name="getExecution"></a>getExecution</h2>
<div markdown="1">
~~~java
public final symbolic.Execution getExecution()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#execution">execution</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#PathTreeNodeTerminal">PathTreeNodeTerminal(PathTreeNode.PathTreeNodeInner, long, Execution)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#getBranch">getBranch()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#getChild">getChild(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#getChildCount">getChildCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#getExecution">getExecution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#getPath">getPath()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#hasBeenGenerated">hasBeenGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#isFullyExplored">isFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#lock">lock()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#setFullyExplored">setFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#setGenerated">setGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeTerminal/' | relative_url }}#unlock">unlock()</a></li>
</ul>
</li>

</ul>
</section>
