@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\\tools\\apache-maven-3.9.5
set SKIP_TESTS=-DskipTests

set PROJECT=kp-spark-project
set DOCKER_IMAGE_SPARK=bitnami/spark:latest
set DOCKER_IMAGE_SERVICE=eeengcs/service01:1.0.0-SNAPSHOT
set CONTAINER_NAME_SERVICE=service01
set DOCKER_FILE_SERVICE=docker-config\Dockerfile
set COMPOSE_FILE=docker-config\compose.yaml
set NET_NAME=app-net

pushd %cd%
cd ..
call %M2_HOME%\bin\mvn -f spark-application\pom.xml --quiet %SKIP_TESTS% clean package

:: set KEY=N
:: set /P KEY="Recreate network? Y [N]"
:: if /i "%KEY:~0,1%"=="Y" (
::    docker network rm --force %NET_NAME%
::    docker network create %NET_NAME%
:: )
echo ------------------------------------------------------------------------------------------
docker container rm --force %CONTAINER_NAME_SERVICE% > nul 2>&1
docker image rm --force %DOCKER_IMAGE_SERVICE% > nul 2>&1
docker build --file %DOCKER_FILE_SERVICE% --tag %DOCKER_IMAGE_SERVICE% .
docker push %DOCKER_IMAGE_SERVICE%
echo ------------------------------------------------------------------------------------------
docker compose down
docker compose -f %COMPOSE_FILE% -p %PROJECT% up --detach
echo ------------------------------------------------------------------------------------------
docker compose -f %COMPOSE_FILE% -p %PROJECT% ps
echo ------------------------------------------------------------------------------------------
docker compose -f %COMPOSE_FILE% -p %PROJECT% images
popd
pause