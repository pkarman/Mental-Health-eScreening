#!/bin/sh

set -e
set -x

export ROOTDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

git clone https://github.com/pkarman/vistalink-tester-for-linux.git

cd vistalink-tester-for-linux/samples-J2SE

mvn install:install-file -DgroupId=gov.va.med.vistalink -DartifactId=vljConnector -Dpackaging=jar -Dversion=1.6.0.028 -Dfile=vljConnector-1.6.0.028.jar

mvn install:install-file -DgroupId=gov.va.med.vistalink -DartifactId=vljFoundationsLib -Dpackaging=jar -Dversion=1.6.0.028 -Dfile=vljFoundationsLib-1.6.0.028.jar

mvn install:install-file -DgroupId=gov.va.med.vistalink -DartifactId=vljSecurity -Dpackaging=jar -Dversion=1.6.0.028 -Dfile=vljSecurity-1.6.0.028.jar

cd $ROOTDIR

mysql < escreening/src/main/sql/initialization/dev_env_run_once.sql

cd $ROOTDIR/escreening

mvn integration-test -DskipTests=true -Drecreate_db=true -P dev


