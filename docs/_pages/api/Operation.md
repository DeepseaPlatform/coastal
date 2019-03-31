---
title: Operation
permalink: /api/Operation/
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
<h2><a class="anchor" name="FALSE"></a>FALSE</h2>
<div markdown="1">
~~~java
public static final solver.Expression FALSE
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="TRUE"></a>TRUE</h2>
<div markdown="1">
~~~java
public static final solver.Expression TRUE
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="operator"></a>operator</h2>
<div markdown="1">
~~~java
protected final solver.Operation.Operator operator
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="operands"></a>operands</h2>
<div markdown="1">
~~~java
protected final solver.Expression operands
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="Operation"></a>Operation</h2>
<div markdown="1">
~~~java
public Operation(solver.Operation.Operator operator, solver.Expression operands)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
operator<br/><span class="paramtype">solver.Operation.Operator</span></td>
<td>
</td>
</tr>
<tr>
<td>
operands<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getOperator"></a>getOperator</h2>
<div markdown="1">
~~~java
public solver.Operation.Operator getOperator()
~~~
</div>
<h2><a class="anchor" name="getOperatandCount"></a>getOperatandCount</h2>
<div markdown="1">
~~~java
public int getOperatandCount()
~~~
</div>
<h2><a class="anchor" name="getOperands"></a>getOperands</h2>
<div markdown="1">
~~~java
public Iterable getOperands()
~~~
</div>
<h2><a class="anchor" name="getOperand"></a>getOperand</h2>
<div markdown="1">
~~~java
public solver.Expression getOperand(int index)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="accept"></a>accept</h2>
<div markdown="1">
~~~java
public void accept(solver.Visitor visitor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
visitor<br/><span class="paramtype">solver.Visitor</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="toString0"></a>toString0</h2>
<div markdown="1">
~~~java
public String toString0()
~~~
</div>
<h2><a class="anchor" name="or"></a>or</h2>
<div markdown="1">
~~~java
public static solver.Expression or(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="and"></a>and</h2>
<div markdown="1">
~~~java
public static solver.Expression and(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="not"></a>not</h2>
<div markdown="1">
~~~java
public static solver.Expression not(solver.Expression a)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="eq"></a>eq</h2>
<div markdown="1">
~~~java
public static solver.Expression eq(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ne"></a>ne</h2>
<div markdown="1">
~~~java
public static solver.Expression ne(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="lt"></a>lt</h2>
<div markdown="1">
~~~java
public static solver.Expression lt(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="le"></a>le</h2>
<div markdown="1">
~~~java
public static solver.Expression le(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="gt"></a>gt</h2>
<div markdown="1">
~~~java
public static solver.Expression gt(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ge"></a>ge</h2>
<div markdown="1">
~~~java
public static solver.Expression ge(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="lcmp"></a>lcmp</h2>
<div markdown="1">
~~~java
public static solver.Expression lcmp(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="fcmpl"></a>fcmpl</h2>
<div markdown="1">
~~~java
public static solver.Expression fcmpl(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="fcmpg"></a>fcmpg</h2>
<div markdown="1">
~~~java
public static solver.Expression fcmpg(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="dcmpl"></a>dcmpl</h2>
<div markdown="1">
~~~java
public static solver.Expression dcmpl(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="dcmpg"></a>dcmpg</h2>
<div markdown="1">
~~~java
public static solver.Expression dcmpg(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="i2l"></a>i2l</h2>
<div markdown="1">
~~~java
public static solver.Expression i2l(solver.Expression a)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="f2d"></a>f2d</h2>
<div markdown="1">
~~~java
public static solver.Expression f2d(solver.Expression a)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="add"></a>add</h2>
<div markdown="1">
~~~java
public static solver.Expression add(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="sub"></a>sub</h2>
<div markdown="1">
~~~java
public static solver.Expression sub(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mul"></a>mul</h2>
<div markdown="1">
~~~java
public static solver.Expression mul(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="div"></a>div</h2>
<div markdown="1">
~~~java
public static solver.Expression div(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="rem"></a>rem</h2>
<div markdown="1">
~~~java
public static solver.Expression rem(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="bitor"></a>bitor</h2>
<div markdown="1">
~~~java
public static solver.Expression bitor(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="bitand"></a>bitand</h2>
<div markdown="1">
~~~java
public static solver.Expression bitand(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="bitxor"></a>bitxor</h2>
<div markdown="1">
~~~java
public static solver.Expression bitxor(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="shl"></a>shl</h2>
<div markdown="1">
~~~java
public static solver.Expression shl(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ashr"></a>ashr</h2>
<div markdown="1">
~~~java
public static solver.Expression ashr(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="lshr"></a>lshr</h2>
<div markdown="1">
~~~java
public static solver.Expression lshr(solver.Expression a, solver.Expression b)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
a<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
<tr>
<td>
b<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#FALSE">FALSE</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#TRUE">TRUE</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#operands">operands</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#operator">operator</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#Operation">Operation(Operation.Operator, Expression...)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#accept">accept(Visitor)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#add">add(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#and">and(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#ashr">ashr(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#bitand">bitand(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#bitor">bitor(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#bitxor">bitxor(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#dcmpg">dcmpg(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#dcmpl">dcmpl(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#div">div(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#eq">eq(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#f2d">f2d(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#fcmpg">fcmpg(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#fcmpl">fcmpl(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#ge">ge(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#getOperand">getOperand(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#getOperands">getOperands()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#getOperatandCount">getOperatandCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#getOperator">getOperator()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#gt">gt(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#i2l">i2l(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#lcmp">lcmp(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#le">le(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#lshr">lshr(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#lt">lt(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#mul">mul(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#ne">ne(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#not">not(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#or">or(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#rem">rem(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#shl">shl(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#sub">sub(Expression, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Operation/' | relative_url }}#toString0">toString0()</a></li>
</ul>
</li>

</ul>
</section>
