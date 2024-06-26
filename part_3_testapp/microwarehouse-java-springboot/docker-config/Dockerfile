FROM maven AS MAVEN_TOOL
COPY service/pom.xml /tmp/
COPY service/src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
COPY --from=MAVEN_TOOL /tmp/target/service01-1.0.0-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","application.jar"]