package com.cart.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.client.ProductServiceClient;
import com.cart.client.UserServiceClient;
import com.cart.dto.ProductDTO;
import com.cart.dto.UserDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RemoteServiceHelper {

	@Autowired
	private ProductServiceClient productServiceClient;

	private static final Logger log = LoggerFactory.getLogger(RemoteServiceHelper.class);

	@Autowired
	private UserServiceClient userServiceClient;

	@CircuitBreaker(name = "productServiceCB", fallbackMethod = "productFallback")
	@Retry(name = "productServiceRetry")
	public ProductDTO getProductById(Long productId) {
		return productServiceClient.getProduct(productId);
	}

	public ProductDTO productFallback(Long productId, Throwable ex) {
		throw new IllegalStateException("Product service is unavailable", ex);
	}

	@CircuitBreaker(name = "userServiceCB", fallbackMethod = "userFallback")
	@Retry(name = "userServiceRetry")
	public UserDTO getUserById(String userId) {
		log.info("ACTUAL CALL: calling /users/{}", userId);
		return userServiceClient.getUser(userId);
	}

	public UserDTO userFallback(String userId, Throwable ex) {
		log.error("Fallback triggered. Error: {}", ex.getMessage(), ex);
		throw new IllegalStateException("User service is unavailable", ex);
	}
}
