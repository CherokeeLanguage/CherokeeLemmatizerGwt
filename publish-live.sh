#!/bin/bash

set -e
set -o pipefail
trap 'echo "error!"; read a' ERR

cd "$(dirname "$0")"
cwd="$(pwd)"

BIN="CherokeeDictionary"
WAR="${BIN}.war"

cd ~/git
#bash git-pull-all.sh

cd "${cwd}"
gradle clean build -xtest
#copy to TEMP file to keep tomcat from trying to unpack it before the transfer finishes
scp build/libs/"${WAR}" muksihs@www.cherokeedictionary.net:/opt/tomcat/webapps/"${WAR}".tmp
#now let tomcat have the file
ssh muksihs@www.cherokeedictionary.net "cd /opt/tomcat/webapps/ && mv -v \"${WAR}\".tmp \"${WAR}\""

echo "DONE."
sleep 1
exit 0
