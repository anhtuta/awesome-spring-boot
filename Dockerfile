# syntax=docker/dockerfile:1

# Ref: https://docs.docker.com/language/java/build-images/

FROM eclipse-temurin:8-jdk-jammy AS base
WORKDIR /app

# Copy the Maven wrapper and our pom.xml file into our image
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies into the image
RUN ./mvnw dependency:resolve

# Add our source code into the image
COPY src ./src

# Tell Docker what command we want to run when our image is executed inside a container
# Add debugger at port 9011 by adding following params to CMD. I tried but it didn't work:
# "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9011'"
FROM base AS development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=docker,swagger"]

FROM base AS build
RUN ./mvnw package

FROM eclipse-temurin:8-jdk-jammy AS production
EXPOSE 9010
COPY --from=build /app/target/awesome-spring-boot-*.jar /awesome-spring-boot.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/awesome-spring-boot.jar"]