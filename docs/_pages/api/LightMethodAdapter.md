---
title: LightMethodAdapter
permalink: /api/LightMethodAdapter/
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
<h2><a class="anchor" name="SYMBOLIC"></a>SYMBOLIC</h2>
<div markdown="1">
~~~java
private static final String SYMBOLIC = "za/ac/sun/cs/coastal/Symbolic"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="LIBRARY"></a>LIBRARY</h2>
<div markdown="1">
~~~java
private static final String LIBRARY = "za/ac/sun/cs/coastal/symbolic/VM"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
private final COASTAL coastal
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
private final Logger log
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="classManager"></a>classManager</h2>
<div markdown="1">
~~~java
private final instrument.InstrumentationClassManager classManager
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="triggerIndex"></a>triggerIndex</h2>
<div markdown="1">
~~~java
private final int triggerIndex
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="isStatic"></a>isStatic</h2>
<div markdown="1">
~~~java
private final boolean isStatic
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="argCount"></a>argCount</h2>
<div markdown="1">
~~~java
private final int argCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="currentLinenumbers"></a>currentLinenumbers</h2>
<div markdown="1">
~~~java
private BitSet currentLinenumbers
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="LightMethodAdapter"></a>LightMethodAdapter</h2>
<div markdown="1">
~~~java
public LightMethodAdapter(COASTAL coastal, org.objectweb.asm.MethodVisitor cv, int triggerIndex, boolean isStatic, int argCount)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
</td>
</tr>
<tr>
<td>
cv<br/><span class="paramtype">org.objectweb.asm.MethodVisitor</span></td>
<td>
</td>
</tr>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
isStatic<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
<tr>
<td>
argCount<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitParameter"></a>visitParameter</h2>
<div markdown="1">
~~~java
private int visitParameter(Trigger trigger, int triggerIndex, int index, int address)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
trigger<br/><span class="paramtype">Trigger</span></td>
<td>
</td>
</tr>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitLineNumber"></a>visitLineNumber</h2>
<div markdown="1">
~~~java
public void visitLineNumber(int line, org.objectweb.asm.Label start)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
line<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
start<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitEnd"></a>visitEnd</h2>
<div markdown="1">
~~~java
public void visitEnd()
~~~
</div>
<h2><a class="anchor" name="visitCode"></a>visitCode</h2>
<div markdown="1">
~~~java
public void visitCode()
~~~
</div>
<h2><a class="anchor" name="visitInsn"></a>visitInsn</h2>
<div markdown="1">
~~~java
public void visitInsn(int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitMethodInsn"></a>visitMethodInsn</h2>
<div markdown="1">
~~~java
public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
owner<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
isInterface<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitJumpInsn"></a>visitJumpInsn</h2>
<div markdown="1">
~~~java
public void visitJumpInsn(int opcode, org.objectweb.asm.Label label)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
label<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
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
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#LIBRARY">LIBRARY</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#SYMBOLIC">SYMBOLIC</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#argCount">argCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#classManager">classManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#currentLinenumbers">currentLinenumbers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#isStatic">isStatic</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#triggerIndex">triggerIndex</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#LightMethodAdapter">LightMethodAdapter(COASTAL, MethodVisitor, int, boolean, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitCode">visitCode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitEnd">visitEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitInsn">visitInsn(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitJumpInsn">visitJumpInsn(int, Label)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitLineNumber">visitLineNumber(int, Label)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitMethodInsn">visitMethodInsn(int, String, String, String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitParameter">visitParameter(Trigger, int, int, int)</a></li>
</ul>
</li>

</ul>
</section>
