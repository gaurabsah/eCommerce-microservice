package com.order.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.client.ProductServiceClient;
import com.order.dto.ProductDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RemoteServiceHelper {

	@Autowired
	private ProductServiceClient productServiceClient;

	private static final Logger log = LoggerFactory.getLogger(RemoteServiceHelper.class);

	@CircuitBreaker(name = "productServiceCB", fallbackMethod = "fallbackGetProduct")
	@Retry(name = "productServiceRetry")
	public ProductDTO getProduct(Long productId) {
		log.info("inside RemoteServiceHelper:: getProduct()...");
		ProductDTO product = productServiceClient.getProduct(productId);
		return product;
	}

	public ProductDTO fallbackGetProduct(Long productId, Throwable ex) {
		throw new IllegalStateException("Product service is unavailable for product ID: " + productId, ex);
	}

}
