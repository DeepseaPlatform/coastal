---
title: State
permalink: /api/State/
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
The contract that defines the behaviour of an abstract state.<h2><a class="anchor" name="FIELD_SEPARATOR"></a>FIELD_SEPARATOR</h2>
<div markdown="1">
~~~java
public static final String FIELD_SEPARATOR = "/"
~~~
</div>
<p>
Separator for fields. For example, the characters of a symbolic object with
 address 0 are called <code>0/a</code>, <code>0/b</code>, <code>0/c</code>, ...</p>
<h2><a class="anchor" name="INDEX_SEPARATOR"></a>INDEX_SEPARATOR</h2>
<div markdown="1">
~~~java
public static final String INDEX_SEPARATOR = "$"
~~~
</div>
<p>
Separator for array elements. For example, the elements of an array named
 <code>A</code> are called <code>A_D_0</code>, <code>A_D_1</code>, <code>A_D_2</code>, ...</p>
<h2><a class="anchor" name="CHAR_SEPARATOR"></a>CHAR_SEPARATOR</h2>
<div markdown="1">
~~~java
public static final String CHAR_SEPARATOR = "!"
~~~
</div>
<p>
Separator for string characters. For example, the characters of a string
 named <code>X</code> are called <code>X_H_0</code>, <code>X_H_1</code>, <code>X_H_2</code>, ...</p>
<h2><a class="anchor" name="NEW_VAR_PREFIX"></a>NEW_VAR_PREFIX</h2>
<div markdown="1">
~~~java
public static final String NEW_VAR_PREFIX = "$"
~~~
</div>
<p>
Prefix for new symbolic variables.</p>
<h2><a class="anchor" name="CREATE_VAR_PREFIX"></a>CREATE_VAR_PREFIX</h2>
<div markdown="1">
~~~java
public static final String CREATE_VAR_PREFIX = "N_D_"
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
protected final COASTAL coastal
~~~
</div>
<p>
The COASTAL that this state belongs to.</p>
<h2><a class="anchor" name="log"></a>log</h2>
<div markdown="1">
~~~java
protected final Logger log
~~~
</div>
<p>
Shortcut to the log instance.</p>
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
protected final messages.Broker broker
~~~
</div>
<p>
Shortcut to the broker instance.</p>
<h2><a class="anchor" name="input"></a>input</h2>
<div markdown="1">
~~~java
protected symbolic.Input input
~~~
</div>
<p>
The input that triggered this execution.
 
 This field is not final, because
 <code><a href="{{ '/api/za.ac.sun.cs.coastal.surfer.TraceState/' | relative_url }}">za.ac.sun.cs.coastal.surfer.TraceState</a>
</code> resets it without creating a
 new instance.</p>
<h2><a class="anchor" name="path"></a>path</h2>
<div markdown="1">
~~~java
protected symbolic.Path path
~~~
</div>
<p>
The path constructed for this execution thus far.</p>
<h2><a class="anchor" name="trackingMode"></a>trackingMode</h2>
<div markdown="1">
~~~java
private boolean trackingMode
~~~
</div>
<p>
Whether or not symbolic tracking is switched on/off. When on, the state
 mirrors the execution of the system-under-test.</p>
<h2><a class="anchor" name="recordingMode"></a>recordingMode</h2>
<div markdown="1">
~~~java
private boolean recordingMode
~~~
</div>
<p>
Whether or not symbolic recording is switched on/off. When on, symbolic
 information (such as path conditions) are recorded.</p>
