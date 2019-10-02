#!/bin/sh
docker run -it -v `pwd`:/coastal coastal-full ${@}
