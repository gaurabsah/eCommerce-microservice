# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory
#WORKDIR /apps

# Copy built jar into image
COPY target/cart-service-0.0.1-SNAPSHOT.jar cart-service.jar

# Expose port
EXPOSE 8082

# Run application
ENTRYPOINT ["java", "-jar", "cart-service.jar"]