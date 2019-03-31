---
title: MTRandom
permalink: /api/MTRandom/
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
Combination of <code>org.apache.commons.math3.random.MersenneTwister.java</code>
 and <code>java.util.concurrent.ThreadLocalRandom.java</code>. Implements a
 powerful pseudo-random number generator developed by Makoto Matsumoto and
 Takuji Nishimura during 1996-1997.
 
 <p>
 This generator features an extremely long period (2<sup>19937</sup>-1) and
 623-dimensional equidistribution up to 32 bits accuracy. The home page for
 this generator is located at
 <a href="http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html">
 http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html</a>.
 </p>
 
 <p>
 This generator is described in a paper by Makoto Matsumoto and Takuji
 Nishimura in 1998: <a href=
 "http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/ARTICLES/mt.pdf">Mersenne
 Twister: A 623-Dimensionally Equidistributed Uniform Pseudo-Random Number
 Generator</a>, ACM Transactions on Modeling and Computer Simulation, Vol. 8,
 No. 1, January 1998, pp 3--30
 </p>
 
 <p>
 This class is mainly a Java port of the 2002-01-26 version of the generator
 written in C by Makoto Matsumoto and Takuji Nishimura. Here is their original
 copyright:
 </p>
 
 <table border="0" width="80%" cellpadding="10">
 <caption>Copyright information</caption>
 <tr>
 <td>Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura, All
 rights reserved.</td>
 </tr>
 
 <tr>
 <td>Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 <ol>
 <li>Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.</li>
 <li>Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.</li>
 <li>The names of its contributors may not be used to endorse or promote
 products derived from this software without specific prior written
 permission.</li>
 </ol>
 </td>
 </tr>
 
 <tr>
 <td><strong>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</strong></td>
 </tr>
 </table><h2><a class="anchor" name="DOUBLE_UNIT"></a>DOUBLE_UNIT</h2>
<div markdown="1">
~~~java
private static final double DOUBLE_UNIT = 1.1102230246251565E-16
~~~
</div>
<p>
</p>
<h2><a class="anchor" name="N"></a>N</h2>
<div markdown="1">
~~~java
private static final int N = 624
~~~
</div>
<p>
Size of the bytes pool.</p>
<h2><a class="anchor" name="M"></a>M</h2>
<div markdown="1">
~~~java
private static final int M = 397
~~~
</div>
<p>
Period second parameter.</p>
<h2><a class="anchor" name="MAG01"></a>MAG01</h2>
<div markdown="1">
~~~java
private static final int MAG01
~~~
</div>
<p>
X * MATRIX_A for X = {0, 1}.</p>
<h2><a class="anchor" name="mt"></a>mt</h2>
<div markdown="1">
~~~java
private int mt
~~~
</div>
<p>
Bytes pool.</p>
<h2><a class="anchor" name="mti"></a>mti</h2>
<div markdown="1">
~~~java
private int mti
~~~
</div>
<p>
Current index in the bytes pool.</p>
<h2><a class="anchor" name="MTRandom()"></a>MTRandom</h2>
<div markdown="1">
~~~java
public MTRandom()
~~~
</div>
Creates a new random number generator.
 
 <p>
 The instance is initialized using the current time plus the system
 identity hash code of this instance as the seed.
 </p><h2><a class="anchor" name="MTRandom(int)"></a>MTRandom</h2>
<div markdown="1">
~~~java
public MTRandom(int seed)
~~~
</div>
Creates a new random number generator using a single int seed.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">int</span></td>
<td>
the initial seed (32 bits integer)</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="MTRandom(int[])"></a>MTRandom</h2>
<div markdown="1">
~~~java
public MTRandom(int seed)
~~~
</div>
Creates a new random number generator using an int array seed.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">int</span></td>
<td>
the initial seed (32 bits integers array), if null the seed of
            the generator will be related to the current time</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="MTRandom(long)"></a>MTRandom</h2>
<div markdown="1">
~~~java
public MTRandom(long seed)
~~~
</div>
Creates a new random number generator using a single long seed.<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">long</span></td>
<td>
the initial seed (64 bits integer)</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setSeed(int)"></a>setSeed</h2>
<div markdown="1">
~~~java
public void setSeed(int seed)
~~~
</div>
Reinitialize the generator as if just built with the given int seed.
 
 <p>
 The state of the generator is exactly the same as a new generator built
 with the same seed.
 </p><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">int</span></td>
<td>
the initial seed (32 bits integer)</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setSeed(int[])"></a>setSeed</h2>
<div markdown="1">
~~~java
public void setSeed(int seed)
~~~
</div>
Reinitialize the generator as if just built with the given int array
 seed.
 
 <p>
 The state of the generator is exactly the same as a new generator built
 with the same seed.
 </p><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">int</span></td>
