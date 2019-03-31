---
layout: home
permalink: /
---

<section class="hero"><div class="wrapper">
	<h1>COASTAL</h1>
	{% include svg/diver.svg %}
	<h2>A Java program analysis tool built on concolic execution and fuzz testing</h2>
	<div class="buttons">
		<a class="button" href="{{ '/userguide/getting-started/' | relative_url }}">Get started</a>
		<span class="github-button"><iframe src="https://ghbtns.com/github-btn.html?user=DeepseaPlatform&amp;repo=coastal&amp;type=star&amp;count=true&amp;size=large" frameBorder="0" scrolling="0" width="160" height="30" title="GitHub Stars"></iframe></span>
	</div>
	<div class="clearfix"></div>
</div></section>

<section class="announcement"><div class="wrapper">
	We're working on a major overhaul of this documentation.<br/>
	Please be patient: everything should be ready by <span style="color:#ffff00;">May 2019</span>.
</div></section>

<section class="frontpage-section other"><div class="wrapper">
	<div class="gridboxes">
		<div class="gridbox2">
			<h2>Something</h2>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
		</div>
		<div class="gridbox2">
			<h2>Something</h2>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
		</div>
	</div>
</div></section>

<section class="uses frontpage-section gray-background"><div class="wrapper">
	<h2>Applications of COASTAL</h2>
	<div class="gridboxes">
		<div class="gridbox3">
			{% include svg/clipboard.svg %}
			<h3>Test case generation</h3>
			<p>
				COASTAL can generate a test suite to test software up to a desired level of coverage in situations where a complete and executable specification of correct behaviour is not available.  A test suite that covers all behaviour is useful for regression testing and when migrating from legacy to new code.
			</p>
			<p>
				<a class="learnmore" href="{{ '/casestudy/test-generation/' | relative_url }}">Learn more</a>
			</p>
		</div>
		<div class="gridbox3">
			{% include svg/bug.svg %}
			<h3>Bug detection</h3>
			<p>
				Null pointer exceptions.
			</p>
			<p>
				<a class="learnmore" href="#">Learn more</a>
			</p>
		</div>
		<div class="gridbox3">
			{% include svg/pie-chart.svg %}
			<h3>Reliability analysis</h3>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
			<p>
				<a class="learnmore" href="#">Learn more</a>
			</p>
		</div>
	</div>
	<div class="gridboxes">
		<div class="gridbox3">
			{% include svg/stethoscope.svg %}
			<h3>Performance analysis</h3>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
			<p>
				<a class="learnmore" href="#">Learn more</a>
			</p>
		</div>
		<div class="gridbox3">
			{% include svg/scale.svg %}
			<h3>Differential program analysis</h3>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
			<p>
				<a class="learnmore" href="#">Learn more</a>
			</p>
		</div>
		<div class="gridbox3">
			{% include svg/dice.svg %}
			<h3>Probabilistic programming</h3>
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			</p>
			<p>
				<a class="learnmore" href="#">Learn more</a>
			</p>
		</div>
	</div>
</div></section>

<section class="research frontpage-section"><div class="wrapper">
	<div class="gridboxes image-right">
		<div class="gridbox2">
			<h2>Our research</h2>
			{% include svg/research.svg %}
			<p>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
			</p>
		</div>
	</div>
</div></section>

<!--
<div class="main-content">
<h1><span>Welcome to COASTAL</span></h1>

COASTAL is a program analysis tool that uses
the instrumentation
to monitor the execution of a Java
program.  As the program executes concretely, COASTAL collects information and
tracks the execution symbolically. 
When the program terminates, the symbolic information is used to calculate
different inputs that will force the program to follow a different path.  In
this way, the program is executed repeatedly until all possible paths have been
explored.
[Learn more →]({{ "/userguide/overview/" | relative_url }})

</div></div>
-->