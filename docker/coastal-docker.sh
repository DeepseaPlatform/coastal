#!/bin/sh
docker run -it -v `pwd`:/coastal jacogeld/coastal-full ${@}
