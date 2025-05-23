package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", url = "http://localhost:8082")
public interface CartServiceClient {
	@DeleteMapping("/cart/clear/{userId}")
	String clearCart(@PathVariable("userId") String userId);
}