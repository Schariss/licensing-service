# Starts with a base image containing java runtime
FROM openjdk:11-slim

# Add maintainer info
LABEL maintainer="Adnane Chahid"

# The application's jar file
# Defines the JAR_FILE variable set by dockerfile-maven-plugin in pom file
ARG JAR_FILE

# Add the application's jar file to the container
# Copies the JAR file to the filesystem of the image named licensing-service.jar
COPY ${JAR_FILE} licensing-service.jar

EXPOSE 8080

# Execute the application
ENTRYPOINT ["java", "-jar", "licensing-service.jar"]


