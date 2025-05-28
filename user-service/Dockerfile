# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar

# Expose port
EXPOSE 8083

# Run application
ENTRYPOINT ["java", "-jar", "user-service.jar"]