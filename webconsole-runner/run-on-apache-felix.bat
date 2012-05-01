@echo off
SETLOCAL
set _SCRIPTDIR_=.

set PAX_CONSTRUCT_VERSION=1.4
set PAX_PLUGIN=org.ops4j:maven-pax-plugin:%PAX_CONSTRUCT_VERSION%
set FRAMEWORK=felix

rmdir /S /Q runner

@echo on
mvn %PAX_PLUGIN%:provision -Dframework=%FRAMEWORK%