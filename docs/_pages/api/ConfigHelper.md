---
title: ConfigHelper
permalink: /api/ConfigHelper/
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
A collection of routines to aid in loading the COASTAL configuration.<h2><a class="anchor" name="COASTAL_CONFIGURATION"></a>COASTAL_CONFIGURATION</h2>
<div markdown="1">
~~~java
private static final String COASTAL_CONFIGURATION = "coastal.xml"
~~~
</div>
<p>
The name of COASTAL configuration file, both the resource (part of the
 project, providing sensible defaults), and the user's own configuration
 file (providing overriding personalizations).</p>
<h2><a class="anchor" name="COASTAL_DIRECTORY"></a>COASTAL_DIRECTORY</h2>
<div markdown="1">
~~~java
private static final String COASTAL_DIRECTORY = ".coastal"
~~~
</div>
<p>
The subdirectory in the user's home directory where the personal coastal
 file is searched for.</p>
<h2><a class="anchor" name="HOME_DIRECTORY"></a>HOME_DIRECTORY</h2>
<div markdown="1">
~~~java
private static final String HOME_DIRECTORY
~~~
</div>
<p>
The user's home directory.</p>
<h2><a class="anchor" name="HOME_COASTAL_DIRECTORY"></a>HOME_COASTAL_DIRECTORY</h2>
<div markdown="1">
~~~java
private static final String HOME_COASTAL_DIRECTORY
~~~
</div>
<p>
The full name of the subdirectory where the personal file is searched
 for.</p>
<h2><a class="anchor" name="HOME_CONFIGURATION"></a>HOME_CONFIGURATION</h2>
<div markdown="1">
~~~java
private static final String HOME_CONFIGURATION
~~~
</div>
<p>
The full name of the personal configuration file.</p>
<h2><a class="anchor" name="ConfigHelper()"></a>ConfigHelper</h2>
<div markdown="1">
~~~java
public ConfigHelper()
~~~
</div>
<h2><a class="anchor" name="createInstance(COASTAL, String)"></a>createInstance</h2>
<div markdown="1">
~~~java
public static Object createInstance(COASTAL coastal, String className)
~~~
</div>
Create an instance of the class with the given name if it has a
 constructor that takes a single parameter: an instance of COASTAL. The
 given instance of COASTAL is passed to the constructor.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
the instance of COASTAL to pass to the constructor</td>
</tr>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
the name of the class to create</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a new instance of the class (or <code>null</code> if it could not be
         instantiated)</p>
<h2><a class="anchor" name="createInstance(COASTAL, ImmutableConfiguration, String)"></a>createInstance</h2>
<div markdown="1">
~~~java
public static Object createInstance(COASTAL coastal, ImmutableConfiguration options, String className)
~~~
</div>
Create an instance of the class with the given name if it has a
 constructor that takes a two parameters: an instance of COASTAL and a
 (sub)configuration. The given instance of COASTAL and the configuration
 are passed to the constructor. This is used for components that may read
 additional options from the configuration.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
the instance of COASTAL to pass to the constructor</td>
</tr>
<tr>
<td>
options<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
the (sub)configuration relevant to the instance</td>
</tr>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
the name of the class to create</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a new instance of the class (or <code>null</code> if it could not be
         instantiated)</p>
<h2><a class="anchor" name="loadClass(COASTAL, String)"></a>loadClass</h2>
<div markdown="1">
~~~java
public static Class loadClass(COASTAL coastal, String className)
~~~
</div>
Load the class specified by the class name.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
instance of COASTAL</td>
</tr>
<tr>
<td>
className<br/><span class="paramtype">String</span></td>
<td>
the name of the class</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the class (or <code>null</code> if it could not be loaded)</p>
<h2><a class="anchor" name="minmax(int, int, int)"></a>minmax</h2>
<div markdown="1">
~~~java
public static int minmax(int value, int min, int max)
~~~
</div>
Return the <code>int</code> value "clamped" to lie between the minimum and
 maximum values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">int</span></td>
<td>
the original value</td>
</tr>
<tr>
<td>
min<br/><span class="paramtype">int</span></td>
<td>
the minimum value allowed</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">int</span></td>
<td>
the maximum value allowed</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the clamped <code>int</code> value</p>
<h2><a class="anchor" name="minmax(long, long, long)"></a>minmax</h2>
<div markdown="1">
~~~java
public static long minmax(long value, long min, long max)
~~~
</div>
Return the <code>long</code> value "clamped" to lie between the minimum and
 maximum values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">long</span></td>
<td>
the original value</td>
</tr>
<tr>
<td>
min<br/><span class="paramtype">long</span></td>
<td>
the minimum value allowed</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">long</span></td>
<td>
the maximum value allowed</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the clamped <code>long</code> value</p>
<h2><a class="anchor" name="minmax(double, double, double)"></a>minmax</h2>
<div markdown="1">
~~~java
public static double minmax(double value, double min, double max)
~~~
</div>
Return the <code>double</code> value "clamped" to lie between the minimum and
 maximum values.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">double</span></td>
