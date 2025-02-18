# 1. Använd en Maven-bild för att bygga applikationen
FROM maven:3.9.9-eclipse-temurin-23-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
CMD [ "tail", "-f", "/dev/null" ]

# 2. Skapa en mindre runtime-container
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
