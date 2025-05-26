package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment.config.FeignClientConfig;
import com.payment.dto.ProductDTO;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductServiceClient {

	@PutMapping("/products/update-stock")
	ProductDTO updateProductStock(@RequestParam Long productId, @RequestParam int stock);
	
	@GetMapping("/products/get/{productId}")
	ProductDTO getProduct(@PathVariable(name = "productId") Long productId);

}