<td>
the original value</td>
</tr>
<tr>
<td>
min<br/><span class="paramtype">double</span></td>
<td>
the minimum value allowed</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">double</span></td>
<td>
the maximum value allowed</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the clamped <code>double</code> value</p>
<h2><a class="anchor" name="zero(int, int)"></a>zero</h2>
<div markdown="1">
~~~java
public static int zero(int value, int zero)
~~~
</div>
Return the <code>int</code> value or, if it is equal to zero, a replacement
 value.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">int</span></td>
<td>
the original value</td>
</tr>
<tr>
<td>
zero<br/><span class="paramtype">int</span></td>
<td>
the replacement value if the first parameter is equal to zero</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the original <code>int</code> value or its zero replacement</p>
<h2><a class="anchor" name="zero(long, long)"></a>zero</h2>
<div markdown="1">
~~~java
public static long zero(long value, long zero)
~~~
</div>
Return the <code>long</code> value or, if it is equal to zero, a replacement
 value.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
value<br/><span class="paramtype">long</span></td>
<td>
the original value</td>
</tr>
<tr>
<td>
zero<br/><span class="paramtype">long</span></td>
<td>
the replacement value if the first parameter is equal to zero</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the original <code>long</code> value or its zero replacement</p>
<h2><a class="anchor" name="limitInt(ImmutableConfiguration, String)"></a>limitInt</h2>
<div markdown="1">
~~~java
public static int limitInt(ImmutableConfiguration config, String key)
~~~
</div>
The <code>int</code> value associated with the key in the supplied
 configuration. The value is clamped to minimum and maximum <code>int</code>
 values. If the key is not present, the maximum <code>int</code> value is
 returned.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
config<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
the configuration to consult</td>
</tr>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key of the value</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the <code>int</code> value associated with the key</p>
<h2><a class="anchor" name="limitLong(ImmutableConfiguration, String)"></a>limitLong</h2>
<div markdown="1">
~~~java
public static long limitLong(ImmutableConfiguration config, String key)
~~~
</div>
The <code>long</code> value associated with the key in the supplied
 configuration. The value is clamped to minimum and maximum <code>long</code>
 values. If the key is not present, the maximum <code>long</code> value is
 returned.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
config<br/><span class="paramtype">ImmutableConfiguration</span></td>
<td>
the configuration to consult</td>
</tr>
<tr>
<td>
key<br/><span class="paramtype">String</span></td>
<td>
the key of the value</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the <code>long</code> value associated with the key</p>
<h2><a class="anchor" name="toString(BitSet)"></a>toString</h2>
<div markdown="1">
~~~java
public static final String toString(BitSet bs)
~~~
</div>
Convert a Java <code><a href="{{ '/api/java.util.BitSet/' | relative_url }}">java.util.BitSet</a>
</code> instance to a <code><a href="{{ '/api/String/' | relative_url }}">String</a>
</code>.
 This is somewhat out of place, but not entirely. A bit set such as
 <code>{1, 3, 4, 5, 7, 9, 10}</code> is formatted as <code>"{1, 3-5, 7,
 9-10}"</code>.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
bs<br/><span class="paramtype">BitSet</span></td>
<td>
the bit set to format</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a string representation of the bit set</p>
<h2><a class="anchor" name="loadConfiguration(Logger, String)"></a>loadConfiguration</h2>
<div markdown="1">
~~~java
public static ImmutableConfiguration loadConfiguration(Logger log, String arg)
~~~
</div>
Load a COASTAL configuration from a configuration file.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
arg<br/><span class="paramtype">String</span></td>
<td>
the name of the configuration file</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if some configuration
         error occurred</p>
<h2><a class="anchor" name="loadConfiguration(Logger, String[])"></a>loadConfiguration</h2>
<div markdown="1">
~~~java
public static ImmutableConfiguration loadConfiguration(Logger log, String args)
~~~
</div>
Load the COASTAL configuration. Three sources are consulted:
 
 <ul>
 <li>a project resource</li>
 <li>the user's personal configuration file</li>
 <li>the configuration file specified on the command line</li>
 </ul>
 
 The second source overrides the first, and the third source overrides the
 first two sources.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
args<br/><span class="paramtype">String</span></td>
<td>
the command-line arguments</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration</p>
<h2><a class="anchor" name="loadConfiguration(Logger, String, String)"></a>loadConfiguration</h2>
<div markdown="1">
~~~java
public static ImmutableConfiguration loadConfiguration(Logger log, String arg, String extra)
~~~
</div>
Load a COASTAL configuration from a configuration file and a
 configuration string.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
arg<br/><span class="paramtype">String</span></td>
<td>
the name of the configuration file</td>
</tr>
<tr>
<td>
extra<br/><span class="paramtype">String</span></td>
<td>
additional configuration settings (in XML)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if some configuration
         error occurred</p>
