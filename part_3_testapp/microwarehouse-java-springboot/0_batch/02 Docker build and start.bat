@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\\tools\\apache-maven-3.9.5
set PROJECT=service01
set DOCKER_IMAGE_SERVICE=eeengcs/service01:1.0.0-SNAPSHOT
set DOCKER_FILE_SERVICE=docker-config\Dockerfile-service01
set COMPOSE_FILE=docker-config\compose.yaml

pushd %cd%
cd ..

echo ------------------------------------------------------------------------------------------
docker container rm --force service01 > nul 2>&1
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