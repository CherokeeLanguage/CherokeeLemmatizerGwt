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
./gradlew clean build -xtest
#copy to TEMP file to keep tomcat9 from trying to unpack it before the transfer finishes
scp build/libs/"${WAR}" clcom@www.cherokeelessons.com:/var/lib/tomcat9/webapps/"${WAR}".tmp
#now let tomcat9 have the file
ssh clcom@www.cherokeelessons.com "cd /var/lib/tomcat9/webapps/ && mv -v \"${WAR}\".tmp \"${WAR}\""

echo "DONE."
sleep 1
exit 0