<h2><a class="anchor" name="loadConfiguration(Logger, String[], String)"></a>loadConfiguration</h2>
<div markdown="1">
~~~java
public static ImmutableConfiguration loadConfiguration(Logger log, String args, String extra)
~~~
</div>
Load a COASTAL configuration from a variety of sources.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
args<br/><span class="paramtype">String</span></td>
<td>
an array of filenames or resources that refer to
            configurations</td>
</tr>
<tr>
<td>
extra<br/><span class="paramtype">String</span></td>
<td>
additional configuration settings (in XML)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if no configuration
         was found</p>
<h2><a class="anchor" name="loadConfiguration(Logger, String, String[], String)"></a>loadConfiguration</h2>
<div markdown="1">
~~~java
public static ImmutableConfiguration loadConfiguration(Logger log, String runName, String args, String extra)
~~~
</div>
Load a COASTAL configuration from a variety of sources.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
runName<br/><span class="paramtype">String</span></td>
<td>
the "name" for this COASTAL run</td>
</tr>
<tr>
<td>
args<br/><span class="paramtype">String</span></td>
<td>
an array of filenames or resources that refer to
            configurations</td>
</tr>
<tr>
<td>
extra<br/><span class="paramtype">String</span></td>
<td>
additional configuration settings (in XML)</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if no configuration
         was found</p>
<h2><a class="anchor" name="loadConfigFromFile(Logger, String)"></a>loadConfigFromFile</h2>
<div markdown="1">
~~~java
public static Configuration loadConfigFromFile(Logger log, String filename)
~~~
</div>
Load a COASTAL configuration from a file.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
the name of the file</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if the file was not
         found</p>
<h2><a class="anchor" name="loadConfigFromResource(Logger, String)"></a>loadConfigFromResource</h2>
<div markdown="1">
~~~java
private static Configuration loadConfigFromResource(Logger log, String resourceName)
~~~
</div>
Load a COASTAL configuration from a Java resource.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
resourceName<br/><span class="paramtype">String</span></td>
<td>
the name of the resource</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration or <code>null</code> if the resource was
         not found</p>
<h2><a class="anchor" name="loadConfigFromStream(Logger, InputStream)"></a>loadConfigFromStream</h2>
<div markdown="1">
~~~java
private static Configuration loadConfigFromStream(Logger log, InputStream inputStream)
~~~
</div>
Load a COASTAL configuration from an input stream.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
inputStream<br/><span class="paramtype">InputStream</span></td>
<td>
the stream from which to read</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration</p>
<h2><a class="anchor" name="loadConfigFromString(Logger, String)"></a>loadConfigFromString</h2>
<div markdown="1">
~~~java
private static Configuration loadConfigFromString(Logger log, String configString)
~~~
</div>
Load a COASTAL configuration from a string.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the logger to which to report</td>
</tr>
<tr>
<td>
configString<br/><span class="paramtype">String</span></td>
<td>
the stirng that contains the configuration</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
an immutable configuration</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#COASTAL_CONFIGURATION">COASTAL_CONFIGURATION</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#COASTAL_DIRECTORY">COASTAL_DIRECTORY</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#HOME_COASTAL_DIRECTORY">HOME_COASTAL_DIRECTORY</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#HOME_CONFIGURATION">HOME_CONFIGURATION</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#HOME_DIRECTORY">HOME_DIRECTORY</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#ConfigHelper()">ConfigHelper()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#createInstance(COASTAL, ImmutableConfiguration, String)">createInstance(COASTAL, ImmutableConfiguration, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#createInstance(COASTAL, String)">createInstance(COASTAL, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#limitInt(ImmutableConfiguration, String)">limitInt(ImmutableConfiguration, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#limitLong(ImmutableConfiguration, String)">limitLong(ImmutableConfiguration, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadClass(COASTAL, String)">loadClass(COASTAL, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfigFromFile(Logger, String)">loadConfigFromFile(Logger, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfigFromResource(Logger, String)">loadConfigFromResource(Logger, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfigFromStream(Logger, InputStream)">loadConfigFromStream(Logger, InputStream)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfigFromString(Logger, String)">loadConfigFromString(Logger, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfiguration(Logger, String)">loadConfiguration(Logger, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfiguration(Logger, String[])">loadConfiguration(Logger, String[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfiguration(Logger, String[], String)">loadConfiguration(Logger, String[], String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfiguration(Logger, String, String)">loadConfiguration(Logger, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#loadConfiguration(Logger, String, String[], String)">loadConfiguration(Logger, String, String[], String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#minmax(double, double, double)">minmax(double, double, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#minmax(int, int, int)">minmax(int, int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#minmax(long, long, long)">minmax(long, long, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#toString(BitSet)">toString(BitSet)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#zero(int, int)">zero(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/ConfigHelper/' | relative_url }}#zero(long, long)">zero(long, long)</a></li>
</ul>
</li>

</ul>
</section>
