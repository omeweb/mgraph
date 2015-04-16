@cd ..
call mvn -Dmaven.test.skip=true -Dmaven.compile.fork=true package -U
rem cd deploy
rem call mvn assembly:assembly
rem @pause