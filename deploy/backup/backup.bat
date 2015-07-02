@echo off

REM This script performs the following in order:
REM 1) Creates a list of all binary logs in the folder
REM 2) Creates a subfolder using current date and time
REM 3) Moves pre-existing mysqldump file to new subfolder
REM 4) Flushes current binary log and creates new MySQL dump
REM 5) Moves each previously-existing (now closed) binary log to new folder
REM 6) Deletes the list created at the top 

REM Setup: for this to work the following setting should be set in my.ini:
REM log-bin="D:\backup\bin" 

set /p password=<pswd.txt

dir bin.* /B > files.txt
for /F "tokens=2-4 delims=/- " %%A in ('date/T') do set dt=%%C%%B%%A
for /F "tokens=1-2 delims=: " %%A in ('time/T') do set tm=%%A%%B
md "%dt%%tm%"
move databases.txt %dt%%tm%

"D:\apps\mysql\MySQL Server 5.6\bin\mysqldump" -h localhost -u root -p%password% --all-databases --flush-logs --result-file=D:\backup\databases.txt

FOR /f %%a in (files.txt) do call:MY_SUB %%a
GOTO SUB_DONE

:MY_SUB
if not %1==bin.index move %1 %dt%%tm%
GOTO :EOF

:SUB_DONE
del files.txt
echo Done 