<h2><a class="anchor" name="State(COASTAL, Input)"></a>State</h2>
<div markdown="1">
~~~java
public State(COASTAL coastal, symbolic.Input input)
~~~
</div>
Construct a new state to record an execution.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
the COASTAL instance that initiated the execution</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
input values for the run</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getTrackingMode()"></a>getTrackingMode</h2>
<div markdown="1">
~~~java
public final boolean getTrackingMode()
~~~
</div>
Return the value of the tracking mode flag.<h4>Returns</h4>
<p>
tracking mode flag</p>
<h2><a class="anchor" name="setTrackingMode(boolean)"></a>setTrackingMode</h2>
<div markdown="1">
~~~java
public final void setTrackingMode(boolean trackingMode)
~~~
</div>
Set a new value for the tracking mode flag.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
trackingMode<br/><span class="paramtype">boolean</span></td>
<td>
new value for the tracking mode flag</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getRecordingMode()"></a>getRecordingMode</h2>
<div markdown="1">
~~~java
public final boolean getRecordingMode()
~~~
</div>
Return the value of the recording mode flag.<h4>Returns</h4>
<p>
recording mode flag</p>
<h2><a class="anchor" name="setRecordingMode(boolean)"></a>setRecordingMode</h2>
<div markdown="1">
~~~java
public final void setRecordingMode(boolean recordingMode)
~~~
</div>
Set a new value for the recording mode flag.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
recordingMode<br/><span class="paramtype">boolean</span></td>
<td>
new value for the recording mode flag</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNewVariableName()"></a>getNewVariableName</h2>
<div markdown="1">
~~~java
public abstract String getNewVariableName()
~~~
</div>
Create a new unique variable name for a symbolic variable.<h4>Returns</h4>
<p>
new unique variable name</p>
<h2><a class="anchor" name="getStringLength(int)"></a>getStringLength</h2>
<div markdown="1">
~~~java
public abstract solver.Expression getStringLength(int stringId)
~~~
</div>
Return the symbolic value of the length of a given string.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
identifier for the string</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
symbolic expression for the length of the string</p>
<h2><a class="anchor" name="getStringChar(int, int)"></a>getStringChar</h2>
<div markdown="1">
~~~java
public abstract solver.Expression getStringChar(int stringId, int index)
~~~
</div>
Return the symbolic value of the character at a given index in a given
 string.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
identifier for the string</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the character</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
symbolic value for the character</p>
<h2><a class="anchor" name="push(Expression)"></a>push</h2>
<div markdown="1">
~~~java
public abstract void push(solver.Expression expr)
~~~
</div>
Push an expression onto the top of the expression stack of the topmost
 invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expr<br/><span class="paramtype">solver.Expression</span></td>
<td>
the expression to push onto the current expression stack</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pop()"></a>pop</h2>
<div markdown="1">
~~~java
public abstract solver.Expression pop()
~~~
</div>
Pop and return the expression on the top of the expression stack of the
 topmost invocation frame.<h4>Returns</h4>
<p>
the expression removed from the current expression stack</p>
<h2><a class="anchor" name="pushExtraCondition(Expression)"></a>pushExtraCondition</h2>
<div markdown="1">
~~~java
public abstract void pushExtraCondition(solver.Expression extraCondition)
~~~
</div>
Register an additional constraint that will be added to the passive conjunct
 at the next branching point.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
