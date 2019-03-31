---
title: PathTreeNode
permalink: /api/PathTreeNode/
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
Abstract class for nodes of the path tree. Inner classes define final,
 non-abstract subclasses to represent specific kinds of nodes.<h2><a class="anchor" name="SPACING"></a>SPACING</h2>
<div markdown="1">
~~~java
private static final int SPACING = 2
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="pathTreeNodeCounter"></a>pathTreeNodeCounter</h2>
<div markdown="1">
~~~java
private static int pathTreeNodeCounter
~~~
</div>
<p>
Global counter for path tree nodes.</p>
<h2><a class="anchor" name="id"></a>id</h2>
<div markdown="1">
~~~java
private final int id
~~~
</div>
<p>
The id for this path tree node.</p>
<h2><a class="anchor" name="parent"></a>parent</h2>
<div markdown="1">
~~~java
private final pathtree.PathTreeNode.PathTreeNodeInner parent
~~~
</div>
<p>
The parent of this node.</p>
<h2><a class="anchor" name="PathTreeNode"></a>PathTreeNode</h2>
<div markdown="1">
~~~java
private PathTreeNode(pathtree.PathTreeNode.PathTreeNodeInner parent, long index)
~~~
</div>
Create a new node for the path tree.<h4>Parameters</h4>
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
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createChild"></a>createChild</h2>
<div markdown="1">
~~~java
public final pathtree.PathTreeNode createChild(symbolic.Path path, long index)
~~~
</div>
Create a child node for this node.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the path associated with the child node</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">long</span></td>
<td>
the index of the child node of its parent</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the node child node</p>
<h2><a class="anchor" name="createLeaf"></a>createLeaf</h2>
<div markdown="1">
~~~java
public final pathtree.PathTreeNode createLeaf(long index, symbolic.Execution execution)
~~~
</div>
Create a leaf child node for this node.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">long</span></td>
<td>
the index of the child node of its parent</td>
</tr>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution that corresponds to this node</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the node child node</p>
<h2><a class="anchor" name="createInfeasible"></a>createInfeasible</h2>
<div markdown="1">
~~~java
public final pathtree.PathTreeNode createInfeasible(long index, symbolic.Execution execution)
~~~
</div>
Create an infeasible child node for this node.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">long</span></td>
<td>
the index of the child node of its parent</td>
</tr>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution that corresponds to this node</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the node child node</p>
<h2><a class="anchor" name="createRoot"></a>createRoot</h2>
<div markdown="1">
~~~java
public static final pathtree.PathTreeNode createRoot(symbolic.Path path)
~~~
</div>
Create a root node for a path tree. This node will have a <code>null</code>
 parent, and the index associated with the node is 0, even though it has no
 meaning.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the path associated with this node</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the new root node</p>
<h2><a class="anchor" name="getId"></a>getId</h2>
<div markdown="1">
~~~java
public final int getId()
~~~
</div>
Return the identifier of this node.<h4>Returns</h4>
<p>
the node identifier</p>
<h2><a class="anchor" name="getBranch"></a>getBranch</h2>
<div markdown="1">
~~~java
public abstract symbolic.Branch getBranch()
~~~
</div>
Return the branch associated with this node. If the node is a leaf or is
 infeasible, return <code>null</code>.<h4>Returns</h4>
<p>
the branch associated with this node or <code>null</code></p>
<h2><a class="anchor" name="getPath"></a>getPath</h2>
<div markdown="1">
~~~java
public abstract symbolic.Path getPath()
~~~
</div>
Return the path associated with this node. If the node is a leaf or is
 infeasible, return <code>null</code>.<h4>Returns</h4>
<p>
the path associated with this node or <code>null</code></p>
<h2><a class="anchor" name="getChildCount"></a>getChildCount</h2>
<div markdown="1">
~~~java
public abstract int getChildCount()
~~~
</div>
Return the number of children for this path tree node. If the node is a leaf
 or is infeasible, return 0.<h4>Returns</h4>
<p>
the number of children of this node</p>
<h2><a class="anchor" name="getChild"></a>getChild</h2>
<div markdown="1">
~~~java
public abstract pathtree.PathTreeNode getChild(long childIndex)
~~~
</div>
Return a given child node of this path tree node. If the node is a leaf or is
 infeasible, always return <code>null</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
