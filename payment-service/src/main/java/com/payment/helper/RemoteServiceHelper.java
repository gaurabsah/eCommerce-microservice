package com.payment.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.client.OrderServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RemoteServiceHelper {

	@Autowired
	private OrderServiceClient orderServiceClient;

	@CircuitBreaker(name = "orderServiceCB", fallbackMethod = "fallbackUpdateStatus")
	@Retry(name = "orderServiceRetry")
	public void updateOrderStatus(Long orderId, String status) {
		orderServiceClient.updateOrderStatus(orderId, status);
	}

	public void fallbackUpdateStatus(Long orderId, String status, Throwable ex) {
		throw new IllegalStateException("Order service is unavailable for order ID: " + orderId, ex);
	}

}
