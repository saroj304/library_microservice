# FROM openjdk:17-jdk-oracle
# COPY target/library-microservice-0.0.1-SNAPSHOT.jar /library-microservice.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "library-microservice.jar"]


# Use official OpenJDK 17 image
FROM openjdk:17-jdk

# Set working directory (optional)
WORKDIR /app

# Copy the WAR file built by Maven
COPY target/library-microservice-0.0.1-SNAPSHOT.war /library-microservice.war

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot WAR file
ENTRYPOINT ["java", "-jar", "/library-microservice.war"]
