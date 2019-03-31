---
title: Choice
permalink: /api/Choice/
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
An encapsulation of a branch taken during an execution of the
 system-under-test.<h2><a class="anchor" name="branch"></a>branch</h2>
<div markdown="1">
~~~java
private final symbolic.Branch branch
~~~
</div>
<p>
The branching point associated with this choice.</p>
<h2><a class="anchor" name="alternative"></a>alternative</h2>
<div markdown="1">
~~~java
private final long alternative
~~~
</div>
<p>
The index (0, 1, 2, ...) taken at the branching point.</p>
<h2><a class="anchor" name="stringRep"></a>stringRep</h2>
<div markdown="1">
~~~java
private String stringRep
~~~
</div>
<p>
String representation of this choice.</p>
<h2><a class="anchor" name="Choice(Branch, long)"></a>Choice</h2>
<div markdown="1">
~~~java
public Choice(symbolic.Branch branch, long alternative)
~~~
</div>
Construct a choice.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
branch<br/><span class="paramtype">symbolic.Branch</span></td>
<td>
the branching point associated with this choice</td>
</tr>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
the alternative taken at the branching point</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getBranch()"></a>getBranch</h2>
<div markdown="1">
~~~java
public symbolic.Branch getBranch()
~~~
</div>
The branch associated with this choice.<h4>Returns</h4>
<p>
the branch associated with this choice</p>
<h2><a class="anchor" name="getAlternative()"></a>getAlternative</h2>
<div markdown="1">
~~~java
public long getAlternative()
~~~
</div>
Return the index of the alternative that was taken at this branch.<h4>Returns</h4>
<p>
the index of alternative taken</p>
<h2><a class="anchor" name="getAlternative(long)"></a>getAlternative</h2>
<div markdown="1">
~~~java
public symbolic.Choice getAlternative(long alternative)
~~~
</div>
Return an alternative choice.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
alternative<br/><span class="paramtype">long</span></td>
<td>
index of the alternative</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
alternative choice</p>
<h2><a class="anchor" name="getActiveConjunct()"></a>getActiveConjunct</h2>
<div markdown="1">
~~~java
public solver.Expression getActiveConjunct()
~~~
</div>
The active conjunct associated with this choice.<h4>Returns</h4>
<p>
the active conjunct associated with this choice</p>
<h2><a class="anchor" name="getPCContribution()"></a>getPCContribution</h2>
<div markdown="1">
~~~java
public solver.Expression getPCContribution()
~~~
</div>
Return the contribution of this choice to a path condition of which it forms
 part.<h4>Returns</h4>
<p>
the choice's contribution to a path condition</p>
<h2><a class="anchor" name="getSignatureContribution()"></a>getSignatureContribution</h2>
<div markdown="1">
~~~java
public String getSignatureContribution()
~~~
</div>
Return the contribution of this choice to the signature of a path of which it forms
 part.<h4>Returns</h4>
<p>
the choice's contribution to the signature</p>
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
Return a string representation of this choice.<h4>Returns</h4>
<p>
string representation of this choice</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#alternative">alternative</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#branch">branch</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#stringRep">stringRep</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#Choice(Branch, long)">Choice(Branch, long)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getActiveConjunct()">getActiveConjunct()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getAlternative()">getAlternative()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getAlternative(long)">getAlternative(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getBranch()">getBranch()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getPCContribution()">getPCContribution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#getSignatureContribution()">getSignatureContribution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#toString()">toString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Choice/' | relative_url }}#toString0()">toString0()</a></li>
</ul>
</li>

</ul>
</section>
