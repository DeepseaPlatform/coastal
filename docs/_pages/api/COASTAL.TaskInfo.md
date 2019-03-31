---
title: COASTAL.TaskInfo
permalink: /api/COASTAL.TaskInfo/
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
Summary of information about task kinds.<h2><a class="anchor" name="factory"></a>factory</h2>
<div markdown="1">
~~~java
private final TaskFactory factory
~~~
</div>
<p>
The factory that corresponds to this task.</p>
<h2><a class="anchor" name="manager"></a>manager</h2>
<div markdown="1">
~~~java
private final TaskFactory.TaskManager manager
~~~
</div>
<p>
The task manager.</p>
<h2><a class="anchor" name="initThreads"></a>initThreads</h2>
<div markdown="1">
~~~java
private final int initThreads
~~~
</div>
<p>
The number of initial threads.</p>
<h2><a class="anchor" name="minThreads"></a>minThreads</h2>
<div markdown="1">
~~~java
private final int minThreads
~~~
</div>
<p>
The minimum number of threads.</p>
<h2><a class="anchor" name="maxThreads"></a>maxThreads</h2>
<div markdown="1">
~~~java
private final int maxThreads
~~~
</div>
<p>
The maximum number of threads.</p>
<h2><a class="anchor" name="threadCount"></a>threadCount</h2>
<div markdown="1">
~~~java
private int threadCount
~~~
</div>
<p>
The current number of threads.</p>
<h2><a class="anchor" name="TaskInfo(COASTAL, TaskFactory, int, int, int)"></a>TaskInfo</h2>
<div markdown="1">
~~~java
 TaskInfo(COASTAL coastal, TaskFactory factory, int initThreads, int minThreads, int maxThreads)
~~~
</div>
Construct a new task summary.<h4>Parameters</h4>
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
factory<br/><span class="paramtype">TaskFactory</span></td>
<td>
the task factory</td>
</tr>
<tr>
<td>
initThreads<br/><span class="paramtype">int</span></td>
<td>
initial number of threads</td>
</tr>
<tr>
<td>
minThreads<br/><span class="paramtype">int</span></td>
<td>
minimum number of threads</td>
</tr>
<tr>
<td>
maxThreads<br/><span class="paramtype">int</span></td>
<td>
maximum number of threads</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="create(COASTAL)"></a>create</h2>
<div markdown="1">
~~~java
public TaskFactory.Task create(COASTAL coastal)
~~~
</div>
Create a new task of this kind. Sometimes, each task is in fact an array of
 tasks, which work together.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
coastal<br/><span class="paramtype">COASTAL</span></td>
<td>
instance of COASTAL</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the new task(s) as an array</p>
<h2><a class="anchor" name="getManager()"></a>getManager</h2>
<div markdown="1">
~~~java
public TaskFactory.TaskManager getManager()
~~~
</div>
Return the manager for this kind of task.<h4>Returns</h4>
<p>
the task manager</p>
<h2><a class="anchor" name="getInitThreads()"></a>getInitThreads</h2>
<div markdown="1">
~~~java
public int getInitThreads()
~~~
</div>
Return the initial number of threads.<h4>Returns</h4>
<p>
initial number of threads</p>
<h2><a class="anchor" name="getMinThreads()"></a>getMinThreads</h2>
<div markdown="1">
~~~java
public int getMinThreads()
~~~
</div>
Return the minimum number of threads.<h4>Returns</h4>
<p>
minimum number of threads</p>
<h2><a class="anchor" name="getMaxThreads()"></a>getMaxThreads</h2>
<div markdown="1">
~~~java
public int getMaxThreads()
~~~
</div>
Return the maximum number of threads.<h4>Returns</h4>
<p>
maximum number of threads</p>
<h2><a class="anchor" name="getThreadCount()"></a>getThreadCount</h2>
<div markdown="1">
~~~java
public int getThreadCount()
~~~
</div>
Return the current number of threads.<h4>Returns</h4>
<p>
current number of threads</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#factory">factory</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#initThreads">initThreads</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#manager">manager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#maxThreads">maxThreads</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#minThreads">minThreads</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#threadCount">threadCount</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#TaskInfo(COASTAL, TaskFactory, int, int, int)">TaskInfo(COASTAL, TaskFactory, int, int, int)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#create(COASTAL)">create(COASTAL)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#getInitThreads()">getInitThreads()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#getManager()">getManager()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#getMaxThreads()">getMaxThreads()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#getMinThreads()">getMinThreads()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}#getThreadCount()">getThreadCount()</a></li>
</ul>
</li>

</ul>
</section>
