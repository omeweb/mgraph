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

rem ʹ�÷�ʽ ��list_files D:\codes\mtee\deploy��,��list_files .��������һ��files.txt����ǰ�ļ�����


rem dir *.* /b >files.txt
rem ���ʹ��������������һ��/b���������һ���򵥵��ļ����б�������������Ϣ�����ַ������ܸ���ʵ��һЩ