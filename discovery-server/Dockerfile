# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/discovery-server-0.0.1-SNAPSHOT.jar discovery-server.jar

# Expose port
EXPOSE 8761

# Run application
ENTRYPOINT ["java", "-jar", "discovery-server.jar"]