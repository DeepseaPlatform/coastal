#!/bin/sh
#===============================================================================
# Run coastal inside a docker container.
#===============================================================================

docker run --rm -it -v `pwd`:/coastal jacogeld/coastal-full ${@}

