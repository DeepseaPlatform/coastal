---
title: SymbolicState
permalink: /api/SymbolicState/
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
Implementation of abstract states for an execution. An instance of a symbolic
 state mirrors a true execution, but captures only the information that is
 required to perform concolic execution.<h2><a class="anchor" name="DELEGATE_PARAMETERS"></a>DELEGATE_PARAMETERS</h2>
<div markdown="1">
~~~java
private static final Class DELEGATE_PARAMETERS
~~~
</div>
<p>
A convenience field. It is an array of the types of the parameters of a
 delegate method.</p>
<h2><a class="anchor" name="limitConjuncts"></a>limitConjuncts</h2>
<div markdown="1">
~~~java
private final long limitConjuncts
~~~
</div>
<p>
The limit on path lengths. In other words, this is the maximum number of
 branches per path.</p>
<h2><a class="anchor" name="trackAll"></a>trackAll</h2>
<div markdown="1">
~~~java
private final boolean trackAll
~~~
</div>
<p>
Whether or not the program state should be tracked from the very beginning of
 the execution, or whether it should only be switched on when a trigger method
 is invoked.</p>
<h2><a class="anchor" name="dangerFlag"></a>dangerFlag</h2>
<div markdown="1">
~~~java
private boolean dangerFlag
~~~
</div>
<p>
Whether or not the current path is in danger of exceeding the path length
 limit.</p>
<h2><a class="anchor" name="mayRecord"></a>mayRecord</h2>
<div markdown="1">
~~~java
private boolean mayRecord
~~~
</div>
<p>
Whether or not record mode may be switched on. Initially this is true, but
 once a trigger has been fully explored, it is set to false, so that
 subsequent triggers cannot reactivate symbolic recording. This is important,
 because the symbolic information of such a second activation could interfere
 with the information of a first activation, rendering both sets of
 information unusable.</p>
<h2><a class="anchor" name="frames"></a>frames</h2>
<div markdown="1">
~~~java
private final Stack frames
~~~
</div>
<p>
A symbolic representation of the method invocation stack of the true
 execution.</p>
<h2><a class="anchor" name="objectIdCount"></a>objectIdCount</h2>
<div markdown="1">
~~~java
private int objectIdCount
~~~
</div>
<p>
The number of objects created during this execution.</p>
<h2><a class="anchor" name="newVariableCount"></a>newVariableCount</h2>
<div markdown="1">
~~~java
private int newVariableCount
~~~
</div>
<p>
The number of symbolic variables created during this execution.</p>
<h2><a class="anchor" name="instanceData"></a>instanceData</h2>
<div markdown="1">
~~~java
private final Map instanceData
~~~
</div>
<p>
A store for data about the heap and static variables.
 
 TO DO: Add a description of of the mapping. Give examples.</p>
<h2><a class="anchor" name="pendingExtraCondition"></a>pendingExtraCondition</h2>
<div markdown="1">
~~~java
private solver.Expression pendingExtraCondition
~~~
</div>
<p>
Extra constraints that are added to the next branching point as the "passive
 conjunct".
 
 TO DO: Add a description of circumstances that create such constraints.</p>
<h2><a class="anchor" name="noExceptionExpression"></a>noExceptionExpression</h2>
<div markdown="1">
~~~java
private final List noExceptionExpression
~~~
</div>
<p>
List of conditions that describe that no exception has taken place since the
 last branching point.
 
 TO DO: Add more detail about how this conditions are used.</p>
<h2><a class="anchor" name="exceptionDepth"></a>exceptionDepth</h2>
<div markdown="1">
~~~java
private int exceptionDepth
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="throwable"></a>throwable</h2>
<div markdown="1">
~~~java
private solver.Expression throwable
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="triggeringIndex"></a>triggeringIndex</h2>
<div markdown="1">
~~~java
private int triggeringIndex
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="mayContinue"></a>mayContinue</h2>
<div markdown="1">
~~~java
private boolean mayContinue
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="justExecutedDelegate"></a>justExecutedDelegate</h2>
<div markdown="1">
~~~java
private boolean justExecutedDelegate
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="lastInvokingInstruction"></a>lastInvokingInstruction</h2>
<div markdown="1">
~~~java
private int lastInvokingInstruction
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="pendingSwitch"></a>pendingSwitch</h2>
<div markdown="1">
~~~java
private final Stack pendingSwitch
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="delegateArguments"></a>delegateArguments</h2>
<div markdown="1">
~~~java
private final Object delegateArguments
~~~
</div>
<p>
A convenience field. It is an array of the actual parameters passed to a
 delegate method.</p>
