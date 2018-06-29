#!/bin/sh

./copyapi.sh \
&& rm -rf /tmp/site \
&& bundle exec jekyll serve --destination /tmp/site

