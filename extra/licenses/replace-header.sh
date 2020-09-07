#!/bin/sh
#===============================================================================
# Update the copyright information in the headers of Java source files.
#===============================================================================

SRC=../../src/main/
NEW=license-01.txt

for FILE in $( find ${SRC} -name '*.java' \
	| grep -v sosy_lab \
	| xargs grep -l 'This file is part of the COASTAL tool' ) ; do
	sed -i '' -e '1,/\*\// {
		1r '${NEW}'
		d
	}' ${FILE}
done

for FILE in $( find ${SRC} -name '*.java' \
	| grep -v sosy_lab \
	| xargs grep -L 'This file is part of the COASTAL tool' ) ; do
	sed -i '' -e '1 {r '${NEW}'
		h;d;};2{H;g;}' ${FILE}
done

