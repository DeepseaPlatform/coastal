---
title: Banner
permalink: /api/Banner/
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
Utility class for creating more visible banners in the log output.
 
 <p>
 Typical usage for a one-line banner:
 </p>
 
 <div markdown="1">
~~~java
log.info((Banner.getBannerLine("some error occurred", '*'));
 ~~~
</div>

 
 <p>
 Typical usage for a larger banner:
 </p>
 
 <div markdown="1">
~~~java
Banner bn = new Banner('@');
bn.println("SOME SERIOUS ERROR:");
bn.println(exception.getMessage());
bn.display(log);
 ~~~
</div>

 
 <p>
 Two additional methods (<code><a href="{{ '/api/Banner/' | relative_url }}#getElapsed(COASTAL)">getElapsed(COASTAL)</a>
</code> and
 <code><a href="{{ '/api/Banner/' | relative_url }}#getElapsed(long)">getElapsed(long)</a>
</code>) are provided to format the COASTAL running time,
 and to format a general amount of elapsed time. These routines produce
 different forms depending on the amount of elapsed time.
 </p><h2><a class="anchor" name="LS"></a>LS</h2>
<div markdown="1">
~~~java
private static final String LS
~~~
</div>
<p>
The line separator used on this system.</p>
<h2><a class="anchor" name="WIDTH"></a>WIDTH</h2>
<div markdown="1">
~~~java
private static final int WIDTH = 70
~~~
</div>
<p>
The maximum width of banners.</p>
<h2><a class="anchor" name="SIDE_WIDTH"></a>SIDE_WIDTH</h2>
<div markdown="1">
~~~java
private static final int SIDE_WIDTH = 4
~~~
</div>
<p>
The number of banner characters in the left and right borders.</p>
<h2><a class="anchor" name="SIDE_SPACE"></a>SIDE_SPACE</h2>
<div markdown="1">
~~~java
private static final int SIDE_SPACE = 2
~~~
</div>
<p>
The number of spaces between the border and the banner content.</p>
<h2><a class="anchor" name="bannerWriter"></a>bannerWriter</h2>
<div markdown="1">
~~~java
protected final StringWriter bannerWriter
~~~
</div>
<p>
A writer used for recording the banner.</p>
<h2><a class="anchor" name="borderLine"></a>borderLine</h2>
<div markdown="1">
~~~java
private final String borderLine
~~~
</div>
<p>
A full border line for the top and bottom of the banner.</p>
<h2><a class="anchor" name="borderLeft"></a>borderLeft</h2>
<div markdown="1">
~~~java
private final String borderLeft
~~~
</div>
<p>
The left-hand border.</p>
<h2><a class="anchor" name="borderRight"></a>borderRight</h2>
<div markdown="1">
~~~java
private final String borderRight
~~~
</div>
<p>
The right-hand border.</p>
<h2><a class="anchor" name="borderEmpty"></a>borderEmpty</h2>
<div markdown="1">
~~~java
private final String borderEmpty
~~~
</div>
<p>
An empty line in the border for creating space between the top border and
 the content, or the content and the bottom border.</p>
<h2><a class="anchor" name="sb"></a>sb</h2>
<div markdown="1">
~~~java
private final StringBuilder sb
~~~
</div>
<p>
A string builder that is used over and over for constructing the banner
 borders and content.</p>
<h2><a class="anchor" name="Banner(char)"></a>Banner</h2>
<div markdown="1">
~~~java
public Banner(char borderChar)
~~~
</div>
Construct a new banner. The main task of this routine is to create the
 border elements. It would be possible to cache constructed banners so
 that the borders do not have to be created every time. But banners are
 used infrequently, mostly during startup or termination, and it is not
 worth the effort.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
borderChar<br/><span class="paramtype">char</span></td>
<td>
the character used for forming the border</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="println(String)"></a>println</h2>
<div markdown="1">
~~~java
public Banner println(String message)
~~~
</div>
Add content to the banner.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
message<br/><span class="paramtype">String</span></td>
<td>
the content to add</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the same instance of the banner so that these calls can be
         chained</p>
<h2><a class="anchor" name="display(Logger)"></a>display</h2>
<div markdown="1">
~~~java
public void display(Logger log)
~~~
</div>
Display the banner to the given log<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
log<br/><span class="paramtype">Logger</span></td>
<td>
the log</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="display(PrintWriter)"></a>display</h2>
<div markdown="1">
~~~java
public void display(PrintWriter out)
~~~
</div>
Display the banner to the given writer.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
out<br/><span class="paramtype">PrintWriter</span></td>
<td>
the writer</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="display(PrintStream)"></a>display</h2>
<div markdown="1">
~~~java
public void display(PrintStream out)
~~~
</div>
Display the banner to the given stream.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
out<br/><span class="paramtype">PrintStream</span></td>
<td>
the stream</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getBannerLine(String, char)"></a>getBannerLine</h2>
<div markdown="1">
~~~java
public static String getBannerLine(String string, char bannerChar)
~~~
</div>
Produce a short, one-line banner.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
string<br/><span class="paramtype">String</span></td>
<td>
the content of the banner</td>
</tr>
<tr>
<td>
bannerChar<br/><span class="paramtype">char</span></td>
<td>
the character to place around the content</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
a one-line banner</p>
<h2><a class="anchor" name="getElapsed(COASTAL)"></a>getElapsed</h2>
<div markdown="1">
~~~java
public static String getElapsed(COASTAL coastal)
~~~
</div>
Format the elapsed time for a COASTAL analysis run in human-readable
 form. It checks the duration and decides which units to include.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
the instance of COASTAL for which to compute the elapsed time</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the nicely-formatted elapsed time</p>
<h2><a class="anchor" name="getElapsed(long)"></a>getElapsed</h2>
<div markdown="1">
~~~java
public static String getElapsed(long delta)
~~~
</div>
Format the elapsed time in human-readable form. It checks the duration
 and decides which units to include.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
delta<br/><span class="paramtype">long</span></td>
<td>
the elapsed time in milliseconds</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the nicely-formatted elapsed time</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#LS">LS</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#SIDE_SPACE">SIDE_SPACE</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#SIDE_WIDTH">SIDE_WIDTH</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#WIDTH">WIDTH</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#bannerWriter">bannerWriter</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#borderEmpty">borderEmpty</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#borderLeft">borderLeft</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#borderLine">borderLine</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#borderRight">borderRight</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#sb">sb</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#Banner(char)">Banner(char)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#display(Logger)">display(Logger)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#display(PrintStream)">display(PrintStream)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#display(PrintWriter)">display(PrintWriter)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#getBannerLine(String, char)">getBannerLine(String, char)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#getElapsed(COASTAL)">getElapsed(COASTAL)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#getElapsed(long)">getElapsed(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Banner/' | relative_url }}#println(String)">println(String)</a></li>
</ul>
</li>

</ul>
</section>
