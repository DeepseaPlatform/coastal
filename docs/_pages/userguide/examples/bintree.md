---
title: "Example: Binary tree"
permalink: /userguide/examples/bintree/
---

Binary trees with two, three, and four operations.

- ``BinTree`` illustrates how to create symbolic variables within the system-under-test.
- ``BinTree3`` illustrates the use of the GUI.
- ``BinTree4`` illustrates the use of different strategies.

## Files
~~~
examples/java/bintree/BinTree.java
examples/java/bintree/BinTree2.java
examples/java/bintree/BinTree3.java
examples/java/bintree/BinTree4.java
examples/resources/bintree/BinTree.properties
examples/resources/bintree/BinTree2.properties
examples/resources/bintree/BinTree3.properties
examples/resources/bintree/BinTree4-DepthFirst.properties
examples/resources/bintree/BinTree4-Generational.properties
examples/resources/bintree/BinTree4-RandomTesting.properties
~~~

## Source

Java PathFinder

## Results

~~~
$ coastal bintree/BinTree.properties -brief
COASTAL version 0.0.0
Paths: 48
Time: 1457

$ coastal bintree/BinTree2.properties -brief
COASTAL version 0.0.0
Paths: 8
Time: 201

$ coastal bintree/BinTree3.properties -brief
COASTAL version 0.0.0
Paths: 48
Time: 1266

$ coastal bintree/BinTree4-DepthFirst.properties -brief
COASTAL version 0.0.0
Paths: 384
Time: 8736
~~~
