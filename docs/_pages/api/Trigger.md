---
title: Trigger
permalink: /api/Trigger/
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
Triggers describe the methods and their symbolic parameters that switch on
 symbolic execution.
 
 One important aspect of triggers is that they used to handle the type
 information for parameters. Over time, the locus of control for types has
 moved to other places, but for historical reasons, this class contains the
 information for adding new types:
 
 <ol>
 
 <li>Add an appropriate <code>if</code>-statement to
 <code><a href="{{ '/api/Trigger/' | relative_url }}#parseType(String)">parseType(String)</a>
</code>.</li>
 
 <li>Add the appropriate code to <code><a href="{{ '/api/Trigger/' | relative_url }}#toString()">toString()</a>
</code>.</li>
 
 <li>Add instrumentation to "pick up" values for the type to
 <code><a href="{{ '/api/HeavyMethodAdapter/' | relative_url }}#visitParameter(String, int)">HeavyMethodAdapter#visitParameter(String, int)</a>
</code>.</li>
 
 <li>Add instrumentation to "pick up" values for the type to
 <code><a href="{{ '/api/LightMethodAdapter/' | relative_url }}#visitParameter(String, int)">LightMethodAdapter#visitParameter(String, int)</a>
</code>.</li>
 
 <li>Add a <code>getConcreteXXX(...)</code> method to <code><a href="{{ '/api/VM/' | relative_url }}">VM</a>
</code>.</li>
 
 <li>Add a <code>getConcreteXXX(...)</code> method to
 <code><a href="{{ '/api/SymbolicState/' | relative_url }}">SymbolicState</a>
</code>.</li>
 
 <li>Add a <code>getConcreteXXX(...)</code> method to <code><a href="{{ '/api/TraceState/' | relative_url }}">TraceState</a>
</code>.</li>
 
 <li>Add appropriate code to <code><a href="{{ '/api/COASTAL/' | relative_url }}#parseConfigBounds()">COASTAL#parseConfigBounds()</a>
</code>.</li>
 
 </ol><h2><a class="anchor" name="methodName"></a>methodName</h2>
<div markdown="1">
~~~java
private final String methodName
~~~
</div>
<p>
The name of the method (e.g., <code>"routine"</code>).</p>
<h2><a class="anchor" name="className"></a>className</h2>
<div markdown="1">
~~~java
private final String className
~~~
</div>
<p>
The fully qualified name of the class (e.g.,
 <code>"example.progs.Program"</code>).</p>
<h2><a class="anchor" name="fullName"></a>fullName</h2>
<div markdown="1">
~~~java
private final String fullName
~~~
</div>
<p>
The fully qualified name of the method (e.g.,
 <code>"example.progs.Program.routine"</code>).</p>
<h2><a class="anchor" name="paramNames"></a>paramNames</h2>
<div markdown="1">
~~~java
private final String paramNames
~~~
</div>
<p>
The names of the symbolic parameters. If a parameter is non-symbolic, its
 entry in this array is <code>null</code>.</p>
<h2><a class="anchor" name="paramTypes"></a>paramTypes</h2>
<div markdown="1">
~~~java
private final Class paramTypes
~~~
</div>
<p>
The types of the parameters.</p>
<h2><a class="anchor" name="signature"></a>signature</h2>
<div markdown="1">
~~~java
private final String signature
~~~
</div>
<p>
The Java signature for this trigger (excluding the return type).</p>
<h2><a class="anchor" name="stringRepr"></a>stringRepr</h2>
<div markdown="1">
~~~java
private String stringRepr
~~~
</div>
<p>
A string representation of the trigger.</p>
<h2><a class="anchor" name="Trigger(String, String, String[], Class<?>[])"></a>Trigger</h2>
<div markdown="1">
~~~java
private Trigger(String methodName, String packageName, String paramNames, Class paramTypes)
~~~
</div>
Construct a new trigger.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodName<br/><span class="paramtype">String</span></td>
<td>
the fully qualified name of the trigger</td>
</tr>
<tr>
<td>
packageName<br/><span class="paramtype">String</span></td>
<td>
the trigger parameter names</td>
</tr>
<tr>
<td>
paramNames<br/><span class="paramtype">String</span></td>
<td>
the trigger parameter types</td>
</tr>
<tr>
<td>
paramTypes<br/><span class="paramtype">Class</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="match(String, String)"></a>match</h2>
<div markdown="1">
~~~java
public boolean match(String methodName, String signature)
~~~
</div>
Check if this trigger corresponds to the given method name and signature.
 This is used to avoid adding duplicate triggers.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
methodName<br/><span class="paramtype">String</span></td>
<td>
the fully qualified name of the method</td>
</tr>
<tr>
<td>
signature<br/><span class="paramtype">String</span></td>
<td>
the method signature (return type excluded)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
<code>true</code> if and only the name and signature are the same as
         this triggers</p>
