---
title: Doclet.MdWriter
permalink: /api/Doclet.MdWriter/
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
<h2><a class="anchor" name="fileseparator"></a>fileseparator</h2>
<div markdown="1">
~~~java
private static final String fileseparator
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="MdWriter"></a>MdWriter</h2>
<div markdown="1">
~~~java
public MdWriter(String path, String title)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
title<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="MdWriter"></a>MdWriter</h2>
<div markdown="1">
~~~java
public MdWriter(String path, String title, boolean toc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
title<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
toc<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="MdWriter"></a>MdWriter</h2>
<div markdown="1">
~~~java
public MdWriter(String path, String filename, String title)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
title<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="MdWriter"></a>MdWriter</h2>
<div markdown="1">
~~~java
public MdWriter(String path, String filename, String title, boolean toc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
path<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
filename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
title<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
toc<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="writeFrontMatter"></a>writeFrontMatter</h2>
<div markdown="1">
~~~java
private void writeFrontMatter(String title, String permalink, Object extras)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
title<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
permalink<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
extras<br/><span class="paramtype">Object</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="genWriter"></a>genWriter</h2>
<div markdown="1">
~~~java
private static Writer genWriter(String fullFilename)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
fullFilename<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tags"></a>tags</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter tags(com.sun.javadoc.Tag tags)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
tags<br/><span class="paramtype">com.sun.javadoc.Tag</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="a"></a>a</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter a(String href, String text)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
href<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
text<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="a"></a>a</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter a(String clas, String href, String text)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
href<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
text<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ax"></a>ax</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter ax(String clas, String href, String text)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
href<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
text<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="href"></a>href</h2>
<div markdown="1">
~~~java
private String href(String href)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
href<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="cdata"></a>cdata</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter cdata(String data)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
data<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="prejava"></a>prejava</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter prejava()
~~~
</div>
<h2><a class="anchor" name="prejavaEnd"></a>prejavaEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter prejavaEnd()
~~~
</div>
<h2><a class="anchor" name="p"></a>p</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter p()
~~~
</div>
<h2><a class="anchor" name="p"></a>p</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter p(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="pEnd"></a>pEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter pEnd()
~~~
</div>
<h2><a class="anchor" name="span"></a>span</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter span()
~~~
</div>
<h2><a class="anchor" name="span"></a>span</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter span(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="spanEnd"></a>spanEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter spanEnd()
~~~
</div>
<h2><a class="anchor" name="code"></a>code</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter code()
~~~
</div>
<h2><a class="anchor" name="code"></a>code</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter code(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="codeEnd"></a>codeEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter codeEnd()
~~~
</div>
<h2><a class="anchor" name="generic"></a>generic</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter generic(String element)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generic"></a>generic</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter generic(String clas, String element)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="genericEnd"></a>genericEnd</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter genericEnd(String element)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generic"></a>generic</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter generic(String element, boolean newline)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
newline<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generic"></a>generic</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter generic(String clas, String element, boolean newline)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
newline<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="genericEnd"></a>genericEnd</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter genericEnd(String element, boolean newline)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
element<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
newline<br/><span class="paramtype">boolean</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h1"></a>h1</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h1(String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h1"></a>h1</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h1(String clas, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h1"></a>h1</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h1(String clas, String heading, String name)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h2"></a>h2</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h2(String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h2"></a>h2</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h2(String clas, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h2"></a>h2</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h2(String clas, String heading, String name)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h3"></a>h3</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h3(String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h3"></a>h3</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h3(String clas, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h3"></a>h3</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h3(String clas, String heading, String name)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h4"></a>h4</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h4(String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h4"></a>h4</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h4(String clas, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="h4"></a>h4</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter h4(String clas, String heading, String name)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="hn"></a>hn</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter hn(String hn, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
hn<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="hn"></a>hn</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter hn(String hn, String clas, String heading)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
hn<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="hn"></a>hn</h2>
<div markdown="1">
~~~java
private utility.Doclet.MdWriter hn(String hn, String clas, String heading, String name)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
hn<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
heading<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
name<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ul"></a>ul</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter ul()
~~~
</div>
<h2><a class="anchor" name="ul"></a>ul</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter ul(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="ulEnd"></a>ulEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter ulEnd()
~~~
</div>
<h2><a class="anchor" name="li"></a>li</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter li()
~~~
</div>
<h2><a class="anchor" name="li"></a>li</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter li(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="liEnd"></a>liEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter liEnd()
~~~
</div>
<h2><a class="anchor" name="section"></a>section</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter section()
~~~
</div>
<h2><a class="anchor" name="section"></a>section</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter section(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="sectionEnd"></a>sectionEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter sectionEnd()
~~~
</div>
<h2><a class="anchor" name="table"></a>table</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter table()
~~~
</div>
<h2><a class="anchor" name="table"></a>table</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter table(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tableEnd"></a>tableEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tableEnd()
~~~
</div>
<h2><a class="anchor" name="thead"></a>thead</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter thead()
~~~
</div>
<h2><a class="anchor" name="thead"></a>thead</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter thead(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="theadEnd"></a>theadEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter theadEnd()
~~~
</div>
<h2><a class="anchor" name="tbody"></a>tbody</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tbody()
~~~
</div>
<h2><a class="anchor" name="tbody"></a>tbody</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tbody(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tbodyEnd"></a>tbodyEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tbodyEnd()
~~~
</div>
<h2><a class="anchor" name="tr"></a>tr</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tr()
~~~
</div>
<h2><a class="anchor" name="tr"></a>tr</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tr(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="trEnd"></a>trEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter trEnd()
~~~
</div>
<h2><a class="anchor" name="th"></a>th</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter th()
~~~
</div>
<h2><a class="anchor" name="th"></a>th</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter th(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="thEnd"></a>thEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter thEnd()
~~~
</div>
<h2><a class="anchor" name="td"></a>td</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter td()
~~~
</div>
<h2><a class="anchor" name="td"></a>td</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter td(String clas)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
clas<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="tdEnd"></a>tdEnd</h2>
<div markdown="1">
~~~java
public utility.Doclet.MdWriter tdEnd()
~~~
</div>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#fileseparator">fileseparator</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#MdWriter">MdWriter(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#MdWriter">MdWriter(String, String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#MdWriter">MdWriter(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#MdWriter">MdWriter(String, String, String, boolean)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#a">a(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#a">a(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#ax">ax(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#cdata">cdata(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#code">code()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#code">code(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#codeEnd">codeEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#generic">generic(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#generic">generic(String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#generic">generic(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#generic">generic(String, String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#genericEnd">genericEnd(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#genericEnd">genericEnd(String, boolean)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#genWriter">genWriter(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h1">h1(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h1">h1(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h1">h1(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h2">h2(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h2">h2(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h2">h2(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h3">h3(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h3">h3(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h3">h3(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h4">h4(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h4">h4(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#h4">h4(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#hn">hn(String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#hn">hn(String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#hn">hn(String, String, String, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#href">href(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#li">li()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#li">li(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#liEnd">liEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#p">p()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#p">p(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#pEnd">pEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#prejava">prejava()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#prejavaEnd">prejavaEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#section">section()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#section">section(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#sectionEnd">sectionEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#span">span()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#span">span(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#spanEnd">spanEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#table">table()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#table">table(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tableEnd">tableEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tags">tags(Tag[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tbody">tbody()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tbody">tbody(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tbodyEnd">tbodyEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#td">td()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#td">td(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tdEnd">tdEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#th">th()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#th">th(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#thead">thead()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#thead">thead(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#theadEnd">theadEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#thEnd">thEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tr">tr()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#tr">tr(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#trEnd">trEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#ul">ul()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#ul">ul(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#ulEnd">ulEnd()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet.MdWriter/' | relative_url }}#writeFrontMatter">writeFrontMatter(String, String, Object[]...)</a></li>
</ul>
</li>

</ul>
</section>
