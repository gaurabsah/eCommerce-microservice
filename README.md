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
Register: POST /users/register
Login: POST /users/login → returns JWT
Use JWT in Authorization: Bearer <token>

📡 Kafka Topics
notificationTopic: triggered after order placement

Hit endpoints through API Gateway (localhost:8888):
POST /users/register
POST /users/login → Get JWT
Use JWT in Authorization header
POST /products/create → add product
GET /products/{id} → Get product details
POST /cart/add with product
POST /orders → places an order and triggers Kafka notification
Check logs from notification-service
POST /payments → process payment
Check product stock will be updated if payment is successful and cart will be empty.
