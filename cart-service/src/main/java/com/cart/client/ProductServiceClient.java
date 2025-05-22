package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.dto.ProductDTO;

@FeignClient(name = "product-service",url = "http://localhost:8081")
public interface ProductServiceClient {

	@GetMapping("/api/products/get/{productId}")
	ProductDTO getProductById(@PathVariable("productId") Long productId);
}
