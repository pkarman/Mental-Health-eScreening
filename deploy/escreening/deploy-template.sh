#!/bin/bash

# git branch to use
branch="master"

# name of tomcat instance
tomcatInstance=""
# maven profile
profile=""

# database application logon
jdbcUsername=""
jdbcPassword=""

# vista specific 
vistaIp=""
 
vistaPort="" 
vistaPrimaryStation="" 
quickOrderIen=""

# Access and verify for Proxy account
# NOTE: When entering access and verify codes directly in the config file (not using the configuration editor), if the codes contain the following special characters, they need to be entered as follows:
#
#		special char    enter as
#		    <           &lt;
#		    &           &amp;
#		    "           &quot;
#		    '           &apos;
vistaAccessCode=""

vistaVerifyCode="" 

deploy.sh $tomcatInstance $profile $branch $jdbcUsername $jdbcPassword $vistaIp $vistaPort $vistaPrimaryStation $vistaAccessCode $vistaVerifyCode $quickOrderIen


