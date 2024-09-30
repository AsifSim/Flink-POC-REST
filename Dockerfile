# Build the functions code ...
FROM maven:3.6.3-jdk-8 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# ... and run the web server!
FROM openjdk:8
WORKDIR /
COPY --from=builder /usr/src/app/target/flink_rest_integration-1.0*jar-with-dependencies.jar flink_rest_integration-1.0.jar
EXPOSE 1108

# # Set up the environment for Stateful Functions
# ENV ROLE=master
# ENV MASTER_HOST=localhost
#
# # Expose required ports for Flink Stateful Functions
# EXPOSE 8081 6123 6122 6124

CMD java -jar flink_rest_integration-1.0.jar
