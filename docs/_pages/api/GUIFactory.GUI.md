---
title: GUIFactory.GUI
permalink: /api/GUIFactory.GUI/
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
<h2><a class="anchor" name="INITIAL_WIDTH"></a>INITIAL_WIDTH</h2>
<div markdown="1">
~~~java
private static final int INITIAL_WIDTH = 1000
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="INITIAL_HEIGHT"></a>INITIAL_HEIGHT</h2>
<div markdown="1">
~~~java
private static final int INITIAL_HEIGHT = 600
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="coastal"></a>coastal</h2>
<div markdown="1">
~~~java
private static COASTAL coastal
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="stopButton"></a>stopButton</h2>
<div markdown="1">
~~~java
private javax.swing.JButton stopButton
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="doneLabel"></a>doneLabel</h2>
<div markdown="1">
~~~java
private javax.swing.JLabel doneLabel
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="quitButton"></a>quitButton</h2>
<div markdown="1">
~~~java
private javax.swing.JButton quitButton
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="tasks"></a>tasks</h2>
<div markdown="1">
~~~java
private final List tasks
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="bottomPanel"></a>bottomPanel</h2>
<div markdown="1">
~~~java
private javax.swing.JPanel bottomPanel
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="GUI(COASTAL)"></a>GUI</h2>
<div markdown="1">
~~~java
public GUI(COASTAL coastal)
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
</tbody>
</table>
<h2><a class="anchor" name="GUI()"></a>GUI</h2>
<div markdown="1">
~~~java
public GUI()
~~~
</div>
<h2><a class="anchor" name="run()"></a>run</h2>
<div markdown="1">
~~~java
public void run()
~~~
</div>
<h2><a class="anchor" name="createAndShowGUI()"></a>createAndShowGUI</h2>
<div markdown="1">
~~~java
public void createAndShowGUI()
~~~
</div>
<h2><a class="anchor" name="actionPerformed(ActionEvent)"></a>actionPerformed</h2>
<div markdown="1">
~~~java
public void actionPerformed(java.awt.event.ActionEvent e)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
e<br/><span class="paramtype">java.awt.event.ActionEvent</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="isDone(Object)"></a>isDone</h2>
<div markdown="1">
~~~java
public void isDone(Object object)
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
<h2><a class="anchor" name="tick(Object)"></a>tick</h2>
<div markdown="1">
~~~java
public void tick(Object object)
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
<h2><a class="anchor" name="update()"></a>update</h2>
<div markdown="1">
~~~java
private void update()
~~~
</div>
<h2><a class="anchor" name="update(boolean)"></a>update</h2>
<div markdown="1">
~~~java
private void update(boolean lastUpdate)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
lastUpdate<br/><span class="paramtype">boolean</span></td>
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
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#INITIAL_HEIGHT">INITIAL_HEIGHT</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#INITIAL_WIDTH">INITIAL_WIDTH</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#bottomPanel">bottomPanel</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#coastal">coastal</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#doneLabel">doneLabel</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#quitButton">quitButton</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#stopButton">stopButton</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#tasks">tasks</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#GUI()">GUI()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#GUI(COASTAL)">GUI(COASTAL)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#actionPerformed(ActionEvent)">actionPerformed(ActionEvent)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#createAndShowGUI()">createAndShowGUI()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#isDone(Object)">isDone(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#run()">run()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#tick(Object)">tick(Object)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#update()">update()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/GUIFactory.GUI/' | relative_url }}#update(boolean)">update(boolean)</a></li>
</ul>
</li>

</ul>
</section>
