package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.config.FeignConfig;
import com.cart.dto.ProductDTO;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

	@GetMapping("/products/get/{productId}")
	ProductDTO getProduct(@PathVariable("productId") Long productId);
}
