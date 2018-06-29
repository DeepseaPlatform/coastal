---
layout: page
title: Troubleshooting DEEPSEA
permalink: /userguide/troubleshooting/
---

We try to keep our software as simple as possible, but things can still go
wrong.  This page tries to help you when you encounter 

## Dives do not execute at all

DEEPSEA seems to execute and displays its header and configuration
information.  But just as the first dive is about to begin, an exception is
raised and the system terminates.  You may typically see the following in the
log:

~~~
INFO  
INFO  ~~~ DEEPSEA version v0.0.1 ~~~
INFO  
CONF  ...A lot of configuration information...
CONF
INFO  ----- starting dive DEEPSEA.0 -----
com.sun.jdi.connect.VMStartException: VM initialization failed for: /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/bin/java -Xdebug -Xrunjdwp:transport=dt_socket,address=Nigglewench.local:60901,suspend=y examples.spf.ArrayTestSimple
	at com.sun.tools.jdi.AbstractLauncher$Helper.launchAndAccept(AbstractLauncher.java:193)
	at com.sun.tools.jdi.AbstractLauncher.launch(AbstractLauncher.java:132)
	at com.sun.tools.jdi.SunCommandLineLauncher.launch(SunCommandLineLauncher.java:223)
	at za.ac.sun.cs.deepsea.agent.VMConnectLauncher.launchTarget(VMConnectLauncher.java:29)
	at za.ac.sun.cs.deepsea.diver.Dive.dive(Dive.java:61)
	at za.ac.sun.cs.deepsea.diver.Diver.start(Diver.java:346)
	at za.ac.sun.cs.deepsea.DEEPSEA.main(DEEPSEA.java:37)
Exception in thread "main" java.lang.Error: Target VM failed to initialize: VM initialization failed for: /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/bin/java -Xdebug -Xrunjdwp:transport=dt_socket,address=Nigglewench.local:60901,suspend=y examples.spf.ArrayTestSimple
	at za.ac.sun.cs.deepsea.agent.VMConnectLauncher.launchTarget(VMConnectLauncher.java:38)
	at za.ac.sun.cs.deepsea.diver.Dive.dive(Dive.java:61)
	at za.ac.sun.cs.deepsea.diver.Diver.start(Diver.java:346)
	at za.ac.sun.cs.deepsea.DEEPSEA.main(DEEPSEA.java:37)
~~~

One potential cause of this problem is that your computer's hostname is
misconfigured.  In the case above, Java tries to connect to a VM on the local
machine which it thinks is called "`Xyz.local`", but there is some component of
the operating system that disagrees.  One way to diagnose this problem is to
run (in a terminal window)

~~~
$ ping Xyz.local
~~~

If you receive a response, the problem lies elsewhere.
If you do not receive a response, this is a strong sign that you have a
misconfigured hostname.

Ideally, this problem should be fixed properly, but there is a shortcut that
can at least help you get up and running more quickly.
If you do not have administrator access, you will have to contact someone who
has.
If you do have administrator access, edit the file `/etc/hosts` and add the
line

~~~
127.0.0.1 Xyz.local
~~~

After this, the `ping` command should show a response immediately, and DEEPSEA
should also work correctly.

## Installation for CLI

  - git clone
  - settings

## Installation in Eclipse

  - git clone
  - gradle import

