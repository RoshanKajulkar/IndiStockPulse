# Use an OpenJDK image
FROM openjdk:11-jre-slim

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
