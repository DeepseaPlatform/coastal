---
layout: page
title: Configuring a DEEPSEA run
permalink: /userguide/configuration/
---

Remember to specify how to change logging settings

## deepsea.target

The name of the class to analyse.
Omit the "`.java`" and "`.class`" extension.

~~~
deepsea.target = examples.spf.BinTree2
~~~

## deepsea.args

The command-line arguments required to run the class under analysis.

~~~
deepsea.args =
~~~

## deapsea.triggers

The list of methods that trigger (start) the symbolic analysis.
Multiple methods are separated with a colon ("`;`").
Each entry in the list is the fully qualified name of a method with its
parameters.

The parameters is given as a comma ("`,`")-separated list of
"`name:type`" and "`type`" entries.
Each parameter with a name is treated symbolically when the method is invoked;
parameters without a name remain concrete.

A trigger is only activated when the number of parameters and their types
match; this caters for method overloading.

~~~
deepsea.triggers = examples.simple.ArrayTestSimple.test(X: int, Y: int); \
	examples.simple.ArrayTestSimple.test2(X: int[])
~~~

## deepsea.bounds

Variable lower and upper bounds.

## deepsea.produceoutput

A boolean setting to control whether the output of the class under analysis
is displayed or supposed.

The default value is _false_.

Values that count as _true_ are: "`true`", "`yes`", "`on`", and "`1`".
Case is ignored.  All other values count as _false_.

## deepsea.config.dump

A boolean setting to control whether the configuration settings are displayed
when DEEPSEA starts up.

The default value is _false_.

Values that count as _true_ are: "`true`", "`yes`", "`on`", and "`1`".
Case is ignored.  All other values count as _false_.

~~~
deepsea.config.dump = true
~~~

