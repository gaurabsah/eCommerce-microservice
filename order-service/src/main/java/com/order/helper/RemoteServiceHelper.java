package com.order.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.client.CartServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RemoteServiceHelper {
	
	@Autowired
	private CartServiceClient cartServiceClient;
	
	@CircuitBreaker(name = "cartServiceCB", fallbackMethod = "fallbackClearCart")
    @Retry(name = "cartServiceRetry")
    public void clearCart(String userId) {
        cartServiceClient.clearCart(userId);
    }

    public void fallbackClearCart(String userId, Throwable ex) {
        throw new IllegalStateException("Cart service is unavailable for user: " + userId, ex);
    }

}
