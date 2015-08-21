#!/bin/bash

# Parameters:
# instance - tomcat instance name can be any defined instance in INSTANCES_DIR
# profile - can be any profile found in the pom
# branch_to_deploy - git branch to deploy

# Notes:
# For each profile, this script depends on having a sub folder which is an eScreening
# git checkout for the given profile.
# In each release folder, local tags are used for each release to track what has been 
# deployed. So for this simple way of tracking changes between deployments to work, we
# have to have a release directory for each profile.
# Troubleshooting:
# If you see some maven error about a missing vistaLink dependency then you have to update your maven settings to point at our 
# shared maven repo.  Add the following: <localRepository>D:\escreening\.m2\repository</localRepository>

if [ $# != 14 ]; then
	printf "\nUsage: $0 tomcat_instance_name mvn_profile_name branch_to_deploy jdbcUsername jdbcPassword vistaIp vistaPort vistaPrimaryStation vistaAccessCode vistaVerifyCode vistaEncrypted quickOrderIen samplePatientIen\n"
	exit 1;
fi

INSTANCES_DIR="d:/apps/tomcatInstances"
instance=$1
profile=$2
branch_to_deploy=$3
jdbcUsername=$4
jdbcPassword=$5
vistaIp=$6
vistaPort=$7
vistaPrimaryStation=$8
vistaAccessCode=$9
vistaVerifyCode=${10}
vistaDuz=${11}
vistaEncrypted=${12}
quickOrderIen=${13}
samplePatientIen=${14}
refTbiServiceName=${15}

webapps_dir="$INSTANCES_DIR/$instance/webapps"

if [ ! -d "$webapps_dir" ]; then
	printf "\nTomcat instance $instance does not exist.\nCheck the name or create an instance.\n"
	exit 1;
fi

if [ ! -d "${profile}-release" ]; then
	printf "\neScreening code for $profile (${profile}-release) is not found in this directory.\n"
	exit 1;
fi

cd "${profile}-release/escreening"

# generate date tag string
stamp=`date +"${profile}_%Y%m%d_%H%M"`

prev_tag=`git describe --abbrev=0 --tags`
printf "\nCurrent tag is: $prev_tag \n\nGetting latest code from branch: $branch_to_deploy...\n\n"

# get release code
#git stash
git fetch
git checkout $branch_to_deploy
git pull
#git stash pop --quiet
printf "\nIf there are no conflicts or errors, hit enter to build the new production package\n"
read

git tag $stamp

printf "\nCreated tag $stamp\n\n"

# ask if they want to rebuild the DB 
printf "\nShould the current database be deleted and rebuilt\n***********************************\n** WARNING ALL DATA WILL BE LOST **\n***********************************\nEnter 'YeS' to rebuild database and lose all data.\n\n"
read rebuildDb

extraParams=""
if [ "$rebuildDb" == "YeS" ]; then
	extraParams="\"-Drecreate_db=true\""

	printf "\nShould test data be inserted into the new database? (Enter 'y' to include)\n"
	read includeTestData

	if [ "$includeTestData" != "y" ]; then
		extraParams="${extraParams} \"-Ddb.skip.testData=true\""
	fi
fi

printf "\nBuilding WAR...\n\n"
mvn --quiet clean package -P$profile -DskipTests \
"-Djdbc.username=$jdbcUsername" \
"-Djdbc.password=$jdbcPassword" \
"-Dvista.ip=$vistaIp" \
"-Dvista.port=$vistaPort" \
"-Dvista.primaryStation=$vistaPrimaryStation" \
"-Dvista.accessCode=$vistaAccessCode" \
"-Dvista.verifyCode=$vistaVerifyCode" \
"-Dvista.duz=$vistaDuz" \
"-Dvista.encrypt=$vistaEncrypted" \
"-Dquick.order.ien=$quickOrderIen" \
"-Dref.tbi.service.name=$refTbiServiceName" \
"-Dsample.patient.ien=$samplePatientIen" $extraParams


printf "\n\nIf there are no errors, please stop service tomcat-$instance then hit enter to continue\n\n"
read 

#get the name of the war without extention
war_name=`basename $(ls -d target/*.war)|cut -f1 -d'.'`

if [ ! -f "target/$war_name.war" ]; then
	printf "\nError: The war was not created. \n`pwd`/target/$war_name.war does not exist.\n"
	exit 1;
fi
printf "\n\nMaking a backup copy of the built $war_name.war...\n"
mkdir -p ../../release_backups/$stamp
cp target/$war_name.war ../../release_backups/$stamp/

printf "\nDeploying WAR...\n"
rm -rf $webapps_dir/$war_name
rm -rf $webapps_dir/$war_name.war
cp target/$war_name.war $webapps_dir

# listing sql changes
if [ "$prev_tag" != "" ]; then
	printf "\nBuilding list of SQL files which have been added or changed...\n\n"
	git diff --name-status "$prev_tag" |grep sql
	printf "\nThe above SQL files where changed\n"
fi

printf "\nDeployment complete!\nPlease apply any sql scripts and restart the tomcat-$instance service.\n"