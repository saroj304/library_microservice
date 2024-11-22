FROM openjdk:17-jdk-oracle
COPY target/library-microservice-0.0.1-SNAPSHOT.jar /library-microservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-microservice.jar"]
