package com.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.config.FeignClientConfig;
import com.order.dto.ProductDTO;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductServiceClient {
	
	@GetMapping("/products/get/{productId}")
	ProductDTO getProduct(@PathVariable(name = "productId") Long productId);

}
