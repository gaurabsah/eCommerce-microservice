# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

# Expose port
EXPOSE 8888

# Run application
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]