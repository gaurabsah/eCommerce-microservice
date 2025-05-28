# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose port
EXPOSE 8084

# Run application
ENTRYPOINT ["java", "-jar", "order-service.jar"]