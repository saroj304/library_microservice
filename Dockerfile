# FROM openjdk:17-jdk-oracle
# COPY target/library-microservice-0.0.1-SNAPSHOT.jar /library-microservice.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "library-microservice.jar"]


FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/library-microservice-0.0.1-SNAPSHOT.war /library-microservice.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/library-microservice.war"]
