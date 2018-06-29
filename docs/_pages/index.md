---
layout: home
permalink: /
---

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

<div class="boxes">

<div class="box">
abc
</div>

<div class="box">
abc
</div>

<div class="box news">
	<span class="boxtitle">Recent news</span>
	<ul>
	{% for post in site.posts limit:5 %}
		<li>
			<span class="post-meta">{{ post.date | date_to_string }}:</span>
			<a class="post-link" href="{{ post.url | relative_url }}">{{ post.title | escape }}</a>
		</li>
	{% endfor %}
	</ul>
</div>

<div class="clearfix"></div>

</div>
