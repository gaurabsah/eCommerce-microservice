# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

# Expose port
EXPOSE 8086

# Run application
ENTRYPOINT ["java", "-jar", "notification-service.jar"]