childIndex<br/><span class="paramtype">long</span></td>
<td>
the number of the child</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the given child (or <code>null</code> if there is no such child</p>
<h2><a class="anchor" name="getPathForChild"></a>getPathForChild</h2>
<div markdown="1">
~~~java
public final symbolic.Path getPathForChild(long childIndex)
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
<h2><a class="anchor" name="getParent"></a>getParent</h2>
<div markdown="1">
~~~java
public final pathtree.PathTreeNode getParent()
~~~
</div>
Return the parent node of this path tree node.<h4>Returns</h4>
<p>
this node's parent</p>
<h2><a class="anchor" name="isLeaf"></a>isLeaf</h2>
<div markdown="1">
~~~java
public boolean isLeaf()
~~~
</div>
Return whether or not this path tree node is a leaf.<h4>Returns</h4>
<p>
the "leaf" status of this node</p>
<h2><a class="anchor" name="isInfeasible"></a>isInfeasible</h2>
<div markdown="1">
~~~java
public boolean isInfeasible()
~~~
</div>
Return whether or not this path tree node is infeasible.<h4>Returns</h4>
<p>
the "infeasible" status of this node</p>
<h2><a class="anchor" name="isFullyExplored"></a>isFullyExplored</h2>
<div markdown="1">
~~~java
public abstract boolean isFullyExplored()
~~~
</div>
Return the "fully explored" status of this node.<h4>Returns</h4>
<p>
whether or not this node has been fully explored</p>
<h2><a class="anchor" name="setFullyExplored"></a>setFullyExplored</h2>
<div markdown="1">
~~~java
public abstract void setFullyExplored()
~~~
</div>
Mark this node as fully explored.<h2><a class="anchor" name="isComplete"></a>isComplete</h2>
<div markdown="1">
~~~java
public final boolean isComplete()
~~~
</div>
Return the "completed" status of this node. A node is complete if it has been
 fully explored or if it is a leaf or infeasible.<h4>Returns</h4>
<p>
the completed status of this node.</p>
<h2><a class="anchor" name="hasBeenGenerated"></a>hasBeenGenerated</h2>
<div markdown="1">
~~~java
public abstract boolean hasBeenGenerated()
~~~
</div>
Return the "generated" status of this node.<h4>Returns</h4>
<p>
whether or not a negated path has been generated for this node</p>
<h2><a class="anchor" name="setGenerated"></a>setGenerated</h2>
<div markdown="1">
~~~java
public abstract void setGenerated()
~~~
</div>
Mark this node as generated.<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
public abstract void lock()
~~~
</div>
Request the lock for this node.<h2><a class="anchor" name="unlock"></a>unlock</h2>
<div markdown="1">
~~~java
public abstract void unlock()
~~~
</div>
Release the lock for this node.<h2><a class="anchor" name="getExecution"></a>getExecution</h2>
<div markdown="1">
~~~java
public abstract symbolic.Execution getExecution()
~~~
</div>
Return the execution associated with this node. This only makes sense if this
 node is either a leaf or an infeasible terminal node.<h4>Returns</h4>
<p>
the execution associated with this node</p>
<h2><a class="anchor" name="height"></a>height</h2>
<div markdown="1">
~~~java
public int height()
~~~
</div>
Return the height of the subtree starting at this node.<h4>Returns</h4>
<p>
the height of the subtree</p>
<h2><a class="anchor" name="width"></a>width</h2>
<div markdown="1">
~~~java
public int width()
~~~
</div>
<h2><a class="anchor" name="stringFill"></a>stringFill</h2>
<div markdown="1">
~~~java
public int stringFill(char lines, int x, int y)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
lines<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
<tr>
<td>
x<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
y<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="stringWrite"></a>stringWrite</h2>
<div markdown="1">
~~~java
private static void stringWrite(char lines, int x, int y, String string)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
lines<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
<tr>
<td>
x<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
y<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
string<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getShape"></a>getShape</h2>
<div markdown="1">
~~~java
public String getShape()
~~~
</div>
Return the shape of the subtree starting at this node. This is a string with
 nested parenthesized strings and the characters '<code>L</code>' (for leaf),
 '<code>I</code>' (for infeasible node), and '<code>0</code>' (for an explored
 stub).<h4>Returns</h4>
<p>
the shape string for the subtree</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#SPACING">SPACING</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#id">id</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#parent">parent</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#pathTreeNodeCounter">pathTreeNodeCounter</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#PathTreeNode">PathTreeNode(PathTreeNode.PathTreeNodeInner, long)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#createChild">createChild(Path, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#createInfeasible">createInfeasible(long, Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#createLeaf">createLeaf(long, Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#createRoot">createRoot(Path)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getBranch">getBranch()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getChild">getChild(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getChildCount">getChildCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getExecution">getExecution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getId">getId()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getParent">getParent()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getPath">getPath()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getPathForChild">getPathForChild(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#getShape">getShape()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#hasBeenGenerated">hasBeenGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#height">height()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#isComplete">isComplete()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#isFullyExplored">isFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#isInfeasible">isInfeasible()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#isLeaf">isLeaf()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#lock">lock()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#setFullyExplored">setFullyExplored()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#setGenerated">setGenerated()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#stringFill">stringFill(char[][], int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#stringWrite">stringWrite(char[][], int, int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#unlock">unlock()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTreeNode/' | relative_url }}#width">width()</a></li>
</ul>
</li>

</ul>
</section>
