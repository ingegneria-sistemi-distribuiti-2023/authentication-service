# AS <NAME> to name this stage as maven
FROM maven:3.8.3-openjdk-17 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copy the executable JAR from the maven stage to the current stage
COPY --from=maven /usr/src/app/target/authentication-service-0.0.1-SNAPSHOT.jar /app
ENV SERVER_PORT 8081
EXPOSE $SERVER_PORT
ENTRYPOINT ["java", "-jar", "authentication-service-0.0.1-SNAPSHOT.jar" ]