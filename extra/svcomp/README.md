# COASTAL - SV-COMP

This directory contains additional files for COASTAL for the [SV-COMP 2020 competition](https://sv-comp.sosy-lab.org/2020/).

## Packaging COASTAL
 
``coastal-package`` is a shell script to create ``coastal.zip``.  It is submitted by forking [https://gitlab.com/sosy-lab/sv-comp/archives-2020](https://gitlab.com/sosy-lab/sv-comp/archives-2020), cloning, adding the file in the right place (``2020/coastal.zip``), committing, and issuing a merge request.

## Other files

  - ``coastal-sv-comp`` is a shell script that compiles the source files, generates the correct properties file, runs COASTAL, and translates the output to the required format.  It is included by the zipping shell script and does not need to submitted separately.


  - ``coastal.xml`` is a high-description of the tool entry in the competition.  It is submitted by forking [https://github.com/sosy-lab/sv-comp](https://github.com/sosy-lab/sv-comp), cloning, adding the file in the right place (``benchmark-defs/coastal.xml``), committing, and issuing a merge request.


  - ``coastal.py`` is a Python-script that describes how to run our tool from within the framework of the competition.  It is submitted by forking [https://github.com/sosy-lab/benchexec](https://github.com/sosy-lab/benchexec), cloning, adding the file in the right place (``tools/coastal.py``), committing, and issuing a merge request.