<h2><a class="anchor" name="params"></a>params</h2>
<div markdown="1">
~~~java
private final Stack params
~~~
</div>
<p>
A small stack where method parameters are stored temporarily.</p>
<h2><a class="anchor" name="SymbolicState"></a>SymbolicState</h2>
<div markdown="1">
~~~java
public SymbolicState(COASTAL coastal, symbolic.Input input)
~~~
</div>
Create a new instance of the symbolic state.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
instance of COASTAL that started this run</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
input values for the run</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mayContinue"></a>mayContinue</h2>
<div markdown="1">
~~~java
public boolean mayContinue()
~~~
</div>
Return the value of the <a href="{{ '/api//' | relative_url }}#mayContinue">#mayContinue</a> flag that indicates whether clients (such as diver, surfers, and strategies) should continue work on the problem.<h4>Returns</h4>
<p>
value of the <a href="{{ '/api//' | relative_url }}#mayContinue">#mayContinue</a> flag</p>
<h2><a class="anchor" name="discontinue"></a>discontinue</h2>
<div markdown="1">
~~~java
public void discontinue()
~~~
</div>
Set the <a href="{{ '/api//' | relative_url }}#mayContinue">#mayContinue</a> flag to <code>false</code>.<h2><a class="anchor" name="getExecution"></a>getExecution</h2>
<div markdown="1">
~~~java
public symbolic.Execution getExecution()
~~~
</div>
Return the result of the execution.<h4>Returns</h4>
<p>
result of the execution</p>
<h2><a class="anchor" name="getLocal"></a>getLocal</h2>
<div markdown="1">
~~~java
private solver.Expression getLocal(int index)
~~~
</div>
Return the symbolic value of a local variable in the topmost invocation
 frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index of the variable</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the symbolic value of the local variable</p>
<h2><a class="anchor" name="setLocal"></a>setLocal</h2>
<div markdown="1">
~~~java
private void setLocal(int index, solver.Expression value)
~~~
</div>
Return the symbolic value of a local variable in the topmost invocation
 frame.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index of the variable</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
<td>
the new symbolic value of the local variable</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="putField"></a>putField</h2>
<div markdown="1">
~~~java
private void putField(int objectId, String fieldName, solver.Expression value)
~~~
</div>
Update the symbolic value of a field in a particular object instance.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
objectId<br/><span class="paramtype">int</span></td>
<td>
the identifier of the object</td>
</tr>
<tr>
<td>
fieldName<br/><span class="paramtype">String</span></td>
<td>
the name of the field</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
<td>
the new symbolic expression for the field</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="putField"></a>putField</h2>
<div markdown="1">
~~~java
private void putField(String objectName, String fieldName, solver.Expression value)
~~~
</div>
Update the symbolic value of a field in a particular object instance.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
objectName<br/><span class="paramtype">String</span></td>
<td>
the name of the object</td>
</tr>
<tr>
<td>
fieldName<br/><span class="paramtype">String</span></td>
<td>
the name of the field</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
<td>
the new symbolic expression for the field</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getField"></a>getField</h2>
<div markdown="1">
~~~java
private solver.Expression getField(int objectId, String fieldName)
~~~
</div>
Return the symbolic value of a field in a particular object instance.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
objectId<br/><span class="paramtype">int</span></td>
<td>
the identifier of the object</td>
</tr>
<tr>
<td>
fieldName<br/><span class="paramtype">String</span></td>
<td>
the name of the field</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the symbolic expression for the value of the field</p>
<h2><a class="anchor" name="getField"></a>getField</h2>
<div markdown="1">
~~~java
private solver.Expression getField(String objectName, String fieldName)
~~~
</div>
Return the symbolic value of a field in a particular object instance. The
 object is identifier by its "name", which could simply be a string version of
 the object number, or a more complex name.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
objectName<br/><span class="paramtype">String</span></td>
<td>
the name of the object</td>
</tr>
<tr>
<td>
fieldName<br/><span class="paramtype">String</span></td>
<td>
the name of the field</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the symbolic expression for the value of the field</p>
<h2><a class="anchor" name="methodReturn"></a>methodReturn</h2>
<div markdown="1">
~~~java
private boolean methodReturn()
~~~
</div>
Handle the termination of a method. The return value is handled elsewhere.
 The task of this method is to potentially switch off the symbolic tracking
 flag.<h4>Returns</h4>
<p>
<code>true</code> if and only if symbolic tracking mode is still switched
         on</p>
<h2><a class="anchor" name="incrAndGetNewObjectId"></a>incrAndGetNewObjectId</h2>
<div markdown="1">
~~~java
private int incrAndGetNewObjectId()
~~~
</div>
Increment and return the object counter.<h4>Returns</h4>
<p>
the new value of the object counter</p>
<h2><a class="anchor" name="dumpFrames"></a>dumpFrames</h2>
<div markdown="1">
~~~java
private void dumpFrames()
~~~
</div>
Dump the stack of invocation frames to the log.<h2><a class="anchor" name="executeDelegate"></a>executeDelegate</h2>
<div markdown="1">
~~~java
private boolean executeDelegate(String owner, String name, String descriptor)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
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
<h2><a class="anchor" name="checkLimitConjuncts"></a>checkLimitConjuncts</h2>
<div markdown="1">
~~~java
private void checkLimitConjuncts()
~~~
</div>
If the danger flag is switched on, check if the current path reaches or
 exceeds the path length limit. If so, an exception is raised. If the danger
 flag is set, it is reset to <code>false</code>.<h2><a class="anchor" name="getArgumentCount"></a>getArgumentCount</h2>
