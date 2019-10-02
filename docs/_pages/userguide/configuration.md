---
title: Configuration settings
permalink: /userguide/configuration/
---

{% include svg/work-in-progress.svg %}

To learn how to analyze programs with COASTAL, you should read the [Usage]({{ "/userguide/basic-usage/" | relative_url }}) page.
As it explains, the convention is to create a file `XYZ.properties` that instructs COASTAL how to analyze program `XYZ.java`.
The property file is then passed as a command-line argument to COASTAL.

However, COASTAL also reads properties from other locations.  In all, it checks the following files in the following order:

  1. The `coastal.properties` file is loaded first (if it exists).  This file is part of the resources distributed with the project.  It allows the user to specify default values for properties so that they do not have to be repeated over and over in individual property files.
  
  2. Next, the `~/.coastal/coastal.properties` file is loaded (if it exists).  The "`~`" refers to the user's home directory.  On Windows, this is _**TODO**_.
  
  3. Next, the properties files specified on the command-line are loaded.
  
  4. Lastly, any `-set` command-line options may add additional properties for the current run.
  
Settings in later files override settings in earlier files.
Once all properties has been loaded, they are processed.

## Logging

Many of the properties specify which information is logged.
COASTAL uses Apache Log4j2 for logging, and the `log4j2.xml` file controls where (and which) logging information is stored.
The default is to write an abbreviated log to the standard output,
and to write a detailed log to the file `/tmp/coastal.log`.
This should be changed on Windows (because the directory is invalid),
and may be changed on Unix or OSX, if the user desired.
To do so, the user can create a file `./WEB-INFO/log4j2.xml` which will be loaded
instead of the default file distributed with COASTAL.

## Summary of properties

There are eight major categories of properties.

| Setting | Description | Default |
|:--------|:------------|:--------|
| `coastal.target...` | Information about the system under test | - |
| `coastal.bounds...` | Bounds on symbolic variables | - |
| `coastal.settings...` | Configuration of COASTAL behaviour | - |
| `coastal.divers...` | Configure divers | - |
| `coastal.surfers...` | Configure surfers | - |
| `coastal.strategies...` | Strategies for generating new path conditions | - |
| `coastal.observers...` | Observers | - |
| `coastal.delegates...` | Delegated classes | - |

| `coastal.limit.run` | Limit on number of runs executed | 0 |
| `coastal.limit.time` | Limit on number of seconds allowed for runs | 0 |
| `coastal.limit.path` | Limit on number of paths investigated | 0 |
| `coastal.limit.conjuncts` | Limit on number of conjuncts investigated per path | 0 |

## coastal.target...

| Setting | Description | Default |
|:--------|:------------|:--------|
| `coastal.target.args` | Arguments passed when execution starts | - |
| `coastal.target.entrypoint` | Method to start execution | - |
| `coastal.target.instrument` | Classes to instrument | - |
| `coastal.target.jars` | Additional jar files that contain classes | - |
| `coastal.target.main` | Class to execute | - |
| `coastal.target.trigger` | Methods that switch symbolic mode on | - |

List of class prefixes that will be instrumented.
Multiple prefixes are separated with a semicolon ("`;`").
Any class whose full name starts with one or more prefixes, will be instrumented.

~~~
coastal.targets = examples.spf
~~~

### coastal.target.jars

~~~
coastal.target.jars.directory
~~~

## coastal.bounds...

Variable lower and upper bounds.  For each symbolic variable name that
appears in the triggers, a lower and upper bound can be specified.
To specify a lower bound for variable "`X`", write

~~~
coastal.bounds.X.min = 2
~~~

To specify an upper bound for variable "`X`", write

~~~
coastal.bounds.X.max = 20
~~~

These two settings can be combined in one setting:

~~~
coastal.bounds.X = 2..20
~~~

## coastal.bounds...

## coastal.settings...

| Setting | Description | Default |
|:--------|:------------|:--------|
| `coastal.settings.concrete-values` | Whether actual return values are used | `false` |
| `coastal.settings.constant-elimination` | Whether constant conjuncts are ignored | `true` |
| `coastal.settings.echo-output` | Whether program output is displayed | `false` |
| `coastal.settings.draw-final-tree` | Whether path tree is displayed at end of run | `false` |
| `coastal.settings.draw-paths` | Whether path trees are displayed in detailed log | `false` |
| `coastal.settings.solver` | Specify the constraint solver to use | - |
| `coastal.settings.show-instrumentation` | Whether instrumented instructions are logged | `false` |
| `coastal.settings.trace-all` | Whether all instructions are tracked symbolically | `false` |
| `coastal.settings.value-factory` | Specify the value factory to use | - |
| `coastal.settings.write-classfile` | Where instrumented class files are written | - |

