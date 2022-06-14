FROM openjdk:11-slim as build
LABEL maintainer="Adnane Chahid"
WORKDIR /application

# Defines the JAR_FILE variable set by dockerfile-maven-plugin (spotify plugin)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-slim
WORKDIR /application
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]

# Spring Boot introduced a new JAR layout called layered JARs.
# In this format, the /lib and /classes folders are split up and categorized into layers.
# This layering was created to separate code based on how likely it is to change between the builds,
# leaving the necessary information for the build

# java -Djarmode=layertools -jar target/licensing-service-0.0.2-SNAPSHOT.jar list
  #dependencies
  #spring-boot-loader
  #snapshot-dependencies
  #application

# The storage location of Docker images and containers
# mac: ~/Library/Containers/com.docker.docker/Data/vms/0/