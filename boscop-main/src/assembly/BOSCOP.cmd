@echo off
set VM_OPTIONS="-Djava.util.logging.config.file=conf/logging.properties"
java %VM_OPTIONS% -jar "%~dp0lib\boscop.jar" %*