<div markdown="1">
~~~java
private static int getArgumentCount(String descriptor)
~~~
</div>
Parse a Java type descriptor of a method and count and return the number of
 formal parameters.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the type descriptor of a method</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the number of formal arguments</p>
<h2><a class="anchor" name="getReturnType"></a>getReturnType</h2>
<div markdown="1">
~~~java
public static String getReturnType(String descriptor)
~~~
</div>
Extract and return the return type from a Java type descriptor of a method.
 The return type is returned in the Java type descriptor format and is not
 converted to an integer code or even an instance of <a href="{{ '/api/Class/' | relative_url }}">Class</a>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the type descriptor of a method</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a type descriptor for the return type of the method as specified in
         the given type descriptor</p>
<h2><a class="anchor" name="getAsciiSignature"></a>getAsciiSignature</h2>
<div markdown="1">
~~~java
public static String getAsciiSignature(String descriptor)
~~~
</div>
Return the sanitized type descriptor for a method. This is used to convert a
 Java type descriptor string to a name that can be given to a method.
 
 The method simply replaces "illegal" characters with legal characters:
 
 <ul>
 <li>"<code>/</code>" is replaced with "<code>_</code>"</li>
 <li>"<code>_</code>" is replaced with "<code>_1</code>"</li>
 <li>"<code>;</code>" is replaced with "<code>_2</code>"</li>
 <li>"<code>[</code>" is replaced with "<code>_3</code>"</li>
 <li>"<code>(</code>" and "<code>)</code>" are replaced with "<code>__</code>"
 </li>
 </ul><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
descriptor<br/><span class="paramtype">String</span></td>
<td>
the original Java type descriptor</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the sanitized type descriptor</p>
<h2><a class="anchor" name="pushReturnValue"></a>pushReturnValue</h2>
<div markdown="1">
~~~java
private void pushReturnValue(char type)
~~~
</div>
Push a new symbolic variable onto the top of the expression stack of the
 topmost invocation frame to represent the return value of an untracked
 method. The nature of the symbolic variable is based on the Java type
 descriptor of the method's return type.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
type<br/><span class="paramtype">char</span></td>
<td>
the method's return type as a Java type descriptor</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getNewVariableName"></a>getNewVariableName</h2>
<div markdown="1">
~~~java
public String getNewVariableName()
~~~
</div>
<h2><a class="anchor" name="createArray"></a>createArray</h2>
<div markdown="1">
~~~java
private int createArray()
~~~
</div>
Create a new array. Arrays are just objects, and this method simply
 increments and returns the object counter.<h4>Returns</h4>
<p>
the new array identifier</p>
<h2><a class="anchor" name="setArrayType"></a>setArrayType</h2>
<div markdown="1">
~~~java
private void setArrayType(int arrayId, int type)
~~~
</div>
Set the type of the elements of an array.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
arrayId<br/><span class="paramtype">int</span></td>
<td>
the array identifier</td>
</tr>
<tr>
<td>
type<br/><span class="paramtype">int</span></td>
<td>
the type of the array elements</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setArrayLength"></a>setArrayLength</h2>
<div markdown="1">
~~~java
private void setArrayLength(int arrayId, int length)
~~~
</div>
Set the length of an array.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
arrayId<br/><span class="paramtype">int</span></td>
<td>
the array identifier</td>
</tr>
<tr>
<td>
length<br/><span class="paramtype">int</span></td>
<td>
the length of the array</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getArrayValue"></a>getArrayValue</h2>
<div markdown="1">
~~~java
private solver.Expression getArrayValue(int arrayId, int index)
~~~
</div>
Return the symbolic value stored in an array at a particular index.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
arrayId<br/><span class="paramtype">int</span></td>
<td>
the array identifier</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index of the element</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the symbolic element value in the array</p>
<h2><a class="anchor" name="setArrayValue"></a>setArrayValue</h2>
<div markdown="1">
~~~java
private void setArrayValue(int arrayId, int index, solver.Expression value)
~~~
</div>
Set the symbolic value stored in an array at a particular index.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
arrayId<br/><span class="paramtype">int</span></td>
<td>
the array identifier</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index of the element</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
<td>
the new symbolic value of the array element</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createString"></a>createString</h2>
<div markdown="1">
~~~java
private int createString()
~~~
</div>
Create a new string. Strings are just objects, and this method simply
 increments and returns the object counter.<h4>Returns</h4>
