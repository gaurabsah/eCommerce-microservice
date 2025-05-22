package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartServiceClient {
	@DeleteMapping("/cart/clear/{userId}")
	void clearCart(@PathVariable("userId") Long userId);
}