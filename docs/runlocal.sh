#!/bin/sh

rm -rf /tmp/site \
&& bundle exec jekyll serve --destination /tmp/site

