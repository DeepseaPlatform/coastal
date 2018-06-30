---
layout: page
title: Configuring a COASTAL run
permalink: /userguide/configuration/
---

Remember to specify how to change logging settings

## Summary

| Setting | Description | Default |
|:--------|:------------|:--------|
| `coastal.targets` | Classes to be instrumented | - |
| `coastal.main` | Class to execute | - |
| `coastal.args` | Arguments passe to `main` | - |
| `coastal.triggers` | Classes to instrument | - |
| `coastal.bounds` | Bounds on symbolic variables | - |
| `coastal.echooutput` | Whether program output is displayed | `false` |
| `coastal.limit.run` | Limit on number of runs executed | 0 |
| `coastal.limit.time` | Limit on number of seconds allowed for runs | 0 |
| `coastal.limit.path` | Limit on number of paths investigated | 0 |
| `coastal.dump.config` | Whether configuration settings are displayed | `false` |
| `coastal.dump.asm` | Whether instrumented code displayed (at end) | `false` |
| `coastal.dump.trace` | Whether instructions are displayed as executed | `false` |
| `coastal.dump.frame` | Whether stack/locals are displayed after every instruction | `false` |
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
coastal.target = examples.spf.BinTree2
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

## coastal.echooutput

A boolean setting to control whether the output of the class under analysis
is displayed (true) or suppressed (false).  This is useful is the analysis
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
Subsequent (individual) settings will override this value. 

~~~
coastal.dump = true
~~~
