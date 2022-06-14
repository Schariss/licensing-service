FROM openjdk:11-slim as build
LABEL maintainer="Adnane Chahid"
WORKDIR /workspace/licensing-service

# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# COPY src src

# RUN ./mvnw install -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Defines the JAR_FILE variable set by dockerfile-maven-plugin (spotify plugin)
ARG JAR_FILE
COPY ${JAR_FILE} target/${JAR_FILE}
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../${JAR_FILE})

FROM openjdk:11-slim
VOLUME /tmp
ARG DEPENDENCY=/workspace/licensing-service/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /licensing-service/lib
COPY --from=build ${DEPENDENCY}/META-INF /licensing-service/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /licensing-service
ENTRYPOINT ["java","-cp","licensing-service:licensing-service/lib/*", "com.optimagrowth.license.LicensingServiceApplication"]

# Build our Docker image using the spotify plugin
# mvn package dockerfile:build

# The storage location of Docker images and containers
# mac: ~/Library/Containers/com.docker.docker/Data/vms/0/