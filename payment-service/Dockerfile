# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar

# Expose port
EXPOSE 8085

# Run application
ENTRYPOINT ["java", "-jar", "payment-service.jar"]