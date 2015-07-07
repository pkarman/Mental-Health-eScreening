@echo off

set /p password=<pswd.txt


REM This file performs the following:
REM 1) Creates a list of all binary logs in the folder
REM 2) Flushes the current log
REM 3) Restores the dump
REM 4) Restores each previously-existing binary log in the current directory
REM 5) Deletes the file created in step 1

REM Usage:
REM The Restore.bat file can be placed into the main backup dir to 
REM restore the most recent backup, or can be placed in one of the 
REM subdirs created by backup.bat to restore that backup. 

echo Restoring last backup...

"D:\apps\mysql\MySQL Server 5.6\bin\mysqladmin" -u root -p%password% flush-logs

"D:\apps\mysql\MySQL Server 5.6\bin\mysql" -u root -p%password% < databases.txt

echo Done restoring last dump. 

setlocal enabledelayedexpansion enableextensions
set files=
for %%x in (bin.*) do (
 if not %%x==bin.index set files=!files! %%x
)

set /P dummy=The following Binlog files will be restored after you press enter: %files:~1%

"D:\apps\mysql\MySQL Server 5.6\bin\mysqlbinlog" %files:~1% | mysql -u root -p%password%

echo Done 