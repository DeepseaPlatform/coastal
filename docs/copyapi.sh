#!/bin/bash

exit

#-------------------------------------------------------------------------------
# Variables
#-------------------------------------------------------------------------------

ARTIFACTS="https://git.cs.sun.ac.za/coastal/coastal/-/jobs/artifacts/master/download?job=build"
ZIP="artifacts.zip"

#ARTIFACT="https://git.cs.sun.ac.za/coastal/coastal/-/jobs/artifacts/master/download?job=build"
TEMPDIR=`pwd`"/_temp"
DEST=`pwd`"/_pages/api"

#-------------------------------------------------------------------------------
# Fetch the artifact from the coastal / deepsea project.
#-------------------------------------------------------------------------------

mkdir -p ${TEMPDIR} 2>/dev/null
CURDIR=`pwd`
cd ${TEMPDIR}
if [ -r ${ZIP} ] ; then
	curl --location --output ${ZIP} --time-cond ${ZIP} "${ARTIFACTS}"
else
	curl --location --output ${ZIP} "${ARTIFACTS}"
fi
# curl -Ls ${ARTIFACT} > artifacts.zip
#wget -q -O artifacts.zip http://git.cs.sun.ac.za/coastal/coastal/-/jobs/artifacts/master/download?job=build
# UGLY HACK!!!
# cp /Users/Jaco/Documents/DOCUMENTS/DOWNLOADS/MISC/artifacts.zip .
unzip -o ${ZIP} &>/dev/null

#-------------------------------------------------------------------------------
# Transform HTML files
#-------------------------------------------------------------------------------

cd ${TEMPDIR}/build/docs/javadoc

for INFILE in `find . -name '[A-Z]*.html'` ; do
	INDIR=`dirname ${INFILE} | cut -c2-`
	OUTBASE=${INDIR}/`basename ${INFILE} ".html"`
	OUTLINK="/api"${OUTBASE}".html"
	OUTFILE=${DEST}${OUTBASE}".md"
	echo "Processing ${INFILE}"
#	echo "INFILE: ${INFILE}"
#	echo "INDIR: ${INDIR}"
#	echo "OUTBASE: ${OUTBASE}"
#	echo "OUTLINK: ${OUTLINK}"
#	echo "OUTFILE: ${OUTFILE}"
	mkdir -p ${DEST}"/"${INDIR}
	echo "---" > ${OUTFILE}
	echo "layout: api" >> ${OUTFILE}
	echo "permalink: ${OUTLINK}" >> ${OUTFILE}
	echo "---" >> ${OUTFILE}
	sed \
		-e '1,/<!-- ======== START OF CLASS DATA ======== -->/d' \
		-e '/<!-- ========= END OF CLASS DATA ========= -->/,$d' \
		-e 's,<div class="subTitle">\([^<]*\)</div>,<div class="subTitle"><a href="/api'${INDIR}'/package-summary.html">\1</a></div>,' \
		< ${INFILE} >> ${OUTFILE}
done

for INFILE in `find . -name 'package-summary.html'` ; do
	INDIR=`dirname ${INFILE} | cut -c2-`
	OUTBASE=${INDIR}/`basename ${INFILE} ".html"`
	OUTLINK="/api"${OUTBASE}".html"
	OUTFILE=${DEST}${OUTBASE}".md"
	echo "Processing ${INFILE}"
	mkdir -p ${DEST}"/"${INDIR}
	echo "---" > ${OUTFILE}
	echo "layout: apii" >> ${OUTFILE}
	echo "permalink: ${OUTLINK}" >> ${OUTFILE}
	echo "---" >> ${OUTFILE}
	sed -e '1,/<\/noscript>/d' -e '/<\/body>/,$d' < ${INFILE} >> ${OUTFILE}
done

INFILE="overview-summary.html"
OUTFILE=${DEST}"/index.md"
OUTLINK="/api/"
echo "Processing ${INFILE}"
echo "---" > ${OUTFILE}
echo "layout: apio" >> ${OUTFILE}
echo "permalink: ${OUTLINK}" >> ${OUTFILE}
echo "---" >> ${OUTFILE}
sed -e '1,/<\/noscript>/d' -e '/<\/body>/,$d' < ${INFILE} >> ${OUTFILE}

#-------------------------------------------------------------------------------
# Return to starting directory and delete temporary file
#-------------------------------------------------------------------------------

cd ${CURDIR}
rm -rf ${TEMPDIR} 2>/dev/null

