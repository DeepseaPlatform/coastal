---
title: PathTree
permalink: /api/PathTree/
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
Representation of all execution paths in a single tree.<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
protected final Logger log
~~~
</div>
<p>
The one-and-only logger.</p>
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
protected final messages.Broker broker
~~~
</div>
<p>
The message broker.</p>
<h2><a class="anchor" name="drawPaths"></a>drawPaths</h2>
<div markdown="1">
~~~java
private final boolean drawPaths
~~~
</div>
<p>
Flag to indicate whether paths should be drawn after updates.</p>
<h2><a class="anchor" name="recordDeepest"></a>recordDeepest</h2>
<div markdown="1">
~~~java
private boolean recordDeepest
~~~
</div>
<p>
Flag to indicate whether deepest paths should be recorded.</p>
<h2><a class="anchor" name="root"></a>root</h2>
<div markdown="1">
~~~java
private pathtree.PathTreeNode root
~~~
</div>
<p>
The root of the tree.</p>
<h2><a class="anchor" name="insertedCount"></a>insertedCount</h2>
<div markdown="1">
~~~java
private final AtomicLong insertedCount
~~~
</div>
<p>
The number of paths inserted.</p>
<h2><a class="anchor" name="revisitCount"></a>revisitCount</h2>
<div markdown="1">
~~~java
private final AtomicLong revisitCount
~~~
</div>
<p>
The number of paths whose insertion was unnecessary because they constitute a
 revisit of an already-inserted path.</p>
<h2><a class="anchor" name="insertTime"></a>insertTime</h2>
<div markdown="1">
~~~java
protected final AtomicLong insertTime
~~~
</div>
<p>
Accumulator of all the path tree insertion times.</p>
<h2><a class="anchor" name="infeasibleCount"></a>infeasibleCount</h2>
<div markdown="1">
~~~java
protected final AtomicLong infeasibleCount
~~~
</div>
<p>
A count of the number of infeasible paths.</p>
<h2><a class="anchor" name="lock"></a>lock</h2>
<div markdown="1">
~~~java
private final ReentrantReadWriteLock lock
~~~
</div>
<p>
A lock for updating the root of the tree.</p>
<h2><a class="anchor" name="deepest"></a>deepest</h2>
<div markdown="1">
~~~java
private final PriorityBlockingQueue deepest
~~~
</div>
<p>
A queue to keep track of the deepest nodes that are not fully explored.</p>
<h2><a class="anchor" name="PathTree"></a>PathTree</h2>
<div markdown="1">
~~~java
public PathTree(COASTAL coastal)
~~~
</div>
Constructor.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
reference to the analysis run controller</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setRecordDeepest"></a>setRecordDeepest</h2>
<div markdown="1">
~~~java
public void setRecordDeepest(boolean recordDeepest)
~~~
</div>
Set the status of the <code>recordDeepest</code> flag.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
recordDeepest<br/><span class="paramtype">boolean</span></td>
<td>
the new value for the flag</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getRoot"></a>getRoot</h2>
<div markdown="1">
~~~java
public pathtree.PathTreeNode getRoot()
~~~
</div>
Return the root of the path tree.<h4>Returns</h4>
<p>
the path tree root node</p>
<h2><a class="anchor" name="getInsertedCount"></a>getInsertedCount</h2>
<div markdown="1">
~~~java
public long getInsertedCount()
~~~
</div>
Return the number of paths inserted.<h4>Returns</h4>
<p>
the number of paths inserted</p>
<h2><a class="anchor" name="getRevisitCount"></a>getRevisitCount</h2>
<div markdown="1">
~~~java
public long getRevisitCount()
~~~
</div>
Return the number of paths revisited.<h4>Returns</h4>
<p>
the number of paths revisited</p>
<h2><a class="anchor" name="getInfeasibleCount"></a>getInfeasibleCount</h2>
<div markdown="1">
~~~java
public long getInfeasibleCount()
~~~
</div>
Return the number of infeasible paths.<h4>Returns</h4>
<p>
the number of infeasible paths</p>
<h2><a class="anchor" name="report"></a>report</h2>
<div markdown="1">
~~~java
public void report(Object object)
~~~
</div>
Publish a report about the use of the path tree.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
dummy</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="insertPath"></a>insertPath</h2>
<div markdown="1">
~~~java
public pathtree.PathTreeNode insertPath(symbolic.Execution execution, boolean isInfeasible)
~~~
</div>
Insert a single path in the path tree. The execution that this path
 represents is given as a parameter. This may not be an actual execution, in
 the sense that it may be that the path associated with the execution is
 infeasible (in other words, there is no model for the path). In this case,
 the execution will contain a path, but no inputs. The <code>isInfeasible</code>
 flag indicates when this is the case.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution associated with the path</td>