### coastal.settings.concrete-values

### coastal.settings.constant-elimination

### coastal.settings.echo-output

A boolean setting to control whether the output of the class under analysis
is displayed (true) or suppressed (false).  This is useful if the analysis
does not behave as expected.

~~~
coastal.settings.echo-output = true
~~~

The default value is _false_.
Values that count as _true_ are: "`true`", "`yes`", "`on`", and "`1`".
Case is ignored.  All other values count as _false_.

### coastal.settings.draw-final-tree

### coastal.settings.draw-paths

A boolean setting to control whether the tree of explored paths is displayed
after each path insertion.  This is only output to the detailed log, but for
large trees it is time consuming and slows down the exploration.

~~~
coastal.settings.draw-paths = true
~~~

The default value is _false_.

### coastal.settings.show-instrumentation

### coastal.settings.trace-all

### coastal.settings.write-classfile

## coastal.strategies...

A fully-qualified name of a class that implements a strategy for generating
new path conditions and therefore new inputs for the program-under-test.

This is an important property that is required for COASTAL to execute at all.

~~~
coastal.strategy = za.ac.sun.cs.coastal.strategy.DepthFirstStrategy
~~~

## coastal.main

The name of the main class to analyse.
This class should have a main method with the signature "`public static void main(String[] args)`". 
Omit the "`.java`" and "`.class`" extension.

~~~
coastal.main = examples.spf.BinTree2
~~~

## coastal.args

The command-line arguments required to run the class under analysis.

~~~
coastal.args = 2 3
~~~

## coastal.triggers

The list of methods that trigger (start) the symbolic analysis.
Multiple methods are separated with a semicolon ("`;`").
Each entry in the list is the fully qualified name of a method with its
parameters, but without its return type.

The parameters is given as a comma ("`,`")-separated list of
"`name:type`" and "`type`" entries.
Each parameter with a name is treated symbolically when the method is invoked;
parameters without a name remain concrete.

A trigger is only activated when the number of parameters and their types
match; this caters for method overloading.

~~~
coastal.triggers = examples.simple.ArrayTestSimple.test(X: int, Y: int); \
	examples.simple.ArrayTestSimple.test2(X: int[])
~~~

The following types are supported:

- `int`
- `int[]`
- `String`
- `String[]`

## coastal.delegates...

List of delegated classes.
Multiple delegates are separated with a semicolon ("`;`").
Each delegate is a pair: *T:D*, where both *T* and *D* are fully
qualified class names.
The former denotes a target that is delegated to the latter.
Whenever a method *T.M* is invoked, the method *D.M* is executed on the
symbolic state, if such a method exists. 
 
~~~
coastal.delegates = java.lang.Math:za.ac.sun.cs.coastal.models.Math
~~~

## coastal.echooutput

## coastal.concrete.values

A boolean setting to control whether the actual values of uninstrumented methods
are used (true) or whether symbolic return values are introduced (false).

~~~
coastal.concrete.values = true
~~~

The default value is _true_.

## coastal.trace.all

A boolean setting to control whether all code is symbolically executed.
This is required for the proper analysis of classes with static and or global data.

~~~
coastal.trace.all = true
~~~

The default value is _false_.

## coastal.limit.run

An upper limit on the number of runs that are executed.
A value of zero means that there is no limit.

~~~
coastal.limit.run = 100
~~~

## coastal.limit.time

An upper limit on the number of seconds allowed for run execution.
A value of zero means that there is no limit.

~~~
coastal.limit.time = 6000
~~~

## coastal.limit.path

An upper limit on the number of paths that are recorded.
This is distinct from the number of runs, because one run may lead to
several (infeasible) paths being generated.
A value of zero means that there is no limit.

~~~
coastal.limit.path = 200
~~~

The default value is 0.

## coastal.limit.conjuncts

An upper limit on the number of conjuncts that are allowed per path.
Once the limit has been reached, no further conjuncts are recorded and
the symbolic execution of the program is stopped.
A value of zero means that there is no limit.

~~~
coastal.limit.conjuncts = 50
~~~

The default value is 0.

The default value is _false_.

## green...

		greenProperties.setProperty("green.log.level", "ALL");
		greenProperties.setProperty("green.services", "model");
		greenProperties.setProperty("green.service.model", "(modeller)");
		greenProperties.setProperty("green.service.model.bounder", "za.ac.sun.cs.green.service.bounder.BounderService");
		greenProperties.setProperty("green.service.model.modeller", "za.ac.sun.cs.green.service.z3.ModelZ3Service");
