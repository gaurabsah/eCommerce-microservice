
# 🛒 E-Commerce Microservices Application

A production-grade microservices-based E-Commerce backend application built with Java 21, Spring Boot 3, Spring Security, JWT, Kafka, and Docker.

## 🧩 Microservices

| Service Name         | Port | Description                                   |
|----------------------|------|-----------------------------------------------|
| `discovery-service`  | 8761 | Eureka server for service registration        |
| `api-gateway`        | 8888 | Entry point for all microservices             |
| `user-service`       | 8083 | Handles user registration & login             |
| `product-service`    | 8081 | Exposes product APIs                          |
| `cart-service`       | 8082 | Manages user carts                            |
| `order-service`      | 8084 | Places orders and sends Kafka events          |
| `payment-service`    | 8085 | Simulates payment processing                  |
| `notification-service`| 8086 | Listens to Kafka topic and logs notification  |

## ⚙️ Tech Stack

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Spring Cloud (Eureka, Gateway)
- Apache Kafka
- Docker + Docker Compose
- OpenTelemetry + Zipkin (for tracing)
- Maven

---

## 🧪 Testing the Application

### ✅ Pre-requisites

- Java 21
- Docker & Docker Compose
- Maven

### 🚀 Running with Docker Compose

```bash
docker-compose up --build
This starts all services, Kafka, Eureka, Zipkin, and the API gateway.

🔐 Authentication Flow
Register/Login at /user/register or /user/login
Receive JWT Token
Access secured endpoints (e.g., /products, /cart) by passing JWT in the Authorization header.
🔁 Kafka Communication
order-service publishes an OrderPlacedEvent to Kafka.
notification-service listens to notificationTopic and logs the order ID.
🔗 Endpoints Overview

user-service
Endpoint	Method	Description
/api/user/register	POST	Register a user
/api/user/login	POST	Login and get token
product-service
Endpoint	Method	Description
/api/products	GET	Fetch all products
cart-service
Endpoint	Method	Description
/api/cart/add	POST	Add product to cart
/api/cart/view	GET	View cart contents
order-service
Endpoint	Method	Description
/api/order/place	POST	Place order, send Kafka

📦 Kafka Topics
Topic Name	Description
notificationTopic	Event topic for notifications

📈 Distributed Tracing
Traces are sent to Zipkin (http://localhost:9411)
All services are configured with OpenTelemetry

🔐 Security
JWT-based auth
Roles-based access (e.g., USER, ADMIN)
Token verification via API Gateway
🛠️ Build & Run Individually

cd user-service
mvn spring-boot:run
For Docker:

docker build -t user-service .
docker run -p 8083:8083 user-service
