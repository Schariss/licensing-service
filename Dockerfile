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
# docker run -e JAVA_OPTS=-Dserver.port=9000 evato/licensing-service
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -jar licensing-service.jar"]

# $@ is all of the parameters passed to the script
# $@ is nearly the same as $*, both meaning all command line arguments
# ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

#ENTRYPOINT ["java", "-jar", "licensing-service.jar"]


