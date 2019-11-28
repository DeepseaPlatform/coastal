# COASTAL

COASTAL is a program analysis tool for Java that uses concolic execution and fuzzing:

  * Concolic execution is a combination of concrete and symbolic execution.
    An instrumented version of the system-under-test is executed concretely,
    and COASTAL collects information to describe the execution trace symbolically.
    At the end of the execution, the symbolic description is modified slightly and
    then passed to a constraint solver to generate new program inputs that are used for the next execution.
    In this way, all possible program executions are explored systematically.

  * Fuzzing also executes the system-under-test repeatedly with random inputs and minimal instrumentation.
    The information collected is integrated with the concolic executions and used to guide the latter.
    At the same time, new random inputs are generated based on the information obtained from concolic
    execution and the success of previous fuzzing runs to find bugs.  

For more information, including installation and usage guides,
visit [https://deepseaplatform.github.io/coastal/](https://deepseaplatform.github.io/coastal/).

## Authors

  * [Jaco Geldenhuys](mailto://geld@sun.ac.za)
  * [Willem Visser](mailto://wvisser@sun.ac.za)