extraCondition<br/><span class="paramtype">solver.Expression</span></td>
<td>
constraint to add</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicBoolean(boolean, int)"></a>createSymbolicBoolean</h2>
<div markdown="1">
~~~java
public abstract boolean createSymbolicBoolean(boolean currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicByte(byte, int)"></a>createSymbolicByte</h2>
<div markdown="1">
~~~java
public abstract byte createSymbolicByte(byte currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicShort(short, int)"></a>createSymbolicShort</h2>
<div markdown="1">
~~~java
public abstract short createSymbolicShort(short currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicChar(char, int)"></a>createSymbolicChar</h2>
<div markdown="1">
~~~java
public abstract char createSymbolicChar(char currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicInt(int, int)"></a>createSymbolicInt</h2>
<div markdown="1">
~~~java
public abstract int createSymbolicInt(int currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicLong(long, int)"></a>createSymbolicLong</h2>
<div markdown="1">
~~~java
public abstract long createSymbolicLong(long currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicFloat(float, int)"></a>createSymbolicFloat</h2>
<div markdown="1">
~~~java
public abstract float createSymbolicFloat(float currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="createSymbolicDouble(double, int)"></a>createSymbolicDouble</h2>
<div markdown="1">
~~~java
public abstract double createSymbolicDouble(double currentValue, int uniqueId)
~~~
</div>
Create a new symbolic variable. It is the responsibility of the caller to
 ensure that the identifier is unique.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
<td>
default value to use in case no overriding value exists</td>
</tr>
<tr>
<td>
uniqueId<br/><span class="paramtype">int</span></td>
<td>
identifier to assign to the new variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
value for the new variable</p>
<h2><a class="anchor" name="getConcreteBoolean(int, int, int, boolean)"></a>getConcreteBoolean</h2>
<div markdown="1">
~~~java
public abstract boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue)
~~~
</div>
Return the value to use for a <code>boolean</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteByte(int, int, int, byte)"></a>getConcreteByte</h2>
<div markdown="1">
~~~java
public abstract byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue)
~~~
</div>
Return the value to use for a <code>byte</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteShort(int, int, int, short)"></a>getConcreteShort</h2>
<div markdown="1">
~~~java
public abstract short getConcreteShort(int triggerIndex, int index, int address, short currentValue)
~~~
</div>
Return the value to use for a <code>short</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteChar(int, int, int, char)"></a>getConcreteChar</h2>
<div markdown="1">
~~~java
public abstract char getConcreteChar(int triggerIndex, int index, int address, char currentValue)
~~~
</div>
Return the value to use for a <code>char</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteInt(int, int, int, int)"></a>getConcreteInt</h2>
<div markdown="1">
~~~java
public abstract int getConcreteInt(int triggerIndex, int index, int address, int currentValue)
~~~
</div>
Return the value to use for a <code>int</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteLong(int, int, int, long)"></a>getConcreteLong</h2>
<div markdown="1">
~~~java
public abstract long getConcreteLong(int triggerIndex, int index, int address, long currentValue)
~~~
</div>
Return the value to use for a <code>long</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteFloat(int, int, int, float)"></a>getConcreteFloat</h2>
<div markdown="1">
~~~java
public abstract float getConcreteFloat(int triggerIndex, int index, int address, float currentValue)
~~~
</div>
Return the value to use for a <code>float</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteDouble(int, int, int, double)"></a>getConcreteDouble</h2>
<div markdown="1">
~~~java
public abstract double getConcreteDouble(int triggerIndex, int index, int address, double currentValue)
~~~
</div>
Return the value to use for a <code>double</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteString(int, int, int, String)"></a>getConcreteString</h2>
<div markdown="1">
~~~java
public abstract String getConcreteString(int triggerIndex, int index, int address, String currentValue)
~~~
</div>
Return the value to use for a <code>String</code> variable.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">String</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the value to use during the execution</p>
<h2><a class="anchor" name="getConcreteBooleanArray(int, int, int, boolean[])"></a>getConcreteBooleanArray</h2>
<div markdown="1">
~~~java
public abstract boolean getConcreteBooleanArray(int triggerIndex, int index, int address, boolean currentValue)
~~~
</div>
Return the value to use for an array of <code>boolean</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteByteArray(int, int, int, byte[])"></a>getConcreteByteArray</h2>
<div markdown="1">
~~~java
public abstract byte getConcreteByteArray(int triggerIndex, int index, int address, byte currentValue)
~~~
</div>
Return the value to use for an array of <code>byte</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteShortArray(int, int, int, short[])"></a>getConcreteShortArray</h2>
<div markdown="1">
~~~java
public abstract short getConcreteShortArray(int triggerIndex, int index, int address, short currentValue)
~~~
</div>
Return the value to use for an array of <code>short</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteCharArray(int, int, int, char[])"></a>getConcreteCharArray</h2>
<div markdown="1">
~~~java
public abstract char getConcreteCharArray(int triggerIndex, int index, int address, char currentValue)
~~~
</div>
Return the value to use for an array of <code>char</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteIntArray(int, int, int, int[])"></a>getConcreteIntArray</h2>
<div markdown="1">
~~~java
public abstract int getConcreteIntArray(int triggerIndex, int index, int address, int currentValue)
~~~
</div>
Return the value to use for an array of <code>int</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteLongArray(int, int, int, long[])"></a>getConcreteLongArray</h2>
<div markdown="1">
~~~java
public abstract long getConcreteLongArray(int triggerIndex, int index, int address, long currentValue)
~~~
</div>
Return the value to use for an array of <code>long</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteFloatArray(int, int, int, float[])"></a>getConcreteFloatArray</h2>
<div markdown="1">
~~~java
public abstract float getConcreteFloatArray(int triggerIndex, int index, int address, float currentValue)
~~~
</div>
Return the value to use for an array of <code>float</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteDoubleArray(int, int, int, double[])"></a>getConcreteDoubleArray</h2>
<div markdown="1">
~~~java
public abstract double getConcreteDoubleArray(int triggerIndex, int index, int address, double currentValue)
~~~
</div>
Return the value to use for an array of <code>double</code> values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="getConcreteStringArray(int, int, int, String[])"></a>getConcreteStringArray</h2>
<div markdown="1">
~~~java
public abstract String getConcreteStringArray(int triggerIndex, int index, int address, String currentValue)
~~~
</div>
Return the value to use for an array of strings.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
index of the formal parameter</td>
</tr>
<tr>
<td>
address<br/><span class="paramtype">int</span></td>
<td>
local variable address where parameter is stored</td>
</tr>
<tr>
<td>
currentValue<br/><span class="paramtype">String</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the values to use during the execution</p>
<h2><a class="anchor" name="triggerMethod(int, int)"></a>triggerMethod</h2>
<div markdown="1">
~~~java
public abstract void triggerMethod(int methodNumber, int triggerIndex)
~~~
</div>
Switch on symbolic tracking because a trigger method has been invoked (and
 tracking is not currently switched on).<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodNumber<br/><span class="paramtype">int</span></td>
<td>
unique number of the method</td>
</tr>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the matching trigger</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="startMethod(int, int)"></a>startMethod</h2>
<div markdown="1">
~~~java
public abstract void startMethod(int methodNumber, int argCount)
~~~
</div>
Handle the invocation of a non-triggering method. Tracking may or may not be
 switched on.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodNumber<br/><span class="paramtype">int</span></td>
<td>
unique number of the method</td>
</tr>
<tr>
<td>
argCount<br/><span class="paramtype">int</span></td>
<td>
number of formal parameters for the method</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(boolean)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(boolean returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">boolean</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(char)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(char returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">char</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(double)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(double returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">double</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(float)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(float returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">float</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(int)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(int returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">int</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(long)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(long returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">long</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="returnValue(short)"></a>returnValue</h2>
<div markdown="1">
~~~java
public abstract void returnValue(short returnValue)
~~~
</div>
Ensure that the return value of a method is placed onto the top of the
 expression stack of the topmost invocation frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
returnValue<br/><span class="paramtype">short</span></td>
<td>
return value expression</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="linenumber(int, int)"></a>linenumber</h2>
<div markdown="1">
~~~java
public abstract void linenumber(int instr, int line)
~~~
</div>
Handle a line number declaration. Line numbers refer to the source file from
 which the class was compiler.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
line<br/><span class="paramtype">int</span></td>
<td>
the line number</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="label(int, String)"></a>label</h2>
<div markdown="1">
~~~java
public abstract void label(int instr, String label)
~~~
</div>
Handle a label.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction following the label</td>
</tr>
<tr>
<td>
label<br/><span class="paramtype">String</span></td>
<td>
a unique label identifier</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="insn(int, int)"></a>insn</h2>
<div markdown="1">
~~~java
public abstract void insn(int instr, int opcode)
~~~
</div>
Handle a zero operand instruction.
 
 These instructions are <code>NOP</code>, <code>ACONST_NULL</code>, <code>ICONST_M1</code>,
 <code>ICONST_0</code>, <code>ICONST_1</code>, <code>ICONST_2</code>, <code>ICONST_3</code>,
 <code>ICONST_4</code>, <code>ICONST_5</code>, <code>LCONST_0</code>, <code>LCONST_1</code>,
 <code>FCONST_0</code>, <code>FCONST_1</code>, <code>FCONST_2</code>, <code>DCONST_0</code>,
 <code>DCONST_1</code>, <code>IALOAD</code>, <code>LALOAD</code>, <code>FALOAD</code>,
 <code>DALOAD</code>, <code>AALOAD</code>, <code>BALOAD</code>, <code>CALOAD</code>,
 <code>SALOAD</code>, <code>IASTORE</code>, <code>LASTORE</code>, <code>FASTORE</code>,
 <code>DASTORE</code>, <code>AASTORE</code>, <code>BASTORE</code>, <code>CASTORE</code>,
 <code>SASTORE</code>, <code>POP</code>, <code>POP2</code>, <code>DUP</code>, <code>DUP_X1</code>,
 <code>DUP_X2</code>, <code>DUP2</code>, <code>DUP2_X1</code>, <code>DUP2_X2</code>, <code>SWAP</code>,
 <code>IADD</code>, <code>LADD</code>, <code>FADD</code>, <code>DADD</code>, <code>ISUB</code>,
 <code>LSUB</code>, <code>FSUB</code>, <code>DSUB</code>, <code>IMUL</code>, <code>LMUL</code>,
 <code>FMUL</code>, <code>DMUL</code>, <code>IDIV</code>, <code>LDIV</code>, <code>FDIV</code>,
 <code>DDIV</code>, <code>IREM</code>, <code>LREM</code>, <code>FREM</code>, <code>DREM</code>,
 <code>INEG</code>, <code>LNEG</code>, <code>FNEG</code>, <code>DNEG</code>, <code>ISHL</code>,
 <code>LSHL</code>, <code>ISHR</code>, <code>LSHR</code>, <code>IUSHR</code>, <code>LUSHR</code>,
 <code>IAND</code>, <code>LAND</code>, <code>IOR</code>, <code>LOR</code>, <code>IXOR</code>,
 <code>LXOR</code>, <code>I2L</code>, <code>I2F</code>, <code>I2D</code>, <code>L2I</code>,
 <code>L2F</code>, <code>L2D</code>, <code>F2I</code>, <code>F2L</code>, <code>F2D</code>, <code>D2I</code>,
 <code>D2L</code>, <code>D2F</code>, <code>I2B</code>, <code>I2C</code>, <code>I2S</code>,
 <code>LCMP</code>, <code>FCMPL</code>, <code>FCMPG</code>, <code>DCMPL</code>, <code>DCMPG</code>,
 <code>IRETURN</code>, <code>LRETURN</code>, <code>FRETURN</code>, <code>DRETURN</code>,
 <code>ARETURN</code>, <code>RETURN</code>, <code>ARRAYLENGTH</code>, <code>ATHROW</code>,
 <code>MONITORENTER</code>, <code>MONITOREXIT</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="intInsn(int, int, int)"></a>intInsn</h2>
<div markdown="1">
~~~java
public abstract void intInsn(int instr, int opcode, int operand)
~~~
</div>
Handle an instruction with a single <code>int</code> operand.

 These instructions are <code>BIPUSH</code>, <code>SIPUSH</code>, <code>NEWARRAY</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
<tr>
<td>
operand<br/><span class="paramtype">int</span></td>
<td>
the operand of the instruction</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="varInsn(int, int, int)"></a>varInsn</h2>
<div markdown="1">
~~~java
public abstract void varInsn(int instr, int opcode, int var)
~~~
</div>
Handle a local variable instruction. A local variable instruction is an
 instruction that loads or stores the value of a local variable.
 
 These instructions are <code>ILOAD</code>, <code>LLOAD</code>, <code>FLOAD</code>,
 <code>DLOAD</code>, <code>ALOAD</code>, <code>ISTORE</code>, <code>LSTORE</code>, <code>FSTORE</code>,
 <code>DSTORE</code>, <code>ASTORE</code>, <code>RET</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
<tr>
<td>
var<br/><span class="paramtype">int</span></td>
<td>
the identifier of the local variable</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="typeInsn(int, int)"></a>typeInsn</h2>
<div markdown="1">
~~~java
public abstract void typeInsn(int instr, int opcode)
~~~
</div>
Handle a type instruction. A type instruction is an instruction that takes
 the internal name of a class as parameter.
 
 These instructions are <code>NEW</code>, <code>ANEWARRAY</code>, <code>CHECKCAST</code>,
 <code>INSTANCEOF</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="fieldInsn(int, int, String, String, String)"></a>fieldInsn</h2>
<div markdown="1">
~~~java
public abstract void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
~~~
</div>
Handle a field instruction. A field instruction is an instruction that loads
 or stores the value of a field of an object.
 
 These instructions are <code>GETSTATIC</code>, <code>PUTSTATIC</code>,
 <code>GETFIELD</code>, <code>PUTFIELD</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
<tr>
<td>
owner<br/><span class="paramtype">String</span></td>
<td>
the internal name of the field's owner class</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the name of the field</td>
</tr>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the type descriptor of the field</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="methodInsn(int, int, String, String, String)"></a>methodInsn</h2>
<div markdown="1">
~~~java
public abstract void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
~~~
</div>
Handle a method instruction. A method instruction is an instruction that
 invokes a method.
 
 These instructions are <code>INVOKEVIRTUAL</code>, <code>INVOKESPECIAL</code>,
 <code>INVOKESTATIC</code>, <code>INVOKEINTERFACE</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
<tr>
<td>
owner<br/><span class="paramtype">String</span></td>
<td>
the internal name of the method's owner class</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
the name of the method</td>
</tr>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the type descriptor of the method</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="invokeDynamicInsn(int, int)"></a>invokeDynamicInsn</h2>
<div markdown="1">
~~~java
public abstract void invokeDynamicInsn(int instr, int opcode)
~~~
</div>
Handle an <code>INVOKEDYNAMIC</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public abstract void jumpInsn(int instr, int opcode)
~~~
</div>
Handle a jump instruction. A jump instruction is an instruction that may jump
 to another instruction. When the instruction is emitted by instrumentation,
 the exact offset is not available, which is why the destination (either as an
 offset of as a label) is not included as a parameter.
 
 These instructions are <code>IFEQ</code>, <code>IFNE</code>, <code>IFLT</code>,
 <code>IFGE</code>, <code>IFGT</code>, <code>IFLE</code>, <code>IF_ICMPEQ</code>,
 <code>IF_ICMPNE</code>, <code>IF_ICMPLT</code>, <code>IF_ICMPGE</code>, <code>IF_ICMPGT</code>,
 <code>IF_ICMPLE</code>, <code>IF_ACMPEQ</code>, <code>IF_ACMPNE</code>, <code>GOTO</code>,
 <code>JSR</code>, <code>IFNULL</code>, <code>IFNONNULL</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public abstract void jumpInsn(int value, int instr, int opcode)
~~~
</div>
Handle a jump instruction where the operand used in the comparison is a
 concrete integer. In the case of a comparison, the other operand is zero.
 This is used to record information about the choices made during an
 execution.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">int</span></td>
<td>
concrete value of one operand</td>
</tr>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(Object, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public abstract void jumpInsn(Object value, int instr, int opcode)
~~~
</div>
Handle a jump instruction where the operand used in the comparison is a
 concrete object. This is used to record information about the choices made
 during an execution.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
concrete value of one operand</td>
</tr>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="jumpInsn(int, int, int, int)"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public abstract void jumpInsn(int value1, int value2, int instr, int opcode)
~~~
</div>
Handle a jump instruction where both the operands used in the comparison are
 concrete. This is used to record information about the choices made during an
 execution.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value1<br/><span class="paramtype">int</span></td>
<td>
concrete value of first operand</td>
</tr>
<tr>
<td>
value2<br/><span class="paramtype">int</span></td>
<td>
concrete value of second operand</td>
</tr>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="postJumpInsn(int, int)"></a>postJumpInsn</h2>
<div markdown="1">
~~~java
public abstract void postJumpInsn(int instr, int opcode)
~~~
</div>
Handle the situation where a jump has *NOT* taken place.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ldcInsn(int, int, Object)"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public abstract void ldcInsn(int instr, int opcode, Object value)
~~~
</div>
Handle a <code>LDC</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
instruction opcode</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
constant to be loaded on the stack</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="iincInsn(int, int, int)"></a>iincInsn</h2>
<div markdown="1">
~~~java
public abstract void iincInsn(int instr, int var, int increment)
~~~
</div>
Handle an <code>IINC</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
number of the instruction</td>
</tr>
<tr>
<td>
var<br/><span class="paramtype">int</span></td>
<td>
index of the local variable to be incremented</td>
</tr>
<tr>
<td>
increment<br/><span class="paramtype">int</span></td>
<td>
amount to increment the local variable by</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tableSwitchInsn(int, int)"></a>tableSwitchInsn</h2>
<div markdown="1">
~~~java
public abstract void tableSwitchInsn(int instr, int opcode)
~~~
</div>
Handle a <code>TABLESWITCH</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tableCaseInsn(int, int, int)"></a>tableCaseInsn</h2>
<div markdown="1">
~~~java
public abstract void tableCaseInsn(int min, int max, int value)
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
public abstract void lookupSwitchInsn(int instr, int opcode)
~~~
</div>
Handle a <code>LOOKUPSWITCH</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="multiANewArrayInsn(int, int)"></a>multiANewArrayInsn</h2>
<div markdown="1">
~~~java
public abstract void multiANewArrayInsn(int instr, int opcode)
~~~
</div>
Handle a <code>MULTIANEWARRAY</code> instruction.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the number of the instruction</td>
</tr>
<tr>
<td>
opcode<br/><span class="paramtype">int</span></td>
<td>
the instruction opcode</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="noException()"></a>noException</h2>
<div markdown="1">
~~~java
public abstract void noException()
~~~
</div>
Handle the case that no exception occurs after an instruction that could
 potentially raise an exception<h2><a class="anchor" name="startCatch(int)"></a>startCatch</h2>
<div markdown="1">
~~~java
public abstract void startCatch(int instr)
~~~
</div>
Handle the case where the execution of an instruction has raised an
 exception.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
instr<br/><span class="paramtype">int</span></td>
<td>
the instruction that caused the exception</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="stop()"></a>stop</h2>
<div markdown="1">
~~~java
public abstract void stop()
~~~
</div>
Handle the situation where the execution has reached a call to
 <code><a href="{{ '/api/Symbolic/' | relative_url }}#stop()">Symbolic#stop()</a>
</code>.<h2><a class="anchor" name="stop(String)"></a>stop</h2>
<div markdown="1">
~~~java
public abstract void stop(String message)
~~~
</div>
Handle the situation where the execution has reached a call to
 <code><a href="{{ '/api/Symbolic/' | relative_url }}#stop(String)">Symbolic#stop(String)</a>
</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
message<br/><span class="paramtype">String</span></td>
<td>
a message passed from the call</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(int)"></a>mark</h2>
<div markdown="1">
~~~java
public abstract void mark(int marker)
~~~
</div>
Handle the situation where the execution has reached a symbolic marker. This
 is usually the result of a call to <code><a href="{{ '/api/Symbolic/' | relative_url }}#mark(int)">Symbolic#mark(int)</a>
</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">int</span></td>
<td>
the marker identity as an integer</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mark(String)"></a>mark</h2>
<div markdown="1">
~~~java
public abstract void mark(String marker)
~~~
</div>
Handle the situation where the execution has reached a symbolic marker. This
 is usually the result of a call to <code><a href="{{ '/api/Symbolic/' | relative_url }}#mark(String)">Symbolic#mark(String)</a>
</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
marker<br/><span class="paramtype">String</span></td>
<td>
the marker identity as a string</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="printPC()"></a>printPC</h2>
<div markdown="1">
~~~java
public abstract void printPC()
~~~
</div>
Handle the situation where the execution has reached a call to
 <code><a href="{{ '/api/Symbolic/' | relative_url }}#printPC()">Symbolic#printPC()</a>
</code>.<h2><a class="anchor" name="printPC(String)"></a>printPC</h2>
<div markdown="1">
~~~java
public abstract void printPC(String label)
~~~
</div>
Handle the situation where the execution has reached a call to
 <code><a href="{{ '/api/Symbolic/' | relative_url }}#printPC(String)">Symbolic#printPC(String)</a>
</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
label<br/><span class="paramtype">String</span></td>
<td>
a message passed from the call</td>
</tr>
</tbody>
</table>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#CHAR_SEPARATOR">CHAR_SEPARATOR</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#CREATE_VAR_PREFIX">CREATE_VAR_PREFIX</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#FIELD_SEPARATOR">FIELD_SEPARATOR</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#INDEX_SEPARATOR">INDEX_SEPARATOR</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#NEW_VAR_PREFIX">NEW_VAR_PREFIX</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#input">input</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#log">log</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#path">path</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#recordingMode">recordingMode</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#trackingMode">trackingMode</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#State(COASTAL, Input)">State(COASTAL, Input)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicBoolean(boolean, int)">createSymbolicBoolean(boolean, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicByte(byte, int)">createSymbolicByte(byte, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicChar(char, int)">createSymbolicChar(char, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicDouble(double, int)">createSymbolicDouble(double, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicFloat(float, int)">createSymbolicFloat(float, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicInt(int, int)">createSymbolicInt(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicLong(long, int)">createSymbolicLong(long, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#createSymbolicShort(short, int)">createSymbolicShort(short, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#fieldInsn(int, int, String, String, String)">fieldInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteBoolean(int, int, int, boolean)">getConcreteBoolean(int, int, int, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteBooleanArray(int, int, int, boolean[])">getConcreteBooleanArray(int, int, int, boolean[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteByte(int, int, int, byte)">getConcreteByte(int, int, int, byte)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteByteArray(int, int, int, byte[])">getConcreteByteArray(int, int, int, byte[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteChar(int, int, int, char)">getConcreteChar(int, int, int, char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteCharArray(int, int, int, char[])">getConcreteCharArray(int, int, int, char[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteDouble(int, int, int, double)">getConcreteDouble(int, int, int, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteDoubleArray(int, int, int, double[])">getConcreteDoubleArray(int, int, int, double[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteFloat(int, int, int, float)">getConcreteFloat(int, int, int, float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteFloatArray(int, int, int, float[])">getConcreteFloatArray(int, int, int, float[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteInt(int, int, int, int)">getConcreteInt(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteIntArray(int, int, int, int[])">getConcreteIntArray(int, int, int, int[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteLong(int, int, int, long)">getConcreteLong(int, int, int, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteLongArray(int, int, int, long[])">getConcreteLongArray(int, int, int, long[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteShort(int, int, int, short)">getConcreteShort(int, int, int, short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteShortArray(int, int, int, short[])">getConcreteShortArray(int, int, int, short[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteString(int, int, int, String)">getConcreteString(int, int, int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getConcreteStringArray(int, int, int, String[])">getConcreteStringArray(int, int, int, String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getNewVariableName()">getNewVariableName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getRecordingMode()">getRecordingMode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getStringChar(int, int)">getStringChar(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getStringLength(int)">getStringLength(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#getTrackingMode()">getTrackingMode()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#iincInsn(int, int, int)">iincInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#insn(int, int)">insn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#intInsn(int, int, int)">intInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#invokeDynamicInsn(int, int)">invokeDynamicInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#jumpInsn(int, int)">jumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#jumpInsn(int, int, int)">jumpInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#jumpInsn(int, int, int, int)">jumpInsn(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#jumpInsn(Object, int, int)">jumpInsn(Object, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#label(int, String)">label(int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#ldcInsn(int, int, Object)">ldcInsn(int, int, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#linenumber(int, int)">linenumber(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#lookupSwitchInsn(int, int)">lookupSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#mark(int)">mark(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#mark(String)">mark(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#methodInsn(int, int, String, String, String)">methodInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#multiANewArrayInsn(int, int)">multiANewArrayInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#noException()">noException()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#pop()">pop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#postJumpInsn(int, int)">postJumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#printPC()">printPC()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#printPC(String)">printPC(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#push(Expression)">push(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#pushExtraCondition(Expression)">pushExtraCondition(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(boolean)">returnValue(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(char)">returnValue(char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(double)">returnValue(double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(float)">returnValue(float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(int)">returnValue(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(long)">returnValue(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#returnValue(short)">returnValue(short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#setRecordingMode(boolean)">setRecordingMode(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#setTrackingMode(boolean)">setTrackingMode(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#startCatch(int)">startCatch(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#startMethod(int, int)">startMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#stop()">stop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#stop(String)">stop(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#tableCaseInsn(int, int, int)">tableCaseInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#tableSwitchInsn(int, int)">tableSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#triggerMethod(int, int)">triggerMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#typeInsn(int, int)">typeInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/State/' | relative_url }}#varInsn(int, int, int)">varInsn(int, int, int)</a></li>
</ul>
</li>

</ul>
</section>
