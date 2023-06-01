#!/bin/sh
VM_OPTIONS=
DIR=`dirname $0`
java $VM_OPTIONS -jar $DIR/lib/boscop.jar "$@"
