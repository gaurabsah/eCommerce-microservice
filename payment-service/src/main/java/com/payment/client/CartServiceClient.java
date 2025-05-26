package com.payment.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.config.FeignClientConfig;
import com.payment.dto.CartItemDTO;

@FeignClient(name = "cart-service",configuration = FeignClientConfig.class)
public interface CartServiceClient {
	@DeleteMapping("/cart/clear/{userId}")
	String clearCart(@PathVariable("userId") String userId);
	
	@GetMapping("/cart/{userId}")
	List<CartItemDTO> getCartItems(@PathVariable String userId);
}