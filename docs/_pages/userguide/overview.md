---
title: How COASTAL works
permalink: /userguide/overview/
---


<div class="quotation">
	You only write code because you expect it to get executed. If you expect it
	to get executed, you ought to know that it works. The only way to know this
	is to test it.
<div class="source">
	Robert C Martin, The Clean Coder, 2011
</div></div>

Bob Martin's argument for testing is compelling, but it raises an important
question.  How do we test code properly?  It is not practical to try all
possible input values, so how can we determine which input values to use, and
when we can stop testing?
One approach to these questions is to realize that we are not interested in
testing a program for all possible **inputs**, but for all possible
**behaviours**.

## Dynamic symbolic execution

Dynamic symbolic execution (DSE) approaches this problem by repeating the
following steps:

1. The software system under test (SUT) is executed with input values
_V1_.

2. While the SUT executes it is monitored to observe its behaviour.

3. Using this information, new input values _V2_ that exercise a new
behaviour, is calculated.

<!-- include figure.html name="figures/overview-dse.md" -->

The "symbolic" in DSE refers to how the behaviour is recorded, and how this is
used to calculate new input values.  Specifically, every concrete action of
the SUT, is mirrored by a corresponding symbolic action in the DSE
engine.  For example, suppose that the concrete variable _a_ contains the
concrete input value _5_, and its mirrored countered part contains the symbolic
value _A_.  If _a_ is incremented on the concrete side, it will contain the
concrete value _6_.  This is just the normal behaviour of the SUT.  On the
symbolic side, however, _a_ will be changed to contain the value _A+1_.

<!-- include figure.html name="figures/overview-sym.md" -->

The mirrored symbolic state plays an important role when the SUT performs a
control flow action.  For example, every time a branch is taken on the
concrete side, the symbolic branch condition is recorded.  When the SUT
terminates, the set of recorded conditions describes the execution path (=
**behaviour**) of the SUT for the given input values.  The conjunction of the
set of conditions is known as the **_path condition_** (PC) of the execution.

Although DSE was proposed in the mid-1990s (and symbolic execution is even
older, dating from the 1970s), it is only since the early 2000s that it has
really become a feasible approach.
This is due to advances in SMT
([Satisfiability modulo theories](https://en.wikipedia.org/wiki/Satisfiability_modulo_theories)).
We are now able to pass complex PCs to an SMT solver and expect it
to respond in a reasonable time.  The SMT solver will determine whether or not
a PC is _satisfiable_ and, if so, can provide one or more examples of input
values that satisfy it.  Such an example of input values is commonly called a
**_model_**.

Given a PC _p1 &and; p2 &and; p3_, the DSE engine will negate one or
more of the conjuncts.  For example, it may change the PC to
_p1 &and; p2 &and; &not;p3_.
If the new PC is satisfiable, the SMT solver will provide a model
that, when passed to the SUT, will force it to exhibit the
behaviour described by the modified PC.

By carefully managing the PCs, the DSE engine is able to explore the full
behaviour of the SUT.  Of course, there are some caveats.
<!-- fidelity -->
If a model for a PC is fed to the SUT, the resulting PC may be
different from the original when the input values expose "new" behaviours.
<!-- completeness -->
There are also boundary condition behaviour: the SUT may not terminate. Or, the
number of behaviours may be prohibitively large, and the DSE engine must decide
to ignore certain behaviours.
<!-- soundness -->
Lastly, there is the issue of _soundness_:  if a behaviour of the SUT is too
complex (because it is "hidden" inside a native library), the PC may not
accurately reflect the behaviour.  This can lead to situations where the DSE
engine reports that a set of input values lead to an error, whereas the
SUT behaves correctly.  Or conversely, the DSE reports
that a set of input values elicit correct behaviour, but in reality the SUT
fails for those inputs.

The DSE engine can easily generate a set of test inputs, but it is important to
remember that it cannot produce a complete test suite unless it has
access to some form of test oracle.
The set of behaviours can also be used to perform other kinds of analyses, such
as coverage analysis.

## COASTAL

COASTAL is a DSE engine that uses the [ASM](https://asm.ow2.io/) Java bytecode manipulation library.
This allows clients to launch, monitor, and control instance of
the Java Virtual Machine (JVM).
To investigate the SUT, a JVM instance is created and
executed repeatedly.  The very first run is unconstrained; subsequent runs are
directed (by modifying method arguments on-the-fly) so that unexplored
behaviours of the SUT are exercised.

<!-- include figure.html name="figures/overview-ds.md" -->
<!-- include_relative figures/overview1.md width="25rem" -->

From the SUT's and JVM2's perspective, everything proceeds as normal.  The
virtual machine notifies DEEPSEA of two kinds of events:

- `MethodEntryEvent` is generated at every method invocation. If the method is
  a user-specified "interesting" method, symbolic mode is switched on and the
  system starts keeping track of the symbolic state of the target program.
  Additionally, for directed runs, DEEPSEA modifies the method's concrete
  argument values on the JVM stack.

- `StepEvent}` is generated just before every instruction is executed.  In
  non-symbolic mode, these events are ignored, but in symbolic mode certain
  instructions are mirrored in the symbolic state.

For improved performance, event notification is limited to only those Java
packages that the user is interested in.

~~~
FIGURE
~~~

Figure~\ref{fig:deepsea} illustrates this approach.  DEEPSEA launches an
initial, unconstrained run of the target program (1).  Symbolic tracking is
triggered by a \texttt{MethodEntryEvent} and subsequent \texttt{StepEvent}s
cause updates to the symbolic state (2).  The symbolic state records the
symbolic value of all variables (including the execution stack contents and,
potentially, the heap), and this information is used to build the path
condition.

Once the run completes, the path condition is modified and a constraint
solver---in this case the Green library~\cite{green}---computes a model for the
constraint.  A further run is launched (3) and at the point where the symbolic
mode is switched on, the model values are injected into the JVM (4).  Execution
continues as before, tracking the symbolic state and computing a new path
condition (5).

%(Note that the illustration is inaccurate: the JVM will follow exactly the
%same path during the second run as in the first, up to the point where new
%values are injected by the handler for the \texttt{MethodEntryEvent}.)

DEEPSEA would be more efficient if the entire state of the JVM is recorded at
the point where the \texttt{MethodEntryEvent} switches on the symbolic mode,
and restored from that point for subsequent runs.  Unfortunately, this
functionality is not available in the ``official'' JVM.  On the other hand, the
current design makes use of a standard mechanism and is conceptually relatively
simple compared to other approaches.

