#!/bin/bash

DIRECTORY=`dirname $0`
DIRECTORY=$(realpath $DIRECTORY)

docker run --name jdbc-mysql \
 -e MYSQL_ROOT_PASSWORD=$USER \
 -e MYSQL_USER=$USER \
 -e MYSQL_PASSWORD=$USER \
 -e MYSQL_DATABASE=$USER \
 -p 3306:3306 \
 --mount type=tmpfs,destination=/var/lib/mysql \
 -d mysql:8.3.0 \
 --log-bin-trust-function-creators=1
