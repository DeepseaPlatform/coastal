---
title: InstrumentationClassManager
permalink: /api/InstrumentationClassManager/
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
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
private final messages.Broker broker
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="showInstrumentation"></a>showInstrumentation</h2>
<div markdown="1">
~~~java
private final boolean showInstrumentation
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="writeClassfile"></a>writeClassfile</h2>
<div markdown="1">
~~~java
private final String writeClassfile
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="classPaths"></a>classPaths</h2>
<div markdown="1">
~~~java
private final List classPaths
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="jars"></a>jars</h2>
<div markdown="1">
~~~java
private final Map jars
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="requestCount"></a>requestCount</h2>
<div markdown="1">
~~~java
private final AtomicLong requestCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="cacheHitCount"></a>cacheHitCount</h2>
<div markdown="1">
~~~java
private final AtomicLong cacheHitCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="instrumentedCount"></a>instrumentedCount</h2>
<div markdown="1">
~~~java
private final AtomicLong instrumentedCount
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="loadTime"></a>loadTime</h2>
<div markdown="1">
~~~java
private final AtomicLong loadTime
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="instrumentedTime"></a>instrumentedTime</h2>
<div markdown="1">
~~~java
private final AtomicLong instrumentedTime
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="uninstrumentedTime"></a>uninstrumentedTime</h2>
<div markdown="1">
~~~java
private final AtomicLong uninstrumentedTime
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="preInstrumentedSize"></a>preInstrumentedSize</h2>
<div markdown="1">
~~~java
private final AtomicLong preInstrumentedSize
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="postInstrumentedSize"></a>postInstrumentedSize</h2>
<div markdown="1">
~~~java
private final AtomicLong postInstrumentedSize
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="clearCache"></a>clearCache</h2>
<div markdown="1">
~~~java
private final Map clearCache
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="heavyCache"></a>heavyCache</h2>
<div markdown="1">
~~~java
private final Map heavyCache
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="lightCache"></a>lightCache</h2>
<div markdown="1">
~~~java
private final Map lightCache
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="instructionCounter"></a>instructionCounter</h2>
<div markdown="1">
~~~java
private int instructionCounter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="methodCounter"></a>methodCounter</h2>
<div markdown="1">
~~~java
private int methodCounter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="newVariableCounter"></a>newVariableCounter</h2>
<div markdown="1">
~~~java
private int newVariableCounter
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="firstInstruction"></a>firstInstruction</h2>
<div markdown="1">
~~~java
private Map firstInstruction
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="lastInstruction"></a>lastInstruction</h2>
<div markdown="1">
~~~java
private Map lastInstruction
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="linenumbers"></a>linenumbers</h2>
<div markdown="1">
~~~java
private Map linenumbers
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="branchInstructions"></a>branchInstructions</h2>
<div markdown="1">
~~~java
private Map branchInstructions
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="InstrumentationClassManager"></a>InstrumentationClassManager</h2>
<div markdown="1">
~~~java
public InstrumentationClassManager(COASTAL coastal, String classPath)
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
classPath<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createHeavyClassLoader"></a>createHeavyClassLoader</h2>
<div markdown="1">
~~~java
public ClassLoader createHeavyClassLoader(diver.SymbolicState symbolicState)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
symbolicState<br/><span class="paramtype">diver.SymbolicState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createLightClassLoader"></a>createLightClassLoader</h2>
<div markdown="1">
~~~java
public ClassLoader createLightClassLoader(surfer.TraceState traceState)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
traceState<br/><span class="paramtype">surfer.TraceState</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startLoad"></a>startLoad</h2>
<div markdown="1">
~~~java
public void startLoad()
~~~
</div>
<h2><a class="anchor" name="endLoad"></a>endLoad</h2>
<div markdown="1">
~~~java
public void endLoad(long time)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
time<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="loadUninstrumented"></a>loadUninstrumented</h2>
<div markdown="1">
~~~java
public byte loadUninstrumented(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="loadUninstrumented0"></a>loadUninstrumented0</h2>
<div markdown="1">
~~~java
private synchronized byte loadUninstrumented0(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="loadHeavyInstrumented"></a>loadHeavyInstrumented</h2>
<div markdown="1">
~~~java
public byte loadHeavyInstrumented(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="loadHeavyInstrumented0"></a>loadHeavyInstrumented0</h2>
<div markdown="1">
~~~java
private synchronized byte loadHeavyInstrumented0(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="loadLightInstrumented"></a>loadLightInstrumented</h2>
<div markdown="1">
~~~java
public byte loadLightInstrumented(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="loadLightInstrumented0"></a>loadLightInstrumented0</h2>
<div markdown="1">
~~~java
private synchronized byte loadLightInstrumented0(String name)
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
</tbody>
</table>
<h2><a class="anchor" name="writeFile"></a>writeFile</h2>
<div markdown="1">
~~~java
public void writeFile(String filename, byte contents)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
contents<br/><span class="paramtype">byte</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="loadFile"></a>loadFile</h2>
<div markdown="1">
~~~java
private byte loadFile(String filename, boolean tryResource, boolean tryJar)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
tryResource<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
<tr>
<td>
tryJar<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="searchFor"></a>searchFor</h2>
<div markdown="1">
~~~java
private InputStream searchFor(String filename, boolean tryResource)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
tryResource<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="report"></a>report</h2>
<div markdown="1">
~~~java
public void report(Object object)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
object<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getFirstInstruction"></a>getFirstInstruction</h2>
<div markdown="1">
~~~java
public Integer getFirstInstruction(int methodNumber)
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
</tbody>
</table>
<h2><a class="anchor" name="getLastInstruction"></a>getLastInstruction</h2>
<div markdown="1">
~~~java
public Integer getLastInstruction(int methodNumber)
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
</tbody>
</table>
<h2><a class="anchor" name="getLineNumbers"></a>getLineNumbers</h2>
<div markdown="1">
~~~java
public BitSet getLineNumbers(int methodNumber)
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
</tbody>
</table>
<h2><a class="anchor" name="getJumpPoints"></a>getJumpPoints</h2>
<div markdown="1">
~~~java
public BitSet getJumpPoints(int methodNumber)
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
</tbody>
</table>
<h2><a class="anchor" name="getInstructionCounter"></a>getInstructionCounter</h2>
<div markdown="1">
~~~java
public int getInstructionCounter()
~~~
</div>
<h2><a class="anchor" name="getNextInstructionCounter"></a>getNextInstructionCounter</h2>
<div markdown="1">
~~~java
public int getNextInstructionCounter()
~~~
</div>
<h2><a class="anchor" name="getMethodCounter"></a>getMethodCounter</h2>
<div markdown="1">
~~~java
public int getMethodCounter()
~~~
</div>
<h2><a class="anchor" name="getNextMethodCounter"></a>getNextMethodCounter</h2>
<div markdown="1">
~~~java
public int getNextMethodCounter()
~~~
</div>
<h2><a class="anchor" name="getNewVariableCounter"></a>getNewVariableCounter</h2>
<div markdown="1">
~~~java
public int getNewVariableCounter()
~~~
</div>
<h2><a class="anchor" name="getNextNewVariableCounter"></a>getNextNewVariableCounter</h2>
<div markdown="1">
~~~java
public int getNextNewVariableCounter()
~~~
</div>
<h2><a class="anchor" name="registerFirstInstruction"></a>registerFirstInstruction</h2>
<div markdown="1">
~~~java
public void registerFirstInstruction()
~~~
</div>
<h2><a class="anchor" name="registerLastInstruction"></a>registerLastInstruction</h2>
<div markdown="1">
~~~java
public void registerLastInstruction()
~~~
</div>
<h2><a class="anchor" name="registerLinenumbers"></a>registerLinenumbers</h2>
<div markdown="1">
~~~java
public void registerLinenumbers(BitSet linenumbers)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
linenumbers<br/><span class="paramtype">BitSet</span></td>
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
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#branchInstructions">branchInstructions</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#cacheHitCount">cacheHitCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#classPaths">classPaths</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#clearCache">clearCache</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#firstInstruction">firstInstruction</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#heavyCache">heavyCache</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#instructionCounter">instructionCounter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#instrumentedCount">instrumentedCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#instrumentedTime">instrumentedTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#jars">jars</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#lastInstruction">lastInstruction</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#lightCache">lightCache</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#linenumbers">linenumbers</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadTime">loadTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#methodCounter">methodCounter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#newVariableCounter">newVariableCounter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#postInstrumentedSize">postInstrumentedSize</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#preInstrumentedSize">preInstrumentedSize</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#requestCount">requestCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#showInstrumentation">showInstrumentation</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#uninstrumentedTime">uninstrumentedTime</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#writeClassfile">writeClassfile</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#InstrumentationClassManager">InstrumentationClassManager(COASTAL, String)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#createHeavyClassLoader">createHeavyClassLoader(SymbolicState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#createLightClassLoader">createLightClassLoader(TraceState)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#endLoad">endLoad(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getFirstInstruction">getFirstInstruction(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getInstructionCounter">getInstructionCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getJumpPoints">getJumpPoints(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getLastInstruction">getLastInstruction(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getLineNumbers">getLineNumbers(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getMethodCounter">getMethodCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getNewVariableCounter">getNewVariableCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getNextInstructionCounter">getNextInstructionCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getNextMethodCounter">getNextMethodCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#getNextNewVariableCounter">getNextNewVariableCounter()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadFile">loadFile(String, boolean, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadHeavyInstrumented">loadHeavyInstrumented(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadHeavyInstrumented0">loadHeavyInstrumented0(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadLightInstrumented">loadLightInstrumented(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadLightInstrumented0">loadLightInstrumented0(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadUninstrumented">loadUninstrumented(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#loadUninstrumented0">loadUninstrumented0(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#registerFirstInstruction">registerFirstInstruction()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#registerLastInstruction">registerLastInstruction()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#registerLinenumbers">registerLinenumbers(BitSet)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#report">report(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#searchFor">searchFor(String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#startLoad">startLoad()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/InstrumentationClassManager/' | relative_url }}#writeFile">writeFile(String, byte[])</a></li>
</ul>
</li>

</ul>
</section>
