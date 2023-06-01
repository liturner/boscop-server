#!/bin/sh
VM_OPTIONS="-Djava.util.logging.config.file=conf/logging.properties"
DIR=`dirname $0`
java $VM_OPTIONS -jar $DIR/lib/boscop.jar "$@"