<td>
the initial seed (32 bits integers array), if null the seed of
            the generator will be the current system time plus the system
            identity hash code of this instance</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="setSeed(long)"></a>setSeed</h2>
<div markdown="1">
~~~java
public void setSeed(long seed)
~~~
</div>
Reinitialize the generator as if just built with the given long seed.
 
 <p>
 The state of the generator is exactly the same as a new generator built
 with the same seed.
 </p><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
seed<br/><span class="paramtype">long</span></td>
<td>
the initial seed (64 bits integer)</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="next(int)"></a>next</h2>
<div markdown="1">
~~~java
protected int next(int bits)
~~~
</div>
Generate next pseudo-random number.
 
 <p>
 This method is the core generation algorithm. It is used by all the public
 generation methods for the various primitive types <code><a href="{{ '/api/next/' | relative_url }}#nextBoolean()">nextBoolean()</a>
</code>,
 <code><a href="{{ '/api/next/' | relative_url }}#nextInt()">nextInt()</a>
</code>, <code><a href="{{ '/api/next/' | relative_url }}#next(int)">next(int)</a>
</code> and <code><a href="{{ '/api/next/' | relative_url }}#nextLong()">nextLong()</a>
</code>.
 </p><h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
bits<br/><span class="paramtype">int</span></td>
<td>
number of random bits to produce</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
random bits generated</p>
<h2><a class="anchor" name="nextBoolean()"></a>nextBoolean</h2>
<div markdown="1">
~~~java
public boolean nextBoolean()
~~~
</div>
Return the next random <code>boolean</code> value.<h4>Returns</h4>
<p>
next random <code>boolean</code></p>
<h2><a class="anchor" name="nextInt()"></a>nextInt</h2>
<div markdown="1">
~~~java
public int nextInt()
~~~
</div>
Return the next random <code>int</code> value.<h4>Returns</h4>
<p>
next random <code>int</code></p>
<h2><a class="anchor" name="nextInt(int)"></a>nextInt</h2>
<div markdown="1">
~~~java
public int nextInt(int bound)
~~~
</div>
Return the next random <code>int</code> value that is greater than or equal to 0
 and less than a given bound<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
bound<br/><span class="paramtype">int</span></td>
<td>
upper bound on random values</td>
</tr>
</tbody>
</table>
<h4>Returns</h4>
<p>
next random <code>int</code> within bounds</p>
<h2><a class="anchor" name="nextInt(int, int)"></a>nextInt</h2>
<div markdown="1">
~~~java
public int nextInt(int origin, int bound)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
origin<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
<tr>
<td>
bound<br/><span class="paramtype">int</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="nextLong()"></a>nextLong</h2>
<div markdown="1">
~~~java
public long nextLong()
~~~
</div>
Return the next random <code>long</code> value.<h4>Returns</h4>
<p>
next random <code>long</code></p>
<h2><a class="anchor" name="nextLong(long)"></a>nextLong</h2>
<div markdown="1">
~~~java
public long nextLong(long bound)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
bound<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="nextLong(long, long)"></a>nextLong</h2>
<div markdown="1">
~~~java
public long nextLong(long origin, long bound)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
origin<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
<tr>
<td>
bound<br/><span class="paramtype">long</span></td>
<td>
</td>
</tr>
</tbody>
</table>
<h2><a class="anchor" name="nextDouble(double, double)"></a>nextDouble</h2>
<div markdown="1">
~~~java
public double nextDouble(double origin, double bound)
~~~
</div>
<h4>Parameters</h4>
<table class="parameters">
<tbody>
<tr>
<td>
origin<br/><span class="paramtype">double</span></td>
<td>
</td>
</tr>
<tr>
<td>
bound<br/><span class="paramtype">double</span></td>
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
<a href="{{ '/api/MTRandom/' | relative_url }}#DOUBLE_UNIT">DOUBLE_UNIT</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#M">M</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#MAG01">MAG01</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#N">N</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Fields<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#mt">mt</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#mti">mti</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Constructors<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#MTRandom()">MTRandom()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#MTRandom(int)">MTRandom(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#MTRandom(int[])">MTRandom(int[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#MTRandom(long)">MTRandom(long)</a></li>
</ul>
</li>
<li class="toc-entry toc-h2">
Methods<ul>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#next(int)">next(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextBoolean()">nextBoolean()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextDouble(double, double)">nextDouble(double, double)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextInt()">nextInt()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextInt(int)">nextInt(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextInt(int, int)">nextInt(int, int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextLong()">nextLong()</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextLong(long)">nextLong(long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#nextLong(long, long)">nextLong(long, long)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#setSeed(int)">setSeed(int)</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#setSeed(int[])">setSeed(int[])</a></li>
<li class="toc-entry toc-h3">
<a href="{{ '/api/MTRandom/' | relative_url }}#setSeed(long)">setSeed(long)</a></li>
</ul>
</li>

</ul>
</section>
