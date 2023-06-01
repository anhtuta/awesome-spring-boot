# syntax=docker/dockerfile:1

# Ref: https://docs.docker.com/language/java/build-images/

FROM eclipse-temurin:8-jdk-jammy
WORKDIR /app

# Copy the Maven wrapper and our pom.xml file into our image
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies into the image
RUN ./mvnw dependency:resolve

# Add our source code into the image
COPY src ./src

# Tell Docker what command we want to run when our image is executed inside a container
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=docker,swagger"]