<p>
new string identifier</p>
<h2><a class="anchor" name="getStringLength"></a>getStringLength</h2>
<div markdown="1">
~~~java
public solver.Expression getStringLength(int stringId)
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
<h2><a class="anchor" name="setStringLength"></a>setStringLength</h2>
<div markdown="1">
~~~java
private void setStringLength(int stringId, solver.Expression length)
~~~
</div>
Set the length for a string instance.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
unique string identifier</td>
</tr>
<tr>
<td>
length<br/><span class="paramtype">solver.Expression</span></td>
<td>
new length</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getStringChar"></a>getStringChar</h2>
<div markdown="1">
~~~java
public solver.Expression getStringChar(int stringId, int index)
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
<h2><a class="anchor" name="setStringChar"></a>setStringChar</h2>
<div markdown="1">
~~~java
private void setStringChar(int stringId, int index, solver.Expression value)
~~~
</div>
Set a character inside a string.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
stringId<br/><span class="paramtype">int</span></td>
<td>
unique string identifier</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
character index to set</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">solver.Expression</span></td>
<td>
value for the character</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="push"></a>push</h2>
<div markdown="1">
~~~java
public void push(solver.Expression expr)
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
<h2><a class="anchor" name="pop"></a>pop</h2>
<div markdown="1">
~~~java
public solver.Expression pop()
~~~
</div>
<h2><a class="anchor" name="peek"></a>peek</h2>
<div markdown="1">
~~~java
private solver.Expression peek()
~~~
</div>
Return (but do not remove) the expression on the top of the expression stack
 of the topmost invocation frame.<h4>Returns</h4>
<p>
the expression on top of the expression stack</p>
<h2><a class="anchor" name="pushConjunct"></a>pushConjunct</h2>
<div markdown="1">
~~~java
private void pushConjunct(solver.Expression conjunct, boolean truthValue)
~~~
</div>
Add a binary conjunct to the current path. This also adds the accumulated
 passive conjuncts to the current path.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
conjunct<br/><span class="paramtype">solver.Expression</span></td>
<td>
the conjunct to add</td>
</tr>
<tr>
<td>
truthValue<br/><span class="paramtype">boolean</span></td>
<td>
the value of the conjunct</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pushConjunct"></a>pushConjunct</h2>
<div markdown="1">
~~~java
private void pushConjunct(solver.Expression conjunct)
~~~
</div>
Add a binary conjunct to the current path. The conjunct is assumed to be
 true.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
conjunct<br/><span class="paramtype">solver.Expression</span></td>
<td>
the conjunct to add</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pushConjunct"></a>pushConjunct</h2>
<div markdown="1">
~~~java
private void pushConjunct(solver.Expression expression, long min, long max, long cur)
~~~
</div>
Add an n-ary conjunct to the current path. This also adds the accumulated
 passive conjuncts to the current path.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
expression<br/><span class="paramtype">solver.Expression</span></td>
<td>
the expression that determines the choice at this branching
                   point</td>
