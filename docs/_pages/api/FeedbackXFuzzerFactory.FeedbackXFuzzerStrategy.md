---
title: FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy
permalink: /api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/
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
<h2><a class="anchor" name="COUNT_TO_BUCKET"></a>COUNT_TO_BUCKET</h2>
<div markdown="1">
~~~java
public static final int COUNT_TO_BUCKET
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="manager"></a>manager</h2>
<div markdown="1">
~~~java
protected final strategy.tracebased.FeedbackXFuzzerFactory.FeedbackXFuzzerManager manager
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="broker"></a>broker</h2>
<div markdown="1">
~~~java
protected final messages.Broker broker
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="visitedModels"></a>visitedModels</h2>
<div markdown="1">
~~~java
protected final Set visitedModels
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="inputsAdded"></a>inputsAdded</h2>
<div markdown="1">
~~~java
protected int inputsAdded
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="parameters"></a>parameters</h2>
<div markdown="1">
~~~java
protected Map parameters
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="queueLimit"></a>queueLimit</h2>
<div markdown="1">
~~~java
protected final int queueLimit
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="rng"></a>rng</h2>
<div markdown="1">
~~~java
private final strategy.MTRandom rng
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="attenuation"></a>attenuation</h2>
<div markdown="1">
~~~java
private final double attenuation
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="firstRepeat"></a>firstRepeat</h2>
<div markdown="1">
~~~java
protected final int firstRepeat
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="pairRepeat"></a>pairRepeat</h2>
<div markdown="1">
~~~java
protected final int pairRepeat
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="highScore"></a>highScore</h2>
<div markdown="1">
~~~java
private int highScore
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="setValues"></a>setValues</h2>
<div markdown="1">
~~~java
private Set setValues
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="incValues"></a>incValues</h2>
<div markdown="1">
~~~java
private Set incValues
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="FeedbackXFuzzerStrategy"></a>FeedbackXFuzzerStrategy</h2>
<div markdown="1">
~~~java
public FeedbackXFuzzerStrategy(COASTAL coastal, strategy.StrategyFactory.StrategyManager manager)
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
manager<br/><span class="paramtype">strategy.StrategyFactory.StrategyManager</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="call"></a>call</h2>
<div markdown="1">
~~~java
public Void call()
~~~
</div>
<h2><a class="anchor" name="refineFirst"></a>refineFirst</h2>
<div markdown="1">
~~~java
protected void refineFirst(symbolic.Execution execution, int score)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
<tr>
<td>
score<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="refinePair"></a>refinePair</h2>
<div markdown="1">
~~~java
protected void refinePair(symbolic.Execution execution1, int score1, symbolic.Execution execution2, int score2)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution1<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
<tr>
<td>
score1<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
execution2<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
</td>
</tr>
<tr>
<td>
score2<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="mutatem"></a>mutatem</h2>
<div markdown="1">
~~~java
protected void mutatem(int score, symbolic.Input input)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
score<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="update"></a>update</h2>
<div markdown="1">
~~~java
private void update(int score, symbolic.Input input, String name, int min, int max)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
score<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
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
</tbody>
</table>
<h2><a class="anchor" name="submitInput"></a>submitInput</h2>
<div markdown="1">
~~~java
private void submitInput(int score, symbolic.Input input)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
score<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
input<br/><span class="paramtype">symbolic.Input</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="randomInt"></a>randomInt</h2>
<div markdown="1">
~~~java
private int randomInt(int min, int max)
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
</tbody>
</table>
<h2><a class="anchor" name="randomLong"></a>randomLong</h2>
<div markdown="1">
~~~java
private long randomLong(long min, long max)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
min<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
max<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="calculateScore"></a>calculateScore</h2>
<div markdown="1">
~~~java
private int calculateScore(symbolic.Execution execution)
~~~
</div>
Calculate the score for a trace. First, the trace is scanned for its
 "blocks", which are line numbers that represent basic blocks.
 Consecutive blocks form tuples, the tuples are counted, and the
 counts are converted according to the "bucket scale":
 
 <ul>
 <li>1 occurrence = bucket 1</li>
 <li>2 occurrences = bucket 2</li>
 <li>3 occurrences = bucket 3</li>
 <li>4&ndash;7 occurrences = bucket 4</li>
 <li>8&ndash;15 occurrences = bucket 5</li>
 <li>16&ndash;31 occurrences = bucket 6</li>
 <li>32&ndash;127 occurrences = bucket 7</li>
 <li>&ge;128 occurrences = bucket 8</li>
 </ul>

 This information is passed to the manager, which returns a score
 based on the counts. Finally, the score of the trace's parent trace
 is added but scaled by a factor.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
execution<br/><span class="paramtype">symbolic.Execution</span></td>
<td>
the trace to calculate a score for</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
the score</p>
</section>
<section class="apitoc">
<ul class="section-nav">
<li class="toc-entry toc-h2">
Constants<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#COUNT_TO_BUCKET">COUNT_TO_BUCKET</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#attenuation">attenuation</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#broker">broker</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#firstRepeat">firstRepeat</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#highScore">highScore</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#incValues">incValues</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#inputsAdded">inputsAdded</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#manager">manager</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#pairRepeat">pairRepeat</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#parameters">parameters</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#queueLimit">queueLimit</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#rng">rng</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#setValues">setValues</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#visitedModels">visitedModels</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#FeedbackXFuzzerStrategy">FeedbackXFuzzerStrategy(COASTAL, StrategyFactory.StrategyManager)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#calculateScore">calculateScore(Execution)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#call">call()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#mutatem">mutatem(int, Input)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#randomInt">randomInt(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#randomLong">randomLong(long, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#refineFirst">refineFirst(Execution, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#refinePair">refinePair(Execution, int, Execution, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#submitInput">submitInput(int, Input)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/FeedbackXFuzzerFactory.FeedbackXFuzzerStrategy/' | relative_url }}#update">update(int, Input, String, int, int)</a></li>
</ul>
</li>

</ul>
</section>
