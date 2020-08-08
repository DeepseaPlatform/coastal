#!/bin/sh
#===============================================================================
# Update the copyright information in the headers of Java source files.
#===============================================================================

SRC=../src
NEW=extra/license-01.txt

find ${SRC} -name '*.java' \
	| grep -v sosy_lab \
	| xargs grep -l 'This file is part of the COASTAL tool' \
	| xargs sed -i'0' -e '1,/\*\// {
		1r '${NEW}'
		d
	}'

find ${SRC} -name '*.java' \
	| grep -v sosy_lab \
	| xargs grep -L 'This file is part of the COASTAL tool' \
	| xargs sed -i'0' -e '1 {r '${NEW}'
		h;d;};2{H;g;}'

