---
layout: page
title: Configuring a COASTAL run
permalink: /userguide/configuration/
---

To learn how to analyze programs with COASTAL, you should read the Usage page.
As it explains, the convention is to create a file `XYZ.properties` that instructs COASTAL how to analyze program `XYZ.java`.
The property file is then passed as the first command-line argument to COASTAL.

However, COASTAL also reads properties from two other locations.  In all, it checks the following files in the following order:

  1. The `coastal.properties` file is loaded first (if it exists).  This file is part of the resources distributed with the project.  It allows the user to specify default values for properties so that they do not have to be repeated over and over in individual property files.
  
  2. Next, the `~/.coastal/coastal.properties` file is loaded (if it exists).  The "`~`" refers to the user's home directory.  On Windows, this is _**TODO**_.
  
  3. Lastly, the properties file specified on the command-line is loaded.
  
Settings in later files, and override settings in earlier files.
Once all properties has been loaded, they are processed.
This last point is important, because it means that if an earlier file specified `coastal.dump.X = true`,
and a later file specified that `coastal.dump = false`, then the `coastal.dump.X` value is still true.
The order in which the settings appear in properties file is not important;
the `coastal.dump.X` property is processed *after* the `coastal.dump` property, which explains this behavior.

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

| Setting | Description | Default |
|:--------|:------------|:--------|
| `coastal.targets` | Classes to be instrumented | - |
| `coastal.main` | Class to execute | - |
| `coastal.args` | Arguments passe to `main` | - |
| `coastal.strategy` | Strategy for generating new path conditions | - |
| `coastal.triggers` | Classes to instrument | - |
| `coastal.bounds` | Bounds on symbolic variables | - |
| `coastal.delegates` | Delegated classes | - |
| `coastal.echooutput` | Whether program output is displayed | `false` |
| `coastal.limit.run` | Limit on number of runs executed | 0 |
| `coastal.limit.time` | Limit on number of seconds allowed for runs | 0 |
| `coastal.limit.path` | Limit on number of paths investigated | 0 |
| `coastal.limit.conjuncts` | Limit on number of conjuncts investigated per path | 0 |
| `coastal.dump.config` | Whether configuration settings are displayed | `false` |
| `coastal.dump.asm` | Whether instrumented code displayed (at end) | `false` |
| `coastal.dump.trace` | Whether instructions are displayed as executed | `false` |
| `coastal.dump.frame` | Whether stack/locals are displayed after every instruction | `false` |
| `coastal.dump.paths` | Whether path tree construction information is logged | `false` |
| `coastal.dump.instrumenter` | Whether code instrumenter produces debugging | `false` |
| `coastal.dump` | Set all dump settings | - |

## coastal.targets

List of class prefixes that will be instrumented.
Multiple prefixes are separated with a semicolon ("`;`").
Any class whose full name starts with one or more prefixes, will be instrumented.

~~~
coastal.targets = examples.spf
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

## coastal.strategy

A fully-qualified name of a class that implements a strategy for generating
new path conditions and therefore new inputs for the program-under-test.

This is an important property that is required for COASTAL to execute at all.

~~~
coastal.strategy = za.ac.sun.cs.coastal.strategy.DepthFirstStrategy
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

## coastal.bounds

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

## coastal.delegates

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

A boolean setting to control whether the output of the class under analysis
is displayed (true) or suppressed (false).  This is useful if the analysis
does not behave as expected.

~~~
coastal.echooutput = true
~~~

The default value is _false_.
Values that count as _true_ are: "`true`", "`yes`", "`on`", and "`1`".
Case is ignored.  All other values count as _false_.

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

## coastal.dump.config

A boolean setting to control whether the tool configuration should be
dumped to the log.

~~~
coastal.dump.config = true
~~~

The default value is _false_.

## coastal.dump.asm

A boolean setting to control whether the instrumented bytecodes should
be dumped to the log at the end of the execution of the tool.
This is mainly for debugging purposes and users should not need to use this.

~~~
coastal.dump.asm = false
~~~

The default value is _false_.

## coastal.dump.trace

A boolean setting to control whether the instructions should be dumped to
the log as they are executed.
This is mainly for debugging purposes and users should not need to use this.

~~~
coastal.dump.trace = false
~~~

The default value is _false_.

## coastal.dump.frame

A boolean setting to control whether the frames (operand stack, local variables,
and the heap) should be dumped to the log after each instruction.
This is mainly for debugging purposes and users should not need to use this.

~~~
coastal.dump.frame = false
~~~

The default value is _false_.

## coastal.dump.paths

A boolean setting to control whether step-by-step debugging information is displayed
as the tree of paths is updated.
This includes a textual representation of the tree which is of considerable help
when tracking down errors.
This is mainly for debugging purposes and users should not need to use this.

~~~
coastal.dump.paths = false
~~~

The default value is _false_.

## coastal.dump.instrumenter

A boolean setting to control whether the instrumenting method calls should
be dumped to the log as they are executed.
This is mainly for debugging purposes and users should not need to use this.

~~~
coastal.dump.instrumenter = false
~~~

The default value is _false_.

## coastal.dump

This settings will set all of the above `coastal.dump...` settings at once.
Subsequent (individual) settings will override this value, but this setting
will *not* override the individual settings. 

~~~
coastal.dump = true
~~~
