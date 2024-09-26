# Use an official OpenJDK image.
FROM openjdk:17-jdk-slim

# Set the working directory inside the container.
WORKDIR /app

# Copy the project jar to the container.
COPY target/*.jar app.jar

# Expose the application port.
EXPOSE 8080

# Run the jar file.
ENTRYPOINT ["java","-jar","app.jar"]
