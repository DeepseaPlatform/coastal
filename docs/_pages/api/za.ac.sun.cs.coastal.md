---
title: za.ac.sun.cs.coastal
permalink: /api/za.ac.sun.cs.coastal/
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
<section class="main package">
<h1>{{ page.title | escape }}</h1>
Main package for the COASTAL concolic execution framework. Includes some
 helper classes, mainly for initialization and very high-level abstractions.
 
 <h2>Interfaces</h2>
<table class="classes">
<tbody>
<tr>
<td>
<a href="{{ '/api/Reporter.Reportable/' | relative_url }}">Reporter.Reportable</a></td>
<td>
An interface to allow one component of COASTAL (such as an observer) to
 interrogate other components with regard to any information they wish to
 distribute.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/TaskFactory/' | relative_url }}">TaskFactory</a></td>
<td>
Base class for diver, surfer, and strategy factories.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/TaskFactory.Task/' | relative_url }}">TaskFactory.Task</a></td>
<td>
A task that will be run inside its own threads.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/TaskFactory.TaskManager/' | relative_url }}">TaskFactory.TaskManager</a></td>
<td>
A general interface for task managers.</td>
</tr>
</tbody>
</table>
<h2>Classes</h2>
<table class="classes">
<tbody>
<tr>
<td>
<a href="{{ '/api/Banner/' | relative_url }}">Banner</a></td>
<td>
Utility class for creating more visible banners in the log output.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/COASTAL/' | relative_url }}">COASTAL</a></td>
<td>
A COASTAL analysis run.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/COASTAL.TaskInfo/' | relative_url }}">COASTAL.TaskInfo</a></td>
<td>
Summary of information about task kinds.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/ConfigHelper/' | relative_url }}">ConfigHelper</a></td>
<td>
A collection of routines to aid in loading the COASTAL configuration.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/ConfigHelper.ConfigCombiner/' | relative_url }}">ConfigHelper.ConfigCombiner</a></td>
<td>
Special combiner that merge (not overwrite) selected nodes in an Apache
 XML configuration.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Reporter/' | relative_url }}">Reporter</a></td>
<td>
A collector of runtime information and statistics about various aspects of a
 COASTAL analysis run.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Symbolic/' | relative_url }}">Symbolic</a></td>
<td>
A placeholder class that can be used in an SUT (system under test) to interact
 with COASTAL.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Trigger/' | relative_url }}">Trigger</a></td>
<td>
Triggers describe the methods and their symbolic parameters that switch on
 symbolic execution.</td>
</tr>
<tr>
<td>
<a href="{{ '/api/Version/' | relative_url }}">Version</a></td>
<td>
Static methods to compute the current version of COASTAL.</td>
</tr>
</tbody>
</table>
<h2>Description</h2>

 
 <p>
 COASTAL can be used directly from the command line.  (Insert example.)
 </p>
 
 <p>
 COASTAL can also be used programmatically.  Here is code to construct and execute an instance of COASTAL:
 </p>
 
 <div markdown="1">
~~~java
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Reporter;

{
   final Logger log = LogManager.getLogger("MY-COASTAL");
   ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, "basic.xml",
      "<coastal><target><trigger>mypackage.MyProgram.calculate(X: int)</trigger></target></coastal>");
   if (config == null) {
      System.out.println("Configuration error");
      System.exit(1);
   }
   final COASTAL coastal = new COASTAL(log, config);
   if (coastal == null) {
      System.out.println("COASTAL constructor error");
      System.exit(1);
   }
   coastal.start(false);
   final Reporter reporter = coastal.getReporter();
   System.out.println("Number of paths: " + reporter.getLong("PathTree.inserted-count"));
}
 ~~~
</div>
</section>
