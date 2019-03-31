---
title: Doclet
permalink: /api/Doclet/
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
<h2><a class="anchor" name="doclet"></a>doclet</h2>
<div markdown="1">
~~~java
private static utility.Doclet doclet
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="destination"></a>destination</h2>
<div markdown="1">
~~~java
private String destination
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="excludedQualifiers"></a>excludedQualifiers</h2>
<div markdown="1">
~~~java
private final Set excludedQualifiers
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastalPackages"></a>coastalPackages</h2>
<div markdown="1">
~~~java
private com.sun.javadoc.PackageDoc coastalPackages
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastalClasses"></a>coastalClasses</h2>
<div markdown="1">
~~~java
private final Set coastalClasses
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="examplePackages"></a>examplePackages</h2>
<div markdown="1">
~~~java
private com.sun.javadoc.PackageDoc examplePackages
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="exampleClasses"></a>exampleClasses</h2>
<div markdown="1">
~~~java
private final Set exampleClasses
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="Doclet"></a>Doclet</h2>
<div markdown="1">
~~~java
public Doclet()
~~~
</div>
<h2><a class="anchor" name="getDoclet"></a>getDoclet</h2>
<div markdown="1">
~~~java
private static utility.Doclet getDoclet()
~~~
</div>
<h2><a class="anchor" name="start"></a>start</h2>
<div markdown="1">
~~~java
public static boolean start(com.sun.javadoc.RootDoc root)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
root<br/><span class="paramtype">com.sun.javadoc.RootDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="optionLength"></a>optionLength</h2>
<div markdown="1">
~~~java
public static int optionLength(String option)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
option<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="validOptions"></a>validOptions</h2>
<div markdown="1">
~~~java
public static boolean validOptions(String options, com.sun.javadoc.DocErrorReporter reporter)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
options<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
reporter<br/><span class="paramtype">com.sun.javadoc.DocErrorReporter</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="languageVersion"></a>languageVersion</h2>
<div markdown="1">
~~~java
public static com.sun.javadoc.LanguageVersion languageVersion()
~~~
</div>
<h2><a class="anchor" name="startDoc"></a>startDoc</h2>
<div markdown="1">
~~~java
private boolean startDoc(com.sun.javadoc.RootDoc root)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
root<br/><span class="paramtype">com.sun.javadoc.RootDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getOptionLength"></a>getOptionLength</h2>
<div markdown="1">
~~~java
private int getOptionLength(String option)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
option<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="areValidOptions"></a>areValidOptions</h2>
<div markdown="1">
~~~java
private boolean areValidOptions(String options, com.sun.javadoc.DocErrorReporter reporter)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
options<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
<tr>
<td>
reporter<br/><span class="paramtype">com.sun.javadoc.DocErrorReporter</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="getLanguageVersion"></a>getLanguageVersion</h2>
<div markdown="1">
~~~java
private com.sun.javadoc.LanguageVersion getLanguageVersion()
~~~
</div>
<h2><a class="anchor" name="setOptions"></a>setOptions</h2>
<div markdown="1">
~~~java
private void setOptions(String options)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
options<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="addToSet"></a>addToSet</h2>
<div markdown="1">
~~~java
private void addToSet(Set s, String str)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
s<br/><span class="paramtype">Set</span></td>
<td>
</td>
</tr>
<tr>
<td>
str<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="excludeQualifier"></a>excludeQualifier</h2>
<div markdown="1">
~~~java
private String excludeQualifier(String qualifier)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
qualifier<br/><span class="paramtype">String</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="initArrays"></a>initArrays</h2>
<div markdown="1">
~~~java
private void initArrays(com.sun.javadoc.RootDoc root)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
root<br/><span class="paramtype">com.sun.javadoc.RootDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generatePackageSummary"></a>generatePackageSummary</h2>
<div markdown="1">
~~~java
private void generatePackageSummary()
~~~
</div>
<h2><a class="anchor" name="generatePackage"></a>generatePackage</h2>
<div markdown="1">
~~~java
private void generatePackage(com.sun.javadoc.PackageDoc packageDoc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
packageDoc<br/><span class="paramtype">com.sun.javadoc.PackageDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generateClass"></a>generateClass</h2>
<div markdown="1">
~~~java
private void generateClass(com.sun.javadoc.ClassDoc classDoc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
classDoc<br/><span class="paramtype">com.sun.javadoc.ClassDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generateMethodHead"></a>generateMethodHead</h2>
<div markdown="1">
~~~java
private void generateMethodHead(utility.Doclet.MdWriter writer, com.sun.javadoc.ExecutableMemberDoc executable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
writer<br/><span class="paramtype">utility.Doclet.MdWriter</span></td>
<td>
</td>
</tr>
<tr>
<td>
executable<br/><span class="paramtype">com.sun.javadoc.ExecutableMemberDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generateMethod"></a>generateMethod</h2>
<div markdown="1">
~~~java
private void generateMethod(utility.Doclet.MdWriter writer, com.sun.javadoc.ExecutableMemberDoc executable)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
writer<br/><span class="paramtype">utility.Doclet.MdWriter</span></td>
<td>
</td>
</tr>
<tr>
<td>
executable<br/><span class="paramtype">com.sun.javadoc.ExecutableMemberDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generateClassSidebar"></a>generateClassSidebar</h2>
<div markdown="1">
~~~java
private void generateClassSidebar(utility.Doclet.MdWriter writer, com.sun.javadoc.ClassDoc classDoc)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
writer<br/><span class="paramtype">utility.Doclet.MdWriter</span></td>
<td>
</td>
</tr>
<tr>
<td>
classDoc<br/><span class="paramtype">com.sun.javadoc.ClassDoc</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="generatePackageSidebar"></a>generatePackageSidebar</h2>
<div markdown="1">
~~~java
private void generatePackageSidebar(utility.Doclet.MdWriter writer)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
writer<br/><span class="paramtype">utility.Doclet.MdWriter</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="sortList"></a>sortList</h2>
<div markdown="1">
~~~java
private T sortList(Collection collection, T prototype)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
collection<br/><span class="paramtype">Collection</span></td>
<td>
</td>
</tr>
<tr>
<td>
prototype<br/><span class="paramtype">T</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="sortList"></a>sortList</h2>
<div markdown="1">
~~~java
private T sortList(T collection, T prototype)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
collection<br/><span class="paramtype">T</span></td>
<td>
</td>
</tr>
<tr>
<td>
prototype<br/><span class="paramtype">T</span></td>
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
<a href="{{ '/api/Doclet/' | relative_url }}#coastalClasses">coastalClasses</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#coastalPackages">coastalPackages</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#destination">destination</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#doclet">doclet</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#exampleClasses">exampleClasses</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#examplePackages">examplePackages</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#excludedQualifiers">excludedQualifiers</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#Doclet">Doclet()</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#addToSet">addToSet(Set<String>, String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#areValidOptions">areValidOptions(String[][], DocErrorReporter)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#excludeQualifier">excludeQualifier(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generateClass">generateClass(ClassDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generateClassSidebar">generateClassSidebar(Doclet.MdWriter, ClassDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generateMethod">generateMethod(Doclet.MdWriter, ExecutableMemberDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generateMethodHead">generateMethodHead(Doclet.MdWriter, ExecutableMemberDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generatePackage">generatePackage(PackageDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generatePackageSidebar">generatePackageSidebar(Doclet.MdWriter)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#generatePackageSummary">generatePackageSummary()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#getDoclet">getDoclet()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#getLanguageVersion">getLanguageVersion()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#getOptionLength">getOptionLength(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#initArrays">initArrays(RootDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#languageVersion">languageVersion()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#optionLength">optionLength(String)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#setOptions">setOptions(String[][])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#sortList">sortList(Collection<T>, T[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#sortList">sortList(T[], T[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#start">start(RootDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#startDoc">startDoc(RootDoc)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/Doclet/' | relative_url }}#validOptions">validOptions(String[][], DocErrorReporter)</a></li>
</ul>
</li>

</ul>
</section>
