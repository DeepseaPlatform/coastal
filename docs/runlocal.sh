#!/bin/sh
#===============================================================================
# Run a local server that provides the Jekyll-based generated documentation.
#===============================================================================

rm -rf /tmp/site \
&& bundle exec jekyll serve --destination /tmp/site

