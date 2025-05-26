package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
//	@Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/users/**")
//                        .uri("lb://user-service"))
//                .route("product-service", r -> r.path("/products/**")
//                        .uri("lb://product-service"))
//                .route("cart-service", r -> r.path("/cart/**")
//                        .uri("lb://cart-service"))
//                .route("order-service", r -> r.path("/orders/**")
//                        .uri("lb://order-service"))
//                .route("payment-service", r -> r.path("/payments/**")
//                        .uri("lb://payment-service"))
//                .build();
//    }

}
