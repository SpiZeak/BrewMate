FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:resolve
COPY src src
RUN ./mvnw package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
