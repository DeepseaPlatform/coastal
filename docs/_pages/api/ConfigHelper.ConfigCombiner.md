---
title: ConfigHelper.ConfigCombiner
permalink: /api/ConfigHelper.ConfigCombiner/
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
Special combiner that merge (not overwrite) selected nodes in an Apache
 XML configuration.<h2><a class="anchor" name="joinNodes"></a>joinNodes</h2>
<div markdown="1">
~~~java
private final Map joinNodes
~~~
</div>
<p>
Mapping from node tags to key attributes. The children of nodes whose
 names (tags) appear in this mapping are merged if and only if they
 agree on the value of the key attribute. If they disagree on the key
 attribute they are combined.
 
 For example, if this mapping contains the pair
 <code>"elems" -> "name"</code>, then the two <code>"elems"</code> elements
 
 <pre>
 &lt;elems&gt;
   &lt;elem name="aaa" max=5/&gt;
   &lt;elem name="bbb" max=7/&gt;
 &lt;/elems&gt;
 
 &lt;elems&gt;
   &lt;elem name="ccc" min=1/&gt;
   &lt;elem name="bbb" min=3/&gt;
 &lt;/elems&gt;
 </pre>
 
 are merged as
 
 <pre>
 &lt;elems&gt;
   &lt;elem name="aaa" max=5/&gt;
   &lt;elem name="bbb" max=7 min=3/&gt;
   &lt;elem name="ccc" min=1/&gt;
 &lt;/elems&gt;
 </pre></p>
<h2><a class="anchor" name="ConfigCombiner()"></a>ConfigCombiner</h2>
<div markdown="1">
~~~java
public ConfigCombiner()
~~~
</div>
Construct a COASTAL configuration node combiner.<h2><a class="anchor" name="addJoinNode(String, String)"></a>addJoinNode</h2>
<div markdown="1">
~~~java
public void addJoinNode(String nodeName, String key)
~~~
</div>
Add a node name and key attribute to the mapping of "special" nodes.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
nodeName<br/><span class="paramtype">String</span></td>
<td>
the name of the node</td>
</tr>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key attribute</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getJoinKey(ImmutableNode)"></a>getJoinKey</h2>
<div markdown="1">
~~~java
public String getJoinKey(ImmutableNode node)
~~~
</div>
Return the key attribute associated with the given node. If there is
 no such attribute, <code>null</code> is returned.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
node<br/><span class="paramtype">ImmutableNode</span></td>
<td>
the node whose key attribute is searched for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the key attribute or <code>null</code> if the node has no
         registered key attribute</p>
<h2><a class="anchor" name="combine(ImmutableNode, ImmutableNode)"></a>combine</h2>
<div markdown="1">
~~~java
public ImmutableNode combine(ImmutableNode node1, ImmutableNode node2)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
node1<br/><span class="paramtype">ImmutableNode</span></td>
<td>
</td>
</tr>
<tr>
<td>
node2<br/><span class="paramtype">ImmutableNode</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="addAttributes(ImmutableNode.Builder, ImmutableNode, ImmutableNode)"></a>addAttributes</h2>
<div markdown="1">
~~~java
protected void addAttributes(Builder result, ImmutableNode node1, ImmutableNode node2)
~~~
</div>
Merge the attributes of <code>node1</code> and <code>node2</code> into
 attributes for <code>result</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
result<br/><span class="paramtype">Builder</span></td>
<td>
the target node for the attributes</td>
</tr>
<tr>
<td>
node1<br/><span class="paramtype">ImmutableNode</span></td>
<td>
the first source node for attributes</td>
</tr>
<tr>
<td>
node2<br/><span class="paramtype">ImmutableNode</span></td>
<td>
the second source node for attributes</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="canCombine(ImmutableNode, ImmutableNode, ImmutableNode)"></a>canCombine</h2>
<div markdown="1">
~~~java
protected ImmutableNode canCombine(ImmutableNode node1, ImmutableNode node2, ImmutableNode child)
~~~
</div>
Check whether the child of node1 corresponds to (= can be combined
 with) some child of node2 (which is then returned) or whether it is
 unique (in which case it is added on its own).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
node1<br/><span class="paramtype">ImmutableNode</span></td>
<td>
the first parent node</td>
</tr>
<tr>
<td>
node2<br/><span class="paramtype">ImmutableNode</span></td>
<td>
the second parent node</td>
</tr>
<tr>
<td>
child<br/><span class="paramtype">ImmutableNode</span></td>
<td>
some child of the first parent node</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the corresponding child of the second parent node or
         <code>null</code></p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#joinNodes">joinNodes</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#ConfigCombiner()">ConfigCombiner()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#addAttributes(ImmutableNode.Builder, ImmutableNode, ImmutableNode)">addAttributes(ImmutableNode.Builder, ImmutableNode, ImmutableNode)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#addJoinNode(String, String)">addJoinNode(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#canCombine(ImmutableNode, ImmutableNode, ImmutableNode)">canCombine(ImmutableNode, ImmutableNode, ImmutableNode)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#combine(ImmutableNode, ImmutableNode)">combine(ImmutableNode, ImmutableNode)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}#getJoinKey(ImmutableNode)">getJoinKey(ImmutableNode)</a></li>
</ul>
</li>

</ul>
</section>
