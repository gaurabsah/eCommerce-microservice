package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.config.FeignClientConfig;

@FeignClient(name = "cart-service",configuration = FeignClientConfig.class)
public interface CartServiceClient {
	@DeleteMapping("/cart/clear/{userId}")
	String clearCart(@PathVariable("userId") String userId);
}