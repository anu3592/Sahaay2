# Use an official JDK base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy your jar file into the container
COPY target/ticket-1.0-SNAPSHOT.jar app.jar

# Expose the port (change 9494 if needed)
EXPOSE 9494

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

