#!/bin/sh
docker run --rm -it -v `pwd`:/coastal jacogeld/coastal-full ${@}
