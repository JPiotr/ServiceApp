FROM openjdk:19-jdk-alpine
LABEL maintainer="oblitus"
EXPOSE 8080
ARG JAR_FILE=target/service-app-1.2.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]