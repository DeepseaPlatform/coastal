---
title: Branch
permalink: /api/Branch/
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
An encapsulation of a potential branching point encountered during an
 execution of the system-under-test.<h2><a class="anchor" name="stringRep"></a>stringRep</h2>
<div markdown="1">
~~~java
protected String stringRep
~~~
</div>
<p>
String representation of this choice.</p>
<h2><a class="anchor" name="Branch()"></a>Branch</h2>
<div markdown="1">
~~~java
public Branch()
~~~
</div>
<h2><a class="anchor" name="getNumberOfAlternatives()"></a>getNumberOfAlternatives</h2>
<div markdown="1">
~~~java
public abstract long getNumberOfAlternatives()
~~~
</div>
Return the number of alternatives at this branch.<h4>Returns</h4>
<p>
the number of alternatives</p>
<h2><a class="anchor" name="getAlternativeRepr(long)"></a>getAlternativeRepr</h2>
<div markdown="1">
~~~java
public abstract String getAlternativeRepr(long alternative)
~~~
</div>
Return a string representation of a specific alternative at this branch.
 Typically, this would be "F" or "T" for a binary branch (if), or "1", "2",
 ... for a n-ary branch (switch).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
the number of the alternative</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a string representation of the alternative</p>
<h2><a class="anchor" name="getAlternative(long)"></a>getAlternative</h2>
<div markdown="1">
~~~java
public abstract solver.Expression getAlternative(long alternative)
~~~
</div>
Return an expression that describes a specific alternative at this branch.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
number of the alternative</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
expression for the alternative</p>
<h2><a class="anchor" name="getPCContribution(long)"></a>getPCContribution</h2>
<div markdown="1">
~~~java
protected abstract solver.Expression getPCContribution(long alternative)
~~~
</div>
Return an expression that describes the contribution that this branch makes
 to the path condition when a given alternative is taken at this branching
 point. This may include, for example, the correct active conjunct and the
 passive conjunct.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
which alternative to describe</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
branch's contribution to the path condition</p>
<h2><a class="anchor" name="toString()"></a>toString</h2>
<div markdown="1">
~~~java
public final String toString()
~~~
</div>
<h2><a class="anchor" name="toString0()"></a>toString0</h2>
<div markdown="1">
~~~java
protected abstract String toString0()
~~~
</div>
Return a string representation of this branch.<h4>Returns</h4>
<p>
string representation of this branch</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#stringRep">stringRep</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#Branch()">Branch()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#getAlternative(long)">getAlternative(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#getAlternativeRepr(long)">getAlternativeRepr(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#getNumberOfAlternatives()">getNumberOfAlternatives()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#getPCContribution(long)">getPCContribution(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#toString()">toString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Branch/' | relative_url }}#toString0()">toString0()</a></li>
</ul>
</li>

</ul>
</section>
