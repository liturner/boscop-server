#!/bin/sh
VM_OPTIONS="-Djava.util.logging.config.file=conf/logging.properties"
DIR=`dirname $0`

# The exec is important and makes the process run as a single parent 
# thread, not a parent and child. (#11)
exec java $VM_OPTIONS -jar $DIR/lib/boscop.jar "$@"
