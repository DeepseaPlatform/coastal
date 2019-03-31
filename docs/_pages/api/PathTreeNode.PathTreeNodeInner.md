---
title: PathTreeNode.PathTreeNodeInner
permalink: /api/PathTreeNode.PathTreeNodeInner/
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
<h2><a class="anchor" name="branch"></a>branch</h2>
<div markdown="1">
~~~java
private symbolic.Branch branch
~~~
</div>
<p>
The branch that led to this node being reach from its parent. Note that this
 may not be part of an actual execution of the program; instead, it could
 represent an infeasible execution. This field is not final, because a node
 may be updated when new information about it becomes available.</p>
<h2><a class="anchor" name="path"></a>path</h2>
<div markdown="1">
~~~java
private symbolic.Path path
~~~
</div>
<p>
The path that leads to this execution. This field is not final, because a
 node may be updated when new information about it becomes available.</p>
<h2><a class="anchor" name="children"></a>children</h2>
<div markdown="1">
~~~java
private final pathtree.PathTreeNode children
~~~
</div>
<p>
The child nodes of this node.</p>
<h2><a class="anchor" name="fullyExplored"></a>fullyExplored</h2>
<div markdown="1">
~~~java
private boolean fullyExplored
~~~
</div>
<p>
Has all executions that pass through this node been fully explored?</p>
<h2><a class="anchor" name="isGenerated"></a>isGenerated</h2>
<div markdown="1">
~~~java
private boolean isGenerated
~~~
</div>
<p>
Flag used for generational search: has the negation of this execution been
 generated?</p>
<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
private final WriteLock lock
~~~
</div>
<p>
A lock used when adding children to this node. Because all changes to the
 path tree are monotone (in other words, we only add children, or change
 fields in one direction), we do not need much locking.</p>
<h2><a class="anchor" name="PathTreeNodeInner"></a>PathTreeNodeInner</h2>
<div markdown="1">
~~~java
private PathTreeNodeInner(pathtree.PathTreeNode.PathTreeNodeInner parent, symbolic.Path path, long index)
~~~
</div>
Create a new path tree node. This constructor is private; public node
 creation routines are found in the <a href="{{ '/api/PathTreeNode/' | relative_url }}">PathTreeNode</a> base class.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
parent<br/><span class="paramtype">pathtree.PathTreeNode.PathTreeNodeInner</span></td>
<td>
the parent of this node</td>
</tr>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the path that led to this node</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">long</span></td>
<td>
the index of this child at its parent</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="PathTreeNodeInner"></a>PathTreeNodeInner</h2>
<div markdown="1">
~~~java
private PathTreeNodeInner(symbolic.Path path)
~~~
</div>
Create a new path tree node that will act as a root for a path tree. This
 constructor is private; public node creation routines are found in the
 <a href="{{ '/api/PathTreeNode/' | relative_url }}">PathTreeNode</a> base class.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the path that led to this node</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getBranch"></a>getBranch</h2>
<div markdown="1">
~~~java
public symbolic.Branch getBranch()
~~~
</div>
<h2><a class="anchor" name="getPath"></a>getPath</h2>
<div markdown="1">
~~~java
public symbolic.Path getPath()
~~~
</div>
<h2><a class="anchor" name="getChildCount"></a>getChildCount</h2>
<div markdown="1">
~~~java
public int getChildCount()
~~~
</div>
<h2><a class="anchor" name="getChild"></a>getChild</h2>
<div markdown="1">
~~~java
public pathtree.PathTreeNode getChild(long childIndex)
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
public boolean isFullyExplored()
~~~
</div>
<h2><a class="anchor" name="setFullyExplored"></a>setFullyExplored</h2>
<div markdown="1">
~~~java
public void setFullyExplored()
~~~
</div>
<h2><a class="anchor" name="hasBeenGenerated"></a>hasBeenGenerated</h2>
<div markdown="1">
~~~java
public boolean hasBeenGenerated()
~~~
</div>
<h2><a class="anchor" name="setGenerated"></a>setGenerated</h2>
<div markdown="1">
~~~java
public void setGenerated()
~~~
</div>
<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
public void lock()
~~~
</div>
<h2><a class="anchor" name="unlock"></a>unlock</h2>
<div markdown="1">
~~~java
public void unlock()
~~~
</div>
<h2><a class="anchor" name="getExecution"></a>getExecution</h2>
<div markdown="1">
~~~java
public symbolic.Execution getExecution()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#branch">branch</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#children">children</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#fullyExplored">fullyExplored</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#isGenerated">isGenerated</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#lock">lock</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#path">path</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#PathTreeNodeInner">PathTreeNodeInner(Path)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#PathTreeNodeInner">PathTreeNodeInner(PathTreeNode.PathTreeNodeInner, Path, long)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#getBranch">getBranch()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#getChild">getChild(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#getChildCount">getChildCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#getExecution">getExecution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#getPath">getPath()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#hasBeenGenerated">hasBeenGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#isFullyExplored">isFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#lock">lock()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#setFullyExplored">setFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#setGenerated">setGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode.PathTreeNodeInner/' | relative_url }}#unlock">unlock()</a></li>
</ul>
</li>

</ul>
</section>