</tr>
<tr>
<td>
isInfeasible<br/><span class="paramtype">boolean</span></td>
<td>
whether or not the execution was infeasible</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the terminal node of the newly-inserted path in the path tree</p>
<h2><a class="anchor" name="insert"></a>insert</h2>
<div markdown="1">
~~~java
private pathtree.PathTreeNode insert(symbolic.Execution execution, symbolic.Path paths, boolean isInfeasible)
~~~
</div>
Helper routine to insert a path into the path tree. The major difference is
 that this routine assumes that the root exists, and that the path that
 corresponds to the execution has been separated into its prefixes.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the execution associated with the path</td>
</tr>
<tr>
<td>
paths<br/><span class="paramtype">symbolic.Path</span></td>
<td>
the prefixes of the path that corresponds to the
                     execution</td>
</tr>
<tr>
<td>
isInfeasible<br/><span class="paramtype">boolean</span></td>
<td>
whether or not the execution was infeasible</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
deepest node on the newly inserted path</p>
<h2><a class="anchor" name="getId"></a>getId</h2>
<div markdown="1">
~~~java
private String getId(pathtree.PathTreeNode node)
~~~
</div>
Return a formatted string to represent the <code>id</code> of a
 <a href="{{ '/api/PathTreeNode/' | relative_url }}">PathTreeNode</a>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
node<br/><span class="paramtype">pathtree.PathTreeNode</span></td>
<td>
the node to compute the representation for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a string representation of the node's <code>id</code></p>
<h2><a class="anchor" name="stringRepr"></a>stringRepr</h2>
<div markdown="1">
~~~java
public String stringRepr()
~~~
</div>
Compute a string representation of the path tree. The tree is neatly
 formatted using ASCII graphics, and returned as an array of strings.<h4>Returns</h4>
<p>
an array of strings that stores an ASCII image of the path tree</p>
<h2><a class="anchor" name="getShape"></a>getShape</h2>
<div markdown="1">
~~~java
public String getShape()
~~~
</div>
<h2><a class="anchor" name="getDeepestNode"></a>getDeepestNode</h2>
<div markdown="1">
~~~java
public pathtree.PathTreeNode getDeepestNode()
~~~
</div>
<h2><a class="anchor" name="compare"></a>compare</h2>
<div markdown="1">
~~~java
public int compare(pathtree.PathTreeNode node1, pathtree.PathTreeNode node2)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
node1<br/><span class="paramtype">pathtree.PathTreeNode</span></td>
<td>
</td>
</tr>
<tr>
<td>
node2<br/><span class="paramtype">pathtree.PathTreeNode</span></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#deepest">deepest</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#drawPaths">drawPaths</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#infeasibleCount">infeasibleCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#insertedCount">insertedCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#insertTime">insertTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#lock">lock</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#recordDeepest">recordDeepest</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#revisitCount">revisitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#root">root</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#PathTree">PathTree(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#compare">compare(PathTreeNode, PathTreeNode)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getDeepestNode">getDeepestNode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getId">getId(PathTreeNode)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getInfeasibleCount">getInfeasibleCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getInsertedCount">getInsertedCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getRevisitCount">getRevisitCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getRoot">getRoot()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#getShape">getShape()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#insert">insert(Execution, Path[], boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#insertPath">insertPath(Execution, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#report">report(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#setRecordDeepest">setRecordDeepest(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/PathTree/' | relative_url }}#stringRepr">stringRepr()</a></li>
</ul>
</li>

</ul>
</section>
