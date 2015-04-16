@echo off

rem if(%1)==('help') GOTO USAGE

IF (%1)==() (
set work_dir=%cd%
) ELSE (
set work_dir=%1
)

rem cd /d %work_dir%

REM *

dir /B /A:-D %work_dir% > files.txt

rem 使用方式 【list_files D:\codes\mtee\deploy】,【list_files .】会生成一个files.txt到当前文件夹下


rem dir *.* /b >files.txt
rem 如果使用下面的命令，即加一个/b，则会生成一个简单的文件名列表，不包括其它信息，这种方法可能更加实用一些