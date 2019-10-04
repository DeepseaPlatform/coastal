---
title: Commit checklist
permalink: /devguide/commit-checklist/
---

Before each commit, please follow the steps below.

1. **Make sure that the commit covers only a single topic.**<br/>
Remember that the git philosophy is to commit early and often.  The changes within one commit should be related:  it is better to split a change into many small commits than to combine many changes within a single commit.  Prefer small increments.  For a large modification, plan ahead and branch-and-merge.

2. **Take special care if the commit includes non-source files.**<br/>
Double-check your work if, for example, you are deleting existing files or adding new files.  Triple-check you work if, for example, you are making changes to ```.gitignore``` or modifying ```build.gradle```.

3. **Update ```CHANGELOG.md``` if the commit makes a noteworthy change.**

4. **Update the documentation if necessary.**

5. **Make sure that you have added units tests for checking your commit.**<br/>
If you do not know how or cannot do so for another reason, add a new issue to record that test cases are required.  Not all changes require tests.  For example, fixing a typo does not.

6. **Run the existing test suite.**<br/>
If there are failing tests, check your work and correct the commit or the tests.  If this is not possible -- if you do not know how to fix your commit or if you believe the failures are incorrect but you do not know how to change it -- add a new issue to report the problem.

7. **Just before you commit, ```git pull``` to make sure that you are working from the latest version.**
