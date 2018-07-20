---
layout: page
title: Design decisions
permalink: /devguide/design/
---

In the longer term, this page will describe the whole design philosophy
behind COASTAL.  In the short term, it is used as a kind of notepad to
document lower-level decisions

## Logging information

At the moment, logging is complicated by the need for various configuration
settings.  They are needed to control what is displayed and omitted.  While
the volume of information produced by switching everything on is substantial,
even simple tools like `grep` can help.  Logging is not expensive, and logging
***everything*** simplifies the code.

Alternatives such as logging levels and logging markers make the code and the
logging configuration even more complicated.  Therefore, all configuration
settings will be removed, and logging is done according to the following
principles:

1. The following logging levels are used:
    * `INFO` for progress messages
    * `TRACE` for detailed messages logged to a file
    * `WARN` for non-fatal warnings/errors
    * `FATAL` for fatal errors  

2. Progress messages are displayed on the standard output.

3. All messages are written to a log file.

4. The log file does not show the message; just the module that produced the
message.  This should suffice to indicate the nature of the message.
