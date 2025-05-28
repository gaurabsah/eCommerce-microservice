🛒 E-Commerce Microservices Application
📦 Modules
user-service: Register/login users with JWT
product-service: Manage product catalog
cart-service: Add/remove items in cart
order-service: Place orders and produce Kafka events
payment-service: Simulated payment gateway
notification-service: Kafka consumer for order notifications
api-gateway: Routes and secures APIs
discovery-service: Eureka service registry


🧪 How to Run
docker-compose up --build


🔐 Authentication
Register: POST /auth/register
Login: POST /auth/login → returns JWT
Use JWT in Authorization: Bearer <token> header


📡 Kafka Topics
notificationTopic: triggered after order placement


Hit endpoints through API Gateway (localhost:8888):
POST /auth/register
POST /auth/login → Get JWT
Use JWT in Authorization header
POST /cart/add with product
POST /order/place → triggers Kafka notification
Check logs from notification-service
