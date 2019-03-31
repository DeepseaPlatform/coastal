---
title: HeavyMethodAdapter
permalink: /api/HeavyMethodAdapter/
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
<h2><a class="anchor" name="VERIFIER"></a>VERIFIER</h2>
<div markdown="1">
~~~java
private static final String VERIFIER = "org/sosy_lab/sv_benchmarks/Verifier"
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
<h2><a class="anchor" name="useConcreteValues"></a>useConcreteValues</h2>
<div markdown="1">
~~~java
private final boolean useConcreteValues
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
<h2><a class="anchor" name="caseLabels"></a>caseLabels</h2>
<div markdown="1">
~~~java
private static Map caseLabels
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="catchLabels"></a>catchLabels</h2>
<div markdown="1">
~~~java
private static Set catchLabels
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
<h2><a class="anchor" name="HeavyMethodAdapter"></a>HeavyMethodAdapter</h2>
<div markdown="1">
~~~java
public HeavyMethodAdapter(COASTAL coastal, org.objectweb.asm.MethodVisitor cv, int triggerIndex, boolean isStatic, int argCount)
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
<h2><a class="anchor" name="visitTryCatchBlock"></a>visitTryCatchBlock</h2>
<div markdown="1">
~~~java
public void visitTryCatchBlock(org.objectweb.asm.Label start, org.objectweb.asm.Label end, org.objectweb.asm.Label handler, String type)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
start<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
<tr>
<td>
end<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
<tr>
<td>
handler<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
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
<h2><a class="anchor" name="visitIntInsn"></a>visitIntInsn</h2>
<div markdown="1">
~~~java
public void visitIntInsn(int opcode, int operand)
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
operand<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitVarInsn"></a>visitVarInsn</h2>
<div markdown="1">
~~~java
public void visitVarInsn(int opcode, int var)
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
var<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitTypeInsn"></a>visitTypeInsn</h2>
<div markdown="1">
~~~java
public void visitTypeInsn(int opcode, String type)
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
type<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitFieldInsn"></a>visitFieldInsn</h2>
<div markdown="1">
~~~java
public void visitFieldInsn(int opcode, String owner, String name, String descriptor)
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
</tbody>
</table>
<h2><a class="anchor" name="primitiveReturnType"></a>primitiveReturnType</h2>
<div markdown="1">
~~~java
private char primitiveReturnType(String descriptor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
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
<h2><a class="anchor" name="visitInvokeDynamicInsn"></a>visitInvokeDynamicInsn</h2>
<div markdown="1">
~~~java
public void visitInvokeDynamicInsn(String name, String descriptor, org.objectweb.asm.Handle bootstrapMethodHandle, Object bootstrapMethodArguments)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
bootstrapMethodHandle<br/><span class="paramtype">org.objectweb.asm.Handle</span></td>
<td>
</td>
</tr>
<tr>
<td>
bootstrapMethodArguments<br/><span class="paramtype">Object</span></td>
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
<h2><a class="anchor" name="visitLdcInsn"></a>visitLdcInsn</h2>
<div markdown="1">
~~~java
public void visitLdcInsn(Object value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitIincInsn"></a>visitIincInsn</h2>
<div markdown="1">
~~~java
public void visitIincInsn(int var, int increment)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
var<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
increment<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitTableSwitchInsn"></a>visitTableSwitchInsn</h2>
<div markdown="1">
~~~java
public void visitTableSwitchInsn(int min, int max, org.objectweb.asm.Label dflt, org.objectweb.asm.Label labels)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
min<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
dflt<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
<tr>
<td>
labels<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitLabel"></a>visitLabel</h2>
<div markdown="1">
~~~java
public void visitLabel(org.objectweb.asm.Label label)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
label<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitLookupSwitchInsn"></a>visitLookupSwitchInsn</h2>
<div markdown="1">
~~~java
public void visitLookupSwitchInsn(org.objectweb.asm.Label dflt, int keys, org.objectweb.asm.Label labels)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
dflt<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
<tr>
<td>
keys<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
labels<br/><span class="paramtype">org.objectweb.asm.Label</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="visitMultiANewArrayInsn"></a>visitMultiANewArrayInsn</h2>
<div markdown="1">
~~~java
public void visitMultiANewArrayInsn(String descriptor, int numDimensions)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
numDimensions<br/><span class="paramtype">int</span></td>
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
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#LIBRARY">LIBRARY</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#SYMBOLIC">SYMBOLIC</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#VERIFIER">VERIFIER</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#argCount">argCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#caseLabels">caseLabels</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#catchLabels">catchLabels</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#classManager">classManager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#currentLinenumbers">currentLinenumbers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#isStatic">isStatic</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#triggerIndex">triggerIndex</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#useConcreteValues">useConcreteValues</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#HeavyMethodAdapter">HeavyMethodAdapter(COASTAL, MethodVisitor, int, boolean, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#primitiveReturnType">primitiveReturnType(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitCode">visitCode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitEnd">visitEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitFieldInsn">visitFieldInsn(int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitIincInsn">visitIincInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitInsn">visitInsn(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitIntInsn">visitIntInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitInvokeDynamicInsn">visitInvokeDynamicInsn(String, String, Handle, Object...)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitJumpInsn">visitJumpInsn(int, Label)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitLabel">visitLabel(Label)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitLdcInsn">visitLdcInsn(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitLineNumber">visitLineNumber(int, Label)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitLookupSwitchInsn">visitLookupSwitchInsn(Label, int[], Label[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitMethodInsn">visitMethodInsn(int, String, String, String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitMultiANewArrayInsn">visitMultiANewArrayInsn(String, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitParameter">visitParameter(Trigger, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitTableSwitchInsn">visitTableSwitchInsn(int, int, Label, Label...)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitTryCatchBlock">visitTryCatchBlock(Label, Label, Label, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitTypeInsn">visitTypeInsn(int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitVarInsn">visitVarInsn(int, int)</a></li>
</ul>
</li>

</ul>
</section>
