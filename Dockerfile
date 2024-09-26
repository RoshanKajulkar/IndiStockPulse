# Use an OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set the JAVA_HOME environment variable (optional, as it's typically set by the image)
ENV JAVA_HOME=/usr/local/openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Set the execute permission for the Maven wrapper
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package

# Copy the JAR file from the target directory to the working directory
COPY target/*.jar app.jar

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
