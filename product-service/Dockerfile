# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

# Expose port
EXPOSE 8081

# Run application
ENTRYPOINT ["java", "-jar", "product-service.jar"]