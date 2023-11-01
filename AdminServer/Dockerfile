FROM openjdk:19-jdk-alpine
LABEL maintainer="oblitus"
EXPOSE 8081
ARG JAR_FILE=target/admin-server-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]