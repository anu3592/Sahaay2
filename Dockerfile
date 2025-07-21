# 1. Use Maven image to build
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Use smaller JDK image to run
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built jar from above stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on (e.g., 8080 or 9494)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
