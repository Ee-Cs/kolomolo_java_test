@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\\tools\\apache-maven-3.9.5
set SKIP_TESTS=-DskipTests

set SPARK_HOME=/opt/bitnami/spark
set CONTAINER_NAME_SPARK=spark
set SRC_APP=spark-application/target/spark-application-1.0.0-SNAPSHOT.jar
set TRG_APP=%CONTAINER_NAME_SPARK%:%SPARK_HOME%/application.jar
set SRC_DATA=spark-application/data/foodhub_order_no_header.csv
set TRG_DATA=%CONTAINER_NAME_SPARK%:%SPARK_HOME%/data/foodhub_order_no_header.csv

pushd %cd%
cd ..
call %M2_HOME%\bin\mvn -f spark-application\pom.xml --quiet %SKIP_TESTS% clean package
docker cp %SRC_APP% %TRG_APP%
docker cp %SRC_DATA% %TRG_DATA%
echo ------------------------------------------------------------------------------------------
docker exec --interactive --tty %CONTAINER_NAME_SPARK% ^
      %SPARK_HOME%/bin/spark-submit --class kp.Application --master local application.jar
popd
pause