<h2><a class="anchor" name="getMethodName()"></a>getMethodName</h2>
<div markdown="1">
~~~java
public String getMethodName()
~~~
</div>
Return the name of the trigger method.<h4>Returns</h4>
<p>
the name of the method</p>
<h2><a class="anchor" name="getClassName()"></a>getClassName</h2>
<div markdown="1">
~~~java
public String getClassName()
~~~
</div>
Return the name of the trigger class.<h4>Returns</h4>
<p>
the name of the class</p>
<h2><a class="anchor" name="getParamCount()"></a>getParamCount</h2>
<div markdown="1">
~~~java
public int getParamCount()
~~~
</div>
Return the number of parameters.<h4>Returns</h4>
<p>
the number of parameters</p>
<h2><a class="anchor" name="getParamName(int)"></a>getParamName</h2>
<div markdown="1">
~~~java
public String getParamName(int index)
~~~
</div>
Return the name of the given parameter. This will be <code>null</code> if the
 parameter is not symbolic.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the number of parameter, starting from 0</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the name of the parameter, or <code>null</code> if the parameter is
         not symbolic</p>
<h2><a class="anchor" name="getParamTypes()"></a>getParamTypes</h2>
<div markdown="1">
~~~java
public Class getParamTypes()
~~~
</div>
Return the array that lists all the parameter types.<h4>Returns</h4>
<p>
the parameter types</p>
<h2><a class="anchor" name="getParamType(int)"></a>getParamType</h2>
<div markdown="1">
~~~java
public Class getParamType(int index)
~~~
</div>
Return the type of the given parameter. This will be a valid type even if
 the parameter is not symbolic.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
index<br/><span class="paramtype">int</span></td>
<td>
the number of parameter, starting from 0</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the type of the parameter</p>
<h2><a class="anchor" name="toString()"></a>toString</h2>
<div markdown="1">
~~~java
public String toString()
~~~
</div>
Compute (if necessary) and return the string representation of a trigger.<h4>Returns</h4>
<p>
a string description of the trigger</p>
<h2><a class="anchor" name="createTrigger(String, String, Map<String, Class<?>>)"></a>createTrigger</h2>
<div markdown="1">
~~~java
public static Trigger createTrigger(String desc, String className, Map parameters)
~~~
</div>
Create a new trigger from a string specified in the COASTAL
 configuration. The string should consist of the name of the method, an
 open parenthesis (<code>(</code>), a comma-separated list of zero or more
 parameters, and a closing parenthesis (<code>)</code>). Each parameter
 consists of a name, followed by a colon (<code>:</code>), and a type
 description. If the method name is not fully qualified, the class name
 parameter is used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
desc<br/><span class="paramtype">String</span></td>
<td>
the description of the trigger method</td>
</tr>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
optional name of the entry point class</td>
</tr>
<tr>
<td>
parameters<br/><span class="paramtype">Map</span></td>
<td>
a mapping that relates parameter names to Java classes</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the new trigger</p>
<h2><a class="anchor" name="createTrigger(String, String)"></a>createTrigger</h2>
<div markdown="1">
~~~java
public static Trigger createTrigger(String desc, String className)
~~~
</div>
Create a new trigger that represents the entry point for the analysis
 run. The string should consist of the name of the method, an open
 parenthesis (<code>(</code>), a comma-separated list of zero or more types,
 and a closing parenthesis (<code>)</code>). If the method name is not fully
 qualified, the class name parameter is used.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
desc<br/><span class="paramtype">String</span></td>
<td>
the description of the entry point</td>
</tr>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
optional name of the entry point class</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the new trigger</p>
<h2><a class="anchor" name="parseType(String)"></a>parseType</h2>
<div markdown="1">
~~~java
private static Class parseType(String type)
~~~
</div>
Convert a string description of a type to a Java class instance. If the
 string is not recognized, <code>Object.class</code> is returned. The following
 types are currently recognized:
 
 <ul>
 <li><code>boolean</code></li>
 <li><code>byte</code></li>
 <li><code>short</code></li>
 <li><code>char</code></li>
 <li><code>int</code></li>
 <li><code>long</code></li>
 <li><code>float</code></li>
 <li><code>double</code></li>
 <li><code>String</code></li>
 <li>and any one-dimensional arrays of the above.</li>
 </ul><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
type<br/><span class="paramtype">String</span></td>
<td>
a string description of a type</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the Java class instance for the type</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#className">className</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#fullName">fullName</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#methodName">methodName</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#paramNames">paramNames</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#paramTypes">paramTypes</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#signature">signature</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#stringRepr">stringRepr</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#Trigger(String, String, String[], Class<?>[])">Trigger(String, String, String[], Class<?>[])</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#createTrigger(String, String)">createTrigger(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#createTrigger(String, String, Map<String, Class<?>>)">createTrigger(String, String, Map<String, Class<?>>)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getClassName()">getClassName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getMethodName()">getMethodName()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getParamCount()">getParamCount()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getParamName(int)">getParamName(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getParamType(int)">getParamType(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#getParamTypes()">getParamTypes()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#match(String, String)">match(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#parseType(String)">parseType(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Trigger/' | relative_url }}#toString()">toString()</a></li>
</ul>
</li>

</ul>
</section>
