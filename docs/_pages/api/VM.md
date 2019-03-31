---
title: VM
permalink: /api/VM/
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
<h2><a class="anchor" name="state"></a>state</h2>
<div markdown="1">
~~~java
public static symbolic.State state
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="VM()"></a>VM</h2>
<div markdown="1">
~~~java
public VM()
~~~
</div>
<h2><a class="anchor" name="setState(State)"></a>setState</h2>
<div markdown="1">
~~~java
public static void setState(symbolic.State state)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
state<br/><span class="paramtype">symbolic.State</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNewVariableName()"></a>getNewVariableName</h2>
<div markdown="1">
~~~java
public static String getNewVariableName()
~~~
</div>
<h2><a class="anchor" name="getStringLength(int)"></a>getStringLength</h2>
<div markdown="1">
~~~java
public static solver.Expression getStringLength(int stringId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getStringChar(int, int)"></a>getStringChar</h2>
<div markdown="1">
~~~java
public static solver.Expression getStringChar(int stringId, int index)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="push(Expression)"></a>push</h2>
<div markdown="1">
~~~java
public static void push(solver.Expression expr)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expr<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pop()"></a>pop</h2>
<div markdown="1">
~~~java
public static solver.Expression pop()
~~~
</div>
<h2><a class="anchor" name="pushExtraConjunct(Expression)"></a>pushExtraConjunct</h2>
<div markdown="1">
~~~java
public static void pushExtraConjunct(solver.Expression extraConjunct)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
extraConjunct<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicInt(int, int)"></a>createSymbolicInt</h2>
<div markdown="1">
~~~java
public static int createSymbolicInt(int oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicShort(short, int)"></a>createSymbolicShort</h2>
<div markdown="1">
~~~java
public static short createSymbolicShort(short oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">short</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicBoolean(boolean, int)"></a>createSymbolicBoolean</h2>
<div markdown="1">
~~~java
public static boolean createSymbolicBoolean(boolean oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicByte(byte, int)"></a>createSymbolicByte</h2>
<div markdown="1">
~~~java
public static byte createSymbolicByte(byte oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">byte</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicChar(char, int)"></a>createSymbolicChar</h2>
<div markdown="1">
~~~java
public static char createSymbolicChar(char oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicLong(long, int)"></a>createSymbolicLong</h2>
<div markdown="1">
~~~java
public static long createSymbolicLong(long oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicFloat(float, int)"></a>createSymbolicFloat</h2>
<div markdown="1">
~~~java
public static float createSymbolicFloat(float oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">float</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicDouble(double, int)"></a>createSymbolicDouble</h2>
<div markdown="1">
~~~java
public static double createSymbolicDouble(double oldValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
oldValue<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getRecordingMode()"></a>getRecordingMode</h2>
<div markdown="1">
~~~java
public static boolean getRecordingMode()
~~~
</div>
<h2><a class="anchor" name="getConcreteBoolean(int, int, int, boolean)"></a>getConcreteBoolean</h2>
<div markdown="1">
~~~java
public static boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteByte(int, int, int, byte)"></a>getConcreteByte</h2>
<div markdown="1">
~~~java
public static byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteShort(int, int, int, short)"></a>getConcreteShort</h2>
<div markdown="1">
~~~java
public static short getConcreteShort(int triggerIndex, int index, int address, short currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteChar(int, int, int, char)"></a>getConcreteChar</h2>
<div markdown="1">
~~~java
public static char getConcreteChar(int triggerIndex, int index, int address, char currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteInt(int, int, int, int)"></a>getConcreteInt</h2>
<div markdown="1">
~~~java
public static int getConcreteInt(int triggerIndex, int index, int address, int currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteLong(int, int, int, long)"></a>getConcreteLong</h2>
<div markdown="1">
~~~java
public static long getConcreteLong(int triggerIndex, int index, int address, long currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteFloat(int, int, int, float)"></a>getConcreteFloat</h2>
<div markdown="1">
~~~java
public static float getConcreteFloat(int triggerIndex, int index, int address, float currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteDouble(int, int, int, double)"></a>getConcreteDouble</h2>
<div markdown="1">
~~~java
public static double getConcreteDouble(int triggerIndex, int index, int address, double currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteString(int, int, int, String)"></a>getConcreteString</h2>
<div markdown="1">
~~~java
public static String getConcreteString(int triggerIndex, int index, int address, String currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteBooleanArray(int, int, int, boolean[])"></a>getConcreteBooleanArray</h2>
<div markdown="1">
~~~java
public static boolean getConcreteBooleanArray(int triggerIndex, int index, int address, boolean currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteByteArray(int, int, int, byte[])"></a>getConcreteByteArray</h2>
<div markdown="1">
~~~java
public static byte getConcreteByteArray(int triggerIndex, int index, int address, byte currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteShortArray(int, int, int, short[])"></a>getConcreteShortArray</h2>
<div markdown="1">
~~~java
public static short getConcreteShortArray(int triggerIndex, int index, int address, short currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteCharArray(int, int, int, char[])"></a>getConcreteCharArray</h2>
<div markdown="1">
~~~java
public static char getConcreteCharArray(int triggerIndex, int index, int address, char currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteIntArray(int, int, int, int[])"></a>getConcreteIntArray</h2>
<div markdown="1">
~~~java
public static int getConcreteIntArray(int triggerIndex, int index, int address, int currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteLongArray(int, int, int, long[])"></a>getConcreteLongArray</h2>
<div markdown="1">
~~~java
public static long getConcreteLongArray(int triggerIndex, int index, int address, long currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteFloatArray(int, int, int, float[])"></a>getConcreteFloatArray</h2>
<div markdown="1">
~~~java
public static float getConcreteFloatArray(int triggerIndex, int index, int address, float currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteDoubleArray(int, int, int, double[])"></a>getConcreteDoubleArray</h2>
<div markdown="1">
~~~java
public static double getConcreteDoubleArray(int triggerIndex, int index, int address, double currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getConcreteStringArray(int, int, int, String[])"></a>getConcreteStringArray</h2>
<div markdown="1">
~~~java
public static String getConcreteStringArray(int triggerIndex, int index, int address, String currentValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<tr>
<td>
currentValue<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="triggerMethod(int, int)"></a>triggerMethod</h2>
<div markdown="1">
~~~java
public static void triggerMethod(int methodNumber, int triggerIndex)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodNumber<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startMethod(int, int)"></a>startMethod</h2>
<div markdown="1">
~~~java
public static void startMethod(int methodNumber, int argCount)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodNumber<br/><span class="paramtype">int</span></td>
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
<h2><a class="anchor" name="returnValue(boolean)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(boolean returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(char)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(char returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">char</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(double)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(double returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(float)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(float returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">float</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(int)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(int returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(long)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(long returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(short)"></a>returnValue</h2>
<div markdown="1">
~~~java
public static void returnValue(short returnValue)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">short</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="linenumber(int, int)"></a>linenumber</h2>
<div markdown="1">
~~~java
public static void linenumber(int instr, int line)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
line<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="label(int, String)"></a>label</h2>
<div markdown="1">
~~~java
public static void label(int instr, String label)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
label<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="insn(int, int)"></a>insn</h2>
<div markdown="1">
~~~java
public static void insn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="intInsn(int, int, int)"></a>intInsn</h2>
<div markdown="1">
~~~java
public static void intInsn(int instr, int opcode, int operand)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
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
<h2><a class="anchor" name="varInsn(int, int, int)"></a>varInsn</h2>
<div markdown="1">
~~~java
public static void varInsn(int instr, int opcode, int var)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
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
<h2><a class="anchor" name="typeInsn(int, int)"></a>typeInsn</h2>
<div markdown="1">
~~~java
public static void typeInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="fieldInsn(int, int, String, String, String)"></a>fieldInsn</h2>
<div markdown="1">
~~~java
public static void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
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
<h2><a class="anchor" name="methodInsn(int, int, String, String, String)"></a>methodInsn</h2>
<div markdown="1">
~~~java
public static void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
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
<h2><a class="anchor" name="invokeDynamicInsn(int, int)"></a>invokeDynamicInsn</h2>
<div markdown="1">
~~~java
public static void invokeDynamicInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public static void jumpInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public static void jumpInsn(int value, int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(Object, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public static void jumpInsn(Object value, int instr, int opcode)
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
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public static void jumpInsn(int value1, int value2, int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value1<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value2<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postJumpInsn(int, int)"></a>postJumpInsn</h2>
<div markdown="1">
~~~java
public static void postJumpInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, int)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public static void ldcInsn(int instr, int opcode, int value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, long)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public static void ldcInsn(int instr, int opcode, long value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, float)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public static void ldcInsn(int instr, int opcode, float value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">float</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, double)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public static void ldcInsn(int instr, int opcode, double value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, Object)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public static void ldcInsn(int instr, int opcode, Object value)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="iincInsn(int, int, int)"></a>iincInsn</h2>
<div markdown="1">
~~~java
public static void iincInsn(int instr, int var, int increment)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
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
<h2><a class="anchor" name="tableSwitchInsn(int, int)"></a>tableSwitchInsn</h2>
<div markdown="1">
~~~java
public static void tableSwitchInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tableCaseInsn(int, int, int)"></a>tableCaseInsn</h2>
<div markdown="1">
~~~java
public static void tableCaseInsn(int min, int max, int value)
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
value<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="lookupSwitchInsn(int, int)"></a>lookupSwitchInsn</h2>
<div markdown="1">
~~~java
public static void lookupSwitchInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="multiANewArrayInsn(int, int)"></a>multiANewArrayInsn</h2>
<div markdown="1">
~~~java
public static void multiANewArrayInsn(int instr, int opcode)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="noException()"></a>noException</h2>
<div markdown="1">
~~~java
public static void noException()
~~~
</div>
<h2><a class="anchor" name="startCatch(int)"></a>startCatch</h2>
<div markdown="1">
~~~java
public static void startCatch(int instr)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="stop()"></a>stop</h2>
<div markdown="1">
~~~java
public static void stop()
~~~
</div>
<h2><a class="anchor" name="stop(String)"></a>stop</h2>
<div markdown="1">
~~~java
public static void stop(String message)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
message<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(int)"></a>mark</h2>
<div markdown="1">
~~~java
public static void mark(int marker)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(String)"></a>mark</h2>
<div markdown="1">
~~~java
public static void mark(String marker)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="printPC(String)"></a>printPC</h2>
<div markdown="1">
~~~java
public static void printPC(String label)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
label<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="printPC()"></a>printPC</h2>
<div markdown="1">
~~~java
public static void printPC()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#state">state</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#VM()">VM()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicBoolean(boolean, int)">createSymbolicBoolean(boolean, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicByte(byte, int)">createSymbolicByte(byte, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicChar(char, int)">createSymbolicChar(char, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicDouble(double, int)">createSymbolicDouble(double, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicFloat(float, int)">createSymbolicFloat(float, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicInt(int, int)">createSymbolicInt(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicLong(long, int)">createSymbolicLong(long, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#createSymbolicShort(short, int)">createSymbolicShort(short, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#fieldInsn(int, int, String, String, String)">fieldInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteBoolean(int, int, int, boolean)">getConcreteBoolean(int, int, int, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteBooleanArray(int, int, int, boolean[])">getConcreteBooleanArray(int, int, int, boolean[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteByte(int, int, int, byte)">getConcreteByte(int, int, int, byte)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteByteArray(int, int, int, byte[])">getConcreteByteArray(int, int, int, byte[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteChar(int, int, int, char)">getConcreteChar(int, int, int, char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteCharArray(int, int, int, char[])">getConcreteCharArray(int, int, int, char[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteDouble(int, int, int, double)">getConcreteDouble(int, int, int, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteDoubleArray(int, int, int, double[])">getConcreteDoubleArray(int, int, int, double[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteFloat(int, int, int, float)">getConcreteFloat(int, int, int, float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteFloatArray(int, int, int, float[])">getConcreteFloatArray(int, int, int, float[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteInt(int, int, int, int)">getConcreteInt(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteIntArray(int, int, int, int[])">getConcreteIntArray(int, int, int, int[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteLong(int, int, int, long)">getConcreteLong(int, int, int, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteLongArray(int, int, int, long[])">getConcreteLongArray(int, int, int, long[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteShort(int, int, int, short)">getConcreteShort(int, int, int, short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteShortArray(int, int, int, short[])">getConcreteShortArray(int, int, int, short[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteString(int, int, int, String)">getConcreteString(int, int, int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getConcreteStringArray(int, int, int, String[])">getConcreteStringArray(int, int, int, String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getNewVariableName()">getNewVariableName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getRecordingMode()">getRecordingMode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getStringChar(int, int)">getStringChar(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#getStringLength(int)">getStringLength(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#iincInsn(int, int, int)">iincInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#insn(int, int)">insn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#intInsn(int, int, int)">intInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#invokeDynamicInsn(int, int)">invokeDynamicInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#jumpInsn(int, int)">jumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#jumpInsn(int, int, int)">jumpInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#jumpInsn(int, int, int, int)">jumpInsn(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#jumpInsn(Object, int, int)">jumpInsn(Object, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#label(int, String)">label(int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#ldcInsn(int, int, double)">ldcInsn(int, int, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#ldcInsn(int, int, float)">ldcInsn(int, int, float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#ldcInsn(int, int, int)">ldcInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#ldcInsn(int, int, long)">ldcInsn(int, int, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#ldcInsn(int, int, Object)">ldcInsn(int, int, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#linenumber(int, int)">linenumber(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#lookupSwitchInsn(int, int)">lookupSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#mark(int)">mark(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#mark(String)">mark(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#methodInsn(int, int, String, String, String)">methodInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#multiANewArrayInsn(int, int)">multiANewArrayInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#noException()">noException()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#pop()">pop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#postJumpInsn(int, int)">postJumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#printPC()">printPC()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#printPC(String)">printPC(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#push(Expression)">push(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#pushExtraConjunct(Expression)">pushExtraConjunct(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(boolean)">returnValue(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(char)">returnValue(char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(double)">returnValue(double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(float)">returnValue(float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(int)">returnValue(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(long)">returnValue(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#returnValue(short)">returnValue(short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#setState(State)">setState(State)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#startCatch(int)">startCatch(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#startMethod(int, int)">startMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#stop()">stop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#stop(String)">stop(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#tableCaseInsn(int, int, int)">tableCaseInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#tableSwitchInsn(int, int)">tableSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#triggerMethod(int, int)">triggerMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#typeInsn(int, int)">typeInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/VM/' | relative_url }}#varInsn(int, int, int)">varInsn(int, int, int)</a></li>
</ul>
</li>

</ul>
</section>
