# COASTAL

This is a program analysis tool for Java that uses Concolic Execution.
This is a combination of symbolic and concrete execution:
As a program is executed concretely, COASTAL collects information to describe the execution trace symbolically.
At the end of the execution, a modified path is passed to the [Green](http://www.green.green) library is used to find new program inputs,
and the process is repeated.

For more information, including installation and usage guides, visit [https://deepseaplatform.github.io/coastal/](https://deepseaplatform.github.io/coastal/).

## Authors

  * [Jaco Geldenhuys](mailto://geld@sun.ac.za)
  * [Willem Visser](mailto://wvisser@sun.ac.za)

### Contributors

  * Someone
  * Someone else
