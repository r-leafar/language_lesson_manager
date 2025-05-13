FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
COPY application.properties /build/src/main/resources/
WORKDIR /build/
RUN mvn clean package
FROM openjdk:17-jdk
ARG JAR_FILE=/build/target/*.jar
WORKDIR app
COPY --from=MAVEN_BUILD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080