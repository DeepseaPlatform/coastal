---
title: The PathTree
permalink: /devguide/pathtree/
---

The ```PathTree``` is the data structure at the heart of COASTAL.  It records all explorations of the system-under-test.  To appreciate its operation, it is important to first understand several related data structures.

## Branches, choices, and paths

Several classes are used to describe different aspects of the branching behaviour of a system-under-test.  These descriptions are intricate, but necessarily so, because COASTAL needs to manage branching carefully.

### Brances

An instance of a ```Branch``` represents a single choice available (but not necessarily taken) somewhere in the program.  There are several ways that such a choice can come about, including

- the execution of an ```if``` statement,
- the execution of an loop statement,
- the execution of a ```switch``` statement, and
- any operation that has more than one behaviour in terms of control flow, such as division which can sometimes result in a integer or real value, and can sometimes cause an exception to be thrown.

A ```Branch``` stores

- the expression which determines the outcome of the branch, and
- the number of potential alternative outcomes.

```Branch``` instances are agnostic about the context in which they occur.  An instance does not record any information about the previous or next branch that the system-under-test might encounter.

```Branch``` instances are also immutable: a choice once constructed will never change during the run of  COASTAL.  If a branching point is encountered more than once during a run of the system-under-test, it could be (but is not necessarily) represented by the same instance of ```Branch```.

### Choices

A run of the system-under-test will encounter (usually) several branching points.  At each point, a single alternative is followed (based on the outcome of the ```if``` condition, ```while``` condition, ```switch``` expression, and so on).  This is represented as an instance of ```Choice```.

### Paths

Choices are collected in instances of ```Path```.  Each instance stores information

- about the last choice made on the path, and
- about the parent (or prefix) path.

More often than not, a path is incomplete, which means that it acts as the parent for another path.

### Example

Consider the following Java program fragment:

~~~java
int que(int a, int b) {
   int x = a + 1;
   int y = b * 2;
   if (x == y) {
      y = y - 3;
   }
   if (y > 5) {
      return x;
   } else {
      return 0;
   }
}
~~~

The following figure shows the whole execution tree with the execution for the invocation ```que(3, 2)``` highlighted, and the associated branches, choices, and path.

## Executions and inputs

### Executions

## The PathTree and PathTreeNode

Paths are collected in an instance of ```PathTree```, and COASTAL maintains only a single instance which is shared among all of its components.

