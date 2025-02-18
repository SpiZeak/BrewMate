# Use Eclipse Temurin JDK 23 as the base image
FROM eclipse-temurin:23-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target folder
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