</tr>
<tr>
<td>
min<br/><span class="paramtype">long</span></td>
<td>
the smallest value handled by the n-ary branching point</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">long</span></td>
<td>
the largest value handled by the n-ary branching point</td>
</tr>
<tr>
<td>
cur<br/><span class="paramtype">long</span></td>
<td>
the current value taken at the n-ary branching point</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pushExtraCondition"></a>pushExtraCondition</h2>
<div markdown="1">
~~~java
public void pushExtraCondition(solver.Expression extraCondition)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
extraCondition<br/><span class="paramtype">solver.Expression</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="createSymbolicBoolean"></a>createSymbolicBoolean</h2>
<div markdown="1">
~~~java
public boolean createSymbolicBoolean(boolean currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">boolean</span></td>
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
<h2><a class="anchor" name="createSymbolicByte"></a>createSymbolicByte</h2>
<div markdown="1">
~~~java
public byte createSymbolicByte(byte currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">byte</span></td>
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
<h2><a class="anchor" name="createSymbolicShort"></a>createSymbolicShort</h2>
<div markdown="1">
~~~java
public short createSymbolicShort(short currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">short</span></td>
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
<h2><a class="anchor" name="createSymbolicChar"></a>createSymbolicChar</h2>
<div markdown="1">
~~~java
public char createSymbolicChar(char currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">char</span></td>
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
<h2><a class="anchor" name="createSymbolicInt"></a>createSymbolicInt</h2>
<div markdown="1">
~~~java
public int createSymbolicInt(int currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">int</span></td>
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
<h2><a class="anchor" name="createSymbolicLong"></a>createSymbolicLong</h2>
<div markdown="1">
~~~java
public long createSymbolicLong(long currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">long</span></td>
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
<h2><a class="anchor" name="createSymbolicFloat"></a>createSymbolicFloat</h2>
<div markdown="1">
~~~java
public float createSymbolicFloat(float currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">float</span></td>
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
<h2><a class="anchor" name="createSymbolicDouble"></a>createSymbolicDouble</h2>
<div markdown="1">
~~~java
public double createSymbolicDouble(double currentValue, int uniqueId)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
currentValue<br/><span class="paramtype">double</span></td>
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
<h2><a class="anchor" name="getConcreteIntegral"></a>getConcreteIntegral</h2>
<div markdown="1">
~~~java
private long getConcreteIntegral(int triggerIndex, int index, int address, int sizeInBits, long currentValue)
~~~
</div>
Compute a value to use during the execution. This method caters for
 <code>byte</code>, <code>short</code>, <code>char</code>, <code>int</code>, and <code>long</code>
 values.
 
 If the trigger description that the formal parameter must be symbolic, a new
 symbolic variable is created with the appropriate bounds. (The
 <code>sizeInBits</code> is needed to create the symbolic variable because the
 solver is bit-vector based.) If a new input value is provided to use in the
 execution, the symbolic variable is set to that values. If not, the default
 value (that the system-under-test would have used naturally), is used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the triggering method</td>
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
sizeInBits<br/><span class="paramtype">int</span></td>
<td>
size of the desired type</td>
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
value that will be used during the execution</p>
<h2><a class="anchor" name="getConcreteReal"></a>getConcreteReal</h2>
<div markdown="1">
~~~java
private double getConcreteReal(int triggerIndex, int index, int address, int sizeInBits, double currentValue)
~~~
</div>
Compute a value to use during the execution. This method caters for
 <code>float</code> and <code>double</code> values.
 
 If the trigger description that the formal parameter must be symbolic, a new
 symbolic variable is created with the appropriate bounds. (The
 <code>sizeInBits</code> is needed to create the symbolic variable because the
 solver is bit-vector based.) If a new input value is provided to use in the
 execution, the symbolic variable is set to that values. If not, the default
 value (that the system-under-test would have used naturally), is used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the triggering method</td>
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
sizeInBits<br/><span class="paramtype">int</span></td>
<td>
size of the desired type</td>
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
value that will be used during the execution</p>
<h2><a class="anchor" name="getConcreteBoolean"></a>getConcreteBoolean</h2>
<div markdown="1">
~~~java
public boolean getConcreteBoolean(int triggerIndex, int index, int address, boolean currentValue)
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
<h2><a class="anchor" name="getConcreteByte"></a>getConcreteByte</h2>
<div markdown="1">
~~~java
public byte getConcreteByte(int triggerIndex, int index, int address, byte currentValue)
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
<h2><a class="anchor" name="getConcreteShort"></a>getConcreteShort</h2>
<div markdown="1">
~~~java
public short getConcreteShort(int triggerIndex, int index, int address, short currentValue)
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
<h2><a class="anchor" name="getConcreteChar"></a>getConcreteChar</h2>
<div markdown="1">
~~~java
public char getConcreteChar(int triggerIndex, int index, int address, char currentValue)
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
<h2><a class="anchor" name="getConcreteInt"></a>getConcreteInt</h2>
<div markdown="1">
~~~java
public int getConcreteInt(int triggerIndex, int index, int address, int currentValue)
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
<h2><a class="anchor" name="getConcreteLong"></a>getConcreteLong</h2>
<div markdown="1">
~~~java
public long getConcreteLong(int triggerIndex, int index, int address, long currentValue)
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
<h2><a class="anchor" name="getConcreteFloat"></a>getConcreteFloat</h2>
<div markdown="1">
~~~java
public float getConcreteFloat(int triggerIndex, int index, int address, float currentValue)
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
<h2><a class="anchor" name="getConcreteDouble"></a>getConcreteDouble</h2>
<div markdown="1">
~~~java
public double getConcreteDouble(int triggerIndex, int index, int address, double currentValue)
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
<h2><a class="anchor" name="getConcreteString"></a>getConcreteString</h2>
<div markdown="1">
~~~java
public String getConcreteString(int triggerIndex, int index, int address, String currentValue)
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
<h2><a class="anchor" name="getConcreteIntegralArray"></a>getConcreteIntegralArray</h2>
<div markdown="1">
~~~java
private Object getConcreteIntegralArray(int triggerIndex, int index, int address, int sizeInBits, Object currentArray, Function unconvert, Function convert)
~~~
</div>
Create an array of concrete values to use during the execution. This method
 caters for <code>boolean</code>, <code>byte</code>, <code>short</code>, <code>char</code>,
 <code>int</code>, and <code>long</code> values.
 
 If the trigger description that the formal parameter must be symbolic, new
 symbolic variables are created with the appropriate bounds. (The
 <code>sizeInBits</code> is needed to create symbolic variables because the solver
 is bit-vector based.) If new input values are provided to use in the
 execution, the symbolic variables are set to these values. If not, the
 default values (that the system-under-test would have used naturally), are
 used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the triggering method</td>
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
sizeInBits<br/><span class="paramtype">int</span></td>
<td>
size of the desired type</td>
</tr>
<tr>
<td>
currentArray<br/><span class="paramtype">Object</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
<tr>
<td>
unconvert<br/><span class="paramtype">Function</span></td>
<td>
<a href="{{ '/api/Function/' | relative_url }}">Function</a> to convert an object to a <a href="{{ '/api/Long/' | relative_url }}">Long</a>
                     value</td>
</tr>
<tr>
<td>
convert<br/><span class="paramtype">Function</span></td>
<td>
<a href="{{ '/api/Function/' | relative_url }}">Function</a> to convert a <a href="{{ '/api/Long/' | relative_url }}">Long</a> value to the
                     desired type</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
array of concrete values that will be used during the execution</p>
<h2><a class="anchor" name="getConcreteRealArray"></a>getConcreteRealArray</h2>
<div markdown="1">
~~~java
private Object getConcreteRealArray(int triggerIndex, int index, int address, int sizeInBits, Object currentArray, Function unconvert, Function convert)
~~~
</div>
Create an array of concrete values to use during the execution. This method
 caters for <code>float</code> and <code>double</code> values.
 
 If the trigger description that the formal parameter must be symbolic, new
 symbolic variables are created with the appropriate bounds. (The
 <code>sizeInBits</code> is needed to create symbolic variables because the solver
 is bit-vector based.) If new input values are provided to use in the
 execution, the symbolic variables are set to these values. If not, the
 default values (that the system-under-test would have used naturally), are
 used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
triggerIndex<br/><span class="paramtype">int</span></td>
<td>
index of the triggering method</td>
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
sizeInBits<br/><span class="paramtype">int</span></td>
<td>
size of the desired type</td>
</tr>
<tr>
<td>
currentArray<br/><span class="paramtype">Object</span></td>
<td>
default values to use (if concolic execution does not
                     override the values)</td>
</tr>
<tr>
<td>
unconvert<br/><span class="paramtype">Function</span></td>
<td>
<a href="{{ '/api/Function/' | relative_url }}">Function</a> to convert an object to a <a href="{{ '/api/Double/' | relative_url }}">Double</a></td>
</tr>
<tr>
<td>
convert<br/><span class="paramtype">Function</span></td>
<td>
<a href="{{ '/api/Function/' | relative_url }}">Function</a> to convert a <a href="{{ '/api/Double/' | relative_url }}">Double</a> value to the
                     desired type</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
array of concrete values that will be used during the execution</p>
<h2><a class="anchor" name="getConcreteBooleanArray"></a>getConcreteBooleanArray</h2>
<div markdown="1">
~~~java
public boolean getConcreteBooleanArray(int triggerIndex, int index, int address, boolean currentValue)
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
<h2><a class="anchor" name="getConcreteByteArray"></a>getConcreteByteArray</h2>
<div markdown="1">
~~~java
public byte getConcreteByteArray(int triggerIndex, int index, int address, byte currentValue)
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
<h2><a class="anchor" name="getConcreteShortArray"></a>getConcreteShortArray</h2>
<div markdown="1">
~~~java
public short getConcreteShortArray(int triggerIndex, int index, int address, short currentValue)
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
<h2><a class="anchor" name="getConcreteCharArray"></a>getConcreteCharArray</h2>
<div markdown="1">
~~~java
public char getConcreteCharArray(int triggerIndex, int index, int address, char currentValue)
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
<h2><a class="anchor" name="getConcreteIntArray"></a>getConcreteIntArray</h2>
<div markdown="1">
~~~java
public int getConcreteIntArray(int triggerIndex, int index, int address, int currentValue)
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
<h2><a class="anchor" name="getConcreteLongArray"></a>getConcreteLongArray</h2>
<div markdown="1">
~~~java
public long getConcreteLongArray(int triggerIndex, int index, int address, long currentValue)
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
<h2><a class="anchor" name="getConcreteFloatArray"></a>getConcreteFloatArray</h2>
<div markdown="1">
~~~java
public float getConcreteFloatArray(int triggerIndex, int index, int address, float currentValue)
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
<h2><a class="anchor" name="getConcreteDoubleArray"></a>getConcreteDoubleArray</h2>
<div markdown="1">
~~~java
public double getConcreteDoubleArray(int triggerIndex, int index, int address, double currentValue)
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
<h2><a class="anchor" name="getConcreteStringArray"></a>getConcreteStringArray</h2>
<div markdown="1">
~~~java
public String getConcreteStringArray(int triggerIndex, int index, int address, String currentValue)
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
<h2><a class="anchor" name="triggerMethod"></a>triggerMethod</h2>
<div markdown="1">
~~~java
public void triggerMethod(int methodNumber, int triggerIndex)
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
<h2><a class="anchor" name="startMethod"></a>startMethod</h2>
<div markdown="1">
~~~java
public void startMethod(int methodNumber, int argCount)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(boolean returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(char returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(double returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(float returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(int returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(long returnValue)
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
<h2><a class="anchor" name="returnValue"></a>returnValue</h2>
<div markdown="1">
~~~java
public void returnValue(short returnValue)
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
<h2><a class="anchor" name="linenumber"></a>linenumber</h2>
<div markdown="1">
~~~java
public void linenumber(int instr, int line)
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
<h2><a class="anchor" name="label"></a>label</h2>
<div markdown="1">
~~~java
public void label(int instr, String label)
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
<h2><a class="anchor" name="insn"></a>insn</h2>
<div markdown="1">
~~~java
public void insn(int instr, int opcode)
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
<h2><a class="anchor" name="intInsn"></a>intInsn</h2>
<div markdown="1">
~~~java
public void intInsn(int instr, int opcode, int operand)
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
<h2><a class="anchor" name="varInsn"></a>varInsn</h2>
<div markdown="1">
~~~java
public void varInsn(int instr, int opcode, int var)
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
<h2><a class="anchor" name="typeInsn"></a>typeInsn</h2>
<div markdown="1">
~~~java
public void typeInsn(int instr, int opcode)
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
<h2><a class="anchor" name="fieldInsn"></a>fieldInsn</h2>
<div markdown="1">
~~~java
public void fieldInsn(int instr, int opcode, String owner, String name, String descriptor)
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
<h2><a class="anchor" name="methodInsn"></a>methodInsn</h2>
<div markdown="1">
~~~java
public void methodInsn(int instr, int opcode, String owner, String name, String descriptor)
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
<h2><a class="anchor" name="invokeDynamicInsn"></a>invokeDynamicInsn</h2>
<div markdown="1">
~~~java
public void invokeDynamicInsn(int instr, int opcode)
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
<h2><a class="anchor" name="jumpInsn"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public void jumpInsn(int value, int instr, int opcode)
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
<h2><a class="anchor" name="jumpInsn"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public void jumpInsn(Object value, int instr, int opcode)
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
<h2><a class="anchor" name="jumpInsn"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public void jumpInsn(int value1, int value2, int instr, int opcode)
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
<h2><a class="anchor" name="jumpInsn"></a>jumpInsn</h2>
<div markdown="1">
~~~java
public void jumpInsn(int instr, int opcode)
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
<h2><a class="anchor" name="postJumpInsn"></a>postJumpInsn</h2>
<div markdown="1">
~~~java
public void postJumpInsn(int instr, int opcode)
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
<h2><a class="anchor" name="ldcInsn"></a>ldcInsn</h2>
<div markdown="1">
~~~java
public void ldcInsn(int instr, int opcode, Object value)
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
<h2><a class="anchor" name="iincInsn"></a>iincInsn</h2>
<div markdown="1">
~~~java
public void iincInsn(int instr, int var, int increment)
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
<h2><a class="anchor" name="tableSwitchInsn"></a>tableSwitchInsn</h2>
<div markdown="1">
~~~java
public void tableSwitchInsn(int instr, int opcode)
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
<h2><a class="anchor" name="tableCaseInsn"></a>tableCaseInsn</h2>
<div markdown="1">
~~~java
public void tableCaseInsn(int min, int max, int value)
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
<h2><a class="anchor" name="lookupSwitchInsn"></a>lookupSwitchInsn</h2>
<div markdown="1">
~~~java
public void lookupSwitchInsn(int instr, int opcode)
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
<h2><a class="anchor" name="multiANewArrayInsn"></a>multiANewArrayInsn</h2>
<div markdown="1">
~~~java
public void multiANewArrayInsn(int instr, int opcode)
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
<h2><a class="anchor" name="noException"></a>noException</h2>
<div markdown="1">
~~~java
public void noException()
~~~
</div>
<h2><a class="anchor" name="startCatch"></a>startCatch</h2>
<div markdown="1">
~~~java
public void startCatch(int instr)
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
<h2><a class="anchor" name="gatherInputs"></a>gatherInputs</h2>
<div markdown="1">
~~~java
private String gatherInputs()
~~~
</div>
Return a string representation of the trigger and its inputs.<h4>Returns</h4>
<p>
a string representation of the trigger and its inputs</p>
<h2><a class="anchor" name="gatherConcreteValue"></a>gatherConcreteValue</h2>
<div markdown="1">
~~~java
private String gatherConcreteValue(Trigger trigger, int index)
~~~
</div>
Return a string representation for the value of a particular parameter
 (identified by <code>index</code>) of a given <code>trigger</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
trigger<br/><span class="paramtype">Trigger</span></td>
<td>
the specific trigger</td>
</tr>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the index of the parameter</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a string representation of the value of the parameter</p>
<h2><a class="anchor" name="gatherConcreteValue0"></a>gatherConcreteValue0</h2>
<div markdown="1">
~~~java
private String gatherConcreteValue0(Class argType, Object value)
~~~
</div>
Return a string representation of a <code>value</code> given its type.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
argType<br/><span class="paramtype">Class</span></td>
<td>
the type of the value</td>
</tr>
<tr>
<td>
value<br/><span class="paramtype">Object</span></td>
<td>
the value itself</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a string representation of the value</p>
<h2><a class="anchor" name="stop"></a>stop</h2>
<div markdown="1">
~~~java
public void stop()
~~~
</div>
<h2><a class="anchor" name="stop"></a>stop</h2>
<div markdown="1">
~~~java
public void stop(String message)
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
<h2><a class="anchor" name="mark"></a>mark</h2>
<div markdown="1">
~~~java
public void mark(int marker)
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
<h2><a class="anchor" name="mark"></a>mark</h2>
<div markdown="1">
~~~java
public void mark(String marker)
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
<h2><a class="anchor" name="printPC"></a>printPC</h2>
<div markdown="1">
~~~java
public void printPC(String label)
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
<h2><a class="anchor" name="printPC"></a>printPC</h2>
<div markdown="1">
~~~java
public void printPC()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#DELEGATE_PARAMETERS">DELEGATE_PARAMETERS</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#dangerFlag">dangerFlag</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#delegateArguments">delegateArguments</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#exceptionDepth">exceptionDepth</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#frames">frames</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#instanceData">instanceData</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#justExecutedDelegate">justExecutedDelegate</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#lastInvokingInstruction">lastInvokingInstruction</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#limitConjuncts">limitConjuncts</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#mayContinue">mayContinue</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#mayRecord">mayRecord</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#newVariableCount">newVariableCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#noExceptionExpression">noExceptionExpression</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#objectIdCount">objectIdCount</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#params">params</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pendingExtraCondition">pendingExtraCondition</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pendingSwitch">pendingSwitch</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#throwable">throwable</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#trackAll">trackAll</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#triggeringIndex">triggeringIndex</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#SymbolicState">SymbolicState(COASTAL, Input)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#checkLimitConjuncts">checkLimitConjuncts()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createArray">createArray()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createString">createString()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicBoolean">createSymbolicBoolean(boolean, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicByte">createSymbolicByte(byte, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicChar">createSymbolicChar(char, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicDouble">createSymbolicDouble(double, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicFloat">createSymbolicFloat(float, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicInt">createSymbolicInt(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicLong">createSymbolicLong(long, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#createSymbolicShort">createSymbolicShort(short, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#discontinue">discontinue()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#dumpFrames">dumpFrames()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#executeDelegate">executeDelegate(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#fieldInsn">fieldInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#gatherConcreteValue">gatherConcreteValue(Trigger, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#gatherConcreteValue0">gatherConcreteValue0(Class<?>, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#gatherInputs">gatherInputs()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getArgumentCount">getArgumentCount(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getArrayValue">getArrayValue(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getAsciiSignature">getAsciiSignature(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteBoolean">getConcreteBoolean(int, int, int, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteBooleanArray">getConcreteBooleanArray(int, int, int, boolean[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteByte">getConcreteByte(int, int, int, byte)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteByteArray">getConcreteByteArray(int, int, int, byte[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteChar">getConcreteChar(int, int, int, char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteCharArray">getConcreteCharArray(int, int, int, char[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteDouble">getConcreteDouble(int, int, int, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteDoubleArray">getConcreteDoubleArray(int, int, int, double[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteFloat">getConcreteFloat(int, int, int, float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteFloatArray">getConcreteFloatArray(int, int, int, float[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteInt">getConcreteInt(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteIntArray">getConcreteIntArray(int, int, int, int[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteIntegral">getConcreteIntegral(int, int, int, int, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteIntegralArray">getConcreteIntegralArray(int, int, int, int, Object, Function<Object, Long>, Function<Long, Object>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteLong">getConcreteLong(int, int, int, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteLongArray">getConcreteLongArray(int, int, int, long[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteReal">getConcreteReal(int, int, int, int, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteRealArray">getConcreteRealArray(int, int, int, int, Object, Function<Object, Double>, Function<Double, Object>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteShort">getConcreteShort(int, int, int, short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteShortArray">getConcreteShortArray(int, int, int, short[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteString">getConcreteString(int, int, int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getConcreteStringArray">getConcreteStringArray(int, int, int, String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getExecution">getExecution()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getField">getField(int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getField">getField(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getLocal">getLocal(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getNewVariableName">getNewVariableName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getReturnType">getReturnType(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getStringChar">getStringChar(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#getStringLength">getStringLength(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#iincInsn">iincInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#incrAndGetNewObjectId">incrAndGetNewObjectId()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#insn">insn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#intInsn">intInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#invokeDynamicInsn">invokeDynamicInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#jumpInsn">jumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#jumpInsn">jumpInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#jumpInsn">jumpInsn(int, int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#jumpInsn">jumpInsn(Object, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#label">label(int, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#ldcInsn">ldcInsn(int, int, Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#linenumber">linenumber(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#lookupSwitchInsn">lookupSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#mark">mark(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#mark">mark(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#mayContinue">mayContinue()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#methodInsn">methodInsn(int, int, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#methodReturn">methodReturn()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#multiANewArrayInsn">multiANewArrayInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#noException">noException()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#peek">peek()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pop">pop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#postJumpInsn">postJumpInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#printPC">printPC()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#printPC">printPC(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#push">push(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pushConjunct">pushConjunct(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pushConjunct">pushConjunct(Expression, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pushConjunct">pushConjunct(Expression, long, long, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pushExtraCondition">pushExtraCondition(Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#pushReturnValue">pushReturnValue(char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#putField">putField(int, String, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#putField">putField(String, String, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(float)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#returnValue">returnValue(short)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setArrayLength">setArrayLength(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setArrayType">setArrayType(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setArrayValue">setArrayValue(int, int, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setLocal">setLocal(int, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setStringChar">setStringChar(int, int, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#setStringLength">setStringLength(int, Expression)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#startCatch">startCatch(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#startMethod">startMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#stop">stop()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#stop">stop(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#tableCaseInsn">tableCaseInsn(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#tableSwitchInsn">tableSwitchInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#triggerMethod">triggerMethod(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#typeInsn">typeInsn(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/SymbolicState/' | relative_url }}#varInsn">varInsn(int, int, int)</a></li>
</ul>
</li>

</ul>
</section>
