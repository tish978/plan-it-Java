# Use a base image with Java runtime
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file to the container
COPY build/libs/plan-it-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]