@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\\tools\\apache-maven-3.9.5
set SKIP_TESTS=-DskipTests

cd ..\service
call %M2_HOME%\bin\mvn -f pom.xml %SKIP_TESTS% clean install
call %M2_HOME%\bin\mvn -f pom.xml --quiet %SKIP_TESTS% spring-boot:run
:: set PROJECT=research
:: start "%PROJECT%" /MAX %M2_HOME%\bin\mvn -f pom.xml --quiet spring-boot:run
pause